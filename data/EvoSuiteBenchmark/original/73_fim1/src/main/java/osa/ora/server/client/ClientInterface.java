/*
 * ClientInterface.java
 *
 * Created on October 27, 2009, 10:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import osa.ora.server.beans.BinaryMessage;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.ResultBean;
import osa.ora.server.beans.TextMessage;
import osa.ora.server.beans.User;

/**
 *
 * @author ooransa
 */
public interface ClientInterface extends Remote {
        //1.message methods (secure and un-secure)
	public boolean receiveTextMessage(TextMessage msg) throws RemoteException;
        public boolean receiveSecureTextMessage(TextMessage msg) throws RemoteException;
        public ResultBean receiveBinaryMessageRequest(BinaryMessage msg) throws RemoteException;
        public ResultBean receiveSecureBinaryMessageRequest(BinaryMessage msg) throws RemoteException;
        public void receiveBinaryMessageLoad(BinaryMessage msg) throws RemoteException;
        public void receiveSecureBinaryMessageLoad(BinaryMessage msg) throws RemoteException;
        //2.announcement methods
        public void receiveTextAnnouncement(TextMessage msg) throws RemoteException;
        public void receiveSecureTextAnnouncement(TextMessage msg) throws RemoteException;
        //3.online control methods..
        public void receiveUpdatedUserStatus(User user) throws RemoteException;
	public void forcedSignOff() throws RemoteException;
        public void kickOffByAdmin(String justification) throws RemoteException;
        public void kickOffByLogin(boolean sameMachine) throws RemoteException;
        //4.periodic server pings to check user availability.
        public boolean ping() throws RemoteException;
        //5.refresh contact list
        public void refreshGroupsAndUsers(Vector<Group> groups) throws RemoteException;
}
