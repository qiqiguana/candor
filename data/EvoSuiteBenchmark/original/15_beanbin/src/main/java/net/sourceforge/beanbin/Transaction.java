package net.sourceforge.beanbin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.command.ActiveListCommand;
import net.sourceforge.beanbin.data.BeanBinDAO;

public class Transaction implements Serializable {
	private static final long serialVersionUID = -1268043668769698463L;
	
	private List<ActiveListCommand> commands;
	private BeanBinDAO dao;
	private Class clazz;
	
	public Transaction(BeanBinDAO dao, Class clazz) {
		this.dao = dao;
		this.clazz = clazz;
	}
	
	public Class getTargetClass() {
		return clazz;
	}
	
	public void begin() throws BeanBinException {		
		this.setCommands(new ArrayList<ActiveListCommand>());
	}
	
	public void addCommand(ActiveListCommand command) throws BeanBinException {
		if(this.getCommands() == null) {
			begin();
		}
		this.getCommands().add(command);
	}
	
	public void commit() throws BeanBinException {
		if(this.getCommands() == null) {
			throw new BeanBinException("You must begin a transaction before you can commit.");
		}
		
		this.dao.execute(this);
		this.setCommands(null);
	}

	private void setCommands(List<ActiveListCommand> commands) {
		this.commands = commands;
	}

	public List<ActiveListCommand> getCommands() {
		return commands;
	}
	
	public boolean hasNotBegun() {
		return getCommands() == null;
	}
}
