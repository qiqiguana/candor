package net.sourceforge.beanbin.data.ejb3;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import net.sourceforge.beanbin.BeanBinDAOFactory;
import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.Transaction;
import net.sourceforge.beanbin.command.AddEntity;
import net.sourceforge.beanbin.command.RemoveAll;
import net.sourceforge.beanbin.data.BeanBinDAO;
import net.sourceforge.beanbin.query.Criteria;
import net.sourceforge.beanbin.query.IndexCriteria;
import net.sourceforge.beanbin.query.Query;
import net.sourceforge.beanbin.query.SearchType;
import net.sourceforge.beanbin.query.SortBy;
import net.sourceforge.beanbin.test.AnotherEntity;
import net.sourceforge.beanbin.test.Base;
import net.sourceforge.beanbin.test.BlobEntity;
import net.sourceforge.beanbin.test.BlobOne;
import net.sourceforge.beanbin.test.BlobTwo;
import net.sourceforge.beanbin.test.ImplOne;
import net.sourceforge.beanbin.test.ImplTwo;
import net.sourceforge.beanbin.test.IndexedEntity;
import net.sourceforge.beanbin.test.SubTestEntity;
import net.sourceforge.beanbin.test.TestEntity;

public class EJB3BeanBinDAOTest extends RemoteTest {
	private BeanBinDAO dao;
	
	public EJB3BeanBinDAOTest() throws Exception {
		super("localhost", 1099);
		BeanBinDAOFactory.setInitialContext(getInitialContext());
	}
	
	@Override
	protected void tearDown() throws Exception {
		removeAll(AnotherEntity.class, SubTestEntity.class, TestEntity.class, Base.class);
		removeAll(IndexedEntity.class);
		removeAll(BlobEntity.class);
		BeanBinDAOFactory.getIndexDAORemote().removeAll(IndexedEntity.class);
	}

	public void testBasic() throws Exception {
		BeanBinDAO dao = getDAO();
		
		TestEntity entity = new TestEntity();
		entity.setAnInt(5);
		entity.setString("a sample string");
		
		Transaction tx = new Transaction(dao, TestEntity.class);
		tx.addCommand(new AddEntity(entity));
		tx.commit();
		
		Query q = new Query(new Criteria("string", "a sample string", SearchType.EQUALS));
		List<Object> results = dao.search(TestEntity.class, q);
		
		assertEquals(1, results.size());
		TestEntity saved = (TestEntity) results.get(0);
		assertEquals("a sample string", saved.getString());
	}
	
	public void testSearchingNumbers() throws Exception {
		BeanBinDAO dao = getDAO();		
		addEntities(10);
		Query query = new Query(new Criteria("anInt", 5, SearchType.GREATERTHAN));
		query.and(new Criteria("anInt", 10, SearchType.LESSTHAN));
		
		List<Object> results = dao.search(TestEntity.class, query);
		assertEquals(4, results.size());
	}
	
	public void testWildcardSearch() throws Exception {
		BeanBinDAO dao = getDAO();
		
		Transaction tx = new Transaction(dao, TestEntity.class);		
		TestEntity entity = new TestEntity();
		entity.setString("beanbin rocks!");
		tx.addCommand(new AddEntity(entity));
		
		entity = new TestEntity();
		entity.setString("beanbin needs help!");
		tx.addCommand(new AddEntity(entity));
		
		entity = new TestEntity();
		entity.setString("beans that go in a bin!");
		tx.addCommand(new AddEntity(entity));
		
		tx.commit();
		
		Query query = new Query(new Criteria("string", "beanbin*", SearchType.EQUALS));
		List<Object> results = dao.search(TestEntity.class, query);
		assertEquals(2, results.size());		
	}
	
	public void testSave() throws Exception {
		BeanBinDAO dao = getDAO();
		
		TestEntity entity = makeSampleEntity();
		
		saveEntity(entity);
		
		Query query = new Query(new Criteria("string", "a string", SearchType.EQUALS));
		TestEntity saved = (TestEntity)dao.search(TestEntity.class, query).get(0);
		assertEquals(10, saved.getAnInt());
		
		saved.setString("something different");
		saveEntity(saved);
		
		List<Object> results = dao.search(TestEntity.class, query);
		assertTrue(results.isEmpty());
	}
	
	public void testPagination() throws Exception {
		addEntities(100);
		
		Query query = new Query();
		query.setFetchSize(10);
		
		List<Object> results = dao.search(TestEntity.class, query);
		assertEquals(10, results.size());
		assertEquals("test10", ((TestEntity)results.get(9)).getString());
		query.setPosition(10);
		results = dao.search(TestEntity.class, query);
		assertEquals(10, results.size());
		assertEquals("test20", ((TestEntity)results.get(9)).getString());
	}
	
	public void testSize() throws Exception {
		addEntities(5);
		assertEquals(5, getDAO().getSize(TestEntity.class, new Query()));
	}
	
	public void testLazies() throws Exception {
		saveEntity(makeSampleEntity());
		Query query = new Query(new Criteria("string", "a string", SearchType.EQUALS));
		TestEntity saved = (TestEntity)dao.search(TestEntity.class, query).get(0);
		assertTrue(saved.getSubs() instanceof LazyActiveList);
		assertEquals(1, saved.getSubs().size());
		assertEquals("some other string 2", saved.getSubs().get(0).getSomeString());		
	}
	
	public void testSort() throws Exception {
		addSomeEntitiesWithMixedUpInts();		
		Query query = new Query(new Criteria("anInt", 3, SearchType.GREATERTHAN));
		query.and(new Criteria("anInt", 10, SearchType.LESSTHAN));
		query.setSortBy("anInt", SortBy.ASCENDING);
		List results = getDAO().search(TestEntity.class, query);
		TestEntity previous = null;
		for(Object obj : results) {
			TestEntity entity = (TestEntity)obj;
			if(previous != null) {
				assertTrue(entity.getAnInt() >= previous.getAnInt());
			}
			previous = entity;
		}
		
		query.setSortBy("anInt", SortBy.DESENDING);
		results = getDAO().search(TestEntity.class, query);
		previous = null;
		for(Object obj : results) {
			TestEntity entity = (TestEntity)obj;
			if(previous != null) {
				assertTrue(entity.getAnInt() <= previous.getAnInt());
			}
			previous = entity;
		}
	}
	
	public void testIndexedEntity() throws Exception {
		Transaction tx = new Transaction(getDAO(), IndexedEntity.class);
		IndexedEntity entity = new IndexedEntity();
		entity.setString("saved string");
		entity.setStringIndex("string index");
		entity.addKeyword("keyword 1");
		entity.addKeyword("keyword 2");
		entity.addKeyword("keyword 3");
		tx.addCommand(new AddEntity(entity));
		tx.commit();
		Thread.sleep(1000L);
		Query query = new Query(new IndexCriteria("keywords", "keyword"));
		
		List<Object> results = getDAO().search(IndexedEntity.class, query);
		assertEquals(1, results.size());
		IndexedEntity saved = (IndexedEntity)results.get(0);
		assertTrue(saved.getKeywords().contains("keyword 1"));
	}
	
	public void testBlobEntity() throws Exception {
		BlobEntity entity = new BlobEntity();
		entity.setString("a string");
		BlobOne one = new BlobOne();
		one.setValue("value");
		BlobTwo two = new BlobTwo();
		two.setValue("another value");
		entity.setBlobOne(one);
		entity.setBlobTwo(two);
		Transaction tx = new Transaction(getDAO(), BlobEntity.class);
		tx.addCommand(new AddEntity(entity));		
		tx.commit();
		Query query = new Query(new Criteria("string", "a string", SearchType.EQUALS));
		List<Object> results = getDAO().search(BlobEntity.class, query);
		assertEquals(1, results.size());
		BlobEntity saved = (BlobEntity)results.get(0);
		assertEquals("value", saved.getBlobOne().getValue());
		assertEquals("another value", saved.getBlobTwo().getValue());
	}

	private void addSomeEntitiesWithMixedUpInts() throws NamingException, Exception, BeanBinException {
		Transaction tx = new Transaction(getDAO(), TestEntity.class);
		TestEntity entity = new TestEntity();
		entity.setAnInt(5);
		tx.addCommand(new AddEntity(entity));
		entity = new TestEntity();
		entity.setAnInt(2);
		tx.addCommand(new AddEntity(entity));
		entity = new TestEntity();
		entity.setAnInt(7);
		tx.addCommand(new AddEntity(entity));
		entity = new TestEntity();
		entity.setAnInt(11);
		tx.addCommand(new AddEntity(entity));
		entity = new TestEntity();
		entity.setAnInt(4);
		tx.addCommand(new AddEntity(entity));
		entity = new TestEntity();
		entity.setAnInt(15);
		tx.addCommand(new AddEntity(entity));
		entity = new TestEntity();
		entity.setAnInt(5);
		tx.addCommand(new AddEntity(entity));
		entity = new TestEntity();
		entity.setAnInt(1);
		tx.addCommand(new AddEntity(entity));
		tx.commit();
	}

	private void saveEntity(TestEntity entity) throws NamingException, Exception, BeanBinException {
		Transaction tx = new Transaction(getDAO(), TestEntity.class);
		tx.addCommand(new AddEntity(entity));
		tx.commit();
	}

	private TestEntity makeSampleEntity() {
		ImplOne one = new ImplOne();
		ImplTwo two = new ImplTwo();
		
		TestEntity entity = new TestEntity();
		entity.setAnInt(10);
		entity.setString("a string");

		entity.setIone(one);
		entity.setItwo(two);
		
		List<SubTestEntity> list = new ArrayList<SubTestEntity>();
		SubTestEntity sub = new SubTestEntity();
		sub.setEntity(entity);
		sub.setSomeString("some other string");

		List<SubTestEntity> subs = new ArrayList<SubTestEntity>();
		
		SubTestEntity subsub = new SubTestEntity();
		subsub.setEntity(entity);
		subsub.setSomeString("some other string 2");
		subs.add(subsub);
		list.add(subsub);
		
		List<AnotherEntity> others = new ArrayList<AnotherEntity>();
		AnotherEntity other = new AnotherEntity();
		other.setSubs(subs);
		others.add(other);
		sub.setAnothers(others);
		list.add(sub);
		
		entity.setSubs(list);
		return entity;
	}
	
	private void addEntities(int howmany) throws Exception {
		Transaction tx = new Transaction(getDAO(), TestEntity.class);
		for(int i = 1; i <= howmany; ++i) {
			TestEntity entity = new TestEntity();
			entity.setAnInt(i);
			entity.setString("test" + i);
			tx.addCommand(new AddEntity(entity));
		}
		tx.commit();
	}
	
	private void removeAll(Class ...classes) throws Exception {
		Transaction tx = new Transaction(getDAO(), classes[0]);
		for(Class c : classes) {
			tx.addCommand(new RemoveAll(c));	
		}
		tx.commit();		
	}
	
	private BeanBinDAO getDAO() throws NamingException, Exception {
		if(dao == null) {
			dao = (BeanBinDAO) getInitialContext().lookup("EJB3BeanBinDAO/remote"); 
		}
		return dao;
	}
}
