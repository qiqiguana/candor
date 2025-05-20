package dsachat.share.hero;

import java.io.Serializable;

/**
 * a spell is like a talent, only different
 * @author bernshausen
 *
 */
public class Spell extends Talent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5760627863734848549L;

	/**
	 * create a new spell
	 * @param name the name
	 * @param value the value
	 * @param chal the three attributes used for the challenge
	 */
	public Spell(String name, int value, String chal){
		super(name,value,chal);
	}
}
