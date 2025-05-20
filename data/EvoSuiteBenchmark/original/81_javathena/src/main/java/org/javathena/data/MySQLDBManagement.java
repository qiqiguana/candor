/*
 * MySQLDBManagement.java
 *
 * Created on 26 avril 2006, 17:17
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


package org.javathena.data;

import java.sql.SQLException;

import org.javathena.core.data.Auth_data;
import org.javathena.login.UserManagement;
import org.javathena.utiles.ConfigurationManagement;
import org.javathena.utiles.sql.MySQLConfig;


/**
 *
 * @author Darksid_1
 */
public class MySQLDBManagement implements IDBManagementLogin
{
    
    /**
     * Creates a new instance of MySQLDBManagement
     */
    public MySQLDBManagement()
    {
    }
    
    public Auth_data addUser(String userid, String password, String email,char sex)
    {
        return UserManagement.addUserSQL( userid,  password,  email, sex);
    }
    
    public boolean isIpBan(String ip)
    {
        try
        {
            return MySQLConfig.checkIpBanMySQL(ip);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return true;
        }
    }
    public void login_log(String ip, String user, String rcode, String log)
    {
        MySQLConfig.login_log( ip,  user,  rcode,  log);
    }
    
    public void login_log( String log)
    {
        MySQLConfig.login_log("",  "",  "0",  log);
    }
    public Auth_data getUser(Integer account_id)
    {
        return UserManagement.getUserSQL(account_id);
    }
    
    public Auth_data getUser(String user_id)
    {
        return UserManagement.getUserSQL(user_id);
    }
    
    @Deprecated
            public int mmo_auth_init()
    {
        //Functions.showWarning("mmo_auth_init : This is a dummy method because the MySQL mode doesn't need this method!!!");
        return 0;
    }
    
    public void dynamicFailBanCheck(String string)
    {
        UserManagement.dynamicFailBanCheckMySQL(string);
    }
    
    @Deprecated
            public void mmo_auth_sync()
    {
        //Functions.showWarning("mmo_auth_sync : This is a dummy method because the MySQL mode doesn't need this method!!!");
    }

    public int login_config_read(String cfgName)
    {
        return ConfigurationManagement.login_config_readTXT(cfgName);
    }
}
