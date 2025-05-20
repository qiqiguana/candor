/*
 * ChatClientApp.java
 *
 * Created on October 27, 2009, 1:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package osa.ora.server.client;

import com.birosoft.liquid.LiquidLookAndFeel;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import osa.ora.server.beans.BinaryMessage;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.IConstant;
import osa.ora.server.beans.ResultBean;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.Status;
import osa.ora.server.beans.TextMessage;
import osa.ora.server.beans.User;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import osa.ora.server.ServerClientInterface;
import osa.ora.server.beans.ClientSettingBean;
import osa.ora.server.beans.LoginBean;
import osa.ora.server.client.threads.PingServerThread;
import osa.ora.server.client.threads.ReceiveAnnouncementThread;
import osa.ora.server.client.threads.ReceiveFileThread;
import osa.ora.server.client.threads.SendAnnouncementThread;
import osa.ora.server.client.threads.SendMyStatusThread;
import osa.ora.server.client.ui.ChangePassPanel;
import osa.ora.server.client.ui.ChatFrame;
import osa.ora.server.client.ui.ChatSysTray;
import osa.ora.server.client.ui.ChatWindowPanel;
import osa.ora.server.client.ui.ContactsPanel;
import osa.ora.server.client.ui.EmotionPanel;
import osa.ora.server.client.ui.FontPanel;
import osa.ora.server.client.ui.LoginPanel;
import osa.ora.server.client.ui.SettingPanel;
import osa.ora.server.utils.FileEncrypter;
import osa.ora.server.utils.StringEncoder64;
import osa.ora.server.utils.StringEncrypter;

/**
 *
 * @author ooransa
 */
public class ChatClientApp extends UnicastRemoteObject implements ClientInterface, Serializable, Runnable, WindowListener {

    private ServerClientInterface serverInterface;
    private Vector<Group> groups;
    private Vector<Room> rooms;
    private Hashtable<Integer, Status> statusLookup;
    private Hashtable<Integer, ChatWindowPanel> openChatPanels;
    private Hashtable<Integer, ChatWindowPanel> openChatPanelsByIndex;
    private static Hashtable<String, String> emotionImages;
    private String lookString = "com.birosoft.liquid.LiquidLookAndFeel";
    private User user;
    private ChatSysTray chatSysTray;
    private LoginPanel loginPanel;
    private ContactsPanel contactsPanel;
    private ChatFrame chatClientFrame;
    private EmotionPanel emotionPanel;
    private FontPanel fontPanel;
    private JDialog emotionFrame;
    private JDialog fontFrame;
    private JDialog settingsFrame;
    private JDialog changePassFrame;
    private SettingPanel settingPanel;
    private ChangePassPanel changePassPanel;
    private static String path = "";
    private ImageIcon group_icon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/groups.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon room_icon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/room.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    private ImageIcon online_icon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/online.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon offline_icon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/offline.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon busy_icon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/busy.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon away_icon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/away.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon[] userStatusIcons = new ImageIcon[5];
    private ImageIcon errorIcon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/error.png")));
    //private ImageIcon chatIcon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/chat.jpg")));
    private Thread th;
    private int number = 0;
    private String title = "";
    private JFileChooser jfm;
    private AudioClip ac;
    private ClientSettingBean clientSettingBean;
    private InetAddress ownIP;
    private PingServerThread pingServerThread;
    private int securityMode;
    private String rootNode;
    private String securityToke;
    private StringEncrypter stringEnc;
    /** Creates a new instance of ChatClientApp */
    public ChatClientApp() throws RemoteException {
        userStatusIcons[0] = offline_icon;
        userStatusIcons[1] = offline_icon;
        userStatusIcons[2] = online_icon;
        userStatusIcons[3] = busy_icon;
        userStatusIcons[4] = away_icon;
        openChatPanels = new Hashtable<Integer, ChatWindowPanel>();
        openChatPanelsByIndex = new Hashtable<Integer, ChatWindowPanel>();
        try {
            path = ChatClientApp.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        path = getPath().substring(0, getPath().lastIndexOf('/') + 1);
        try{
          ownIP=InetAddress.getLocalHost();
        }catch (Exception e){
        }
        clientSettingBean = new ClientSettingBean(path);
        System.out.println("ServerIP=" + clientSettingBean.getServerURL());
        System.out.println("ServerPort=" + clientSettingBean.getServerPort());
        initSound();
        jfm = new JFileChooser();
        jfm.setMultiSelectionEnabled(false);
        jfm.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfm.setDialogTitle("Select File To Send");
        try {
            if (lookString instanceof String) {
                LiquidLookAndFeel.setLiquidDecorations(true);
                UIManager.setLookAndFeel((String) lookString);
                SwingUtilities.updateComponentTreeUI(jfm);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in applying look and feel!!");
        }
        loginPanel = new LoginPanel(this);
        chatClientFrame = new ChatFrame(this);
        chatClientFrame.getContactPanel().add(loginPanel, java.awt.BorderLayout.CENTER);
        chatClientFrame.setTitle("Free Instant Messenger");
        chatClientFrame.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/groups.png")));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        chatClientFrame.setSize(300, 500);
        Dimension frameSize = chatClientFrame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        chatClientFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        chatClientFrame.repaint();
        chatClientFrame.addWindowListener(this);
        chatClientFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        chatClientFrame.setVisible(true);
        emotionImages = new Hashtable<String, String>();
        emotionImages.put(":)", "smile.png");
        emotionImages.put(":D", "bigSmile.png");
        emotionImages.put(";)", "wink.png");
        emotionImages.put(":|", "shocked.png");
        emotionImages.put(":(", "sad.png");
        emotionImages.put(":S", "confused.png");
        emotionImages.put(":P", "evilSmile.png");
        emotionImages.put(":E", "lol.png");
        emotionImages.put(":@", "angery.png");
        chatSysTray = new ChatSysTray(this);
        emotionFrame = new JDialog(chatClientFrame, false);
        emotionFrame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                emotionFrame.setVisible(false);
            }
        });
        emotionFrame.setSize(100, 120);
        emotionFrame.setAlwaysOnTop(true);
        emotionFrame.setResizable(false);
        emotionFrame.setUndecorated(true);
        emotionPanel = new EmotionPanel(emotionFrame);
        emotionFrame.getContentPane().add(emotionPanel);
        fontFrame = new JDialog(chatClientFrame, "Change Font And Color", true);
        fontPanel = new FontPanel(fontFrame, this);
        fontFrame.setSize(270, 200);
        fontFrame.setAlwaysOnTop(true);
        fontFrame.setResizable(false);
        fontFrame.setLocationRelativeTo(chatClientFrame);
        fontFrame.getContentPane().add(fontPanel);
        settingsFrame = new JDialog(chatClientFrame, "Change Settings", true);
        settingPanel = new SettingPanel(settingsFrame, this);
        settingsFrame.setSize(200, 450);
        settingsFrame.setAlwaysOnTop(true);
        settingsFrame.setResizable(false);
        settingsFrame.getContentPane().add(settingPanel);
        changePassFrame = new JDialog(chatClientFrame, "Change Password", true);
        changePassPanel = new ChangePassPanel(changePassFrame, this);
        changePassFrame.setSize(190, 200);
        changePassFrame.setAlwaysOnTop(true);
        changePassFrame.setResizable(false);
        changePassFrame.getContentPane().add(changePassPanel);
        loadStatusLookup();
        String[] statusList = new String[getStatusLookup().size()-1];
        for (int i = 1; i < getStatusLookup().size(); i++) {
            statusList[i-1] = getStatusLookup().get(i).getDesc();
        }
        settingPanel.updateStatusList(statusList, Integer.parseInt(clientSettingBean.getDefaultStatus()));
    }

    public void initServer() throws Exception {
        //lookup for the server
        Registry reg = null;
        System.out.println("Starting Client .....");
        reg = LocateRegistry.getRegistry(clientSettingBean.getServerURL(), Integer.parseInt(clientSettingBean.getServerPort()));
        System.out.println("trying to connect to the FIM server .....");
        try {
            serverInterface = (ServerClientInterface) reg.lookup("ModernChatServer");
            System.out.println("connected to the FIM server .....");
        } catch (Exception ex) {
            //ex.printStackTrace();
            throw new Exception("Can't find FIM Server at:"+clientSettingBean.getServerURL()+":"+clientSettingBean.getServerPort());
        }
    }

    public void login(String userEmail, String password) throws Exception {
        if (getServerInterface() == null) {
            initServer();
        }
        stringEnc=StringEncrypter.getInstance(password);
        LoginBean loginBean=serverInterface.signIn(this, StringEncoder64.encodeStringUTF8(userEmail), stringEnc.encrypt(password),StringEncoder64.encodeStringUTF8(ownIP.getHostAddress()));
        if (loginBean == null) {
            throw new Exception("Invalid User Email or Password!");
        } else {
            user=loginBean.getUser();
            securityToke=stringEnc.decrypt(loginBean.getSecureToken());
            stringEnc=StringEncrypter.getInstance(securityToke);
            FileEncrypter.getInstanceInit(securityToke.getBytes());
            securityMode=serverInterface.getSecurityMode();
            rootNode=serverInterface.getRootNode();
            getChatClientFrame().setTitle(user.getName());
            chatClientFrame.changeStatusEnabled(true);
            try {
                groups = getServerInterface().loadGroupsAndUsers(loginBean.getAuthToken());
            } catch (RemoteException ex) {
                ex.printStackTrace();
                throw new Exception("Error During Getting User Contacts List From FIM Server!");
            }
            try {
                setRooms(getServerInterface().getMyRooms(user,loginBean.getAuthToken()));
            } catch (RemoteException ex) {
                ex.printStackTrace();
                throw new Exception("Error During Getting User Rooms List From FIM Server!");
            }
            String[] statusList = new String[getStatusLookup().size()];
            for (int i = 0; i < getStatusLookup().size(); i++) {
                statusList[i] = getStatusLookup().get(i).getDesc();
            }
            //default status from setting file
            chatClientFrame.updateStatusList(statusList, Integer.parseInt(clientSettingBean.getDefaultStatus()));
            updateMyStatus(Integer.parseInt(getClientSettingBean().getDefaultStatus()));
            getChatSysTray().login();
            clientSettingBean.setUserEmail(userEmail);
            if("1".equals(clientSettingBean.getRememberPass())){
                clientSettingBean.setPassword(password);
            }
            clientSettingBean.updateSettings();
            pingServerThread = new PingServerThread(this);
            pingServerThread.start();
        }
    }
    public void kickOffByAdmin(String justification) throws RemoteException {
        chatClientFrame.changeStatusEnabled(false);
        settingsFrame.setVisible(false);
        fontFrame.setVisible(false);
        //need to hide it also :)
        //jfm.setAcVisible(false);
        number = 20;
        pingServerThread = null;
        //if change status throws exception than also sign from the GUI , server may be down!
        getChatSysTray().logOut();
        //adjust UI again
        showLoginAgain();
        setUser(null);
        if(justification==null || justification.equals("")){
            JOptionPane.showMessageDialog(getChatClientFrame(),"You have been signed out by the Admin!","Admin Sign Out",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(getChatClientFrame(),"You have been signed out off by the Admin for: "+justification,"Admin Sign Out",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void kickOffByLogin(boolean sameMachine) throws RemoteException {
        chatClientFrame.changeStatusEnabled(false);
        settingsFrame.setVisible(false);
        fontFrame.setVisible(false);
        //need to hide it also :)
        //jfm.setAcVisible(false);
        number = 20;
        pingServerThread = null;
        //if change status throws exception than also sign from the GUI , server may be down!
        getChatSysTray().logOut();
        //adjust UI again
        showLoginAgain();
        setUser(null);
        if(sameMachine){
            JOptionPane.showMessageDialog(getChatClientFrame(),"You have been logged off by login from client in the same machine!","Concurrent Login",JOptionPane.ERROR_MESSAGE,getErrorIcon());
        }else{
            JOptionPane.showMessageDialog(getChatClientFrame(),"You have been logged off by login from client in another machine!","Concurrent Login",JOptionPane.ERROR_MESSAGE,getErrorIcon());
        }
    }

    public void updateMyStatus(int statusNo){
        if (statusNo != 0 && user != null) {
            user.setStatus_id(getStatusLookup().get(statusNo).getId());
            SendMyStatusThread sendMyStatusThread = new SendMyStatusThread(this, SendMyStatusThread.UPDATE_STATUS);
            sendMyStatusThread.start();
        } else {
            chatClientFrame.changeStatusEnabled(false);
            settingsFrame.setVisible(false);
            fontFrame.setVisible(false);
            //need to hide it also :)
            //jfm.setAcVisible(false);
            number = 20;
            pingServerThread = null;
            SendMyStatusThread sendMyStatusThread = new SendMyStatusThread(this, SendMyStatusThread.SIGN_OUT);
            sendMyStatusThread.start();
        }
        System.gc();
    }

    public boolean ping() throws RemoteException {
        return true;
    }

    public void receiveUpdatedUserStatus(User updatedUser) throws RemoteException {
        if (updatedUser.getId() != user.getId()) {
            for (int i = 0; i < groups.size(); i++) {
                if (updatedUser.getGroup_id() == groups.get(i).getId()) {
                    Vector<User> tempUsers = groups.get(i).getUsers();
                    if (tempUsers != null && tempUsers.size() > 0) {
                        for (int n = 0; n < tempUsers.size(); n++) {
                            if (updatedUser.getId() == tempUsers.get(n).getId()) {
                                tempUsers.get(n).setStatus_id(updatedUser.getStatus_id());
                                if (contactsPanel != null) {
                                    contactsPanel.updateContactList();
                                }
                                chatClientFrame.updateFrameIconForUser(updatedUser.getName(), userStatusIcons[updatedUser.getStatus_id()]);
                            }
                        }
                    }
                }
            }
        }
    }
    public void refreshGroupsAndUsers(Vector<Group> groups) throws RemoteException {
        this.groups=groups;
        if (contactsPanel != null) {
            closeAllChat();
            contactsPanel.updateContactList();
            JOptionPane.showMessageDialog(getChatClientFrame(),"Your contact list is refreshed by the Admin!","Contact List Updated",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void openChat(Group group, boolean fileTransfer, File file) {
        //check if group can have a chat room or not
        ChatWindowPanel temp = new ChatWindowPanel(this, group);
        if (getChatClientFrame().addNewFrame(group.getName(), group_icon, temp)) {
            openChatPanels.put(group.getId(), temp);
            getOpenChatPanelsByIndex().put(getChatClientFrame().getParentTabbedPane().getSelectedIndex() - 1, temp);
            if (fileTransfer) {
                if (file == null) {
                    temp.openSendFileDialog();
                } else {
                    temp.sendFileInit(file);
                }
            }
        }
    }

    public void openChat(Room room, boolean fileTransfer, File file) {
        ChatWindowPanel temp = new ChatWindowPanel(this, room);
        if (getChatClientFrame().addNewFrame(room.getName(), room_icon, temp)) {
            openChatPanels.put(room.getId(), temp);
            getOpenChatPanelsByIndex().put(getChatClientFrame().getParentTabbedPane().getSelectedIndex() - 1, temp);
            if (fileTransfer) {
                if (file == null) {
                    temp.openSendFileDialog();
                } else {
                    temp.sendFileInit(file);
                }
            }
        }
    }

    public void openChat(User chatUser, boolean fileTransfer, File file) {
        //check if user can't accept chat or not
        ChatWindowPanel temp = new ChatWindowPanel(this, chatUser);
        //if user status not exist , get it from the groups we have
        for (int i = 0; i < groups.size(); i++) {
            Vector<User> tempUsers = groups.get(i).getUsers();
            if (tempUsers != null && tempUsers.size() > 0) {
                for (int n = 0; n < tempUsers.size(); n++) {
                    if (chatUser.getId() == tempUsers.get(n).getId()) {
                        chatUser.setStatus_id(tempUsers.get(n).getStatus_id());
                        break;
                    }
                }
            }
        }
        if (chatClientFrame.addNewFrame(chatUser.getName(), userStatusIcons[chatUser.getStatus_id()], temp)) {
            openChatPanels.put(chatUser.getId(), temp);
            getOpenChatPanelsByIndex().put(getChatClientFrame().getParentTabbedPane().getSelectedIndex() - 1, temp);
        }
        if (fileTransfer) {
            if (file == null) {
                temp.openSendFileDialog();
            } else {
                temp.sendFileInit(file);
            }
        }
    }

    public void closeAllChat() {
        int n = getChatClientFrame().getParentTabbedPane().getTabCount() - 1;
        for (int i = n; i > 0; i--) {
            getChatClientFrame().removeFrame(i);
        }
        openChatPanels = new Hashtable<Integer, ChatWindowPanel>();
        openChatPanelsByIndex = new Hashtable<Integer, ChatWindowPanel>();
    }

    public void closeChatAtIndex(int index) {
        String name = getChatClientFrame().getParentTabbedPane().getTitleAt(index);
        openChatPanelsByIndex.remove(index);
        getChatClientFrame().removeFrame(index);
        Enumeration en = openChatPanels.elements();
        while (en.hasMoreElements()) {
            ChatWindowPanel obj = (ChatWindowPanel) en.nextElement();
            if (obj.getChat_type() == IConstant.USER_CHAT) {
                if (obj.getUser().getName().equals(name)) {
                    openChatPanels.remove(obj.getUser().getId());
                }
            } else if (obj.getChat_type() == IConstant.GROUP_CHAT) {
                if (obj.getGroup().getName().equals(name)) {
                    openChatPanels.remove(obj.getGroup().getId());
                }
            } else if (obj.getChat_type() == IConstant.ROOM_CHAT) {
                if (obj.getRoom().getName().equals(name)) {
                    openChatPanels.remove(obj.getRoom().getId());
                }
            }
        }
    }
    public String changePassword(String oldPass, String newPass){
        ResultBean result = null;
        oldPass=stringEnc.encrypt(oldPass);
        newPass=stringEnc.encrypt(newPass);
        String email=StringEncoder64.encodeStringUTF8(user.getEmail());
        try {
            result = getServerInterface().changePassword(email, oldPass, newPass);
        } catch (RemoteException ex) {
            return "Error:Failed to Connect To Server!";
        }
        if (result.isSuccess()) {
            if("1".equals(clientSettingBean.getRememberPass())){
                newPass=stringEnc.decrypt(newPass);
                clientSettingBean.setPassword(newPass);
                clientSettingBean.updateSettings();
                loginPanel.updatePassword(newPass);
            }
            return "New Password Applied Successfully!";
        } else {
            if (result.getAction() == IConstant.ERROR) {
                //set user status in the tab and list as offline !
                return result.getMessage();
            } else {
                return "Failed to Change Password!";
            }
        }
    }
    public String sendTextMessage(TextMessage textMessage) {
        textMessage.setFromUserId(user.getId());
        textMessage.setTitle(user.getName());
        boolean result = false;
        try {
            if(getSecurityMode()==0) {
                result = getServerInterface().sendTextMessage(textMessage);
            }else{
                textMessage.setMessage(stringEnc.encrypt(textMessage.getMessage()));
                textMessage.setTitle(stringEnc.encrypt(textMessage.getTitle()));
                result = getServerInterface().sendSecureTextMessage(textMessage);
            }
        } catch (RemoteException ex) {
            return "Error:Failed to Deliver This Message!";
        }
        if (result) {
            return null;
        } else {
            return "Error:Failed to Deliver This Message!";
        }
    }

    public String sendBinaryMessage(BinaryMessage bm) {
        bm.setFromUserId(user.getId());
        bm.setTitle(user.getName());
        ResultBean result = null;
        try {
            if(getSecurityMode()==0){
                result = getServerInterface().sendBinaryMessage(bm);
            }else{
                bm.setDesc(stringEnc.encrypt(bm.getDesc()));
                bm.setTitle(stringEnc.encrypt(bm.getTitle()));
                result = getServerInterface().sendSecureBinaryMessage(bm);
            }
        } catch (RemoteException ex) {
            return "Error:Failed to Deliver This File!";
        }
        if (result.isSuccess()) {
            return null;
        } else {
            if (result.getAction() == IConstant.REJECTED) {
                return "Rejected:File Transfer Declined by the User!";
            }else {
                return "Error:Failed to Deliver This File!";
            }
        }
    }

    public void sendAnnouncement(TextMessage textMessage) {
        textMessage.setFromUserId(user.getId());
        textMessage.setTitle(user.getName());
        SendAnnouncementThread sendThread = new SendAnnouncementThread(this, textMessage);
        sendThread.start();
    }

    public static void main(String[] args) {
        try {
            ChatClientApp clientApp = new ChatClientApp();
        } catch (RemoteException ex) {
            ex.printStackTrace();
            System.out.println("Error in Running!");
        }
    }

    public boolean receiveTextMessage(TextMessage msg) throws RemoteException {
        ChatWindowPanel tempPanel = null;
        if (msg.getTargetType() == IConstant.USER_CHAT) {
            tempPanel = openChatPanels.get(msg.getFromUserId());
            if (tempPanel == null) {
                User temp = new User();
                temp.setId(msg.getFromUserId());
                temp.setName(msg.getTitle());
                //to gurantee user tab is exist.
                openChat(temp, false, null);
                tempPanel = openChatPanels.get(msg.getFromUserId());
            }
        } else if (msg.getTargetType() == IConstant.GROUP_CHAT) {
            tempPanel = openChatPanels.get(msg.getToUserId());
            if (tempPanel == null) {
                Group temp = new Group();
                temp.setId(msg.getToUserId());
                for (int i = 0; i < groups.size(); i++) {
                    if (groups.get(i).getId() == msg.getToUserId()) {
                        temp.setName(groups.get(i).getName());
                    }
                }
                //to gurantee user tab is exist.
                openChat(temp, false, null);
                tempPanel = openChatPanels.get(msg.getToUserId());
            }
        } else if (msg.getTargetType() == IConstant.ROOM_CHAT) {
            tempPanel = openChatPanels.get(msg.getToUserId());
            if (tempPanel == null) {
                Room temp = new Room();
                temp.setId(msg.getToUserId());
                for (int i = 0; i < rooms.size(); i++) {
                    if (rooms.get(i).getId() == msg.getToUserId()) {
                        temp.setName(rooms.get(i).getName());
                    }
                }
                //to gurantee user tab is exist.
                openChat(temp, false, null);
                tempPanel = openChatPanels.get(msg.getToUserId());
            }
        }
        tempPanel.addTextChat(msg);
        title = msg.getTitle();
        startThread();
        return true;
    }

    public ResultBean receiveBinaryMessageRequest(BinaryMessage msg) throws RemoteException {
        ChatWindowPanel tempPanel = openChatPanels.get(msg.getFromUserId());
        if (tempPanel == null) {
            User temp = new User();
            temp.setId(msg.getFromUserId());
            temp.setName(msg.getTitle());
            //to gurantee user tab is exist.
            openChat(temp, false, null);
            tempPanel = openChatPanels.get(msg.getFromUserId());
        }
        title = msg.getTitle();
        startThread();
        tempPanel.addTextChat(msg.getTitle() + " want to send you the file [" + msg.getDesc() + "], Accept?");
        int n = JOptionPane.showConfirmDialog(getChatClientFrame(), msg.getTitle() + " : want to send you the file [" + msg.getDesc() + "], Accept?", "Accept File Transfer", JOptionPane.OK_CANCEL_OPTION);
        if (n == JOptionPane.OK_OPTION) {
            return new ResultBean(true, IConstant.SUCCESS, null);
        } else {
            tempPanel.addTextChat("File transfer rejected!");
            TextMessage tm = new TextMessage();
            tm.setTitle(user.getName());
            tm.setFromUserId(user.getId());
            tm.setMessage("File [" + msg.getDesc() + "] transfer rejected by " + user.getName());
            tm.setToUserId(msg.getFromUserId());
            tm.setTargetType(IConstant.USER_CHAT);
            getServerInterface().sendTextMessage(tm);
            return new ResultBean(false, IConstant.REJECTED, null);
        }
    }

    public void receiveBinaryMessageLoad(BinaryMessage msg) throws RemoteException {
        ChatWindowPanel tempPanel = openChatPanels.get(msg.getFromUserId());
        if (tempPanel == null) {
            User temp = new User();
            temp.setId(msg.getFromUserId());
            temp.setName(msg.getTitle());
            //to gurantee user tab is exist.
            openChat(temp, false, null);
            tempPanel = openChatPanels.get(msg.getFromUserId());
        }
        title = msg.getTitle();
        startThread();
        ReceiveFileThread receiveFileThread = new ReceiveFileThread(tempPanel, msg, this);
        receiveFileThread.start();
    }

    public void receiveTextAnnouncement(TextMessage msg) throws RemoteException {
        ReceiveAnnouncementThread receiveThread = new ReceiveAnnouncementThread(this, msg);
        receiveThread.start();
        title = msg.getTitle();
        startThread();
    }
    public void receiveSecureTextAnnouncement(TextMessage msg) throws RemoteException {
        msg.setMessage(stringEnc.decrypt(msg.getMessage()));
        msg.setTitle(stringEnc.decrypt(msg.getTitle()));
        receiveTextAnnouncement(msg);
    }
    public boolean receiveSecureTextMessage(TextMessage msg) throws RemoteException {
        msg.setMessage(stringEnc.decrypt(msg.getMessage()));
        msg.setTitle(stringEnc.decrypt(msg.getTitle()));
        return receiveTextMessage(msg);
    }
    public ResultBean receiveSecureBinaryMessageRequest(BinaryMessage msg) throws RemoteException {
        msg.setDesc(stringEnc.decrypt(msg.getDesc()));
        msg.setTitle(stringEnc.decrypt(msg.getTitle()));
        return receiveBinaryMessageRequest(msg);
    }
    public void receiveSecureBinaryMessageLoad(BinaryMessage msg) throws RemoteException {
        msg.setDesc(stringEnc.decrypt(msg.getDesc()));
        msg.setTitle(stringEnc.decrypt(msg.getTitle()));
        receiveBinaryMessageLoad(msg);
    }
    private void startThread() {
        if (chatClientFrame.isVisible() == false) {
            getChatSysTray().showMessage(title);
        }
        chatClientFrame.setVisible(true);
        chatClientFrame.toFront();
        chatClientFrame.requestFocusInWindow();
        //don't disturb me if in busy status
        if(user.getStatus_id()!=IConstant.BUSY){
            if (number == 0 || number >= 16) {
                number = 0;
                if(ac!=null) ac.play();
                th = new Thread(this);
                th.start();
            }
        }
    }

    public void showChangePass() {
        changePassFrame.setLocationRelativeTo(settingsFrame);
        changePassPanel.clearFields();
        changePassFrame.setVisible(true);
    }

    public void showSettings() {
        settingsFrame.setLocationRelativeTo(getChatClientFrame());
        settingPanel.updateSettings();
        settingsFrame.setVisible(true);
    }
    public void showLoginAgain() {
        closeAllChat();
        if("1".equals(clientSettingBean.getRememberPass())){
            loginPanel.updatePassword(clientSettingBean.getPassword());
        }else{
            loginPanel.updatePassword("");
        }
        getChatClientFrame().getContactPanel().remove(contactsPanel);
        getChatClientFrame().changeStatusEnabled(false);
        contactsPanel = new ContactsPanel(this);
        getChatClientFrame().getContactPanel().add(loginPanel, java.awt.BorderLayout.CENTER);
        getChatClientFrame().setIconAt(0, new javax.swing.ImageIcon(getClass().getResource("/images/login.gif")));
        getChatClientFrame().setTitleAt(0, "Login");
        getChatClientFrame().setTitle("Free Instant Messenger");
        //getChatClientFrame().setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/groups.png")));
        getChatClientFrame().repaint();
    }

    public void showContactList() {
        getChatClientFrame().getContactPanel().remove(loginPanel);
        contactsPanel = new ContactsPanel(this);
        getChatClientFrame().getContactPanel().add(contactsPanel, java.awt.BorderLayout.CENTER);
        getChatClientFrame().setIconAt(0, group_icon);
        getChatClientFrame().setTitleAt(0, "Contact List");
        getChatClientFrame().repaint();
    }

    public void initSound() {
        if (clientSettingBean.getSoundType() != null && !"0".equals(clientSettingBean.getSoundType())) {
            try {
                if ("1".equals(getClientSettingBean().getSoundType())) {
                    ac = Applet.newAudioClip(new File(path + "/sound/alert.mid").toURI().toURL());
                } else {
                    ac = Applet.newAudioClip(new File(path + "/sound/bird.mid").toURI().toURL());
                }
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }else{
            ac=null;
        }
    }

    public void forcedSignOff() throws RemoteException {
    }

    private void exitME() {
        chatClientFrame.setVisible(false);
    }

    public void run() {
        while (number < 16) {
            if (chatClientFrame.isVisible()) {
                if (number % 2 == 0) {
                    chatClientFrame.setTitle(title + " is talking!");
                    chatClientFrame.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/chat.jpg")));
                } else {
                    if (user != null) {
                        chatClientFrame.setTitle(user.getName());
                    } else {
                        chatClientFrame.setTitle("Free Instant Messenger");
                    }
                    chatClientFrame.setIconImage(getChatSysTray().getStatusImage());
                }
            }
            try {
                number++;
                th.sleep(1500);
            } catch (Exception e) {
            }
        }
        if (user != null) {
            chatClientFrame.setTitle(user.getName());
        } else {
            chatClientFrame.setTitle("Free Instant Messenger");
        }
        chatClientFrame.setIconImage(getChatSysTray().getStatusImage());
    }

    public String getPath() {
        return path;
    }

    public Vector<Group> getGroups() {
        return groups;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vector<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Vector<Room> rooms) {
        this.rooms = rooms;
    }

    public Hashtable<Integer, ChatWindowPanel> getOpenChatPanelsByIndex() {
        return openChatPanelsByIndex;
    }

    public static Hashtable<String, String> getEmotionImages() {
        return emotionImages;
    }

    public JFileChooser getJfm() {
        return jfm;
    }

    public ChatFrame getChatClientFrame() {
        return chatClientFrame;
    }

    public void windowOpened(WindowEvent arg0) {
        //about();
    }
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */

    public void windowClosing(WindowEvent arg0) {
        exitME();
    }
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */

    public void windowClosed(WindowEvent arg0) {
    }
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */

    public void windowIconified(WindowEvent arg0) {
    }
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */

    public void windowDeiconified(WindowEvent arg0) {
    }
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */

    public void windowActivated(WindowEvent arg0) {
    }
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */

    public void windowDeactivated(WindowEvent arg0) {
    }

    /**
     * @return the statusLookup
     */
    public Hashtable<Integer, Status> getStatusLookup() {
        return statusLookup;
    }

    private void loadStatusLookup() {
        statusLookup = new Hashtable<Integer, Status>();
        Status status0 = new Status();
        status0.setId(0);
        status0.setDesc("Sign Out");
        Status status1 = new Status();
        status1.setId(1);
        status1.setDesc("Offline");
        Status status2 = new Status();
        status2.setId(2);
        status2.setDesc("Online");
        Status status3 = new Status();
        status3.setId(3);
        status3.setDesc("Busy");
        Status status4 = new Status();
        status4.setId(4);
        status4.setDesc("Away");
        statusLookup.put(IConstant.SIGN_OUT, status0);
        statusLookup.put(IConstant.OFFLINE, status1);
        statusLookup.put(IConstant.ONLINE, status2);
        statusLookup.put(IConstant.BUSY, status3);
        statusLookup.put(IConstant.AWAY, status4);
    }

    /**
     * @return the serverInterface
     */
    public ServerClientInterface getServerInterface() {
        return serverInterface;
    }

    /**
     * @return the chatSysTray
     */
    public ChatSysTray getChatSysTray() {
        return chatSysTray;
    }

    /**
     * @return the errorIcon
     */
    public ImageIcon getErrorIcon() {
        return errorIcon;
    }

    /**
     * @return the emotionPanel
     */
    public EmotionPanel getEmotionPanel() {
        return emotionPanel;
    }

    /**
     * @return the emotionFrame
     */
    public JDialog getEmotionFrame() {
        return emotionFrame;
    }

    /**
     * @return the fontFrame
     */
    public JDialog getFontFrame() {
        return fontFrame;
    }

    /**
     * @return the clientSettingBean
     */
    public ClientSettingBean getClientSettingBean() {
        return clientSettingBean;
    }

    /**
     * @return the securityMode
     */
    public int getSecurityMode() {
        return securityMode;
    }
    public String getRootNode(){
        return rootNode;
    }

    /**
     * @return the stringEnc
     */
    public StringEncrypter getStringEnc() {
        return stringEnc;
    }
}
