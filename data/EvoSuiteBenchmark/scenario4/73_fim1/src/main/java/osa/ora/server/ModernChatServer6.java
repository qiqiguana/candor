package osa.ora.server;

import java.net.URISyntaxException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;
import osa.ora.server.bd.UsersBD;
import osa.ora.server.beans.BinaryMessage;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.IConstant;
import osa.ora.server.beans.LoginBean;
import osa.ora.server.beans.ResultBean;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.ServerSettingBean;
import osa.ora.server.beans.TextMessage;
import osa.ora.server.beans.User;
import osa.ora.server.client.ClientInterface;
import osa.ora.server.threads.SendKickOffByLoginMessageThread;
import osa.ora.server.threads.SendKickOffMessageThread;
import osa.ora.server.threads.SendRefreshContactThread;
import osa.ora.server.threads.SendUserUpdatedStatusThread;
import osa.ora.server.utils.StringEncoder64;
import osa.ora.server.utils.StringEncrypter;

/**
 * @author ooransa
 * Class implements 2 interfaces:
 * 1.Runnable for ping users thread : that ping users periodically to check if they are still connected or not.
 * 2.ServerInterface that extends 2 other interfaces : Admin Interface and Client Interface , both used
 * As the RMI view of the server for the connected client.
 */
public class ModernChatServer implements ServerInterface, Runnable {

    //Logger object
    private static Logger logger = Logger.getLogger("ModernChatServer");

    private static FileHandler fh;

    private String authToken;

    private String clientAuthToken;

    private String secToken;

    private StringEncrypter passwordEnc;

    /**
     * @return the logger
     */
    public static Logger getLogger();

    //password, groups and rooms hashtable
    private Hashtable<Integer, String> passwords;

    private Vector<Group> groups;

    private Vector<Room> rooms;

    //admin user object
    private User adminUser;

    //connected clients connections
    private Hashtable<Integer, ClientInterface> connectedClients;

    private Hashtable<Integer, String> connectedClientsIPs;

    //Business Deligate to load all data.
    private UsersBD userBD;

    private Thread checkupThread;

    private boolean serverRunning = false;

    //current run path
    private String path = "/";

    //security securityMode
    private int securityMode = 0;

    //server setting bean
    private ServerSettingBean serverSettingBean;

    //main method
    public static void main(String[] args);

    /**
     * private method to start the RMI registry
     * @throws Exception
     */
    private void startRMIRegistry() throws Exception;

    /**
     * Creates a new instance of ModernChatServer
     */
    public ModernChatServer() {
    }

    /**
     * any clinet ping it should receive true
     * @return true always
     */
    public boolean ping();

    /**
     * Method to sing in ..
     * @param cf : user client interface to communicate with the client
     * @param emailAddr : user email
     * @param password : user password
     * @param ipAddress : user ip address
     * @return User object if authentication correctly , or null if not exist.
     * @throws RemoteException
     */
    public LoginBean signIn(ClientInterface cf, String emailAddr, String password, String ipAddress) throws RemoteException;

    /**
     * method to sign out
     * @param user
     * @throws RemoteException
     */
    public void signOut(User user) throws RemoteException;

    /**
     * change user password
     * @param email : user email
     * @param oldPass : current password
     * @param newPass : new password
     * @return ResultBean with either true or false
     * @throws RemoteException
     */
    public ResultBean changePassword(String email, String oldPass, String newPass) throws RemoteException;

    /**
     * Change admin password
     * @param emailAddr : email of the admin
     * @param oldPass   : current password
     * @param newPass   : new password
     * @return ResultBean : return status
     * @throws RemoteException
     */
    public ResultBean changeAdminPassword(String emailAddr, String oldPass, String newPass) throws RemoteException;

    /**
     * load groups and users to admin user
     * @return All Groups with there users
     * @throws RemoteException
     */
    public Vector<Group> loadGroupsAndUsers(String authToken) throws RemoteException;

    /**
     * Send text message between users
     * @param msg
     * @return true/false if message delivered or not.
     * @throws RemoteException
     */
    public boolean sendTextMessage(TextMessage msg) throws RemoteException;

    /**
     * private method to send text message to a user
     * @param msg : the message to be send
     * @param cf  : the client interface of the user
     * @return boolean : true/false if the text message send or not.
     */
    private boolean sendTextMessageToUser(TextMessage msg, ClientInterface cf);

    /**
     * Send text message but securly (i.e. encrypted)
     * @param msg : the message to be send
     * @return boolean : true/false if the text message send or not.
     * @throws RemoteException
     */
    public boolean sendSecureTextMessage(TextMessage msg) throws RemoteException;

    /**
     * private method to send secure text message to a user (encrypted)
     * @param msg : the message to be send
     * @param cf  : the client interface of the user
     * @return boolean : true/false if the text message send or not.
     */
    private boolean sendSecureTextMessageToUser(TextMessage msg, ClientInterface cf);

    /**
     * Send Binary message (files)
     * @param msg : the message to be send
     * @return ResultBean include the status of message send or not.
     * @throws RemoteException
     */
    public ResultBean sendBinaryMessage(BinaryMessage msg) throws RemoteException;

    /**
     * Send Binary message (files) but secure (files not secure)
     * @param msg : the message to be send
     * @return ResultBean include the status of message send or not.
     * @throws RemoteException
     */
    public ResultBean sendSecureBinaryMessage(BinaryMessage msg) throws RemoteException;

    public boolean sendTextAnnouncement(TextMessage msg) throws RemoteException;

    /**
     * private method to send announcemnt
     * @param msg : the announcemnt to send
     * @param cf  : the user client interface.
     */
    private boolean sendTextAnnouncementToUser(TextMessage msg, ClientInterface cf) throws RemoteException;

    /**
     * Send announcemnt but securly (encrypted)
     * @param msg : the announcemnt to be send
     * @throws RemoteException
     */
    public boolean sendSecureTextAnnouncement(TextMessage msg) throws RemoteException;

    /**
     * private method to send announcemnt securly (encrypted)
     * @param msg : the announcemnt to send
     * @param cf  : the user client interface.
     */
    private boolean sendSecureTextAnnouncementToUser(TextMessage msg, ClientInterface cf) throws RemoteException;

    /**
     * Method used to update user status
     * @param updatedUser : the user with the new method included.
     * @throws RemoteException
     */
    public void updateUserStatus(User updatedUser) throws RemoteException;

    /**
     * return user rooms
     * @param user
     * @return : Vector of rooms where this user is memeber of them.
     * @throws RemoteException
     */
    public Vector<Room> getMyRooms(User user, String authToken) throws RemoteException;

    /**
     * Run method to periodic ping all user to check if any user lost the connection with the server
     * so the server offline his/her status.
     * It run each 5 minutes.
     */
    public void run();

    /**
     * private method to offline user status and update other users with the user new status (offline)
     * @param userId
     * @throws RemoteException
     */
    private void offlineStatus(int userId) throws RemoteException;

    /**
     * private method to offline user status and NOT update other users with the user new status (offline)
     * Typically used when shutdown the server, don't care about informing users with the new status.
     * @param userId
     * @throws RemoteException
     */
    private void offlineStatusAndNoUpdate(int userId) throws RemoteException;

    /**
     * private method to authenticate the user and return its full detailed bean.
     * @param emailAddr : email of the user
     * @param password  : password of the user.
     * @return User or Null according to the authentication results.
     */
    private User authenticateUser(String emailAddr, String password);

    /**
     * @return the groups
     */
    public Vector<Group> getGroups();

    /**
     * @return the rooms
     */
    public Vector<Room> getRooms();

    /**
     * public method to create new user , used by the admin user
     * @param user : the user details
     * @return User : with the user Id included or null if failed to create it.
     * @throws RemoteException
     */
    public User createUser(User user, String authToken) throws RemoteException;

    public Group createGroup(Group group, String authToken) throws RemoteException;

    /**
     * public method to create new room , used by the admin user
     * @param room : the room details
     * @return Room : with room id or null if failed to create it.
     * @throws RemoteException
     */
    public Room createRoom(Room room, String authToken) throws RemoteException;

    /**
     * method to delete user
     * @param user : to be deleted
     * @return User
     * @throws RemoteException
     */
    public User delUser(User user, String authToken) throws RemoteException;

    public Group delGroup(Group group, String authToken) throws RemoteException;

    public Room delRoom(Room room, String authToken) throws RemoteException;

    /**
     * method to update user
     * @param user to be updated
     * @return User after updated or null if failed to update it
     * @throws RemoteException
     */
    public User updateUser(User user, String authToken) throws RemoteException;

    /**
     * method to update Group
     * @param Group to be updated
     * @return Group after updated or null if failed to update it
     * @throws RemoteException
     */
    public Group updateGroup(Group group, String authToken) throws RemoteException;

    /**
     * method to update Room
     * @param Room to be updated
     * @return Room after updated or null if failed to update it
     * @throws RemoteException
     */
    public Room updateRoom(Room room, String authToken) throws RemoteException;

    /**
     * public method to shutdown the server, it will include kick off of all users.
     * @throws RemoteException
     */
    public void shutdownServer(String justification, String authToken) throws RemoteException;

    /**
     * public method to kick of all users
     * @return boolean true after kicking off all users
     * @throws RemoteException
     */
    public boolean kickOffUsers(String justification, String authToken) throws RemoteException;

    /**
     * return all rooms
     * @return : Vector of all rooms
     * @throws RemoteException
     */
    public Vector<Room> loadRooms(String authToken) throws RemoteException;

    /**
     * method used for sign in by the admin user
     * @param emailAddr : email of the admin user
     * @param password  : password of the admin user.
     * @return User either adminUser object or null if authentication failed.
     * @throws RemoteException
     */
    public LoginBean signInAsAdmin(String emailAddr, String password) throws RemoteException;

    /**
     * public method to set a new default password for newly created users
     * @param newPass : the new default password.
     * @return true after set the new default password.
     * @throws RemoteException
     */
    public boolean setNewDefaultPassword(String newPass, String authToken) throws RemoteException;

    /**
     * public method to reset user password , used by the admin user to reset user password.
     * @param user : to reset its password.
     * @return User or null if failed to reset his/her password.
     * @throws RemoteException
     */
    public User resetUserPass(User user, String authToken) throws RemoteException;

    /**
     * @return the serverSettingBean
     */
    public ServerSettingBean getServerSettingBean();

    /**
     * public method to move user from a group into another group
     * @param user : to be moved included the group id of the new group
     * @return User or null if failed to move this user.
     * @throws RemoteException
     */
    public User updateUserGroup(User user, String authToken) throws RemoteException;

    /**
     * public method to set the log level of the server.
     * @param level integer from 0 - 5 (0 = no logging , 5 = All)
     * @param saveValue , save the log level or just set it without saving it.
     * @return true after set it.
     * @throws RemoteException
     */
    public boolean setLogLevel(int level, boolean saveValue, String authToken) throws RemoteException;

    /**
     * public method to return online users ips
     * @return Hashtable of the online users ips
     * @throws RemoteException
     */
    public Hashtable<Integer, String> returnOnlineIPs(String authToken) throws RemoteException;

    /**
     * public method to get security mode
     * @return int the security level.
     * @throws RemoteException
     */
    public int getSecurityMode(String authToken) throws RemoteException;

    /**
     * public method to kick off a user
     * @param userId : of the user to be kicked off
     * @return true when the user kicked off
     * @throws RemoteException
     */
    public boolean kickOffUser(int userId, String authToken) throws RemoteException;

    /**
     * public method to set the security level
     * @param level : either : 0= encrypt email/password, 1=encrypt titles, 2=encrypt also files.
     * @return int of the security level after set the level
     * @throws RemoteException
     */
    public int setSecurityMode(int level, String authToken) throws RemoteException;

    /**
     * public method to get log level
     * @return int of the got level
     * @throws RemoteException
     */
    public int getLogLevel(String authToken) throws RemoteException;

    /**
     * @return the adminUser
     */
    public User getAdminUser();

    public String getRootNode(String authToken) throws RemoteException;

    public boolean setRootNode(String rootNode, String authToken) throws RemoteException;

    public boolean sendGlobalTextAnn(String msg, String authToken) throws RemoteException;

    public boolean sendGlobalSecureTextAnn(String msg, String authToken) throws RemoteException;

    public int getSecurityMode() throws RemoteException;

    public String getRootNode() throws RemoteException;

    public boolean refreshContactList(String authToken) throws RemoteException;

    /**
     * @return the passwordEnc
     */
    public StringEncrypter getPasswordEnc();
}
