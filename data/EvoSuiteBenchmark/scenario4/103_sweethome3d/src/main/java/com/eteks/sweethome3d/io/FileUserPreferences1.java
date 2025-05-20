package com.eteks.sweethome3d.io;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.eteks.sweethome3d.model.CatalogDoorOrWindow;
import com.eteks.sweethome3d.model.CatalogPieceOfFurniture;
import com.eteks.sweethome3d.model.CatalogTexture;
import com.eteks.sweethome3d.model.Content;
import com.eteks.sweethome3d.model.FurnitureCatalog;
import com.eteks.sweethome3d.model.FurnitureCategory;
import com.eteks.sweethome3d.model.LengthUnit;
import com.eteks.sweethome3d.model.PatternsCatalog;
import com.eteks.sweethome3d.model.RecorderException;
import com.eteks.sweethome3d.model.Sash;
import com.eteks.sweethome3d.model.TexturesCatalog;
import com.eteks.sweethome3d.model.TexturesCategory;
import com.eteks.sweethome3d.model.UserPreferences;
import com.eteks.sweethome3d.tools.OperatingSystem;
import com.eteks.sweethome3d.tools.TemporaryURLContent;
import com.eteks.sweethome3d.tools.URLContent;

/**
 * User preferences initialized from
 * {@link com.eteks.sweethome3d.io.DefaultUserPreferences default user preferences}
 * and stored in user preferences on local file system.
 *
 * @author Emmanuel Puybaret
 */
public class FileUserPreferences extends UserPreferences {

    private static final String LANGUAGE = "language";

    private static final String UNIT = "unit";

    private static final String FURNITURE_CATALOG_VIEWED_IN_TREE = "furnitureCatalogViewedInTree";

    private static final String NAVIGATION_PANEL_VISIBLE = "navigationPanelVisible";

    private static final String MAGNETISM_ENABLED = "magnetismEnabled";

    private static final String RULERS_VISIBLE = "rulersVisible";

    private static final String GRID_VISIBLE = "gridVisible";

    private static final String FURNITURE_VIEWED_FROM_TOP = "furnitureViewedFromTop";

    private static final String ROOM_FLOOR_COLORED_OR_TEXTURED = "roomFloorColoredOrTextured";

    private static final String WALL_PATTERN = "wallPattern";

    private static final String NEW_WALL_HEIGHT = "newHomeWallHeight";

    private static final String NEW_WALL_THICKNESS = "newWallThickness";

    private static final String NEW_FLOOR_THICKNESS = "newFloorThickness";

    private static final String AUTO_SAVE_DELAY_FOR_RECOVERY = "autoSaveDelayForRecovery";

    private static final String AUTO_COMPLETION_PROPERTY = "autoCompletionProperty#";

    private static final String AUTO_COMPLETION_STRINGS = "autoCompletionStrings#";

    private static final String RECENT_HOMES = "recentHomes#";

    private static final String IGNORED_ACTION_TIP = "ignoredActionTip#";

    private static final String FURNITURE_NAME = "furnitureName#";

    private static final String FURNITURE_CATEGORY = "furnitureCategory#";

    private static final String FURNITURE_ICON = "furnitureIcon#";

    private static final String FURNITURE_MODEL = "furnitureModel#";

    private static final String FURNITURE_WIDTH = "furnitureWidth#";

    private static final String FURNITURE_DEPTH = "furnitureDepth#";

    private static final String FURNITURE_HEIGHT = "furnitureHeight#";

    private static final String FURNITURE_MOVABLE = "furnitureMovable#";

    private static final String FURNITURE_DOOR_OR_WINDOW = "furnitureDoorOrWindow#";

    private static final String FURNITURE_ELEVATION = "furnitureElevation#";

    private static final String FURNITURE_COLOR = "furnitureColor#";

    private static final String FURNITURE_MODEL_ROTATION = "furnitureModelRotation#";

    private static final String FURNITURE_STAIRCASE_CUT_OUT_SHAPE = "furnitureStaircaseCutOutShape#";

    private static final String FURNITURE_BACK_FACE_SHOWN = "furnitureBackFaceShown#";

    private static final String FURNITURE_ICON_YAW = "furnitureIconYaw#";

    private static final String FURNITURE_PROPORTIONAL = "furnitureProportional#";

    private static final String TEXTURE_NAME = "textureName#";

    private static final String TEXTURE_CATEGORY = "textureCategory#";

    private static final String TEXTURE_IMAGE = "textureImage#";

    private static final String TEXTURE_WIDTH = "textureWidth#";

    private static final String TEXTURE_HEIGHT = "textureHeight#";

    private static final String FURNITURE_CONTENT_PREFIX = "Furniture-3-";

    private static final String TEXTURE_CONTENT_PREFIX = "Texture-3-";

    private static final String LANGUAGE_LIBRARIES_PLUGIN_SUB_FOLDER = "languages";

    private static final String FURNITURE_LIBRARIES_PLUGIN_SUB_FOLDER = "furniture";

    private static final String TEXTURES_LIBRARIES_PLUGIN_SUB_FOLDER = "textures";

    private static final Content DUMMY_CONTENT;

    private final Map<String, Boolean> ignoredActionTips = new HashMap<String, Boolean>();

    private List<ClassLoader> resourceClassLoaders;

    private final File preferencesFolder;

    private final File[] applicationFolders;

    private Preferences preferences;

    private Executor catalogsLoader;

    private Executor updater;

    static {
    }

    /**
     * Creates user preferences read from user preferences in file system,
     * and from resource files.
     */
    public FileUserPreferences() {
    }

    /**
     * Creates user preferences stored in the folders given in parameter.
     * @param preferencesFolder the folder where preferences files are stored
     *    or <code>null</code> if this folder is the default one.
     * @param applicationFolders the folders where application private files are stored
     *    or <code>null</code> if it's the default one. As the first application folder
     *    is used as the folder where plug-ins files are imported by the user, it should
     *    have write access otherwise the user won't be able to import them.
     */
    public FileUserPreferences(File preferencesFolder, File[] applicationFolders) {
    }

    /**
     * Creates user preferences stored in the folders given in parameter.
     * @param preferencesFolder the folder where preferences files are stored
     *    or <code>null</code> if this folder is the default one.
     * @param applicationFolders  the folders where application private files are stored
     *    or <code>null</code> if it's the default one. As the first application folder
     *    is used as the folder where plug-ins files are imported by the user, it should
     *    have write access otherwise the user won't be able to import them.
     * @param updater  an executor that will be used to update user preferences for lengthy
     *    operations. If <code>null</code>, then these operations and
     *    updates will be executed in the current thread.
     */
    public FileUserPreferences(File preferencesFolder, File[] applicationFolders, Executor updater) {
    }

    /**
     * Updates the default supported languages with languages available in plugin folder.
     */
    private void updateSupportedLanguages();

    /**
     * Returns the languages included in the given language library file.
     */
    private Set<String> getLanguages(File languageLibraryFile) throws IOException;

    /**
     * Returns the default class loader of user preferences and the class loaders that
     * give access to resources in language libraries plugin folder.
     */
    @Override
    public List<ClassLoader> getResourceClassLoaders();

    /**
     * Reloads furniture default catalogs.
     */
    private void updateFurnitureDefaultCatalog(Executor furnitureCatalogLoader, final Executor updater);

    /**
     * Reloads textures default catalog.
     */
    private void updateTexturesDefaultCatalog(Executor texturesCatalogLoader, final Executor updater);

    /**
     * Adds to auto completion strings the default strings of the new chosen language.
     */
    private void updateAutoCompletionStrings();

    /**
     * Read modifiable furniture catalog from preferences.
     */
    private void readModifiableFurnitureCatalog(Preferences preferences);

    /**
     * Returns model rotation parsed from key value.
     */
    private float[][] getModelRotation(Preferences preferences, String key);

    /**
     * Returns a content instance from the resource file value of key.
     */
    private Content getContent(Preferences preferences, String key);

    /**
     * Read modifiable textures catalog from preferences.
     */
    private void readModifiableTexturesCatalog(Preferences preferences);

    /**
     * Writes user preferences in current user preferences in system.
     */
    @Override
    public void write() throws RecorderException;

    /**
     * Writes modifiable furniture in <code>preferences</code>.
     */
    private void writeModifiableFurnitureCatalog(Preferences preferences) throws RecorderException;

    /**
     * Returns the string value of the given float, except for -1.0, 1.0 or 0.0 where -1, 1 and 0 is returned.
     */
    private String floatToString(float f);

    /**
     * Writes modifiable textures catalog in <code>preferences</code>.
     */
    private void writeModifiableTexturesCatalog(Preferences preferences) throws RecorderException;

    /**
     * Writes <code>key</code> <code>content</code> in <code>preferences</code>.
     */
    private void putContent(Preferences preferences, String key, Content content, String contentPrefix, Set<URL> furnitureContentURLs) throws RecorderException;

    /**
     * Returns a content object that references a copy of <code>content</code> in
     * user preferences folder.
     */
    private URLContent copyToPreferencesURLContent(Content content, String contentPrefix) throws RecorderException;

    /**
     * Returns the folder where language libraries files must be placed
     * or <code>null</code> if that folder can't be retrieved.
     */
    private File[] getLanguageLibrariesPluginFolders();

    /**
     * Returns the folder where furniture catalog files must be placed
     * or <code>null</code> if that folder can't be retrieved.
     */
    private File[] getFurnitureLibrariesPluginFolders();

    /**
     * Returns the folder where texture catalog files must be placed
     * or <code>null</code> if that folder can't be retrieved.
     */
    private File[] getTexturesLibrariesPluginFolders();

    /**
     * Returns the first Sweet Home 3D application folder.
     */
    public File getApplicationFolder() throws IOException;

    /**
     * Returns Sweet Home 3D application folders.
     */
    public File[] getApplicationFolders() throws IOException;

    /**
     * Returns subfolders of Sweet Home 3D application folders of a given name.
     */
    public File[] getApplicationSubfolders(String subfolder) throws IOException;

    /**
     * Returns a new file in user preferences folder.
     */
    private File createPreferencesFile(String filePrefix) throws IOException;

    /**
     * Creates preferences folder and its sub folders if it doesn't exist.
     */
    private void checkPreferencesFolder() throws IOException;

    /**
     * Creates the first folder in the given folders.
     */
    private void checkPreferencesSubFolder(File[] librariesPluginFolders);

    /**
     * Deletes from application folder the content files starting by <code>contentPrefix</code>
     * that don't belong to <code>contentURLs</code>.
     */
    private void deleteObsoleteContent(final Set<URL> contentURLs, final String contentPrefix) throws RecorderException;

    /**
     * Returns the folder where files depending on preferences are stored.
     */
    private File getPreferencesFolder() throws IOException;

    /**
     * Returns default Java preferences for current system user.
     * Caution : This method is called once in constructor so overriding implementations
     * shouldn't be based on the state of their fields.
     */
    protected Preferences getPreferences();

    /**
     * Sets which action tip should be ignored.
     */
    @Override
    public void setActionTipIgnored(String actionKey);

    /**
     * Returns whether an action tip should be ignored or not.
     */
    @Override
    public boolean isActionTipIgnored(String actionKey);

    /**
     * Resets the display flag of action tips.
     */
    @Override
    public void resetIgnoredActionTips();

    /**
     * Returns <code>true</code> if the given language library exists in the first
     * language libraries folder.
     */
    public boolean languageLibraryExists(String name) throws RecorderException;

    /**
     * Adds <code>languageLibraryName</code> to the first language libraries folder
     * to make the language library it contains available to supported languages.
     */
    public void addLanguageLibrary(String languageLibraryName) throws RecorderException;

    /**
     * Returns <code>true</code> if the given furniture library file exists in the first
     * furniture libraries folder.
     * @param name the name of the resource to check
     */
    @Override
    public boolean furnitureLibraryExists(String name) throws RecorderException;

    /**
     * Adds the file <code>furnitureLibraryName</code> to the first furniture libraries folder
     * to make the furniture library available to catalog.
     */
    @Override
    public void addFurnitureLibrary(String furnitureLibraryName) throws RecorderException;

    /**
     * Returns <code>true</code> if the given textures library file exists in the first textures libraries folder.
     * @param name the name of the resource to check
     */
    @Override
    public boolean texturesLibraryExists(String name) throws RecorderException;

    /**
     * Adds the file <code>texturesLibraryName</code> to the first textures libraries folder
     * to make the textures library available to catalog.
     */
    @Override
    public void addTexturesLibrary(String texturesLibraryName) throws RecorderException;

    /**
     * Copies a library file to a folder.
     */
    private void copyToLibraryFolder(File libraryFile, File folder) throws IOException;

    /**
     * Preferences based on the <code>preferences.xml</code> file
     * stored in a preferences folder.
     * @author Emmanuel Puybaret
     */
    private class PortablePreferences extends AbstractPreferences {

        private static final String PREFERENCES_FILE = "preferences.xml";

        private Properties preferencesProperties;

        private boolean exist;

        private PortablePreferences() {
            super(null, "");
            this.preferencesProperties = new Properties();
            this.exist = readPreferences();
        }

        public boolean exist() {
            return this.exist;
        }

        @Override
        protected void syncSpi() throws BackingStoreException {
            this.preferencesProperties.clear();
            this.exist = readPreferences();
        }

        @Override
        protected void removeSpi(String key) {
            this.preferencesProperties.remove(key);
        }

        @Override
        protected void putSpi(String key, String value) {
            this.preferencesProperties.put(key, value);
        }

        @Override
        protected String[] keysSpi() throws BackingStoreException {
            return this.preferencesProperties.keySet().toArray(new String[0]);
        }

        @Override
        protected String getSpi(String key) {
            return (String) this.preferencesProperties.get(key);
        }

        @Override
        protected void flushSpi() throws BackingStoreException {
            try {
                writePreferences();
            } catch (IOException ex) {
                throw new BackingStoreException(ex);
            }
        }

        @Override
        protected void removeNodeSpi() throws BackingStoreException {
            throw new UnsupportedOperationException();
        }

        @Override
        protected String[] childrenNamesSpi() throws BackingStoreException {
            throw new UnsupportedOperationException();
        }

        @Override
        protected AbstractPreferences childSpi(String name) {
            throw new UnsupportedOperationException();
        }

        /**
         * Reads user preferences.
         */
        private boolean readPreferences() {
            InputStream in = null;
            try {
                in = new FileInputStream(new File(getPreferencesFolder(), PREFERENCES_FILE));
                this.preferencesProperties.loadFromXML(in);
                return true;
            } catch (IOException ex) {
                // Preferences don't exist
                return false;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                    // Let default preferences unchanged
                }
            }
        }

        /**
         * Writes user preferences.
         */
        private void writePreferences() throws IOException {
            OutputStream out = null;
            try {
                checkPreferencesFolder();
                out = new FileOutputStream(new File(getPreferencesFolder(), PREFERENCES_FILE));
                this.preferencesProperties.storeToXML(out, "Portable user preferences 3.0");
            } finally {
                if (out != null) {
                    out.close();
                    this.exist = true;
                }
            }
        }
    }
}
