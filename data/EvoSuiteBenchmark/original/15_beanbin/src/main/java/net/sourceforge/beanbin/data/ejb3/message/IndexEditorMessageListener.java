package net.sourceforge.beanbin.data.ejb3.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.search.index.Index;
import net.sourceforge.beanbin.search.index.IndexEntry;
import net.sourceforge.beanbin.search.index.IndexManager;
import net.sourceforge.beanbin.search.index.RemoveAllFromIndex;
import net.sourceforge.beanbin.search.index.RemoveFromIndex;

@MessageDriven(
	    mappedName = "jms/IndexEditor",
	    activationConfig = {
	        @ActivationConfigProperty(
	            propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
	        @ActivationConfigProperty(
	            propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
	        @ActivationConfigProperty(
	            propertyName = "destination", propertyValue = "queue/indexsaver")
	    }
)
public class IndexEditorMessageListener implements MessageListener {	
	private IndexManager manager;
	
	public IndexEditorMessageListener() throws BeanBinException {
		this.manager = Index.getManager();
	}
	
	public void onMessage(Message msg) {
		try {
			if(msg instanceof ObjectMessage) {
				ObjectMessage message = (ObjectMessage)msg;
				Serializable obj = message.getObject();
				List<IndexEntry> entries = null;
				if(obj instanceof List) {
					entries = (List<IndexEntry>)obj;
					manager.save(entries);
				} else if(obj instanceof IndexEntry) {
					entries = new ArrayList<IndexEntry>();
					entries.add((IndexEntry)obj);
					manager.save(entries);
				} else if(obj instanceof RemoveFromIndex) {
					RemoveFromIndex remove = (RemoveFromIndex)obj;
					manager.remove(remove.getTargetClass(), remove.getKey());
				} else if(obj instanceof RemoveAllFromIndex) {
					RemoveAllFromIndex remove = (RemoveAllFromIndex)obj;
					manager.removeAll(remove.getTargetClass());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
