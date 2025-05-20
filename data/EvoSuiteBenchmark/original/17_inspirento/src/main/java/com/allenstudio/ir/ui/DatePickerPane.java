/*
 * @(#)DatePickerPane.java
 * Created on 2005-8-13
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
package com.allenstudio.ir.ui;

import java.util.GregorianCalendar;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import com.allenstudio.ir.ui.calendar.DateChooser;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;
/**
 * A pane that contains a date picker in it.
 * 
 * @author Allen Chue
 */
public class DatePickerPane extends SimpleInternalFrame {

    private DateChooser chooser;

    public static void main(String[] args) throws Exception {
        Font font = new Font("Dialog", Font.PLAIN, 12);
        UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
        UIManager.put("Label.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("Button.font", font);
        
        JFrame f = new JFrame("Test");
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new DatePickerPane(), BorderLayout.CENTER);
        f.pack();
        
        f.setVisible(true);
    }
    
    public DatePickerPane() {
        setTitle(UIResources.getString("datePickerTitle"));//$NON-NLS-1$
        
        initComponents();
    }
    
    private void initComponents() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        chooser = new DateChooser();
        chooser.setBorder(BorderFactory.createEmptyBorder(4, 5, 3, 5));
        contentPane.add(chooser, BorderLayout.CENTER);
    }
    
    public GregorianCalendar reportSelectedDate() {
        return null;
    }

    /**
     * Returns the <code>DateChooser</code> that is
     * integrated in this pane. This method is used
     * to get a reference to the chooser, and then
     * add listeners to it.
     * @return a <code>DateChooser</code>
     */
    public DateChooser getDateChooser() {
        return chooser;
    }
}
