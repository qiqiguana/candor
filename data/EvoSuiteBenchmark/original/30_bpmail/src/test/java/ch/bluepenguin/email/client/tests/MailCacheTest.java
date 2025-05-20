/*
 * Created on 16.09.2004
 */
package ch.bluepenguin.email.client.tests;

import ch.bluepenguin.email.client.Folder;
import ch.bluepenguin.email.client.MailMessage;
import ch.bluepenguin.email.client.cache.MailCache;
import ch.bluepenguin.email.client.service.EmailFacade;
import ch.bluepenguin.email.client.service.EmailFacadeException;

/**
 * @author Christian
 *
 */
public class MailCacheTest extends SpringTestBase {
    private MailCache cache;
    private EmailFacade facade;
    
    public void setUp() {
    	super.setUp();
    	cache = (MailCache) factory.getBean("mailCache");
    	facade = (EmailFacade)factory.getBean("emailFacade");
    }

    public void NOtestCacheWithFolders() throws EmailFacadeException {
		System.out.println("starting testCacheWithFolders");
    	
    	//this should fill the cache
    	Folder[] folders = facade.getAllFolders();
    	facade.getAllMessages();
    	//and now it should find it!
    	Folder[] folders2 = facade.getAllFolders();
    	assertTrue(cache.getCache().getHitCount()==(folders2.length+1));
    	assertTrue(folders.length==folders2.length);
		System.out.println("stopping testCacheWithFolders");
    }
    
    public void testCacheWithMessages() throws EmailFacadeException {
		System.out.println("starting testCacheWithMessages");
    	Folder[] folders = facade.getAllFolders();
    	assertTrue(facade.isDirty());
    	for (int i = 0; i < folders.length; i++) {
			MailMessage[] messages = facade.getMessagesFromFolder(folders[i]);
			System.out.println("should read from cache for folder "  + folders[i].getFullName());
			messages = facade.getMessagesFromFolder(folders[i]);
			assertTrue(facade.isDirty());
		}
    	assertFalse(facade.isDirty());
		System.out.println("stopping testCacheWithMessages");
    	
    }
}
