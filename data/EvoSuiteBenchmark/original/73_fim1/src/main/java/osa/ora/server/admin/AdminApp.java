/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.server.admin;

import osa.ora.server.*;
import com.birosoft.liquid.LiquidLookAndFeel;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import osa.ora.server.admin.ui.AdminLoginPanel;
import osa.ora.server.admin.ui.ControlPanel;
import osa.ora.server.beans.AdminSettingBean;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.IConstant;
import osa.ora.server.beans.LoginBean;
import osa.ora.server.beans.ResultBean;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.User;
import osa.ora.server.utils.StringEncoder64;
import osa.ora.server.utils.StringEncrypter;

/**
 *
 * @author ooransa
 */
public class AdminApp {

    private ServerAdminInterface serverAdminInterface;
    private Vector<Group> groups;
    private Vector<Room> rooms;
    private String lookString = "com.birosoft.liquid.LiquidLookAndFeel";
    private User user;
    private JFrame chatAdminFrame;
    private JDialog loginDialog;
    private static String path = "";
    private AdminSettingBean adminSettingBean;
    private ControlPanel controlPanel;
    private ImageIcon errorIcon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/error.png")));
    private Hashtable<Integer, String> connectedClientsIPs;
    private int securityMode;
    private String rootNode;
    private String authToken;
    private String securityToke;
    private StringEncrypter stringEnc;
    /**
     * main method for admin
     * @param args
     */
    public static void main(String[] args) {
        try {
            AdminApp adminApp = new AdminApp();
        } catch (RemoteException ex) {
            ex.printStackTrace();
            System.out.println("Error in Running!");
        }
    }

    public AdminApp() throws RemoteException {
        connectedClientsIPs = new Hashtable<Integer, String>(0);
        groups = new Vector<Group>(0);
        rooms = new Vector<Room>(0);
        try {
            path = AdminApp.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        path = path.substring(0, path.lastIndexOf('/') + 1);
        adminSettingBean = new AdminSettingBean(path);
        try {
            if (lookString instanceof String) {
                LiquidLookAndFeel.setLiquidDecorations(true);
                UIManager.setLookAndFeel((String) lookString);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in applying look and feel!!");
        }
        chatAdminFrame = new JFrame();
        chatAdminFrame.setTitle("Free Instant Messenger - Admin Interface");
        chatAdminFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/groups.png")));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        chatAdminFrame.setSize(580, 455);
        Dimension frameSize = chatAdminFrame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        chatAdminFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        chatAdminFrame.setResizable(false);
        chatAdminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginDialog = new JDialog(getChatAdminFrame(), "Login", true);
        AdminLoginPanel loginPanel = new AdminLoginPanel(this);
        loginDialog.setSize(300, 150);
        //loginDialog.setUndecorated(true);
        loginDialog.setLocationRelativeTo(chatAdminFrame);
        loginDialog.setAlwaysOnTop(true);
        loginDialog.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        loginDialog.setResizable(false);
        loginDialog.getContentPane().add(loginPanel);
        loginDialog.setVisible(true);
    }

    public void initServer() throws Exception {
        //lookup for the server
        Registry reg = null;
        reg = LocateRegistry.getRegistry(adminSettingBean.getServerURL(), Integer.parseInt(adminSettingBean.getServerPort()));
        System.out.println("trying to connect to the FIM server .....");
        try {
            serverAdminInterface = (ServerAdminInterface) reg.lookup("ModernChatServer");
            System.out.println("connected to the FIM server .....");
        } catch (RemoteException ex) {
            //ex.printStackTrace();
            throw new Exception("Can't find this FIM Server!");
        } catch (NotBoundException ex) {
            //ex.printStackTrace();
            throw new Exception("Server invalid IP/Port or FIM Server not running!");
        }
    }

    public void login(String userEmail, String password) throws Exception {
        if (serverAdminInterface == null) {
            initServer();
        }
        stringEnc=StringEncrypter.getInstance(password);
        LoginBean loginBean= serverAdminInterface.signInAsAdmin(StringEncoder64.encodeStringUTF8(userEmail), stringEnc.encrypt(password));
        if (loginBean == null) {
            throw new Exception("Invalid User Email or Password!");
        } else {
            authToken=loginBean.getAuthToken();
            securityToke=stringEnc.decrypt(loginBean.getSecureToken());
            stringEnc=StringEncrypter.getInstance(securityToke);
            user=loginBean.getUser();
            loginDialog.setVisible(false);
            controlPanel = new ControlPanel(this);
            getChatAdminFrame().getContentPane().add(controlPanel);
            getChatAdminFrame().setVisible(true);
            try {
                securityMode = serverAdminInterface.getSecurityMode(authToken);
                rootNode = serverAdminInterface.getRootNode(authToken);
                setGroups(loadGroups());
            } catch (RemoteException ex) {
                ex.printStackTrace();
                throw new Exception("Error During Getting All Groups From FIM Server!");
            }
            try {
                setRooms(serverAdminInterface.loadRooms(authToken));
            } catch (RemoteException ex) {
                ex.printStackTrace();
                throw new Exception("Error During Getting All Rooms From FIM Server!");
            }
        }
    }

    public Vector<Group> loadGroups() throws RemoteException {
        groups = serverAdminInterface.loadGroupsAndUsers(authToken);
        return groups;
    }

    public String changePassword(String oldPass, String newPass) {
        ResultBean result = null;
        try {
            result = serverAdminInterface.changeAdminPassword(StringEncoder64.encodeStringUTF8(user.getEmail()), stringEnc.encrypt(oldPass), stringEnc.encrypt(newPass));
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
        if (result.isSuccess()) {
            return "New Password Applied Successfully!";
        } else {
            if (result.getAction() == IConstant.ERROR) {
                //set user status in the tab and list as offline !
                return "Error: " + result.getMessage();
            } else {
                return "Failed to Change Password!";
            }
        }
    }

    /**
     * @return the errorIcon
     */
    public ImageIcon getErrorIcon() {
        return errorIcon;
    }

    /**
     * @return the loginDialog
     */
    public JDialog getLoginDialog() {
        return loginDialog;
    }

    /**
     * @return the chatAdminFrame
     */
    public JFrame getChatAdminFrame() {
        return chatAdminFrame;
    }

    /**
     * @return the groups
     */
    public Vector<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(Vector<Group> groups) {
        this.groups = groups;
    }

    /**
     * @return the rooms
     */
    public Vector<Room> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(Vector<Room> rooms) {
        this.rooms = rooms;
    }

    public User createUser(User user) throws RemoteException {
        return serverAdminInterface.createUser(user,authToken);
    }

    public Group createGroup(Group group) throws RemoteException {
        return serverAdminInterface.createGroup(group,authToken);
    }

    public Room createRoom(Room room) throws RemoteException {
        return serverAdminInterface.createRoom(room,authToken);
    }

    public User delUser(User user) throws RemoteException {
        return serverAdminInterface.delUser(user,authToken);
    }

    public Group delGroup(Group group) throws RemoteException {
        return serverAdminInterface.delGroup(group,authToken);
    }

    public Room delRoom(Room room) throws RemoteException {
        return serverAdminInterface.delRoom(room,authToken);
    }

    public User updateUser(User user) throws RemoteException {
        return serverAdminInterface.updateUser(user,authToken);
    }

    public User updateUserGroup(User user) throws RemoteException {
        return serverAdminInterface.updateUserGroup(user,authToken);
    }

    public Group updateGroup(Group group) throws RemoteException {
        return serverAdminInterface.updateGroup(group,authToken);
    }

    public Room updateRoom(Room room) throws RemoteException {
        return serverAdminInterface.updateRoom(room,authToken);
    }

    public boolean setNewDefaultPassword(String newPass) throws RemoteException {
        return serverAdminInterface.setNewDefaultPassword(stringEnc.encrypt(newPass),authToken);
    }

    public void shutdown(String justification) throws RemoteException {
        serverAdminInterface.ping();
        try{
            serverAdminInterface.shutdownServer(justification,authToken);
        }catch(Throwable t){
            //nothing to done
        }
    }

    public void kickOffUsers(String justification) throws RemoteException {
        serverAdminInterface.kickOffUsers(justification,authToken);
    }

    public void kickOffUser(int userId) throws RemoteException {
        serverAdminInterface.kickOffUser(userId,authToken);
    }

    public boolean ping() throws Exception {
        try {
            return serverAdminInterface.ping();
        } catch (Exception ex) {
            try {
                initServer();
                return serverAdminInterface.ping();
            } catch (Exception ex1) {
                throw ex1;
            }
        }
    }

    public User resetUserPass(User user) throws RemoteException {
        return serverAdminInterface.resetUserPass(user,authToken);
    }

    public boolean setLogLevel(int level) throws RemoteException {
        return serverAdminInterface.setLogLevel(level, true,authToken);
    }

    /**
     * @return the connectedClientsIPs
     */
    public Hashtable<Integer, String> getConnectedClientsIPs() {
        return connectedClientsIPs;
    }

    public void returnOnlineIPs() throws RemoteException {
        connectedClientsIPs = serverAdminInterface.returnOnlineIPs(authToken);
    }

    public int setSecurityLevel(int level) throws RemoteException {
        securityMode=level;
        return serverAdminInterface.setSecurityMode(level,authToken);
    }

    public int getLogLevel() throws RemoteException {
        return serverAdminInterface.getLogLevel(authToken);
    }

    public int getSecurityLevel() throws RemoteException {
        return serverAdminInterface.getSecurityMode(authToken);
    }

    public boolean sendGlobalTextAnn(String msg) throws RemoteException {
        if (securityMode == 0) {
            return serverAdminInterface.sendGlobalTextAnn(msg,authToken);
        } else {
            msg=stringEnc.encrypt(msg);
            return serverAdminInterface.sendGlobalSecureTextAnn(msg,authToken);
        }
    }
    public boolean setNewRootNode(String rootNode) throws RemoteException{
        this.rootNode=rootNode;
        return serverAdminInterface.setRootNode(rootNode,authToken);
    }
    public boolean refreshContactList() throws RemoteException{
        return serverAdminInterface.refreshContactList(authToken);
    }

    /**
     * @return the rootNode
     */
    public String getRootNode() {
        return rootNode;
    }

    /**
     * @return the adminSettingBean
     */
    public AdminSettingBean getAdminSettingBean() {
        return adminSettingBean;
    }
}
