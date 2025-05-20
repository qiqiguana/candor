/*
 * MySQLConfig.java
 *
 * Created on 24 avril 2006, 10:01
 *
 * Copyright (c) 2006, Franois Bradette
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
 * Translate from eAthena(c) by Franois Bradette
 */

package org.javathena.utiles.sql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.javathena.core.utiles.Functions;
import org.javathena.utiles.ConfigurationManagement;

/**
 *
 * @author Darksid_1
 */
public class MySQLConfig
{
    public final static String SQL_CONF_NAME = "conf/inter_athena.conf";
    
    
    private static int dynamic_account_ban = 1;
    private static int dynamic_account_ban_class = 0;
    
    private static int login_server_port = 3306;
    private static String login_server_ip = "127.0.0.1";
    private static String login_server_id= "ragnarok";
    private static String login_server_pw= "ragnarok";
    private static String login_server_db= "ragnarok";
    private static String default_codepage= ""; //Feature by irmin.
    private static int use_md5_passwds = 0;
    private static String login_db = "login";
    private static String loginlog_db= "loginlog";
    private static boolean login_gm_read = true;
    private static int connection_ping_interval = 0;
    
// added to help out custom login tables, without having to recompile
// source so options are kept in the login_athena.conf or the inter_athena.conf
    private static String login_db_account_id = "account_id";
    private static String login_db_userid = "userid";
    private static String login_db_user_pass = "user_pass";
    private static String login_db_level = "level";
    
    private static String gm_db = "gm_accounts";
    private static String md5key;
    private static String reg_db = "global_reg_value";
    
    private static Connection databaseConnection;
    
    
    /**Don't creates a new instance of MySQLConfig */
    private MySQLConfig()
    {
    }
    
    public static synchronized ResultSet executeQuery(String sql) throws SQLException
    {
        return  databaseConnection.createStatement().executeQuery(sql);
    }
    
    public static synchronized int executeUpdate(String sql) throws SQLException
    {
        Statement currentStatement = databaseConnection.createStatement();
        int nbRows = currentStatement.executeUpdate(sql);
        ResultSet response = currentStatement.getGeneratedKeys();
        if(nbRows >= 0 && response.next())
            return  response.getInt(1);
        return -1;
    }
    
    public static void initConnection() throws ClassNotFoundException, SQLException
    {
        Functions.showStatus("Connect Login Database Server....\n");
        
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://"+MySQLConfig.getLogin_server_ip()+":"+MySQLConfig.getLogin_server_port()+"/" + MySQLConfig.getLogin_server_db()+"?autoReconnect=true";
        String user = MySQLConfig.getLogin_server_id();
        String password = MySQLConfig.getLogin_server_pw();
        databaseConnection =  DriverManager.getConnection( url, user, password );
        
        if( default_codepage.length() > 0 )
        {
            String sql = String.format("SET NAMES %s", default_codepage );
            executeQuery(sql);
        }
//        Login.dbManagemtType.login_log("","lserver","100","login server started");
    }
    
    public static int getDynamic_account_ban()
    {
        return dynamic_account_ban;
    }
    
    public static void setDynamic_account_ban(int dynamic_account_banP)
    {
        dynamic_account_ban = dynamic_account_banP;
    }
    
    public static int getDynamic_account_ban_class()
    {
        return dynamic_account_ban_class;
    }
    
    public static void setDynamic_account_ban_class(int dynamic_account_ban_classP)
    {
        dynamic_account_ban_class = dynamic_account_ban_classP;
    }
    
    public static int getLogin_server_port()
    {
        return login_server_port;
    }
    
    public static void setLogin_server_port(int login_server_portP)
    {
        login_server_port = login_server_portP;
    }
    
    public static String getLogin_server_ip()
    {
        return login_server_ip;
    }
    
    public static void setLogin_server_ip(String login_server_ipP)
    {
        login_server_ip = login_server_ipP;
    }
    
    public static String getLogin_server_id()
    {
        return login_server_id;
    }
    
    public static void setLogin_server_id(String login_server_idP)
    {
        login_server_id = login_server_idP;
    }
    
    public static String getLogin_server_pw()
    {
        return login_server_pw;
    }
    
    public static void setLogin_server_pw(String login_server_pwP)
    {
        login_server_pw = login_server_pwP;
    }
    
    public static String getLogin_server_db()
    {
        return login_server_db;
    }
    
    public static void setLogin_server_db(String login_server_dbP)
    {
        login_server_db = login_server_dbP;
    }
    
    public static String getDefault_codepage()
    {
        return default_codepage;
    }
    
    public static void setDefault_codepage(String default_codepageP)
    {
        default_codepage = default_codepageP;
    }
    
    public static int getUse_md5_passwds()
    {
        return use_md5_passwds;
    }
    
    public static void setUse_md5_passwds(int use_md5_passwdsP)
    {
        use_md5_passwds = use_md5_passwdsP;
    }
    
    public static String getLogin_db()
    {
        return login_db;
    }
    
    public static void setLogin_db(String login_dbP)
    {
        login_db = login_dbP;
    }
    
    public static String getLoginlog_db()
    {
        return loginlog_db;
    }
    
    public static void setLoginlog_db(String loginlog_dbP)
    {
        loginlog_db = loginlog_dbP;
    }
    
    public static boolean isLogin_gm_read()
    {
        return login_gm_read;
    }
    
    public static void setLogin_gm_read(boolean login_gm_readP)
    {
        login_gm_read = login_gm_readP;
    }
    
    public static int getConnection_ping_interval()
    {
        return connection_ping_interval;
    }
    
    public static void setConnection_ping_interval(int connection_ping_intervalP)
    {
        connection_ping_interval = connection_ping_intervalP;
    }
    
    public static String getLogin_db_account_id()
    {
        return login_db_account_id;
    }
    
    public static void setLogin_db_account_id(String login_db_account_idP)
    {
        login_db_account_id = login_db_account_idP;
    }
    
    public static String getLogin_db_userid()
    {
        return login_db_userid;
    }
    
    public static void setLogin_db_userid(String login_db_useridP)
    {
        login_db_userid = login_db_useridP;
    }
    
    public static String getLogin_db_user_pass()
    {
        return login_db_user_pass;
    }
    
    public static void setLogin_db_user_pass(String login_db_user_passP)
    {
        login_db_user_pass = login_db_user_passP;
    }
    
    public static String getLogin_db_level()
    {
        return login_db_level;
    }
    
    public static void setLogin_db_level(String login_db_levelP)
    {
        login_db_level = login_db_levelP;
    }
    
    public static String getGm_db()
    {
        return gm_db;
    }
    
    public static void setGm_db(String gm_dbP)
    {
        gm_db = gm_dbP;
    }
    
    public static String getReg_db()
    {
        return reg_db;
    }
    
    public static void setReg_db(String reg_dbP)
    {
        reg_db = reg_dbP;
    }
    
    public static String getMd5key()
    {
        return md5key;
    }
    
    public static void setMd5key(String aMd5key)
    {
        md5key = aMd5key;
    }
    public static void sql_config_read(String filePath) throws IOException, ClassNotFoundException, SQLException
    {
        File configFile = new File(filePath);
        if(!configFile.exists())
        {
            Functions.showFatalError("file not found: %s\n",filePath);
            System.exit(0);
        }
        
        String sqlConfig = Functions.readConf(configFile);
        String vtmp = Functions.getValueFromConfigString(sqlConfig,"gm_read_method");
        if(vtmp != null)
            login_gm_read = Integer.parseInt(vtmp) == 0;
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"gm_db");
        if(vtmp != null)
            gm_db = vtmp;
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"login_db");
        if(vtmp != null)
            login_db = vtmp;
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"login_server_ip");
        if(vtmp != null)
        {
            login_server_ip = vtmp;
            Functions.showStatus("set login_server_ip : %s\n",login_server_ip);
        }
        vtmp = Functions.getValueFromConfigString(sqlConfig,"login_server_port");
        if(vtmp != null)
        {
            //ConfigurationManagement.setLogin_port(Integer.parseInt(vtmp));
            Functions.showStatus("set login_server_port : %s\n",login_server_port);
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"login_server_id");
        if(vtmp != null)
        {
            login_server_id = vtmp;
            Functions.showStatus("set login_server_id : %s\n",login_server_id);
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"login_server_pw");
        if(vtmp != null)
        {
            login_server_pw = vtmp;
            Functions.showStatus("set login_server_pw : %s\n",login_server_pw);
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"login_server_db");
        if(vtmp != null)
        {
            login_server_db = vtmp;
            Functions.showStatus("set login_server_db : %s\n",login_server_db);
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"connection_ping_interval");
        if(vtmp != null)
        {
            connection_ping_interval = Integer.parseInt(vtmp);
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"default_codepage");
        if(vtmp != null)
        {
            default_codepage = vtmp;
            Functions.showStatus("set default_codepage : %s\n",default_codepage);
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"login_db_account_id");
        if(vtmp != null)
        {
            login_db_account_id = vtmp;
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"login_db_userid");
        if(vtmp != null)
        {
            login_db_userid = vtmp;
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"login_db_user_pass");
        if(vtmp != null)
        {
            login_db_user_pass = vtmp;
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"login_db_level");
        if(vtmp != null)
        {
            login_db_level = vtmp;
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"loginlog_db");
        if(vtmp != null)
        {
            loginlog_db = vtmp;
        }
        
        vtmp = Functions.getValueFromConfigString(sqlConfig,"reg_db");
        if(vtmp != null)
        {
            reg_db = vtmp;
        }
        initConnection();
    }
    

    public static boolean checkIpBanMySQL(String ipStr) throws SQLException
    {
        byte ip[] = Functions.ipStringToByteTab(ipStr);
        
        String sqlQuery = String.format("SELECT count(*) FROM `ipbanlist` WHERE `list` = '%d.*.*.*' OR `list` = '%d.%d.*.*' OR `list` = '%d.%d.%d.*' OR `list` = '%d.%d.%d.%d'",
                ip[0], ip[0], ip[1], ip[0], ip[1], ip[2], ip[0], ip[1], ip[2], ip[3]);
        ResultSet awser = executeQuery(sqlQuery);
        if(awser.next() && awser.getInt(1) > 0)
        {
            login_log(String.format("%d.%d.%d.%d",ip[0], ip[1], ip[2], ip[3]), "unknown","-3", "ip banned" );
            return true;
        }
        return false;
    }
    
    public static void login_log( String ip, String user, String rcode, String log)
    {
        if(ConfigurationManagement.getLoginAthenaConf().isLog_login())
        {
            try
            {
                executeUpdate(String.format("INSERT DELAYED INTO `%s`(`time`,`ip`,`user`,`rcode`,`log`) VALUES (NOW(), '%s', '%s','%s', '%s')",loginlog_db, ip, user, rcode, log));
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
