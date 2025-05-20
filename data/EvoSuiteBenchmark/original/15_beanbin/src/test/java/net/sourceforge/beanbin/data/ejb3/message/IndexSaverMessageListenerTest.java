package net.sourceforge.beanbin.data.ejb3.message;

import java.util.ArrayList;
import java.util.Set;

import net.sourceforge.beanbin.BeanBinDAOFactory;
import net.sourceforge.beanbin.BeanBinTest;
import net.sourceforge.beanbin.data.IndexDAO;
import net.sourceforge.beanbin.search.index.IndexEntry;
import net.sourceforge.beanbin.search.index.IndexField;
import net.sourceforge.beanbin.search.index.IndexSaver;
import net.sourceforge.beanbin.test.IndexedEntity;

public class IndexSaverMessageListenerTest extends BeanBinTest {
	private IndexDAO dao;

	public IndexSaverMessageListenerTest() throws Exception {
		super("localhost", 1099);
		this.dao = BeanBinDAOFactory.getIndexDAORemote();
	}
	

	protected void tearDown() throws Exception {
		dao.remove(IndexedEntity.class, 1000);
		dao.remove(IndexedEntity.class, 1001);
		Thread.sleep(2000L); // to give time to remove..
	}


	public void testBasic() throws Exception {
		IndexSaver saver = new IndexSaver();
		saver.sendMessage(getEntries());
		Thread.sleep(2000L); // to give time to save..
		Set<Object> results = dao.search(IndexedEntity.class, "keywords", "some*");
		assertEquals(2, results.size());
		assertTrue(results.contains(1000L));
		assertTrue(results.contains(1001L));
	}
	
	private ArrayList<IndexEntry> getEntries() {
		ArrayList<IndexEntry> entries = new ArrayList<IndexEntry>();
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
