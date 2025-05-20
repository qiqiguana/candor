package net.sourceforge.beanbin.data.ejb3;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.EntityUtils;
import net.sourceforge.beanbin.search.index.IndexUtils;
import net.sourceforge.beanbin.search.index.LazyIndexList;

public class Lazifier {
	public void execute(Object entity) throws BeanBinException {
		Object id = EntityUtils.getId(entity);
		injectSubs(id, entity);
		injectIndex(id, entity);
	}

	private void injectIndex(Object id, Object entity) throws BeanBinException {
		try {
			if(IndexUtils.hasSettableIndexes(entity.getClass())) {
				for(Method setter : IndexUtils.getSettableIndexSetters(entity.getClass())) {
					String property = EntityUtils.getProperty(setter);
					Object[] args = {new LazyIndexList<String>(entity.getClass(), id, property)};
					setter.invoke(entity, args);
				}
			}	
		} catch(Exception e) {
			throw new BeanBinException("Lazifer.injectIndex: " + e.getMessage(), e);
		}
	}

	private void injectSubs(Object id, Object entity) throws BeanBinException {
		List<Method> getters = EntityUtils.getSubEntityMethods(entity.getClass());
		for(Method getter : getters) {
			if(EntityUtils.isCollection(getter.getReturnType())) {
				for(Annotation anno : getter.getAnnotations()) {
					Class type = anno.annotationType();
					if(type == OneToMany.class) {
						OneToMany lazyanno = (OneToMany)anno;
						if(lazyanno.fetch() == FetchType.LAZY) {
							inject(getter, id, entity);
						}
					} else if (type == ManyToMany.class) {
						ManyToMany lazyanno = (ManyToMany)anno;
						if(lazyanno.fetch() == FetchType.LAZY) {
							inject(getter, id, entity);
						}
					}
				}
			}
		}
	}
	
	private void inject(Method getter, Object id, Object entity) throws BeanBinException {
		try {
			Method setter = EntityUtils.getSetter(getter);
			Object[] args = new Object[1];
			args[0] = new LazyActiveList(entity.getClass(), id, EntityUtils.getProperty(getter));
			setter.invoke(entity, args);
		} catch (Exception e) {
			if(e instanceof BeanBinException) {
				throw (BeanBinException)e;
			} else {
				throw new BeanBinException("Lazy Injection: " + e.getMessage(), e);	
			}
		}
	}
}
