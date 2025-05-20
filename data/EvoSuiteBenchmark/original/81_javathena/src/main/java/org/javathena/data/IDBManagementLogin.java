/*
 * IDBManagementLogin.java
 *
 * Created on 26 avril 2006, 17:09
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

import org.javathena.core.data.Auth_data;


/**
 * @author Darksid_1
 */
public interface IDBManagementLogin
{
    public abstract Auth_data addUser(String userid, String password, String email,char sex);
    public abstract Auth_data getUser(Integer account_id);
    public abstract Auth_data getUser(String user_id);
    public abstract boolean isIpBan(String ip);
    public abstract void dynamicFailBanCheck(String string);
    public abstract void mmo_auth_sync();
    public abstract void login_log(String ip, String user, String rcode, String log);
    public abstract int mmo_auth_init();
    public abstract int login_config_read(String cfgName);
}
