/*
 * Created on 27.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.bluepenguin.email.client.tapestry;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageEvent;

import ch.bluepenguin.email.client.Folder;
import ch.bluepenguin.email.client.MailMessage;
import ch.bluepenguin.email.client.service.EmailFacade;
import ch.bluepenguin.email.client.service.EmailFacadeException;
import ch.bluepenguin.email.client.tapestry.helpers.TreeModelHelper;
import ch.bluepenguin.tapestry.components.menu.model.IMenuModel;

/**
 * @author Christian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class MessageList extends BaseComponent {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MessageList.class.getName());
	private Folder currentFolder;

	public abstract void setEmailFacade(EmailFacade emailFacade);
	public abstract EmailFacade getEmailFacade();
	public abstract Map getSelectedMessages();
	public abstract void setSelectedMessages(Map selectedMessages);
	
	public void setCurrentFolder(ch.bluepenguin.email.client.Folder folderName) {
		this.currentFolder = folderName;
		mailMessages = null;
	}
	public ch.bluepenguin.email.client.Folder getCurrentFolder() {
		return currentFolder;
	}
//	public abstract void setCurrentMessage(MailMessage message);
	public abstract MailMessage getCurrentMessage();

	private MailMessage[] mailMessages;
		
	public void setCheckboxSelectedMessage(boolean selected) {
		MailMessage myMessage =getCurrentMessage();
		if(myMessage!=null) {
			Map mySelected = getSelectedMessages(); 
			if(selected &! mySelected.containsKey(myMessage.getUniqueID().getId())) {
				logger.info("Setting the message as selected " + myMessage.getUniqueID().getId());
				mySelected.put(myMessage.getUniqueID().getId(), myMessage);
			} else if(!selected && mySelected.containsKey(myMessage.getUniqueID().getId())){
				mySelected.remove(myMessage);
				logger.info("Setting the message as *NOT* selected " + myMessage.getUniqueID().getId());
			}
			setSelectedMessages(mySelected);
		}
	}

	public boolean getCheckboxSelectedMessage() {
		if(getSelectedMessages()==null) {
			setSelectedMessages(new java.util.HashMap());
			return false;
		}
		boolean result = getSelectedMessages().containsKey(getCurrentMessage().getUniqueID().getId());
		logger.info("Checking for selected " + getCurrentMessage().getUniqueID().getId()+ " was " + result);
		return result;
	}
	
	public MailMessage[] getMessagesFromCurrentFolder() {
		if(getCurrentFolder()==null) return null;
		try {
			if(mailMessages==null) {
				mailMessages = getEmailFacade().getMessagesFromFolder(getCurrentFolder());
			}
			return mailMessages;

		} catch (EmailFacadeException e) {
			logger.info("getMessagesFromCurrentFolder: Problem with email facade: " + e);
		}
		return null;
	}

	public void moveSelectedMessages(Folder newFolder) {
		logger.info("actionMoveMessage in table called");
		MailMessage[] selected =  new MailMessage[getSelectedMessages().size()];
		Iterator iter = getSelectedMessages().keySet().iterator();
		int i=0;
		while(iter.hasNext()) {
			Integer key = (Integer) iter.next();
			selected[i++] = (MailMessage)getSelectedMessages().get(key);
		}
												  
		try {
			getEmailFacade().moveMessages(selected, getCurrentFolder().getFullName(), newFolder.getFullName());
		} catch (EmailFacadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mailMessages = null;
		getSelectedMessages().clear();
	}

}
