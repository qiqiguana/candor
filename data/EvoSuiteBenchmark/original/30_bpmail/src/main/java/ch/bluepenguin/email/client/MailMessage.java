/*
 * Created on 22.01.2005
 * use this class for handling email messages. currently no extensions
 */
package ch.bluepenguin.email.client;

import java.io.Serializable;
import java.text.DateFormat;

import org.springframework.mail.SimpleMailMessage;

/**
 * @author Christian
 *
 */
public class MailMessage extends SimpleMailMessage implements Serializable {
	private  Integer ID;
    private Folder folder;
    private int technicalID;
	/**
	 * @return Returns the technicalID.
	 */
	public int getTechnicalID() {
		return technicalID;
	}
	/**
	 * @param technicalID The technicalID to set.
	 */
	public void setTechnicalID(int technicalID) {
		this.technicalID = technicalID;
	}
	private AbstractUniqueID uniqueID = new AbstractUniqueID();
  
	/**
	 * @return Returns the folder.
	 */
	public Folder getFolder() {
		return folder;
	}
	/**
	 * @param folder The folder to set.
	 */
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
    
	/**
	 * @return Returns the uniqueID.
	 */
	public AbstractUniqueID getUniqueID() {
		return uniqueID;
	}
		
}
