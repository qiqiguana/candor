/* $Id: MessageProcessingException.java,v 1.2 2004/04/27 19:26:21 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.2 $
 *
 */

package module;

public class MessageProcessingException extends Exception {
    public MessageProcessingException(Message msg) {
	super("msg=" + msg);
    }
    public MessageProcessingException(Message msg, String str) {
	super("msg=" + msg + ": " + str);
    }
    public MessageProcessingException(Throwable cause) { 
	super(cause); 
    }
    public MessageProcessingException(Message msg, Throwable cause) {
	super("msg="+msg, cause); 
    }
    public MessageProcessingException(String message, Throwable cause) {
	super(message, cause);
    }
    public MessageProcessingException(Module mod, Message msg,
				      Throwable cause) {
	this(mod, msg, "", cause); 
    }
    public MessageProcessingException(Module mod, Message msg, 
			       String str, Throwable cause) {
	super("mod=" + mod + "," + "msg="+msg+": " + str, cause); 
    }
}
