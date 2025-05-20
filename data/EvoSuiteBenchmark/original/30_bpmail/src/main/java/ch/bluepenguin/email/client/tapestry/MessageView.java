/*
 * Created on 08.01.2005
 *
 */
package ch.bluepenguin.email.client.tapestry;

import java.util.logging.Logger;

import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IActionListener;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.event.PageRenderListener;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.listener.ListenerMap;

import ch.bluepenguin.email.client.MailMessage;
import ch.bluepenguin.email.client.service.EmailFacade;
import ch.bluepenguin.email.client.service.EmailFacadeException;
import ch.bluepenguin.email.client.tapestry.helpers.TreeModelHelper;
import ch.bluepenguin.tapestry.components.menu.model.IMenuModel;

/**
 * @author Christian
 *  
 */
public abstract class MessageView extends BasePage implements PageRenderListener, IActionListener {
	protected static Logger logger = Logger.getLogger(MessageView.class.getName());

	public abstract void setEmailFacade(EmailFacade emailFacade);
	public abstract EmailFacade getEmailFacade();

	public abstract void setCurrentFolder(ch.bluepenguin.email.client.Folder folderName);
	public abstract ch.bluepenguin.email.client.Folder getCurrentFolder();

	public abstract IMenuModel getFolderMenuModel();
	public abstract void setFolderMenuModel(IMenuModel model);

	public abstract IMenuModel getActionMenuModel();
	public abstract void setActionMenuModel(IMenuModel model);

	public abstract void setCurrentMessage(MailMessage message);
	public abstract MailMessage getCurrentMessage();

	public abstract void setCreateMode(boolean mode);
	public abstract boolean getCreateMode();

	public abstract void setMoveMode(boolean mode);
	public abstract boolean getMoveMode();

	public abstract void setComposeMessageText(String text);
	public abstract String getComposeMessageText();

	public abstract void setComposeMessageTo(String text);
	public abstract String getComposeMessageTo();

	public abstract void setComposeMessageCC(String text);
	public abstract String getComposeMessageCC();

	public abstract void setComposeMessageBCC(String text);
	public abstract String getComposeMessageBCC();

	public abstract void setComposeMessageSubject(String text);
	public abstract String getComposeMessageSubject();


	public void pageBeginRender(PageEvent evt) {
		if(getFolderMenuModel()==null) {
			TreeModelHelper helper = new TreeModelHelper();
			IMenuModel folderModel =null;
			try {
				folderModel = helper.buildFolderModel
				(getEmailFacade().getAllFolders(),this.getComponent("folderDirectLink"),evt.getRequestCycle());
			} catch (EmailFacadeException e) {
				logger.info("pageBeginRender: Problem with email facade");
			}
			setFolderMenuModel(folderModel);
		}
	}

	/**
	 * displays the list of messages from a selected folder
	 */
	public void actionDisplayFolder(IRequestCycle cycle) {
		ch.bluepenguin.email.client.Folder currentFolder = ((ch.bluepenguin.email.client.Folder)cycle.getServiceParameters()[0]);
		if(getMoveMode()) {
			((MessageList)getComponent("messageList")).moveSelectedMessages(currentFolder);
		} else {
			((MessageList)getComponent("messageList")).setCurrentFolder(currentFolder);
			setCurrentFolder(currentFolder);
		}
		setMoveMode(false);
	}
	/**
	 * displays the chosen message from a selected folder
	 */
	public void actionDisplayMessage(IRequestCycle cycle) {
		int id =  ((Integer)cycle.getServiceParameters()[0]).intValue();
		MailMessage currentMessage=null;
		try {
			MailMessage[] currentMessages = getEmailFacade().getMessagesFromFolder(getCurrentFolder());
			for(int i=0; i<currentMessages.length;i++) {
				if(id==currentMessages[i].getUniqueID().getId().intValue()) {
					currentMessage = currentMessages[i];
					break;
				}
			}
			getEmailFacade().fillMessageBody(currentMessage);
		} catch (EmailFacadeException e) {
			logger.severe("actionDisplayMessage: Problem with email facade");
		}
		setCurrentMessage(currentMessage);
	}
	
	public void actionCreateMessage(IRequestCycle cycle) {
		logger.info("actionCreateMessage called");
		setCreateMode(true);
	}

	public void actionCreateMessageCancel(IRequestCycle cycle) {
		logger.info("actionCreateMessageCancel called");
		setCreateMode(false);
	}

	public void actionMoveMessageCancel(IRequestCycle cycle) {
		logger.info("actionMoveMessageCancel called");
		setMoveMode(false);
	}

	public void actionCreateMessageSaveDraft(IRequestCycle cycle) {
		logger.info("actionCreateMessageSaveDraft called");
		setCreateMode(false);
	}

	public void actionReplyMessage(IRequestCycle cycle) {
		logger.info("actionReplyMessage called");
	}

	public void actionDeleteMessage(IRequestCycle cycle) {
		logger.info("actionDeleteMessage called");
	}
	
	public void actionMoveMessage(IRequestCycle cycle) {
		logger.info("actionMoveMessage called");
		setMoveMode(true);
	}

	public void actionSendMessage(IRequestCycle cycle) {
		/*
		 * (String from, String to, 
            String subject, String message);
		 */
		logger.info("actionSendMessage called");
		//TODO: FROM out of account
		//TODO: arrays for FROM/TO/CC/BCC
		//TODO: BCC
		//TODO: attachment, MimeMessages
		getEmailFacade().sendMessage("test@localhost.ch", getComposeMessageTo(),
				getComposeMessageSubject(),
				getComposeMessageText());
	}

}