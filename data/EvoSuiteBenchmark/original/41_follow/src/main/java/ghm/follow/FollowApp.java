/*
 * Copyright (C) 2000-2003 Greg Merrill (greghmerrill@yahoo.com)
 * 
 * This file is part of Follow (http://follow.sf.net).
 * 
 * Follow is free software; you can redistribute it and/or modify it under the
 * terms of version 2 of the GNU General Public License as published by the Free
 * Software Foundation.
 * 
 * Follow is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Follow; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA
 */

package ghm.follow;

import ghm.follow.config.Configure;
import ghm.follow.config.FollowAppAttributes;
import ghm.follow.event.WindowTracker;
import ghm.follow.gui.About;
import ghm.follow.gui.Clear;
import ghm.follow.gui.ClearAll;
import ghm.follow.gui.Close;
import ghm.follow.gui.Debug;
import ghm.follow.gui.Delete;
import ghm.follow.gui.DeleteAll;
import ghm.follow.gui.DndFileOpener;
import ghm.follow.gui.Edit;
import ghm.follow.gui.Exit;
import ghm.follow.gui.FileFollowingPane;
import ghm.follow.gui.FollowAppAction;
import ghm.follow.gui.Menu;
import ghm.follow.gui.ComponentBuilder;
import ghm.follow.gui.Open;
import ghm.follow.gui.Pause;
import ghm.follow.gui.PopupMenu;
import ghm.follow.gui.Reset;
import ghm.follow.gui.StartupStatus;
import ghm.follow.gui.TabbedPane;
import ghm.follow.gui.ToolBar;
import ghm.follow.gui.FollowAppAction.ActionContext;
import ghm.follow.nav.Bottom;
import ghm.follow.nav.NextTab;
import ghm.follow.nav.PreviousTab;
import ghm.follow.nav.Top;
import ghm.follow.search.ClearAllHighlights;
import ghm.follow.search.ClearHighlights;
import ghm.follow.search.Find;
import ghm.follow.search.SearchableTextPane;
import ghm.follow.systemInterface.DefaultSystemInterface;
import ghm.follow.systemInterface.SystemInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.dnd.DropTarget;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class' main() method is the entry point into the Follow application.
 * 
 * @see #main(String[])
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class FollowApp
{
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String MESSAGE_LINE_SEPARATOR = "\n";
	public static final boolean DEBUG = Boolean.getBoolean("follow.debug");
	public static boolean HAS_SOLARIS_BUG = false;

	private static Logger LOG = Logger.getLogger(FollowApp.class.getName());
	private int currentCursor = Cursor.DEFAULT_CURSOR;
	private Cursor defaultCursor;
	private Cursor waitCursor;
	private Map<File, FileFollowingPane> fileToFollowingPaneMap = new HashMap<File, FileFollowingPane>();
	private JTabbedPane tabbedPane;
	private ToolBar toolBar;
	private PopupMenu popupMenu;
	private Menu recentFilesMenu;
	private MouseListener rightClickListener;
	private HashMap<String, FollowAppAction> actions = new HashMap<String, FollowAppAction>();
	private SystemInterface systemInterface;
	private StartupStatus startupStatus;

	private FollowAppAttributes attributes;
	private static FollowApp instance;
	private static ResourceBundle resources = ResourceBundle
	        .getBundle("ghm.follow.FollowAppResourceBundle");
	private JFrame frame;

	// We should remove this hack once JDK 1.4 gets wide adoption on Solaris.
	static
	{
		boolean isSolaris = "SunOS".equals(System.getProperty("os.name"));

		if (isSolaris)
		{
			String version = System.getProperty("java.version");
			if ((version != null) && version.startsWith("1."))
			{
				String substring = version.substring(2, 3);
				try
				{
					int minor = Integer.parseInt(substring);
					if (minor < 4)
					{
						HAS_SOLARIS_BUG = true;
					}
				}
				catch (NumberFormatException nfe)
				{
					// Nothing else to do.
				}
			}
		}
	}

	/**
	 * @param fileNames
	 *            names of files to be opened
	 */
	FollowApp(List<String> fileNames) throws IOException, InterruptedException,
	        InvocationTargetException
	{
		this(fileNames, null);
	}

	FollowApp(List<String> filenames, File propertyFile) throws IOException, InterruptedException,
	        InvocationTargetException
	{
		// Create & show startup status window
		startupStatus = new StartupStatus(resources);
		centerWindowInScreen(startupStatus);
		startupStatus.pack();
		SwingUtilities.invokeAndWait(new Runnable()
		{
			public void run()
			{
				startupStatus.setVisible(true);
			}
		});

		// Ghastly workaround for bug in Font construction, in review by
		// Sun with review id 108683.
		GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		SwingUtilities.invokeAndWait(new Runnable()
		{
			public void run()
			{
				startupStatus.markDone(startupStatus.LOAD_SYSTEM_FONTS);
			}
		});

		// create frame first. the close operation is handled in WindowTracker
		frame = new JFrame(getResourceString("frame.title"));

		// load the attributes
		attributes = new FollowAppAttributes(propertyFile);

		// add listeners to update the recent files list
		RecentFileListener rfl = new RecentFileListener();
		attributes.addPropertyChangeListener(FollowAppAttributes.RECENT_FILES_KEY, rfl);
		attributes.addPropertyChangeListener(FollowAppAttributes.RECENT_FILES_MAX_KEY, rfl);

		// load the actions referenced in the application
		loadActions();

		// initialize SystemInterface
		systemInterface = new DefaultSystemInterface(this);

		// initialize menubar
		JMenuBar jMenuBar = ComponentBuilder.buildMenuBar(resources, getActions());

		// set the recent files menu to local variable so it can be updated
		// easily
		recentFilesMenu = ComponentBuilder.recentFilesMenu;

		// fake an event to get the menu setup initially
		rfl.propertyChange(null);

		// initialize popupMenu
		popupMenu = ComponentBuilder.buildPopupMenu(getActions());

		// initialize toolbar
		toolBar = ComponentBuilder.buildToolBar(getActions());

		// initialize tabbedPane, but wait to open files until after frame
		// initialization
		tabbedPane = new TabbedPane(attributes);
		enableDragAndDrop(tabbedPane);

		// initialize frame
		initFrame(jMenuBar);

		// This is an ugly hack. It seems like JFrame.setLocation() is buggy
		// on Solaris jdk versions before 1.4
		if (HAS_SOLARIS_BUG)
		{
			frame.setLocation(50, 50);
		}
		else
		{
			frame.setLocation(attributes.getX(), attributes.getY());
		}

		// track window close events. WindowTracker handles the close operation
		frame.addWindowListener(new WindowTracker(attributes, tabbedPane, systemInterface));
		enableDragAndDrop(frame);

		// Open files from attributes; this is done after the frame is complete
		// and all components have been added to it to make sure that the frame
		// can be shown absolutely as soon as possible. If we put this code
		// before frame creation (as in v1.0), frame creation may take longer
		// because there are more threads (spawned in the course of open())
		// contending for processor time.
		List<File> files = attributes.getFollowedFiles();
		StringBuffer nonexistentFilesBuffer = null;
		int nonexistentFileCount = 0;
		for (File file : files)
		{
			try
			{
				openFile(file);
			}
			catch (FileNotFoundException e)
			{
				// This file has been deleted since the previous execution.
				// Remove it from the list of followed files
				attributes.removeFollowedFile(file);
				nonexistentFileCount++;
				if (nonexistentFilesBuffer == null)
				{
					nonexistentFilesBuffer = new StringBuffer(file.getAbsolutePath());
				}
				else
				{
					nonexistentFilesBuffer.append(file.getAbsolutePath());
				}
				nonexistentFilesBuffer.append(MESSAGE_LINE_SEPARATOR);
			}
		}

		// open files from the command line
		for (String filename : filenames)
		{
			try
			{
				openFile(new File(filename));
			}
			catch (FileNotFoundException e)
			{
				String msg = MessageFormat.format(
				        getResourceString("message.cmdLineFileNotFound.text"),
				        new Object[] { filename });
				LOG.info(msg);
			}
		}

		if (nonexistentFileCount > 0)
		{
			// Alert the user of the fact that one or more files have been
			// deleted since the previous execution
			String text = getResourceString("message.filesDeletedSinceLastExecution.text");
			String message = MessageFormat.format(text, new Object[] { nonexistentFileCount,
			        nonexistentFilesBuffer.toString() });
			// String title =
			// getResourceString("message.filesDeletedSinceLastExecution.title");
			// JOptionPane.showMessageDialog(frame_, message, title,
			// JOptionPane.WARNING_MESSAGE);
			LOG.info(message);
		}

		int tabCount = tabbedPane.getTabCount();
		if (tabCount > 0)
		{
			if (tabCount > attributes.getSelectedTabIndex())
			{
				tabbedPane.setSelectedIndex(attributes.getSelectedTabIndex());
			}
			else
			{
				tabbedPane.setSelectedIndex(0);
			}
		}
	}

	/**
	 * Close the current tab
	 */
	public void closeFile()
	{
		FileFollowingPane fileFollowingPane = getSelectedFileFollowingPane();
		int tab = tabbedPane.getSelectedIndex();
		if (tab >= 0)
		{
			tabbedPane.removeTabAt(tab);
			disableDragAndDrop(fileFollowingPane.getTextPane());
			attributes.removeFollowedFile(fileFollowingPane.getFollowedFile());
			fileFollowingPane.stopFollowing();
			fileToFollowingPaneMap.remove(fileFollowingPane.getFollowedFile());
		}
		updateActions();
	}

	/**
	 * Get a string from the resource bundle. Convenience method to shorten and
	 * centralize this common call
	 * 
	 * @param key
	 * @return The value of key in the resource bundle. null if the key is not
	 *         found.
	 */
	public static String getResourceString(String key)
	{
		String value = null;
		try
		{
			value = resources.getString(key);
		}
		catch (MissingResourceException mre)
		{
			LOG.warning(mre.getMessage());
		}
		return value;
	}

	/**
	 * Gets an image icon from the resource path.
	 * 
	 * @param clazz
	 *            The class to use as an entry point to the resource path. Image
	 *            path should be relative to this class.
	 * @param iconNameKey
	 *            The resource key name where the image is defined.
	 * @return An image icon based on the URL generated from the value of
	 *         iconNameKey. null if no URL can be found.
	 */
	public static ImageIcon getIcon(Class<?> clazz, String iconNameKey)
	{
		String filename = getResourceString(iconNameKey);
		URL url = clazz.getResource(filename);
		LOG.finer("Class: " + clazz + ", iconNameKey: " + iconNameKey);
		LOG.finer("filename: " + filename);
		LOG.finer("url: " + url);
		ImageIcon icon = null;
		if (url != null)
		{
			icon = new ImageIcon(url);
			LOG.finer("errored: " + (java.awt.MediaTracker.ERRORED == icon.getImageLoadStatus()));
		}
		return icon;
	}

	/**
	 * Loads the actions used in the application
	 * 
	 * @throws IOException
	 */
	private void loadActions() throws IOException
	{
		// initialize actions
		putAction(Open.NAME, new Open(this));
		putAction(Close.NAME, new Close(this));
		putAction(Edit.NAME, new Edit(this));
		putAction(Exit.NAME, new Exit(this));
		putAction(Top.NAME, new Top(this));
		putAction(Bottom.NAME, new Bottom(this));
		putAction(Clear.NAME, new Clear(this));
		putAction(ClearAll.NAME, new ClearAll(this));
		putAction(Delete.NAME, new Delete(this));
		putAction(DeleteAll.NAME, new DeleteAll(this));
		putAction(Configure.NAME, new Configure(this));
		putAction(About.NAME, new About(this));
		if (DEBUG)
		{
			putAction(Debug.NAME, new Debug(this));
		}
		putAction(Pause.NAME, new Pause(this));
		putAction(NextTab.NAME, new NextTab(this));
		putAction(PreviousTab.NAME, new PreviousTab(this));
		putAction(Find.NAME, new Find(this));
		putAction(ClearHighlights.NAME, new ClearHighlights(this));
		putAction(ClearAllHighlights.NAME, new ClearAllHighlights(this));
		putAction(Reset.NAME, new Reset(this));
	}

	/**
	 * @param jMenuBar
	 */
	private void initFrame(JMenuBar jMenuBar)
	{
		frame.setJMenuBar(jMenuBar);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		frame.setSize(attributes.getWidth(), attributes.getHeight());
	}

	public void show()
	{
		frame.setVisible(true);
	}

	public FollowAppAction getAction(String name)
	{
		return actions.get(name);
	}

	/**
	 * Get all actions associated to the application
	 * 
	 * @return
	 */
	public HashMap<String, FollowAppAction> getActions()
	{
		return actions;
	}

	/**
	 * Set an action to the action map of the application.
	 * 
	 * @param name
	 *            The key to set the action to.
	 * @param action
	 *            The action to create an association for.
	 */
	public void putAction(String name, FollowAppAction action)
	{
		if (FollowAppAction.ActionContext.APP == action.getContext())
			action.setEnabled(true);
		else
			action.setEnabled(false);
		actions.put(name, action);
	}

	public void openFile(File file) throws FileNotFoundException
	{
		openFile(file, attributes.autoScroll());
	}

	/**
	 * Warning: This method should be called only from (1) the FollowApp
	 * initializer (before any components are realized) or (2) from the event
	 * dispatching thread.
	 */
	void openFile(File file, boolean startFollowing) throws FileNotFoundException
	{
		if (file == null)
		{
			throw new FileNotFoundException("file is null.");
		}
		if (!file.exists())
		{
			throw new FileNotFoundException(file.getName() + " not found.");
		}
		FileFollowingPane fileFollowingPane = (FileFollowingPane) fileToFollowingPaneMap.get(file);
		if (fileFollowingPane != null)
		{
			// File is already open; merely select its tab
			tabbedPane.setSelectedComponent(fileFollowingPane);
		}
		else
		{
			fileFollowingPane = new FileFollowingPane(file, attributes.getBufferSize(), attributes
			        .getLatency(), attributes.autoScroll(), attributes.getFont(), attributes
			        .getTabSize());
			SearchableTextPane ffpTextPane = fileFollowingPane.getTextPane();
			enableDragAndDrop(ffpTextPane);
			fileFollowingPane.setSize(frame.getSize());
			ffpTextPane.setFont(attributes.getFont());
			ffpTextPane.addMouseListener(getRightClickListener());
			fileToFollowingPaneMap.put(file, fileFollowingPane);
			if (startFollowing)
			{
				fileFollowingPane.startFollowing();
			}
			tabbedPane.addTab(file.getName(), null, fileFollowingPane, file.getAbsolutePath());
			int tabCount = tabbedPane.getTabCount();
			if (tabCount < 10)
			{
				// KeyEvent.VK_1 through KeyEvent.VK_9 is represented by the
				// ascii characters 1-9 (49-57)
				int index = tabCount - 1;
				tabbedPane.setMnemonicAt(index, index + ((int) '1'));
			}
			tabbedPane.setSelectedIndex(tabCount - 1);
			// add a listener to set the pause icon correctly
			fileFollowingPane.addComponentListener(new ComponentAdapter()
			{
				public void componentShown(ComponentEvent e)
				{
					FileFollowingPane ffp = (FileFollowingPane) e.getSource();
					Pause pause = (Pause) getAction(Pause.NAME);
					pause.setIconByState(ffp.isFollowingPaused());
				}
			});

			// add the file to history
			attributes.addFollowedFile(file);
			attributes.addRecentFile(file);

			updateActions();
		}
	}

	private void updateActions()
	{
		int tabCount = tabbedPane.getTabCount();
		for (FollowAppAction a : actions.values())
		{
			if (tabCount <= 1 && a.getContext() == ActionContext.MULTI_FILE)
				a.setEnabled(false);
			else if (tabCount == 0 && a.getContext() == ActionContext.SINGLE_FILE)
				a.setEnabled(false);
			else
				a.setEnabled(true);
		}
	}

	/**
	 * Warning: This method should be called only from the event dispatching
	 * thread.
	 * 
	 * @param cursorType
	 *            may be Cursor.DEFAULT_CURSOR or Cursor.WAIT_CURSOR
	 */
	public void setCursor(int cursorType)
	{
		if (cursorType == currentCursor)
		{
			return;
		}
		switch (cursorType)
		{
		case Cursor.DEFAULT_CURSOR:
			if (defaultCursor == null)
			{
				defaultCursor = Cursor.getDefaultCursor();
			}
			frame.setCursor(defaultCursor);
			break;

		case Cursor.WAIT_CURSOR:
			if (waitCursor == null)
			{
				waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
			}
			frame.setCursor(waitCursor);
			break;

		default:
			throw new IllegalArgumentException(
			        "Supported cursors are Cursor.DEFAULT_CURSOR and Cursor.WAIT_CURSOR");
		}
		currentCursor = cursorType;
	}

	// Lazy initializer for the right-click listener which invokes a popup menu
	private MouseListener getRightClickListener()
	{
		if (rightClickListener == null)
		{
			rightClickListener = new MouseAdapter()
			{
				public void mouseReleased(MouseEvent e)
				{
					if (SwingUtilities.isRightMouseButton(e))
					{
						Component source = e.getComponent();
						popupMenu.show(source, e.getX(), e.getY());
					}
				}
			};
		}
		return rightClickListener;
	}

	public void enableDragAndDrop(Component c)
	{
		// Invoking this constructor automatically sets the component's drop
		// target
		new DropTarget(c, new DndFileOpener(this));
	}

	public void disableDragAndDrop(Component c)
	{
		c.setDropTarget(null);
	}

	public FileFollowingPane getSelectedFileFollowingPane()
	{
		return (FileFollowingPane) tabbedPane.getSelectedComponent();
	}

	public List<FileFollowingPane> getAllFileFollowingPanes()
	{
		int tabCount = tabbedPane.getTabCount();
		List<FileFollowingPane> allFileFollowingPanes = new ArrayList<FileFollowingPane>();
		for (int i = 0; i < tabCount; i++)
		{
			allFileFollowingPanes.add((FileFollowingPane) tabbedPane.getComponentAt(i));
		}
		return allFileFollowingPanes;
	}

	public FollowAppAttributes getAttributes()
	{
		return attributes;
	}

	public Map<File, FileFollowingPane> getFileToFollowingPaneMap()
	{
		return fileToFollowingPaneMap;
	}

	public JFrame getFrame()
	{
		return frame;
	}

	public static FollowApp getInstance()
	{
		return instance;
	}

	public SystemInterface getSystemInterface()
	{
		return systemInterface;
	}

	public void setSystemInterface(SystemInterface systemInterface)
	{
		this.systemInterface = systemInterface;
	}

	public JTabbedPane getTabbedPane()
	{
		return tabbedPane;
	}

	public static void centerWindowInScreen(Window window)
	{
		Dimension screenSize = window.getToolkit().getScreenSize();
		Dimension windowSize = window.getPreferredSize();
		window.setLocation((int) (screenSize.getWidth() / 2 - windowSize.getWidth() / 2),
		        (int) (screenSize.getHeight() / 2 - windowSize.getHeight() / 2));
	}

	/**
	 * Invoke this method to start the Follow application. If any command-line
	 * arguments are passed in, they are assume to be filenames and are opened
	 * in the Follow application
	 * 
	 * @param args
	 *            files to be opened
	 */
	public static void main(String[] args)
	{
		try
		{
			ArrayList<String> fileNames = new ArrayList<String>();
			File propFile = null;
			for (int i = 0; i < args.length; i++)
			{
				if (args[i].startsWith("-"))
				{
					if ("-propFile".equalsIgnoreCase(args[i]))
					{
						propFile = new File(args[++i]);
					}
				}
				else
				{
					fileNames.add(args[i]);
				}
			}
			instance = new FollowApp(fileNames, propFile);
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					// ensure all widgets inited before opening files
					instance.show();
					instance.startupStatus.markDone(instance.startupStatus.CREATE_WIDGETS);
				}
			});
			instance.startupStatus.dispose();
			// commented code below so that windows follow based on setting in
			// preferences which is set on the pane when the file is opened
			// for (int i=0; i < instance_.tabbedPane_.getTabCount(); i++) {
			// ((FileFollowingPane)instance_.tabbedPane_.getComponentAt(i)).startFollowing();
			// }
		}
		catch (Throwable t)
		{
			LOG.log(Level.SEVERE, "Unhandled exception", t);
			System.exit(-1);
		}
	}

	private class RecentFileListener implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (recentFilesMenu != null)
			{
				recentFilesMenu.removeAll();
				List<File> recentFiles = attributes.getRecentFiles();
				// descend down the list to order files by last opened
				for (int i = recentFiles.size() - 1; i >= 0; i--)
				{
					// have to use FollowApp.this because 'this' is now the
					// context of
					// the inner class
					recentFilesMenu.add(new Open(FollowApp.this, recentFiles.get(i)));
				}
			}
		}
	}
}