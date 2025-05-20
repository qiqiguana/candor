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

    public static File getDefaultApplicationFolder() throws IOException {
        File userApplicationFolder;
        if (isMacOSX()) {
            userApplicationFolder = new File(MacOSXFileManager.getApplicationSupportFolder());
        } else if (isWindows()) {
            userApplicationFolder = new File(System.getProperty("user.home"), "Application Data");
            // If user Application Data directory doesn't exist, use user home
            if (!userApplicationFolder.exists()) {
                userApplicationFolder = new File(System.getProperty("user.home"));
            }
        } else {
            // Unix
            userApplicationFolder = new File(System.getProperty("user.home"));
        }
        return new File(userApplicationFolder, EDITOR_SUB_FOLDER + File.separator + APPLICATION_SUB_FOLDER);
    }
}
