package dsachat.share;

import java.io.Serializable;

/**
 * This class holds all parameters to request a silent challenge from a client
 * @author bernshausen
 *
 */
public class ChallReq implements Serializable{
	
	private static final long serialVersionUID = -6143517936765414870L;
	private int mod;
	private String to;
	private String responseTo;
	private Object ch;
	
	/**
	 * Create new request
	 * @param o the talent, spell or attribute
	 * @param mod the difficulty
	 * @param to the client who is requested
	 * @param response the sender to response to
	 */
	public ChallReq(Object o, int mod, String to, String response){
		this.mod=mod;
		this.ch=o;
		this.to=to;
		this.responseTo=response;
	}

	public int getMod() {
		return mod;
	}

	public String getTo() {
		return to;
	}

	public String getResponseTo() {
		return responseTo;
	}

	public Object getCh() {
		return ch;
	}
	
	
	
}
