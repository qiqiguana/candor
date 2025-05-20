package com.eteks.sweethome3d.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.security.AccessControlException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import com.apple.eio.FileManager;
import com.eteks.sweethome3d.model.Home;

/**
 * Tools used to test current user operating system.
 *
 * @author Emmanuel Puybaret
 */
public class OperatingSystem {

    private static final String EDITOR_SUB_FOLDER;

    private static final String APPLICATION_SUB_FOLDER;

    private static final String TEMPORARY_SUB_FOLDER;

    private static final String TEMPORARY_SESSION_SUB_FOLDER;

    static {
    }

    // This class contains only static methods
    private OperatingSystem() {
    }

    /**
     * Returns <code>true</code> if current operating is Linux.
     */
    public static boolean isLinux();

    /**
     * Returns <code>true</code> if current operating is Windows.
     */
    public static boolean isWindows();

    /**
     * Returns <code>true</code> if current operating is Mac OS X.
     */
    public static boolean isMacOSX();

    /**
     * Returns <code>true</code> if current operating is Mac OS X 10.5
     * or superior.
     */
    public static boolean isMacOSXLeopardOrSuperior();

    /**
     * Returns a temporary file that will be deleted when JVM will exit.
     * @throws IOException if the file couldn't be created
     */
    public static File createTemporaryFile(String prefix, String suffix) throws IOException;

    /**
     * Deletes all the temporary files created with {@link #createTemporaryFile(String, String) createTemporaryFile}.
     */
    public static void deleteTemporaryFiles();

    /**
     * Returns the default folder used to store temporary files created in the program.
     */
    private synchronized static File getDefaultTemporaryFolder(boolean create) throws IOException;

    /**
     * Returns default application folder.
     */
    public static File getDefaultApplicationFolder() throws IOException;

    /**
     * File manager class that accesses to Mac OS X specifics.
     * Do not invoke methods of this class without checking first if
     * <code>os.name</code> System property is <code>Mac OS X</code>.
     * This class requires some classes of <code>com.apple.eio</code> package
     * to compile.
     */
    private static class MacOSXFileManager {

        public static String getApplicationSupportFolder() throws IOException {
            // Find application support folder (0x61737570) for user domain (-32763)
            return FileManager.findFolder((short) -32763, 0x61737570);
        }
    }
}
