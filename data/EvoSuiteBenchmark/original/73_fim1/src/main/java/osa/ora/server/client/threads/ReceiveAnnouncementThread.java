/*
 * ReceiveAnnouncementThread.java
 *
 * Created on November 2, 2009, 11:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.client.threads;

import javax.swing.JOptionPane;
import osa.ora.server.beans.TextMessage;
import osa.ora.server.client.*;

/**
 *
 * @author ooransa
 */
public class ReceiveAnnouncementThread extends Thread {
    TextMessage textMessage;
    ChatClientApp chatApp;
    /** Creates a new instance of ReceiveAnnouncementThread */
    public ReceiveAnnouncementThread(ChatClientApp chatApp,TextMessage textMessage) {
        this.textMessage=textMessage;
        this.chatApp=chatApp;
    }
    
    public void run(){
        JOptionPane.showConfirmDialog(chatApp.getChatClientFrame(), textMessage.getTitle()+" : ["+textMessage.getMessage()+"]","Announcement",JOptionPane.CLOSED_OPTION);
    }
    
}
