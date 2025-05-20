/*
 * @(#)UIResources.java
 * Created on 2005-7-28
 * 
 * Copyright AllenStudio
 */
package com.allenstudio.ir.ui;

import java.util.*;

/**
 * Provides only a public static method
 * to offer UI resources, such as menu labels,
 * icons, toolbar icons, and etc.
 * 
 * @author Allen Chue
 */
public class UIResources {
    private static ResourceBundle resources = null;
    
    static {
        try {
            resources = ResourceBundle.getBundle
                    ("com.allenstudio.ir.ui.resources.Inspirento");//$NON-NLS-1$
        }
        catch (MissingResourceException e) {
            System.out.println("Cannot load resource file.");//$NON-NLS-1$
            System.exit(1);
        }
    }

    public static String getString(String key) {
        return resources.getString(key);
    }
}
