/*
 * PingServerThread.java
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
public class PingServerThread extends Thread {

    ChatClientApp chatApp;

    /** Creates a new instance of PingServerThread */
    public PingServerThread(ChatClientApp chatApp) {
        this.chatApp = chatApp;
    }

    public void run() {
        while (chatApp.getUser() != null) {
            try {
                //sleep for 5 min as we are just runed!
                try {
                    sleep(1000 * 60 * 5);
                } catch (Exception e) {
                }
                //ping
                chatApp.getServerInterface().ping();
            } catch (Throwable ex) {
                //if exception ,end the thread now!
                chatApp.getChatSysTray().logOut();
                //adjust UI again
                chatApp.showLoginAgain();
                chatApp.setUser(null);
                JOptionPane.showMessageDialog(chatApp.getChatClientFrame(),"Connection Lost with the server!","Connection Error",JOptionPane.ERROR_MESSAGE,chatApp.getErrorIcon());
            }
        }
    }
}
