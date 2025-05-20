/*
 * IConstant.java
 *
 * Created on October 29, 2009, 12:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package osa.ora.server.beans;

/**
 *
 * @author ooransa
 */
public interface IConstant {
    //status
    public static final int SIGN_OUT=0;
    public static final int OFFLINE=1;
    public static final int ONLINE=2;
    public static final int BUSY=3;
    public static final int AWAY=4;
    //results
    public static final int SUCCESS=0;
    public static final int ERROR=1;
    public static final int EXCEPTION=2;
    public static final int OFFLINE_MESSAGE=3;
    public static final int INVALID_CREDENTIALS=4;
    public static final int REJECTED=5;
    public static final int NO_USERS=6;
    public static final int NO_GROUP=7;
    public static final int NO_ROOM=8;
    //binary messages
    public static final int REQUEST=1;
    public static final int DATA=2;
    //chat types
    public static int USER_CHAT=0;
    public static int GROUP_CHAT=1;
    public static int ROOM_CHAT=2;
    
}
