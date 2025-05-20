/*
 * ConfigurationManagement.java
 *
 * Created on 5 avril 2006, 19:57
 *
 * Translate from Eathena(c) by darksid_1@htomail.com
 */
package org.javathena.utiles;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.javathena.conf.LoginAthena;
import org.javathena.core.data.SubNetConf;
import org.javathena.core.utiles.ACO;
import org.javathena.core.utiles.Constants;
import org.javathena.core.utiles.Functions;
import org.javathena.core.utiles.MultilanguageManagement;

import javolution.util.FastTable;

//import javathena.login.*;

/**
 * 
 * @author Darksid_1
 */
public class ConfigurationManagement
{

	private static LoginAthena loginAthenaConf;

	private static int server_num;
	private static String lan_char_ip;// [16];
	private static int subneti[];// [4];
	private static int subnetmaski[];// [4];
	private static int auth_before_save_file = 0; // Counter. First save when
													// 1st char-server do
													// connection.

	private static long new_reg_tick;
	private static long num_regs;

	private static ACO access_order;
	private static int access_allownum;
	private static int access_denynum;
	private static String access_allow;
	private static String access_deny;

	private static long startLimitedTime;
	private static int GMMax;
	private static String timestamp_format;

	private static boolean console_silent;

	private static String wisp_server_name;

	private static boolean login_ip_set_;

	private static int email_creation;

	private static String char_txt;

	private static String scdata_txt;

	private static String backup_txt;

	private static String friends_txt;

	private static String backup_txt_flag;
	private static String max_connect_user;
	private static int gm_allow_level;
	private static int autosave_time;
	private static int autosave_interval;


	private static FastTable<SubNetConf> subNetConfs;

	private static int char_id_count;

	private static String lan_map_ip;
	static
	{
		loginAthenaConf = new LoginAthena();
		subNetConfs = new FastTable<SubNetConf>();
		getLoginAthenaConf().setLogin_port(Constants.DEFAULT_LOGIN_PORT);
		startLimitedTime = -1;
		GMMax = 30;

	}

	/** Creates a new instance of ConfigurationManagement */
	private ConfigurationManagement()
	{
	}



	public static int login_lan_config_read(String lancfgName)
	{
		int j;
		File fp;

		// set default configuration
		lan_char_ip = "127.0.0.1";
		subneti[0] = 127;
		subneti[1] = 0;
		subneti[2] = 0;
		subneti[3] = 1;
		for (j = 0; j < 4; j++)
			subnetmaski[j] = 255;

		fp = new File(lancfgName);

		if (fp == null || !fp.exists())
		{
			Functions.showWarning(MultilanguageManagement.getWarning_1(),
					lancfgName);
			return 1;
		}

		Functions.showInfo(MultilanguageManagement.getInfo_2(), lancfgName);

		String lu = null;

		try
		{

			lu = Functions.readConf(fp);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		StringTokenizer stLu = new StringTokenizer(lu, ":");

		String param = stLu.nextElement().toString();
		if (param.equals("subnet"))
		{
			String subnet = stLu.nextElement().toString();
			StringTokenizer subip = new StringTokenizer(subnet, ":./");

			for (j = 0; j < 4; j++)
				subneti[j] = Integer.parseInt(subip.nextElement().toString()
						.trim());

			for (j = 0; j < 4; j++)
				subnetmaski[j] = Integer.parseInt(subip.nextElement()
						.toString());

			lan_char_ip = stLu.nextElement().toString().trim();

			lan_map_ip = stLu.nextElement().toString().trim();

		}

		Functions.showInfo(MultilanguageManagement.getInfo_3(), lancfgName);
		return 0;
	}



	/**
	 * ----------------------------------- 
	 *  Reading general configuration file
	 * ---------------------------------
	 **/
	public static int login_config_readTXT(String cfgName)
	{
		// setLoginAthenaConf(new LoginAthena());
		int j;
		File fp;
		// set default configuration
		lan_char_ip = "127.0.0.1";
		subneti[0] = 127;
		subneti[1] = 0;
		subneti[2] = 0;
		subneti[3] = 1;
		for (j = 0; j < 4; j++)
			subnetmaski[j] = 255;

		fp = new File(cfgName);

		if (fp == null || !fp.exists())
		{
			Functions.showWarning(MultilanguageManagement.getWarning_2(),
					cfgName);
			return 1;
		}
		String lu = null;
		Functions.showInfo(MultilanguageManagement.getInfo_2(), cfgName);
		try
		{
			lu = Functions.readConf(fp);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		//String ladminallowip = "";
		int indArg = -1;
		if((indArg = lu.lastIndexOf("timestamp_format"))!= -1)
		{
			loginAthenaConf.setTimestamp_format(lu.substring(lu.indexOf(':',indArg) + 2, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("stdout_with_ansisequence"))!= -1)
		{
			loginAthenaConf.setStdout_with_ansisequence(Functions.config_switch((lu.substring(lu.indexOf(':',indArg) + 2, lu.indexOf('\n',indArg))).trim()) == 1);
		}
		if((indArg = lu.lastIndexOf("console_silent"))!= -1)
		{
			loginAthenaConf.setStdout_with_ansisequence(Functions.config_switch((lu.substring(lu.indexOf(':',indArg) + 2, lu.indexOf('\n',indArg))).trim()) == 1);
		}
		if((indArg = lu.lastIndexOf("console_silent"))!= -1)
		{
			Functions.showWarning("Sorry niy");
		}

		if((indArg = lu.lastIndexOf("login_port"))!= -1)
		{
			loginAthenaConf.setLogin_port(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 2, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("log_login"))!= -1)
		{
			loginAthenaConf.setLog_login(Functions.config_switch(lu.substring(lu.indexOf(':',indArg) + 2, lu.indexOf('\n',indArg)).trim()) == 1);
		}
		if((indArg = lu.lastIndexOf("new_account"))!= -1)
		{
			loginAthenaConf.setNew_account_flag(Functions.config_switch(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()) == 1);
		}
		if((indArg = lu.lastIndexOf("start_limited_time"))!= -1)
		{
			loginAthenaConf.setStart_limited_time(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("check_client_version"))!= -1)
		{
			loginAthenaConf.setCheck_client_version((Functions.config_switch(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()) == 1));
		}
		if((indArg = lu.lastIndexOf("client_version_to_connect"))!= -1)
		{
			loginAthenaConf.setClient_version_to_connect(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("use_MD5_passwords"))!= -1)
		{
			loginAthenaConf.setUse_MD5_passwords(Functions.config_switch((lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim())) == 1);
		}
		if((indArg = lu.lastIndexOf("min_level_to_connect"))!= -1)
		{
			loginAthenaConf.setMin_level_to_connect(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("date_format"))!= -1)
		{
			loginAthenaConf.setDate_format(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("console"))!= -1)
		{
			loginAthenaConf.setConsole((Functions.config_switch(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()) == 1));
		}
		if((indArg = lu.lastIndexOf("allowed_regs"))!= -1)
		{
			loginAthenaConf.setAllowed_regs(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("time_allowed"))!= -1)
		{
			loginAthenaConf.setTime_allowed(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("use_dnsbl"))!= -1)
		{
			loginAthenaConf.setUse_dnsbl((Functions.config_switch(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()) == 1));
		}
		if((indArg = lu.lastIndexOf("dnsbl_servers"))!= -1)
		{
			loginAthenaConf.setDnsbl_servs(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("ipban_cleanup_interval"))!= -1)
		{
			loginAthenaConf.setIpban_cleanup_interval(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("ip_sync_interval"))!= -1)
		{
			loginAthenaConf.setIp_sync_interval(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("admin_state"))!= -1)
		{
			loginAthenaConf.setAdmin_state((Functions.config_switch((lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim())) == 1));
		}
		if((indArg = lu.lastIndexOf("admin_pass"))!= -1)
		{
			loginAthenaConf.setAdmin_pass(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("admin_allowed_host"))!= -1)
		{
			loginAthenaConf.setAdmin_allowed_host(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("import:"))!= -1)
		{
			login_config_readTXT(lu.substring(indArg + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("account.engine"))!= -1)
		{
			loginAthenaConf.setAccount_engine(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		
/*

		if(!strcmpi(w1, "account.engine"))
			safestrncpy(login_config.account_engine, w2, sizeof(login_config.account_engine));
		else
		{// try the account engines
			int i;
			for( i = 0; account_engines[i].constructor; ++i )
			{
				AccountDB* db = account_engines[i].db;
				if( db && db->set_property(db, w1, w2) )
					break;
			}
			// try others
			ipban_config_read(w1, w2);
			loginlog_config_read(w1, w2);
		}
	}*/

		Functions.showInfo(MultilanguageManagement.getInfo_3(), cfgName);
		return 0;
	}


	public static boolean yesNoOnOffToBoolean(String anw)
	{
		anw = anw.toUpperCase();
		if (anw.equals("YES") || anw.equals("ON"))
			return true;
		if (anw.equals("NO") || anw.equals("OFF"))
			return false;
		throw new IllegalArgumentException(
				"anw doit etre egale a yes/no/on/off");
	}

	public static void display_conf_warnings()
	{
		

		if (getLoginAthenaConf().getAdmin_state())
		{
			if (getLoginAthenaConf().getAdmin_pass() == null)
			{
				Functions.showWarning(MultilanguageManagement.getWarning_4());
			}
			else if (getLoginAthenaConf().getAdmin_pass().equals("admin"))
			{
				Functions.showWarning(MultilanguageManagement.getWarning_5());
				Functions.showWarning(MultilanguageManagement.getWarning_6());
			}
		}

		if (getLoginAthenaConf().getLogin_port() < 1024
				|| getLoginAthenaConf().getLogin_port() > 65535)
		{
			Functions.showWarning(MultilanguageManagement.getWarning_13());
			getLoginAthenaConf().setLogin_port(6900);
		}

		if (getLoginAthenaConf().getMin_level_to_connect() < 0)
		{ // 0: all players, 1-99 at least gm level x
			Functions.showWarning(MultilanguageManagement.getWarning_18(),
					getLoginAthenaConf().getMin_level_to_connect());
			getLoginAthenaConf().setMin_level_to_connect(0);
		}
		else if (getLoginAthenaConf().getMin_level_to_connect() > 99)
		{ // 0: all players, 1-99 at least gm level x
			Functions.showWarning(MultilanguageManagement.getWarning_19(),
					getLoginAthenaConf().getMin_level_to_connect());
			getLoginAthenaConf().setMin_level_to_connect(99);
		}

		if (getLoginAthenaConf().getStart_limited_time() < -1)
		{ // -1: create unlimited account, 0 or more: additionnal sec from now
			// to create limited time
			Functions.showWarning(MultilanguageManagement.getWarning_22());
			Functions.showWarning(MultilanguageManagement.getWarning_23());
			getLoginAthenaConf().setStart_limited_time(-1);
		}

		if (access_order == ACO.DENY_ALLOW)
		{
			if (access_denynum == 1 && access_deny == null)
			{
				Functions.showWarning(MultilanguageManagement.getWarning_26());
			}
		}
		else if (access_order == ACO.ALLOW_DENY)
		{
			if (access_allownum == 0)
			{
				Functions.showWarning(MultilanguageManagement.getWarning_27());
			}
		}
		else
		{ // ACO_MUTUAL_FAILTURE
			if (access_allownum == 0)
			{
				Functions.showWarning(MultilanguageManagement.getWarning_28());
				Functions.showWarning(MultilanguageManagement.getWarning_29());
				Functions.showWarning(MultilanguageManagement.getWarning_30());
			}
			else if (access_denynum == 1 && access_deny == null)
			{
				Functions.showWarning(MultilanguageManagement.getWarning_31());
				Functions.showWarning(MultilanguageManagement.getWarning_32());
				Functions.showWarning(MultilanguageManagement.getWarning_33());
			}
		}

		if (getLoginAthenaConf().isDynamic_pass_failure_ban())
		{
			
		}

		return;
	}





	public static void setStart_limited_time(long aStart_limited_time)
	{
		startLimitedTime = aStart_limited_time;
	}

	public static int getGMMax()
	{
		return GMMax;
	}

	public static void setGMMax(int aGMMax)
	{
		GMMax = aGMMax;
	}

	public static void addGMMax(int aGMMax)
	{
		aGMMax += aGMMax;
	}

	public static void addGMMax()
	{
		GMMax++;
	}

	public static int getServer_num()
	{
		return server_num;
	}

	public static void setServer_num(int aServer_num)
	{
		server_num = aServer_num;
	}

	public static String getLan_char_ip()
	{
		return lan_char_ip;
	}

	public static void setLan_char_ip(String aLan_char_ip)
	{
		lan_char_ip = aLan_char_ip;
	}

	public static int[] getSubneti()
	{
		return subneti;
	}

	public static void setSubneti(int[] aSubneti)
	{
		subneti = aSubneti;
	}

	public static int[] getSubnetmaski()
	{
		return subnetmaski;
	}

	public static void setSubnetmaski(int[] aSubnetmaski)
	{
		subnetmaski = aSubnetmaski;
	}

	public static long getNew_reg_tick()
	{
		return new_reg_tick;
	}

	public static void setNew_reg_tick(long aNew_reg_tick)
	{
		new_reg_tick = aNew_reg_tick;
	}

	public static long getNum_regs()
	{
		return num_regs;
	}

	public static void setNum_regs(long aNum_regs)
	{
		num_regs = aNum_regs;
	}

	public static void addNum_regs(long aNum_regs)
	{
		num_regs += aNum_regs;
	}

	public static void addNum_regs()
	{
		num_regs++;
	}

	public static ACO getAccess_order()
	{
		return access_order;
	}

	public static void setAccess_order(ACO aAccess_order)
	{
		access_order = aAccess_order;
	}

	public static int getAccess_allownum()
	{
		return access_allownum;
	}

	public static void setAccess_allownum(int aAccess_allownum)
	{
		access_allownum = aAccess_allownum;
	}

	public static int getAccess_denynum()
	{
		return access_denynum;
	}

	public static void setAccess_denynum(int aAccess_denynum)
	{
		access_denynum = aAccess_denynum;
	}

	public static String getAccess_allow()
	{
		return access_allow;
	}

	public static void setAccess_allow(String aAccess_allow)
	{
		access_allow = aAccess_allow;
	}

	public static String getAccess_deny()
	{
		return access_deny;
	}

	public static void setAccess_deny(String aAccess_deny)
	{
		access_deny = aAccess_deny;
	}

	public static long getStartLimitedTime()
	{
		return startLimitedTime;
	}

	public static void setStartLimitedTime(long aStartLimitedTime)
	{
		startLimitedTime = aStartLimitedTime;
	}

	public static int lesslessAuth_before_save_file()
	{
		return --auth_before_save_file;
	}

	public static int getAuth_before_save_file()
	{
		return auth_before_save_file;
	}

	public static void setAuth_before_save_file(int aAuth_before_save_file)
	{
		auth_before_save_file = aAuth_before_save_file;
	}


	public static String getTimestamp_format()
	{
		return timestamp_format;
	}

	public static void setTimestamp_format(String aTimestamp_format)
	{
		timestamp_format = aTimestamp_format;
	}

	public static boolean isConsole_silent()
	{
		return console_silent;
	}

	public static void setConsole_silent(boolean aConsole_silent)
	{
		console_silent = aConsole_silent;
	}

	public static String getWisp_server_name()
	{
		return wisp_server_name;
	}

	public static String getLogin_ip_str()
	{
		return getLoginAthenaConf().getBind_ip_str();
	}

	public static boolean isLogin_ip_set_()
	{
		return login_ip_set_;
	}

	public static int getEmail_creation()
	{
		return email_creation;
	}

	public static String getChar_txt()
	{
		return char_txt;
	}

	public static String getScdata_txt()
	{
		return scdata_txt;
	}

	public static String getBackup_txt()
	{
		return backup_txt;
	}

	public static String getFriends_txt()
	{
		return friends_txt;
	}

	public static String getBackup_txt_flag()
	{
		return backup_txt_flag;
	}

	public static String getMax_connect_user()
	{
		return max_connect_user;
	}

	public static int getGm_allow_level()
	{
		return gm_allow_level;
	}

	public static int getAutosave_time()
	{
		return autosave_time;
	}

	public static int getAutosave_interval()
	{
		return autosave_interval;
	}
	
	public static void setLogin_ip_str(String aLogin_ip_str)
	{

		getLoginAthenaConf().setBind_ip_str(aLogin_ip_str);

	}

	public static int getChar_id_count()
	{
		return char_id_count;
	}

	public static void setChar_id_count(int aChar_id_count)
	{
		char_id_count = aChar_id_count;
	}

	public static void addChar_id_count(int aChar_id_count)
	{
		char_id_count += aChar_id_count;
	}

	public static void addChar_id_count()
	{
		char_id_count++;
	}

	public static int login_config_readXML(String cfgName)
	{
		File gmacc = new File(cfgName);
		if (!gmacc.exists() && gmacc.length() != 0)
		{
			Functions.showError(MultilanguageManagement.getError_1(),
					loginAthenaConf.getAccount_filename());
			return 1;
		}
		try
		{
			XStream xstream = new XStream(new DomDriver());
			setLoginAthenaConf((LoginAthena) xstream.fromXML(Functions
					.readConf(gmacc)));
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}



	private static void setLoginAthenaConf(LoginAthena loginAthenaConfa)
	{
		loginAthenaConf = loginAthenaConfa;
		
	}

	public static LoginAthena getLoginAthenaConf()
	{
		return loginAthenaConf;
	}
}
