package net.sourceforge.beanbin.data;

import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.beanbin.test.BlobEntity;
import net.sourceforge.beanbin.test.IndexedEntity;
import net.sourceforge.beanbin.test.TestEntity;

public class EntityUtilsTest extends TestCase {
	public void testGetId() throws Exception {
		assertEquals("id", EntityUtils.getIdProperty(TestEntity.class));
		
		TestEntity entity = new TestEntity();
		entity.setId(12345L);
		
		assertEquals(12345L, EntityUtils.getId(entity));
	}
	
	public void testGetProperty() throws Exception {
		Method method = TestEntity.class.getMethod("getId", new Class[0]);
		assertEquals("id", EntityUtils.getProperty(method));
	}
	
	public void testGetSubEntityMethods() throws Exception {
		List<Method> subs = EntityUtils.getSubEntityMethods(TestEntity.class);
		
		assertEquals(3, subs.size());
		Method getImplOne = TestEntity.class.getMethod("getIone", new Class[0]);
		Method getImplTwo = TestEntity.class.getMethod("getItwo", new Class[0]);
		Method getSubs = TestEntity.class.getMethod("getSubs", new Class[0]);
		
		assertTrue(subs.contains(getImplOne));
		assertTrue(subs.contains(getImplTwo));
		assertTrue(subs.contains(getSubs));
	}
	
	public void testGetBlobGetters() throws Exception {
		List<Method> getters = EntityUtils.getBlobGetters(BlobEntity.class);
		assertEquals(2, getters.size());
		assertTrue(getters.get(0).getName().equals("getBlobOne") || getters.get(0).getName().equals("getBlobTwo"));
		assertTrue(getters.get(1).getName().equals("getBlobOne") || getters.get(1).getName().equals("getBlobTwo"));
	}
	
	public void testGetMethod() throws Exception {
		Method method = EntityUtils.getMethod(TestEntity.class, "string");
		assertEquals("getString", method.getName());
	}
	
	public void testGetSetter() throws Exception {
		Method getter = EntityUtils.getMethod(IndexedEntity.class, "keywords");
		Method setter = EntityUtils.getSetter(getter);
		assertEquals("setKeywords", setter.getName());
		getter = EntityUtils.getMethod(IndexedEntity.class, "generatedKeywords");
		setter = EntityUtils.getSetter(getter);
		assertEquals(null, setter);
	}
	
	public void testMergeEntities() throws Exception {
		TestEntity master = new TestEntity();
		master.setAnInt(10);
		master.setString("a string");
		TestEntity tomerge = new TestEntity();
		tomerge.setAnInt(2);
		EntityUtils.mergeEntities(master, tomerge);
		assertEquals(master.getAnInt(), tomerge.getAnInt());
		assertEquals(2, master.getAnInt());
		assertEquals(null, master.getString());
		tomerge.setString("blah");
		EntityUtils.mergeEntities(master, tomerge);
		assertEquals("blah", master.getString());
	}
}
