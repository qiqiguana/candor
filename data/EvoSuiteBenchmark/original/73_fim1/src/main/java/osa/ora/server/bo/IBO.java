/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package osa.ora.server.bo;

import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Logger;
import osa.ora.server.ModernChatServer;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.User;

/**
 *
 * @author ooransa
 */
public interface IBO {
    public Vector<Group> loadGroupsAndUsers();
    public Vector<Room> loadRooms();
    public User getAdminUser();
    public Hashtable<Integer, String> getPasswords();
    public boolean updatePassword(int userId,String oldPass,String newPass);
    public User createUser(User user);
    public Group createGroup(Group group);
    public Room createRoom(Room room);
    public User delUser(User user);
    public Group delGroup(Group group);
    public Room delRoom(Room room);
    public User updateUser(User user);
    public User updateUserGroup(User user);
    public User resetUserPass(User user);
    public Group updateGroup(Group group);
    public Room updateRoom(Room room);
}
