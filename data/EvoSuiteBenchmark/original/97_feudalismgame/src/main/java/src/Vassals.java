package src;

import java.io.Serializable;

/**
 * @author  mpretel
 */
public class Vassals implements Serializable, Player{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lord;
	private String fiefdom;
	private String location;
	private String rank = "vassal";
	
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Vassals(){
		
	}

	public Vassals(String lord, String fiefdom){
		this.lord = lord;
		this.fiefdom = fiefdom;
	}

	/**
	 * @return
	 * @uml.property  name="fiefdom"
	 */
	public String getFiefdom() {
		return fiefdom;
	}

	/**
	 * @param fiefdom
	 * @uml.property  name="fiefdom"
	 */
	public void setFiefdom(String fiefdom) {
		this.fiefdom = fiefdom;
	}

	/**
	 * @return
	 * @uml.property  name="lord"
	 */
	public String getLord() {
		return lord;
	}

	/**
	 * @param lord
	 * @uml.property  name="lord"
	 */
	public void setLord(String lord) {
		this.lord = lord;
	}

	/**
	 * @return
	 * @uml.property  name="location"
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param position
	 * @uml.property  name="location"
	 */
	public void setLocation(String position) {
		this.location = position;
	}

	public void kill() {
		
	}

	public void wound() {
		Fiefdoms fief = Map.map.get(getFiefdom());
		fief.getLoyalVassals().add(this);
		setLocation(getFiefdom());
	}

}
