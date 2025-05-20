package net.sf.xbus.technical;

/**
 * A class implementing the <code>ReceiverSingleInterface</code> is executing
 * the <code>receive</code> method one time per <code>System</code> to
 * receive one or more message. The <code>receive</code> method is invoked
 * from <code>SingleReceiver</code>.
 */
public interface ReceiverSingleInterface
{
	/**
	 * The implementation of the <code>receive</code> method shall receive one
	 * or more messages from the given system.
	 * 
	 * @param system The name of the system from which messages shall be
	 *            received.
	 */
	public void receive(String system);
}
