package com.hf.sfm.sfmis.personinfo.business;

import org.hibernate.Transaction;

import com.hf.sfm.sfmis.personinfo.pdo.APersonInfo;
import com.hf.sfm.util.DaoFactory;


/**
 * @author 忧里修斯
 * 用户信息的管理
 */
public class PersonInfoMgr extends DaoFactory{
	
	/**
	 * 增加或更新用户的信息
	 * @param pInfo
	 */
	public String saveOrUpdate(APersonInfo pInfo){
		String rtn = "0";//0为执行失败，1为执行成功
		Transaction tx = null;
		try {
			this.currentSession();
			tx = this.session.beginTransaction();
			if(pInfo.getPersonid() == null || pInfo.getPersonid().equalsIgnoreCase("")){//新增
				this.save(pInfo);
			}else{
				this.update(pInfo);
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
	 * 删除用户的信息
	 * @param pInfo
	 */
	public String deleteByIds(String[] idnos){
		String rtn = "0";//0为执行失败，1为执行成功
		Transaction tx = null;
		try {
			this.currentSession();
			tx = this.session.beginTransaction();
			String hql = "delete from APersonInfo where personid=?";
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
