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
public class SendKickOffByLoginMessageThread extends Thread {
    ClientInterface cf;
    boolean sameMachine;
    /** Creates a new instance of SendUserUpdatedStatusThread */
    public SendKickOffByLoginMessageThread(ClientInterface cf,boolean sameMachine) {
        this.cf=cf;
        this.sameMachine=sameMachine;
    }
    
    public void run(){
        try{
            cf.kickOffByLogin(sameMachine);
        }catch(Throwable a){
            //consume it no need to do any thing in this
        }
    }
    
}
