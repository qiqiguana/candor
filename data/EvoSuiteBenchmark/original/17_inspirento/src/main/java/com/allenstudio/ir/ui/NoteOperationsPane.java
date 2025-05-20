/*
 * @(#)NoteOperationsPane.java
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

import java.awt.*;

import javax.swing.*;

import com.jgoodies.uif_lite.panel.SimpleInternalFrame;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * A pane that contains a series of components
 * related to operations on a note.
 *  
 * @author Allen Chue
 */
public class NoteOperationsPane extends SimpleInternalFrame {

    public static void main(String[] args) throws Exception {
        Font font = new Font("Dialog", Font.PLAIN, 12);
        UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
        UIManager.put("Label.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("Button.font", font);
        
        JFrame f = new JFrame("Test");
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new NoteOperationsPane(), BorderLayout.CENTER);
        f.pack();
        
        f.setVisible(true);
    }
    
    private JComboBox typeBox = new JComboBox();
    private JButton featureButton = new JButton("C:\\Setup.exe");
    private JComboBox repetitionBox = new JComboBox();
    private JButton datePickButton = new JButton("Pick a date...");
    
    public NoteOperationsPane() {
        setTitle(UIResources.getString("noteOperationsTitle"));//$NON-NLS-1$
        
        initComponents();
    }
    
    private void initComponents() {
        CellConstraints cc = new CellConstraints();
        
        JLabel typeLabel = new JLabel(UIResources.getString("typeLabel") + ":");        
        JLabel featureLabel = new JLabel(UIResources.getString("featureLabel") + ":");        
        JLabel repetitionLabel = new JLabel(UIResources.getString("repetitionLabel") + ":");        
        JLabel moveLabel = new JLabel(UIResources.getString("moveLabel") + ":");
        
        //======== Panel building ========
        {
            this.setContentPaneBorder(BorderFactory.createEmptyBorder(8, 2, 8, 3));
                        
            Container thisContentPane = this.getContentPane();
            
            thisContentPane.setLayout(new FormLayout(
                new ColumnSpec[] {
                    new ColumnSpec("right:max(default;40dlu)"),
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    new ColumnSpec("max(default;40dlu)")
                },
                new RowSpec[] {
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC
                }));
            
            //---- typeLabel ----
            thisContentPane.add(typeLabel, cc.xy(1, 1));
            thisContentPane.add(typeBox, cc.xy(3, 1));
            
            //---- featureLabel ----
            thisContentPane.add(featureLabel, cc.xy(1, 3));
            
            //---- featureButton ----
            featureButton.setText("F:\\Setup.exe");
            thisContentPane.add(featureButton, cc.xy(3, 3));
            
            //---- repetitionLabel ----
            thisContentPane.add(repetitionLabel, cc.xy(1, 5));
            thisContentPane.add(repetitionBox, cc.xy(3, 5));
            
            //---- moveLabel ----
            thisContentPane.add(moveLabel, cc.xy(1, 7));
            
            //---- datePickButton ----
            datePickButton.setText(UIResources.getString("datePickLabel"));
            thisContentPane.add(datePickButton, cc.xy(3, 7));
        }
    }
}
