/* $Id: Kernel.java,v 1.7 2004/05/01 23:20:45 bja Exp $ 
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.7 $
 *
 */

package module;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Collections;

@cvs(file     = "$RCSfile: Kernel.java,v $",
     revision = "$Revision: 1.7 $",
     date     = "$Date: 2004/05/01 23:20:45 $",
     author   = "$Author: bja $",
     tag      = "$Name:  $",
     build    = "")

@mod(name     = "Kernel",
     topics   = "KERNEL",
     cmds     = "DUMP LOAD UNLOAD LIST EXISTS",
     desc     = 
     "This class provides a message delivery system. It's primary task is " +
     "to manage loaded modules. It has methods for loading and unloading "  +
     "modules, subscribing and unsubscribing modules to specified topics, " +
     "and for creating and destroying sessions.")

/**
 * This class provides a message delivery system. It's primary task is to 
 * manage loaded modules. It has methods for loading and unloading modules,
 * subscribing and unsubscribing modules to specified topics, and for creating
 * and destroying sessions.
 */
public class Kernel implements Module  {

    /** Topic to subcribers lookup table. */
    private Hashtable<String, Vector<Module>> subscribers;

    /** Module name lookup table. */
    private Hashtable<String, Module> modules;

    /** Message queue. */
    private List<Message> messages;

    /** The thread associated with this module. */
    private Thread thread;

    /** Contains module specific information. */
    private ModuleInfo modinfo = new ModuleInfo(this);

    /** Specifies whether the Kernel should stop running. */
    private boolean done = false;

    /**
     * Create a new instance of the Kernel module. There has to be at least
     * one kernel up and running in order to have messages processed.
     */
    public Kernel() {

	/* Create the lookup table of module subscriptions */
	subscribers = new Hashtable<String, Vector<Module>>();
	
	/* Create the table of registered modules */
	modules = new Hashtable<String, Module>();

	/* Initialize message queue */
	messages = Collections.synchronizedList(new LinkedList<Message>());

	/* fixme - for now! */
	start();
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
     * This is the kernels main thread routine. Its sole purpose is to
     * regularly process the messagequeue.
     */
    public void run() {
	while (!done) {
	    try {
		processMessages();
		thread.sleep(10);
	    } catch (Exception e) {
		System.err.println(e);
	    }
	}
    }

    /**
     * Load and register the specified module. This method provides
     * a mechanism for dynamically loading modules to the runtime.
     *
     * @param name The name of the module that is to be loaded.
     * @throws ModuleLoadException If an exception occurred.
     */
    public void loadModule(String name) 
	throws ModuleLoadException {

	try {
	    Class c = getClass().forName(name);
	    Module m = (Module) c.newInstance();
	    m.register(this);
	    m.start();
	} catch (Exception e) {
	    throw new ModuleLoadException(e);
	}
    }

    /**
     * Unload and unregister the specified module. Modules that are not
     * needed any more may be unloaded from the kernel to save resources.
     *
     * @param mod The module that is to be unloaded.
     *
     * @throws ModuleRegisterException If an register exception occurred.
     * @throws ModuleUnloadException If an exception occurred.
     */
    public void unloadModule(Module mod) 
	throws ModuleRegisterException {
	unregister(mod);
    }

    /**
     * Unload and unregister the specified module. Modules that are not
     * needed any more may be unloaded from the kernel to save resources.
     *
     * @param name The name of the module that is to be unloaded.
     *
     * @throws ModuleUnloadException If an exception occurred.
     */
    public void unloadModule(String name) 
	throws ModuleUnloadException {
	try {
	    Module m = modules.get(name);
	    unloadModule(m);
	    m.getThread().join(100);
	    m = null;
	} catch (Exception e) {
	    throw new ModuleUnloadException("mod=" + name, e);
	}
    }

    /**
     * Register the specified module with this kernel. A module is required
     * to register with the kernel in order to subscribe to messages and
     * in turn communicate with other modules.
     *
     * @param mod The module that is to be registered. 
     * @throws ModuleRegisterException if the module has already registered.
     */
    public void register(Module m) 
	throws ModuleRegisterException {

	Message msg = null;

	System.err.println("KERNEL REGISTERED: " + m);

	try {

	    if (modules.containsKey(m.info().name)) {
		throw new ModuleRegisterException(m,
                   "A module with that name has already been registered: " +
		    "name=" + m.info().name);
	    }

	    // Add the module to the collection of registered modules.
	    modules.put(m.info().name, m);

	    // Let the module know it has been registered.
	    //sendMessage(m, new Message("KERNEL","REGISTERED"));

	    msg = MessageFactory.createMessage("KERNEL",m.getClass().getName(),"REGISTERED");
	    
	    

	    // MessageFactory.getInstance().sendRegisterMessage(this, m);

	    sendMessage(m, msg);

	} catch (ModuleRegisterException e) {
	    throw e;
	} catch (Exception e) {
	    throw new ModuleRegisterException(m, e);
	} finally {
	    if (msg != null) msg.unref();
	}
    }

    /**
     * Unregister the specified module. An unused module should unregister
     * with the kernel so that all references to that module will be freed.
     *
     * @param mod The module that is to be unregistered.
     * @throws ModuleRegisterException if the module is not registered.
     */
    public void unregister(Module m)
	throws ModuleRegisterException {
	try {

	    if (!modules.containsKey(m.info().name)) {
		throw new ModuleRegisterException(m, "Not registered!");
	    }

	    // maybe this should be synchronized

	    unsubscribe(m);
	    modules.remove(m.info().name);

	    //MessageFactory.getInstance().sendUnregisterMessage(this, m);

	    sendMessage(m, new Message("KERNEL", "UNREGISTERED"));

	} catch (ModuleRegisterException e) {
	    throw e;
	} catch (Exception e) {
	    throw new ModuleRegisterException(m, e);
	} 
    }

    /**
     * Adds module as a subscriber of all messages with the 
     * specified topic.
     *
     * @param mod The module that is to subscribe to topic.
     * @param topic The topic that should be subscribed to.
     */
    public void subscribe(Module mod, String topics) 
	throws ModuleSubscriptionException {

	try {

	    String[] top = topics.split("(,|\\s+)");

	    for (int i=0; i<top.length; i++) {
		if (subscribers.containsKey(top[i])) {
		    subscribers.get(top[i]).add(mod);
		} else {
		    Vector<Module> nv = new Vector<Module>();
		    nv.add(mod);
		    subscribers.put(top[i], nv);
		}
	    }

	} catch (Exception e) {
	    throw new ModuleSubscriptionException(mod, "topics=" + topics, e);
	}
    }

    /**
     * Unsubscribes the specified module from every topic it is 
     * subscribing to. Modules will still receive messages from
     * the kernel though.
     *
     * @param mod The module that is to be unsubscribed.
     */
    public void unsubscribe(Module mod) {

	// fixme - this is really ugly, O(t+ts) may easily be O(t)!

	for (Vector v : subscribers.values()) {
	    v.remove(mod);
	}
    }

    /**
     * Unsubscribes the specified module from the given topic. Modules may
     * call this method to instruct the kernel that they do not whish to
     * receive messages in the specified topic.
     *
     * @param mod The module that is to be unsubscribed from topic.
     * @param topic The topic from which we wish to unsubscribe.
     *
     * @throws ModuleSubscriptionException If either the topic or module are
     * null.
     */
    public void unsubscribe(Module mod, String topic) 
	throws ModuleSubscriptionException {
	try {
	    if (subscribers.containsKey(topic)) {
		Vector t = subscribers.get(topic);
		t.remove(mod);
		if (t.size() == 0) {
		    subscribers.remove(topic);
		}
	    }
	} catch (Exception e) {
	    throw new ModuleSubscriptionException(e);
	}
    }

    /**
     * Requests the specified query from the specified topic. Observe that
     * if there are more than one module answering a specific request, only
     * the first one will be returned.
     * 
     * @deprecated Requests are currently not used by the Kernel.
     *
     * @param mod The module that should receive the request.
     * @param key A string containing the actual request.
     */
    public Object request(String mod, String key) 
	throws MessageTimeoutException {
	throw new MessageTimeoutException("Requests are not implemented");
    }

    /**
     * Requests the specified query from the specified topic. Observe that
     * if there are more than one module answering a specific request, only
     * the first one will be returned.
     *
     * @deprecated Requests are currently not used by the Kernel.
     *
     * @param msg The message containing the request.
     *
     * @throws MessageTimeoutException 
     * If the request times out before we receive any replies.
     *
     */
    public Message request(Message msg) throws MessageTimeoutException {
	throw new MessageTimeoutException(
	    "Sorry, requests are not implemented in Kernel");
    }

    /**
     *
     */
    public ModuleInfo info() {
	return modinfo;
    }
    
    /**
     * Delivers the specified message to the kernel.
     * @param msg The message to be delivered.
     * @throws MessageDeliveryException if message could not be delivered.
     */
    public void sendMessage(Message msg) throws MessageDeliveryException {
	try {
	    sendMessage(modules.get(msg.recipient), msg);
	} catch (Exception e) {
	    throw new MessageDeliveryException(e);
	}
    }

    /**
     * Delivers the specified message to the given module.
     *
     * @param mod the module that should receive the message.
     * @param msg the message that should be sent.
     * @throws MessageDeliveryException if message could not be delivered.
     */
    private void sendMessage(Module mod, Message msg) 
	throws MessageDeliveryException {
	try {
	    mod.receiveMessage(msg);
	} catch (Exception e) {
	    throw new MessageDeliveryException(mod,msg,e);
	}
    }

    /**
     * Receives a message from another module. This function is public
     * and should be called from the other module.
     *
     * @param msg The message to receive.
     */
    public void receiveMessage(Message msg) 
	throws MessageDeliveryException {
	try {
	    msg.ref();
	    messages.add(msg);
	    //msg.setState(Message.SENT);
	} catch (Exception e) {
	    msg.unref();
	    throw new MessageDeliveryException(this,msg,e);
	}
    }


    /**
     * Handles what should done with messages sent to this module.
     *
     * @param msg The message to be processed.
     * @throws Exception if the processing failed for this message.
     */
    protected void processMessage(Message msg) 
	throws Exception {

	String[] cmd = null;
	
	cmd = ((String) msg.body).split(" ");

	if (cmd.length == 0) {
	    return;
	}

	if (cmd[0].equals("LOAD")) {
	    loadModule(cmd[1]);
	    return;
	}
	
	if (cmd[0].equals("UNLOAD")) {
	    unloadModule(cmd[1]);
	    return;
	}

	if (cmd[0].equals("DUMP")) {

	    sendMessage(modules.get(msg.sender),
			msg.reply(modules.toString()));

	    sendMessage(modules.get(msg.sender),
			msg.reply(subscribers.toString()));
	    return;
	}
    }

    /** 
     * Process messages from the message queue.
     *
     * @throws MessageProcessingException if processing failed for any 
     * message in the message queue.
     */
    private void processMessages() throws MessageProcessingException {

	Message msg = null;
	Module dst = null;

	try {

	    while (!messages.isEmpty()) {
	    
		msg = messages.remove(0);

		/* intercept messages destined for the kernel */

		if (msg.header.equals("KERNEL")) {
		    processMessage(msg);
		}

		/* try sending message directly to recipient, if not null,
		   otherwise send to everyone that is subscribing to the
		   specified topic. */

		dst = modules.get(msg.getRecipient());

		if (dst != null) {
		    sendMessage(dst, msg);
		} else if (subscribers.containsKey(msg.getHeader())) {
		    sendMessageNotify(subscribers.get(msg.getHeader()), msg);
		}
	
		/* Notify modules subscribing on all Message classes. */

		if (subscribers.containsKey("*")) {
		    sendMessageNotify(subscribers.get("*"), msg);
		}

		msg.setDelivered(true);
		msg.unref();
	    } 

	} catch (Exception e) {
	    if (msg != null) { msg.unref(); }
	    throw new MessageProcessingException(msg, e);
	}
    }

    /**
     * Sends the specified message to the given collection of modules. 
     * The modules are notified of the delivery (i.e. the thread is notified.)
     *
     * @param mods the modules that should receive the message.
     * @param msg the message that should be delivered.
     * @throws MessageDeliveryException if the message could not be delivered.
     */
    public void sendMessageNotify(Collection<Module> mods, Message msg) 
	throws MessageDeliveryException {
	for (Module mod : mods) {
	    sendMessage(mod, msg);
	    synchronized(mod) { mod.notify(); }
	}
    }

    /**
     * Returns the Kernel associated with this Module.
     * @return the Kernel associated with this Module.
     */
    public Kernel getKernel() {
	return null;
    }

    /**
     * Returns the thread associated with this module.
     * @return The thread associated with this module.
     */
    public Thread getThread() {
	return thread;
    }    
}
