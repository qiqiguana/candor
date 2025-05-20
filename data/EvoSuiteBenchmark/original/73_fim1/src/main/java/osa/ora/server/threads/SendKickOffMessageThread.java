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
public class SendKickOffMessageThread extends Thread {
    ClientInterface cf;
    String justification;
    /** Creates a new instance of SendUserUpdatedStatusThread */
    public SendKickOffMessageThread(ClientInterface cf,String justification) {
        this.cf=cf;
        this.justification=justification;
    }
    
    public void run(){
        try{
            cf.kickOffByAdmin(justification);
        }catch(Throwable a){
            //consume it no need to do any thing in this
        }
    }
    
}
