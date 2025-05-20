package net.sourceforge.beanbin.data;

import java.io.Serializable;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.Transaction;
import net.sourceforge.beanbin.query.Query;

public interface BeanBinDAO extends Serializable {
	public void execute(Transaction transaction) throws BeanBinException;
	public List<Object> search(Class clazz, Query query) throws BeanBinException;
	public int getSize(Class clazz, Query query) throws BeanBinException;
	public List<Object> initializeLazy(Class<?> clazz, Object key, String property) throws BeanBinException;
}