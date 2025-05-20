/*
 * Auth_data.java
 *
 * Created on 1 mai 2006, 21:08
 *
 * Copyright (c) 2006, Fran?ois Bradette
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of their contributors may be used to endorse or
 *       promote products derived from this software without specific prior
 *       written permission.
 *
 * This software is provided by the regents and contributors "as is" and any
 * express or implied warranties, including, but not limited to, the implied
 * warranties of merchantability and fitness for a particular purpose are
 * disclaimed.  In no event shall the regents and contributors be liable for any
 * direct, indirect, incidental, special, exemplary, or consequential damages
 * (including, but not limited to, procurement of substitute goods or services;
 * loss of use, data, or profits; or business interruption) however caused and
 * on any theory of liability, whether in contract, strict liability, or tort
 * (including negligence or otherwise) arising in any way out of the use of this
 * software, even if advised of the possibility of such damage.
 *
 * Translate from eAthena(c) by Fran?ois Bradette
 */


package org.javathena.login.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javolution.util.FastMap;

import org.javathena.core.data.Auth_data;
import org.javathena.core.utiles.Functions;
import org.javathena.login.Login;
import org.javathena.login.UserManagement;
import org.javathena.utiles.sql.MySQLConfig;


/**
 *
 * @author Darksid_1
 */
public class Auth_data_SQL extends Auth_data//implements IAccountData
{
    
    /** Creates a new instance of Auth_data */
    public Auth_data_SQL(String userid, String password, String email,int sex) throws SQLException, Exception
    {
        super();
        super.setUserid(userid);
        super.setPass(password);
        super.setEmail(email);
        super.setSex((sex == 2) ? 'S' : (sex == 1 ? 'M' : 'F'));
        //Auth_data_SQL new_accountSQL = new Auth_data_SQL(new_account);
        String sql = String.format("SELECT `%s` FROM `%s` WHERE `userid` = '%s'", MySQLConfig.getLogin_db_userid(), MySQLConfig.getLogin_db(), userid);
        ResultSet anwser = null;
        char sexA = sex == 1?'M':'F';
        
        anwser = MySQLConfig.executeQuery(sql);
        if(anwser.next())
            throw new Exception("This user exist already");
        
        Functions.showInfo("New account: user: %s with passwd: %s sex: %c\n", userid, password, sexA);
        sql = String.format("INSERT INTO `%s` (`%s`, `%s`, `sex`, `email`, `level` ) VALUES ('%s', '%s', '%c', '%s', '%d')",
                MySQLConfig.getLogin_db(), MySQLConfig.getLogin_db_userid(), MySQLConfig.getLogin_db_user_pass(), userid, password , sexA, email, 0);
        
        
        
        int generatedKeys = MySQLConfig.executeUpdate(sql);
        if(generatedKeys != -1 && generatedKeys < Login.START_ACCOUNT_NUM)
        {
            sql = String.format("UPDATE `%s` SET `%s`='%d' WHERE `%s`='%d'", MySQLConfig.getLogin_db(), MySQLConfig.getLogin_db_account_id(),  Login.START_ACCOUNT_NUM, MySQLConfig.getLogin_db_account_id(), generatedKeys);
            try
            {
                MySQLConfig.executeUpdate(sql);
                MySQLConfig.executeUpdate(String.format("ALTER TABLE `%s`.`%s` AUTO_INCREMENT = %d;",MySQLConfig.getLogin_server_db(),MySQLConfig.getLogin_db(),Login.START_ACCOUNT_NUM+1));
            }
            catch (SQLException ex)
            {
                sql = String.format("DELETE FROM `%s` WHERE `%s`='%d'", MySQLConfig.getLogin_db(), MySQLConfig.getLogin_db_account_id(), generatedKeys);
                MySQLConfig.executeUpdate(sql);
                ex.printStackTrace();
                throw new Exception("The add fail!!");
            }
            Functions.showNotice("Updated New account %s's ID %d->%d (account_id must be %d or higher).", userid, generatedKeys, Login.START_ACCOUNT_NUM, Login.START_ACCOUNT_NUM);
            super.account_id = Login.START_ACCOUNT_NUM;
            return;
        }
        super.account_id = generatedKeys;
        /*
        if(tick > new_reg_tick)
        {	//Update the registration check.
                num_regs=0;
                new_reg_tick=gettick()+time_allowed*1000;
        }
        num_regs++;
      */
    }
    
    public Auth_data_SQL()
    {
        
    }
    
    public Auth_data_SQL(Auth_data nAuth_data)
    {
        super.account_id = nAuth_data.getAccount_id();
        super.account_reg2 = nAuth_data.getAccount_reg2();
        super.ban_until_time = nAuth_data.getBan_until_time();
        super.connect_until_time = nAuth_data.getConnect_until_time();
        super.delflag = nAuth_data.getDelflag();
        super.email= nAuth_data.getEmail();
        super.last_ip = nAuth_data.getLast_ip();
        super.lastlogin = nAuth_data.getLastlogin();
        super.level = nAuth_data.getLevel();
        super.logincount = nAuth_data.getLogincount();
        super.pass = nAuth_data.getPass();
        super.state = nAuth_data.getState();
        super.userid = nAuth_data.getUserid();
        super.version = nAuth_data.getVersion();
        super.sex = nAuth_data.getSex();
    }
    
    
    public void setSex(char sex)
    {
        try
        {
            MySQLConfig.executeUpdate(String.format("UPDATE `%s` SET `sex` = '%c' WHERE `%s` = '%d'",  MySQLConfig.getLogin_db(), sex,  MySQLConfig.getLogin_db_account_id(), super.account_id));
            return;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        super.sex = sex;
    }
    
    public void setLastlogin()
    {
        try
        {
            MySQLConfig.executeUpdate(String.format("UPDATE `%s` SET `lastlogin` = NOW(), `logincount`=`logincount` +1, `last_ip`='%s'  WHERE BINARY  `%s` = '%s'",
                    MySQLConfig.getLogin_db(),last_ip , MySQLConfig.getLogin_db_account_id(), super.account_id));
            return;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        super.setLastlogin();
    }
    
    
    public void setState(int state)
    {
        
        try
        {
            MySQLConfig.executeUpdate(String.format("UPDATE `%s` SET `state` = '%d' WHERE `%s` = '%d'", MySQLConfig.getLogin_db(), state,MySQLConfig.getLogin_db_account_id(),super.account_id));
            return;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        super.setState(state);
        
        
    }
    
    public void setEmail(String email)
    {
        try
        {
            MySQLConfig.executeUpdate(String.format("UPDATE `%s` SET `email` = '%s' WHERE `%s` = '%d'",MySQLConfig.getLogin_db(),email,MySQLConfig.getLogin_db_account_id(),super.account_id));
            return;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        super.setEmail(email);
    }
    
    public void setBan_until_time(long ban_until_time)
    {
        try
        {
            MySQLConfig.executeUpdate(String.format("UPDATE `%s` SET `ban_until` = '%d', `state`='7' WHERE `%s` = '%d'", MySQLConfig.getLogin_db(), ban_until_time, MySQLConfig.getLogin_db_account_id(), super.account_id));
            return;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        this.ban_until_time = ban_until_time;
    }
    
    public void setConnect_until_time(long connect_until_time)
    {
        try
        {
            MySQLConfig.executeUpdate(String.format("UPDATE `%s` SET `ban_until` = '%d', `state`='7' WHERE `%s` = '%d'", MySQLConfig.getLogin_db(), connect_until_time, MySQLConfig.getLogin_db_account_id(), super.account_id));
            
            return;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        this.connect_until_time = connect_until_time;
    }
    
    public void setLast_ip(String last_ip)
    {
        try
        {
            MySQLConfig.executeUpdate(String.format("UPDATE `%s` SET `last_ip` = '%s'  WHERE `%s` = '%d'", MySQLConfig.getLogin_db(),last_ip, MySQLConfig.getLogin_db_account_id(), super.account_id));
            
            return;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        this.last_ip = last_ip;
    }
    
    public void setMemo(String memo)
    {
        try
        {
            MySQLConfig.executeUpdate(String.format("UPDATE `%s` SET `memo` = '%s'  WHERE `%s` = '%d'", MySQLConfig.getLogin_db(),memo, MySQLConfig.getLogin_db_account_id(), super.account_id));
            
            return;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void setAccount_reg2(FastMap<String, String> account_reg2)
    {
        try
        {
            MySQLConfig.executeUpdate(String.format("DELETE FROM `%s` WHERE `type`='1' AND `account_id`='%d';",MySQLConfig.getReg_db(),super.account_id));
			Set<Map.Entry<String, String>> keys = account_reg2.entrySet();
			for(Map.Entry<String, String> me:keys)
			{
                MySQLConfig.executeUpdate(String.format("INSERT INTO `%s` (`type`, `account_id`, `str`, `value`) VALUES ( 1 , '%d' , '%s' , '%s');",  MySQLConfig.getReg_db(), super.account_id, me.getKey(), me.getValue()));
			}
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        super.account_reg2 = account_reg2;
    }
    
    public void setLevel(int level)
    {
        try
        {
            if(UserManagement.addGM(super.account_id,level))
                MySQLConfig.executeUpdate(String.format("UPDATE `%s` SET `level` = '%d'  WHERE `%s` = '%d'", MySQLConfig.getLogin_db(),level, MySQLConfig.getLogin_db_account_id(), super.account_id));
            else
                return;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return;
        }
        super.level = level;
    }
    
}
