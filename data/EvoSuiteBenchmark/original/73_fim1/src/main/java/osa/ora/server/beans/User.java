/*
 * User.java
 *
 * Created on October 27, 2009, 12:25 AM
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
public class User  implements Serializable{
    
    /** Creates a new instance of User */
    private int id;
    private String name;
    private String email;
    private String jobTitle;
    private String directPhone;
    private int group_id;
    private int status_id;
    //won't show user status to others
    private boolean showMyStatus;
    //no one can start chat with him unless he start the chat window.
    private boolean onlyStartChat;
    //this attribute for admin when select users for rooms..
    private boolean canJoinChatRoom;
    
    public User() {
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

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public boolean isShowMyStatus() {
        return showMyStatus;
    }

    public void setShowMyStatus(boolean showMyStatus) {
        this.showMyStatus = showMyStatus;
    }

    public boolean isOnlyStartChat() {
        return onlyStartChat;
    }

    public void setOnlyStartChat(boolean onlyStartChat) {
        this.onlyStartChat = onlyStartChat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isCanJoinChatRoom() {
        return canJoinChatRoom;
    }

    public void setCanJoinChatRoom(boolean canJoinChatRoom) {
        this.canJoinChatRoom = canJoinChatRoom;
    }

    /**
     * @return the directPhone
     */
    public String getDirectPhone() {
        return directPhone;
    }

    /**
     * @param directPhone the directPhone to set
     */
    public void setDirectPhone(String directPhone) {
        this.directPhone = directPhone;
    }

    public void writeToFile(BufferedWriter out) throws Exception{
        out.write("<user>");out.newLine();
        out.write("<id>"+id+"</id>");out.newLine();
        out.write("<name>"+XMLParser.returnSpecial(name)+"</name>");out.newLine();
        out.write("<email>"+XMLParser.returnSpecial(email)+"</email>");out.newLine();
        out.write("<directPhone>"+XMLParser.returnSpecial(directPhone)+"</directPhone>");out.newLine();
        out.write("<jobTitle>"+XMLParser.returnSpecial(jobTitle)+"</jobTitle>");out.newLine();
        out.write("<groupId>"+group_id+"</groupId>");out.newLine();
        out.write("<showStatus>"+showMyStatus+"</showStatus>");out.newLine();
        out.write("<onlyStartChat>"+onlyStartChat+"</onlyStartChat>");out.newLine();
        out.write("<canJoinChatRoom>"+canJoinChatRoom+"</canJoinChatRoom>");out.newLine();
        out.write("</user>");out.newLine();
    }

    /**
     * @return the status_id
     */
    public int getStatus_id() {
        return status_id;
    }

    /**
     * @param status_id the status_id to set
     */
    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    /**
     * @return the jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle the jobTitle to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
