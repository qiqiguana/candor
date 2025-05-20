package net.sourceforge.beanbin.data.ejb3;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.Transaction;
import net.sourceforge.beanbin.command.ActiveListCommand;
import net.sourceforge.beanbin.command.AddEntity;
import net.sourceforge.beanbin.command.RemoveAll;
import net.sourceforge.beanbin.command.RemoveEntity;
import net.sourceforge.beanbin.data.BeanBinDAO;
import net.sourceforge.beanbin.data.EntityUtils;
import net.sourceforge.beanbin.data.ejb3.dao.EJB3Searcher;
import net.sourceforge.beanbin.data.ejb3.dao.IndexEJB3Searcher;
import net.sourceforge.beanbin.data.ejb3.dao.transaction.AddAction;
import net.sourceforge.beanbin.data.ejb3.dao.transaction.RemoveAction;
import net.sourceforge.beanbin.data.ejb3.dao.transaction.RemoveAllAction;
import net.sourceforge.beanbin.data.ejb3.dao.transaction.TransactionAction;
import net.sourceforge.beanbin.query.Query;
import net.sourceforge.beanbin.search.index.IndexUtils;
	
@Stateless
@TransactionManagement(value=TransactionManagementType.BEAN)
@Local(BeanBinDAO.class)
@Remote(BeanBinDAO.class)
public class EJB3BeanBinDAO implements BeanBinDAO {
	private static final long serialVersionUID = 8260245986951942429L;
	
	@PersistenceUnit(name="beanbin")
	EntityManagerFactory emff;
	
	public List<Object> initializeLazy(Class<?> clazz, Object key, String property) throws BeanBinException {
		List<Object> list = new ArrayList<Object>();
		EntityManager em = getEntityManager(clazz);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Object entity = em.find(clazz, key);
		Method getter = EntityUtils.getMethod(clazz, property);
		try {
			Collection c = (Collection) getter.invoke(entity, new Object[0]);
			c.size();
			list.addAll(c);
		} catch (Exception e) {
			throw new BeanBinException("Initialize Lazy Exception: " + e.getMessage(), e);
		}
		tx.commit();
		
		// inject lazy entities after the commit becuase if you
		// set anything inside of the transaction it'll think
		// it should update when commits...
		
		Lazifier lazy = new Lazifier();
		for(Object obj : list) {
			lazy.execute(obj);
		}
		return list;
	}
	
	public void execute(Transaction transaction) throws BeanBinException {
		EntityManager em = getEntityManager(transaction.getTargetClass());
		EntityTransaction realTx = em.getTransaction();
		realTx.begin();
		for(ActiveListCommand cmd : transaction.getCommands()) {
			TransactionAction action = getTransactionAction(cmd);
			action.execute(em);
		}
		realTx.commit();
	}
	
	private TransactionAction getTransactionAction(ActiveListCommand cmd) throws BeanBinException {
		if(cmd instanceof AddEntity) {
			return new AddAction((AddEntity)cmd);
		} else if(cmd instanceof RemoveEntity) {
			return new RemoveAction((RemoveEntity)cmd);
		} else if(cmd instanceof RemoveAll) {
			return new RemoveAllAction((RemoveAll)cmd);
		} else {
			throw new BeanBinException("Unsupported ActiveListCommand sent to getTransactionAction(" + cmd.getClass().getName() + ")");
		}
	}
	
	public List<Object> search(Class clazz, Query query) throws BeanBinException {
		EJB3Searcher searcher = null;
		if(IndexUtils.hasSettableIndexes(clazz)) {
			searcher = new IndexEJB3Searcher(clazz, query, getEntityManager(clazz));
		} else {
			searcher = new EJB3Searcher(clazz, query, getEntityManager(clazz));	
		}
		return searcher.getResults();
	}
	
	private EntityManager getEntityManager(Class clazz) throws BeanBinException {
		EntityManagerFactory factory = EntityManagerFactoryShelf.getInstance().get(clazz);

		return factory.createEntityManager();
	}

	public int getSize(Class clazz, Query query) throws BeanBinException {
		return new EJB3Searcher(clazz, query, getEntityManager(clazz)).getSize();
	}
}
