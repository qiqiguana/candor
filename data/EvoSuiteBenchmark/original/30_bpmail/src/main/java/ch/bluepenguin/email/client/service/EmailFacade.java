package ch.bluepenguin.email.client.service;

import ch.bluepenguin.email.client.Folder;
import ch.bluepenguin.email.client.MailMessage;


/**
 * @author Christian
 *
 * This should define the external interface usable by any client
 * app
 */
public interface EmailFacade{

    public void sendMessage(String from, String to, 
            String subject, String message);
    /**
     *
     * @return folders in depth first order
     */
    public Folder[] getAllFolders() throws EmailFacadeException ;
    /**
     * returns message list, but no message bodies filled
     */
    public MailMessage[] getAllMessages() throws EmailFacadeException ;
    /**
     * returns message list, but no message bodies filled
     */
    public MailMessage[] getMessagesFromFolder(Folder folder) throws EmailFacadeException ;
    
    public String getName();
    
    public void copyMessages(MailMessage[] messages, String folderFrom, String folderTo)throws EmailFacadeException ;

    public void moveMessages(MailMessage[] messages, String folderFrom, String folderTo)throws EmailFacadeException ;

    /**
     * returns message with message body filled
     */
    //public MailMessage getMessage(Integer id , String folderName) throws EmailFacadeException ;
    public void fillMessageBody(MailMessage message) throws EmailFacadeException;
    /**
     * if true, there is still some data not loaded yet from the backend
     * @return
     */
	public boolean isDirty();
	
	/**
	 * set this flag, and all data will be reloaded from the backend the next time
	 * it is acessed
	 * @param dirty
	 */
	public void setDirty(boolean dirty);
}
