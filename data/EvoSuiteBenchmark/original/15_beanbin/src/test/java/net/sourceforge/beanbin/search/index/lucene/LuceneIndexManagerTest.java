package net.sourceforge.beanbin.search.index.lucene;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import net.sourceforge.beanbin.search.index.IndexEntry;
import net.sourceforge.beanbin.search.index.IndexField;
import net.sourceforge.beanbin.search.index.cache.Results;
import net.sourceforge.beanbin.test.IndexedEntity;

public class LuceneIndexManagerTest extends TestCase {
	public void tearDown() throws Exception {
		LuceneIndexManager manager = new LuceneIndexManager();
		manager.removeAll(IndexedEntity.class);
	}
	
	public void testSave() throws Exception {
		LuceneIndexManager manager = new LuceneIndexManager();		
		manager.save(getEntries());
		Set<Object> results = manager.search(IndexedEntity.class, "keywords", "some*");
		assertEquals(2, results.size());
		assertTrue(results.contains(1000L));
		assertTrue(results.contains(1001L));
	}
	
	public void testGetResults() throws Exception {
		LuceneIndexManager manager = new LuceneIndexManager();		
		manager.save(getEntries());
		
		List<Object> keys = new ArrayList<Object>();
		keys.add(1000);
		keys.add(1001);
		
		Results results = manager.getResults(IndexedEntity.class, keys);
		List<String> values = results.get(1000L).getValues("keywords");
		assertEquals(2, values.size());
		assertTrue(values.contains("some value"));
		assertTrue(values.contains("another value"));
	}
	
	public void testGetValues() throws Exception {
		LuceneIndexManager manager = new LuceneIndexManager();		
		manager.save(getEntries());
		
		List<String> values = manager.getValues(IndexedEntity.class, "stringIndex", 1001L);
		assertEquals(1, values.size());
		assertTrue(values.contains("see rock city"));
	}

	private List<IndexEntry> getEntries() {
		List<IndexEntry> entries = new ArrayList<IndexEntry>();
		IndexEntry entry = new IndexEntry(IndexedEntity.class, 1000);
		IndexField field = new IndexField("keywords");
		field.addValue("some value");
		field.addValue("another value");
		entry.addField(field);
		field = new IndexField("stringIndex");
		field.addValue("lucene rocks!");
		entry.addField(field);
		entries.add(entry);
		entry = new IndexEntry(IndexedEntity.class, 1001);
		field = new IndexField("keywords");
		field.addValue("something else");
		field.addValue("and another");
		entry.addField(field);
		field = new IndexField("stringIndex");
		field.addValue("see rock city");
		entry.addField(field);
		entries.add(entry);
		return entries;
	}
}
