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

    public static boolean isMacOSXLeopardOrSuperior() {
        // Just need to test is OS version is different of 10.4 because Sweet Home 3D
        // isn't supported under Mac OS X versions previous to 10.4
        return isMacOSX() && !System.getProperty("os.version").startsWith("10.4");
    }
}
