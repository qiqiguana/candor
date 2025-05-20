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

package corina.gui.menus;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import corina.core.App;
import corina.gui.CanOpener;
import corina.ui.Alert;
import corina.ui.Builder;

/**
    A menu which shows recently-opened files.

    <p>(It's actually implemented as a list of recently-opened files,
    and a factory method to generate <code>JMenu</code>s.  But this will
    change in the near future.)</p>

    <p>To use, simply call <code>OpenRecent.generateMenu()</code>, and
    use that as you would any other <code>JMenu</code>.  It will
    automatically be updated whenever the list changes due to some new
    file being opened.  (When it's no longer strongly
    reachable, it'll be garbage collected.)</p>
    
    <h2>Left to do:</h2>
    <ul>
        <li>rename to OpenRecentMenu
        <li>extend JMenu; the c'tor should take care of notification stuff (add/removeNotify(), if necessary)
        <li>i don't need to use refs if i use add/remove notify, right?
        <li>doesn't use special 1.3 font hack any more -- do i care?
        <li>refactor -- separate model and view?
        <li>catch errors gracefully - "this file may have been moved...", etc.
        <li>if document is already open, just toFront() (needs other work first)
    </ul>

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
    @version $Id: OpenRecent.java,v 1.3 2005/01/24 03:09:30 aaron Exp $
*/
public class OpenRecent {
    // number of recent docs to remember
    private final static int NUMBER_TO_REMEMBER = 10;

    // list of files, most-recent-first
    private static List recent;

    // list of created menus -- soft references
    private static List menus=new ArrayList();

    // on class load: load previous recent-list from system property into static structure
    static {
	loadList();
    }

    /** Indicate to the recent-file list that a file was just opened.
	This also updates every recent-file menu automatically.
	@param filename the (full) name of the file that was opened
     */
    public static void fileOpened(String filename) {
	// if already in spot #0, don't need to do anything
	if (!recent.isEmpty() && ((String) recent.get(0)).equals(filename))
	    return;

	// if this item is in the list, remove me, too
	recent.remove(filename);

	// remove last item, if full
	if (recent.size() == NUMBER_TO_REMEMBER)
	    recent.remove(NUMBER_TO_REMEMBER-1);

	// prepend filename
	recent.add(0, filename);

	// update menu(s)
	updateAllMenus();

	// update disk
	saveList();
    }

    /** Generate a new recent-file menu.  This menu will contain the
	names of (up to) the last 4 files opened.  As long as the menu
	returned by this method is referenced, it will automatically
	be kept updated. */
    public static JMenu makeOpenRecentMenu() {
        // create a new menu
        JMenu menu = Builder.makeMenu("open_recent");

        // generate its elements
        updateMenu(menu);

        // add it to the list
        menus.add(new WeakReference(menu));

        // return it
        return menu;
    }

    // update all existing open-recent menus.  if any has since
    // disappeared, remove it from my list.
    private static void updateAllMenus() {
	// for each menu...
	for (int i=0; i<menus.size(); i++) {
	    // dereference it
	    JMenu m = (JMenu) ((Reference) menus.get(i)).get();

	    // already gone?  remove it from my list
	    if (m == null) {
		menus.remove(i);
		continue;
	    }

	    // update it
	    updateMenu(m);
	}
    }

    // BUG: error handling in here is miserable-to-nonexistant
    private static void updateMenu(JMenu menu) {
	menu.removeAll();
	for (int i=0; i<recent.size(); i++) {
	    String fn = (String) recent.get(i);
	    JMenuItem r = new JMenuItem(new File(fn).getName());

	    final int glue=i;
	    r.addActionListener(new AbstractAction() {
		    // todo: if already open, just toFront() it!
		    public void actionPerformed(ActionEvent e) {
			try {
			    CanOpener.open((String) recent.get(glue));
			} catch (FileNotFoundException fnfe) {
			    // file moved?
			    Alert.error("File Isn't There",
					"The file called '" + recent.get(glue) + "'\n" +
					"isn't there any more.  If it was moved, " +
					"you'll have to open it with File -> Open...");

			    // remove it from the list
			    recent.remove(glue);
			    updateAllMenus(); // (doesn't really update all menus on mac.  ack.)

			    // FUTURE: search for it, or allow user to.

			    return;
			} catch (IOException ioe) {
			    // !!!
			    return;
			}

			/* -- don't do this; CanOpener.open() calls fileOpened(), which takes care of this
			// move this entry to top of list, now
			if (glue != 0) {
			    String me = (String) recent.remove(glue);
			    recent.add(0, me);
			    updateAllMenus(); // ok?

			    // update disk
			    saveList();
			}
			*/
		    }
		});
	    menu.add(r);
	}

	JMenuItem clear = Builder.makeMenuItem("clear_menu");
	if (recent.isEmpty()) {
	    // no recent items: just "Clear Menu", but dimmed
	    clear.setEnabled(false);
	    menu.add(clear);
	} else {
	    // some recent items: spacer, then "Clear Menu"
	    clear.addActionListener(new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
			recent = new ArrayList(); // why not something like reset()?
			updateAllMenus();
			saveList();
		    }
		});
	    menu.addSeparator();
	    menu.add(clear);
	}
    }

    // load recent-list from |corina.recent.files|.
    // only called on class-load, so doesn't need to be synch.
    private static void loadList() {
	// create recent list
	recent = new ArrayList();

	// parse |corina.recent.files| pref, splitting by |path.separator| chars.
	// (ASSUMES (path.separator).length()==1?
	StringTokenizer tok = new StringTokenizer(App.prefs.getPref("corina.recent.files", ""),
						  System.getProperty("path.separator"));

	// add all files to recent
	while (tok.hasMoreTokens()) {
	    String next = tok.nextToken();
	    recent.add(next);
	}
    }

    // store the recent-list in a string, in |corina.recent.files|
    private static synchronized void saveList() {
	StringBuffer buf = new StringBuffer();

	char sep = File.pathSeparatorChar;

	for (int i=0; i<recent.size(); i++) {
	    buf.append(recent.get(i).toString());
	    if (i < recent.size()-1)
		buf.append(sep);
	}

	// store in pref
  App.prefs.setPref("corina.recent.files", buf.toString());
    }
}
