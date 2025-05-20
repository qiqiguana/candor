/*
 * ReceiveAnnouncementThread.java
 *
 * Created on November 2, 2009, 11:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.client.threads;

import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import osa.ora.server.beans.IConstant;
import osa.ora.server.beans.TextMessage;
import osa.ora.server.client.*;

/**
 *
 * @author ooransa
 */
public class SendAnnouncementThread extends Thread {
    TextMessage textMessage;
    ChatClientApp chatApp;
    /** Creates a new instance of ReceiveAnnouncementThread */
    public SendAnnouncementThread(ChatClientApp chatApp,TextMessage textMessage) {
        this.textMessage=textMessage;
        this.chatApp=chatApp;
    }
    
    public void run(){
        boolean result=false;
        try {
            if(chatApp.getSecurityMode()==0){
                result=chatApp.getServerInterface().sendTextAnnouncement(textMessage);
            }else{
                textMessage.setMessage(chatApp.getStringEnc().encrypt(textMessage.getMessage()));
                textMessage.setTitle(chatApp.getStringEnc().encrypt(textMessage.getTitle()));
                result=chatApp.getServerInterface().sendSecureTextAnnouncement(textMessage);
            }
            if(!result){
                if(textMessage.getTargetType() == IConstant.USER_CHAT){
                    JOptionPane.showMessageDialog(chatApp.getChatClientFrame(), "Failed to deliver the Announcement!","Error!",JOptionPane.OK_OPTION);
                }else if(textMessage.getTargetType() == IConstant.GROUP_CHAT){
                    JOptionPane.showMessageDialog(chatApp.getChatClientFrame(), "Not All Users received the Announcement!","Error!",JOptionPane.OK_OPTION);
                }else if(textMessage.getTargetType() == IConstant.ROOM_CHAT){
                    JOptionPane.showMessageDialog(chatApp.getChatClientFrame(),  "Not All Users received the Announcement!","Error!",JOptionPane.OK_OPTION);
                }
            }
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(chatApp.getChatClientFrame(), ex.getMessage(),"Error!",JOptionPane.OK_OPTION);
        }
    }
    
}
