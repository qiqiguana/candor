package com.allenstudio.ir.ui;
/*
 * @(#)MainFrame.java
 * Created on 2005-7-27
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

import com.allenstudio.ir.util.*;
import com.allenstudio.ir.core.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Main frame of Inspirento.
 * In convenience, it is also the mediator class
 * in charge of receiving other widgets's requests
 * and determining what to do next when changes occur.
 * 
 * @version alpha 0.86 27/07/2005
 * @author Allen Chue
 */
public class MainFrame extends JFrame implements InspirentoMediator {

    private StatusBar statusBar = null;
    
    ///////////////////
    ///////Widgets/////
    //////////////////
    private DatePickerPane datePickerPane;
    private NoteOperationsPane noteOperationsPane;
    private MainMenu mainMenu;
    
    //////////////////
    
        
    public MainFrame() {
        
        setTitle(Constants.PROJECT_NAME + " - " +
                Constants.VERSION);
        
        setJMenuBar(new MainMenu(this));

        add(new Toolbar(), BorderLayout.NORTH);
        
        statusBar = new StatusBar();
        add(statusBar, BorderLayout.SOUTH);
        buildSplitPane();
        
        setSize(parseDimension(
                ConfigurationManager.getInstance().getProperty("window.size")));//$NON-NLS-1$

        Dimension location = parseDimension(
                ConfigurationManager.getInstance().getProperty("window.location"));//$NON-NLS-1$
        setLocation(location.width, location.height);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        this.addWindowListener(new WindowListener() {

            public void windowOpened(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                ConfigurationManager cm = ConfigurationManager.getInstance();
                cm.setProperty("window.size",//$NON-NLS-1$
                        getSize().width + " ," + getSize().height);
                cm.setProperty("window.location",//$NON-NLS-1$
                        getLocation().x + " ," + getLocation().y);
                cm.writeBack();
                System.exit(0);
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
            
        });
    }
       
    private void buildSplitPane() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                true);
        splitPane.setResizeWeight(1);
        splitPane.setLeftComponent(createNoteTabPane());
        splitPane.setRightComponent(createSidebar());
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void arrangeMenubar() {
        
    }
    
    private void arrangeToolbar() {
        
    }
    
    private NoteListsTabPane createNoteTabPane() {
        NoteListsTabPane pane = new NoteListsTabPane();
        
        return pane;
    }
    
    /**
     * Arranges the side bar including a
     * <code>DateChooserPane</code> and
     * a <code>NoteOperationsPane</code>.
     *
     */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        BoxLayout box = new BoxLayout(sidebar, BoxLayout.Y_AXIS);
        sidebar.setLayout(box);
        
        sidebar.add(new DatePickerPane());
        sidebar.add(new NoteOperationsPane());
        sidebar.setMaximumSize(sidebar.getPreferredSize());
        
        return sidebar;
    }
    
    /**
     * Parses the string-form-presented dimension to
     * a real <code>Dimension</code> object. This method is
     * used when reading properties from <code>ConfigurationManager</code>.
     * The dimension, perhaps, is "400,300", which causes this method
     * to return <code>new Dimension(400, 300)</code>.<br>
     * <em>Note: if the parsing process comes across any exception,
     * a Dimension of (599, 400) is returned. However, this situation
     * is supposed never to happen.</em>
     * @param dimension a string-form dimension, e.g. "400,300"
     * @return a real <code>Dimension</code> object
     */
     private Dimension parseDimension(String dimension) {
        try {
            String[] str = InspirentoUtilities.tokenize(dimension, ",");
            int width = Integer.parseInt(str[0]);
            int height = Integer.parseInt(str[1]);
            return new Dimension(width, height);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dimension format incorrect!");//$NON-NLS-1$
            return new Dimension(599, 400);
        }
    }
    
    

    public static void main(String[] args) {
        try {
            try {

                Font font = new Font("Dialog", Font.PLAIN, 12);
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UIManager.put("Label.font", font);
                UIManager.put("ComboBox.font", font);
                UIManager.put("Button.font", font);
                UIManager.put("MenuBar.font", font);
                UIManager.put("Menu.font", font);
                UIManager.put("MenuItem.font", font);
                UIManager.put("CheckBoxMenuItem.font", font);
            }
            catch (Exception e) {
                System.out.println("Load jgoodies lnf failed. Default setting loaded.");//$NON-NLS-1$
            }
            MainFrame frame = new MainFrame();
            //frame.pack();
            frame.setVisible(true);
        }
        catch (Throwable t) {
            System.out.println("Uncaught exception: " + t);//$NON-NS-1$
            t.printStackTrace();
        }
    }

    public void widgetChanged(InspirentoWidget iw) {
        
    }
}
