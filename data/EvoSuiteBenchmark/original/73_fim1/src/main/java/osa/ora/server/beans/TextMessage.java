/*
 * TextMessage.java
 *
 * Created on October 27, 2009, 1:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.beans;

import java.io.Serializable;

/**
 *
 * @author ooransa
 */
public class TextMessage  implements Serializable{
    private int fromUserId;
    private int toUserId;
    private int targetType;
    private String title;
    private String message;
    /** Creates a new instance of TextMessage */
    public TextMessage() {
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }
    
}
