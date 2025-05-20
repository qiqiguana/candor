package dsachat.share.hero;

import java.io.Serializable;

/**
 * a talent is the ability of the hero to do something.
 * The value describes how good the hero is in doing a task.
 * 
 * @author bernshausen
 *
 */
public class Talent implements Serializable{
	
	private static final long serialVersionUID = 7349793007364986386L;
	private String name;
	private int value;
	private String challenge;
	
	/**
	 * create a new talent
	 * @param n the name
	 * @param v the value
	 * @param c the three attributes that this talent uses
	 */
	public Talent(String n, int v, String c){
		setName(n);
		setValue(v);
		setChallenge(c);
	}
	
	/**
	 * return the name of this talent
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * set the name for this talent
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * get the value of this talent
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * set the value of this talent
	 * @param value the new value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * get the three attributes
	 * @return the attributes
	 */
	public String getChallenge() {
		return challenge;
	}
	/**
	 * set the attributes
	 * @param challenge the new attributes
	 */
	public void setChallenge(String challenge) {
		String c = challenge;
		c = c.substring(2, 10);
		this.challenge = c;
	}
	
	/**
	 * represents this class as string
	 */
	public String toString(){
		return name+" ("+challenge+") "+value;
	}
}
