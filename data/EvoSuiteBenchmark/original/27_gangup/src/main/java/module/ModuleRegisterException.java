/* $Id: ModuleRegisterException.java,v 1.4 2004/04/27 19:26:21 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.4 $
 *
 */

package module;

public class ModuleRegisterException extends Exception {
    ModuleRegisterException(Module mod, String str) {
	super("mod=" + mod + ": " + str);
    }
    ModuleRegisterException(Module mod, Throwable cause) {
	super("mod=" + mod, cause);
    }
    ModuleRegisterException(Module mod, String str, Throwable cause) {
	super("mod=" + mod + ": " + str, cause);
    }
}
