/* $Id: NetworkModule.java,v 1.3 2004/04/27 19:26:21 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emillkth.se>
 * @version: $Revision: 1.3 $
 *
 */

package module;

/**
 * Interface for the NetworkModules, ServerNetworkModule and
 * ClientNetworkModule.
 */
interface NetworkModule extends Module {

    /**
     * Unpacks an incoming message from the network represented by
     * data and sends it internally.  Called by the Connection class.
     *
     * @param type the type of data which was received.
     * @param data the actula data received.
     * @param id the client id.
     */
    public void receiveNetworkMessage(int type, byte[] data, int id)
	throws NetworkException;

    /**
     * Invoked by the Connection class whenever the connection is
     * closed.
     *
     * @param c the connection which should be deleted.
     */
    public void deleteConnection(Connection c);
}
