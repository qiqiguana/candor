package de.huxhorn.lilith.swing;

import de.huxhorn.lilith.Lilith;
import de.huxhorn.lilith.LilithSounds;
import de.huxhorn.lilith.data.access.HttpStatus;
import de.huxhorn.lilith.data.logging.LoggingEvent;
import de.huxhorn.lilith.swing.filefilters.GroovyConditionFileFilter;
import de.huxhorn.lilith.swing.preferences.SavedCondition;
import de.huxhorn.lilith.swing.table.ColorScheme;
import de.huxhorn.lilith.swing.table.model.PersistentTableColumnModel;
import de.huxhorn.sulky.conditions.Condition;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.*;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.*;

public class ApplicationPreferences {

    public static enum SourceFiltering {

        NONE, BLACKLIST, WHITELIST
    }

    private static final Preferences PREFERENCES = Preferences.userNodeForPackage(ApplicationPreferences.class);

    private static final String STATUS_COLORS_XML_FILENAME = "statusColors.xml";

    private static final String LEVEL_COLORS_XML_FILENAME = "levelColors.xml";

    private static final String DETAILS_VIEW_ROOT_FOLDER = "detailsView";

    public static final String DETAILS_VIEW_CSS_FILENAME = "detailsView.css";

    public static final String DETAILS_VIEW_GROOVY_FILENAME = "detailsView.groovy";

    private static final String CONDITIONS_XML_FILENAME = "savedConditions.xml";

    public static final String STATUS_COLORS_PROPERTY = "statusColors";

    public static final String LEVEL_COLORS_PROPERTY = "levelColors";

    public static final String LOOK_AND_FEEL_PROPERTY = "lookAndFeel";

    public static final String CLEANING_LOGS_ON_EXIT_PROPERTY = "cleaningLogsOnExit";

    public static final String COLORING_WHOLE_ROW_PROPERTY = "coloringWholeRow";

    public static final String SHOWING_IDENTIFIER_PROPERTY = "showingIdentifier";

    public static final String SHOWING_FULL_CALLSTACK_PROPERTY = "showingFullCallstack";

    public static final String SHOWING_STACKTRACE_PROPERTY = "showingStackTrace";

    public static final String CHECKING_FOR_UPDATE_PROPERTY = "checkingForUpdate";

    public static final String SOURCE_FILTERING_PROPERTY = "sourceFiltering";

    public static final String SOUND_LOCATIONS_PROPERTY = "soundLocations";

    public static final String MUTE_PROPERTY = "mute";

    public static final String USING_INTERNAL_FRAMES_PROPERTY = "usingInternalFrames";

    public static final String SCROLLING_TO_BOTTOM_PROPERTY = "scrollingToBottom";

    public static final String SOURCE_NAMES_PROPERTY = "sourceNames";

    public static final String APPLICATION_PATH_PROPERTY = "applicationPath";

    public static final String AUTO_OPENING_PROPERTY = "autoOpening";

    public static final String AUTO_CLOSING_PROPERTY = "autoClosing";

    public static final String IMAGE_PATH_PROPERTY = "imagePath";

    public static final String SOUND_PATH_PROPERTY = "soundPath";

    public static final String AUTO_FOCUSING_WINDOW_PROPERTY = "autoFocusingWindow";

    public static final String SOURCE_LISTS_PROPERTY = "sourceLists";

    public static final String BLACK_LIST_NAME_PROPERTY = "blackListName";

    public static final String WHITE_LIST_NAME_PROPERTY = "whiteListName";

    public static final String CONDITIONS_PROPERTY = "conditions";

    public static final String SPLASH_SCREEN_DISABLED_PROPERTY = "splashScreenDisabled";

    public static final String ASKING_BEFORE_QUIT_PROPERTY = "askingBeforeQuit";

    public static final String LOGGING_LAYOUT_GLOBAL_XML_FILENAME = "loggingLayoutGlobal.xml";

    public static final String LOGGING_LAYOUT_XML_FILENAME = "loggingLayout.xml";

    public static final String ACCESS_LAYOUT_GLOBAL_XML_FILENAME = "accessLayoutGlobal.xml";

    public static final String ACCESS_LAYOUT_XML_FILENAME = "accessLayout.xml";

    public static final String SOURCE_NAMES_XML_FILENAME = "SourceNames.xml";

    public static final String SOURCE_LISTS_XML_FILENAME = "SourceLists.xml";

    public static final String SOURCE_NAMES_PROPERTIES_FILENAME = "SourceNames.properties";

    public static final String SOUND_LOCATIONS_XML_FILENAME = "SoundLocations.xml";

    public static final String SOUND_LOCATIONS_PROPERTIES_FILENAME = "SoundLocations.properties";

    public static final String PREVIOUS_APPLICATION_PATH_FILENAME = ".previous.application.path";

    private static final String OLD_LICENSED_PREFERENCES_KEY = "licensed";

    private static final String LICENSED_PREFERENCES_KEY = "licensedVersion";

    public static final String USER_HOME;

    public static final String DEFAULT_APPLICATION_PATH;

    private static final Map<String, String> DEFAULT_SOURCE_NAMES;

    private static final Map<String, String> DEFAULT_SOUND_LOCATIONS;

    private static final Map<LoggingEvent.Level, ColorScheme> DEFAULT_LEVEL_COLORS;

    private static final Map<HttpStatus.Type, ColorScheme> DEFAULT_STATUS_COLORS;

    private static final String PREVIOUS_OPEN_PATH_PROPERTY = "previousOpenPath";

    private static final String PREVIOUS_IMPORT_PATH_PROPERTY = "previousImportPath";

    public static final String STARTUP_LOOK_AND_FEEL;

    private static final long CONDITIONS_CHECK_INTERVAL = 30000;

    private static final String GROOVY_SUFFIX = ".groovy";

    private static final String EXAMPLE_GROOVY_BASE = "/conditions/";

    private static final String EXAMPLE_GROOVY_LIST = "conditions.txt";

    static {
    }

    private final Logger logger = LoggerFactory.getLogger(ApplicationPreferences.class);

    private PropertyChangeSupport propertyChangeSupport;

    private File startupApplicationPath;

    private File detailsViewRoot;

    private ArrayList<String> installedLookAndFeels;

    private String[] conditionScriptFiles;

    private long lastConditionsCheck;

    private Map<LoggingEvent.Level, ColorScheme> levelColors;

    private Map<HttpStatus.Type, ColorScheme> statusColors;

    private URL detailsViewRootUrl;

    /**
     * Identifier => Name
     */
    private Map<String, String> sourceNames;

    private long lastSourceNamesModified;

    private long lastConditionsModified;

    private Map<String, String> soundLocations;

    private long lastSoundLocationsModified;

    private Map<String, Set<String>> sourceLists;

    private long lastSourceListsModified;

    private SourceFiltering sourceFiltering;

    private Set<String> blackList;

    private Set<String> whiteList;

    private List<SavedCondition> conditions;

    private File groovyConditionsPath;

    public ApplicationPreferences() {
    }

    public File resolveConditionScriptFile(String input);

    public String[] getAllConditionScriptFiles();

    public void installExampleConditions();

    private void initLevelColors();

    private Map<LoggingEvent.Level, ColorScheme> cloneLevelColors(Map<LoggingEvent.Level, ColorScheme> input);

    public void setLevelColors(Map<LoggingEvent.Level, ColorScheme> colors);

    private void writeLevelColors(Map<LoggingEvent.Level, ColorScheme> colors);

    public Map<LoggingEvent.Level, ColorScheme> getLevelColors();

    private void initStatusColors();

    private Map<HttpStatus.Type, ColorScheme> cloneStatusColors(Map<HttpStatus.Type, ColorScheme> input);

    public void setStatusColors(Map<HttpStatus.Type, ColorScheme> colors);

    private void writeStatusColors(Map<HttpStatus.Type, ColorScheme> colors);

    public Map<HttpStatus.Type, ColorScheme> getStatusColors();

    public void setSourceFiltering(SourceFiltering sourceFiltering);

    private void initSourceLists();

    public Map<String, Set<String>> getSourceLists();

    public void setSourceLists(Map<String, Set<String>> sourceLists);

    public SourceFiltering getSourceFiltering();

    public void initDetailsViewRoot(boolean overwriteAlways);

    private void initIfNecessary(File file, String resourcePath, String historyBasePath, boolean overwriteAlways);

    private void copy(URL source, File target, boolean overwrite);

    /**
     * Returns a list of strings containing all non-empty, non-comment lines found in the given URL.
     * Commented lines start with a #.
     *
     * @param url the URL to read the lines from.
     * @return a List of type String containing all non-empty, non-comment lines.
     */
    private List<String> readLines(URL url);

    public File getDetailsViewRoot();

    public URL getDetailsViewRootUrl();

    public boolean isValidSource(String source);

    public boolean isBlackListed(String source);

    public void setBlackListName(String name);

    public String getBlackListName();

    public boolean isWhiteListed(String source);

    public void setWhiteListName(String name);

    public String getWhiteListName();

    public void setLookAndFeel(String name);

    public String getLookAndFeel();

    private void initConditions();

    public SavedCondition resolveSavedCondition(Condition condition);

    public SavedCondition resolveSavedCondition(String conditionName);

    public List<SavedCondition> getConditions();

    public void setConditions(List<SavedCondition> conditions);

    public void setAutoOpening(boolean autoOpening);

    public boolean isAutoOpening();

    public void setShowingIdentifier(boolean showingIdentifierWithName);

    public boolean isShowingIdentifier();

    public void setSplashScreenDisabled(boolean splashScreenDisabled);

    public boolean isSplashScreenDisabled();

    public void setAskingBeforeQuit(boolean askingBeforeQuit);

    public boolean isAskingBeforeQuit();

    public void setShowingFullCallstack(boolean showingFullCallstack);

    public boolean isShowingFullCallstack();

    public void setShowingStackTrace(boolean showingStackTrace);

    public boolean isShowingStackTrace();

    public void setCleaningLogsOnExit(boolean cleaningLogsOnExit);

    public boolean isCleaningLogsOnExit();

    public void setColoringWholeRow(boolean coloringWholeRow);

    public boolean isColoringWholeRow();

    public void setCheckingForUpdate(boolean checkingForUpdate);

    public boolean isCheckingForUpdate();

    public void setAutoClosing(boolean autoClosing);

    public boolean isAutoClosing();

    public File getImagePath();

    public void setImagePath(File imagePath);

    public File getPreviousOpenPath();

    public void setPreviousOpenPath(File openPath);

    public File getPreviousImportPath();

    public void setPreviousImportPath(File importPath);

    public File getSoundPath();

    public void setSoundPath(File soundPath);

    public void setMute(boolean mute);

    public boolean isMute();

    public void setLicensed(boolean licensed);

    public boolean isLicensed();

    public void setApplicationPath(File applicationPath);

    public File getApplicationPath();

    /**
     * The StartupApplicationPath is initialized on application startup via ApplicationPreferences.getApplicationPath.
     * If a part of the application needs the application path it should *always* use this method instead of
     * getApplicationPath() since the application path might change while this one will always stay
     * the same.
     * <p/>
     * A switch of the application path while the application is running isn't safe so it's changed for real
     * upon next restart.
     *
     * @return the application path at startup time.
     */
    public File getStartupApplicationPath();

    public void setUsingInternalFrames(boolean usingInternalFrames);

    public boolean isUsingInternalFrames();

    public void setAutoFocusingWindow(boolean autoFocusingWindow);

    public boolean isAutoFocusingWindow();

    public void setSourceNames(Map<String, String> sourceNames);

    public Map<String, String> getSourceNames();

    public Map<String, String> getSoundLocations();

    public void setSoundLocations(Map<String, String> soundLocations);

    public void resetSoundLocations();

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

    public void reset();

    public void setScrollingToBottom(boolean scrollingToBottom);

    public boolean isScrollingToBottom();

    private boolean loadSoundLocationsXml(File file);

    private boolean writeSoundLocations(Map<String, String> sourceNames);

    private boolean loadSourceNamesXml(File file);

    private boolean loadSourceNamesProperties(File sourceNamesFile);

    private boolean writeSourceNames(Map<String, String> sourceNames);

    private boolean writeSourceLists(Map<String, Set<String>> sourceLists);

    private boolean writeConditions(List<SavedCondition> conditions);

    /**
     * @noinspection MismatchedQueryAndUpdateOfCollection
     */
    private Map<String, String> loadPropertiesXml(File file);

    /**
     * @noinspection MismatchedQueryAndUpdateOfCollection
     */
    private boolean writePropertiesXml(File file, Map<String, String> sourceNames, String comment);

    private Map<String, String> loadProperties(File file);

    public void writeLoggingColumnLayout(boolean global, List<PersistentTableColumnModel.TableColumnLayoutInfo> layoutInfos);

    public void writeAccessColumnLayout(boolean global, List<PersistentTableColumnModel.TableColumnLayoutInfo> layoutInfos);

    public List<PersistentTableColumnModel.TableColumnLayoutInfo> readLoggingColumnLayout(boolean global);

    public List<PersistentTableColumnModel.TableColumnLayoutInfo> readAccessColumnLayout(boolean global);

    private boolean writeColumnLayout(File file, List<PersistentTableColumnModel.TableColumnLayoutInfo> layoutInfos);

    private List<PersistentTableColumnModel.TableColumnLayoutInfo> readColumnLayout(File file);

    /**
     * Quick & dirty MD5 checksum function.
     * Returns null in case of error.
     *
     * @param input the input
     * @return the checksum
     */
    public static byte[] getMD5(InputStream input);

    public void flush();

    /**
     * As described in http://weblogs.java.net/blog/malenkov/archive/2006/08/how_to_encode_e.html
     */
    static class EnumPersistenceDelegate extends PersistenceDelegate {

        protected boolean mutatesTo(Object oldInstance, Object newInstance) {
            return oldInstance == newInstance;
        }

        protected Expression instantiate(Object oldInstance, Encoder out) {
            Enum e = (Enum) oldInstance;
            return new Expression(e, e.getClass(), "valueOf", new Object[] { e.name() });
        }
    }
}
