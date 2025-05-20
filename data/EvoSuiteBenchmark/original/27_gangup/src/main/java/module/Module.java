/* $Id: Module.java,v 1.4 2004/04/27 19:26:21 bja Exp $ 
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.4 $
 *
 */

package module;

import java.lang.annotation.*;

/**
 * Interface for modules.
 *
 */
public interface Module extends Runnable {

    /**
     * Register the specified kernel. A module is required to register with
     * the kernel in order to subscribe to messages and in turn communicate
     * with other modules.
     *
     * @param mod The kernel that this module should registered with. 
     * @throws ModuleRegisterException if the module is already registered.
     *
     */
    public void register(Module mod) throws ModuleRegisterException;

    /**
     * Unregister from specified Kernel. An unused module should unregister
     * with the kernel so that all references to that module will be freed.
     *
     * @param mod The kernel with which we wish to unregister.
     * @throws ModuleRegisterException if the module is not registered.
     */
    public void unregister(Module mod) throws ModuleRegisterException;

    /**
     * Delivers the specified message to the kernel.
     * @param msg The message to be delivered.
     * @throws MessageDeliveryException if message could not be delivered.
     */
    public void sendMessage(Message msg) throws MessageDeliveryException;

    /**
     * Receives a message. Called from the kernel to add a message
     * this module is currently listening for.
     *
     * @param msg The message that is to be received.
     * @throws MessageDeliveryException if message could not be delivered.
     */
    public void receiveMessage(Message msg) throws MessageDeliveryException;

    /**
     * Returns the Kernel associated with this Module.
     * @return the Kernel associated with this Module.
     */
    public Kernel getKernel();

    /**
     * Requests the specified query from the specified topic. Observe that
     * if there are more than one module answering a specific request, only
     * the first one will be returned.
     *
     * @param msg The message containing the request.
     *
     * @throws MessageTimeoutException If the request times out before any
     * replies have been received.
     *
     */
    public Message request(Message msg) throws MessageTimeoutException; 

    /**
     * Returns the ModuleInfo associated with this Module.
     * @return the ModuleInfo associated with this Module.
     */
    public ModuleInfo info();

    /**
     * Returns the thread associated with this module.
     * @return The thread associated with this module.
     */
    public Thread getThread();

    /**
     * Start executing the run method in a separate thread. This method
     * is invoked automatically be the Kernel when the module is loaded
     * and should not be invoked manually.
     */
    public void start();
}
