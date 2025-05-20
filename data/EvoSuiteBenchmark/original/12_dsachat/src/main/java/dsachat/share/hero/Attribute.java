package dsachat.share.hero;

import java.io.Serializable;

/**
 * this class represents an attribute of a hero.
 * an attribute has a name, a shortcut and a value.
 * @author bernshausen
 *
 */
public class Attribute implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9100340328348471402L;
	private int value;
	private String name;
	private String shortcut;
	
	/**
	 * create new attribute
	 * @param name the name
	 * @param shortcut the shortcut
	 * @param value the value
	 */
	public Attribute(String name,String shortcut, int value){
		this.name = name;
		this.value=value;
		this.shortcut=shortcut;
	}

	/**
	 * return the value of this attribute
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * get the full name of this attribute
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the shortcut for this attribute
	 * @return the shortcut
	 */
	public String getShortcut() {
		return shortcut;
	}
	
	/**
	 * represents this class as String
	 */
	public String toString(){
		return name+" ("+shortcut+") :"+value;
	}

}
