package net.sourceforge.beanbin.search.index;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;

import net.sourceforge.beanbin.BeanBinDAOFactory;
import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.EntityUtils;

public class IndexSaver {
	
	private QueueConnectionFactory fact;
	private Queue q;

	public IndexSaver() throws BeanBinException {
		try {
			this.fact = (QueueConnectionFactory)BeanBinDAOFactory.getInitialContext().lookup("QueueConnectionFactory");				
			this.q = (Queue)BeanBinDAOFactory.getInitialContext().lookup("queue/indexsaver");	
		} catch(Exception e) {
			throw new BeanBinException("IndexSaver() : " + e.getMessage(), e);
		}
	}
	
	public void save(Object entity) throws BeanBinException {
		Class clazz = entity.getClass();
		try {
			IndexEntry entry = new IndexEntry(clazz, EntityUtils.getId(entity));
			for(Method getter : IndexUtils.getIndexGetters(clazz)) {
				IndexField field = new IndexField(EntityUtils.getProperty(getter));
				Object toindex = getter.invoke(entity, new Object[0]);
				if(toindex != null) {
					if(toindex instanceof List) {
						List list = (List)toindex;
						for(Object sub : list) {
							field.addValue(sub.toString());
						}
					} else {
						field.addValue(toindex.toString());
					}
				}
				entry.addField(field);
			}
			sendMessage(entry);
		} catch(Exception e) {
			if(e instanceof BeanBinException) {
				throw (BeanBinException)e;
			} else {
				throw new BeanBinException("IndexSaver.save: " + e.getMessage(), e);
			}
		}
	}
	
	public void sendMessage(Serializable obj) throws Exception {
		QueueConnection conn = fact.createQueueConnection();
		QueueSession session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer producer = session.createProducer(q);
		ObjectMessage message = session.createObjectMessage();
		
		
		message.setObject(obj);
		producer.send(message);				
		
		producer.close();
		session.close();
		conn.close();				
	}
}
