/**
 * 
 */
package client.network;

import messages.Message;


/**
 * @author Andi
 *@deprecated
 */
public interface MsgProcessor {

	public void processMsg(Message msg);
	
}
