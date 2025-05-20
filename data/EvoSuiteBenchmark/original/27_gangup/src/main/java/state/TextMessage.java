/* $Id: TextMessage.java,v 1.3 2004/04/30 23:35:13 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.3 $
 *
 */

package state;

import java.io.UnsupportedEncodingException;

/**
 * Class for TextMessages, i.e. messages used for the chat.
 */

public class TextMessage implements Packable {

    private int actor;
    private int target;
    private int messagetype;
    private String text;
    private long time;

    public final static int GENERAL = 0;
    public final static int GROUP = 1;
    public final static int PRIVATE = 2;
    
    public final int type = Packable.TEXT_MESSAGE;

    /**
     * Creates a new TextMessage.
     */
    public TextMessage(int actor, int target, int messagetype, String text) {
	this.actor = actor;
	this.target = target;
	this.messagetype = messagetype;
	this.text = text;
    }

    /**
     * Creates a new empty TextMessage. Empty messages cannot be sent
     * over the network, and will throw a NullPointerException if
     * done.
     */
    public TextMessage() {
	this(-1,-1,-1,null);
    }

    /**
     * Sets the actor of this Action object.
     */
    public void setActor(int actor) {
	this.actor = actor;
    }
    
    public int getActor() {
	return actor;
    }

    public int getTarget() {
	return target;
    }

    public int getMessageType() {
	return messagetype;
    }
    
    public String getText() {
	return text;
    }

    public long getTime() {
	return time;
    }

    public void setTime(long l) {
	time = l;
    }

    public int type() {
	return Packable.TEXT_MESSAGE;
    }

    /**
     * Converts a TextMessage into a byte array representation.
     *
     * @param msg The message to be converted.
     * @return The resulting byte array.
     */
    public byte[] pack() {
	
	//TextMessage msg = (TextMessage) p;
	byte[] data = null;
	
	try {
	    byte[] t2 = text.getBytes("UTF-8");
	    data = new byte[t2.length+3];
	    System.arraycopy(t2, 0, data, 3, t2.length);
	    data[0] = (byte) actor;
	    data[1] = (byte) target;
	    data[2] = (byte) messagetype;

	} catch (UnsupportedEncodingException e) {
	    /* All javavms are required to implement UTF-8 */
	}
	return data;
    }
    
    /**
     * Converts a byte array back into a TextMessage.
     *
     * @param data The byte array to be converted.
     * @return The resultning message.
     */
    public void unpack(byte[] data) {

	try {
	    this.actor = (int) data[0];
	    this.target = (int) data[1];
	    this.messagetype = (int) data[2];
	    byte[] textbytes = new byte[data.length-3];
	    System.arraycopy(data, 3, textbytes, 0, data.length-3);
	    this.text = new String(textbytes,"UTF-8");
	    
	} catch (UnsupportedEncodingException e) {
	    /* All javavms are required to implement UTF-8 */
	}
    }

    public String toString() {
	return "TextMessage[" +actor+ "," +target+ "," +messagetype+ "," +text+ "]";
    }
}
