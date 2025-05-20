package com.allenstudio.ir.ui;
/*
 * @(#)Toolbar.java
 * Created on 2005-8-1
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

import java.awt.Insets;
import java.net.URL;

import javax.swing.*;

import com.allenstudio.ir.util.InspirentoUtilities;

/**
 * The toolbar of Inspirento.
 * 
 * @author Allen Chue
 */
public class Toolbar extends JToolBar  {
    private static final String ICON_SUFFIX = "Icon";
    
    public Toolbar() {
        super();
        setFloatable(false);
        createButtons();
    }
    
    private void createButtons() {
        String[] buttons = InspirentoUtilities.tokenize(
                UIResources.getString("toolbar"));//$NON-NLS-1$
        for (String button : buttons) {
            String path = UIResources.getString(button + ICON_SUFFIX);
            if (!("-".equals(path))) {
                URL url = this.getClass().getResource(path);
                if (url != null) {
                    JButton toolbarButton = new JButton(new ImageIcon(url));
                    toolbarButton.setOpaque(true);
                    toolbarButton.setMargin(new Insets(1, 1, 1, 1));
                    this.add(toolbarButton);
                }
                else {
                    this.add(new JButton(button));
                    System.out.println("Warning: button icon file lost!" +
                            "\n Use text instead.");//$NON-NLS-1$
                }
            }
            else {//Add a separator
                this.addSeparator();
            }
        }
    }
}
