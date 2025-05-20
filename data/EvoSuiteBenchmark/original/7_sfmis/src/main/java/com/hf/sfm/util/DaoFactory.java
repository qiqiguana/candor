package com.hf.sfm.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *此类主要是提供一些常用的方法使用，已在DaoFactoryUtil.java中实例化，业务类只需要继承于DaoFactoryUtil即可调用
 */
import com.hf.sfm.crypt.Base64;

public class DaoFactory {

	private static Log log = LogFactory.getLog(DaoFactory.class);
	public Session session = null;
	public CallableStatement ps = null;
	public ResultSet rs = null;
	public Connection conn = null;
	private Transaction tx = null;
	
	public DaoFactory() {
	}
	/**
	 *创建会话session 
	 */
	public void currentSession(){
		this.session = HibernateSessionFactory.currentSession();
	}
	
	/**
	 *关闭session 
	 */
	public void closeSession(){
		HibernateSessionFactory.closeSession();
	}
	
	/**
	 * 提交事务并关闭相关资源
	 */
	public void commit() {
		this.tx.commit();
		closeAll();
	}

	/**
	 * 创建session开启事务
	 */
	public void beginTransaction() {
		currentSession();
		tx = this.session.beginTransaction();
	}
	
	/**
	 * 回滚事务
	 */
	public void rollback(){
		tx.rollback();
	}
	/**
	 *密码加密 
	 */
	public String encrypt(String str){
		return Base64.byteArrayToBase64(str.getBytes());
	}
	
	/**
	 *密码解密 
	 */
	public String decrypt(String s){
		return new String(Base64.base64ToByteArray(s));
	}
	
	/**
	 * 持久化一个对象
	 * @param obj
	 */
	public void save(Object obj) {
		session.save(obj);
	}

	/**
	 * 更新一个对象
	 * @param obj
	 */
	public void update(Object obj) {
		session.update(obj);
	}
	
	/**
	 *关闭数据库操作所使用的相关资源 
	 */
	public void closeAll(){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(session!=null){
			session.close();
		}
	}
}
