/*
 * Created on 08.06.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.bluepenguin.email.client.cache;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import ch.bluepenguin.email.client.Folder;
import ch.bluepenguin.email.client.MailMessage;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author Christian
 *
 *  Cache usable by EmailFacade implementations
 */
public class MailCache {
	private Logger log = Logger.getLogger(MailCache.class.getName());
	private CacheManager manager;
	private Cache elementCache;
	
	
	public MailCache(String config) {
		URL url = getClass().getResource(config);
		try {
			manager = CacheManager.create(url);
			elementCache = manager.getCache("elementCache");
		} catch (CacheException e) {
			log.error("Creation of CacheManger failed. Cache will not be used. Exception was " + e);
		}
	}
	
	
	public Cache getCache() {
		return elementCache;
	}
	public void addElement(Integer id, Object element, List childList) {
		Element myElement = new HierarchicalElement((Serializable)id, (Serializable) element, childList);
		elementCache.put(myElement);
	}
	
    public void clearCache() {
    	try {
			elementCache.removeAll();
		} catch (IllegalStateException e) {
			log.error("Error while clearing the cache:" + e);
		} catch (IOException e) {
			log.error("Error while clearing the cache:" + e);
		}
    }

    public Object getElement(Integer id) {
		try {
			HierarchicalElement element = (HierarchicalElement)elementCache.get(id);
			return element.getValue();
		} catch (IllegalStateException e) {
			log.error("Error while retrieving element with ID " + id + " from cache. Exception was " + e);
			return null;
		} catch (CacheException e) {
			log.error("Error while retrieving element with ID " + id + " from cache. Exception was " + e); 
			return null;
		}
	}

    public List getChildKeys(Integer id) {
		try {
			HierarchicalElement element = (HierarchicalElement)elementCache.get(id);
			return element.getChildKeys();
		} catch (IllegalStateException e) {
			log.error("Error while retrieving element with ID " + id + " from cache. Exception was " + e);
			return null;
		} catch (CacheException e) {
			log.error("Error while retrieving element with ID " + id + " from cache. Exception was " + e); 
			return null;
		}
	}
	
}
