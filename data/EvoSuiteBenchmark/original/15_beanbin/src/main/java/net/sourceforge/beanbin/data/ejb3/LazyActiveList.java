package net.sourceforge.beanbin.data.ejb3;

import java.util.List;
import java.util.Set;

import net.sourceforge.beanbin.ActiveList;
import net.sourceforge.beanbin.BeanBinException;

@SuppressWarnings("unchecked")
public class LazyActiveList<E> extends ActiveList<E> implements Set<E> {
	private static final long serialVersionUID = -3338772142481683544L;
	
	private String property;
	private Object key;
	private boolean initialized;

	public LazyActiveList() {
		super();
	}
	
	public LazyActiveList(Class clazz, Object key, String property) throws BeanBinException {
		super(clazz);
		this.key = key;
		this.property = property;
		this.initialized = false;
	}

	protected List<Object> sendQuery() throws BeanBinException {		
		List<Object> list = getDAO().initializeLazy(getClazz(), key, property);
		setInitialized(true);
		return list;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
}