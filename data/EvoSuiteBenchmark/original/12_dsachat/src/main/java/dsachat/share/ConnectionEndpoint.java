package dsachat.share;

import java.io.IOException;

/**
 * This interface represents the behavior of a connection endpoint.
 * @author bernshausen
 *
 */
public interface ConnectionEndpoint {
	
	/**
	 * send an object
	 * @param o the object
	 * @throws IOException
	 */
	public void send(Object o) throws IOException;
	/**
	 * add listener to a list
	 * @param l the listener
	 */
	public void addInputListener(InputListener l);
	/**
	 * remove the listener from the list
	 * @param l the listener
	 */
	public void removeInputListener(InputListener l);
	/**
	 * get the port of the client
	 * @return the port
	 */
	public int getClientPort();
	/**
	 * Return the state of the connection
	 * @return true, after key exchange was successful
	 */
	public boolean isConnected();
}
