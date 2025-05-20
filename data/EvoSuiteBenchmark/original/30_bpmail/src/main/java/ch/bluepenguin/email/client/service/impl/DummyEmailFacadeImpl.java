/*
 * Created on 20.10.2004
 *
 */
package ch.bluepenguin.email.client.service.impl;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.bluepenguin.email.client.Folder;
import ch.bluepenguin.email.client.IDGenerator;
import ch.bluepenguin.email.client.MailMessage;
import ch.bluepenguin.email.client.service.EmailFacade;
import ch.bluepenguin.email.client.service.EmailFacadeException;

/**
 * @author Christian
 *
 */
public class DummyEmailFacadeImpl /*implements EmailFacade*/ {

    //dependency injected stuff
    Logger logger;
    private int count=0;
	private String folderName;
    
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    /* (non-Javadoc)
     * @see ch.bluepenguin.email.client.service.EmailFacade#sendMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void sendMessage(String from, String to, String subject,
            String message) {
        logger.log(Level.INFO, "sendMessage called");
    }

    /* (non-Javadoc)
     * @see ch.bluepenguin.email.client.service.EmailFacade#getAllFolders()
     */
    public Folder[] getAllFolders() {
    	Folder[] result = new Folder[3];
    	
   	 for(int i=0;i<result.length;i++) {
	 	result[i]= new Folder();
	 	result[i].getUniqueID().setId(IDGenerator.next());
	 }
    	result[0].setName("INBOX");
    	result[1].setName("IMPORTANT");
    	result[1].setParent(result[0]);
    	result[2].setName("DUMMY");
        return  result;
    }

    /* (non-Javadoc)
     * @see ch.bluepenguin.email.client.service.EmailFacade#getAllMessagesFromFolder(java.lang.String)
     */
    public MailMessage[] getAllMessages() {
    	return getMessagesFromFolder("NO Folder selectet! ");
    }


	/* (non-Javadoc)
	 * @see ch.bluepenguin.email.client.service.EmailFacade#getName()
	 */
	public String getName() {
		return "Dummy EmailFacade Impl";
	}

	/* (non-Javadoc)
	 * @see ch.bluepenguin.email.client.service.EmailFacade#setFolder(java.lang.String)
	 */
	public void setCurrentFolder(String folderName) {
		this.folderName = folderName;
		
	}
	
	
	
    public MailMessage[] getMessagesFromFolder(String folderName) {
    	MailMessage[] result = new MailMessage[3];
     	 for(int i=0;i<result.length;i++) {
     	 	//TODO: make this configurable
    	 	result[i]= new MailMessage();
    	 	result[i].getUniqueID().setId(IDGenerator.next());
    	 }

    	result[0].setSubject(folderName + " Very important message " + count++);
    	result[0].setFrom("cs@bluepenguin.ch");
    	result[0].setTo("noone@nowhere.com");
    	result[0].setSentDate(new Date(1999,20,12));
    	result[0].setCc("cc@dd.ee");
    	result[0].setText("short message");
    	result[1].setSubject(folderName + " not too important message");
    	result[1].setFrom("cs@bluepenguin.ch");
    	result[1].setTo("noone@nowhere.com");
    	result[1].setSentDate(new Date(1999,20,12));
    	result[1].setCc("cc@dd.ee");
    	result[1].setText("short message");
    	result[2].setSubject(folderName + " spam!");
    	result[2].setFrom("cs@bluepenguin.ch");
    	result[2].setTo("noone@nowhere.com");
    	result[2].setSentDate(new Date(1999,20,12));
    	result[2].setCc("cc@dd.ee");
    	result[2].setText("short message");
    	return result; 
    }

	/* (non-Javadoc)
	 * @see ch.bluepenguin.email.client.service.EmailFacade#getFolder(java.lang.String)
	 */
	public Folder getCurrentFolder() {
		Folder result = new Folder();
		result.setName("Current Folder");
		return result;
		
	}

	/* (non-Javadoc)
	 * @see ch.bluepenguin.email.client.service.EmailFacade#getMessage(int, java.lang.String)
	 */
	public MailMessage getMessage(int id, String folderName) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Method not implemented");
	}

	/* (non-Javadoc)
	 * @see ch.bluepenguin.email.client.service.EmailFacade#copyMessages(ch.bluepenguin.email.client.MailMessage[], java.lang.String, java.lang.String)
	 */
	public void copyMessages(MailMessage[] messages, String folderFrom, String folderTo) throws EmailFacadeException {
		// TODO Auto-generated method stub
		throw new RuntimeException("Method not implemented");
		
	}

	/* (non-Javadoc)
	 * @see ch.bluepenguin.email.client.service.EmailFacade#moveMessages(ch.bluepenguin.email.client.MailMessage[], java.lang.String, java.lang.String)
	 */
	public void moveMessages(MailMessage[] messages, String folderFrom, String folderTo) throws EmailFacadeException {
		// TODO Auto-generated method stub
		throw new RuntimeException("Method not implemented");
	}

}
