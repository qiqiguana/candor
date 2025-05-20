package net.sourceforge.beanbin.codegen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;

import junit.framework.TestCase;
import net.sourceforge.beanbin.test.BlobEntity;
import net.sourceforge.beanbin.test.BlobOne;
import net.sourceforge.beanbin.test.BlobTwo;

import org.hibernate.Hibernate;

public class BlobClassTemplateTest extends TestCase {
	public void testGetBlobObject() throws Exception {
		BlobEntity entity = new BlobEntity();
		BlobOne one = new BlobOne();
		one.setValue("value");
		BlobTwo two = new BlobTwo();
		two.setValue("another value");
		entity.setBlobOne(one);
		entity.setBlobTwo(two);
		
		BlobClassTemplate template = new BlobClassTemplate(entity);
		BlobOne got = (BlobOne) template.getBlobObject("getBlobOne");
		assertEquals("value", got.getValue());
	}
	
	public void testGetInputStream() throws Exception {
		BlobEntity entity = new BlobEntity();
		BlobOne one = new BlobOne();
		one.setValue("value");
		BlobTwo two = new BlobTwo();
		two.setValue("another value");
		entity.setBlobOne(one);
		entity.setBlobTwo(two);
		
		BlobClassTemplate template = new BlobClassTemplate(entity);
		InputStream is = template.getInputStream(one);
		ObjectInputStream ois = new ObjectInputStream(is);
		BlobOne deserial = (BlobOne)ois.readObject();
		assertEquals("value", deserial.getValue());
		is = template.getInputStream(two);
		BlobTwo toinit = new BlobTwo();
		toinit.setInputStream(is);
		assertEquals("another value", toinit.getValue());
	}
	
	public void testSetBlob() throws Exception {
		BlobOne one = new BlobOne();
		one.setValue("value");
		BlobTwo two = new BlobTwo();
		two.setValue("another value");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream ois = new ObjectOutputStream(out);
		ois.writeObject(one);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		Blob oneBlob = Hibernate.createBlob(in);
		Blob twoBlob = Hibernate.createBlob(two.getInputStream());
		
		BlobEntity entity = new BlobEntity();
		BlobClassTemplate template = new BlobClassTemplate(entity);
		template.setBlob("getBlobOne", oneBlob);
		template.setBlob("getBlobTwo", twoBlob);
		
		assertEquals("value", entity.getBlobOne().getValue());
		assertEquals("another value", entity.getBlobTwo().getValue());
	}
}
