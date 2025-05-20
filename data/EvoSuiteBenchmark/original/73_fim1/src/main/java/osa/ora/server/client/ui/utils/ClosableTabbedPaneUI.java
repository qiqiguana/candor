/*
 * ClosableTabbedPaneUI.java
 *
 * Created on October 29, 2009, 5:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.client.ui.utils;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.*;
import osa.ora.server.client.*;
/**
 *
 * @author ooransa
 */
public  class ClosableTabbedPaneUI extends BasicTabbedPaneUI{
    //override to return our layoutmanager
    ChatClientApp chatApp;
    public ClosableTabbedPaneUI(ChatClientApp chatApp){
        this.chatApp=chatApp;
    }
    protected LayoutManager createLayoutManager(){        
        return new TestPlafLayout();
    }
    //add 40 to the tab size to allow room for the close button and 8 to the height
    protected Insets getTabInsets(int tabPlacement,int tabIndex){
        //note that the insets that are returned to us are not copies.
        Insets defaultInsets = (Insets)super.getTabInsets(tabPlacement,tabIndex).clone();
        defaultInsets.right += 40;
        defaultInsets.top += 4;
        defaultInsets.bottom += 4;
        return defaultInsets;
    }
    class TestPlafLayout extends TabbedPaneLayout {
        //a list of our close buttons
        java.util.ArrayList closeButtons = new java.util.ArrayList();
        public void layoutContainer(Container parent){
            super.layoutContainer(parent);
            //ensure that there are at least as many close buttons as tabs
            while(tabPane.getTabCount() > closeButtons.size()){
                closeButtons.add(new CloseButton(closeButtons.size()));
            }
            Rectangle rect = new Rectangle();
            int i;
            //start loop from 1 to exclude 1st tab from having this close button
            for(i = 1; i < tabPane.getTabCount();i++){
                rect = getTabBounds(i,rect);
                JButton closeButton = (JButton)closeButtons.get(i);
                //shift the close button 3 down from the top of the pane and 20 to the left
                closeButton.setLocation(rect.x+rect.width-20,rect.y+5);
                closeButton.setSize(15,15);
                tabPane.add(closeButton);
            }
            for(;i < closeButtons.size();i++){
                //remove any extra close buttons
                tabPane.remove((JButton)closeButtons.get(i));
            }
        }
        // implement UIResource so that when we add this button to the
        // tabbedpane, it doesn't try to make a tab for it!
        class CloseButton extends JButton implements javax.swing.plaf.UIResource{
            public CloseButton(int index){
                super(new CloseButtonAction(index));
                setToolTipText("Close This Tab");
                //remove the typical padding for the button
                setMargin(new Insets(0,0,0,0));
                //no need as use icon instead of x text
                /*addMouseListener(new MouseAdapter(){
                    public void mouseEntered(MouseEvent e){
                        setForeground(new Color(255,0,0));
                    }
                    public void mouseExited(MouseEvent e){
                        setForeground(new Color(0,0,0));
                    }
                });*/
            }
        }
        class CloseButtonAction extends AbstractAction{
            int index;
            public CloseButtonAction(int index){
                super();//"x");
                putValue(AbstractAction.SMALL_ICON,new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/close.jpg"))));
                this.index = index;
            }
            public void actionPerformed(ActionEvent e){
                if(index>0) //tabPane.remove(index);
                if(index>0) ClosableTabbedPaneUI.this.chatApp.closeChatAtIndex(index);
                        //getOpenChatPanelsByIndex().remove(index);
            }
        }	// End of CloseButtonAction
    }	// End of TestPlafLayout
}	// End of static class ClosableTabbedPaneUI
