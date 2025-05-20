package com.eteks.sweethome3d.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FocusTraversalPolicy;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.text.JTextComponent;
import com.eteks.sweethome3d.j3d.Ground3D;
import com.eteks.sweethome3d.j3d.HomePieceOfFurniture3D;
import com.eteks.sweethome3d.j3d.OBJWriter;
import com.eteks.sweethome3d.j3d.Room3D;
import com.eteks.sweethome3d.j3d.Wall3D;
import com.eteks.sweethome3d.model.BackgroundImage;
import com.eteks.sweethome3d.model.Camera;
import com.eteks.sweethome3d.model.CatalogPieceOfFurniture;
import com.eteks.sweethome3d.model.CollectionEvent;
import com.eteks.sweethome3d.model.CollectionListener;
import com.eteks.sweethome3d.model.Compass;
import com.eteks.sweethome3d.model.Content;
import com.eteks.sweethome3d.model.DimensionLine;
import com.eteks.sweethome3d.model.Elevatable;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomeEnvironment;
import com.eteks.sweethome3d.model.HomeFurnitureGroup;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.InterruptedRecorderException;
import com.eteks.sweethome3d.model.Label;
import com.eteks.sweethome3d.model.Level;
import com.eteks.sweethome3d.model.RecorderException;
import com.eteks.sweethome3d.model.Room;
import com.eteks.sweethome3d.model.Selectable;
import com.eteks.sweethome3d.model.SelectionEvent;
import com.eteks.sweethome3d.model.SelectionListener;
import com.eteks.sweethome3d.model.TextStyle;
import com.eteks.sweethome3d.model.UserPreferences;
import com.eteks.sweethome3d.model.Wall;
import com.eteks.sweethome3d.plugin.HomePluginController;
import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.tools.OperatingSystem;
import com.eteks.sweethome3d.viewcontroller.ContentManager;
import com.eteks.sweethome3d.viewcontroller.FurnitureController;
import com.eteks.sweethome3d.viewcontroller.HomeController;
import com.eteks.sweethome3d.viewcontroller.HomeController3D;
import com.eteks.sweethome3d.viewcontroller.HomeView;
import com.eteks.sweethome3d.viewcontroller.PlanController;
import com.eteks.sweethome3d.viewcontroller.PlanController.Mode;
import com.eteks.sweethome3d.viewcontroller.PlanView;
import com.eteks.sweethome3d.viewcontroller.View;

/**
 * The MVC view that edits a home.
 *
 * @author Emmanuel Puybaret
 */
public class HomePane extends JRootPane implements HomeView {

    private enum MenuActionType {

        FILE_MENU,
        EDIT_MENU,
        FURNITURE_MENU,
        PLAN_MENU,
        VIEW_3D_MENU,
        HELP_MENU,
        OPEN_RECENT_HOME_MENU,
        ALIGN_OR_DISTRIBUTE_MENU,
        SORT_HOME_FURNITURE_MENU,
        DISPLAY_HOME_FURNITURE_PROPERTY_MENU,
        MODIFY_TEXT_STYLE,
        GO_TO_POINT_OF_VIEW,
        SELECT_OBJECT_MENU
    }

    private static final String MAIN_PANE_DIVIDER_LOCATION_VISUAL_PROPERTY = "com.eteks.sweethome3d.SweetHome3D.MainPaneDividerLocation";

    private static final String CATALOG_PANE_DIVIDER_LOCATION_VISUAL_PROPERTY = "com.eteks.sweethome3d.SweetHome3D.CatalogPaneDividerLocation";

    private static final String PLAN_PANE_DIVIDER_LOCATION_VISUAL_PROPERTY = "com.eteks.sweethome3d.SweetHome3D.PlanPaneDividerLocation";

    private static final String PLAN_VIEWPORT_X_VISUAL_PROPERTY = "com.eteks.sweethome3d.SweetHome3D.PlanViewportX";

    private static final String PLAN_VIEWPORT_Y_VISUAL_PROPERTY = "com.eteks.sweethome3d.SweetHome3D.PlanViewportY";

    private static final String FURNITURE_VIEWPORT_Y_VISUAL_PROPERTY = "com.eteks.sweethome3d.SweetHome3D.FurnitureViewportY";

    private static final String DETACHED_VIEW_VISUAL_PROPERTY = ".detachedView";

    private static final String DETACHED_VIEW_DIVIDER_LOCATION_VISUAL_PROPERTY = ".detachedViewDividerLocation";

    private static final String DETACHED_VIEW_X_VISUAL_PROPERTY = ".detachedViewX";

    private static final String DETACHED_VIEW_Y_VISUAL_PROPERTY = ".detachedViewY";

    private static final String DETACHED_VIEW_WIDTH_VISUAL_PROPERTY = ".detachedViewWidth";

    private static final String DETACHED_VIEW_HEIGHT_VISUAL_PROPERTY = ".detachedViewHeight";

    private static final int DEFAULT_SMALL_ICON_HEIGHT = 16;

    private final Home home;

    private final UserPreferences preferences;

    private final HomeController controller;

    private JComponent lastFocusedComponent;

    private PlanController.Mode previousPlanControllerMode;

    private TransferHandler catalogTransferHandler;

    private TransferHandler furnitureTransferHandler;

    private TransferHandler planTransferHandler;

    private boolean transferHandlerEnabled;

    private MouseInputAdapter furnitureCatalogDragAndDropListener;

    private boolean clipboardEmpty = true;

    private ActionMap menuActionMap;

    private List<Action> pluginActions;

    /**
     * Creates home view associated with its controller.
     */
    public HomePane(Home home, UserPreferences preferences, final HomeController controller) {
    }

    /**
     * Create the actions map of this component.
     */
    private void createActions(Home home, UserPreferences preferences, final HomeController controller);

    /**
     * Returns a new <code>ControllerAction</code> object that calls on <code>controller</code> a given
     * <code>method</code> with its <code>parameters</code>. This action is added to the action map of this component.
     */
    private Action createAction(ActionType actionType, UserPreferences preferences, Object controller, String method, Object... parameters);

    /**
     * Returns a new <code>ControllerAction</code> object associated with a <code>ToggleButtonModel</code> instance
     * set as selected or not.
     */
    private Action createToggleAction(ActionType actionType, boolean selected, ButtonGroup group, UserPreferences preferences, Object controller, String method, Object... parameters);

    /**
     * Creates a <code>ReourceAction</code> object that calls
     * <code>actionPerfomed</code> method on a given
     * existing <code>clipboardAction</code> with a source equal to focused component.
     */
    private void createClipboardAction(ActionType actionType, UserPreferences preferences, final Action clipboardAction, final boolean copyAction);

    /**
     * Create the actions map used to create menus of this component.
     */
    private void createMenuActions(UserPreferences preferences, HomeController controller);

    /**
     * Creates a <code>ResourceAction</code> object stored in menu action map.
     */
    private void createMenuAction(UserPreferences preferences, MenuActionType action);

    /**
     * Creates the Swing actions matching each actions available in <code>plugins</code>.
     */
    private void createPluginActions(List<Plugin> plugins);

    /**
     * Creates components transfer handlers.
     */
    private void createTransferHandlers(Home home, HomeController controller);

    /**
     * Adds a property change listener to <code>home</code> to update
     * View from top and View from observer toggle models according to used camera.
     */
    private void addHomeListener(final Home home);

    /**
     * Changes the selection of the toggle model matching the given action.
     */
    private void setToggleButtonModelSelected(ActionType actionType, boolean selected);

    /**
     * Adds listener to <code>home</code> to update
     * Display all levels and Display selected level toggle models
     * according their visibility.
     */
    private void addLevelVisibilityListener(final Home home);

    /**
     * Adds a property change listener to <code>preferences</code> to update
     * actions when preferred language changes.
     */
    private void addLanguageListener(UserPreferences preferences);

    /**
     * Preferences property listener bound to this component with a weak reference to avoid
     * strong link between preferences and this component.
     */
    private static class LanguageChangeListener implements PropertyChangeListener {

        private WeakReference<HomePane> homePane;

        public LanguageChangeListener(HomePane homePane) {
            this.homePane = new WeakReference<HomePane>(homePane);
        }

        public void propertyChange(PropertyChangeEvent ev) {
            // If home pane was garbage collected, remove this listener from preferences
            HomePane homePane = this.homePane.get();
            if (homePane == null) {
                ((UserPreferences) ev.getSource()).removePropertyChangeListener(UserPreferences.Property.LANGUAGE, this);
            } else {
                SwingTools.updateSwingResourceLanguage((UserPreferences) ev.getSource());
            }
        }
    }

    /**
     * Adds a property change listener to <code>planController</code> to update
     * Select and Create walls toggle models according to current mode.
     */
    private void addPlanControllerListener(final PlanController planController);

    /**
     * Adds a focus change listener to report to controller focus changes.
     */
    private void addFocusListener();

    /**
     * Property listener bound to this component with a weak reference to avoid
     * strong link between KeyboardFocusManager and this component.
     */
    private static class FocusCycleRootChangeListener implements PropertyChangeListener {

        private WeakReference<HomePane> homePane;

        private PropertyChangeListener focusChangeListener;

        public FocusCycleRootChangeListener(HomePane homePane) {
            this.homePane = new WeakReference<HomePane>(homePane);
        }

        public void propertyChange(PropertyChangeEvent ev) {
            // If home pane was garbage collected, remove this listener from KeyboardFocusManager
            final HomePane homePane = this.homePane.get();
            if (homePane == null) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener("currentFocusCycleRoot", this);
            } else {
                if (SwingUtilities.isDescendingFrom(homePane, (Component) ev.getOldValue())) {
                    this.focusChangeListener = null;
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner", this.focusChangeListener);
                } else if (SwingUtilities.isDescendingFrom(homePane, (Component) ev.getNewValue())) {
                    this.focusChangeListener = new PropertyChangeListener() {

                        public void propertyChange(PropertyChangeEvent ev) {
                            if (homePane.lastFocusedComponent != null) {
                                // Update component which lost focused
                                JComponent lostFocusedComponent = homePane.lastFocusedComponent;
                                if (SwingUtilities.isDescendingFrom(lostFocusedComponent, SwingUtilities.getWindowAncestor(homePane))) {
                                    lostFocusedComponent.removeKeyListener(homePane.specialKeysListener);
                                    // Restore previous plan mode if plan view had focus and window is deactivated
                                    if (homePane.previousPlanControllerMode != null && (lostFocusedComponent == homePane.controller.getPlanController().getView() || ev.getNewValue() == null)) {
                                        homePane.controller.getPlanController().setMode(homePane.previousPlanControllerMode);
                                        homePane.previousPlanControllerMode = null;
                                    }
                                }
                            }
                            if (ev.getNewValue() != null) {
                                // Retrieve component which gained focused
                                Component gainedFocusedComponent = (Component) ev.getNewValue();
                                if (SwingUtilities.isDescendingFrom(gainedFocusedComponent, SwingUtilities.getWindowAncestor(homePane)) && gainedFocusedComponent instanceof JComponent) {
                                    View[] focusableViews = { homePane.controller.getFurnitureCatalogController().getView(), homePane.controller.getFurnitureController().getView(), homePane.controller.getPlanController().getView(), homePane.controller.getHomeController3D().getView() };
                                    // Notify controller that active view changed
                                    for (View view : focusableViews) {
                                        if (view != null && SwingUtilities.isDescendingFrom(gainedFocusedComponent, (JComponent) view)) {
                                            homePane.controller.focusedViewChanged(view);
                                            gainedFocusedComponent.addKeyListener(homePane.specialKeysListener);
                                            // Update the component used by clipboard actions
                                            homePane.lastFocusedComponent = (JComponent) gainedFocusedComponent;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    };
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner", this.focusChangeListener);
                }
            }
        }
    }

    private KeyListener specialKeysListener = new KeyAdapter() {

        public void keyPressed(KeyEvent ev) {
            // Temporarily toggle plan controller mode to panning mode when space bar is pressed
            PlanController planController = controller.getPlanController();
            if (ev.getKeyCode() == KeyEvent.VK_SPACE && (ev.getModifiers() & (KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK | KeyEvent.META_MASK)) == 0 && getActionMap().get(ActionType.PAN).getValue(Action.NAME) != null && planController.getMode() != PlanController.Mode.PANNING && !planController.isModificationState() && SwingUtilities.isDescendingFrom(lastFocusedComponent, HomePane.this) && !isSpaceUsedByComponent(lastFocusedComponent)) {
                previousPlanControllerMode = planController.getMode();
                planController.setMode(PlanController.Mode.PANNING);
                ev.consume();
            }
        }

        private boolean isSpaceUsedByComponent(JComponent component) {
            return component instanceof JTextComponent || component instanceof JComboBox;
        }

        public void keyReleased(KeyEvent ev) {
            if (ev.getKeyCode() == KeyEvent.VK_SPACE && previousPlanControllerMode != null) {
                controller.getPlanController().setMode(previousPlanControllerMode);
                previousPlanControllerMode = null;
                ev.consume();
            }
        }

        @Override
        public void keyTyped(KeyEvent ev) {
            // This listener manages accelerator keys that may require the use of shift key
            // depending on keyboard layout (like + - or ?)
            ActionMap actionMap = getActionMap();
            Action[] specialKeyActions = { actionMap.get(ActionType.ZOOM_IN), actionMap.get(ActionType.ZOOM_OUT), actionMap.get(ActionType.INCREASE_TEXT_SIZE), actionMap.get(ActionType.DECREASE_TEXT_SIZE), actionMap.get(ActionType.HELP) };
            int modifiersMask = KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK | KeyEvent.META_MASK;
            for (Action specialKeyAction : specialKeyActions) {
                KeyStroke actionKeyStroke = (KeyStroke) specialKeyAction.getValue(Action.ACCELERATOR_KEY);
                if (ev.getKeyChar() == actionKeyStroke.getKeyChar() && (ev.getModifiers() & modifiersMask) == (actionKeyStroke.getModifiers() & modifiersMask) && specialKeyAction.isEnabled()) {
                    specialKeyAction.actionPerformed(new ActionEvent(HomePane.this, ActionEvent.ACTION_PERFORMED, (String) specialKeyAction.getValue(Action.ACTION_COMMAND_KEY)));
                    ev.consume();
                }
            }
        }
    };

    /**
     * Sets a focus traversal policy that ignores invisible split pane components.
     */
    private void updateFocusTraversalPolicy();

    /**
     * Returns <code>true</code> if the top or the bottom component of the <code>splitPane</code>
     * is a parent of the given child component and is too small enough to show it.
     */
    private boolean isChildComponentInvisible(JSplitPane splitPane, Component childComponent);

    /**
     * Returns the menu bar displayed in this pane.
     */
    private JMenuBar createMenuBar(final Home home, UserPreferences preferences, final HomeController controller);

    /**
     * Adds the given action to <code>menu</code>.
     */
    private void addActionToMenu(ActionType actionType, JMenu menu);

    /**
     * Adds the given action to <code>menu</code>.
     */
    private void addActionToMenu(ActionType actionType, boolean popup, JMenu menu);

    /**
     * Adds to <code>menu</code> the menu item matching the given <code>actionType</code>.
     */
    private void addToggleActionToMenu(ActionType actionType, boolean radioButton, JMenu menu);

    /**
     * Adds to <code>menu</code> the menu item matching the given <code>actionType</code>.
     */
    private void addToggleActionToMenu(ActionType actionType, boolean popup, boolean radioButton, JMenu menu);

    /**
     * Creates a menu item for a toggle action.
     */
    private JMenuItem createToggleMenuItem(Action action, boolean popup, boolean radioButton);

    /**
     * Adds the given action to <code>menu</code>.
     */
    private void addActionToPopupMenu(ActionType actionType, JPopupMenu menu);

    /**
     * Adds to <code>menu</code> the menu item matching the given <code>actionType</code>
     * and returns <code>true</code> if it was added.
     */
    private void addToggleActionToPopupMenu(ActionType actionType, boolean radioButton, JPopupMenu menu);

    /**
     * Removes the useless separators and empty menus among children of component.
     */
    private void removeUselessSeparatorsAndEmptyMenus(JComponent component);

    /**
     * Returns align or distribute menu.
     */
    private JMenu createAlignOrDistributeMenu(final Home home, final UserPreferences preferences, boolean popup);

    /**
     * Returns furniture sort menu.
     */
    private JMenu createFurnitureSortMenu(final Home home, UserPreferences preferences);

    /**
     * Adds to <code>actions</code> the action matching <code>actionType</code>.
     */
    private void addActionToMap(ActionType actionType, Map<HomePieceOfFurniture.SortableProperty, Action> actions, HomePieceOfFurniture.SortableProperty key);

    /**
     * Returns furniture display property menu.
     */
    private JMenu createFurnitureDisplayPropertyMenu(final Home home, UserPreferences preferences);

    /**
     * Returns Lock / Unlock base plan menu item.
     */
    private JMenuItem createLockUnlockBasePlanMenuItem(final Home home, final boolean popup);

    /**
     * Returns the action active on Lock / Unlock base plan menu item.
     */
    private Action createLockUnlockBasePlanAction(Home home, boolean popup);

    /**
     * Returns Lock / Unlock base plan button.
     */
    private JComponent createLockUnlockBasePlanButton(final Home home);

    /**
     * Returns text style menu.
     */
    private JMenu createTextStyleMenu(final Home home, final UserPreferences preferences, boolean popup);

    /**
     * Creates a toggle button model that is selected when all the text of the
     * selected items in <code>home</code> use bold style.
     */
    private JToggleButton.ToggleButtonModel createBoldStyleToggleModel(final Home home, final UserPreferences preferences);

    /**
     * Creates a toggle button model that is selected when all the text of the
     * selected items in <code>home</code> use italic style.
     */
    private JToggleButton.ToggleButtonModel createItalicStyleToggleModel(final Home home, final UserPreferences preferences);

    /**
     * Returns Import / Modify background image menu item.
     */
    private JMenuItem createImportModifyBackgroundImageMenuItem(final Home home, final boolean popup);

    /**
     * Adds to home and levels the given listener to follow background image changes.
     */
    private void addBackgroundImageChangeListener(final Home home, final PropertyChangeListener listener);

    /**
     * Returns the action active on Import / Modify menu item.
     */
    private Action createImportModifyBackgroundImageAction(Home home, boolean popup);

    /**
     * Returns Hide / Show background image menu item.
     */
    private JMenuItem createHideShowBackgroundImageMenuItem(final Home home, final boolean popup);

    /**
     * Returns the action active on Hide / Show menu item.
     */
    private Action createHideShowBackgroundImageAction(Home home, boolean popup);

    /**
     * Returns Go to point of view menu.
     */
    private JMenu createGoToPointOfViewMenu(final Home home, UserPreferences preferences, final HomeController controller);

    /**
     * Updates Go to point of view menu items from the cameras stored in home.
     */
    private void updateGoToPointOfViewMenu(JMenu goToPointOfViewMenu, Home home, final HomeController controller);

    /**
     * Returns Attach / Detach menu item for the 3D view.
     */
    private JMenuItem createAttachDetach3DViewMenuItem(final HomeController controller, final boolean popup);

    /**
     * Returns the action Attach / Detach menu item.
     */
    private Action createAttachDetach3DViewAction(HomeController controller, boolean popup);

    /**
     * Updates <code>openRecentHomeMenu</code> from current recent homes in preferences.
     */
    protected void updateOpenRecentHomeMenu(JMenu openRecentHomeMenu, final HomeController controller);

    /**
     * Returns the tool bar displayed in this pane.
     */
    private JToolBar createToolBar(Home home);

    /**
     * Adds to tool bar the button matching the given <code>actionType</code>
     * and returns <code>true</code> if it was added.
     */
    private void addToggleActionToToolBar(ActionType actionType, JToolBar toolBar);

    /**
     * Adds to tool bar the button matching the given <code>actionType</code>.
     */
    private void addActionToToolBar(ActionType actionType, JToolBar toolBar);

    /**
     * Enables or disables the action matching <code>actionType</code>.
     */
    public void setEnabled(ActionType actionType, boolean enabled);

    /**
     * Sets the <code>NAME</code> and <code>SHORT_DESCRIPTION</code> properties value
     * of undo and redo actions. If a parameter is null,
     * the properties will be reset to their initial values.
     */
    public void setUndoRedoName(String undoText, String redoText);

    /**
     * Sets the <code>NAME</code> and <code>SHORT_DESCRIPTION</code> properties value
     * matching <code>actionType</code>. If <code>name</code> is null,
     * the properties will be reset to their initial values.
     */
    private void setNameAndShortDescription(ActionType actionType, String name);

    /**
     * Enables or disables transfer between components.
     */
    public void setTransferEnabled(boolean enabled);

    /**
     * Returns a mouse listener for catalog that acts as catalog view, furniture view and plan transfer handlers
     * for drag and drop operations.
     */
    private MouseInputAdapter createFurnitureCatalogMouseListener();

    /**
     * Returns the main pane with catalog tree, furniture table and plan pane.
     */
    private JComponent createMainPane(Home home, UserPreferences preferences, HomeController controller);

    /**
     * Configures <code>splitPane</code> divider location.
     * If <code>dividerLocationProperty</code> visual property exists in <code>home</code>,
     * its value will be used, otherwise the given resize weight will be used.
     */
    private void configureSplitPane(final JSplitPane splitPane, Home home, final String dividerLocationProperty, final double defaultResizeWeight, boolean showBorder, final HomeController controller);

    /**
     * Returns the catalog tree and furniture table pane.
     */
    private JComponent createCatalogFurniturePane(Home home, UserPreferences preferences, final HomeController controller);

    /**
     * Preferences property listener bound to this component with a weak reference to avoid
     * strong link between preferences and this component.
     */
    private static class FurnitureCatalogViewChangeListener implements PropertyChangeListener {

        private WeakReference<HomePane> homePane;

        private WeakReference<JComponent> furnitureCatalogView;

        public FurnitureCatalogViewChangeListener(HomePane homePane, JComponent furnitureCatalogView) {
            this.homePane = new WeakReference<HomePane>(homePane);
            this.furnitureCatalogView = new WeakReference<JComponent>(furnitureCatalogView);
        }

        public void propertyChange(PropertyChangeEvent ev) {
            // If home pane was garbage collected, remove this listener from preferences
            HomePane homePane = this.homePane.get();
            if (homePane == null) {
                ((UserPreferences) ev.getSource()).removePropertyChangeListener(UserPreferences.Property.FURNITURE_CATALOG_VIEWED_IN_TREE, this);
            } else {
                // Replace previous furniture catalog view by the new one
                JComponent oldFurnitureCatalogView = this.furnitureCatalogView.get();
                if (oldFurnitureCatalogView != null) {
                    boolean transferHandlerEnabled = homePane.transferHandlerEnabled;
                    homePane.setTransferEnabled(false);
                    JComponent newFurnitureCatalogView = (JComponent) homePane.controller.getFurnitureCatalogController().getView();
                    newFurnitureCatalogView.setComponentPopupMenu(oldFurnitureCatalogView.getComponentPopupMenu());
                    homePane.setTransferEnabled(transferHandlerEnabled);
                    JComponent splitPaneTopComponent = newFurnitureCatalogView;
                    if (newFurnitureCatalogView instanceof Scrollable) {
                        splitPaneTopComponent = SwingTools.createScrollPane(newFurnitureCatalogView);
                    } else {
                        splitPaneTopComponent = newFurnitureCatalogView;
                    }
                    ((JSplitPane) SwingUtilities.getAncestorOfClass(JSplitPane.class, oldFurnitureCatalogView)).setTopComponent(splitPaneTopComponent);
                    this.furnitureCatalogView = new WeakReference<JComponent>(newFurnitureCatalogView);
                }
            }
        }
    }

    /**
     * Returns the plan view and 3D view pane.
     */
    private JComponent createPlanView3DPane(final Home home, UserPreferences preferences, final HomeController controller);

    /**
     * Adds to the menu a listener that will update the menu items able to select
     * the selectable items in plan at the location where the menu will be triggered.
     */
    private void addSelectObjectMenuItems(final JMenu selectObjectMenu, final PlanController planController, final UserPreferences preferences);

    /**
     * Preferences property listener bound to this component with a weak reference to avoid
     * strong link between preferences and this component.
     */
    private static class RulersVisibilityChangeListener implements PropertyChangeListener {

        private WeakReference<HomePane> homePane;

        private WeakReference<JScrollPane> planScrollPane;

        private WeakReference<HomeController> controller;

        public RulersVisibilityChangeListener(HomePane homePane, JScrollPane planScrollPane, HomeController controller) {
            this.homePane = new WeakReference<HomePane>(homePane);
            this.planScrollPane = new WeakReference<JScrollPane>(planScrollPane);
            this.controller = new WeakReference<HomeController>(controller);
        }

        public void propertyChange(PropertyChangeEvent ev) {
            // If home pane was garbage collected, remove this listener from preferences
            HomePane homePane = this.homePane.get();
            JScrollPane planScrollPane = this.planScrollPane.get();
            HomeController controller = this.controller.get();
            if (homePane == null || planScrollPane == null || controller == null) {
                ((UserPreferences) ev.getSource()).removePropertyChangeListener(UserPreferences.Property.RULERS_VISIBLE, this);
            } else {
                homePane.setPlanRulersVisible(planScrollPane, controller, (Boolean) ev.getNewValue());
            }
        }
    }

    /**
     * Sets the rulers visible in plan view.
     */
    private void setPlanRulersVisible(JScrollPane planScrollPane, HomeController controller, boolean visible);

    /**
     * Adds to <code>view</code> a mouse listener that disables all menu items of
     * <code>menuBar</code> during a drag and drop operation in <code>view</code>.
     */
    private void disableMenuItemsDuringDragAndDrop(View view, final JMenuBar menuBar);

    /**
     * Detaches the given <code>view</code> from home view.
     */
    public void detachView(final View view);

    /**
     * Detaches a <code>view</code> at the given location and size.
     */
    private void detachView(final View view, int x, int y, int width, int height);

    /**
     * Attaches the given <code>view</code> to home view.
     */
    public void attachView(View view);

    /**
     * Returns among <code>parent</code> children the first child with the given name.
     */
    private Component findChild(Container parent, String childName);

    /**
     * Displays a content chooser open dialog to choose the name of a home.
     */
    public String showOpenDialog();

    /**
     * Displays a content chooser open dialog to choose a language library.
     */
    public String showImportLanguageLibraryDialog();

    /**
     * Displays a dialog that lets user choose whether he wants to overwrite
     * an existing language library or not.
     */
    public boolean confirmReplaceLanguageLibrary(String languageLibraryName);

    /**
     * Displays a content chooser open dialog to choose a furniture library.
     */
    public String showImportFurnitureLibraryDialog();

    /**
     * Displays a dialog that lets user choose whether he wants to overwrite
     * an existing furniture library or not.
     */
    public boolean confirmReplaceFurnitureLibrary(String furnitureLibraryName);

    /**
     * Displays a content chooser open dialog to choose a textures library.
     */
    public String showImportTexturesLibraryDialog();

    /**
     * Displays a dialog that lets user choose whether he wants to overwrite
     * an existing textures library or not.
     */
    public boolean confirmReplaceTexturesLibrary(String texturesLibraryName);

    /**
     * Displays a dialog that lets user choose whether he wants to overwrite
     * an existing plug-in or not.
     */
    public boolean confirmReplacePlugin(String pluginName);

    /**
     * Displays a content chooser save dialog to choose the name of a home.
     */
    public String showSaveDialog(String homeName);

    /**
     * Displays <code>message</code> in an error message box.
     */
    public void showError(String message);

    /**
     * Displays <code>message</code> in a message box.
     */
    public void showMessage(String message);

    /**
     * Displays the tip matching <code>actionTipKey</code> and
     * returns <code>true</code> if the user chose not to display again the tip.
     */
    public boolean showActionTipMessage(String actionTipKey);

    /**
     * Displays a dialog that lets user choose whether he wants to save
     * the current home or not.
     * @return {@link com.eteks.sweethome3d.viewcontroller.HomeView.SaveAnswer#SAVE}
     * if the user chose to save home,
     * {@link com.eteks.sweethome3d.viewcontroller.HomeView.SaveAnswer#DO_NOT_SAVE}
     * if he doesn't want to save home,
     * or {@link com.eteks.sweethome3d.viewcontroller.HomeView.SaveAnswer#CANCEL}
     * if he doesn't want to continue current operation.
     */
    public SaveAnswer confirmSave(String homeName);

    /**
     * Displays a dialog that let user choose whether he wants to save
     * a home that was created with a newer version of Sweet Home 3D.
     * @return <code>true</code> if user confirmed to save.
     */
    public boolean confirmSaveNewerHome(String homeName);

    /**
     * Displays a dialog that let user choose whether he wants to exit
     * application or not.
     * @return <code>true</code> if user confirmed to exit.
     */
    public boolean confirmExit();

    /**
     * Displays an about dialog.
     */
    public void showAboutDialog();

    /**
     * Shows a print dialog to print the home displayed by this pane.
     * @return a print task to execute or <code>null</code> if the user canceled print.
     *    The <code>call</code> method of the returned task may throw a
     *    {@link RecorderException RecorderException} exception if print failed
     *    or an {@link InterruptedRecorderException InterruptedRecorderException}
     *    exception if it was interrupted.
     */
    public Callable<Void> showPrintDialog();

    /**
     * Shows a content chooser save dialog to print a home in a PDF file.
     */
    public String showPrintToPDFDialog(String homeName);

    /**
     * Prints a home to a given PDF file. This method may be overridden
     * to write to another kind of output stream.
     */
    public void printToPDF(String pdfFile) throws RecorderException;

    /**
     * Shows a content chooser save dialog to export a home plan in a SVG file.
     */
    public String showExportToSVGDialog(String homeName);

    /**
     * Exports the plan objects to a given SVG file.
     */
    public void exportToSVG(String svgFile) throws RecorderException;

    /**
     * Shows a content chooser save dialog to export a 3D home in a OBJ file.
     */
    public String showExportToOBJDialog(String homeName);

    /**
     * Exports the objects of the 3D view to the given OBJ file.
     */
    public void exportToOBJ(String objFile) throws RecorderException;

    /**
     * Export to OBJ in a separate class to be able to run HomePane without Java 3D classes.
     */
    private static class OBJExporter {

        public static void exportHomeToFile(Home home, String objFile, String header) throws RecorderException {
            OBJWriter writer = null;
            boolean exportInterrupted = false;
            try {
                writer = new OBJWriter(objFile, header, -1);
                List<Selectable> emptySelection = Collections.emptyList();
                home.setSelectedItems(emptySelection);
                if (home.getWalls().size() > 0) {
                    // Create a not alive new ground to be able to explore its coordinates without setting capabilities
                    Rectangle2D homeBounds = getExportedHomeBounds(home);
                    Ground3D groundNode = new Ground3D(home, (float) homeBounds.getX(), (float) homeBounds.getY(), (float) homeBounds.getWidth(), (float) homeBounds.getHeight(), true);
                    writer.writeNode(groundNode, "ground");
                }
                // Write 3D walls
                int i = 0;
                for (Wall wall : home.getWalls()) {
                    // Create a not alive new wall to be able to explore its coordinates without setting capabilities
                    Wall3D wallNode = new Wall3D(wall, home, true, true);
                    writer.writeNode(wallNode, "wall_" + ++i);
                }
                // Write 3D furniture
                i = 0;
                for (HomePieceOfFurniture piece : home.getFurniture()) {
                    if (piece.isVisible()) {
                        // Create a not alive new piece to be able to explore its coordinates without setting capabilities
                        HomePieceOfFurniture3D pieceNode = new HomePieceOfFurniture3D(piece, home, true, true);
                        writer.writeNode(pieceNode);
                    }
                }
                // Write 3D rooms
                i = 0;
                for (Room room : home.getRooms()) {
                    // Create a not alive new room to be able to explore its coordinates without setting capabilities
                    Room3D roomNode = new Room3D(room, home, false, true, true);
                    writer.writeNode(roomNode, "room_" + ++i);
                }
            } catch (InterruptedIOException ex) {
                exportInterrupted = true;
                throw new InterruptedRecorderException("Export to " + objFile + " interrupted");
            } catch (IOException ex) {
                throw new RecorderException("Couldn't export to OBJ in " + objFile, ex);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                        // Delete the file if exporting is interrupted
                        if (exportInterrupted) {
                            new File(objFile).delete();
                        }
                    } catch (IOException ex) {
                        throw new RecorderException("Couldn't export to OBJ in " + objFile, ex);
                    }
                }
            }
        }

        /**
         * Returns <code>home</code> bounds.
         */
        private static Rectangle2D getExportedHomeBounds(Home home) {
            // Compute bounds that include walls and furniture
            Rectangle2D homeBounds = updateObjectsBounds(null, home.getWalls());
            for (HomePieceOfFurniture piece : getVisibleFurniture(home.getFurniture())) {
                for (float[] point : piece.getPoints()) {
                    if (homeBounds == null) {
                        homeBounds = new Rectangle2D.Float(point[0], point[1], 0, 0);
                    } else {
                        homeBounds.add(point[0], point[1]);
                    }
                }
            }
            return updateObjectsBounds(homeBounds, home.getRooms());
        }

        /**
         * Returns all the visible pieces in the given <code>furniture</code>.
         */
        private static List<HomePieceOfFurniture> getVisibleFurniture(List<HomePieceOfFurniture> furniture) {
            List<HomePieceOfFurniture> visibleFurniture = new ArrayList<HomePieceOfFurniture>(furniture.size());
            for (HomePieceOfFurniture piece : furniture) {
                if (piece.isVisible() && (piece.getLevel() == null || piece.getLevel().isVisible())) {
                    if (piece instanceof HomeFurnitureGroup) {
                        visibleFurniture.addAll(getVisibleFurniture(((HomeFurnitureGroup) piece).getFurniture()));
                    } else {
                        visibleFurniture.add(piece);
                    }
                }
            }
            return visibleFurniture;
        }

        /**
         * Updates <code>objectBounds</code> to include the bounds of <code>items</code>.
         */
        private static Rectangle2D updateObjectsBounds(Rectangle2D objectBounds, Collection<? extends Selectable> items) {
            for (Selectable item : items) {
                if (!(item instanceof Elevatable) || ((Elevatable) item).getLevel() == null || ((Elevatable) item).getLevel().isVisible()) {
                    for (float[] point : item.getPoints()) {
                        if (objectBounds == null) {
                            objectBounds = new Rectangle2D.Float(point[0], point[1], 0, 0);
                        } else {
                            objectBounds.add(point[0], point[1]);
                        }
                    }
                }
            }
            return objectBounds;
        }
    }

    /**
     * Displays a dialog that let user choose whether he wants to delete
     * the selected furniture from catalog or not.
     * @return <code>true</code> if user confirmed to delete.
     */
    public boolean confirmDeleteCatalogSelection();

    /**
     * Displays a dialog that lets the user choose a name for the current camera.
     * @return the chosen name or <code>null</code> if the user canceled.
     */
    public String showStoreCameraDialog(String cameraName);

    /**
     * Returns <code>true</code> if clipboard contains data that
     * components are able to handle.
     */
    public boolean isClipboardEmpty();

    /**
     * Execute <code>runnable</code> asynchronously in the thread
     * that manages toolkit events.
     */
    public void invokeLater(Runnable runnable);

    /**
     * A Swing action adapter to a plug-in action.
     */
    private class ActionAdapter implements Action {

        private PluginAction pluginAction;

        private SwingPropertyChangeSupport propertyChangeSupport;

        private ActionAdapter(PluginAction pluginAction) {
            this.pluginAction = pluginAction;
            this.propertyChangeSupport = new SwingPropertyChangeSupport(this);
            this.pluginAction.addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent ev) {
                    String propertyName = ev.getPropertyName();
                    Object oldValue = ev.getOldValue();
                    Object newValue = ev.getNewValue();
                    if (PluginAction.Property.ENABLED.name().equals(propertyName)) {
                        propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(), "enabled", oldValue, newValue));
                    } else {
                        // In case a property value changes, fire the new value decorated in subclasses
                        // unless new value is null (most Swing listeners don't check new value is null !)
                        if (newValue != null) {
                            if (PluginAction.Property.NAME.name().equals(propertyName)) {
                                propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(), Action.NAME, oldValue, newValue));
                            } else if (PluginAction.Property.SHORT_DESCRIPTION.name().equals(propertyName)) {
                                propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(), Action.SHORT_DESCRIPTION, oldValue, newValue));
                            } else if (PluginAction.Property.MNEMONIC.name().equals(propertyName)) {
                                propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(), Action.MNEMONIC_KEY, oldValue != null ? new Integer((Character) oldValue) : null, newValue));
                            } else if (PluginAction.Property.SMALL_ICON.name().equals(propertyName)) {
                                propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(), Action.SMALL_ICON, oldValue != null ? IconManager.getInstance().getIcon((Content) oldValue, DEFAULT_SMALL_ICON_HEIGHT, HomePane.this) : null, newValue));
                            } else {
                                propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(), propertyName, oldValue, newValue));
                            }
                        }
                    }
                }
            });
        }

        public void actionPerformed(ActionEvent ev) {
            this.pluginAction.execute();
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            this.propertyChangeSupport.addPropertyChangeListener(listener);
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
            this.propertyChangeSupport.removePropertyChangeListener(listener);
        }

        public Object getValue(String key) {
            if (NAME.equals(key)) {
                return this.pluginAction.getPropertyValue(PluginAction.Property.NAME);
            } else if (SHORT_DESCRIPTION.equals(key)) {
                return this.pluginAction.getPropertyValue(PluginAction.Property.SHORT_DESCRIPTION);
            } else if (SMALL_ICON.equals(key)) {
                Content smallIcon = (Content) this.pluginAction.getPropertyValue(PluginAction.Property.SMALL_ICON);
                return smallIcon != null ? IconManager.getInstance().getIcon(smallIcon, DEFAULT_SMALL_ICON_HEIGHT, HomePane.this) : null;
            } else if (MNEMONIC_KEY.equals(key)) {
                Character mnemonic = (Character) this.pluginAction.getPropertyValue(PluginAction.Property.MNEMONIC);
                return mnemonic != null ? new Integer(mnemonic) : null;
            } else if (PluginAction.Property.TOOL_BAR.name().equals(key)) {
                return this.pluginAction.getPropertyValue(PluginAction.Property.TOOL_BAR);
            } else if (PluginAction.Property.MENU.name().equals(key)) {
                return this.pluginAction.getPropertyValue(PluginAction.Property.MENU);
            } else {
                return null;
            }
        }

        public void putValue(String key, Object value) {
            if (NAME.equals(key)) {
                this.pluginAction.putPropertyValue(PluginAction.Property.NAME, value);
            } else if (SHORT_DESCRIPTION.equals(key)) {
                this.pluginAction.putPropertyValue(PluginAction.Property.SHORT_DESCRIPTION, value);
            } else if (SMALL_ICON.equals(key)) {
                // Ignore icon change
            } else if (MNEMONIC_KEY.equals(key)) {
                this.pluginAction.putPropertyValue(PluginAction.Property.MNEMONIC, new Character((char) ((Integer) value).intValue()));
            } else if (PluginAction.Property.TOOL_BAR.name().equals(key)) {
                this.pluginAction.putPropertyValue(PluginAction.Property.TOOL_BAR, value);
            } else if (PluginAction.Property.MENU.name().equals(key)) {
                this.pluginAction.putPropertyValue(PluginAction.Property.MENU, value);
            }
        }

        public boolean isEnabled() {
            return this.pluginAction.isEnabled();
        }

        public void setEnabled(boolean enabled) {
            this.pluginAction.setEnabled(enabled);
        }
    }

    /**
     * An object able to format a selectable item.
     */
    private abstract interface SelectableFormat<T extends Selectable> {

        public abstract String format(T item);
    }
}
