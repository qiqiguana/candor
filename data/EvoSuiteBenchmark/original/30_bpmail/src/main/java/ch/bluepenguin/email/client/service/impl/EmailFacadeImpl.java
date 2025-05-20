
/*
 * Created on 20.10.2004
 *
 */
package ch.bluepenguin.email.client.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailReader;

import ch.bluepenguin.email.client.Account;
import ch.bluepenguin.email.client.Folder;
import ch.bluepenguin.email.client.IDGenerator;
import ch.bluepenguin.email.client.MailMessage;
import ch.bluepenguin.email.client.cache.MailCache;
import ch.bluepenguin.email.client.service.EmailFacade;
import ch.bluepenguin.email.client.service.EmailFacadeException;

/**
 * @author Christian
 *
 */
public class EmailFacadeImpl implements EmailFacade {

    //dependency injected stuff
    private Logger logger;
    private MailSender sender;
    private JavaMailReader reader;
    private EmailFacadeState state = new EmailFacadeState();
    private MailCache cache;
    //TODO: multiple accounts
	private Account account = new Account();
	
	public EmailFacadeImpl() {
		account.getUniqueID().setId(IDGenerator.next());
	}
	/**
	 * @return Returns the cache.
	 */
	public MailCache getCache() {
		return cache;
	}
	/**
	 * @param cache The cache to set.
	 */
	public void setCache(MailCache cache) {
		this.cache = cache;
		setDirty(true);
	}
	/**
	 * @return Returns the dirty.
	 */
	public boolean isDirty() {
		return !state.isAllClean();
	}
	/**
	 * @param dirty The dirty to set.
	 */
	public void setDirty(boolean dirty) {
		state.setAll(dirty);
	}

	public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    public Logger getLogger() {
        return logger;
    }
    /* (non-Javadoc)
     * @see ch.bluepenguin.email.client.service.EmailFacade#sendMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void sendMessage(String from, String to, String subject,
            String message) {
        logger.log(Level.INFO, "sendMessage called");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);
        sender.send(mail);
        logger.log(Level.INFO, "sendMessage finished");
    }

    /* (non-Javadoc)
     * @see ch.bluepenguin.email.client.service.EmailFacade#getAllFolders()
     */
    public Folder[] getAllFolders() throws EmailFacadeException {
    	Folder[] myFolders = null;
    	if(isDirty()) {
    		//clear the state. This leads to all messages beeing dirty
    		// too
	    	state.clear();
	    	Vector folders = reader.getAllFolders();
	    	myFolders = new Folder[folders.size()];
			ArrayList folderIDs = new ArrayList();
			cache.addElement(account.getUniqueID().getId(), account, null);
	    	for(int i=0; i<folders.size(); i++) {
				myFolders[i] =convertFolder((javax.mail.Folder)folders.get(i));
		    	//this will mark the folder as beeing dirty, and will lead to 
				// the messages being read
				state.setState(myFolders[i].getUniqueID().getId(), true);
		    	//add the folder to the cache without children
				cache.addElement(myFolders[i].getUniqueID().getId(), myFolders[i], null);
				//add the folders to the cache, as children of an account
				//TODO: account handling!
				myFolders[i].setAccount(account);
				folderIDs.add(myFolders[i].getUniqueID().getId());
	    	}
	    	logger.info("adding folders to cache");
	    	cache.addElement(account.getUniqueID().getId(), account, folderIDs);
    	} else {
	    	myFolders = getCachedFolders();
    	}
        return myFolders;
    }

	private MailMessage[] getCachedMessagesFromFolder(Folder folder) throws EmailFacadeException {
		MailMessage[] result;
		logger.info("reading messages from cache");
		List messages = cache.getChildKeys(folder.getUniqueID().getId());
		//might be out of cache already
		if(messages==null) {
			logger.warning("The messages of folder where not found in the cache: " + folder.getFullName());
			setDirty(true);
			//should work!
			result = getMessagesFromFolder(folder);
		}else {
			result= new MailMessage[messages.size()];
			for(int i=0; i<messages.size(); i++) {
				//TODO: this might fail too...
				result[i] = (MailMessage) cache.getElement((Integer) messages.get(i));
			}
		}
		return result;
	}

	
	/**
	 * @return
	 * @throws EmailFacadeException
	 */
	private Folder[] getCachedFolders() throws EmailFacadeException {
		Folder[] myFolders;
		logger.info("reading folders from cache");
		List folders = cache.getChildKeys(account.getUniqueID().getId());
		//might be out of cache already
		if(folders==null) {
			logger.warning("The folders of account where not found in the cache: " + account.getUniqueID().getId());
			setDirty(true);
			//should work!
			//TODO risk of loops
			myFolders = getAllFolders();
		}else {
			myFolders = new Folder[folders.size()];
			for(int i=0; i<folders.size(); i++) {
				//TODO: this might fail too...
				myFolders[i] = (Folder) cache.getElement((Integer) folders.get(i));
			}
		}
		return myFolders;
	}
	/**
	 * @param current
	 * @return
	 * @throws EmailFacadeException
	 */
	private Folder convertFolder(javax.mail.Folder current) throws EmailFacadeException {
		Folder myFolder = new Folder();
		myFolder.getUniqueID().setId(IDGenerator.next());
		myFolder.setName(current.getName());
		myFolder.setFullName(current.getFullName());
		
		try {
			if(current.getParent()!=null && current.getParent().getName()!=null 
					&! "".equals(current.getParent().getName())) {
				javax.mail.Folder parent =current.getParent(); 
				Folder myParent = new Folder();
				myParent.getUniqueID().setId(IDGenerator.next());
				myParent.setName(parent.getName());
				myParent.setFullName(parent.getFullName());
				myFolder.setParent(myParent);
			}
		} catch (MessagingException e) {
			logger.severe("convertFolder failed with " + e);
			EmailFacadeException ex = new EmailFacadeException();
			ex.setTechnicalException(e);
			throw ex;
		}
		return myFolder;
	}

	/* (non-Javadoc)
	 * @see ch.bluepenguin.email.client.service.EmailFacade#getName()
	 */
	public String getName() {
		return "Default EmailFacade Impl";
	}

	
	/* (non-Javadoc)
	 * @see ch.bluepenguin.email.client.service.EmailFacade#getAllMessages()
	 */
	//TODO: exchang this with loop over all folders!
	public MailMessage[] getAllMessages() throws EmailFacadeException {

		ArrayList allMessages = new ArrayList(); 
    	int completeSize=0;
		
    	logger.info("getAllMessages: resetting cache");
    	//THIS WILL SET THE DIRTY FLAG ANYWAYS
    	Folder[] folders = getAllFolders();
    	for(int i =0; i<folders.length; i++) {
    		MailMessage[] messagesFromFolder = getMessagesFromFolder(folders[i]);
    		completeSize+=messagesFromFolder.length;
    		allMessages.add(messagesFromFolder);
    	}
    	//everything read, incl folders and messages
		setDirty(false);
		MailMessage[] result = new MailMessage[completeSize];
		int count =0;
		for(int i=0; i<allMessages.size();i++) {
			MailMessage[] messagesFromFolder = (MailMessage[]) allMessages.get(i);
			for(int j=0; j<messagesFromFolder.length;j++) {
				result[count]=messagesFromFolder[j];
				count++;
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see ch.bluepenguin.email.client.service.EmailFacade#getMessagesFromFolder(java.lang.String)
	 */
	public MailMessage[] getMessagesFromFolder(Folder folder) throws EmailFacadeException {
		MailMessage[] result=null;
    	try {
    		
		if(state.isDirty(folder.getUniqueID().getId())) {
	    	logger.info("reading messages directly for folder " + folder.getFullName());
			result = convertMessages(reader.getAllMessages(folder.getFullName()));
			ArrayList messageIDs = new ArrayList();
			for(int i=0; i<result.length;i++) {
				state.setState(result[i].getUniqueID().getId(),false);
				messageIDs.add(result[i].getUniqueID().getId());
				getCache().addElement(result[i].getUniqueID().getId(),result[i],null);
			}
			getCache().addElement(folder.getUniqueID().getId(),folder,messageIDs);
			state.setState(folder.getUniqueID().getId(), false);
		} else {
	    	logger.info("reading messages from cache for folder " + folder.getFullName());
	    	//lookup one folder from cache
	    	result =  getCachedMessagesFromFolder(folder);
			
		}
			return result;
		} catch (MessagingException e) {
			logger.info("getAllFolders failed with " + e);
			EmailFacadeException ex = new EmailFacadeException();
			ex.setTechnicalException(e);
			throw ex;
		}
	}

	/**
	 * @param messages
	 * @return
	 * @throws MessagingException
	 * @throws EmailFacadeException
	 */
	private MailMessage[] convertMessages(Message[] messages) throws MessagingException, EmailFacadeException {
		if(messages == null) return null; 	 	
		MailMessage[] result = new MailMessage[messages.length];
		for(int h=0; h<messages.length; h++) {
			MailMessage current = new MailMessage();
			Message technical = messages[h];
			current.setTechnicalID(technical.getMessageNumber());
			current.setFolder(convertFolder(technical.getFolder()));
			Address[] technicalAddresses = technical.getAllRecipients();
			Vector tos = new Vector();
			Vector bccs = new Vector();
			Vector ccs= new Vector();
			for(int j =0; j<technicalAddresses.length;j++) {
				Address technicalAddress = technicalAddresses[j];
				if(technicalAddress.getType().equals(Message.RecipientType.BCC)) {
					bccs.add(technicalAddress.toString());
				} else if(technicalAddress.getType().equals(Message.RecipientType.CC)) {
					bccs.add(technicalAddress.toString());
				}else {
					tos.add(technicalAddress.toString());
				}
			}
			
			String[] bccs2 = new String[bccs.size()];
			for(int i=0; i<bccs.size(); i++) {
				bccs2[i] = (String)bccs.get(i);
			}
			String[] ccs2 = new String[ccs.size()];
			for(int i=0; i<ccs.size(); i++) {
				ccs2[i] = (String)ccs.get(i);
			}
			String[] tos2 = new String[tos.size()];
			for(int i=0; i<tos.size(); i++) {
				tos2[i] = (String)tos.get(i);
			}
			
			current.setBcc(bccs2);
			current.setCc(ccs2);
			current.setTo(tos2);
			current.setFrom(technical.getFrom()[0].toString());
			current.setReplyTo(technical.getReplyTo()[0].toString());
			current.setSentDate(technical.getSentDate());
			current.setSubject(technical.getSubject());
			//current.setText(technical.getContent().toString());
			current.getUniqueID().setId(IDGenerator.next());
			result[h] = current;
		}
		return result;
	}

	/**
	 * @return Returns the reader.
	 */
	public JavaMailReader getReader() {
		return reader;
	}
	/**
	 * @param reader The reader to set.
	 */
	public void setReader(JavaMailReader reader) {
		this.reader = reader;
	}
	/**
	 * @return Returns the sender.
	 */
	public MailSender getSender() {
		return sender;
	}
	/**
	 * @param sender The sender to set.
	 */
	public void setSender(MailSender sender) {
		this.sender = sender;
	}
	
	/** this will fill the message body
	 * 
	 * @param messageID
	 * @param folder
	 * @return
	 * @throws EmailFacadeException
	 */
    public void fillMessageBody(MailMessage message) throws EmailFacadeException {
    	Message technical;
		try {
			technical = reader.getMessageInFolder(message.getTechnicalID() , message.getFolder().getFullName());
			message.setText(technical.getContent().toString());
		} catch (MessagingException e) {
			logger.info("getMessage failed with " + e);
			EmailFacadeException ex = new EmailFacadeException();
			ex.setTechnicalException(e);
			throw ex;
		} catch (IOException e) {
			logger.info("getMessage failed with " + e);
			EmailFacadeException ex = new EmailFacadeException();
			ex.setTechnicalException(e);
			throw ex;
		}
    }
    public void moveMessages(MailMessage[] messages, String folderFrom, String folderTo) throws EmailFacadeException {
    	Message[] technical = copyMessagesInternal(messages, folderFrom, folderTo);
		try {
			for(int i=0; i<technical.length; i++) {
				technical[i].setFlag(Flags.Flag.DELETED, true);
			}
			getFolder(folderFrom).expunge();
		} catch (MessagingException e) {
			logger.info("moveMessages failed with " + e);
			EmailFacadeException ex = new EmailFacadeException();
			ex.setTechnicalException(e);
			throw ex;
		}
    	Folder[] folders = getAllFolders();
    	for (int i = 0; i < folders.length; i++) {
			if(folderFrom.equals(folders[i].getFullName())
				||	folderTo.equals(folders[i].getFullName())) {
				state.setState(folders[i].getUniqueID().getId(),true);
			}
		}
    }
    
    public void copyMessages(MailMessage[] messages, String folderFrom, String folderTo) throws EmailFacadeException {
    	copyMessagesInternal(messages, folderFrom, folderTo);
    	Folder[] folders = getAllFolders();
    	for (int i = 0; i < folders.length; i++) {
			if(folderFrom.equals(folders[i].getFullName())
				||	folderTo.equals(folders[i].getFullName())) {
				state.setState(folders[i].getUniqueID().getId(),true);
			}
		}
    }

	/**
	 * @param messages
	 * @param folderFrom
	 * @param folderTo
	 * @throws EmailFacadeException
	 */
	private Message[]  copyMessagesInternal(MailMessage[] messages, String folderFrom, String folderTo) throws EmailFacadeException {
    	Message[] technical = new Message[messages.length];
		try {
			for(int i=0; i<messages.length; i++) {
				technical[i] = reader.getMessageInFolder(messages[i].getTechnicalID(), folderFrom);
			}
			getFolder(folderFrom).copyMessages(technical, getFolder(folderTo));
		} catch (MessagingException e) {
			logger.info("copyMessagesInternal failed with " + e);
			EmailFacadeException ex = new EmailFacadeException();
			ex.setTechnicalException(e);
			throw ex;
		}
		return technical;
	}

	/**
	 * @param folderFrom
	 * @return
	 * @throws MessagingException
	 */
	private javax.mail.Folder getFolder(String fullName) throws MessagingException {
		return reader.getFolder(fullName);
	}
	
}
