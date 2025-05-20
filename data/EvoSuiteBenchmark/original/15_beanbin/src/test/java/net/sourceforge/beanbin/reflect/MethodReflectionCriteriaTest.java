package net.sourceforge.beanbin.reflect;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.query.SearchType;
import net.sourceforge.beanbin.test.TestEntity;

public class MethodReflectionCriteriaTest extends TestCase {
	public void testBasic() throws Exception {
		MethodReflectionCriteria criteria = new MethodReflectionCriteria(TestEntity.class, "getString", SearchType.EQUALS);
		Iterator iter = criteria.getResults().iterator();
		Method method = (Method)iter.next();
		assertFalse(iter.hasNext());
		assertEquals("getString", method.getName());
		
		criteria = new MethodReflectionCriteria(TestEntity.class, "get", SearchType.CONTAINS);
		List results = criteria.getResults();
		assertEquals(8, results.size()); // dont forget about getClass
		for(Object obj : results) {
			Method getter = (Method)obj;
			if(getter.getName().indexOf("get") == -1) {
				fail(getter.getName() + " did not have get in it!");
			}
		}
		
		criteria = new MethodReflectionCriteria(TestEntity.class, "getString", SearchType.DOESNOTEQUAL);
		results = criteria.getResults();
		assertEquals(21, results.size()); // dont forget about all of Object's methods..
		for(Object obj : results) {
			Method getter = (Method)obj;
			if(getter.getName().equals("getString")) {
				fail("getString was found in results!");
			}
		}
		
		criteria = new MethodReflectionCriteria(TestEntity.class, "getString", SearchType.GREATERTHAN);
		try {
			results = criteria.getResults();
			fail("We should have thrown due to invalid SearchType");
		} catch(BeanBinException e) {
			// do nothing we should have thrown!
		}
	}
	
	public void testWildcards() throws Exception {
		MethodReflectionCriteria criteria = new MethodReflectionCriteria(TestEntity.class, "get*", SearchType.EQUALS);
		List results = criteria.getResults();
		assertEquals(8, results.size()); // dont forget about getClass
		for(Object obj : results) {
			Method getter = (Method)obj;
			if(getter.getName().indexOf("get") != 0) {
				fail(getter.getName() + " is not a getter!");
			}
		}
	}
	
	public void testAnnotations() throws Exception {
		MethodReflectionCriteria criteria = new MethodReflectionCriteria(TestEntity.class, "@Transient", SearchType.EQUALS);
		List results = criteria.getResults();
		assertEquals(1, results.size());
		assertEquals("getStaticData", ((Method)results.get(0)).getName());
		
		criteria = new MethodReflectionCriteria(TestEntity.class, "@Column", SearchType.EQUALS);
		results = criteria.getResults();
		assertEquals(3, results.size());
		
		criteria = new MethodReflectionCriteria(TestEntity.class, "@*Col*", SearchType.EQUALS);
		results = criteria.getResults();
		assertEquals(5, results.size());
		assertTrue(((Method)results.get(0)).getName().equals("getAnInt") 
						|| ((Method)results.get(0)).getName().equals("getString") 
						|| ((Method)results.get(0)).getName().equals("getId")
						|| ((Method)results.get(0)).getName().equals("getIone") 
						|| ((Method)results.get(0)).getName().equals("getItwo")						
				   );
		assertTrue(((Method)results.get(1)).getName().equals("getAnInt") 
						|| ((Method)results.get(1)).getName().equals("getString") 
						|| ((Method)results.get(1)).getName().equals("getId")
						|| ((Method)results.get(1)).getName().equals("getIone") 
						|| ((Method)results.get(1)).getName().equals("getItwo")						
				   );
		assertTrue(((Method)results.get(2)).getName().equals("getAnInt") 
						|| ((Method)results.get(2)).getName().equals("getString") 
						|| ((Method)results.get(2)).getName().equals("getId")
						|| ((Method)results.get(2)).getName().equals("getIone") 
						|| ((Method)results.get(2)).getName().equals("getItwo")						
				   );
		assertTrue(((Method)results.get(3)).getName().equals("getAnInt") 
						|| ((Method)results.get(3)).getName().equals("getString") 
						|| ((Method)results.get(3)).getName().equals("getId")
						|| ((Method)results.get(3)).getName().equals("getIone") 
						|| ((Method)results.get(3)).getName().equals("getItwo")						
		   		   );
		assertTrue(((Method)results.get(4)).getName().equals("getAnInt") 
						|| ((Method)results.get(4)).getName().equals("getString") 
						|| ((Method)results.get(4)).getName().equals("getId")
						|| ((Method)results.get(4)).getName().equals("getIone") 
						|| ((Method)results.get(4)).getName().equals("getItwo")						
		   		   );		
//		TODO: put in searching for specific parameter values within Annotations
/*		criteria = new MethodReflectionCriteria(TestEntity.class, "@Column(name=\"thestring\")", SearchType.EQUALS);
		results = criteria.getResults();
		assertEquals(1, results.size());
		assertEquals("getString", ((Method)results.get(0)).getName()); */		
	}
	
	public void testDoesNotEqual() throws Exception {
		MethodReflectionCriteria criteria = new MethodReflectionCriteria(TestEntity.class, "get*", SearchType.DOESNOTEQUAL);
		List results = criteria.getResults();
		assertEquals(14, results.size());
		
		criteria = new MethodReflectionCriteria(TestEntity.class, "@Transient", SearchType.DOESNOTEQUAL);
		results = criteria.getResults();
		assertEquals(21, results.size());
		
		criteria = new MethodReflectionCriteria(TestEntity.class, "@Id", SearchType.DOESNOTEQUAL);
		results = criteria.getResults();
		assertEquals(21, results.size());
	}
	
	public void testContains() throws Exception {
		MethodReflectionCriteria criteria = new MethodReflectionCriteria(TestEntity.class, "et*t", SearchType.CONTAINS);
		List results = criteria.getResults();
		assertEquals(7, results.size());
		
		criteria = new MethodReflectionCriteria(TestEntity.class, "@Trans", SearchType.CONTAINS);
		results = criteria.getResults();
		assertEquals(1, results.size());		
	}
}
