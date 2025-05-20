/*
 * Status.java
 *
 * Created on October 27, 2009, 12:30 AM
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
public class Status  implements Serializable{
    
    private int id;
    private String desc;
    private String customMessage;
    /** Creates a new instance of Status */
    public Status() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }
    
}
