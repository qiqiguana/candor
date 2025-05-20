package com.hf.sfm.test;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hf.sfm.util.HibernateSessionFactory;

import junit.framework.TestCase;

public class LoaderUnitTest extends TestCase {
	

	Session session = null;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		try {
			session = HibernateSessionFactory.currentSession();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		try {
			HibernateSessionFactory.closeSession();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public void testing(){
			String[][] params = new String[2][];
			String[] values = new String[1];
			String[] ptypes = new String[1];
			values[0] = "182401";
//			values[1] = new DaoFactory().encrypt("666666");
			ptypes[0] = "String";
//			ptypes[1] = "String";
			params[0] = values;
			params[1] = ptypes;
			Loader loader = new Loader();
//			loader.run("test//test_sql", 0, 12, params);
			loader.run("system//main_tree",params);
			System.out.println("JSONString:"+loader.getMetaData());
			System.out.println("Arraytring:"+loader.getArrayData());
	}
	
}
