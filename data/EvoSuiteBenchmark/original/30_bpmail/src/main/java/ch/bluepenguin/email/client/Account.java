/*
 * Created on 09.06.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.bluepenguin.email.client;

import java.io.Serializable;

/**
 * @author Christian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Account implements Serializable{
	private Person person;
	private AbstractUniqueID uniqueID = new AbstractUniqueID();
	/**
	 * @return Returns the person.
	 */
	public Person getPerson() {
		return person;
	}
	/**
	 * @param person The person to set.
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/*
	 * @return Returns the uniqueID.
	 */
	public AbstractUniqueID getUniqueID() {
		return uniqueID;
	}
}
