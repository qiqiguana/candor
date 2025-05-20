package dsachat.share;

import java.io.Serializable;

/**
 * this class is used to send an encoded key
 * @author bernshausen
 *
 */
public class KeyExc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7043539794016807791L;
	private byte[] b;
	
	/**
	 * new Exchange
	 * @param key the key to send
	 */
	public KeyExc(byte[] key){
		b=key;
	}
	
	/**
	 * get the key which is transfered
	 * @return the key
	 */
	public byte[] getKey(){
		return b;
	}
}
