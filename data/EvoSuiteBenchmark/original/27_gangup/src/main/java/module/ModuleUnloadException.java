/* $Id: ModuleUnloadException.java,v 1.2 2004/04/27 19:26:21 bja Exp $
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

public class ModuleUnloadException extends Exception {
    ModuleUnloadException(Module mod) {
	super("mod=" + mod);
    }
    ModuleUnloadException(Throwable cause) { 
	super(cause); 
    }
    ModuleUnloadException(Module mod, String message) {
	super("mod=" + mod + ", " + message); 
    }
    ModuleUnloadException(Module mod, String message, Throwable cause) {
	super("mod=" + mod + ", " + message, cause); 
    }
    ModuleUnloadException(Module mod, Throwable cause) {
	super("mod=" + mod, cause); 
    }
    ModuleUnloadException(String message, Throwable cause) {
	super(message, cause);
    }

}
