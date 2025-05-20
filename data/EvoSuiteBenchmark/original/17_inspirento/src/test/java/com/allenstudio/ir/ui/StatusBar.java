package com.allenstudio.ir.ui;
/*
 * @(#)StatusBar.java
 * Created on 2005-8-2
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

import javax.swing.*;

import java.awt.*;

/**
 * A status bar intended to be arranged
 * at the bottom of a window.<br>
 * In current design, this <code>StatusBar</code>
 * is only used to show tooltips of menu items as
 * well as toolbar buttons.
 * 
 * @author Allen Chue
 */
public class StatusBar extends JComponent {
    /**Display tooltip information*/
    private JLabel tooltipLabel = null;
    
    public StatusBar() {
        setLayout(new BorderLayout());
        //setBorder(BorderFactory.createRaisedBevelBorder());
        initComponents();
    }
    
    void initComponents() {
        tooltipLabel = new JLabel("  ");
        tooltipLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        add(tooltipLabel, BorderLayout.CENTER);
    }
    
    public void displayTooltip(String tooltip) {
        tooltipLabel.setText(tooltip);
    }
}
