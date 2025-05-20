/*
 * SendUserUpdatedStatusThread.java
 *
 * Created on November 2, 2009, 11:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.threads;

import java.util.Vector;
import osa.ora.server.beans.Group;
import osa.ora.server.client.*;

/**
 *
 * @author ooransa
 */
public class SendRefreshContactThread extends Thread {
    ClientInterface cf;
    Vector<Group> groups;
    
    /** Creates a new instance of SendUserUpdatedStatusThread */
    public SendRefreshContactThread(ClientInterface cf,Vector<Group> groups) {
        this.cf=cf;
        this.groups=groups;
    }
    
    public void run(){
        try{
            cf.refreshGroupsAndUsers(groups);
        }catch(Throwable a){
            //consume it no need to do any thing in this
        }
    }
    
}
