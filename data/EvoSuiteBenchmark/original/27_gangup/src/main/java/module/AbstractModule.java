/* $Id: AbstractModule.java,v 1.8 2004/05/04 23:08:22 emill Exp $ 
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.8 $
 *
 */

package module;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Locale;

@cvs(file     = "$RCSfile: AbstractModule.java,v $",
     revision = "$Revision: 1.8 $",
     date     = "$Date: 2004/05/04 23:08:22 $",
     author   = "$Author: emill $",
     tag      = "$Name:  $",
     build    = "")

@mod(name     = "AbstractModule",
     topics   = "",
     cmds     = "",
     desc     = "This is module serves as abstract base class for other "  +
                "modules to faciliate creation of new modules. It cannot " +
                "be instatiated directly.") 

/**
 * This class provides skeletal implementation of the Module interface. 
 * Inorder to implement the Module interface it is ...
 *
 */
public abstract class AbstractModule implements Module {

    /** Reference to the kernel of this module. */
    protected Kernel kernel;

    /** The message queue used for storing incoming messages. */
    private List<Message> messages;
	
    /** The number of instances of this class. */
    private static int moduleCount;

    /** The ID of an instance of this module. */
    protected int moduleID;

    /** Contains module specific information. */
    protected ModuleInfo modinfo;

    /** Localized strings. */
    protected ResourceBundle locale;

    /** true if this module is not (or should not be) running. */
    private boolean done;

    /** The thread associated with this module. */
    private Thread thread;

    /**
     * Create a new instance of this Module. The module have to register
     * with a Kernel in order to receive any messages.
     */
    public AbstractModule() {

	synchronized(this) { moduleID = moduleCount++; }

	/* Get the locale specific resource file. */

	locale = ResourceBundle.getBundle("gangup", Locale.getDefault());

	/* Initialize this class' ModuleInfo structure. Values are
	   retrieved from the cvs and mod annotations made above. */

	modinfo = new ModuleInfo(this);

	/* Initialize message queue */

	messages = Collections.synchronizedList(new LinkedList<Message>());
    }

    /**
     * Create a new instance of this Module and register with the
     * specified kernel in the default class.
     *
     * Right now, generally module constructors should take the kernel 
     * module as an argument.
     *
     * @param krn The kernel associated with this module.
     *
     */
    public AbstractModule(Kernel krn)
	throws ModuleRegisterException {

	this();
	register(krn);
    }

    /**
     * Create a new instance of this Module and register with the
     * specified kernel and class.  
     *
     * @param krn The kernel associated with this module.
     * @param cls The class this module should be registered in.
     *
     */
    public AbstractModule(Kernel krn, String cls) 
	throws ModuleRegisterException, ModuleSubscriptionException {

	this(krn);
	subscribe(cls); 
    }

    /**
     * Register the specified kernel. A module is required to register with
     * the kernel in order to subscribe to messages and in turn communicate
     * with other modules.
     *
     * @param krn The kernel that this module should registered with. 
     * @throws ModuleRegisterException if the module is already registered.
     *
     */
    public void register(Module krn) 
	throws ModuleRegisterException {

	if (krn instanceof Kernel) {
	    krn.register(this);
	    kernel = (Kernel) krn;
     	}
    }

    /**
     * Unregister from specified Kernel. An unused module should unregister
     * with the kernel so that all references to that module will be freed.
     *
     * @param krn The kernel with which we wish to unregister.
     * @throws ModuleRegisterException if the module is not registered.
     */
    public void unregister(Module krn)
	throws ModuleRegisterException {
	
	if (krn instanceof Kernel) {
	    krn.unregister(this);
	}

    }

    /**
     * Subscribes this module to all messages within the specified
     * topic. When a module subscibes to a certain topic it notifies
     * the kernel that it is interrested in receiving all messages
     * that has the given topic.
     *
     * @param topic The topic on which to subscribe.
     */
    protected void subscribe(String topic) throws ModuleSubscriptionException {
	if (topic != null) {
	    kernel.subscribe(this, topic);
	}
    }

    /**
     * Start executing the run method in a separate thread. This method
     * is invoked automatically be the Kernel when the module is loaded
     * and should not be invoked manually.
     */
    public void start() {
	thread = new Thread(this, modinfo.name);
	thread.start();
    }

    /**
     * This is this module's thread entry point. It handles most of the
     * common module functionality. Subclasses should not override this
     * method in order to customize the module's behavior. Instead the 
     * subclasses should implement the init(), step(), and free() methods.
     */
    public void run() {

	init();

	done = false;

	while (!done) {
	    try {
		processMessages();
		step();
		Thread.sleep(100);
	    } catch (Exception e) {
		System.err.println(e);
		// e.printStackTrace();
	    }
	}
	
	free();
    }
    
    /**
     * Terminate the current thread. This method should be called if
     * the module wishes to terminate its main processing thread.
     */
    protected final void exit() {

	/* wonder if unregistering and unsubscribing should be 
	   placed here? */

	done = true;
    }

    /**
     * Method to run recurrently in the loop of run(). This method
     * should never be blocking, lest the module will not be able to
     * process any messages.
     */
    protected void step() {}
    
    /**
     * Initialization method for modules. Using init() is recommended
     * instead of overloading the constructor.
     */
    protected void init() {}
    
    /**
     * Exit method for modules. This method is invoked after a call to
     * the @see exit() method. Modules should overload this method with
     * code to do a proper exit.
     */
    protected void free() {}

    /**
     * Handles what should done with messages sent to this module.
     *
     * @param msg The message to be processed.
     */
    protected void processMessage(Message msg) {}
    

    /**
     * Handles what should done with kernel messages sent to this module.
     *
     * @param msg The message to be processed.
     */
    protected void processKernelMessage(Message msg) 
	throws MessageProcessingException {

	if (msg == null || msg.body == null) {
	    throw new MessageProcessingException(msg, "invalid message!");
	}

	try {
	    if (msg.body.equals("REGISTERED")) {
		subscribe(modinfo.topics);
	    } else if (msg.body.equals("UNREGISTERED")) {
		exit();
	    }
	} catch (Exception e) {
	    sendMessage(new Message("WARNING","unable to subscribe!"));
	    throw new MessageProcessingException(msg, e);
	}
    }

    /**
     * Processes and removes the messages in the queue. The messages are
     * processed in the order they were received.
     */
    private void processMessages() throws MessageProcessingException {
	Message m = null;
	try {
	    while (!messages.isEmpty()) {
		m = messages.remove(0);
		if (m.header.equals("KERNEL")) {
		    processKernelMessage(m);
		} else {
		    processMessage(m);
		}
		m.unref();
		m = null;
	    }
	} catch (Exception e) {
	    if (m != null) { m.unref(); }
	    throw new MessageProcessingException(e);
	}
    }

    /**
     * Delivers the specified message to the kernel. At the moment there's
     * no way of telling if the message got delivered or not.
     *
     * @param msg The message to be delivered.
     */
    public void sendMessage/*AndIDontCareIfItDoesntWork*/(Message msg) {
	try {
	    msg.sender = modinfo.name;
	    kernel.receiveMessage(msg);
	} catch (MessageDeliveryException e) {
	    // catch it here, it breaks to many modules if we were to
	    // let it propagate right now. 
	    e.printStackTrace();
	}
    }	
    
    /**
     * Called by the kernel to put a Message in this module's queue.
     *
     * @param msg The message that was received.
     *
     */
    public void receiveMessage(Message msg) {
	msg.ref();
	messages.add(msg);
    }

    /**
     * Requests the specified query from the specified topic. Observe that
     * if there are more than one module answering a specific request, only
     * the first one will be returned.
     *
     * @param msg The message containing the request.
     *
     * @throws MessageTimeoutException 
     * If the request times out before we receive any replies.
     *
     */
    public Message request(Message msg) 
	throws MessageTimeoutException {
	try {

	    Message r = null;
	    int latestIndex = -1;
	    long timeout = System.currentTimeMillis() + msg.timetolive;
	    sendMessage(msg);

	    /* Wait until we receive a response to our request or until
	       the specified timeout occurs. */

	    while (System.currentTimeMillis() < timeout) {

		if (!messages.isEmpty()) {

		    for (int i = messages.size()-1; i > latestIndex; i--) {
			r = messages.get(i);

			if (r.sessionID == msg.sessionID &&
			    r.messageID != msg.messageID) {
			    messages.remove(r);
			    r.unref();
			    return r;
			}
		    }

		    latestIndex = messages.size()-1;

		    /* Keep processing other messages. */
		/*
		    if (r.header.equals("KERNEL")) {
			processKernelMessage(r);
		    } else {
			processMessage(r);
		    }

		    r.unref(); */
		
		    // Thread.currentThread().yield();
		}
		
		Thread.sleep(10); 
	    }

	} catch (Exception e) {
	    // throw new MessageRequestException(e);
	} finally {
	    msg.unref();
	}

	throw new MessageTimeoutException("Sorry, your request timed out!");
    }

    /**
     * Requests the specified query from the specified topic. Observe that
     * if there are more than one module answering a specific request, only
     * the first one will be honored.
     *
     * @param topic The topic in which to make the request.
     * @param query The request to be made.
     *
     * @throws MessageTimeoutException 
     * If the request times out before we receive any replies.
     *
     */
    public Message request(String topic, String query) 
	throws MessageTimeoutException {
	return request(new Message(topic, query));
    }

    /**
     * Returns the Kernel associated with this Module.
     * @return the Kernel associated with this Module.
     */
    public Kernel getKernel() {
	return kernel;
    }

    /**
     * Returns the thread associated with this module.
     * @return The thread associated with this module.
     */
    public Thread getThread() {
	return thread;
    }    

    /**
     * Returns the module info associated whith this module.
     * @return the module info associated whith this module.
     */
    public ModuleInfo info() {
	return modinfo;
    }

    /**
     *
     *
     */
    public String toString() {
	return "" + modinfo.name + "[id=" + moduleID + "]";
    }

    /**
     *
     *
     */
    protected void finalize() {
	System.err.println(toString() + " finalized!");
    }
}
