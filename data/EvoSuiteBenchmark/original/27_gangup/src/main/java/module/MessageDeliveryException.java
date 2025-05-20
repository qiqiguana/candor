/* $Id: MessageDeliveryException.java,v 1.2 2004/04/27 19:26:21 bja Exp $
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

public class MessageDeliveryException extends Exception {
    MessageDeliveryException(Throwable cause) { 
	super(cause); 
    }
    MessageDeliveryException(Module mod, Message msg, Throwable cause) {
	super("mod"+mod +",msg="+msg, cause); 
    }
    MessageDeliveryException(Module mod, Message msg, String str) {
	super("mod"+mod +",msg="+msg + ": " + str); 
    }
    MessageDeliveryException(String message, Throwable cause) {
	super(message, cause);
    }

}
