package net.sourceforge.beanbin.search.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sourceforge.beanbin.BeanBinDAOFactory;
import net.sourceforge.beanbin.BeanBinTest;
import net.sourceforge.beanbin.data.IndexDAO;
import net.sourceforge.beanbin.test.IndexedEntity;

public class IndexSaverTest extends BeanBinTest {
	private IndexDAO dao;

	public IndexSaverTest() throws Exception {
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
		saver.save(makeEntity(1000));
		saver.save(makeEntity(1001));
		Thread.sleep(2000L); // to give time to save..
		Set<Object> results = dao.search(IndexedEntity.class, "keywords", "man");
		assertEquals(2, results.size());
		assertTrue(results.contains(1000L));
		assertTrue(results.contains(1001L));
	}
	
	private IndexedEntity makeEntity(long id) {
		IndexedEntity entity = new IndexedEntity();
		entity.setId(id);
		entity.setString("a string");
		entity.setStringIndex("string index");
		List<String> list = new ArrayList<String>();
		list.add("super man");
		list.add("bat man");
		list.add("green goblin");
		entity.setKeywords(list);
		return entity;
	}
}
