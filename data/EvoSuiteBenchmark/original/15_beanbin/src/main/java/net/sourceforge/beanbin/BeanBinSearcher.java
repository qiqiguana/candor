package net.sourceforge.beanbin;

public class BeanBinSearcher<E> extends AbstractSearcher<E> {
	private Class clazz;
	
	public BeanBinSearcher() {
		super();
	}
	
	public BeanBinSearcher(Class clazz) throws BeanBinException {
		super(clazz);
		this.clazz = clazz;
	}
	protected ActiveList<E> getList() throws BeanBinException {
		return new ActiveList<E>(clazz);
	}
	
}
