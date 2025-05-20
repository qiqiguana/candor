package net.sourceforge.beanbin;

import java.util.Collection;
import java.util.Iterator;

import net.sourceforge.beanbin.command.ActiveListCommand;
import net.sourceforge.beanbin.command.AddEntity;
import net.sourceforge.beanbin.command.RemoveEntity;
import net.sourceforge.beanbin.data.BeanBinDAO;
import net.sourceforge.beanbin.query.Query;

/**
 * BeanBin is a utility for persisting EJB 3.0 Entity beans. 
 * It works without the need of any xml configuration (except for database connectivity) 
 * and it generates all the nessessary JPA-QL.
 * 
 * @author Brian Gorman
 *
 * @param <E> A bean that is an {@link java.persistence.Entity}
 */
public class BeanBin<E> extends BeanBinSearcher<E> implements Iterable<E> {
	private BeanBinDAO dao;
	private Class clazz;
	private Transaction transaction;
	
	public BeanBin(Class clazz) throws BeanBinException {
		super(clazz);
		this.clazz = clazz;
		this.dao = BeanBinDAOFactory.getDAO();
		this.transaction = new Transaction(dao, clazz);
	}
	
	public Transaction getTransaction() {
		return transaction;
	}
		
	/**
	 * Returns the amount of beans stored in this BeanBin
	 * @return
	 * @throws BeanBinException 
	 */
	public int getSize() throws BeanBinException {
		return dao.getSize(clazz, new Query());
	}
		
	
	/**
	 * Search the bin
	 * @param query
	 * @return ActiveList
	 * @throws BeanBinException
	 */
	public ActiveList<E> search(Query query) throws BeanBinException {
		ActiveList<E> list = new ActiveList<E>(clazz);
		list.setQuery(query);
		return list;
	}
	
	/**
	 * Persist an {@link java.persistence.Entity}
	 * @param entity
	 * @throws BeanBinException 
	 */
	public void putIn(E obj) throws BeanBinException {
		addCommandToTransaction(new AddEntity(obj));
	}

	private void addCommandToTransaction(ActiveListCommand ... cmds) throws BeanBinException {
		if(getTransaction().hasNotBegun()) {
			Transaction tx = new Transaction(dao, clazz);
			for(ActiveListCommand cmd : cmds) {
				tx.addCommand(cmd);	
			}
			tx.commit();
		} else {
			for(ActiveListCommand cmd : cmds) {
				getTransaction().addCommand(cmd);	
			}
		}
	}
	
	/**
	 *  Remove an {@link java.persistence.Entity} from the bin
	 * @param entity
	 */
	public void takeOut(E obj) throws BeanBinException {
		addCommandToTransaction(new RemoveEntity(obj));		
	}
	
	/**
	 * Persist a collection of {@link java.persistence.Entity}'s
	 * @param collection 
	 * @throws BeanBinException 
	 */	
	public void putIn(Collection<E> c) throws BeanBinException {
		ActiveListCommand[] cmds = new AddEntity[c.size()];
		Iterator iter = c.iterator();
		for(int i = 0; iter.hasNext(); ++i) {
			cmds[i] = new AddEntity(iter.next());
		}
		addCommandToTransaction(cmds);
	}
	
	/**
	 *  Remove a collection of {@link java.persistence.Entity}'s from the bin
	 * @param collection
	 * @throws BeanBinException 
	 */
	public void takeOut(Collection<E> c) throws BeanBinException {
		ActiveListCommand[] cmds = new AddEntity[c.size()];
		Iterator iter = c.iterator();
		for(int i = 0; iter.hasNext(); ++i) {
			cmds[i] = new RemoveEntity(iter.next());
		}
		addCommandToTransaction(cmds);		
	}

	public Iterator<E> iterator() {
		try {
			return fetchSize(100).iterator();
		} catch (BeanBinException e) {
			throw new RuntimeException("BeanBin iterator failed: " + e.getMessage(), e);
		}
	}
}