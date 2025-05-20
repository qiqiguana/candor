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
import java.rmi.RemoteException;
import java.util.Vector;
import osa.ora.server.beans.BinaryMessage;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.LoginBean;
import osa.ora.server.beans.ResultBean;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.TextMessage;
import osa.ora.server.beans.User;
import osa.ora.server.client.ClientInterface;

/**
 *
 * @author ooransa
 */
public interface ServerClientInterface extends Remote {
    //1.sign in methods
    public LoginBean signIn(ClientInterface cf,String emailAddr,String password,String ipAddress) throws RemoteException;
    public void signOut(User user) throws RemoteException;
    public ResultBean changePassword(String email,String oldPass,String newPass) throws RemoteException;
    public int getSecurityMode() throws RemoteException;
    //2.groups, and rooms
    public String getRootNode() throws RemoteException;
    public Vector<Group> loadGroupsAndUsers(String authToken) throws RemoteException;
    public Vector<Room> getMyRooms(User user,String authToken) throws RemoteException;
    //3.messages methods
    public boolean sendTextMessage(TextMessage msg) throws RemoteException;
    public ResultBean sendBinaryMessage(BinaryMessage msg) throws RemoteException;
    public boolean sendTextAnnouncement(TextMessage msg) throws RemoteException;
    //4.secure messages methods
    public boolean sendSecureTextMessage(TextMessage msg) throws RemoteException;
    public ResultBean sendSecureBinaryMessage(BinaryMessage msg) throws RemoteException;
    public boolean sendSecureTextAnnouncement(TextMessage msg) throws RemoteException;
    //5.periodic keep in touch methods
    public void updateUserStatus(User user) throws RemoteException;
    public boolean ping() throws RemoteException;
}
