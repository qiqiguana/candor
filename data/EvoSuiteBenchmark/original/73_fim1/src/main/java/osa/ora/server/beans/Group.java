/*
 * Group.java
 *
 * Created on October 27, 2009, 12:25 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.beans;

import java.io.BufferedWriter;
import java.io.Serializable;
import java.util.Vector;
import osa.ora.server.utils.XMLParser;

/**
 *
 * @author ooransa
 */
public class Group implements Serializable{
    
    /** Creates a new instance of Group */
    private int id;
    private String name;
    private Vector<User> users;
    
    public Group() {
    }

    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String descName) {
        this.name = descName;
    }

    public Vector<User> getUsers() {
        return users;
    }

    public void setUsers(Vector<User> users) {
        this.users = users;
    }
    public void writeToFile(BufferedWriter out) throws Exception{
        out.write("<group>");out.newLine();
        out.write("<id>"+id+"</id>");out.newLine();
        out.write("<name>"+XMLParser.returnSpecial(name)+"</name>");out.newLine();
        out.write("</group>");out.newLine();
    }
}
