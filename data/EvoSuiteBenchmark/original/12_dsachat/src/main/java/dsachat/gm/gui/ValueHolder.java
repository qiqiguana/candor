package dsachat.gm.gui;

import dsachat.share.hero.Hero;
/**
 * This class is used to set up the tree view of multiple heros
 * @author bernshausen
 *
 */
public class ValueHolder {
	public String name;
	public Hero parent;
	
	/**
	 * create
	 * @param name the name of this value
	 * @param parent the parent hero
	 */
	public ValueHolder(String name, Hero parent){
		this.name=name;
		this.parent=parent;
	}
	
	/**
	 * this class is represented by the name it holds
	 */
	public String toString(){
		return name;
	}
}
