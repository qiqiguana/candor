package org.exolab.jms.net.multiplexer;



/**
 * Listener for {@link Multiplexer} events.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2006/12/16 12:37:17 $
 */
public interface MultiplexerListener {

    /**
     * Invoked for an invocation request.
     *
     * @param channel the channel the invocation is on
     */
    void request(Channel channel);

    /**
     * Invoked when the connection is closed by the peer.
     */
    void closed();

    /**
     * Invoked when an error occurs on the multiplexer.
     *
     * @param error the error
     */
    void error(Throwable error);

    /**
     * Notifies of a successful ping.
     *
     * @param token the token sent in the ping
     */
    void pinged(int token);

}
