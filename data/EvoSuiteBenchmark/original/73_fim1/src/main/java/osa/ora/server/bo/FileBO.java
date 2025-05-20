/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.server.bo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import osa.ora.server.ModernChatServer;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.IConstant;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.User;
import osa.ora.server.utils.XMLParser;

/**
 *
 * @author ooransa
 */
public class FileBO implements IBO {

    String path;
    XMLParser parser1;
    XMLParser parser2;
    XMLParser parser3;
    Properties config;
    private Logger logger;
    private User adminUser;
    private ModernChatServer modernServer;
    private Hashtable<Integer, String> passwords;
    private static final String SERVER_DATA_1_FILE_NAME = "/config/data1.xml";
    private static final String SERVER_DATA_2_FILE_NAME = "/config/data2.xml";
    private static final String SERVER_DATA_3_FILE_NAME = "/config/data3.xml";
    private static final String SERVER_TRANS_FILE_NAME = "/config/trans.dat";

    public FileBO(String path,ModernChatServer modernServer) throws Exception {
        this.path = path;
        this.modernServer=modernServer;
        this.logger=ModernChatServer.getLogger();
        loadXMLFile(path);
        config = new Properties();
        try {
            config.load(new FileInputStream(path + SERVER_TRANS_FILE_NAME));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!,Can't load user passwords!",e);
        }
        passwords = new Hashtable<Integer, String>();
    }

    private void loadXMLFile(String path) throws Exception {
        parser1 = new XMLParser(path + SERVER_DATA_1_FILE_NAME);
        parser2 = new XMLParser(path + SERVER_DATA_2_FILE_NAME);
        parser3 = new XMLParser(path + SERVER_DATA_3_FILE_NAME);
    }
    public Vector<Group> loadGroupsAndUsers() {
        logger.log(Level.FINE, "Load All Users and Groups");
        Vector<Group> result = new Vector<Group>(0);
        //load groups
        Vector tempVector = parser1.getProperty("group");
        logger.log(Level.FINE, "All Groups size=" + tempVector.size());
        for (int i = 0; i < tempVector.size(); i++) {
            Group groupTemp = new Group();
            Hashtable hashTemp = (Hashtable) tempVector.get(i);
            groupTemp.setId(Integer.parseInt((String) hashTemp.get("id")));
            groupTemp.setName((String) hashTemp.get("name"));
            groupTemp.setUsers(new Vector<User>());
            result.add(groupTemp);
        }
        tempVector = parser2.getProperty("user");
        logger.log(Level.FINE, "All Users size=" + tempVector.size());
        for (int i = 0; i < tempVector.size(); i++) {
            User userTemp = new User();
            Hashtable hashTemp = (Hashtable) tempVector.get(i);
            userTemp.setId(Integer.parseInt((String) hashTemp.get("id")));
            userTemp.setName((String) hashTemp.get("name"));
            userTemp.setEmail((String) hashTemp.get("email"));
            userTemp.setDirectPhone((String) hashTemp.get("directPhone"));
            userTemp.setJobTitle((String) hashTemp.get("jobTitle"));
            userTemp.setGroup_id(Integer.parseInt((String) hashTemp.get("groupId")));
            userTemp.setCanJoinChatRoom(Boolean.parseBoolean((String) hashTemp.get("canJoinChatRoom")));
            userTemp.setOnlyStartChat(Boolean.parseBoolean((String) hashTemp.get("onlyStartChat")));
            userTemp.setShowMyStatus(Boolean.parseBoolean((String) hashTemp.get("showStatus")));
            userTemp.setStatus_id(IConstant.SIGN_OUT);
            String password = config.getProperty("" + userTemp.getId());
            //only add user if he has group :)
            //admin user doesn't have group

            if(userTemp.getId()==1000){
                adminUser=userTemp;
                getPasswords().put(userTemp.getId(), password);
            }else{
                for (int n = 0; n < result.size(); n++) {
                    if (result.get(n).getId() == userTemp.getGroup_id()) {
                        result.get(n).getUsers().add(userTemp);
                        getPasswords().put(userTemp.getId(), password);
                        break;
                    }
                }
            }
        }
        System.gc();
        return result;
    }
    public User getAdminUser(){
        return adminUser;
    }
    public Vector<Room> loadRooms() {
        logger.log(Level.FINE, "Load All Rooms");
        Vector<Room> result = new Vector<Room>();
        Vector tempVector = parser3.getProperty("room");
        logger.log(Level.FINE, "All rooms size=" + tempVector.size());
        for (int i = 0; i < tempVector.size(); i++) {
            Room roomTemp = new Room();
            Hashtable hashTemp = (Hashtable) tempVector.get(i);
            roomTemp.setId(Integer.parseInt((String) hashTemp.get("id")));
            roomTemp.setName((String) hashTemp.get("name"));
            String users = (String) hashTemp.get("users");
            if (users != null && users.length() > 0) {
                String[] userIds = users.split(":");
                if (userIds != null && userIds.length > 0) {
                    int[] roomUsers = new int[userIds.length];
                    for (int n = 0; n < userIds.length; n++) {
                        roomUsers[n] = Integer.parseInt(userIds[n]);
                    }
                    roomTemp.setUserId(roomUsers);
                    //this is done here to enuse no room is added unless it
                    //has users assigened on it.
                    result.add(roomTemp);
                }
            }
        }
        System.gc();
        return result;
    }

    /**
     * @return the passwords
     */
    public Hashtable<Integer, String> getPasswords() {
        return passwords;
    }

    private Group saveNewGroup(Vector<Group> groups, Group newGroup) {
        logger.log(Level.FINE, "Save New Group");
        File tempFile = new File(path + SERVER_DATA_1_FILE_NAME);
        BufferedWriter out = null;
        try {
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            out.write("<data>");
            out.newLine();
            out.write("<groups>");
            out.newLine();
            int maxId = 0;
            for (int i = 0; i < groups.size(); i++) {
                groups.get(i).writeToFile(out);
                if (groups.get(i).getId() > maxId) {
                    maxId = groups.get(i).getId();
                }
            }
            if(maxId<100) maxId=100;
            newGroup.setId(maxId + 1);
            newGroup.writeToFile(out);
            out.write("</groups>");
            out.newLine();
            out.write("</data>");
            out.newLine();
            out.flush();
            out.close();
            groups.add(newGroup);
            return newGroup;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!,Can't save groups!",e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    private User saveNewUser(Vector<Group> groups,User adminUser,  User newUser,String defaultPass) {
        logger.log(Level.FINE, "Save New User");
        File tempFile = new File(path + SERVER_DATA_2_FILE_NAME);
        BufferedWriter out = null;
        try {
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            out.write("<data>");

            out.newLine();
            out.write("<users>");
            out.newLine();
            adminUser.writeToFile(out);
            int maxId = 0;
            for (int i = 0; i < groups.size(); i++) {
                Vector<User> tempUsers = groups.get(i).getUsers();
                for (int n = 0; n < tempUsers.size(); n++) {
                    tempUsers.get(n).writeToFile(out);
                    if (tempUsers.get(n).getId() > maxId) {
                        maxId = tempUsers.get(n).getId();
                    }
                }
            }
            if(maxId<1000) maxId=1000;
            newUser.setId(maxId + 1);
            newUser.writeToFile(out);
            out.write("</users>");
            out.newLine();
            out.write("</data>");
            out.newLine();
            out.flush();
            out.close();
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() == newUser.getGroup_id()) {
                    newUser.setStatus_id(IConstant.SIGN_OUT);
                    groups.get(i).getUsers().add(newUser);
                    break;
                }
            }
            config.setProperty("" + newUser.getId(), defaultPass);
            savePasswordFile();
            return newUser;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!, Can't save users!",e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    private void savePasswordFile() throws Exception {
        logger.log(Level.FINE, "Save Password File");
        try {
            BufferedWriter out = null;
            File tempFile = new File(path + SERVER_TRANS_FILE_NAME);
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            config.store(out, "Updated AT :" + Calendar.getInstance());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!, can't save password file!",e);
            throw e;
        }
    }

    private Room saveNewRoom(Vector<Room> rooms, Room newRoom) {
        logger.log(Level.FINE, "Save New Room");
        File tempFile = new File(path + SERVER_DATA_3_FILE_NAME);
        BufferedWriter out = null;
        try {
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            out.write("<data>");
            out.newLine();
            out.write("<rooms>");
            out.newLine();
            int maxId = 0;
            for (int i = 0; i < rooms.size(); i++) {
                rooms.get(i).writeToFile(out);
                if (rooms.get(i).getId() > maxId) {
                    maxId = rooms.get(i).getId();
                }
            }
            if(maxId<10000) maxId=10000;
            newRoom.setId(maxId + 1);
            newRoom.writeToFile(out);
            out.write("</rooms>");
            out.newLine();
            out.write("</data>");
            out.newLine();
            out.flush();
            out.close();
            rooms.add(newRoom);
            return newRoom;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!, can't save rooms!",e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    private Group deleteGroup(Vector<Group> groups, Group delGroup) {
        logger.log(Level.FINE, "Delete Group");
        File tempFile = new File(path + SERVER_DATA_1_FILE_NAME);
        BufferedWriter out = null;
        try {
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            out.write("<data>");
            out.newLine();
            out.write("<groups>");
            out.newLine();
            int index = -1;
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() != delGroup.getId()) {
                    groups.get(i).writeToFile(out);
                } else {
                    index = i;
                }
            }
            out.write("</groups>");
            out.newLine();
            out.write("</data>");
            out.newLine();
            out.flush();
            out.close();
            groups.remove(index);
            return delGroup;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!, can't save groups!",e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    private Room deleteRoom(Vector<Room> rooms, Room delRoom) {
        logger.log(Level.FINE, "Delete Room");
        File tempFile = new File(path + SERVER_DATA_3_FILE_NAME);
        BufferedWriter out = null;
        try {
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            out.write("<data>");
            out.newLine();
            out.write("<rooms>");
            out.newLine();
            int index = -1;
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getId() != delRoom.getId()) {
                    rooms.get(i).writeToFile(out);
                } else {
                    index = i;
                }
            }
            out.write("</rooms>");
            out.newLine();
            out.write("</data>");
            out.newLine();
            out.flush();
            out.close();
            rooms.remove(index);
            return delRoom;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!, can't save rooms!",e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    private User deleteUser(Vector<Group> groups,User adminUser, User delUser) {
        logger.log(Level.FINE, "Delete User");
        File tempFile = new File(path + SERVER_DATA_2_FILE_NAME);
        BufferedWriter out = null;
        try {
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            out.write("<data>");
            out.newLine();
            out.write("<users>");
            out.newLine();
            adminUser.writeToFile(out);
            for (int i = 0; i < groups.size(); i++) {
                Vector<User> tempUsers = groups.get(i).getUsers();
                for (int n = 0; n < tempUsers.size(); n++) {
                    if (tempUsers.get(n).getId() != delUser.getId()) {
                        tempUsers.get(n).writeToFile(out);
                    }
                }
            }
            out.write("</users>");
            out.newLine();
            out.write("</data>");
            out.newLine();
            out.flush();
            out.close();
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() == delUser.getGroup_id()) {
                    Vector<User> tempUsers = groups.get(i).getUsers();
                    for (int n = 0; n < tempUsers.size(); n++) {
                        if (tempUsers.get(n).getId() == delUser.getId()) {
                            groups.get(i).getUsers().remove(n);
                            break;
                        }
                    }
                }
            }
            return delUser;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!, can't save users!",e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    private User updateUser(Vector<Group> groups,User adminUser, User updUser) {
        logger.log(Level.FINE, "Update User");
        File tempFile = new File(path + SERVER_DATA_2_FILE_NAME);
        BufferedWriter out = null;
        try {
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            out.write("<data>");
            out.newLine();
            out.write("<users>");
            out.newLine();
            adminUser.writeToFile(out);
            for (int i = 0; i < groups.size(); i++) {
                Vector<User> tempUsers = groups.get(i).getUsers();
                for (int n = 0; n < tempUsers.size(); n++) {
                    if (tempUsers.get(n).getId() != updUser.getId()) {
                        tempUsers.get(n).writeToFile(out);
                    } else {
                        updUser.writeToFile(out);
                    }
                }
            }
            out.write("</users>");
            out.newLine();
            out.write("</data>");
            out.newLine();
            out.flush();
            out.close();
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() == updUser.getGroup_id()) {
                    Vector<User> tempUsers = groups.get(i).getUsers();
                    for (int n = 0; n < tempUsers.size(); n++) {
                        if (tempUsers.get(n).getId() == updUser.getId()) {
                            groups.get(i).getUsers().set(n, updUser);
                            break;
                        }
                    }
                }
            }
            return updUser;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!, can't save users!",e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }
    private User updateUserGroup(Vector<Group> groups,User adminUser, User updUser) {
        logger.log(Level.FINE, "Update User Group");
        File tempFile = new File(path + SERVER_DATA_2_FILE_NAME);
        BufferedWriter out = null;
        try {
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            out.write("<data>");
            out.newLine();
            out.write("<users>");
            out.newLine();
            adminUser.writeToFile(out);
            for (int i = 0; i < groups.size(); i++) {
                Vector<User> tempUsers = groups.get(i).getUsers();
                for (int n = 0; n < tempUsers.size(); n++) {
                    if (tempUsers.get(n).getId() != updUser.getId()) {
                        tempUsers.get(n).writeToFile(out);
                    } else {
                        updUser.writeToFile(out);
                    }
                }
            }
            out.write("</users>");
            out.newLine();
            out.write("</data>");
            out.newLine();
            out.flush();
            out.close();
            //remove from old group
            for (int i = 0; i < groups.size(); i++) {
                Vector<User> tempUsers = groups.get(i).getUsers();
                for (int n = 0; n < tempUsers.size(); n++) {
                    if (tempUsers.get(n).getId() == updUser.getId()) {
                        groups.get(i).getUsers().remove(n);
                        break;
                    }
                }
            }
            //add to the new group
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() == updUser.getGroup_id()) {
                    groups.get(i).getUsers().add(updUser);
                    break;
                }
            }
            return updUser;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!, can't save users!",e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    private Group updateGroup(Vector<Group> groups, Group updateGroup) {
        logger.log(Level.FINE, "Update Group");
        File tempFile = new File(path + SERVER_DATA_1_FILE_NAME);
        BufferedWriter out = null;
        try {
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            out.write("<data>");
            out.newLine();
            out.write("<groups>");
            out.newLine();
            int index = 0;
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() != updateGroup.getId()) {
                    groups.get(i).writeToFile(out);
                } else {
                    updateGroup.writeToFile(out);
                    index = i;
                }
            }
            out.write("</groups>");
            out.newLine();
            out.write("</data>");
            out.newLine();
            out.flush();
            out.close();
            groups.set(index, updateGroup);
            return updateGroup;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!, can't save groups!",e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    private Room updateRoom(Vector<Room> rooms, Room updateRoom) {
        logger.log(Level.FINE, "Update Room");
        File tempFile = new File(path + SERVER_DATA_3_FILE_NAME);
        BufferedWriter out = null;
        try {
            tempFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            out.write("<data>");
            out.newLine();
            out.write("<rooms>");
            out.newLine();
            int index = 0;
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getId() != updateRoom.getId()) {
                    rooms.get(i).writeToFile(out);
                } else {
                    updateRoom.writeToFile(out);
                    index = i;
                }
            }
            out.write("</rooms>");
            out.newLine();
            out.write("</data>");
            out.newLine();
            out.flush();
            out.close();
            rooms.set(index, updateRoom);
            return updateRoom;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server Data Error!, can't save rooms!",e);
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    public User createUser(User user) {
        return saveNewUser(modernServer.getGroups(),modernServer.getAdminUser(), user,modernServer.getServerSettingBean().getDefualtPassword());
    }

    public Group createGroup(Group group) {
        return saveNewGroup(modernServer.getGroups(), group);
    }

    public Room createRoom(Room room) {
        return saveNewRoom(modernServer.getRooms(), room);
    }

    public User delUser(User user) {
        return deleteUser(modernServer.getGroups(),modernServer.getAdminUser(), user);
    }

    public Group delGroup(Group group) {
        return deleteGroup(modernServer.getGroups(), group);
    }

    public Room delRoom(Room room) {
        return deleteRoom(modernServer.getRooms(), room);
    }

    public User updateUser(User user) {
        return updateUser(modernServer.getGroups(),modernServer.getAdminUser(), user);
    }

    public Group updateGroup(Group group) {
        return updateGroup(modernServer.getGroups(), group);
    }

    public Room updateRoom(Room room) {
        return updateRoom(modernServer.getRooms(), room);
    }

    public boolean updatePassword(int userId,String oldPass, String newPass) {
        logger.log(Level.FINE, "Update Password");
        config.setProperty("" + userId,newPass);
        try {
            savePasswordFile();
            return true;
        } catch (Exception ex) {
            config.setProperty("" + userId,oldPass);
            logger.log(Level.SEVERE, "Server Data Error!, can't update user password!",ex);
            return false;
        }
    }

    public User resetUserPass(User user) {
        logger.log(Level.FINE, "Reset User Password");
        config.setProperty("" + user.getId(),modernServer.getServerSettingBean().getDefualtPassword());
        try {
            savePasswordFile();
            return user;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Server Data Error!, can't reset user password!",ex);
            return null;
        }
    }

    public User updateUserGroup(User user) {
        return updateUserGroup(modernServer.getGroups(),modernServer.getAdminUser(),user);
    }

}
