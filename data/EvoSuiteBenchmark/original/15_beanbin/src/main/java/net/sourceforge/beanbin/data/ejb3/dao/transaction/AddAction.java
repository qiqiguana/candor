package net.sourceforge.beanbin.data.ejb3.dao.transaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.command.AddEntity;
import net.sourceforge.beanbin.data.EntityUtils;
import net.sourceforge.beanbin.data.ejb3.LazyActiveList;
import net.sourceforge.beanbin.search.index.IndexSaver;
import net.sourceforge.beanbin.search.index.IndexUtils;

public class AddAction implements TransactionAction {
	private Object entity;
	
	public AddAction(AddEntity cmd) {
		this.entity = cmd.getEntity();
	}
	
	public void execute(EntityManager em) throws BeanBinException {
		try {
			save(em, entity, new ArrayList<Object>());
		} catch(Exception e) {
			throw new BeanBinException("AddAction Exception: " + e.getMessage(), e);
		}
	}
	
	private void save(EntityManager em, Object obj, List<Object> saving) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, BeanBinException {
		saveSubs(em, obj, saving);
		
		Object id = EntityUtils.getId(obj);
		if(id == null) {
			em.persist(obj);
		} else if(id.getClass().isPrimitive()) {
			if(id instanceof Number) {
				Number num = (Number)id;
				if(num.doubleValue() == 0) {
					em.persist(obj);
				}
			}
		} else {
			Object saved = em.find(obj.getClass(), id);
			if(saved == null) {
				em.persist(obj);
			} else {
				EntityUtils.mergeEntities(saved, obj);
			}
		}
		
		if(IndexUtils.hasAnIndexSearch(obj.getClass())) {
			IndexSaver saver = new IndexSaver();
			saver.save(obj);
		}
	}

	private void saveSubs(EntityManager em, Object obj, List<Object> saving) throws BeanBinException, IllegalAccessException, InvocationTargetException {
		List<Method> entityGetters = EntityUtils.getSubEntityMethods(obj.getClass());
		for(Method getter : entityGetters) {
			Object sub = getter.invoke(obj, new Object[0]);
			if(sub != null) {
				if(EntityUtils.isCollection(sub.getClass())) {
					// TODO: this is temporary until lazies..
					if(sub instanceof LazyActiveList) {
						LazyActiveList bag = (LazyActiveList)sub;
						if(!bag.isInitialized()) {
							continue; // skip uninitialized
						}
					}
					for(Object o : (Collection)sub) {						
						if(!saving.contains(o)) {
							saving.add(o);
							save(em, o, saving);	
						}
					}
				} else {
					if(!saving.contains(sub)) {
						saving.add(sub);
						save(em, sub, saving);
					}
				}
			}
		}
	}
}
