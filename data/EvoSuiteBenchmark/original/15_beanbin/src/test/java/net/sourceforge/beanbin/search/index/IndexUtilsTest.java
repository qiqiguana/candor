package net.sourceforge.beanbin.search.index;

import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.beanbin.test.IndexedEntity;
import net.sourceforge.beanbin.test.TestEntity;

public class IndexUtilsTest extends TestCase {
	public void testGetIndexProperties() throws Exception {
		List<String> props = IndexUtils.getIndexProperties(IndexedEntity.class);
		assertEquals(3, props.size());
		assertTrue(props.contains("keywords"));
		assertTrue(props.contains("generatedKeywords"));
		assertTrue(props.contains("stringIndex"));
	}
	
	public void testGetSettables() throws Exception {
		assertTrue(IndexUtils.hasSettableIndexes(IndexedEntity.class));
		assertFalse(IndexUtils.hasSettableIndexes(TestEntity.class));
		List<String> props = IndexUtils.getSettableIndexProperties(IndexedEntity.class);
		assertEquals(1, props.size());
		assertTrue(props.contains("keywords"));		
	}
}
