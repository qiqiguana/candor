package com.allenstudio.ir.ui;
/*
 * @(#)MainMenu.java
 * Created on 2005-7-28
 * Inspirento, Copyright AllenStudio, All Rights Reserved
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
 *
 */

import java.net.URL;
import java.util.*;

import javax.swing.*;

import com.allenstudio.ir.core.*;
import com.allenstudio.ir.util.*;

/**
 * The menu class of Inspirento.
 * 
 * @author Allen Chue
 */
public class MainMenu extends JMenuBar implements InspirentoWidget {
    
    // These String's are used for resource-based
    // GUI binding.
    private static final String LABEL_SUFFIX = "Label";
    private static final String ICON_SUFFIX = "Icon";
    private static final String SHORTCUT_SUFFIX = "Shortcut";
    
    private InspirentoMediator mediator = null;
    
    public MainMenu(InspirentoMediator mediator) {
        super();
        
        this.mediator = mediator;
        
        String[] menus = InspirentoUtilities.tokenize(UIResources.getString("menu"));//$NON-NLS-1$
        for (String menu : menus) {
            add(createMenu(menu));
        }
    }
    
    
    
    /**
     * Creates a <code>JMenu</code> according to
     * the specified <code>menuLabel</code>. All
     * <code>JMenuItem</code>s associated with this
     * <code>JMenu</code> are added to it.
     * @param menuLabel the <code>JMenu</code>'s label text
     * @return a <code>JMenu</code> containing items
     */
    private JMenu createMenu(String menu) {
        JMenu jMenu = null;
                
        try {
            String tempLabel = UIResources.getString(menu + LABEL_SUFFIX);
            if (tempLabel.length() > 2 
                    && tempLabel.charAt(tempLabel.length() - 2) == '*') {
                //Add Mnemonic
                jMenu = new JMenu(tempLabel.substring(0, tempLabel.length() - 2));
                jMenu.setMnemonic(tempLabel.charAt(tempLabel.length() - 1));
            }
            else {
                jMenu = new JMenu(tempLabel);
            }
            
            String[] items = InspirentoUtilities.tokenize(UIResources.getString(menu));
            for (String item : items) {
                if (!("-".equals(item))) {
                    if (item.length() > 1 &&
                            item.charAt(item.length() - 1) == '>') {
//                        Add submenus recursively
                        jMenu.add(createMenu(item.substring(0, item.length() - 1)));
                    }
                    else if (item.charAt(0) == '#') {//CheckBoxMenuItem
//                      Actions should be binded with menu items in this block
                        String itemLabel = UIResources.getString(item.substring(1)
                                + LABEL_SUFFIX);
                        JCheckBoxMenuItem jMenuItem = null;
                        if (itemLabel.length() > 2 
                                && itemLabel.charAt(itemLabel.length() - 2) == '*') {
    //                      Add Mnemonic
                            jMenuItem = new JCheckBoxMenuItem(itemLabel.substring(0, itemLabel.length() - 2));
                            jMenuItem.setMnemonic(itemLabel.charAt(itemLabel.length() - 1));
                        }
                        else {
                            jMenuItem = new JCheckBoxMenuItem(itemLabel);
                        }
    //                  Add icon and shortcut
                        addShortcutAndIcon(jMenuItem, item);
                        
                        jMenu.add(jMenuItem);
                    }
                    else {
//                        Actions should be binded with menu items in this block
                        String itemLabel = UIResources.getString(item + LABEL_SUFFIX);
                        JMenuItem jMenuItem = null;
                        if (itemLabel.length() > 2 
                                && itemLabel.charAt(itemLabel.length() - 2) == '*') {
    //                      Add Mnemonic
                            jMenuItem = new JMenuItem(itemLabel.substring(0, itemLabel.length() - 2));
                            jMenuItem.setMnemonic(itemLabel.charAt(itemLabel.length() - 1));
                        }
                        else {
                            jMenuItem = new JMenuItem(itemLabel);
                        }
    //                  Add icon and shortcut
                        addShortcutAndIcon(jMenuItem, item);
                        //jMenuItem.addMouseListener(mediator.getMainFrame().getTooltipHandler());
                        
                        jMenu.add(jMenuItem);
                    }
                }
                else {//Add a separator
                    jMenu.addSeparator();
                }
            }
        }
        catch (MissingResourceException e) {
            System.out.println("Cannot load certain menu label(s).\n" + e);//$NON-NLS-1$
            System.exit(2);
        }
        
        return jMenu;
    }
    
    /**
     * Sets the accelerator and icon for the <code>item</code>,
     * if possibel. Here <code>MissingResourceException</code>
     * should be ignored, since not every menu item has its accelerator
     * and icon.
     * @param item the <code>JMenuItem</code> to be decorated
     * @param key the key associated with this menu item
     */
    private void addShortcutAndIcon(JMenuItem item, String key) {
        try {
            String shortcut = UIResources.getString(key + SHORTCUT_SUFFIX);
            item.setAccelerator(KeyStroke.getKeyStroke(shortcut));
        }
        catch (MissingResourceException e) {}
        
        try {
            String path = UIResources.getString(key + ICON_SUFFIX);
            URL url = this.getClass().getResource(path);
            if (url != null) {
                item.setIcon(new ImageIcon(url));
            }
            else {
                System.out.println("Warning: icon file lost!");//$NON-NLS-1$
            }
        }
        catch (MissingResourceException e) {}
    }



    public void changed() {
        
    }
}
