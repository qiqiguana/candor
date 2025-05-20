package net.sourceforge.beanbin.search.index;

import java.util.List;
import java.util.Set;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.search.index.cache.Results;

public interface IndexManager {
	public Set<Object> search(Class clazz, String property, String term) throws BeanBinException;
	public void save(List<IndexEntry> entries) throws BeanBinException;
	public void remove(Class clazz, Object key) throws BeanBinException;
	public void removeAll(Class clazz) throws BeanBinException;
	public Results getResults(Class clazz, List<Object> keys) throws BeanBinException;
	public List<String> getValues(Class clazz, String property, Object key) throws BeanBinException;
}
