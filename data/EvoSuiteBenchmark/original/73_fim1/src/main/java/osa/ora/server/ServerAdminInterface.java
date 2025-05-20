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
import java.util.Hashtable;
import java.util.Vector;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.LoginBean;
import osa.ora.server.beans.ResultBean;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.User;

/**
 *
 * @author ooransa
 */
public interface ServerAdminInterface extends Remote {
    //load data
    public Vector<Group> loadGroupsAndUsers(String authToken) throws RemoteException;
    public Vector<Room> loadRooms(String authToken) throws RemoteException;
    public Hashtable<Integer, String> returnOnlineIPs(String authToken) throws RemoteException;
    //create , update , delete and change password methods
    public String getRootNode(String authToken) throws RemoteException;
    public boolean setRootNode(String rootNode,String authToken) throws RemoteException;
    public User createUser(User user,String authToken) throws RemoteException;
    public Group createGroup(Group group,String authToken) throws RemoteException;
    public Room createRoom(Room room,String authToken) throws RemoteException;
    public User delUser(User user,String authToken) throws RemoteException;
    public Group delGroup(Group group,String authToken) throws RemoteException;
    public Room delRoom(Room room,String authToken) throws RemoteException;
    public User updateUser(User user,String authToken) throws RemoteException;
    public User updateUserGroup(User user,String authToken) throws RemoteException;
    public Group updateGroup(Group group,String authToken) throws RemoteException;
    public Room updateRoom(Room room,String authToken) throws RemoteException;
    public boolean setNewDefaultPassword(String newPass,String authToken) throws RemoteException;
    public User resetUserPass(User user,String authToken) throws RemoteException;
    //sign in methods
    public LoginBean signInAsAdmin(String emailAddr,String password) throws RemoteException;
    public ResultBean changeAdminPassword(String emailAddr,String oldPass,String newPass) throws RemoteException;
    //ping and shutdown server
    public boolean ping() throws RemoteException;
    public boolean refreshContactList(String authToken) throws RemoteException;
    public void shutdownServer(String justification,String authToken) throws RemoteException;
    //kick of user or all users.
    public boolean kickOffUsers(String justification,String authToken) throws RemoteException;
    public boolean kickOffUser(int userId,String authToken) throws RemoteException;
    //log and security methids
    public boolean setLogLevel(int level,boolean save,String authToken) throws RemoteException;
    public int getLogLevel(String authToken) throws RemoteException;
    public int getSecurityMode(String authToken) throws RemoteException;
    public int setSecurityMode(int level,String authToken) throws RemoteException;
    //send announcements
    public boolean sendGlobalTextAnn(String msg,String authToken) throws RemoteException;
    //secure messages methods
    public boolean sendGlobalSecureTextAnn(String msg,String authToken) throws RemoteException;
}
