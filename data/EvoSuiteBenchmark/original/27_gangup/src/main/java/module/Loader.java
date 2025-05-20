/* $Id: Loader.java,v 1.2 2004/03/31 21:12:03 bja Exp $
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

/**
 * This class provides the main entry point into the application. It is
 * responisble for loading the kernel and essential modules.
 *
 */
public class Loader {

    public static final void main(String[] argv) {

	try {

	    Kernel kernel = new Kernel();
	    ConfigModule config = new ConfigModule(kernel);

	    /* Start running the config module. */

	    config.start();

	    /* Load the default system configuration */

	    config.read(argv[0]);
	    
	    /* Find list of additional modules to load */

	    String modules = config.getCVar("Kernel", "autoload");

	    if (modules != null) {
		String[] mod = modules.split(",");
		for (int i=0; i<mod.length; i++) {
		    kernel.loadModule(mod[i]);
		}
	    }

	} catch (ArrayIndexOutOfBoundsException e) {

	    System.err.println("Usage: java Loader CONFIG");
	    System.exit(1);

	} catch (Exception e) {

	    e.printStackTrace(System.err);
	    System.exit(1);

	}
    }
}
