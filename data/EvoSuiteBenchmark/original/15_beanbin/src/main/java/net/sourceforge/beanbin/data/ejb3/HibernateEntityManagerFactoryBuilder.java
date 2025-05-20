package net.sourceforge.beanbin.data.ejb3;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.configuration.PropertyManager;

import org.hibernate.ejb.Ejb3Configuration;

public class HibernateEntityManagerFactoryBuilder implements EntityManagerFactoryBuilder {
	public EntityManagerFactory getFactory(List<Class> schema, boolean createSchema) throws BeanBinException {
		Ejb3Configuration config = new Ejb3Configuration();
		config.configure("beanbin", new HashMap());
		Properties props = new Properties();
		
		if(createSchema) {
			props.put("hibernate.hbm2ddl.auto", "create");	
		} else {
			props.put("hibernate.hbm2ddl.auto", "update");			
		}
		PropertyManager manager = new PropertyManager();
		for(String name : manager.getAllPropertyNames()) {
			if(!name.startsWith("beanbin")) {
				props.put(name, manager.getProperty(name).getValue());				
			}
		}
		
		config.addProperties(props);
		
		for(Class c : schema) {
			config.addAnnotatedClass(c);
		}
		return config.buildEntityManagerFactory();
	}
}