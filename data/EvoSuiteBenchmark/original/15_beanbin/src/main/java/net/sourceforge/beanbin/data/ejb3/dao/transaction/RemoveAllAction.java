package net.sourceforge.beanbin.data.ejb3.dao.transaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.JoinTable;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.command.RemoveAll;
import net.sourceforge.beanbin.reflect.ReflectionSearch;

public class RemoveAllAction implements TransactionAction {
	private Class clazz;
	public RemoveAllAction(RemoveAll removeAll) {
		this.clazz = removeAll.getClazz();
	}
	public void execute(EntityManager em) throws BeanBinException {
		List<Method> manytomanies = new ReflectionSearch(clazz).methodsThatHave("@ManyToMany").getMethods();
		for(Method getter : manytomanies) {
			for(Annotation anno : getter.getAnnotations()) {
				if(anno.annotationType() == JoinTable.class) {
					JoinTable table = (JoinTable)anno;
					em.createNativeQuery("delete from " + table.name()).executeUpdate();
				}
			}
		}
		em.createQuery("delete from " + clazz.getName()).executeUpdate();
	}
}
