/*
 * Created on 16.09.2004
 */
package ch.bluepenguin.email.client.tests;

import ch.bluepenguin.email.client.Folder;
import ch.bluepenguin.email.client.MailMessage;
import ch.bluepenguin.email.client.service.EmailFacade;
import ch.bluepenguin.email.client.service.EmailFacadeException;

/**
 * @author Christian
 *
 */
public class EmailFacadeTest extends SpringTestBase {
    private EmailFacade facade;
    
    public void setUp() {
    	super.setUp();
        facade = (EmailFacade) factory.getBean("emailFacade");
    }
    public void testSendEmail() {
        facade.sendMessage("one@localhost", "two@localhost", "subject", "message");
    }
    public void testAllFolders() throws EmailFacadeException {
        Folder[] folders = facade.getAllFolders();
        assertNotNull(folders);
        for(int i = 0; i<folders.length; i++) {
        	System.out.println("folder: " + folders[i].getFullName()); 
        }
    }

    /**
     * carefull: this test will copy all messages from the first folder to the second.
     * @throws EmailFacadeException
     */
    public void testCopyMessage() throws EmailFacadeException {
        Folder[] folders = facade.getAllFolders();
        assertTrue(folders.length >=2);
        Folder folderFrom = folders[0];
        Folder folderTo= folders[1];
    	int folderToStartSize = facade.getMessagesFromFolder(folderTo).length;
    	int folderFromStartSize = facade.getMessagesFromFolder(folderFrom).length;
    	MailMessage[] messages = facade.getMessagesFromFolder(folderFrom);
    	assertTrue(messages.length >=1);
    	facade.copyMessages(messages, folderFrom.getFullName(), folderTo.getFullName());
    	assertTrue(facade.getMessagesFromFolder(folderTo).length == folderFromStartSize + folderToStartSize);
    }
    
    /**
     * carefull: this test will move all messages from the second folder to the first.
     * @throws EmailFacadeException
     */
    public void testMoveMessage() throws EmailFacadeException {
        Folder[] folders = facade.getAllFolders();
        assertTrue(folders.length >=2);
        Folder folderFrom = folders[1];
        Folder folderTo= folders[0];
    	int folderToStartSize = facade.getMessagesFromFolder(folderTo).length;
    	int folderFromStartSize = facade.getMessagesFromFolder(folderFrom).length;
    	MailMessage[] messages = facade.getMessagesFromFolder(folderFrom);
    	assertTrue(messages.length >=1);
    	facade.moveMessages(messages, folderFrom.getFullName(), folderTo.getFullName());
    	assertTrue(facade.getMessagesFromFolder(folderTo).length == folderFromStartSize + folderToStartSize);
    	assertTrue(facade.getMessagesFromFolder(folderFrom).length == 0);
    }

    public void testAllMessages() throws EmailFacadeException {
        Folder[] folders = facade.getAllFolders();
        assertNotNull(folders);
        for(int i = 0; i<folders.length; i++) {
        	System.out.println("folder: " + folders[i].getFullName());
        	MailMessage[] messages = facade.getMessagesFromFolder(folders[i]);
            for(int j = 0; j<messages.length; j++) {
            	System.out.println("  message: From " + messages[j].getFrom() + "\n" +
            			           "           To   " + messages[j].getTo() + "\n" +
            			           "           Subj " + messages[j].getSubject() + "\n" +
            			           "           Msg  " + messages[j].getText()
            	);
            	
            }
        }
    }

}
