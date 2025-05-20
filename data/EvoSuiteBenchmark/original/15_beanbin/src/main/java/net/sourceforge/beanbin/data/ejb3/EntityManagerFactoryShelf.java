package net.sourceforge.beanbin.data.ejb3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.configuration.PropertyManager;
import net.sourceforge.beanbin.data.EntityUtils;
import net.sourceforge.beanbin.data.ejb3.config.SchemaFinder;

public class EntityManagerFactoryShelf {
	private Map<Class, EntityManagerFactory> factories;
	private EntityManagerFactoryBuilder builder;
	
	private static EntityManagerFactoryShelf shelf;
	
	private EntityManagerFactoryShelf() throws Exception {
		this.factories =  new HashMap<Class, EntityManagerFactory>();
		this.builder = getBuilder();		
	}
	
	private EntityManagerFactoryBuilder getBuilder() throws Exception {
		PropertyManager manager = new PropertyManager();
		String className = manager.getProperty("entitymanagerfactorybuilder").getValue();
		if(className == null) {
			throw new BeanBinException("Set beanbin.entitymanagerfactorybuilder property in beanbin.properties.");			
		} else {
			Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
			return (EntityManagerFactoryBuilder)clazz.newInstance();	
		}
	}
	
	public boolean contains(Class clazz) {
		return factories.containsKey(clazz);
	}
	
	public EntityManagerFactory get(Class clazz) throws BeanBinException {
		EntityManagerFactory fact = factories.get(clazz);
		if(fact == null) {
			fact = buildFactory(clazz);
		}
		return fact;
	}
	
	private EntityManagerFactory buildFactory(Class clazz) throws BeanBinException {
		SchemaFinder finder = new SchemaFinder(clazz);
		List<Class> schema = finder.getSchema();
		EntityManagerFactory fact = builder.getFactory(schema, false);
		
		EntityManager em = fact.createEntityManager();
		Query query = em.createQuery("select " + EntityUtils.getIdProperty(clazz) + " from " + clazz.getName());
		query.setMaxResults(1);
		try {
			query.getResultList();	
		} catch (Exception e) {
			// TODO: change this exception to check and make sure that it is a need 
			// to create exception
			fact = builder.getFactory(schema, true);
		}
		for(Class c : schema) {
			factories.put(c, fact);	
		}
		return fact;
	}
	
	public static EntityManagerFactoryShelf getInstance() throws BeanBinException {
		if(shelf == null) {
			try {
				shelf = new EntityManagerFactoryShelf();
			} catch (Exception e) {
				throw new BeanBinException("Please provide a correct EntitymanagerFactoryBuilder in beanbin.properties. cause: " + e.getMessage(), e);
			}
		}
		
		return shelf;
	}
}
