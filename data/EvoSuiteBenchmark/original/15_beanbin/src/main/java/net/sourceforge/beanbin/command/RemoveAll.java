package net.sourceforge.beanbin.command;


public class RemoveAll implements ActiveListCommand {
	private static final long serialVersionUID = -5494826276869395552L;
	private Class clazz;
	
	public RemoveAll(Class clazz) {
		this.clazz = clazz;
	}
	
	public Class getClazz() {
		return clazz;
	}
}