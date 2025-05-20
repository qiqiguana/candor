/*
 * CharacterManagement.java
 *
 * Created on 27 novembre 2006, 12:23
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
package org.javathena.login;

//Sun jdk
import java.io.*;
import java.sql.SQLException;
import java.net.*;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.javathena.core.data.Auth_data;
import org.javathena.core.data.Socket_data;
import org.javathena.core.utiles.Constants;
import org.javathena.core.utiles.Functions;
import org.javathena.core.utiles.MultilanguageManagement;
import org.javathena.core.utiles.Version;
import org.javathena.core.utiles.ACO;
import org.javathena.data.IDBManagementLogin;
import org.javathena.data.MySQLDBManagement;
import org.javathena.data.TXTDBManagement;
import org.javathena.data.XMLDBManagementLogin;
import org.javathena.login.parse.FromClient;
import org.javathena.utiles.ConfigurationManagement;
import org.javathena.utiles.sql.MySQLConfig;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

//JavAthena
import javolution.util.FastMap;
import javolution.util.FastTable;

/**
 * 
 * @author Francois
 */
public class Login
{

	class End extends Thread
	{
		public End()
		{
		}

		// traitement qui sera effectue suite d'une interruption du programme
		public void run()
		{
			do_final();
		}
	}

	class Timer_interval_check_auth_sync extends TimerTask
	{
		public Timer_interval_check_auth_sync()
		{

		}

		public void run()
		{
			check_auth_sync();
		}
	}

	/*
	 * enum ACF { DEF, ALLOW, DENY } //*auth_dat = NULL;
	 */

	public static final int MAX_SERVERS = 30;
	public static final String LAN_CONF_NAME = "conf/subnet_athena.conf";
	// public static final int PASSWORDENC = 3;
	public static final int START_ACCOUNT_NUM = 2000000;
	public static final int END_ACCOUNT_NUM = 100000000;

	public static final int AUTH_BEFORE_SAVE_FILE = 10;
	public static final int AUTH_SAVE_FILE_DIVIDER = 50;
	public static final int DEFAULT_PASSWORDENC = 2;// System.getProperty("PASSWORDENC")
													// != null;
	private static int PASSWORDENC = DEFAULT_PASSWORDENC;// System.getProperty("PASSWORDENC")
															// != null;

	private static File log_fp;
	protected static PrintWriter loginLogOut;
	protected static int auth_num = 0;
	// according to the save method
	private static IDBManagementLogin dbManagemtType;

	private Thread connectionListenerThread;
	private ConnectionListener connectionListener;

	public static IDBManagementLogin getDbManagemtType()
	{
		return dbManagemtType;
	}

	/**
	 * @param session
	 */
	public static void codingKey(Socket_data session)
	{

		session.func_send(getMd5Data(session));
		dbManagemtType.login_log(session.getIpStr(), "", "0", String.format(
				"'ladmin': Sending request of the coding key (ip: %s)",
				session.getIpStr()));
	}

	/**
	 * @param session
	 */
	public static void codingKeyAdministration(Socket_data session)
	{
		session.func_send(getMd5Data(session));
		dbManagemtType.login_log(session.getIpStr(), "", "0", String.format(
				"Sending request of the coding key (ip: %s)",
				session.getIpStr()));
	}

	/**
	 * @param session
	 * @return byte[] Md5Data
	 */
	public static byte[] getMd5Data(Socket_data session)
	{
		if (session.getMd5key() != null)
		{
			Functions
					.showWarning("login: abnormal request of MD5 key (already opened session).");
			session.setEof(1);
		}
		String tmpMd5 = Functions.getMd5String();
		session.setMd5key(tmpMd5);
		byte data[] = new byte[tmpMd5.length() + 4];
		Functions.intToByteTab(0x01dc, 0, 2, data);
		Functions.intToByteTab(data.length, 2, 4, data);
		Functions.stringToByteTable(tmpMd5, data, 4, data.length);
		return data;
	}

	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public static int getPASSWORDENC()
	{
		return PASSWORDENC;
	}

	/**
	 * @param message
	 */
	/**
	 * @param message
	 */
	public static void login_log(String message)
	{
		if (ConfigurationManagement.getLoginAthenaConf().isLog_login())
		{
			loginLogOut.println(Functions.calendarToString(Calendar
					.getInstance()) + ": " + message);
		}
	}

	/**
	 * @param format
	 * @param args
	 */
	/**
	 * @param format
	 * @param args
	 */
	private static void login_log(String format, Object... args)
	{
		if (ConfigurationManagement.getLoginAthenaConf().isLog_login())
		{
			loginLogOut.printf(
					Functions.calendarToString(Calendar.getInstance()) + ": "
							+ format, args);
			loginLogOut.println();
		}

	}

	/**
	 * @param session
	 */
	/**
	 * @param session
	 */
	public static void logUnknownPackets(Socket_data session)
	{
	}

	public static int mmo_auth_initXML()
	{

		File gmacc = new File("save/account.xml");

		if (!gmacc.exists() && gmacc.length() != 0)
		{
			Functions.showError(MultilanguageManagement.getError_1(),
					ConfigurationManagement.getLoginAthenaConf()
							.getAccount_filename());
			return 1;
		}
		try
		{
			XStream xstream = new XStream(new DomDriver());
			UserManagement.setAuth_dats((FastMap<Integer, Auth_data>) xstream
					.fromXML(Functions.readConf(gmacc)));

		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	// public void (Socket_data session){}

	// database version reading (v2)
	// only
	/**
	 * @return
	 */
	public static int mmo_auth_initTXT()
	{

		File fp;
		int account_id;
		int server_count = 0;

		fp = new File(ConfigurationManagement.getLoginAthenaConf()
				.getAccount_filename());
		if (!fp.exists())
		{
			Functions.showError(MultilanguageManagement.getError_1(),
					ConfigurationManagement.getLoginAthenaConf()
							.getAccount_filename());
		}
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(fp));
			String ligne = null;
			ligne = in.readLine();
			while (ligne != null)
			{

				Auth_data new_account = new Auth_data();
				if (ligne.indexOf("//") != -1)
				{
					ligne = ligne.substring(0, ligne.indexOf("//"));
				}
				String[] splitedLine = ligne.split("\t");
				if (splitedLine.length > 1)
				{

					account_id = Integer.parseInt(splitedLine[0]);
					// new acount
					if (splitedLine.length == 2
							&& splitedLine[1].equals("%newid%"))
					{
						if (account_id > UserManagement.getAccountIdCount())
							UserManagement.setAccountIdCount(account_id);
					}
					if (splitedLine.length >= 12)
					{
						new_account.setAccount_id(Integer
								.parseInt(splitedLine[0]));
						new_account.setUserid(splitedLine[1].trim());
						new_account.setPass(splitedLine[2].trim());

						new_account.setSex(splitedLine[3].trim().charAt(0));
						if (splitedLine[3].trim().charAt(0) == 'S')
						{
							server_count++;
						}
						new_account.setEmail(splitedLine[4].trim());
						new_account.setLevel(Integer.parseInt(splitedLine[5]
								.trim()));
						new_account.setState(Integer.parseInt(splitedLine[6]
								.trim()));
						if (splitedLine[7].trim().length() > 1)
						{
							new_account.setBan_until_time(Long
									.parseLong(splitedLine[7].trim()));
						}

						if (splitedLine[8].trim().length() > 1)
						{
							new_account.setConnect_until_time(Long
									.parseLong(splitedLine[8].trim()));
						}
						new_account.setLogincount(Integer
								.parseInt(splitedLine[9].trim()));

						if (splitedLine[10].trim().length() > 1
								&& !splitedLine[10].trim().equals(
										"0000-00-00 00:00:00"))
						{
							new_account.setLastlogin(Functions
									.stringToCalendar(splitedLine[10].trim()));
						}
						new_account.setLast_ip(splitedLine[11].trim());

						UserManagement.addUser(new_account);
					}
				}
				ligne = in.readLine();
			}
			switch (UserManagement.getNBUser())
			{
			case 0:
				Functions.showNotice(MultilanguageManagement.getNotice_2(),
						ConfigurationManagement.getLoginAthenaConf()
								.getAccount_filename());
				dbManagemtType.login_log("", "", "0", String.format(
						MultilanguageManagement.getNotice_2(),
						ConfigurationManagement.getLoginAthenaConf()
								.getAccount_filename() + "."));
				break;
			case 1:
				Functions.showNotice(MultilanguageManagement.getNotice_3(),
						ConfigurationManagement.getLoginAthenaConf()
								.getAccount_filename());
				dbManagemtType.login_log("", "", "", String.format(
						MultilanguageManagement.getNotice_3(),
						ConfigurationManagement.getLoginAthenaConf()
								.getAccount_filename()));
				break;
			default:
				Functions.showNotice(MultilanguageManagement.getNotice_4(),
						UserManagement.getNBUser(), ConfigurationManagement
								.getLoginAthenaConf().getAccount_filename());
				dbManagemtType.login_log("", "", "", String.format(
						MultilanguageManagement.getNotice_4(), UserManagement
								.getNBUser(), ConfigurationManagement
								.getLoginAthenaConf().getAccount_filename()));
			}
			// this.getGm_account_db ()
			switch (UserManagement.getNBGM())
			{
			case 0:
				Functions.showNotice(MultilanguageManagement.getNotice_5());
				dbManagemtType.login_log(
						"",
						"",
						"0",
						String.format(MultilanguageManagement.getNotice_5()
								+ ConfigurationManagement.getLoginAthenaConf()
										.getAccount_filename() + "."));
				break;
			case 1:
				Functions.showNotice(MultilanguageManagement.getNotice_6());
				dbManagemtType.login_log("", "", "0",
						String.format(MultilanguageManagement.getNotice_6()));
				break;
			default:
				Functions.showNotice(MultilanguageManagement.getNotice_7(),
						UserManagement.getNBGM());
				dbManagemtType.login_log("", "", "0", String.format(
						MultilanguageManagement.getNotice_7(),
						UserManagement.getNBGM()));
			}
			switch (server_count)
			{
			case 0:
				Functions.showNotice(MultilanguageManagement.getNotice_8());
				dbManagemtType.login_log("", "", "0",
						String.format(MultilanguageManagement.getNotice_8()));
				break;
			case 1:
				Functions.showNotice(MultilanguageManagement.getNotice_9());
				dbManagemtType.login_log("", "", "0",
						String.format(MultilanguageManagement.getNotice_9()));
				break;
			default:
				Functions.showNotice(MultilanguageManagement.getNotice_10(),
						server_count);
				dbManagemtType.login_log("", "", "0", String.format(
						MultilanguageManagement.getNotice_10(), server_count));
			}
			in.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			Functions.showError(ex.getMessage());
		}
		return 0;
	}

	/**
	 * ------------------------------------------ // Writing of the accounts
	 * database file // (accounts are sorted by id before save)
	 * //------------------------------------------
	 */
	public static void mmo_auth_syncTXT()
	{
		File account_fp = new File(ConfigurationManagement.getLoginAthenaConf()
				.getAccount_filename());

		FastTable col = UserManagement.getAccountIds();
		col.sort();
		try
		{
			PrintWriter outAcc = new PrintWriter(new FileWriter(account_fp));
			outAcc.println("// Accounts file: here are saved all information about the accounts.\n"
					+ "// Structure: account ID, username, password, sex, email, level, state, unban time, expiration time, # of logins, last login time, last (accepted) login ip, repeated(register key, register value)\n"
					+ "// where:\n"
					+ "//   sex             : M or F for normal accounts, S for server accounts\n"
					+ "//   level           : this account's gm level\n"
					+ "//   state           : 0: account is ok, 1 to 256: error code of packet 0x006a + 1\n"
					+ "//   unban time      : 0: no ban, <other value>: banned until the date (unix timestamp)\n"
					+ "//   expiration time : 0: unlimited account, <other value>: account expires on the date (unix timestamp)");
			outAcc.flush();
			for (int i = 0; i < UserManagement.getNBUser(); i++)
			{

				if ((Integer) col.get(i) >= 0)
					outAcc.println(UserManagement.getUserTxt((Integer) col
							.get(i)));
				outAcc.flush();
			}
			outAcc.println(UserManagement.getAccountIdCount() + "\t%newid%");
			ConfigurationManagement.setAuth_before_save_file(UserManagement
					.getNBUser() / AUTH_SAVE_FILE_DIVIDER);
			if (ConfigurationManagement.getAuth_before_save_file() < Login.AUTH_BEFORE_SAVE_FILE)
				ConfigurationManagement
						.setAuth_before_save_file(Login.AUTH_BEFORE_SAVE_FILE);

			outAcc.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

	}

	public static void mmo_auth_syncXML()
	{
		File account_fp = new File(ConfigurationManagement.getLoginAthenaConf()
				.getAccount_filename());

		XStream xstream = new XStream(new DomDriver());
		FastTable col = UserManagement.getAccountIds();
		col.sort();
		try
		{
			PrintWriter outAccXML = new PrintWriter(new FileWriter(
					"save/account.xml"));
			outAccXML.println(xstream.toXML(UserManagement.getAuth_dats()));
			if (ConfigurationManagement.getAuth_before_save_file() < Login.AUTH_BEFORE_SAVE_FILE)
				ConfigurationManagement
						.setAuth_before_save_file(Login.AUTH_BEFORE_SAVE_FILE);
			outAccXML.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

	}

	/**
	 * @param aPASSWORDENC
	 */
	public static void setPASSWORDENC(int aPASSWORDENC)
	{
		PASSWORDENC = aPASSWORDENC;
	}

	/**
	 * @param donnes
	 */
	public static void showTab(byte donnes[])
	{
		for (int i = 0; i < donnes.length; i++)
		{
			System.out.println(donnes[i] + " " + (char) donnes[i]);
		}
		System.out.println();
	}

	/**
	 * @param session
	 */
	public static void version(Socket_data session)
	{
		String ip = session.getClient_addr().getInetAddress().getHostAddress();
		dbManagemtType.login_log(ip, session.getName(), "",
				String.format("Sending of the server version (ip: %s)", ip));
		byte reponse[] = new byte[10];
		reponse[0] = 0x31;
		reponse[1] = 0x75;
		reponse[2] = Version.ATHENA_MAJOR_VERSION;
		reponse[3] = Version.ATHENA_MINOR_VERSION;
		reponse[4] = Version.ATHENA_REVISION;
		reponse[5] = Version.ATHENA_RELEASE_FLAG;
		reponse[6] = Version.ATHENA_OFFICIAL_FLAG;
		reponse[7] = Version.ATHENA_SERVER_LOGIN;
		Functions.intToByteTab(Version.ATHENA_MOD_VERSION, 8, 10, reponse);
		session.func_send(reponse);
	}

	/**
 *
 */
	protected int auth_max = 0;

	/**
 *
 */
	protected int server_fd[];

	/**
 *
 */
	private final ExecutorService pool;

	/** Creates a new instance of Login int server_fd */

	/**
 *
 */
	public Login()
	{

		Runtime.getRuntime().addShutdownHook(new End());
		String PASSWORDENCtmp = System.getProperty("PASSWORDENC");
		if (PASSWORDENCtmp != null && !PASSWORDENCtmp.equals(""))
		{
			PASSWORDENC = Integer.parseInt(PASSWORDENCtmp);
		}

		ConfigurationManagement.setGMMax(4000);

		setServer_fd(new int[MAX_SERVERS]);
		UserManagement.setAccountIdCount(START_ACCOUNT_NUM);
		ConfigurationManagement.getLoginAthenaConf().setNew_account_flag(false);

		ConfigurationManagement.getLoginAthenaConf()
				.setBind_ip_str("127.0.0.1");
		ConfigurationManagement.setLan_char_ip("");
		ConfigurationManagement.setSubneti(new int[4]);
		ConfigurationManagement.setSubnetmaski(new int[4]);

		ConfigurationManagement.getLoginAthenaConf().setAccount_filename(
				"save/account.txt");
		ConfigurationManagement.getLoginAthenaConf().setLogin_log_filename(
				"log/login.log");

		ConfigurationManagement.getLoginAthenaConf().setLog_login(true);
		ConfigurationManagement.setNew_reg_tick(0);
		ConfigurationManagement.getLoginAthenaConf().setAllowed_regs(1);
		ConfigurationManagement.getLoginAthenaConf().setTime_allowed(10);

		ConfigurationManagement.setAccess_order(ACO.DENY_ALLOW);
		ConfigurationManagement.setAccess_allownum(0);
		ConfigurationManagement.setAccess_denynum(0);
		ConfigurationManagement.setAccess_allow(null);
		ConfigurationManagement.setAccess_deny(null);

		ConfigurationManagement.getLoginAthenaConf().setMin_level_to_connect(0);
		ConfigurationManagement.setStart_limited_time(-1);

		ConfigurationManagement.getLoginAthenaConf().setCheck_client_version(
				false);
		ConfigurationManagement.getLoginAthenaConf()
				.setClient_version_to_connect(20);
		pool = Executors.newFixedThreadPool(MAX_SERVERS);
		// ResourceBundle lang =
		// java.util.ResourceBundle.getBundle("javathena/lang/" +
		// Fonctions.LANG);

	}

	/**
	 * ----------------------------------------------------- // Check if we must
	 * save accounts file or not // every minute, we check if we must save
	 * because we // have do some authentifications without arrive to // the
	 * minimum of authentifications for the save. // Note: all other
	 * modification of accounts (deletion, // change of some informations
	 * excepted lastip/ // lastlogintime, creation) are always save //
	 * immediatly and set the minimum of // authentifications to its
	 * initialization value.
	 * //-----------------------------------------------------
	 */
	/**
	 * @return
	 */
	public int check_auth_sync()
	{
		// we only save if necessary:
		// we have do some authentifications without do saving
		if (ConfigurationManagement.getAuth_before_save_file() < Login.AUTH_BEFORE_SAVE_FILE
				|| ConfigurationManagement.getAuth_before_save_file() < (int) (UserManagement
						.getNBUser() / Login.AUTH_SAVE_FILE_DIVIDER))
		{
			dbManagemtType.mmo_auth_sync();
		}

		return 0;
	}

	/*
	 * @param ip An InetAddress to check
	 * 
	 * @throws NullPointerException If the <tt>ip</tt> is <tt>null</tt>
	 */
	/**
	 * @param ip
	 * @return
	 */
	public boolean check_ip(InetAddress ip)
	{
		try
		{
			return ip.isReachable(180);
		}
		catch (IOException ex)
		{
			Functions.showError(ex.getMessage());
			return false;
		}
	}

	// Not implemented yet
	/**
	 * @param ip
	 * @param str
	 * @return
	 */
	public int check_ipmask(int ip, String str)
	{
		return 0;
	}

	/**
 *
 */
	public void do_final()
	{
		int i;
		if(dbManagemtType == null)
		{
			return;
		}
		dbManagemtType.mmo_auth_sync();
		
		Functions.showInfo(MultilanguageManagement.getNotice_13());

		for (i = 0; i < UserManagement.getNBSession(); i++)
			UserManagement.getSessionAt(i).setEof(-1);
		dbManagemtType
				.login_log("", "", "0",
						"----End of login-server (normal end with closing of all files).");
		if (loginLogOut != null)
		{
			loginLogOut.flush();
			loginLogOut.close();
		}
		connectionListener.close();
		connectionListenerThread.interrupt();
		
		Functions.showStatus("Finished.");
	}

	public void do_initTXT()
	{
		dbManagemtType = new TXTDBManagement();
		dbManagemtType
				.login_config_read((System.getenv("LOGIN_CONF_NAME") != null && !System
						.getenv("LOGIN_CONF_NAME").equals("")) ? System
						.getenv("LOGIN_CONF_NAME") : Constants.DEFAULT_LOGIN_CONF_NAME);
		// dbManagemtType.readGMAccount();
		save_config_in_log(); // not before, because log file name can be
								// changed
		dbManagemtType.mmo_auth_init();
		Timer t = new Timer("sauvegarde");
		t.schedule(new Timer_interval_check_auth_sync(), 60000, 60000);
	}

	public static void do_initSQL()
	{
		dbManagemtType = new MySQLDBManagement();
		dbManagemtType
				.login_config_read((System.getenv("LOGIN_CONF_NAME") != null && !System
						.getenv("LOGIN_CONF_NAME").equals("")) ? System
						.getenv("LOGIN_CONF_NAME") : Constants.DEFAULT_LOGIN_CONF_NAME);
		Functions.showInfo("Initializing md5key...");
		MySQLConfig.setMd5key(Functions.getMd5String());
		Functions.showInfo("md5key setup complete");
		try
		{
			MySQLConfig.sql_config_read(MySQLConfig.SQL_CONF_NAME);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

		// dbManagemtType.readGMAccount();
		// ban deleter timer - 1 minute term
		/*
		 * Functions.showStatus("add interval tic (ip_ban_check)....\n");
		 * add_timer_func_list(ip_ban_check,"ip_ban_check");
		 * add_timer_interval(gettick()+10, ip_ban_check,0,0,60*1000);
		 */
	}

	public void do_initXML()
	{
		dbManagemtType = new XMLDBManagementLogin();
		dbManagemtType.login_config_read("conf/login_athena.xml");
		// dbManagemtType.readGMAccount();
		dbManagemtType.mmo_auth_init();
		UserManagement.getNBUser();
		save_config_in_log(); // not before, because log file name can be
								// changed
		mmo_auth_initXML();
		Timer t = new Timer("sauvegarde");
		t.schedule(new Timer_interval_check_auth_sync(), 60000, 60000);
	}

	public int do_init()
	{
		MultilanguageManagement.init();
		display_title();
		// read login-server configuration

		try
		{
			switch (Constants.DB_MODE)
			{
			case Constants.DB_TXT:
				do_initTXT();
				break;
			case Constants.DB_MYSQL:
				do_initSQL();
				break;
			case Constants.DB_XML:
				do_initXML();
				break;
			}
			ConfigurationManagement.display_conf_warnings(); // not in
																// login_config_read,
																// because we
																// can use
																// 'import'
																// option, and
																// display same
																// message twice
																// or more
			File account_fp = new File(ConfigurationManagement
					.getLoginAthenaConf().getAccount_filename());
		}
		catch (NullPointerException ex)
		{
			ex.printStackTrace();
		}
		ConfigurationManagement.login_lan_config_read((System
				.getenv("LAN_CONF_NAME") != null && !System.getenv(
				"LAN_CONF_NAME").equals("")) ? System.getenv("LAN_CONF_NAME")
				: LAN_CONF_NAME);

		/*
		 * if (console) { set_defaultconsoleparse(parse_console);
		 * start_console(); }
		 */

		dbManagemtType
				.login_log(
						"",
						"",
						"0",
						String.format(
								"The login-server is ready (Server is listening on the port %d).",
								ConfigurationManagement.getLoginAthenaConf()
										.getLogin_port()));
		Functions
				.showStatus(
						"The login-server is %sready%s (Server is listening on the port %d).",
						Constants.CL_GREEN, Constants.CL_RESET,
						ConfigurationManagement.getLoginAthenaConf()
								.getLogin_port());

		/*
		 * ServerSocket ss = new ServerSocket(
		 * ConfigurationManagement.getLogin_port(), 500, new
		 * InetSocketAddress(ConfigurationManagement.getLogin_ip_str(),
		 * ConfigurationManagement.getLogin_port()).getAddress() );
		 * 
		 * ServerSocket ss = new ServerSocket(ConfigurationManagement
		 * .getLoginAthenaConf().getLogin_port(), 500,
		 * InetSocketAddress.createUnresolved(
		 * ConfigurationManagement.getLogin_ip_str(),
		 * ConfigurationManagement.getLoginAthenaConf()
		 * .getLogin_port()).getAddress()); while (true) { Socket_data session =
		 * new Socket_data(ss.accept(), new ToLogin());
		 * UserManagement.addSession(session); session.setName("JavAthena"); }
		 */
		try
		{
			connectionListener = new ConnectionListener();
			connectionListenerThread = new Thread(connectionListener);
			connectionListenerThread.start();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	class ConnectionListener implements Runnable
	{
		private ServerSocket ss;
		private boolean open;

		public ConnectionListener() throws IOException
		{
			open = true;
			ss = new ServerSocket(ConfigurationManagement.getLoginAthenaConf()
					.getLogin_port(), 500, InetSocketAddress.createUnresolved(
					ConfigurationManagement.getLogin_ip_str(),
					ConfigurationManagement.getLoginAthenaConf()
							.getLogin_port()).getAddress());

		}

		@Override
		public void run()
		{
			Socket_data session;
			try
			{
				while (open)
				{
					session = new Socket_data(ss.accept(), new FromClient());
					UserManagement.addSession(session);
					session.setName("JavAthena");
				}
			}
			catch (SocketException e)
			{
				//that's probably because we close th connection while
				//we was waiting for a connection 
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public void close()
		{
			try
			{
				ss.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			open = false;
		}
	}

	/**
	 * @return
	 */
	public int getAuth_max()
	{
		return auth_max;
	}

	/**
	 * @return
	 */
	public int getAuth_num()
	{
		return auth_num;
	}

	/**
	 * @return
	 */
	public File getLog_fp()
	{
		return log_fp;
	}

	/**
	 * @return
	 */
	public PrintWriter getOut()
	{
		return loginLogOut;
	}

	/**
	 * @return
	 */
	public int[] getServer_fd()
	{
		return server_fd;
	}

	/**
	 * --------------------------------------- // Packet parsing for
	 * administation login //---------------------------------------
	 */
	/**
	 * @param commande
	 * @param session
	 * @param hote
	 * @return
	 */
	public int parse_admin(int commande, Socket_data session, Login hote)
	{
		// InetAddress sin_addr = session.getClient_addr().getInetAddress();
		// String ip = sin_addr.getHostAddress();

		if (session.getEof() == 1)
		{
			Functions.showNotice(MultilanguageManagement.getNotice_11(),
					session.getAccount_id());
			return 0;
		}

		commande += session.func_recv();

		while (commande != -1)
		{
			commande += session.func_recv() * 256;
			switch (commande)
			{
			case -1:
				return 0;
			case 0x7530:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7532:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7920:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7930:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7932:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7934:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7936:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7938:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x793a:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x793c:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x793e:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7940:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7942:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7944:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7946:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7948:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x794a:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x794c:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x794e:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7950:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7952:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7954:
				Functions.showWarning(commande + " parse_admin : niy");
			case 0x7955:
				System.out
						.println("L'administration a distance n'est pas implementer");
			}
			commande = session.func_recv();
		}
		return 0;
	}

	/**
	 * ----------------------- // Console Command Parser [Wizputer]
	 * //-----------------------
	 * 
	 * 
	 * **/

	/**
	 * @param command
	 * @return
	 */
	public int parse_console(String command)
	{
		dbManagemtType.login_log("", "", "",
				String.format("Console command : '%s'", command));

		if (command.equals("shutdown") || command.equals("exit")
				|| command.equals("quit") || command.equals("end"))
			System.exit(0);
		else if (command.equals("alive") || command.equals("status"))
			Functions.showNotice(MultilanguageManagement.getNotice_12());
		else if (command.equals("help"))
		{
			System.out.printf("\033[32mHelp of commands:\033[0m");
			System.out.printf("  To shutdown the server:");
			System.out.printf("  'shutdown|exit|qui|end'");
			System.out.printf("  To know if server is alive:");
			System.out.printf("  'alive|status'");
		}

		return 0;
	}

	// ----------------------------------
	// Reading Lan Support configuration
	// ----------------------------------
	/**
 *
 */
	public void save_config_in_log()
	{
		int i;
		loginLogOut = Functions.open_log(ConfigurationManagement
				.getLoginAthenaConf().getLogin_log_filename());
		// a newline in the log...
		login_log("");
		login_log("The login-server starting...");

		// save configuration in log file
		login_log("The configuration of the server is set:");

		if (!ConfigurationManagement.getLoginAthenaConf().getAdmin_state())
			login_log("- with no remote administration.");
		else if (ConfigurationManagement.getLoginAthenaConf().getAdmin_pass() == null)
			login_log("- with a remote administration with a VOID password.");
		else if (ConfigurationManagement.getLoginAthenaConf().getAdmin_pass()
				.equals("admin"))
			login_log("- with a remote administration with the DEFAULT password.");
		else
			login_log(
					"- with a remote administration with the password of %d character(s).",
					ConfigurationManagement.getLoginAthenaConf()
							.getAdmin_pass().length());

		if (ConfigurationManagement.getLoginAthenaConf().getNew_account_flag())
			login_log("- to ALLOW new users (with _F/_M).");
		else
			login_log("- to NOT ALLOW new users (with _F/_M).");
		login_log("- with port: %d.", ConfigurationManagement
				.getLoginAthenaConf().getLogin_port());
		login_log("- with the accounts file name: '%s'.",
				ConfigurationManagement.getLoginAthenaConf()
						.getAccount_filename());

		if (!ConfigurationManagement.getLoginAthenaConf()
				.getUse_MD5_passwords())
			login_log("- to save password in plain text.");
		else
			login_log("- to save password with MD5 encrypting.");

		if (ConfigurationManagement.getLoginAthenaConf()
				.getMin_level_to_connect() == 0) // 0: all
			// players,
			// 1-99 at
			// least gm
			// level x
			login_log("- with no minimum level for connection.");
		else if (ConfigurationManagement.getLoginAthenaConf()
				.getMin_level_to_connect() == 99)
			login_log("- to accept only GM with level 99.");
		else
			login_log("- to accept only GM with level %d or more.",
					ConfigurationManagement.getLoginAthenaConf()
							.getMin_level_to_connect());

		if (ConfigurationManagement.getLoginAthenaConf()
				.getStart_limited_time() < 0)
			login_log("- to create new accounts with an unlimited time.");
		else if (ConfigurationManagement.getLoginAthenaConf()
				.getStart_limited_time() == 0)
			login_log("- to create new accounts with a limited time: time of creation.");
		else
			login_log(
					"- to create new accounts with a limited time: time of creation %d second(s).",
					ConfigurationManagement.getLoginAthenaConf()
							.getStart_limited_time());

		if (ConfigurationManagement.getAccess_order() == ACO.DENY_ALLOW)
		{
			if (ConfigurationManagement.getAccess_denynum() == 0)
			{
				login_log("- with the IP security order: 'deny,allow' (allow if not deny). You refuse no IP.");
			}
			else if (ConfigurationManagement.getAccess_denynum() == 1
					&& ConfigurationManagement.getAccess_deny() == null)
			{
				login_log("- with the IP security order: 'deny,allow' (allow if not deny). You refuse ALL IP.");
			}
			else
			{
				login_log("- with the IP security order: 'deny,allow' (allow if not deny). Refused IP are:");
				for (i = 0; i < ConfigurationManagement.getAccess_denynum(); i++)
					login_log(ConfigurationManagement.getAccess_deny());
			}
		}
		else if (ConfigurationManagement.getAccess_order() == ACO.ALLOW_DENY)
		{
			if (ConfigurationManagement.getAccess_allownum() == 0)
			{
				login_log("- with the IP security order: 'allow,deny' (deny if not allow). But, NO IP IS AUTHORISED!");
			}
			else if (ConfigurationManagement.getAccess_allownum() == 1
					&& ConfigurationManagement.getAccess_allow() == null)
			{
				login_log("- with the IP security order: 'allow,deny' (deny if not allow). You authorise ALL IP.");
			}
			else
			{
				login_log("- with the IP security order: 'allow,deny' (deny if not allow). Authorised IP are:");
				for (i = 0; i < ConfigurationManagement.getAccess_denynum(); i++)
					login_log(ConfigurationManagement.getAccess_deny());
			}
		}
		else
		{ // ACO_MUTUAL_FAILTURE
			login_log("- with the IP security order: 'mutual-failture' (allow if in the allow list and not in the deny list).");
			if (ConfigurationManagement.getAccess_allownum() == 0)
			{
				login_log("  But, NO IP IS AUTHORISED!");
			}
			else if (ConfigurationManagement.getAccess_denynum() == 1
					&& ConfigurationManagement.getAccess_deny() == null)
			{
				login_log("  But, you refuse ALL IP!");
			}
			else
			{
				if (ConfigurationManagement.getAccess_allownum() == 1
						&& ConfigurationManagement.getAccess_allow() == null)
				{
					login_log("  You authorise ALL IP.");
				}
				else
				{
					for (i = 0; i < ConfigurationManagement.getAccess_denynum(); i++)
						login_log(ConfigurationManagement.getAccess_deny());
				}
				login_log("  Refused IP are:");
				for (i = 0; i < ConfigurationManagement.getAccess_denynum(); i++)
					login_log(ConfigurationManagement.getAccess_deny());
			}

			// dynamic password error ban
			if (ConfigurationManagement.getLoginAthenaConf()
					.isDynamic_pass_failure_ban())
				login_log("- with NO dynamic password error ban.");
			else
			{
				login_log("- with a dynamic password error ban:");
				login_log("  IP is banned for %d minutes",
						ConfigurationManagement.getLoginAthenaConf()
								.getDynamic_pass_failure_ban_duration());
			}
		}
	}

	/**
	 * @param auth_max
	 */
	public void setAuth_max(int auth_max)
	{
		this.auth_max = auth_max;
	}

	/**
	 * @param out
	 */
	public void setOut(PrintWriter out)
	{
		loginLogOut = out;
	}

	/**
	 * @param server_fd
	 */
	public void setServer_fd(int[] server_fd)
	{
		this.server_fd = server_fd;
	}

	/**
	 * @param anw
	 * @return
	 */
	public boolean yesNoOnOffToBoolean(String anw)
	{
		anw = anw.toUpperCase();
		if (anw.equals("YES") || anw.equals("ON"))
			return true;
		if (anw.equals("NO") || anw.equals("OFF"))
			return false;
		throw new IllegalArgumentException(
				"anw doit etre egale a yes/no/on/off");
	}

	public static void display_title()
	{
		Functions.showMessage(Constants.CL_WTBL + "(=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=)"	+ Constants.CL_CLL + "" + Constants.CL_NORMAL + "\n");
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BT_YELLOW + "                        (c)2005-2010 Darksid_1 presents                      " + Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // yellow writing (33)
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BOLD + "       __                 ______  __    __                                   "	+ Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // 1: bold char, 0:
		Functions.showMessage(Constants.CL_XXBL + "(" + Constants.CL_BOLD + "     / \\ \\               /\\  _  \\/\\ \\__/\\ \\                     v%2d.%02d.%02d    " + Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n",	Version.ATHENA_MAJOR_VERSION, Version.ATHENA_MINOR_VERSION, Version.ATHENA_REVISION); // 1:
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BOLD + "      \\ \\ \\  __    __   \\__\\ \\_\\ \\ \\ ,_\\ \\ \\___      __    ___      __       "	+ Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // 1: bold char, 0:
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BOLD + "  __   \\ \\ \\/'__`\\/\\ \\  / / \\  __ \\ \\ \\/\\ \\  _ `\\  /'__`\\/' _ `\\  /'__`\\     "	+ Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // 1: bold char, 0:
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BOLD + " /\\ \\___\\/ /\\ \\_\\.\\_\\ \\/ / \\ \\ \\/\\ \\ \\ \\_\\ \\ \\ \\ \\/\\  __//\\ \\/\\ \\/\\ \\_\\.\\_   " + Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // 1: bold char, 0:
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BOLD + " \\ \\______/\\ \\__/.\\_\\\\__/   \\ \\_\\ \\_\\ \\__\\\\ \\_\\ \\_\\ \\____\\ \\_\\ \\_\\ \\__/.\\_\\  "	+ Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // 1: bold char, 0:
		Functions.showMessage(Constants.CL_XXBL + "(" + Constants.CL_BOLD + "  \\/_____/  \\/__/\\/_//_/     \\/_/\\/_/\\/__/ \\/_/\\/_/\\/____/\\/_/\\/_/\\/__/\\/_/  " + Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // 1: bold char, 0:		Functions
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BOLD + "          _   _   _   _       _   _   _   _   _   _                          "	+ Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // 1: bold char, 0:
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BOLD + "         / \\ / \\ / \\ / \\     / \\ / \\ / \\ / \\ / \\ / \\                         " + Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // 1: bold char, 0:
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BOLD + "        ( j | a | v | a )   ( A | t | h | e | n | a )                        " + Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // 1: bold char, 0:
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BOLD + "         \\_/ \\_/ \\_/ \\_/     \\_/ \\_/ \\_/ \\_/ \\_/ \\_/                         " + Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // 1: bold char, 0:
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BOLD + "                                                                             "	+ Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // yellow writing (33)
		Functions.showMessage(Constants.CL_XXBL	+ "(" + Constants.CL_BT_YELLOW + "              (c)2005-2006 The Translate From C To Java Project              " + Constants.CL_XXBL + ")" + Constants.CL_CLL + Constants.CL_NORMAL + "\n"); // yellow writing (33)
		Functions.showMessage(Constants.CL_WTBL	+ "(=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=)"	+ Constants.CL_CLL + "" + Constants.CL_NORMAL + "\n\n"); // reset
	}
}
