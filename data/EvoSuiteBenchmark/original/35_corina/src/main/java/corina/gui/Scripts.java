// 
// This file is part of Corina.
// 
// Corina is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// Corina is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Corina; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Copyright 2001 Ken Harris <kbh7@cornell.edu>
//

package corina.gui;

import corina.Year;
import corina.Range;
import corina.Sample;
import corina.Element;
import corina.index.Index;
import corina.index.Exponential; // extract const! -- better: let user pick!
import corina.util.Overwrite;
import corina.ui.Builder;
import corina.ui.Alert;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

/*
  3 things which used to be written as python scripts, but should be
  better integrated.  plus i'm becoming less and less impressed with
  python.

  this desparately needs REFACTORING, but for now just getting it a
  bit more homogeneous should help.
*/

// TODO: move to appropriate places, like corina.index.Index.indexMany()
// BUG: after one of these is called by the browser, how will it know to update itself?
// -- do i need a way to say "hey browser, add/modify just this one element"?

/*
  ok, wise guy, what's a better user interface for this?
  -- (???)
*/

public class Scripts {

    // given a (possibly fully qualified) filename, return only its name.
    // REFACTOR: move this to util?
    // RENAME: not how it works, but what it does -- extractName()?
    public static String removeFolders(String filename) {
	return new File(filename).getName();
    }

    //
    // --------------------------------------------------
    //

    // given the filename of a sample, load it, and
    // split even/odd years into "-early" and "-late" files
    public static void splitIntoEarlyLate(String filename) {
	// load it
	Sample raw;
	try {
	    raw = new Sample(filename);
	} catch (IOException ioe) {
	    String f = removeFolders(filename);
	    Alert.error("Error loading sample",
			"The sample \"" + f + "\" could not be loaded.");
	    return;
	}

	// make sure we're not summed
	if (raw.isSummed()) {
	    String f = removeFolders(filename);
	    Alert.error("Can't split summed sample",
			"The sample \"" + f + "\" could not\n" +
			"be split, because it is summed file.\n");
	    return;
	}

	// split it: data
	Sample early = new Sample();
	Sample late = new Sample();
	for (int i=0; i<raw.data.size(); i++) {
	    if (i%2 == 0)
		early.data.add(raw.data.get(i));
	    else
		late.data.add(raw.data.get(i));
	}

	// compute range
	Year start = raw.range.getStart();
	early.range = new Range(start, early.data.size());
	late.range = new Range(start, late.data.size());

	// copy meta, and set titles
	early.meta = new Hashtable(raw.meta);
	late.meta = new Hashtable(raw.meta);
	String rawTitle = (String) raw.meta.get("title");
	early.meta.put("title", rawTitle + " - Earlywood");
	late.meta.put("title", rawTitle + " - Latewood");

	// make the new filenames
	String earlyName = filename + " - early";
	String lateName = filename + " - late";

	// save the halves
	try {
	    Overwrite.overwrite(earlyName);
	    early.save(earlyName);
	} catch (IOException ioe) {
	    Alert.error("Error saving sample",
			"There was an error while trying to " +
			"save one of the results:\n" +
			ioe.getMessage());
	    return;
	} catch (UserCancelledException uce) {
	    // don't do anything
	} catch (RuntimeException re) {
	    Bug.bug(re);
	}

	// REFACTOR: these 2 are almost exactly the same
	// (and probably the same as elsewhere)

	try {
	    Overwrite.overwrite(lateName);
	    late.save(lateName);
	} catch (IOException ioe) {
	    Alert.error("Error saving sample",
			"There was an error while trying to " +
			"save one of the results:\n" +
			ioe.getMessage());
	    return;
	} catch (UserCancelledException uce) {
	    // don't do anything
	} catch (RuntimeException re) {
	    Bug.bug(re);
	}
    }

    //
    // --------------------------------------------------
    //

    // given the filename
    public static void joinEarlyLate(String filename) {
	// load it
	Sample raw;
	try {
	    raw = new Sample(filename);
	} catch (IOException ioe) {
	    Alert.error("Error loading sample",
			"The sample \"" + removeFolders(filename) + "\" " +
			"could not be loaded.");
	    return;
	}

	// join it: data
	List joined = new ArrayList();
	for (int i=0; i<raw.data.size()/2; i++) {
	    int a = ((Number) raw.data.get(2*i)).intValue();
	    int b = ((Number) raw.data.get(2*i+1)).intValue();
	    joined.add(new Integer(a+b));
	}

	// what if the length is odd?  take the odd last value as-is.
	if (raw.data.size() % 2 == 1)
	    joined.add(raw.data.get(raw.data.size()-1));

	// assign data list
	raw.data = joined;

	// recompute range
	Year start = raw.range.getStart();
	raw.range = new Range(start, raw.data.size());

	// adjust title
	String title = (String) raw.meta.get("title");
	if (title == null || title.equals(""))
	    title = "Untitled";
	title = title + " - joined";
	raw.meta.put("title", title);

	// BUG: doList doesn't update if summary is changed, e.g., n=63->n=64

	// return filename, so browser can select it?

	try {
	    // get new target file
	    filename = filename + " - joined";

	    // overwrite?
	    Overwrite.overwrite(filename);

	    // save it
	    raw.save(filename);
	} catch (IOException ioe) {
	    Alert.error("Error saving sample",
			"There was an error while trying to " +
			"save the result:\n" +
			ioe.getMessage());
	} catch (UserCancelledException uce) {
	    // don't do anything
	}
    }

    //
    // --------------------------------------------------
    //

    // change |name|'s extension to |ext|, if it has one, or add |ext| if it doesn't.
    // e.g., changeExtension("myfile.raw", "ind") => "myfile.ind"
    private static String changeExtension(String name, String ext) {
	// deal with ext starting with dot -- i might imagine it goes either way,
	// so i might as well handle both.
	if (ext.startsWith("."))
	    ext = ext.substring(1);

	int lastDot = name.lastIndexOf('.');
	if (lastDot == -1)
	    return name + '.' + ext;
	else
	    return name.substring(0, lastDot) + '.' + ext;
    }

    // let the user select several files, and index them all
    // TODO: let user pick algorithm?  (show index-multi-dialog with indexes for each?)
    // TODO: better exception handling?

    /* unused, now:
    public static void indexManyFiles() {
	try {
	    List files = FileDialog.showMulti("Index");
	    indexManyFiles(files);
	} catch (UserCancelledException uce) {
	    // ignore
	}
    }
    */

    // BUG: what if it's already indexed?

    public static void indexManyFiles(List elements) {
	// keep a count of how many were already indexed, so i can
	// tell the user nicely at the end, once, instead of bugging
	// him at each file
	int alreadyIndexed = 0;

	// index each one as exponential, and save it as .IND
	for (int i=0; i<elements.size(); i++) {
	    Element e = (Element) elements.get(i);
	    Sample s;
	    try {
		s = e.load();
	    } catch (IOException ioe) {
		String f = removeFolders(e.getFilename());
		Alert.error("Error loading sample",
			    "The sample \"" + f + "\" could not be loaded.");
		continue;
	    }

	    // already indexed?
	    // PERF: i can find out if it's indexed by looking at the summary,
	    // which i know is loaded, because it's in the browser...
	    if (s.isIndexed()) {
		alreadyIndexed++;
		continue;
	    }

	    // run an exp index
	    Index index = new Exponential(s);
	    index.run();
	    index.apply();

	    // add "(indexed)" to the title, so they know later
	    s.meta.put("title", s.meta.get("title") + " (indexed)");

	    // take the filename, and make it end in .IND.
	    String name = new File((String) s.meta.get("filename")).getPath();

	    // change the extension, or add one if it didn't have one
	    name = changeExtension(name, "IND");;

	    try {
		// make sure it's safe to write here
		Overwrite.overwrite(name);

		// save it
		s.save(name);
	    } catch (IOException ioe) {
		String f = removeFolders(e.getFilename());
		Alert.error("Error saving sample",
			    "The sample \"" + f + "\" could not be saved.");
	    } catch (UserCancelledException uce) {
		// skip it
	    }
	}

	// tell the user about the files which were already indexed.
	if (alreadyIndexed != 0) {
	    // make some effort at phrasing it appropriately
	    String alert;
	    if (alreadyIndexed == 1 && elements.size() == 1)
		alert = "This file is already indexed.";
	    else if (alreadyIndexed == elements.size())
		alert = "All of the selected files are already indexed.";
	    else
		alert = alreadyIndexed + " of the selected files were already indexed.";

	    // tell them
	    Alert.error("Already indexed", alert);
	}
    }

}
