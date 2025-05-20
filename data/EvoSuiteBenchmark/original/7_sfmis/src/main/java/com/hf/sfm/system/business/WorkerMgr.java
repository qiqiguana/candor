package com.hf.sfm.system.business;

import org.hibernate.Transaction;

import com.hf.sfm.sfmis.personinfo.business.PersonInfoMgr;
import com.hf.sfm.system.pdo.AWorker;
import com.hf.sfm.util.DaoFactory;


/**
 * @author 忧里修斯
 * 用户登录信息管理
 */
public class WorkerMgr extends DaoFactory{
	
	/**
	 * 增加或更新用户登录信息
	 * @param pInfo
	 */
	public String saveOrUpdate(AWorker worker){
		
		//密码加密
		worker.setPassword(encrypt(worker.getPassword()));
		String rtn = "0";//0为执行失败，1为执行成功
		Transaction tx = null;
		try {
			this.currentSession();
			tx = this.session.beginTransaction();
			if(worker.getIdno() == null || worker.getIdno().equalsIgnoreCase("")){//新增
				this.save(worker);
			}else{//更新
				this.update(worker);
			}
			tx.commit();
			rtn = "1";
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			this.closeSession();
		}
		return rtn;
	}
	
	/**
	 * 删除用户登录的信息
	 * @param idnos
	 */
	public String deleteByIds(String[] idnos){
		String rtn = "0";//0为执行失败，1为执行成功
		Transaction tx = null;
		try {
			this.currentSession();
			tx = this.session.beginTransaction();
			String hql = "delete from AWorker where idno=?";
			for (int i = 0; i < idnos.length; i++) {
				this.session.createQuery(hql).setString(0, idnos[i]).executeUpdate();
			}
			tx.commit();
			rtn = "1";
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			this.closeSession();
		}
		return rtn;
	}
}
