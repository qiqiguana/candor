package net.sourceforge.beanbin.data.ejb3.dao.transaction;

import javax.persistence.EntityManager;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.command.RemoveEntity;
import net.sourceforge.beanbin.data.EntityUtils;

public class RemoveAction implements TransactionAction {
	private Object entity;
	
	public RemoveAction(RemoveEntity cmd) {
		this.entity = cmd.getEntity();
	}
	
	public void execute(EntityManager em) throws BeanBinException {
		Object saved = em.find(entity.getClass(), EntityUtils.getId(entity));
		if(saved != null) {
			em.remove(saved);	
		}
	}
}
