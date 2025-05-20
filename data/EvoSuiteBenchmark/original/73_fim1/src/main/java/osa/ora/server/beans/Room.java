/*
 * Room.java
 *
 * Created on October 28, 2009, 4:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.beans;

import java.io.BufferedWriter;
import java.io.Serializable;
import osa.ora.server.utils.XMLParser;

/**
 *
 * @author ooransa
 */
public class Room implements Serializable{
    private int id;
    private String name;
    private int[] userId;
    /** Creates a new instance of Room */
    public Room() {
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

    public void setName(String name) {
        this.name = name;
    }

    public int[] getUserId() {
        return userId;
    }

    public void setUserId(int[] userId) {
        this.userId = userId;
    }

     public void writeToFile(BufferedWriter out) throws Exception{
        out.write("<room>");out.newLine();
        out.write("<id>"+id+"</id>");out.newLine();
        out.write("<name>"+XMLParser.returnSpecial(name)+"</name>");out.newLine();
        String users="";
        for(int i=0;i<userId.length;i++){
            users+=userId[i]+":";
        }
        out.write("<users>"+users.substring(0,users.length()-1)+"</users>");out.newLine();
        out.write("</room>");out.newLine();
    }
}
