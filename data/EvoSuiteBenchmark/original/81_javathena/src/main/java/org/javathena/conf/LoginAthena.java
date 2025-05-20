/*
 * LoginAthena.java
 *
 * Created on March 28, 2007, 1:25 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.javathena.conf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.javathena.core.utiles.Constants;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javolution.util.FastTable;

/**
 * 
 * @author francois
 */
public class LoginAthena
{

	private static XStream xstream;

	private String bind_ip_str = "127.0.0.1"; // the address to bind to
	private int login_port;
	private int ipban_cleanup_interval; // interval (in seconds) to clean up
										// expired IP
	// bans
	private int ip_sync_interval; // interval (in minutes) to execute a DNS/IP
									// update (for dynamic IPs)
	private String admin_allowed_host; // host/ip that is allowed to connect as
										// ladmin

	private int dynamic_pass_failure_ban_interval; // how far to scan the
													// loginlog for password
													// failures
	private int dynamic_pass_failure_ban_limit; // number of failures needed to
												// trigger the ipban
	private int dynamic_pass_failure_ban_duration; // duration of the ipban
	private boolean dynamic_pass_failure_ban = true;

	private String dnsbl_servs; // comma-separated list of dnsbl servers
	private String account_engine;
	private String timestamp_format;
	// char date_format[32] = "%Y-%m-%d %H:%M:%S";
	private String date_format;

	// private String bind_ip;
	private boolean use_MD5_passwords;
	private String account_filename;
	private String login_log_filename;
	private boolean admin_state;
	private String admin_pass;// [24] = "";
	private boolean console = false;

	private boolean stdout_with_ansisequence;
	private boolean new_account_flag;
	private boolean log_login;

	private int allowed_regs;//
	private int time_allowed; // Init this to 10 seconds. [Skotlex]
	private int min_level_to_connect; // minimum level of player/GM (0: player,
										// 1-99: gm) to connect on the server
	private int start_limited_time; // Starting additional sec from now for the
									// limited time at creation of accounts (-1:
									// unlimited time, 0 or more: additional sec
									// from now)
	private boolean check_client_version; // Client version check ON/OFF ..
											// (sirius)
	private int client_version_to_connect; // Client version needed to connect
											// ..(sirius)
	private boolean use_dnsbl;
	private boolean ipban = true;
	private boolean use_md5_passwds;
	private FastTable<String> banIps;

	static
	{
		xstream = new XStream(new DomDriver());
	}

	public LoginAthena()
	{
		banIps = new FastTable<String>();
	}

	public void addToBanListTXT(String ip)
	{
		banIps.add(ip);
	}

	public boolean checkIpBanTXT(String ip)
	{
		return banIps.contains(ip);
	}

	public boolean isUse_dnsbl()
	{
		return use_dnsbl;
	}

	public void setUse_dnsbl(boolean aUse_dnsbl)
	{
		use_dnsbl = aUse_dnsbl;
	}

	public String getDate_format()
	{
		return date_format;
	}

	public void setDate_format(String aDate_format)
	{
		date_format = aDate_format;
	}

	public boolean getUse_MD5_passwords()
	{
		return use_MD5_passwords;
	}

	public void setUse_MD5_passwords(boolean aUse_MD5_passwords)
	{
		use_MD5_passwords = aUse_MD5_passwords;
	}

	public String getAccount_filename()
	{
		return account_filename;
	}

	public void setAccount_filename(String aAccount_filename)
	{
		account_filename = aAccount_filename;
	}

	public String getLogin_log_filename()
	{
		return login_log_filename;
	}

	public void setLogin_log_filename(String aLogin_log_filename)
	{
		login_log_filename = aLogin_log_filename;
	}

	public boolean getAdmin_state()
	{
		return admin_state;
	}

	public void setAdmin_state(boolean aAdmin_state)
	{
		admin_state = aAdmin_state;
	}

	public String getAdmin_pass()
	{
		return admin_pass;
	}

	public void setAdmin_pass(String aAdmin_pass)
	{
		admin_pass = aAdmin_pass;
	}

	public boolean isConsole()
	{
		return console;
	}

	public void setConsole(boolean aConsole)
	{
		console = aConsole;
	}

	public int getLogin_port()
	{
		return login_port;
	}

	public void setLogin_port(int aLogin_port)
	{
		login_port = aLogin_port;
	}

	public boolean isDynamic_pass_failure_ban()
	{
		return dynamic_pass_failure_ban;
	}

	public void setDynamic_pass_failure_ban(boolean aDynamic_pass_failure_ban)
	{
		dynamic_pass_failure_ban = aDynamic_pass_failure_ban;
	}

	public boolean isLog_login()
	{
		return log_login;
	}

	public void setLog_login(boolean aLog_login)
	{
		log_login = aLog_login;
	}

	public int getAllowed_regs()
	{
		return allowed_regs;
	}

	public void setAllowed_regs(int aAllowed_regs)
	{
		allowed_regs = aAllowed_regs;
	}

	public int getTime_allowed()
	{
		return time_allowed;
	}

	public void setTime_allowed(int aTime_allowed)
	{
		time_allowed = aTime_allowed;
	}

	public int getMin_level_to_connect()
	{
		return min_level_to_connect;
	}

	public void setMin_level_to_connect(int aMin_level_to_connect)
	{
		min_level_to_connect = aMin_level_to_connect;
	}

	public int getStart_limited_time()
	{
		return start_limited_time;
	}

	public void setStart_limited_time(int aStart_limited_time)
	{
		start_limited_time = aStart_limited_time;
	}

	public boolean isCheck_client_version()
	{
		return check_client_version;
	}

	public void setCheck_client_version(boolean aCheck_client_version)
	{
		check_client_version = aCheck_client_version;
	}

	public int getClient_version_to_connect()
	{
		return client_version_to_connect;
	}

	public void setClient_version_to_connect(int aClient_version_to_connect)
	{
		client_version_to_connect = aClient_version_to_connect;
	}

	public boolean isIpban()
	{
		return ipban;
	}

	public void setIpban(boolean aIpban)
	{
		ipban = aIpban;
	}

	public boolean isUse_md5_passwds()
	{
		return use_md5_passwds;
	}

	public String getBind_ip_str()
	{
		return bind_ip_str;
	}

	public void setBind_ip_str(String aBind_ip_str)
	{
		bind_ip_str = aBind_ip_str;
	}

	public boolean getNew_account_flag()
	{
		return new_account_flag;
	}

	public void setNew_account_flag(boolean aNew_account_flag)
	{
		new_account_flag = aNew_account_flag;
	}

	public int getIpban_cleanup_interval()
	{
		return ipban_cleanup_interval;
	}

	public void setIpban_cleanup_interval(int ipban_cleanup_interval)
	{
		this.ipban_cleanup_interval = ipban_cleanup_interval;
	}

	public int getIp_sync_interval()
	{
		return ip_sync_interval;
	}

	public void setIp_sync_interval(int ip_sync_interval)
	{
		this.ip_sync_interval = ip_sync_interval;
	}

	public String getAdmin_allowed_host()
	{
		return admin_allowed_host;
	}

	public void setAdmin_allowed_host(String admin_allowed_host)
	{
		this.admin_allowed_host = admin_allowed_host;
	}

	public int getDynamic_pass_failure_ban_interval()
	{
		return dynamic_pass_failure_ban_interval;
	}

	public void setDynamic_pass_failure_ban_interval(
			int dynamic_pass_failure_ban_interval)
	{
		this.dynamic_pass_failure_ban_interval = dynamic_pass_failure_ban_interval;
	}

	public int getDynamic_pass_failure_ban_limit()
	{
		return dynamic_pass_failure_ban_limit;
	}

	public void setDynamic_pass_failure_ban_limit(
			int dynamic_pass_failure_ban_limit)
	{
		this.dynamic_pass_failure_ban_limit = dynamic_pass_failure_ban_limit;
	}

	public int getDynamic_pass_failure_ban_duration()
	{
		return dynamic_pass_failure_ban_duration;
	}

	public void setDynamic_pass_failure_ban_duration(
			int dynamic_pass_failure_ban_duration)
	{
		this.dynamic_pass_failure_ban_duration = dynamic_pass_failure_ban_duration;
	}

	public String getDnsbl_servs()
	{
		return dnsbl_servs;
	}

	public void setDnsbl_servs(String dnsbl_servs)
	{
		this.dnsbl_servs = dnsbl_servs;
	}

	public String getAccount_engine()
	{
		return account_engine;
	}

	public void setAccount_engine(String account_engine)
	{
		this.account_engine = account_engine;
	}

	public String getTimestamp_format()
	{
		return timestamp_format;
	}

	public void setTimestamp_format(String timestamp_format)
	{
		this.timestamp_format = timestamp_format;
	}

	public FastTable<String> getBanIps()
	{
		return banIps;
	}

	public boolean isStdout_with_ansisequence()
	{
		return stdout_with_ansisequence;
	}

	public void setStdout_with_ansisequence(boolean stdout_with_ansisequence)
	{
		this.stdout_with_ansisequence = stdout_with_ansisequence;
	}

	public void setBanIps(FastTable<String> banIps)
	{
		this.banIps = banIps;
	}

	public void save() throws IOException
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.DEFAULT_LOGIN_CONF_NAME_XML)));
		out.write(xstream.toXML(this));
		out.close();
	}

	public static LoginAthena load() throws FileNotFoundException, IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(new File(
				Constants.DEFAULT_LOGIN_CONF_NAME_XML)));
		String lu = "";
		String line = null;
		 while ((line = in.readLine())!= null)
		 {
			lu += line;
		}
		in.close();
		return (LoginAthena) xstream.fromXML(lu);
	}
}