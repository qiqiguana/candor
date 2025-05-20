package net.sourceforge.beanbin.data.ejb3;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.IndexDAO;
import net.sourceforge.beanbin.search.index.Index;
import net.sourceforge.beanbin.search.index.IndexManager;
import net.sourceforge.beanbin.search.index.IndexSaver;
import net.sourceforge.beanbin.search.index.RemoveAllFromIndex;
import net.sourceforge.beanbin.search.index.RemoveFromIndex;
import net.sourceforge.beanbin.search.index.cache.IndexCache;
import net.sourceforge.beanbin.search.index.cache.IndexCacheSingleton;
import net.sourceforge.beanbin.search.index.cache.Results;

@Stateful
@Local(IndexDAO.class)
@Remote(IndexDAO.class)
public class EJB3IndexDAO implements IndexDAO {
	
	private IndexManager manager;
	private IndexSaver saver;
	private IndexCache cache;
	
	public EJB3IndexDAO() throws BeanBinException {
		this.manager = Index.getManager();
		this.saver = new IndexSaver();
		this.cache = IndexCacheSingleton.getInstance();
	}

	public Set<Object> search(Class clazz, String property, String term) throws BeanBinException {
		return manager.search(clazz, property, term);
	}
	
	public void remove(Class clazz, Object key) throws BeanBinException {
		try {
			saver.sendMessage(new RemoveFromIndex(clazz, key));
		} catch (Exception e) {
			throw new BeanBinException("EJB3IndexDAO remove: " + e.getMessage(), e);
		}
	}
	
	public void removeAll(Class clazz) throws BeanBinException {
		try {
			saver.sendMessage(new RemoveAllFromIndex(clazz));
		} catch (Exception e) {
			throw new BeanBinException("EJB3IndexDAO remove: " + e.getMessage(), e);
		}		
	}
	
	public List<String> getValues(Class clazz, String property, Object key) throws BeanBinException {
		List<String> values = cache.getValues(clazz, key, property);
		if(values == null) {
			values = manager.getValues(clazz, property, key);
		}
		return values;
	}
	
	public void addToCache(Class clazz, Results toadd) throws BeanBinException {
		Results results = this.cache.get(clazz);
		if(results == null) {
			cache.add(clazz, toadd);
		} else {
			results.add(toadd);
		}
	}
}
