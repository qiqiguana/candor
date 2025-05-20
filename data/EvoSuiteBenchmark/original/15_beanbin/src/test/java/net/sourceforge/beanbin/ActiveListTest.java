package net.sourceforge.beanbin;

import net.sourceforge.beanbin.command.RemoveAll;
import net.sourceforge.beanbin.data.ejb3.RemoteTest;
import net.sourceforge.beanbin.test.AnotherEntity;
import net.sourceforge.beanbin.test.Base;
import net.sourceforge.beanbin.test.SubTestEntity;
import net.sourceforge.beanbin.test.TestEntity;

public class ActiveListTest extends RemoteTest {	
	public ActiveListTest() throws Exception {
		super("localhost", 1099);
		BeanBinDAOFactory.setInitialContext(getInitialContext());
	}
	
	@Override
	protected void tearDown() throws Exception {
		removeAll(AnotherEntity.class, SubTestEntity.class, TestEntity.class, Base.class);
	}

	public void testPagination() throws Exception {
		addEntities(50);
		BeanBin<TestEntity> bin = new BeanBin<TestEntity>(TestEntity.class);
		ActiveList<TestEntity> list = bin.greaterThan("anInt", 10).lessThan("anInt", 40).fetchSize(10);
		for(int i = 0; i < 10; ++i) {
			list.get(i);
			assertEquals(0, list.getQuery().getPosition());
		}
		assertEquals("test20", list.get(9).getString());
		for(int i = 10; i < 20; ++i) {
			list.get(i);
			assertEquals(10, list.getQuery().getPosition());			
		}
		assertEquals("test30", list.get(19).getString());		
		for(int i = 20; i < 29; ++i) {
			list.get(i);
			assertEquals(20, list.getQuery().getPosition());			
		}
		assertEquals("test39", list.get(28).getString());
	}
	
	private void addEntities(int howmany) throws Exception {
		BeanBin<TestEntity> bin = new BeanBin<TestEntity>(TestEntity.class);
		
		Transaction tx = bin.getTransaction();		
		tx.begin();
		for(int i = 1; i <= howmany; ++i) {
			TestEntity entity = new TestEntity();
			entity.setAnInt(i);
			entity.setString("test" + i);
			bin.putIn(entity);
		}
		tx.commit();
	}
	
	private void removeAll(Class ...classes) throws Exception {
		Transaction tx = new BeanBin(TestEntity.class).getTransaction();
		for(Class c : classes) {
			tx.addCommand(new RemoveAll(c));	
		}
		tx.commit();		
	}
}
