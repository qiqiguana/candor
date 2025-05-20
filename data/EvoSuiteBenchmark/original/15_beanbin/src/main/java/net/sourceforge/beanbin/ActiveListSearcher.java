package net.sourceforge.beanbin;

import java.io.Serializable;

public class ActiveListSearcher<E> extends AbstractSearcher<E> implements Serializable {
	private static final long serialVersionUID = -4423726360966145537L;
	private Class clazz;
	private ActiveList<E> list;
	
	public ActiveListSearcher() {
		super();
	}
	
	public ActiveListSearcher(Class clazz) throws BeanBinException {
		super(clazz);
		this.clazz = clazz;		
	}
	protected ActiveList<E> getList() throws BeanBinException {
		if(list == null) {
			list = new ActiveList<E>(clazz);
		}
		return list;
	}
	
	protected void setList(ActiveList<E> list) {
		this.list = list;
	}
	
	public Class getClazz() {
		return clazz;
	}
	
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
}
