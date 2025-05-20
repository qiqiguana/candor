package dsachat.share.hero;

import java.io.Serializable;
import java.util.Vector;

/**
 * A fight value contains the values for attacking and defending.
 * It also has a name to describe the type of the values
 * @author bernshausen
 *
 */
public class FightValue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8843015037627509214L;
	private String name;
	private int attack;
	private int defense;
	private Vector<Weapon> weapons = new Vector<Weapon>();
	
	/**
	 * create new FightValue
	 * @param name the type of the weapons to be uset with this
	 * @param attack the attack value
	 * @param defense the defense value
	 */
	public FightValue(String name, int attack, int defense){
		this.name=name;
		this.attack = attack;
		this.defense=defense;
	}

	/**
	 * get the name of this values
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the attack value
	 * @return attack value
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * get the defense value
	 * @return defense value
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * Get the weapons the hero carries and that can be used with this value
	 * @return the weapons
	 */
	public Vector<Weapon> getWeapons() {
		return weapons;
	}

	/**
	 * set the attack value
	 * @param value the new value
	 */
	public void setAttack(int value) {
		attack = value;
	}
	
	/**
	 * set the defense value
	 * @param value the new defens value
	 */
	public void setDefense(int value) {
		defense = value;
	}
	
	/**
	 * return a string representation of this class
	 */
	public String toString(){
		return name+" AT: "+attack+" PA: "+defense;
	}
	
}
