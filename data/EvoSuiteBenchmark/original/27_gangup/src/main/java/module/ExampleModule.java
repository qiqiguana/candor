/* $Id: ExampleModule.java,v 1.5 2004/04/27 19:26:21 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.5 $
 *
 */

package module;

import static module.MessageFactory.*;

@cvs(file     = "$RCSfile: ExampleModule.java,v $",
     revision = "$Revision: 1.5 $",
     date     = "$Date: 2004/04/27 19:26:21 $",
     author   = "$Author: bja $",
     tag      = "$Name:  $",
     build    = "")

@mod(name     = "ExampleModule",
     topics   = "EXAMPLE FOO BAR", 
     cmds     = "DUMP NOP",
     desc     = "This is an example of a complete but useless module.")

/**
 * This is an example of a simple module. It's purpose is to demonstrate
 * the basic structure of a module.
 */
public class ExampleModule extends AbstractModule {

    /**
     * Creates a new instance of this module. This constructor is usually
     * called from the kernel when it wishes to load a module dynamically.
     *
     * @throws ModuleRegisterException If you have a broken Java interpreter.
     */
    public ExampleModule() throws ModuleRegisterException {}

    /**
     * Initialization method for modules. Using init() is recommended
     * instead of overloading the constructor.
     */
    protected void init() {}

    /**
     * Method to run recurrently in the loop of run(). This method
     * should never be blocking, lest the module will not be able to
     * process any messages.
     */
    protected void step() {}

    /**
     * Exit method for modules. This method is invoked after a call to
     * the @see exit() method. Modules should overload this method with
     * code to do a proper exit.
     */
    protected void free() {}

    /**
     * This method is invoked once for every Message in the input queue.
     * Normally this is where module specific message handling is performed.
     * 
     * @param msg The Message that is to be processed.
     */
    protected void processMessage(Message msg) {

	if (msg.getBody().equals("CRASH")) {
	    Message m;
	    try {
		m = createWarningMessage("_UNKNOWN_EXCEPTION");
		m.send(this);
		System.out.println(m);
	    } catch (MessageDeliveryException e) {
		e.printStackTrace();
	    }
	}

	if (msg.getBody().equals("DUMP")) {
	}
    }
}
