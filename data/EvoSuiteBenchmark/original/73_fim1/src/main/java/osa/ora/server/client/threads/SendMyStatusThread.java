/*
 * SendMyStatusThread.java
 *
 * Created on November 2, 2009, 11:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.client.threads;

import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import osa.ora.server.client.*;

/**
 *
 * @author ooransa
 */
public class SendMyStatusThread extends Thread {
    ChatClientApp chatApp;
    int action;
    public static final int SIGN_OUT=0;
    public static final int SIGN_OUT_AND_EXIT=1;
    public static final int UPDATE_STATUS=2;

    /** Creates a new instance of SendMyStatusThread */
    public SendMyStatusThread(ChatClientApp chatApp, int actionNeeded) {
        this.chatApp=chatApp;
        this.action=actionNeeded;
    }
    
    public void run(){
            if(action==UPDATE_STATUS){
                try{
                    chatApp.getServerInterface().updateUserStatus(chatApp.getUser());
                    chatApp.getChatSysTray().updateMenuOptions();
                } catch (RemoteException ex1) {
                    //if change status throws exception than also sign from the GUI , server may be down!
                    chatApp.getChatSysTray().logOut();
                    //adjust UI again
                    chatApp.showLoginAgain();
                    chatApp.setUser(null);
                    JOptionPane.showMessageDialog(chatApp.getChatClientFrame(),"Connection Error with the server!","Connection Error",JOptionPane.ERROR_MESSAGE,chatApp.getErrorIcon());
                }
            }else{
                try{
                    //sign off any way from the GUI , server may be down!
                    chatApp.getChatSysTray().logOut();
                    //adjust UI again
                    chatApp.showLoginAgain();
                    chatApp.getServerInterface().signOut(chatApp.getUser());
                    chatApp.setUser(null);
                } catch (RemoteException ex1) {
                    if(action==SIGN_OUT){
                        JOptionPane.showMessageDialog(chatApp.getChatClientFrame(),"Connection Error with the server!","Connection Error",JOptionPane.ERROR_MESSAGE,chatApp.getErrorIcon());
                    }
                }
            }
            if(action==SIGN_OUT_AND_EXIT){
                System.exit(0);
            }
    }    
}
