/*
 * SendUserUpdatedStatusThread.java
 *
 * Created on November 2, 2009, 11:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.threads;

import osa.ora.server.beans.User;
import osa.ora.server.client.*;

/**
 *
 * @author ooransa
 */
public class SendUserUpdatedStatusThread extends Thread {
    ClientInterface cf;
    User updatedUser;

    /** Creates a new instance of SendUserUpdatedStatusThread */
    public SendUserUpdatedStatusThread(ClientInterface cf,User updatedUser) {
        this.cf=cf;
        this.updatedUser=updatedUser;
    }
    
    public void run(){
        try{
            cf.receiveUpdatedUserStatus(updatedUser);
        }catch(Throwable a){
            //consume it no need to do any thing in this
        }
    }
    
}
