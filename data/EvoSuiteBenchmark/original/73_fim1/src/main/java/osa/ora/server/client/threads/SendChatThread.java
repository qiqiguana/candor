/*
 * SendFileThread.java
 *
 * Created on November 1, 2009, 10:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.client.threads;

import osa.ora.server.beans.TextMessage;
import osa.ora.server.client.*;
import osa.ora.server.client.ui.ChatWindowPanel;

/**
 *
 * @author ooransa
 */
public class SendChatThread extends Thread {
    
    ChatWindowPanel parentPanel;
    TextMessage tm;
    ChatClientApp chatApp;
    /** Creates a new instance of SendFileThread */
    public SendChatThread(ChatWindowPanel parentPanel,TextMessage tm,ChatClientApp chatApp) {
        this.parentPanel=parentPanel;
        this.tm=tm;
        this.chatApp=chatApp;
    }
    
    public void run() {
        String result=chatApp.sendTextMessage(tm);
        if(result!=null){
            parentPanel.addTextChat(result);
        }
    }
}
