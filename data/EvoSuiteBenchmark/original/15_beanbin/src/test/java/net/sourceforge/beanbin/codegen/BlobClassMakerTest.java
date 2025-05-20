package net.sourceforge.beanbin.codegen;

import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Blob;

import junit.framework.TestCase;
import net.sourceforge.beanbin.test.BlobEntity;
import net.sourceforge.beanbin.test.BlobOne;
import net.sourceforge.beanbin.test.BlobTwo;

public class BlobClassMakerTest extends TestCase {
	public void testInterfaceClass() throws Exception {
		BlobClassMaker maker = new BlobClassMaker(BlobEntity.class);
		Class generated = maker.getInterfaceClass();
		assertTrue(generated.isInterface());
		generated.getMethod("getBlobOneGeneratedBlob", new Class[0]);
		generated.getMethod("getBlobTwoGeneratedBlob", new Class[0]);
		Class[] params = {Blob.class};
		generated.getMethod("setBlobOneGeneratedBlob", params);
		generated.getMethod("setBlobTwoGeneratedBlob", params);
	}
	
	public void testMixinClass() throws Exception {
		BlobClassMaker maker = new BlobClassMaker(BlobEntity.class);
		Class generated = maker.getMixinClass();
		Method first = generated.getMethod("getBlobOneGeneratedBlob", new Class[0]);
		Method second = generated.getMethod("getBlobTwoGeneratedBlob", new Class[0]);
		Class[] params = {Blob.class};
		generated.getMethod("setBlobOneGeneratedBlob", params);
		generated.getMethod("setBlobTwoGeneratedBlob", params);
		
		Class[] constructParams = {Object.class};
		Constructor construct = generated.getConstructor(constructParams);
		
		BlobEntity entity = new BlobEntity();
		BlobOne one = new BlobOne();
		one.setValue("value");
		BlobTwo two = new BlobTwo();
		two.setValue("another value");
		entity.setBlobOne(one);
		entity.setBlobTwo(two);
		
		Object[] args = {entity};
		Object blobber = construct.newInstance(args);
		
		Blob firstBlob = (Blob)first.invoke(blobber, new Object[0]);
		Blob secondBlob = (Blob)second.invoke(blobber, new Object[0]);
		
		ObjectInputStream in = new ObjectInputStream(firstBlob.getBinaryStream());
		BlobOne deserial = (BlobOne)in.readObject();
		assertEquals("value", deserial.getValue());
		BlobTwo initialized = new BlobTwo();
		initialized.setInputStream(secondBlob.getBinaryStream());
		assertEquals("another value", initialized.getValue());
	}
}
