package net.sourceforge.beanbin.data.ejb3.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.persistence.EntityManager;

import net.sourceforge.beanbin.BeanBinDAOFactory;
import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.query.Query;
import net.sourceforge.beanbin.search.index.cache.AddToCache;

public class IndexEJB3Searcher extends EJB3Searcher {

	private QueueConnectionFactory fact;
	private Queue q;
	
	public IndexEJB3Searcher(Class clazz, Query query, EntityManager em) throws BeanBinException {
		super(clazz, query, em);		
		try {
			this.fact = (QueueConnectionFactory)BeanBinDAOFactory.getInitialContext().lookup("QueueConnectionFactory");				
			this.q = (Queue)BeanBinDAOFactory.getInitialContext().lookup("queue/indexcache");
		} catch(Exception e) {
			throw new BeanBinException("IndexEJB3Searcher() : " + e.getMessage(), e);
		}
	}

	@Override
	protected String getPrefix() {
		return "select id, " + ALIAS + "";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Object> getResults(javax.persistence.Query query) throws BeanBinException {
		List<Object> list = new ArrayList<Object>();
		ArrayList<Object> keys = new ArrayList<Object>();
		
		List<Object> results = query.getResultList();
		for(Object obj : results) {
			Object[] array = (Object[])obj;
			keys.add(array[0]);
			list.add(array[1]);
		}
		
		try {
			addToIndexCache(keys);	
		} catch(Exception e) {
			throw new BeanBinException("IndexEJB3Searcher.getResults : " + e.getMessage(), e);
		}
		
		return list;
	}
	
	private void addToIndexCache(ArrayList<Object> keys) throws Exception {
		QueueConnection conn = fact.createQueueConnection();
		QueueSession session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer producer = session.createProducer(q);
		ObjectMessage message = session.createObjectMessage();
				
		message.setObject(new AddToCache(this.clazz, keys));
		producer.send(message);				
		
		producer.close();
		session.close();
		conn.close();				
	}
}
