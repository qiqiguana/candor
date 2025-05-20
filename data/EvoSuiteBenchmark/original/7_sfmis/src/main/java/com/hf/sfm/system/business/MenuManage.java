package com.hf.sfm.system.business;

import org.hibernate.Transaction;

import com.hf.sfm.system.pdo.Menu;
import com.hf.sfm.util.DaoFactory;

public class MenuManage extends DaoFactory {

	public String saveOrUpdate(Menu menu){
		System.out.println("******:idno:"+menu.getIdno()+",\nname:"+menu.getName()+",\nimg:"+menu.getImg()+",\nstatus:"+menu.getStatus());
		String rtn = "0";
		Transaction tran = null;
		try {
			this.currentSession();
			tran = this.session.beginTransaction();
			if(menu.getIdno()==null||menu.getIdno().equals("")){
				this.session.save(menu);
			}else{
				this.session.update(menu);
			}
			tran.commit();
			rtn = "1";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			tran.rollback();
			System.out.println("操作失败！");
			e.printStackTrace();
		}finally{
			this.closeSession();
		}
		return rtn;
	}
	
	public String del(String[] idnos){
		String rtn = "0";
		Transaction tran = null;
		String delsql = "delete from Menu where idno=?";
		try {
			this.currentSession();
			tran = this.session.beginTransaction();
			for(int i=0;i<idnos.length;i++){
				this.session.createQuery(delsql).setString(0, idnos[i]).executeUpdate();
			}
			tran.commit();
			rtn = "1";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			tran.rollback();
			System.out.println("删除失败！");
			e.printStackTrace();
		}
		return rtn;
	}
}
