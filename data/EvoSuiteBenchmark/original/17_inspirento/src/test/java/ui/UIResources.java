/*
 * @(#)UIResources.java
 * Created on 2005-7-28
 * iChat LE. Copyright AllenStudio. All Rights Reserved
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package ui;

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
            resources = ResourceBundle.getBundle("ui.resources.ichat",
                    Locale.getDefault());//$NON-NLS-1$
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
