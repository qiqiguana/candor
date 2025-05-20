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

    /**
     * Gets an image icon from the resource path.
     *
     * @param clazz The class to use as an entry point to the resource path. Image
     *            path should be relative to this class.
     * @param iconNameKey The resource key name where the image is defined.
     * @return An image icon based on the URL generated from the value of
     *         iconNameKey. null if no URL can be found.
     */
    public static ImageIcon getIcon(Class<?> clazz, String iconNameKey) {
        String filename = getResourceString(iconNameKey);
        URL url = clazz.getResource(filename);
        LOG.finer("Class: " + clazz + ", iconNameKey: " + iconNameKey);
        LOG.finer("filename: " + filename);
        LOG.finer("url: " + url);
        ImageIcon icon = null;
        if (url != null) {
            icon = new ImageIcon(url);
            LOG.finer("errored: " + (java.awt.MediaTracker.ERRORED == icon.getImageLoadStatus()));
        }
        return icon;
    }
}
