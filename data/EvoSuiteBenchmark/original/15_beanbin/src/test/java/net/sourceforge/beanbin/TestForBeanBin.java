package net.sourceforge.beanbin;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.command.RemoveAll;
import net.sourceforge.beanbin.test.AnotherEntity;
import net.sourceforge.beanbin.test.Base;
import net.sourceforge.beanbin.test.IndexedEntity;
import net.sourceforge.beanbin.test.SubTestEntity;
import net.sourceforge.beanbin.test.TestEntity;

public class TestForBeanBin extends BeanBinTest {
	public TestForBeanBin() throws Exception {
		super("localhost", 1099);
	}
	
	@Override
	protected void tearDown() throws Exception {
		removeAll(AnotherEntity.class, SubTestEntity.class, TestEntity.class, Base.class);
		removeAll(IndexedEntity.class);
	}

	public void testBasic() throws Exception {
		BeanBin<TestEntity> bin = new BeanBin<TestEntity>(TestEntity.class); 
		String string1 = "billy bob likes twinkies";
		String string2 = "that is why he is so fat";
		String string3 = "is that why he has two names?";
		String string4 = "yeah, the left side is billy and the right is bob";

		Transaction tx = bin.getTransaction();
		
		tx.begin();
		bin.putIn(makeSampleEntity(10, string1));
		bin.putIn(makeSampleEntity(5, string2));
		bin.putIn(makeSampleEntity(7, string3));
		bin.putIn(makeSampleEntity(9, string4));
		tx.commit();
		
		List<TestEntity> list = bin.contains("string", "billy");
		
		assertEquals(2, list.size());
		for(TestEntity saved : list) {
			assertTrue(saved.getString().equals(string1) || saved.getString().equals(string4));
		}
		
		list = bin.greaterThan("anInt", 5).and().lessThan("anInt", 10);
		assertEquals(2, list.size());
		for(TestEntity saved : list) {
			assertTrue(saved.getAnInt() == 7 || saved.getAnInt() == 9);
		}
		
		List<String> strings = new ArrayList<String>();
		strings.add(string1);
		strings.add(string2);
		strings.add(string3);
		strings.add(string4);
		for(TestEntity entity : bin) {
			strings.remove(entity.getString());
		}
		
		assertTrue(strings.isEmpty());
		
		list = bin.matches("string", string1);
		assertEquals(1, list.size());
		TestEntity saved = list.get(0);
		assertEquals(string1, saved.getString());
		bin.takeOut(saved);
		
		list = bin.matches("string", string1);
		assertEquals(0, list.size());
	}
	
	public void testIndexSearch() throws Exception {
		IndexedEntity entity = new IndexedEntity();
		entity.setString("saved string");
		entity.setStringIndex("string index");
		entity.addKeyword("keyword 1");
		entity.addKeyword("keyword 2");
		entity.addKeyword("keyword 3");
		
		BeanBin<IndexedEntity> bin = new BeanBin<IndexedEntity>(IndexedEntity.class);
		bin.putIn(entity);
		Thread.sleep(1000L);
		List<IndexedEntity> results = bin.matches("keywords", "keyword");
		assertEquals(1, results.size());
		IndexedEntity saved = results.get(0);
		assertTrue(saved.getKeywords().contains("keyword 2"));
	}
	
	private TestEntity makeSampleEntity(int i, String string) {		
		TestEntity entity = new TestEntity();
		entity.setAnInt(i);
		entity.setString(string);
		return entity;
	}
	
	private void removeAll(Class ...classes) throws Exception {
		Transaction tx = new Transaction(BeanBinDAOFactory.getDAO(), classes[0]);
		for(Class c : classes) {
			tx.addCommand(new RemoveAll(c));	
		}
		tx.commit();		
	}
}
