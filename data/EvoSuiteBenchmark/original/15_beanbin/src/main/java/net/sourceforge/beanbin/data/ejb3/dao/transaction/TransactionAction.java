package net.sourceforge.beanbin.data.ejb3.dao.transaction;

import javax.persistence.EntityManager;

import net.sourceforge.beanbin.BeanBinException;

public interface TransactionAction {
	public void execute(EntityManager em) throws BeanBinException;
}
