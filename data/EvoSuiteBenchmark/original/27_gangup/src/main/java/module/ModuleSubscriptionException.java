/* $Id: ModuleSubscriptionException.java,v 1.2 2004/04/27 19:26:21 bja Exp $
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

public class ModuleSubscriptionException extends Exception {
    ModuleSubscriptionException(Module mod) {
	this(mod, null);
    }
    ModuleSubscriptionException(Throwable cause) { 
	super(cause); 
    }
    ModuleSubscriptionException(Module mod, Throwable cause) {
	super("mod=" + mod, cause); 
    }
    ModuleSubscriptionException(String message, Throwable cause) {
	super(message, cause);
    }
    ModuleSubscriptionException(Module mod, String message, Throwable cause) {
	super("mod=" + mod + ", " + message, cause); 
    }
}
