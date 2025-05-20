/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package osa.ora.server.bd;

import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Logger;
import osa.ora.server.ModernChatServer;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.User;
import osa.ora.server.bo.FileBO;
import osa.ora.server.bo.IBO;

/**
 *
 * @author ooransa
 */
public class UsersBD implements IBO{
    public static final String FILE_TYPE="1";
    public static final String DB_TYPE="2";
    private IBO businessObject;
    public UsersBD(String type,String path,ModernChatServer modernServer) throws Exception{
        if(type==null || type.equals(FILE_TYPE)){
            businessObject=new FileBO(path,modernServer);
        }else if(type.equals(DB_TYPE)){
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public Vector<Group> loadGroupsAndUsers() {
        return businessObject.loadGroupsAndUsers();
    }

    public Vector<Room> loadRooms() {
        return businessObject.loadRooms();
    }

    public Hashtable<Integer, String> getPasswords() {
        return businessObject.getPasswords();
    }

    public User createUser(User user) {
        return businessObject.createUser(user);
    }

    public Group createGroup(Group group) {
        return businessObject.createGroup(group);
    }

    public Room createRoom(Room room) {
        return businessObject.createRoom(room);
    }

    public User delUser(User user) {
        return businessObject.delUser(user);
    }

    public Group delGroup(Group group) {
        return businessObject.delGroup(group);
    }

    public Room delRoom(Room room) {
        return businessObject.delRoom(room);
    }

    public User updateUser(User user) {
        return businessObject.updateUser(user);
    }

    public Group updateGroup(Group group) {
        return businessObject.updateGroup(group);
    }

    public Room updateRoom(Room room) {
        return businessObject.updateRoom(room);
    }

    public boolean updatePassword(int userId, String oldPass, String newPass) {
        return businessObject.updatePassword(userId, oldPass, newPass);
    }

    public User getAdminUser() {
        return businessObject.getAdminUser();
    }

    public User resetUserPass(User user) {
        return businessObject.resetUserPass(user);
    }

    public User updateUserGroup(User user) {
        return businessObject.updateUserGroup(user);
    }

}
