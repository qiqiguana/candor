/* $Id: MessageFactory.java,v 1.2 2004/04/27 19:26:21 bja Exp $ */

package module;

import util.AbstractFactory;
import module.Message;
import java.util.ResourceBundle;

/**
 * This class provides methods for creating Messages of various kind.
 * Messages created by this factory are managed, that is instances are
 * reused after they have been used.
 */
public class MessageFactory extends AbstractFactory<Message> {

    /** The reference to the MessageFactory instance. */
    protected static MessageFactory instance = null;

    /**
     * Creates a new instance of the MessageFactory class.
     */
    private MessageFactory() {
	super(new Message());
	this.instance = this;
    }

    /**
     * Returns a reference to new empty message. The message topic and
     * data is set to null. The recipient field is set to default "*".
     */
    public Message createMessage() {
	return createMessage(null, "*", null);
    }

    /**
     * Returns the MessageFactory instance, currently the instance
     * returned is that of the last created MessageFactory.
     */
    public static MessageFactory getInstance() {
	return instance != null ? instance : new MessageFactory();
    }

    /**
     * Create a new Message instance with the specified topic and data.
     *
     * @param topic The topic this messages is published to.
     * @param data The data associated with this message.
     */
    public static Message createMessage(String topic, Object data) {
	return createMessage(topic, "*", data);
    }

    /**
     * Create a new Message instance with the specified topic, 
     * recipient, and data.
     *
     * @param topic The topic this messages is published to.
     * @param recip The recipient of this message.
     * @param data The data associated with this message.
     */
    public static Message createMessage(String topic, String recip,
					Object data) {
	Message msg = getInstance().createObject();
	msg.set(topic, recip, data);
	return msg;
    }

    public static Message createUnloadMessage(String name) {
	return createMessage("KERNEL", "UNLOAD " + name);
    }

    /**
     *
     * @param localeKey
     * @param args
     */
    public static Message createWarningMessage(String localeKey,
					       Object ... args) {

	Message msg = getInstance().createObject();

	try {
	    ResourceBundle bundle = ResourceBundle.getBundle("gangup");
	    msg.set("WARNING", "*", 
		    String.format(bundle.getString(localeKey), args));
	} catch (Exception e) {
	    msg.set("WARNING", "*",
		    "Reading string from locale failed: key="+localeKey+"\n"+
		    "This probably means that you have an old or otherwise\n"+
		    "inaccurate locale file (maybe it's missing altogether)\n"+
		    "or that the classpath does not contain the directory.");
	}
	return msg;
    }

    /**
     *
     * @param localeKey
     * @param args
     */
    public static Message createErrorMessage(String localeKey, 
					     Object ... args) {
	Message msg = createWarningMessage(localeKey, args);
	msg.setHeader("ERROR");
	return msg;
    }

    /**
     *
     * @param id
     * @param addr
     */
    public static Message createConnectionDroppedMessage(int id, String addr) {
	Message msg = getInstance().createObject();
	msg.set("DROPPED", "*", "ID: " + id + " IP: " + addr);
	return msg;
    }
}
