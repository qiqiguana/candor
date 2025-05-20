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
public class FollowApp {

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

    private static ResourceBundle resources = ResourceBundle.getBundle("ghm.follow.FollowAppResourceBundle");

    private JFrame frame;

    // We should remove this hack once JDK 1.4 gets wide adoption on Solaris.
    static {
    }

    /**
     * @param fileNames
     *            names of files to be opened
     */
    FollowApp(List<String> fileNames) throws IOException, InterruptedException, InvocationTargetException {
    }

    FollowApp(List<String> filenames, File propertyFile) throws IOException, InterruptedException, InvocationTargetException {
    }

    /**
     * Close the current tab
     */
    public void closeFile();

    /**
     * Get a string from the resource bundle. Convenience method to shorten and
     * centralize this common call
     *
     * @param key
     * @return The value of key in the resource bundle. null if the key is not
     *         found.
     */
    public static String getResourceString(String key);

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
    public static ImageIcon getIcon(Class<?> clazz, String iconNameKey);

    /**
     * Loads the actions used in the application
     *
     * @throws IOException
     */
    private void loadActions() throws IOException;

    /**
     * @param jMenuBar
     */
    private void initFrame(JMenuBar jMenuBar);

    public void show();

    public FollowAppAction getAction(String name);

    /**
     * Get all actions associated to the application
     *
     * @return
     */
    public HashMap<String, FollowAppAction> getActions();

    /**
     * Set an action to the action map of the application.
     *
     * @param name
     *            The key to set the action to.
     * @param action
     *            The action to create an association for.
     */
    public void putAction(String name, FollowAppAction action);

    public void openFile(File file) throws FileNotFoundException;

    /**
     * Warning: This method should be called only from (1) the FollowApp
     * initializer (before any components are realized) or (2) from the event
     * dispatching thread.
     */
    void openFile(File file, boolean startFollowing) throws FileNotFoundException;

    private void updateActions();

    /**
     * Warning: This method should be called only from the event dispatching
     * thread.
     *
     * @param cursorType
     *            may be Cursor.DEFAULT_CURSOR or Cursor.WAIT_CURSOR
     */
    public void setCursor(int cursorType);

    // Lazy initializer for the right-click listener which invokes a popup menu
    private MouseListener getRightClickListener();

    public void enableDragAndDrop(Component c);

    public void disableDragAndDrop(Component c);

    public FileFollowingPane getSelectedFileFollowingPane();

    public List<FileFollowingPane> getAllFileFollowingPanes();

    public FollowAppAttributes getAttributes();

    public Map<File, FileFollowingPane> getFileToFollowingPaneMap();

    public JFrame getFrame();

    public static FollowApp getInstance();

    public SystemInterface getSystemInterface();

    public void setSystemInterface(SystemInterface systemInterface);

    public JTabbedPane getTabbedPane();

    public static void centerWindowInScreen(Window window);

    /**
     * Invoke this method to start the Follow application. If any command-line
     * arguments are passed in, they are assume to be filenames and are opened
     * in the Follow application
     *
     * @param args
     *            files to be opened
     */
    public static void main(String[] args);

    private class RecentFileListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            if (recentFilesMenu != null) {
                recentFilesMenu.removeAll();
                List<File> recentFiles = attributes.getRecentFiles();
                // descend down the list to order files by last opened
                for (int i = recentFiles.size() - 1; i >= 0; i--) {
                    // have to use FollowApp.this because 'this' is now the
                    // context of
                    // the inner class
                    recentFilesMenu.add(new Open(FollowApp.this, recentFiles.get(i)));
                }
            }
        }
    }
}
