package com.hf.sfm.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *业务类继承于此类 调用相应的方法 
 */
public abstract class DaoFactoryUtil {
	public CallableStatement ps = null;
	public ResultSet rs = null;
	public Connection conn = null;	
	DaoFactory dao = new DaoFactory();
	
	/**
	 *关闭调用存储过程所使用的相关资源 
	 */
	public void closeAll(){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
