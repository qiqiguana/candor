package net.sourceforge.beanbin.command;


public class AddEntity implements ActiveListCommand {
	private static final long serialVersionUID = -2741797671136318289L;
	
	private Object entity;
	
	public AddEntity(Object entity) {
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
}