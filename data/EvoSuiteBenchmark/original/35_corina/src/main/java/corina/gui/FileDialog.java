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

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import corina.core.App;
import corina.logging.CorinaLog;
import corina.ui.I18n;

/**
 * A wrapper for JFileChooser, providing typical (Corina) usage.
 * 
 * <p>
 * Here's what they provide, and why you'd want to use this class over
 * JFileChooser directly:
 * </p>
 * 
 * <ul>
 * 
 * <li>automatically uses a bunch of default filters</li>
 * 
 * <li>gets its initial directory from corina.dir.data; subsequent calls start
 * from the last-used-directory</li>
 * 
 * <li>incredibly neat preview component</li>
 * 
 * <li>multiple-file-chooser lets the user select several files from one dialog
 * </li>
 * 
 * <li>simple return values: a String (for single-selection) or a List of
 * Strings (for multiple-selection), or throws a UserCancelledException if the
 * user cancelled. No need to mess with JFileChooser.APPROVE_OPTION</li>
 * 
 * </ul>
 * 
 * @see corina.gui.UserCancelledException
 * @see javax.swing.JFileChooser
 * 
 * @author Ken Harris &lt;kbh7 <i style="color: gray">at </i> cornell <i
 *         style="color: gray">dot </i> edu&gt;
 * @author Aaron Hamid
 * @version $Id: FileDialog.java,v 1.12 2006/10/08 10:17:51 lucasmo Exp $
 */
public class FileDialog {
	private static final Dimension SINGLE_DEFAULT_DIMENSION = new Dimension(640, 480);
  	private static final Dimension MULTI_DEFAULT_DIMENSION = new Dimension(640, 480);
  	private static final String CLASS_NAME = FileDialog.class.getName();
  	private static final String SINGLE_DIM_PREF = CLASS_NAME + ".single.dimension";
  	private static final String MULTI_DIM_PREF = CLASS_NAME + ".double.dimension";
  	private static final String SINGLE_VIEWMODE_PREF = CLASS_NAME + ".single.viewmode";
  	private static final String MULTI_VIEWMODE_PREF = CLASS_NAME + ".double.viewmode";

  	private static final CorinaLog log = new CorinaLog("Prefs");

	private FileDialog() {
		// (don't instantiate me)
	}

	private static class ExtensionFilter extends FileFilter {
		private String TLA, tla;

		private String name;

		/**
		 * Create a new filter with the given name, for the given TLA.
		 * @param tla
		 *          3-letter-acronym to match as an extension
		 * @param name
		 *          the name of this filter
		 */
		public ExtensionFilter(String tla, String name) {
			this.tla = tla.toLowerCase();
			this.TLA = tla.toUpperCase();
			this.name = name + " (*." + this.TLA + ")";
		}

		/**
		 * Decide whether this file ends in the given TLA.
		 * @param f
		 *          the file to test
		 * @return true, if and only if the filename ends in the TLA
		 */
		public boolean accept(File f) {
			// users can always traverse a directory
			if (f.isDirectory())
				return true;
			// it's okay if it ends with the TLA, either case
			String filename = f.getName();
			return (filename.endsWith(tla) || filename.endsWith(TLA));
		}

		/**
		 * The user-readable description of this filter. Specified as "name
		 * (*.TLA)".
		 * @return the user-readable name of this filter
		 */
		public String getDescription() {
			return name;
		}
	}

	/**
	 * The default list of filters to use, as a list of extensions.
	 */
	public static String FILTERS[] = new String[] { "raw", "sum", "rec", "ind",
			"cln", "trn", };

	// add all default filters to this target, and then reset to default (*.*)
	private static void addFilters(JFileChooser f) {
		for (int i = 0; i < FILTERS.length; i++)
			f.addChoosableFileFilter(new ExtensionFilter(FILTERS[i], I18n
					.getText("." + FILTERS[i])));
		// REFACTOR: should only need to pass FILTERS[i] to constructor here
		f.setFileFilter(f.getAcceptAllFileFilter());
	}

	// working directory -- this gets updated whenever OK is clicked
	// XXX: can you say race condition - aaron... dependence on static
	// initializers, data and methods needs to be fixed
	// If this class is referenced before Prefs is, we are SOL
	private static String wd = App.prefs.getPref("corina.dir.data");

	/**
	 * This is a big hack to snoop into the JFileChooser GUI, and find
	 * the details view mode button and fire it, so we can preserve
	 * settings. 
	 */
	private static void setConfiguredMode(JFileChooser chooser, String pref) {
		String v = App.prefs.getPrefs().getProperty(pref, "list");
		if (!"details".equals(v))
			return;
		Component[] comps = chooser.getComponents();
		// ok, the JFileChooser contains a JToolBar
		for (int i = 0; i < comps.length; i++) {
			if (!(comps[i] instanceof JToolBar))
				continue;
			Component[] comps2 = ((JToolBar) comps[i]).getComponents();
			// and the JToolBar has JToggleButtons
			for (int j = 0; j < comps2.length; j++) {
				if (!(comps2[j] instanceof JToggleButton))
					continue;
				JToggleButton tb = (JToggleButton) comps2[j];
				String s = tb.getAccessibleContext().getAccessibleName();
				if (s != null
						&& s.equals(UIManager.getString(
								"FileChooser.detailsViewButtonAccessibleName",
								chooser.getLocale()))) {
					tb.doClick();
					return;
				}
			}
		}
	}

	private static void saveConfiguredMode(JFileChooser chooser, String pref) {
		Component[] comps = chooser.getComponents();
		// ok, the JFileChooser contains a JToolBar
		for (int i = 0; i < comps.length; i++) {
			if (!(comps[i] instanceof JToolBar))
				continue;
			Component[] comps2 = ((JToolBar) comps[i]).getComponents();
			// and the JToolBar has JToggleButtons
			for (int j = 0; j < comps2.length; j++) {
				if (!(comps2[j] instanceof JToggleButton))
					continue;
				JToggleButton tb = (JToggleButton) comps2[j];
				String s = tb.getAccessibleContext().getAccessibleName();
				if (s != null
						&& s.equals(UIManager.getString(
								"FileChooser.detailsViewButtonAccessibleName",
								chooser.getLocale()))) {
					if (tb.isSelected()) {
						App.prefs.getPrefs().setProperty(pref, "details");
					} else {
						App.prefs.getPrefs().setProperty(pref, "list");
					}
				}
			}
		}
	}

	
	/**
	 * Show a file selection dialog. This allows the user to select one file. It
	 * shows a preview component, and has the default filters available.
	 * 
	 * @param prompt
	 *          the text string to use for both the title bar and approve button
	 * @return the filename that was selected
	 * @exception UserCancelledException
	 *              if the user cancelled
	 */
	public static String showSingle(String prompt) throws UserCancelledException {
		return showSingle(prompt, wd);
	}
	
	/**
	 * Show a file selection dialog. This allows the user to select one file. It
	 * shows a preview component, and has the default filters available.
	 * 
	 * @param prompt
	 *          the text string to use for both the title bar and approve button
	 * @return the filename that was selected
	 * @exception UserCancelledException
	 *              if the user cancelled
	 */
	public static String showSingle(String prompt, String workingDirectory)
			throws UserCancelledException {
		// create chooser
		JFileChooser f = new JFileChooser(workingDirectory);

		// add filters
		addFilters(f);

		// preview component
		f.setAccessory(new SamplePreview(f));

		// make the window a resonable size to see everything
		Dimension dim = App.prefs.getDimensionPref(SINGLE_DIM_PREF,
				SINGLE_DEFAULT_DIMENSION);
		f.setPreferredSize(dim);
		setConfiguredMode(f, SINGLE_VIEWMODE_PREF);

		// show the dialog
		int result = f.showDialog(null, prompt);

		try {
			if (result == JFileChooser.APPROVE_OPTION) {
				// ok: store wd, and return file
				wd = f.getCurrentDirectory().getPath();
				return f.getSelectedFile().getPath();
			} else {
				// cancel
				throw new UserCancelledException();
			}
		} finally {
			Dimension d = f.getSize();
			App.prefs.setPref(SINGLE_DIM_PREF, d.width + "," + d.height);
			saveConfiguredMode(f, SINGLE_VIEWMODE_PREF);
		}
	}

	// --------------------------------------------------
	// multi
	//

	/**
	 * Show a multiple file selection dialog. This allows the user to select any
	 * number of files. It shows a preview component, and has the default filters
	 * available.
	 * 
	 * @param prompt
	 *          the text string to use for both the title bar and approve button
	 * @return a List of filenames that were selected
	 * @exception UserCancelledException
	 *              if the user cancelled
	 */
	public static List showMulti(String prompt) throws UserCancelledException {
		// create a new list to use
		List list = new ArrayList();
		return showMultiReal(prompt, list);
	}

	/**
	 * Show a multiple file selection dialog, with a list of files to start with.
	 * This allows the user to select any number of files. It shows a preview
	 * component, and has the default filters available.
	 * 
	 * @param prompt
	 *          the text string to use for both the title bar and approve button
	 * @param list
	 *          a List of filenames to have already selected
	 * @return a List of filenames that were selected
	 * @exception UserCancelledException
	 *              if the user cancelled
	 */
	public static List showMulti(String prompt, List list)
			throws UserCancelledException { // to edit a list
		// use the given list
		return showMultiReal(prompt, list);
	}

	private static List showMultiReal(String prompt, List list)
			throws UserCancelledException {
		// big-preview-list-component-thingy ... yeah.
		final MultiPreview mp = new MultiPreview(list);

		// make double-clicking a file call MultiPreview's addClicked() method
		JFileChooser f = new JFileChooser(wd) {
			public void approveSelection() {
				mp.addClicked(); // heh heh heh...
			}
		};

		// filters
		addFilters(f);

		// hide ok/cancel, we'll do that ourselves
		f.setControlButtonsAreShown(false);
		
		// allow multiple selection!
		f.setMultiSelectionEnabled(true);

		// preview component + multi-list
		mp.hook(f); // add reference to this jfilechooser
		f.setAccessory(mp);

		// make the window a resonable size to see everything
		Dimension dim = App.prefs.getDimensionPref(MULTI_DIM_PREF,
				MULTI_DEFAULT_DIMENSION);
		f.setPreferredSize(dim);
		setConfiguredMode(f, MULTI_VIEWMODE_PREF);

		// show dialog
		f.showDialog(null, prompt); // don't care what the return value is, it's
		// always CANCEL

		Dimension d = f.getSize();
		App.prefs.setPref(MULTI_DIM_PREF, d.width + "," + d.height);
		saveConfiguredMode(f, MULTI_VIEWMODE_PREF);

		// store wd, if ok
		if (mp.getSamples() != null)
			wd = f.getCurrentDirectory().getPath();

		// null? have to deal, now.
		if (mp.getSamples() == null)
			throw new UserCancelledException();

		// return samples
		return mp.getSamples();
	}

}