package dsachat.share;

import java.util.EventObject;

/**
 * This is used to tell the listener that an event was called
 * 
 * @author bernshausen
 *
 */
public class InputEvent extends EventObject {
	
	private static final long serialVersionUID = -5119326126381814743L;
	private ConnectionEndpoint source;
	private Object data;

	/**
	 * Create new event
	 * @param source who sent this event
	 * @param input what was the case
	 */
	public InputEvent(ConnectionEndpoint source, Object input){
		super(source);
		this.source = source;
		data=input;
	}

	/**
	 * return the sender of this event
	 */
	public ConnectionEndpoint getSource() {
		return source;
	}

	/**
	 * return the data of this event
	 * @return the data as object
	 */
	public Object getData() {
		return data;
	}
}
