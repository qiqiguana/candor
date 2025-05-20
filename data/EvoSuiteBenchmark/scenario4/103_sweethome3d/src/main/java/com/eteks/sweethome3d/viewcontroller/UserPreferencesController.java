package com.eteks.sweethome3d.viewcontroller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import com.eteks.sweethome3d.model.LengthUnit;
import com.eteks.sweethome3d.model.TextureImage;
import com.eteks.sweethome3d.model.UserPreferences;

/**
 * A MVC controller for user preferences view.
 *
 * @author Emmanuel Puybaret
 */
public class UserPreferencesController implements Controller {

    /**
     * The properties that may be edited by the view associated to this controller.
     */
    public enum Property {

        LANGUAGE,
        UNIT,
        MAGNETISM_ENABLED,
        RULERS_VISIBLE,
        GRID_VISIBLE,
        FURNITURE_VIEWED_FROM_TOP,
        ROOM_FLOOR_COLORED_OR_TEXTURED,
        WALL_PATTERN,
        NEW_WALL_THICKNESS,
        NEW_WALL_HEIGHT,
        NEW_FLOOR_THICKNESS,
        FURNITURE_CATALOG_VIEWED_IN_TREE,
        NAVIGATION_PANEL_VISIBLE,
        AUTO_SAVE_DELAY_FOR_RECOVERY,
        AUTO_SAVE_FOR_RECOVERY_ENABLED
    }

    private final UserPreferences preferences;

    private final ViewFactory viewFactory;

    private final HomeController homeController;

    private final PropertyChangeSupport propertyChangeSupport;

    private DialogView userPreferencesView;

    private String language;

    private LengthUnit unit;

    private boolean furnitureCatalogViewedInTree;

    private boolean navigationPanelVisible;

    private boolean magnetismEnabled;

    private boolean rulersVisible;

    private boolean gridVisible;

    private boolean furnitureViewedFromTop;

    private boolean roomFloorColoredOrTextured;

    private TextureImage wallPattern;

    private float newWallThickness;

    private float newWallHeight;

    private float newFloorThickness;

    private int autoSaveDelayForRecovery;

    private boolean autoSaveForRecoveryEnabled;

    /**
     * Creates the controller of user preferences view.
     */
    public UserPreferencesController(UserPreferences preferences, ViewFactory viewFactory, ContentManager contentManager) {
    }

    /**
     * Creates the controller of user preferences view.
     */
    public UserPreferencesController(UserPreferences preferences, ViewFactory viewFactory, ContentManager contentManager, HomeController homeController) {
    }

    /**
     * Returns the view associated with this controller.
     */
    public DialogView getView();

    /**
     * Displays the view controlled by this controller.
     */
    public void displayView(View parentView);

    /**
     * Adds the property change <code>listener</code> in parameter to this controller.
     */
    public void addPropertyChangeListener(Property property, PropertyChangeListener listener);

    /**
     * Removes the property change <code>listener</code> in parameter from this controller.
     */
    public void removePropertyChangeListener(Property property, PropertyChangeListener listener);

    /**
     * Updates preferences properties edited by this controller.
     */
    protected void updateProperties();

    /**
     * Returns <code>true</code> if the given <code>property</code> is editable.
     * Depending on whether a property is editable or not, the view associated to this controller
     * may render it differently.
     * The implementation of this method always returns <code>true</code> except for <code>LANGUAGE</code> if it's not editable.
     */
    public boolean isPropertyEditable(Property property);

    /**
     * Sets the edited language.
     */
    public void setLanguage(String language);

    /**
     * Returns the edited language.
     */
    public String getLanguage();

    /**
     * Sets the edited unit.
     */
    public void setUnit(LengthUnit unit);

    /**
     * Returns the edited unit.
     */
    public LengthUnit getUnit();

    /**
     * Sets whether the furniture catalog should be viewed in a tree or a different way.
     */
    public void setFurnitureCatalogViewedInTree(boolean furnitureCatalogViewedInTree);

    /**
     * Returns <code>true</code> if furniture catalog should be viewed in a tree.
     */
    public boolean isFurnitureCatalogViewedInTree();

    /**
     * Sets whether the navigation panel should be displayed or not.
     */
    public void setNavigationPanelVisible(boolean navigationPanelVisible);

    /**
     * Returns <code>true</code> if the navigation panel should be displayed.
     */
    public boolean isNavigationPanelVisible();

    /**
     * Sets whether magnetism is enabled or not.
     */
    public void setMagnetismEnabled(boolean magnetismEnabled);

    /**
     * Returns whether magnetism is enabled or not.
     */
    public boolean isMagnetismEnabled();

    /**
     * Sets whether rulers are visible or not.
     */
    public void setRulersVisible(boolean rulersVisible);

    /**
     * Returns whether rulers are visible or not.
     */
    public boolean isRulersVisible();

    /**
     * Sets whether grid is visible or not.
     */
    public void setGridVisible(boolean gridVisible);

    /**
     * Returns whether grid is visible or not.
     */
    public boolean isGridVisible();

    /**
     * Sets how furniture should be displayed in plan.
     */
    public void setFurnitureViewedFromTop(boolean furnitureViewedFromTop);

    /**
     * Returns how furniture should be displayed in plan.
     */
    public boolean isFurnitureViewedFromTop();

    /**
     * Sets whether floor texture is visible in plan or not.
     */
    public void setRoomFloorColoredOrTextured(boolean floorTextureVisible);

    /**
     * Returns <code>true</code> if floor texture is visible in plan.
     */
    public boolean isRoomFloorColoredOrTextured();

    /**
     * Sets how furniture should be displayed in plan, and notifies
     * listeners of this change.
     */
    public void setWallPattern(TextureImage wallPattern);

    /**
     * Returns the wall pattern in plan.
     */
    public TextureImage getWallPattern();

    /**
     * Sets the edited new wall thickness.
     */
    public void setNewWallThickness(float newWallThickness);

    /**
     * Returns the edited new wall thickness.
     */
    public float getNewWallThickness();

    /**
     * Sets the edited new wall height.
     */
    public void setNewWallHeight(float newWallHeight);

    /**
     * Returns the edited new wall height.
     */
    public float getNewWallHeight();

    /**
     * Sets the edited new floor thickness.
     */
    public void setNewFloorThickness(float newFloorThickness);

    /**
     * Returns the edited new floor thickness.
     */
    public float getNewFloorThickness();

    /**
     * Sets the edited auto recovery save delay.
     */
    public void setAutoSaveDelayForRecovery(int autoSaveDelayForRecovery);

    /**
     * Returns the edited auto recovery save delay.
     */
    public int getAutoSaveDelayForRecovery();

    /**
     * Sets whether auto recovery save is enabled or not.
     */
    public void setAutoSaveForRecoveryEnabled(boolean autoSaveForRecoveryEnabled);

    /**
     * Returns <code>true</code> if auto recovery save is enabled.
     */
    public boolean isAutoSaveForRecoveryEnabled();

    /**
     * Returns <code>true</code> if language libraries can be imported.
     */
    public boolean mayImportLanguageLibrary();

    /**
     * Imports a language library chosen by the user.
     */
    public void importLanguageLibrary();

    /**
     * Controls the modification of user preferences.
     */
    public void modifyUserPreferences();

    /**
     * Resets the displayed flags of action tips.
     */
    public void resetDisplayedActionTips();
}
