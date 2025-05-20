package com.hf.sfm.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

	private static Log log = LogFactory.getLog(HibernateSessionFactory.class);
	
	private static SessionFactory sessionFactory;
	static{
		try{
		sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
		}catch(HibernateException e){
			log.error("加载hibernate.cfg.xml失败！");
		}
	}
	
	public static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();
	
	public static Session currentSession(){
		Session s = threadSession.get();
		try {
			if(s==null){
				s=sessionFactory.openSession();
				threadSession.set(s);
				log.debug("创建session成功！");
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("创建session失败！");
		}
		return s;
	}
	
	public static void closeSession(){
		Session s = threadSession.get();
		threadSession.set(null);
		try {
			if(s!=null){
				s.close();
				log.debug("关闭session成功！");
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug("关闭session失败！");
		}
	}
	
	public static void main(String[] args) {
		HibernateSessionFactory.currentSession();
		HibernateSessionFactory.closeSession();
	}
}
