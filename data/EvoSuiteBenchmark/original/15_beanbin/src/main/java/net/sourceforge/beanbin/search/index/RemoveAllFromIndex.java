package net.sourceforge.beanbin.search.index;

import java.io.Serializable;

public class RemoveAllFromIndex implements Serializable {
	private static final long serialVersionUID = 1647273352061378511L;
	
	private Class clazz;
	
	public RemoveAllFromIndex(Class clazz) {
		this.clazz = clazz;
	}
	
	public Class getTargetClass() {
		return this.clazz;
	}

}
