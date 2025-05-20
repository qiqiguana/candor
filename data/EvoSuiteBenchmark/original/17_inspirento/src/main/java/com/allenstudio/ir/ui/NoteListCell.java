/*
 * @(#)NoteListCell.java
 * Created on 2005-8-9
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import com.allenstudio.ir.core.plugins.AbstractNote;

/**
 * The cell component in <code>NoteList</code>.<br>
 * This design is mainly a layout concern.
 * 
 * @author Allen Chue
 */
//TODO leave a common interface for plugins to show contents in various ways
public class NoteListCell extends JPanel {

    private static final Color SELECTION_BG = UIManager.getColor("List.selectionBackground");

    private static final Color SELECTION_FG = UIManager.getColor("List.selectionForeground");

    private static final Color UNSELECTION_BG = new Color(245, 245, 245);

    private static final Color UNSELECTION_FG = UIManager.getColor("List.foreground");
    
    
    private JLabel titleIconLabel;//Used to show a title image icon
    private JLabel titleLabel;//Used to show bolded texts as a title
    private JLabel mainInfoLabel;//Show the main info texts
    private JLabel otherInfoLabel;//Show other info texts
    
    private JButton featureButton;//Used for speacial feature, currently attachment-launch button
    
    private boolean isSelected;//True if selected. Uni-mode
    
    private int indexInList = -1;
    
    /**
     * Constructs a new cell according to these
     * parammeters. 
     * FIXME Documentation needed
     * @param titleIcon the title icon
     * @param title the title of this cell
     * @param mainInfo
     * @param otherInfo
     * @param hasFeature
     */
    public NoteListCell(AbstractNote note) {
        
        super(new BorderLayout(5, 0));
        
        initComponents();
        
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setBackground(UNSELECTION_BG);
        
        titleIconLabel.setIcon(note.getIcon());
        titleIconLabel.setVerticalAlignment(JLabel.TOP);
        titleLabel.setText(note.getTitle());
        mainInfoLabel.setText(note.getDescription());
        otherInfoLabel.setText(note.getOtherInfoText());
        
        this.add(createCenterPane(), BorderLayout.CENTER);
        this.add(createFeaturePane(note.getFeature()), BorderLayout.EAST);
        this.add(titleIconLabel, BorderLayout.WEST);
        
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 5));
        
        this.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
//                select(e.getPoint());
            }

            public void mouseClicked(MouseEvent e) {
                if (getParent() instanceof NoteList) {//Judge the parent type
                    NoteList parent = (NoteList)getParent();
                    parent.select(getIndexInList(), true);
                    if (e.getClickCount() == 2) {
                        //Double click
                        parent.fireDoubleClick(NoteListCell.this, e.getPoint());
                    }
                    else if (SwingUtilities.isLeftMouseButton(e)) {
                        //Left click
                        parent.fireLeftClick(NoteListCell.this, e.getPoint());
                    }
                    else if (SwingUtilities.isRightMouseButton(e)) {
                        //Right click
                        parent.fireRightClick(NoteListCell.this, e.getPoint());
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                if (getParent() instanceof NoteList) {//Judge the parent type
                    NoteList parent = (NoteList)getParent();
                    parent.fireCursorEnter(NoteListCell.this, e.getPoint());
                }
            }

            public void mouseExited(MouseEvent e) {
                if (getParent() instanceof NoteList) {//Judge the parent type
                    NoteList parent = (NoteList)getParent();
                    parent.fireCursorExit(NoteListCell.this, e.getPoint());
                }
            }
        });
        
        //This extra mouselistener added to featureButton
        //is due to mouseevent that happens to a button
        //has no effects to the list cell.
        featureButton.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
//                select(e.getPoint());
            }

            public void mouseClicked(MouseEvent e) {
                if (getParent() instanceof NoteList) {//Judge the parent type
                    NoteList parent = (NoteList)getParent();
                    parent.select(getIndexInList(), true);
                    //Double click and left click should be discarded as of featureButton
                    if (e.getClickCount() == 2) {
                    }
                    else if (SwingUtilities.isLeftMouseButton(e)) {
                    }
                    else if (SwingUtilities.isRightMouseButton(e)) {
                        //Right click
                        parent.fireRightClick(NoteListCell.this, e.getPoint());
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                if (getParent() instanceof NoteList) {//Judge the parent type
                    NoteList parent = (NoteList)getParent();
                    parent.fireCursorEnter(NoteListCell.this, e.getPoint());
                }
            }

            public void mouseExited(MouseEvent e) {
                if (getParent() instanceof NoteList) {//Judge the parent type
                    NoteList parent = (NoteList)getParent();
                    parent.fireCursorExit(NoteListCell.this, e.getPoint());
                }
            }
        });
    }
    
    @Override
    public Dimension getMaximumSize() {
        Dimension d = getPreferredSize();
        d.width = Integer.MAX_VALUE;
        
        return d;
    }
    
    /**
     * Selects or diselects a cell. 
     * @param b true for selecting it,
     *          while false for deselecting it
     */
    public void setSelected(boolean b) {
        this.isSelected = b;
        if (b) {
            setBackground(SELECTION_BG);
            titleLabel.setForeground(SELECTION_FG);
            mainInfoLabel.setForeground(SELECTION_FG);
            otherInfoLabel.setForeground(SELECTION_FG);
            
        }
        else {
            setBackground(UNSELECTION_BG);
            titleLabel.setForeground(UNSELECTION_FG);
            mainInfoLabel.setForeground(UNSELECTION_FG);
            otherInfoLabel.setForeground(UNSELECTION_FG);
        }
    }
    
    /**
     * Returns this cell is selected
     * @return true if selected, otherwise false
     */
    public boolean isSelected() {
        return this.isSelected;
    }
    
    void initComponents() {
        titleIconLabel = new JLabel();
        titleLabel = new JLabel();
        mainInfoLabel = new JLabel();
        otherInfoLabel = new JLabel();
        featureButton = new JButton();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    private JPanel createCenterPane() {            
        JPanel centerPane = new JPanel(new BorderLayout(0, 2));
        centerPane.setOpaque(false);

        centerPane.add(titleLabel, BorderLayout.NORTH);
        centerPane.add(mainInfoLabel, BorderLayout.CENTER);
        centerPane.add(otherInfoLabel, BorderLayout.SOUTH);

        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        
        return centerPane;
    }
    
    private JToolBar createFeaturePane(String feature) {
        JToolBar featurePane = new JToolBar();
        featurePane.setOpaque(false);
        featurePane.setFloatable(false);
        featurePane.setRollover(true);
        featurePane.setLayout(new BoxLayout(featurePane,
                BoxLayout.X_AXIS));
        featurePane.setBorder(null);
        featurePane.add(featureButton);
        setFeatureButtonStatus(feature);
        
        return featurePane;
    }
    
    /**
     * Sets the status of the feature button.
     * If <code>feature</code> is non-null,
     * the <code>featureButton</code> is
     * enabled and binded with a specified command.
     * Otherwise, the button is disabled and displays
     * another icon.
     * @param feature the feature command string
     */
    private void setFeatureButtonStatus(final String feature) {
        Icon icon = null;
        featureButton.setMargin(new Insets(0, 0, 0, 0));
        featureButton.setContentAreaFilled(false);
        if (feature != null) {
            icon = new ImageIcon(this.getClass().getResource(
                    UIResources.getString("featureOnIcon")));//$NON-NLS-1$
            featureButton.setEnabled(true);
            featureButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println(feature);
                }
            });
            featureButton.setToolTipText(feature);
        }
        else {
            icon = new ImageIcon(this.getClass().getResource(
                    UIResources.getString("featureOffIcon")));//$NON-NLS-1$
            featureButton.setEnabled(false);
            featureButton.setToolTipText(UIResources.getString("nofeatureText"));//$NON-NLS-1$
        }
        featureButton.setIcon(icon);
    }

    public int getIndexInList() {
        return indexInList;
    }

    public void setIndexInList(int indexInList) {
        this.indexInList = indexInList;
    }
}
