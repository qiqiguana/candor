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
// Copyright 2003 Ken Harris <kbh7@cornell.edu>
//

package corina.formats;

import corina.Sample;
import corina.Element;
import corina.ui.I18n;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
   The name and range of a sum's elements (no data).

   For a sum, output the name and range of each elem<p>ent, one per
   line, with columns separated by tabs, and elements separated by
   newlines.  For example:</p>

<pre>
Sample	Start	End	Span
ZKB-1   1001    1236    236
ZKB-2   1003    1072    70
ZKB-3   1011    1099    89
</pre>

   <p>(Useful for dumping into spreadsheets, or generating bar
   graphs.)</p>

   <h2>Left to do</h2>
   <ul>
     <li>i18n (2 ioe's)
   </ul>

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: RangesOnly.java,v 1.1 2004/01/18 17:58:11 aaron Exp $
*/
public class RangesOnly implements Filetype {

    public String toString() {
        return I18n.getText("format.ranges_only");
    }

    public String getDefaultExtension() {
	return ".TXT";
    }

    /**
       Throws a WrongFiletypeException - RangesOnly files can't be
       loaded.  (This should never be called.)

       @return never returns
       @exception WrongFiletypeException always thrown
    */
    public Sample load(BufferedReader r) throws IOException {
        throw new WrongFiletypeException();
    }

    public void save(Sample s, BufferedWriter w) throws IOException {
        // verify it's a master
        if (s.elements == null)
            throw new IOException("Ranges-only format is only available " +
				  "for summed samples with Elements");

	// write a header
	w.write(I18n.getText("sample"));
	w.write("\t");
	w.write(I18n.getText("browser_start"));
	w.write("\t");
	w.write(I18n.getText("browser_end"));
	w.write("\t");
	w.write(I18n.getText("browser_length"));
	w.newLine();

        int n = s.elements.size();
        for (int i=0; i<n; i++) {
            Element e = (Element) s.elements.get(i);
	    // OBSOLETE: getRange(), etc., now load the file automatically.
	    // but BUG: they don't throw anything if it fails.
	    // (and i don't have a getMeta() yet, so it's not unnecessary.)
            try {
                e.loadMeta(); // this aborts if element can't be loaded.
            } catch (IOException ioe) {
                throw new IOException("Can't load element " + e.getFilename());
            }

            // output (name, start, end)
            if (e.details.containsKey("title"))
                w.write(e.details.get("title").toString());
            else
                w.write(e.details.get("filename").toString());
            w.write("\t");
            w.write(e.getRange().getStart().toString());
            w.write("\t");
            w.write(e.getRange().getEnd().toString());
            w.write("\t");
            w.write(String.valueOf(e.getRange().span()));
	    w.newLine();
        }
    }
}
