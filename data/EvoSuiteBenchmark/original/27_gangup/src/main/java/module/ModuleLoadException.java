/* $Id: ModuleLoadException.java,v 1.2 2004/04/27 19:26:21 bja Exp $
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

public class ModuleLoadException extends Exception {
    ModuleLoadException(Module mod) {
	this(mod, null);
    }
    ModuleLoadException(Throwable cause) { 
	super(cause); 
    }
    ModuleLoadException(Module mod, Throwable cause) {
	super("mod=" + mod, cause); 
    }
    ModuleLoadException(String message, Throwable cause) {
	super(message, cause);
    }

}
