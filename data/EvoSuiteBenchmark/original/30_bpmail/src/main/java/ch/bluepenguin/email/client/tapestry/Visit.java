/*
 * Created on 08.01.2005
 *
 */
package ch.bluepenguin.email.client.tapestry;

/**
 * @author Christian
 * 
 * Main visit class, this is the "session" state of tapestry
 */
public class Visit {
	private String userID;
   
	public void setUserID(String userID) {
   	  this.userID = userID;
   }
	
	public String getUserID() {
		return userID;
	}
}
