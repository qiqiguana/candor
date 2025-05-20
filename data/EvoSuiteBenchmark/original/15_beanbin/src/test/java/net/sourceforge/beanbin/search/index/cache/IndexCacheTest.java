package net.sourceforge.beanbin.search.index.cache;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.test.IndexedEntity;
import net.sourceforge.beanbin.test.TestEntity;

import junit.framework.TestCase;

public class IndexCacheTest extends TestCase {
	public void testBasic() throws Exception {
		IndexCache cache = new IndexCache();
		Results results = new Results();
		Properties props = new Properties();
		
		List<String> values = new ArrayList<String>();
		values.add("value 1");
		values.add("value 2");
		values.add("value 3");
		values.add("value 4");
		
		List<String> values2 = new ArrayList<String>();
		values2.add("value 5");
		values2.add("value 6");
		values2.add("value 7");
		values2.add("value 8");
		
		props.add("prop1", values);
		props.add("prop2", values2);
		
		results.add(1234, props);
		cache.add(IndexedEntity.class, results);
		
		List<String> cached = cache.getValues(IndexedEntity.class, 1234, "prop1");
		assertEquals(4, cached.size());
		assertTrue(cached.contains("value 3"));
		cached = cache.getValues(IndexedEntity.class, 1234, "prop1");
		assertNull(cached);
		
		IndexCache another = new IndexCache();
		results = new Results();
		props = new Properties();
		values = new ArrayList<String>();
		values.add("value 1");
		values.add("value 2");
		values.add("value 3");
		values.add("value 4");
		
		values2 = new ArrayList<String>();
		values2.add("value 5");
		values2.add("value 6");
		values2.add("value 7");
		values2.add("value 8");
		
		props.add("prop1", values);
		props.add("prop2", values2);
		
		results.add(1234, props);
		
		another.add(TestEntity.class, results);
		another.add(IndexedEntity.class, getAnotherResults());
		
		cache.addCache(another);
		
		cached = cache.getValues(TestEntity.class, 1234, "prop1");
		assertEquals(4, cached.size());
		cached = cache.getValues(IndexedEntity.class, 1234, "prop1");
		assertEquals(5, cached.size());
		assertTrue(cached.contains("value 0"));		
	}
	
	
	private Results getAnotherResults() {
		Results results = new Results();
		Properties props = new Properties();
		
		List<String> values = new ArrayList<String>();
		values.add("value 0");
		values.add("value 1");
		values.add("value 2");
		values.add("value 3");
		values.add("value 4");
		
		List<String> values2 = new ArrayList<String>();
		values2.add("value 5");
		values2.add("value 6");
		
		props.add("prop1", values);
		props.add("prop2", values2);
		
		results.add(1234, props);
		return results;
	}
}
