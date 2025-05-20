/* $Id: Message.java,v 1.6 2004/04/27 19:26:21 bja Exp $ 
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.6 $
 *
 */

package module;

import util.ManagedObject;

/**
 * The Message class provides the common data link between modules. It's
 * sole purpose is to move data from one module to another.
 */
public class Message extends ManagedObject {

    /** Enumeration of possible message states */

    public static final int UNDEFINED = 0;
    public static final int CREATED   = 1;
    public static final int RECEIVED  = 2;
    public static final int DELIVERED = 4;

    /** Default time to live in milliseconds. */
    public static final int TIME_TO_LIVE = 3000;

    /** The number of instantiated messages. */
    private static long messages;

    /** Integer that uniqely identifies this message. */
    long messageID;

    /** Integer that uniqely identifies this session. */
    long sessionID;

    /** The header or class of this message. */
    String header;

    /** The name of the module this message orignitated from. */
    String sender;

    /** The name of the module this message is destined for. */
    String recipient;

    /** The content or data of this message. */
    Object body;   

    /** The time when this message was created. */
    long timestamp;

    /** The maximum time to live in milliseconds. */
    long timetolive;

    /** The current state of this message. */
    int state;

    /**
     * Create a new Message instance without topic and data.
     *
     */
    public Message() {
	//this(null, "*", null);
    }

    /**
     * Create a new Message instance with the specified
     * topic and data.
     *
     * @param topic The topic this messages is published to.
     * @param data The data associated with this message.
     */
    public Message(String topic, Object data) {
	this(topic, "*", data);
    }

    /**
     * Create a new Message instance with the specified
     * topic, recipient, and data.
     *
     * @param topic The topic this messages is published to.
     * @param recipient The recipient of this message.
     * @param data The data associated with this message.
     */
    public Message(String topic, String recipient, Object data) {

	timestamp = System.currentTimeMillis();
	timetolive = TIME_TO_LIVE;

	state = CREATED;

	this.header = topic;
	this.recipient = recipient;
	setBody(data);;

	synchronized(this) { messageID = messages++; }

	sessionID = messageID;
	sender = null;
	
    }

    /**
     * Set the content of this message to the specified values.
     *
     * @param topic
     * @param recipient
     * @param data
     */
    public void set(String topic, String recipient, Object data) {

	timestamp = System.currentTimeMillis();
	timetolive = TIME_TO_LIVE;

	state = CREATED;

	header = topic;
	setBody(data);;
	this.recipient = recipient;

	synchronized(this) { messageID = messages++; }

	sessionID = messageID;
	sender = null;
	
    }


    public long getID() {
	return messageID;
    }

    public long getSID() {
	return sessionID;
    }

    public long getTTL() {
	return timetolive;
    }

    public long getTimeStamp() {
	return timestamp;
    }

    public int getState() {
	return state;
    }

    /**
     * Sets the topic attribute of this message to the specified value.
     * @param name the name of the topic to send this message to.
     */
    public void setHeader(String topic) {
	header = topic;
    }

    /**
     * Returns The header of this message.
     * @return The header of this message.
     */
    public String getHeader() {
	return header == null ? "" : header;
    }

    /**
     * Sets the body attribute of this message to the specified value.
     * @param data the data associated with this message.
     */
    public void setBody(Object data) {
	if (data instanceof ManagedObject) {
	    ((ManagedObject) data).ref();
	}
	body = data;
    }

    /**
     * Returns The body of this message.
     * @return The body of this message.
     */
    public Object getBody() {
	return body == null ? "" : body;
    }

    /**
     * Sets the sender attribute of this message to the specified value.
     * @param name the name of the module that sent this message.
     */
    public void setSender(String name) {
	sender = name;
    }

    /**
     * Returns the name of the module this message originated from.
     * @return The name of the module this message originated from.
     */
    public String getSender() {
	return sender;
    }

    /**
     * Returns the name of the module this message is destined for.
     * @return The name of the module this message is destined for.
     */
    public String getRecipient() {
	return recipient;
    }

    /**
     * Sets the recipient attribute of this message to the specified value.
     * @param name the name of the module that this message is destined for.
     */
    public void setRecipient(String name) {
	recipient = name;
    }

    /**
     * Sets the delivery state of this Message to the specified boolean.
     * @param b The new delivery state of thie Message.
     */
    public void setDelivered(boolean b) {
	if (b) {
	    state |= DELIVERED;
	} else {
	    state &= ~DELIVERED;
	}
    }

    /**
     * Creates a new reply to this Message. Replies share the session key
     * with the original Message.
     *
     * @param data The body of the returned Message.
     */
    public Message reply(Object data) {
	Message m = new Message(header, sender, data);
	m.sessionID = sessionID;
	return m;
    }

    /**
     *
     * @return true if this Message has been delivered by the message
     *         service, otherwise return false.
     */
    public boolean delivered() {
	return (state & DELIVERED) != 0;
    }

    /**
     *
     * @return true if this Message has been received by the message
     *         service, otherwise return false.
     */
    public boolean received() {
	return (state & RECEIVED) != 0;
    }

    /**
     * This method is invoked when the reference count for this object
     * is set to zero, meaning no objects (modules) are referencing it,
     * thus it's safe to change the content.
     */
    protected synchronized void consume() {
	if (body instanceof ManagedObject) {
	    ((ManagedObject) body).unref();
	    //ManagedObject mo = (ManagedObject) body;
	    //if (mo.getIsManaged()) {
	    //	mo.unref();
	    //}
	}
	super.consume();

    }
    
    public Message(util.ObjectManager m) {
	super(m);
    }

    public Message clone() {
	return new Message(manager);
    }

    /**
     * 
     *
     */
    private Module sourceModule = null;
    public void send() throws MessageDeliveryException {
	if (sourceModule == null) {
	    throw new MessageDeliveryException(
		sourceModule, this, "no source module");
	}
	sourceModule.sendMessage(this);
    }

    /**
     *
     * @param source the module from which to send this message.
     */
    public void send(Module source) throws MessageDeliveryException {
	source.sendMessage(this);
    }

    /**
     * 
     * @param source the module from which to send this message.
     */
    public void sendFrom(Module source) throws MessageDeliveryException {
	source.sendMessage(this);
    }

    /**
     *
     * @param destinatino the module to which to send this message.
     */
    public void sendTo(Module destination) throws MessageDeliveryException {
	destination.receiveMessage(this);
    }

    /**
     * Return a string representation of this Message.
     * @return a string representation of this object.
     */
    public String toString() {
	return "Message[id=" + messageID +  ",session=" + sessionID + 
	    ",head=" + header + ",recip=" + recipient +
	    ",sender=" + sender + ",state=" + state + 
	    ",ttl=" + timetolive + ",time=" + timestamp + 
	    ",body=" + body + "]";
    }
}
