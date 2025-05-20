package net.sourceforge.beanbin.search.index;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.BeanBinDAOFactory;
import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.IndexDAO;
import net.sourceforge.beanbin.data.ejb3.LazyActiveList;

public class LazyIndexList<E> extends LazyActiveList<E> {
	private static final long serialVersionUID = 738356497837562888L;
	private IndexDAO dao;
	
	public LazyIndexList() {
		super();
	}
	
	public LazyIndexList(Class clazz, Object key, String property) throws BeanBinException {
		super(clazz, key, property);
		this.dao = BeanBinDAOFactory.getIndexDAORemote();
	}

	@Override
	protected List<Object> sendQuery() throws BeanBinException {
		List<Object> list = new ArrayList<Object>();
		List<String> values = dao.getValues(getClazz(), getProperty(), getKey());
		list.addAll(values);
		return list;
	}
}
