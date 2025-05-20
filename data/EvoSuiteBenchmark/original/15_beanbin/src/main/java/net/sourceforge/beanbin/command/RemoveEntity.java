package net.sourceforge.beanbin.command;


public class RemoveEntity implements ActiveListCommand {
	private static final long serialVersionUID = -129012304920527486L;
	
	private Object entity;
	
	public RemoveEntity(Object entity) {
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
}