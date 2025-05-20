/*
 * Auth_data.java
 *
 * Created on March 24, 2006, 3:06 PM
 *
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

package org.javathena.core.data;

import org.javathena.core.utiles.Functions;
import java.util.Calendar;

import javolution.util.FastMap;

/**
 * 
 * @author root
 */
public class Auth_data
{
	// Structure: account ID, username, password, sex, email, level, state,
	// unban time, expiration time, # of logins, last login time, last
	// (accepted) login ip, repeated(register key, register value)
	protected final static String PRINT_FORMAT = "%d\t%s\t%s\t%s\t%s\t%d\t%d\t%s\t%s\t%d\t%s\t%s\t";

	protected int account_id;
	protected char sex;
	protected int delflag;

	protected String userid;// [24],
	protected String pass;// [33],
	protected Calendar lastlogin;// [24]; // 33 for 32 + NULL terminated

	protected int logincount;
	protected int state; // packet 0x006a value + 1 (0: compte OK)

	protected String email;// [40]; // e-mail (by default: a@a.com)

	protected long ban_until_time; // # of seconds 1/1/1970 (timestamp): ban
									// time limit of the account (0 = no
									// ban)
	protected long connect_until_time; // # of seconds 1/1/1970 (timestamp):
										// Validity limit of the account (0
										// = unlimited)

	protected String last_ip;// [16]; // save of last IP of connection

	protected int account_reg2_num;
	protected FastMap<String, String> account_reg2;// [ACCOUNT_REG2_NUM];
	protected int version;
	protected int level;

	protected int clientType;

	protected boolean resetLogincount;

	protected int charserver;
	protected int login_id1;


	protected int login_id2;

	public Auth_data()
	{
		account_reg2 = new FastMap<String, String>();
		charserver = -1;
	}

	public Integer getAccount_id()
	{
		return account_id;
	}

	public void setAccount_id(int account_id)
	{
		this.account_id = account_id;
	}

	public char getSex()
	{
		return sex;
	}

	public void setSex(char sex)
	{
		this.sex = sex;
	}

	public int getDelflag()
	{
		return delflag;
	}

	public void setDelflag(int delflag)
	{
		this.delflag = delflag;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public String getPass()
	{
		return pass;
	}

	public void setPass(String pass)
	{
		this.pass = pass;
	}

	public Calendar getLastlogin()
	{
		return lastlogin;
	}

	public void setLastlogin(Calendar lastlogin)
	{
		this.lastlogin = lastlogin;
	}

	public void setLastlogin()
	{
		this.lastlogin = Calendar.getInstance();
		logincount++;
	}

	public int getLogincount()
	{
		return logincount;
	}

	public void setLogincount(int logincount)
	{
		if (logincount > 0 && resetLogincount)
			this.logincount = 0;
		else
			this.logincount = logincount;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		if (state > 255)
			this.state = 100;
		else if (state < 0)
			this.state = 0;
		else
			this.state = state;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public long getBan_until_time()
	{
		return ban_until_time;
	}

	public void setBan_until_time(long ban_until_time)
	{
		this.ban_until_time = ban_until_time;
	}

	public long getConnect_until_time()
	{
		return connect_until_time;
	}

	public void setConnect_until_time(long connect_until_time)
	{
		this.connect_until_time = connect_until_time;
	}

	public String getLast_ip()
	{
		return last_ip;
	}

	public void setLast_ip(String last_ip)
	{
		this.last_ip = last_ip;
	}

	public int getAccount_reg2_num()
	{
		return account_reg2.size();
	}

	public FastMap<String, String> getAccount_reg2()
	{
		return account_reg2;
	}

	public void setAccount_reg2(FastMap<String, String> account2)
	{
		account_reg2 = account2;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public boolean isResetLogincoun()
	{
		return resetLogincount;
	}

	public void setResetLogincoun(boolean resetLogincount)
	{
		this.resetLogincount = resetLogincount;
	}

	public String toString()
	{
		String str = "";

		// Structure: account ID, username, password, sex, email, level, state,
		// unban time, expiration time, # of logins, last login time, last
		// (accepted) login ip, repeated(register key, register value)
		str = String.format(
				PRINT_FORMAT,
				getAccount_id(),
				getUserid(),
				getPass(),
				sex,
				getEmail(),
				getLevel(),
				getState(),
				getBan_until_time(),
				getConnect_until_time(),
				getLogincount(),
				(getLastlogin() == null) ? "0" : Functions
						.calendarToString(getLastlogin()), getLast_ip());
		
		for (String s:account_reg2.keySet())
		{
			str += s + "," + account_reg2.get(s);
		}

		return str;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public int getLevel()
	{
		return level;
	}

	public int getClientType()
	{
		return clientType;
	}

	public void setClientType(int clientType)
	{
		this.clientType = clientType;
	}
	public int getCharserver()
	{
		return charserver;
	}

	public void setCharserver(int charserver)
	{
		this.charserver = charserver;
	}
	public int getLogin_id1()
	{
		return login_id1;
	}

	public void setLogin_id1(int login_id1)
	{
		this.login_id1 = login_id1;
	}

	public int getLogin_id2()
	{
		return login_id2;
	}

	public void setLogin_id2(int login_id2)
	{
		this.login_id2 = login_id2;
	}
}