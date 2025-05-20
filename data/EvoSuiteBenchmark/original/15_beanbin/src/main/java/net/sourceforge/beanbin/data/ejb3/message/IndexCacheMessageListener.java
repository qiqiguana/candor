package net.sourceforge.beanbin.data.ejb3.message;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.sourceforge.beanbin.BeanBinDAOFactory;
import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.IndexDAO;
import net.sourceforge.beanbin.search.index.Index;
import net.sourceforge.beanbin.search.index.IndexManager;
import net.sourceforge.beanbin.search.index.cache.AddToCache;
import net.sourceforge.beanbin.search.index.cache.Results;

@MessageDriven(
	    mappedName = "jms/IndexCache",
	    activationConfig = {
	        @ActivationConfigProperty(
	            propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
	        @ActivationConfigProperty(
	            propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
	        @ActivationConfigProperty(
	            propertyName = "destination", propertyValue = "queue/indexcache")
	    }
)
public class IndexCacheMessageListener implements MessageListener {	
	private IndexManager manager;
	private IndexDAO dao;
	
	public IndexCacheMessageListener() throws BeanBinException {
		this.manager = Index.getManager();
		this.dao = BeanBinDAOFactory.getIndexDAOLocal();
	}
	
	public void onMessage(Message msg) {
		try {
			if(msg instanceof ObjectMessage) {
				ObjectMessage message = (ObjectMessage)msg;
				if(message.getObject() instanceof AddToCache) {
					AddToCache toadd = (AddToCache)message.getObject();
					Results results = manager.getResults(toadd.getTargetClass(), toadd.getKeys());
					dao.addToCache(toadd.getTargetClass(), results);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
