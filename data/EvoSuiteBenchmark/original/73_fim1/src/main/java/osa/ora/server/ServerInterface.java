/*
 * ServerInterface.java
 *
 * Created on October 27, 2009, 10:40 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server;

import java.rmi.Remote;

/**
 *
 * @author ooransa
 */
public interface ServerInterface extends Remote,ServerAdminInterface,ServerClientInterface {    
}
