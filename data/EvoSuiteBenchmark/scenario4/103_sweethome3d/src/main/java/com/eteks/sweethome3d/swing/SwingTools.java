package com.eteks.sweethome3d.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;
import com.eteks.sweethome3d.model.TextureImage;
import com.eteks.sweethome3d.model.UserPreferences;
import com.eteks.sweethome3d.tools.OperatingSystem;

/**
 * Gathers some useful tools for Swing.
 *
 * @author Emmanuel Puybaret
 */
public class SwingTools {

    // Borders for focused views
    private static Border unfocusedViewBorder;

    private static Border focusedViewBorder;

    private SwingTools() {
    }

    /**
     * Updates the border of <code>component</code> with an empty border
     * changed to a colored border when it will gain focus.
     * If the <code>component</code> component is the child of a <code>JViewPort</code>
     * instance this border will be installed on its scroll pane parent.
     */
    public static void installFocusBorder(JComponent component);

    /**
     * Updates the Swing resource bundles in use from the current Locale and class loader.
     */
    public static void updateSwingResourceLanguage();

    /**
     * Updates the Swing resource bundles in use from the current Locale and the class loaders of preferences.
     */
    public static void updateSwingResourceLanguage(UserPreferences preferences);

    /**
     * Updates the Swing resource bundles in use from the current Locale and class loaders.
     */
    private static void updateSwingResourceLanguage(List<ClassLoader> classLoaders);

    /**
     * Updates a Swing resource bundle in use from the current Locale.
     */
    private static void updateSwingResourceBundle(String swingResource, List<ClassLoader> classLoaders);

    /**
     * Returns a localized text for menus items and labels depending on the system.
     */
    public static String getLocalizedLabelText(UserPreferences preferences, Class<?> resourceClass, String resourceKey, Object... resourceParameters);

    /**
     * Adds focus and mouse listeners to the given <code>textComponent</code> that will
     * select all its text when it gains focus by transfer.
     */
    public static void addAutoSelectionOnFocusGain(final JTextComponent textComponent);

    /**
     * Forces radio buttons to be deselected even if they belong to a button group.
     */
    public static void deselectAllRadioButtons(JRadioButton... radioButtons);

    /**
     * Displays <code>messageComponent</code> in a modal dialog box, giving focus to one of its components.
     */
    public static int showConfirmDialog(JComponent parentComponent, JComponent messageComponent, String title, final JComponent focusedComponent);

    /**
     * Displays <code>messageComponent</code> in a modal dialog box, giving focus to one of its components.
     */
    public static void showMessageDialog(JComponent parentComponent, JComponent messageComponent, String title, int messageType, final JComponent focusedComponent);

    private static Map<TextureImage, BufferedImage> patternImages;

    /**
     * Returns the image matching a given pattern.
     */
    public static BufferedImage getPatternImage(TextureImage pattern, Color backgroundColor, Color foregroundColor);

    /**
     * Returns the border of a component where a user may drop objects.
     */
    public static Border getDropableComponentBorder();

    /**
     * Displays the image referenced by <code>imageUrl</code> in an AWT window
     * disposed once an other AWT frame is created.
     * If the <code>imageUrl</code> is incorrect, nothing happens.
     */
    public static void showSplashScreenWindow(URL imageUrl);

    /**
     * Returns a new panel with a border and the given <code>title</code>
     */
    public static JPanel createTitledPanel(String title);

    /**
     * Returns a scroll pane containing the given <code>component</code>
     * that always displays scroll bars under Mac OS X.
     */
    public static JScrollPane createScrollPane(JComponent component);

    /**
     * Adds a listener that will update the given popup menu to hide disabled menu items.
     */
    public static void hideDisabledMenuItems(JPopupMenu popupMenu);

    /**
     * A popup menu listener that displays only enabled menu items.
     */
    private static class MenuItemsVisibilityListener implements PopupMenuListener {

        public void popupMenuWillBecomeVisible(PopupMenuEvent ev) {
            JPopupMenu popupMenu = (JPopupMenu) ev.getSource();
            hideDisabledMenuItems(popupMenu);
            // Ensure at least one item is visible
            boolean allItemsInvisible = true;
            for (int i = 0; i < popupMenu.getComponentCount(); i++) {
                if (popupMenu.getComponent(i).isVisible()) {
                    allItemsInvisible = false;
                    break;
                }
            }
            if (allItemsInvisible) {
                popupMenu.getComponent(0).setVisible(true);
            }
        }

        /**
         * Makes useless menu items invisible.
         */
        private void hideDisabledMenuItems(JPopupMenu popupMenu) {
            for (int i = 0; i < popupMenu.getComponentCount(); i++) {
                Component component = popupMenu.getComponent(i);
                if (component instanceof JMenu) {
                    boolean containsEnabledItems = containsEnabledItems((JMenu) component);
                    component.setVisible(containsEnabledItems);
                    if (containsEnabledItems) {
                        hideDisabledMenuItems(((JMenu) component).getPopupMenu());
                    }
                } else if (component instanceof JMenuItem) {
                    component.setVisible(component.isEnabled());
                }
            }
            hideUselessSeparators(popupMenu);
        }

        /**
         * Makes useless separators invisible.
         */
        private void hideUselessSeparators(JPopupMenu popupMenu) {
            boolean allMenuItemsInvisible = true;
            int lastVisibleSeparatorIndex = -1;
            for (int i = 0; i < popupMenu.getComponentCount(); i++) {
                Component component = popupMenu.getComponent(i);
                if (allMenuItemsInvisible && (component instanceof JMenuItem)) {
                    if (component.isVisible()) {
                        allMenuItemsInvisible = false;
                    }
                } else if (component instanceof JSeparator) {
                    component.setVisible(!allMenuItemsInvisible);
                    if (!allMenuItemsInvisible) {
                        lastVisibleSeparatorIndex = i;
                    }
                    allMenuItemsInvisible = true;
                }
            }
            if (lastVisibleSeparatorIndex != -1 && allMenuItemsInvisible) {
                // Check if last separator is the first visible component
                boolean allComponentsBeforeLastVisibleSeparatorInvisible = true;
                for (int i = lastVisibleSeparatorIndex - 1; i >= 0; i--) {
                    if (popupMenu.getComponent(i).isVisible()) {
                        allComponentsBeforeLastVisibleSeparatorInvisible = false;
                        break;
                    }
                }
                boolean allComponentsAfterLastVisibleSeparatorInvisible = true;
                for (int i = lastVisibleSeparatorIndex; i < popupMenu.getComponentCount(); i++) {
                    if (popupMenu.getComponent(i).isVisible()) {
                        allComponentsBeforeLastVisibleSeparatorInvisible = false;
                        break;
                    }
                }
                popupMenu.getComponent(lastVisibleSeparatorIndex).setVisible(!allComponentsBeforeLastVisibleSeparatorInvisible && !allComponentsAfterLastVisibleSeparatorInvisible);
            }
        }

        /**
         * Returns <code>true</code> if the given <code>menu</code> contains
         * at least one enabled menu item.
         */
        private boolean containsEnabledItems(JMenu menu) {
            boolean menuContainsEnabledItems = false;
            for (int i = 0; i < menu.getMenuComponentCount() && !menuContainsEnabledItems; i++) {
                Component component = menu.getMenuComponent(i);
                if (component instanceof JMenu) {
                    menuContainsEnabledItems = containsEnabledItems((JMenu) component);
                } else if (component instanceof JMenuItem) {
                    menuContainsEnabledItems = component.isEnabled();
                }
            }
            return menuContainsEnabledItems;
        }

        public void popupMenuCanceled(PopupMenuEvent ev) {
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent ev) {
        }
    }

    /**
     * Attempts to display the given <code>url</code> in a browser and returns <code>true</code>
     * if it was done successfully.
     */
    public static boolean showDocumentInBrowser(URL url);

    /**
     * Separated static class to be able to exclude JNLP library from classpath.
     */
    private static class BrowserSupport {

        public static boolean showDocumentInBrowser(URL url) {
            try {
                // Lookup the javax.jnlp.BasicService object
                BasicService basicService = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
                // Ignore the basic service, if it doesn't support web browser
                if (basicService.isWebBrowserSupported()) {
                    return basicService.showDocument(url);
                }
            } catch (UnavailableServiceException ex) {
                // Too bad : service is unavailable
            } catch (LinkageError ex) {
                // JNLP classes not available in classpath
                System.err.println("Can't show document in browser. JNLP classes not available in classpath.");
            }
            return false;
        }
    }

    /**
     * Returns the children of a component of the given class.
     */
    public static <T extends JComponent> List<T> findChildren(JComponent parent, Class<T> childrenClass);

    private static <T extends JComponent> void findChildren(JComponent parent, Class<T> childrenClass, List<T> children);

    /**
     * Returns <code>true</code> if the given rectangle is fully visible at screen.
     */
    public static boolean isRectangleVisibleAtScreen(Rectangle rectangle);
}
