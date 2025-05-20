package net.sourceforge.beanbin.data.ejb3;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import net.sourceforge.beanbin.BeanBinException;

public interface EntityManagerFactoryBuilder {	 
	public EntityManagerFactory getFactory(List<Class> schema, boolean createSchema) throws BeanBinException;
}
