package org.javathena.data;

import org.javathena.login.Login;
import org.javathena.utiles.ConfigurationManagement;


public class XMLDBManagementLogin extends TXTDBManagement
{
    public int mmo_auth_init()
    {
        return Login.mmo_auth_initXML();
    }
    
    public void mmo_auth_sync()
    {
        Login.mmo_auth_syncXML();
    }
    
    
    public int login_config_read(String cfgName)
    {
        return ConfigurationManagement.login_config_readXML(cfgName);
    }
}
