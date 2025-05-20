/*
 * UserManagement.java
 *
 * Created on 3 avril 2006, 20:59
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.javathena.core.data.Auth_data;
import org.javathena.core.data.Socket_data;
import org.javathena.core.utiles.Constants;
import org.javathena.core.utiles.Functions;
import org.javathena.core.utiles.MultilanguageManagement;
import org.javathena.login.parse.FromChar;
import org.javathena.login.sql.data.Auth_data_SQL;
import org.javathena.utiles.ConfigurationManagement;
import org.javathena.utiles.sql.MySQLConfig;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javolution.util.FastMap;
import javolution.util.FastTable;

/**
 * 
 * @author Darksid_1
 */
public class UserManagement
{

	/** Don't creates a new instance of UserManagement */
	private UserManagement()
	{

	}

	// private static final String LOGIN_LOG =
	// ResourceBundle.getBundle("javathena/login/Log_ "+ (Constants.DB_MODE ==
	// Constants.DB_TXT?"txt":"sql")).getString("loginlog");
	// Account saved in save/account.txt
	// account_id,account
	private static FastMap<Integer, Auth_data> auth_dats;
	// Accounts connected
	// account_id,account
	private static FastMap<Integer, Auth_data> auth_dats_connecte;
	// userid,account
	private static FastMap<String, Integer> index_userid_accountid;
	// all accont_ids
	private static FastTable<Integer> account_ids;
	// Server connected
	// userid Socket data
	private static FastMap<Integer, Socket_data> servers_connecter;

	// GM accounts
	private static FastMap<Integer, Integer> gm_account_db;

	private static FastTable<Integer> online_db;

	private static int account_id_count;

	private static FastTable<Socket_data> sessions;
	private static FastTable<Socket_data> char_sessions; // charserver connected

	// users indexed by server id
	// charserver connected
	private static FastMap<Integer, FastTable<Integer>> charServerIndex; 

	private static FastMap<String, Integer> dynamicFailBanCheck;
	static
	{
		sessions = new FastTable<Socket_data>();
		servers_connecter = new FastMap<Integer, Socket_data>();

		char_sessions = new FastTable<Socket_data>();

		auth_dats = new FastMap<Integer, Auth_data>();
		account_ids = new FastTable<Integer>();
		index_userid_accountid = new FastMap<String, Integer>();

		auth_dats_connecte = new FastMap<Integer, Auth_data>();
		online_db = new FastTable<Integer>();

		gm_account_db = new FastMap<Integer, Integer>();

		dynamicFailBanCheck = new FastMap<String, Integer>();
		charServerIndex = new FastMap<Integer, FastTable<Integer>>();
	}

	public static synchronized Auth_data addUser(Auth_data new_account)
	{
		if (index_userid_accountid.get(new_account.getAccount_id()) == null)
		{
			getAuth_dats().put(new_account.getAccount_id(), new_account);
			index_userid_accountid.put(new_account.getUserid(),
					new_account.getAccount_id());
			account_ids.add(new_account.getAccount_id());
			return new_account;
		}
		return null;
	}

	public static void addSession(Socket_data session)
	{
		sessions.add(session);
	}

	public static Socket_data getSessionAt(int ind)
	{
		return sessions.get(ind);
	}

	public static int getNBSession()
	{
		return sessions.size();
	}

	public static void numberOfUser(Socket_data session, byte packet[])
	{
		byte alivePacket[] = { 0x18, 0x27 };
		session.func_send(alivePacket);
		session.setUsers(Functions.byteTabToInt(2, 4, packet));
		Functions.showStatus("set users %s : %d\n", session.getName(),
				session.getUsers());
		// showTab(donnes);
	}

	public static void emailCreation(Socket_data session)
	{
		byte donnes[] = new byte[44];

		String ip = session.getIpStr();
		session.func_recv(donnes);

		Integer accountid = Functions.byteTabToInt(0, 4, donnes);
		String email = Functions.unsignedBytesToString(donnes, 4, 44).trim();
		if (!Functions.e_mail_check(email))
			Login.getDbManagemtType().login_log(
					ip,
					session.getName(),
					"",
					String.format(MultilanguageManagement.getLogin_log_1(),
							session.getName(), accountid, ip));
		else
		{
			Auth_data tmpAcc = Login.getDbManagemtType().getUser(accountid);
			if (tmpAcc != null
					&& (tmpAcc.getEmail().equals(Constants.DEFAULT_EMAIL) || tmpAcc
							.getEmail() == null))
			{
				tmpAcc.setEmail(email);
				Login.getDbManagemtType().mmo_auth_sync();
			}
			else
				Login.getDbManagemtType().login_log(
						ip,
						session.getName(),
						"0",
						String.format(MultilanguageManagement.getLogin_log_2(),
								session.getName(), accountid, ip));
		}
	}

	public static void emailLimitedTime(Socket_data session)
	{
		byte donnes[] = new byte[4];
		byte answer[] = new byte[50];

		String ip = session.getIpStr();
		session.func_recv(donnes);
		Integer accountid = Functions.byteTabToInt(0, 4, donnes);
		Auth_data tmpAcc = Login.getDbManagemtType().getUser(accountid);
		if (tmpAcc != null)
		{
			Functions.intToByteTab(0x2717, 0, 2, answer);
			Functions.stringToByteTable(tmpAcc.getEmail(), answer, 6, 46);
			// Functions.doubleToByteTab( tmpAcc.getConnect_until_time(),46,50,
			// answer);
			session.func_send(answer);
		}
		else
			Login.getDbManagemtType().login_log(
					ip,
					session.getName(),
					"",
					String.format(MultilanguageManagement.getLogin_log_3(),
							session.getName(), accountid, ip));
	}

	public static void statusChange(Socket_data session, byte packet[])
	{
		byte donnes[] = new byte[8];
		String ip = session.getIpStr();
		int accountid = Functions.byteTabToInt(2, 6, donnes);
		int statut = Functions.byteTabToInt(6, 10, donnes);
		Auth_data actualAccount = Login.getDbManagemtType().getUser(accountid);
		if (actualAccount != null)
		{
			if (actualAccount.getState() != statut)
			{
				Login.getDbManagemtType().login_log(
						ip,
						session.getName(),
						"" + statut,
						String.format(MultilanguageManagement.getLogin_log_4()
								+ Constants.NEWLINE, session.getName(),
								accountid, statut, ip));
				if (statut != 0)
				{
					int buff[] = new int[16];
					Functions.intToIntTab(0x2731, 0, 2, buff);
					Functions.intToIntTab(accountid, 2, 6, buff);
					Functions.intToIntTab(statut, 7, 8, buff);
					charif_sendallwos(-1, buff);
				}
				Login.getDbManagemtType().mmo_auth_sync();
			}
			else
				Login.getDbManagemtType().login_log(
						ip,
						session.getName(),
						"0" + statut,
						String.format(MultilanguageManagement.getLogin_log_5()
								+ Constants.NEWLINE, session.getName(),
								accountid, statut, ip));

		}
		else
			Login.getDbManagemtType().login_log(
					ip,
					session.getName(),
					"0" + statut,
					String.format(MultilanguageManagement.getLogin_log_6(),
							session.getName(), accountid, statut, ip));
	}

	public static void banResquest(Socket_data session)
	{
		byte donnes[] = new byte[16];
		String ip = session.getIpStr();
		session.func_recv(donnes);
		int accountid = Functions.byteTabToInt(0, 4, donnes);
		Auth_data actualAccount = Login.getDbManagemtType().getUser(accountid);
		if (actualAccount != null)
		{
			Calendar timestamp = Calendar.getInstance();
			/*
			 * if(actualAccount.getBan_until_time().getTimeInMillis() == 0 ||
			 * actualAccount.getBan_until_time().getTimeInMillis() <
			 * timestamp.getTimeInMillis()) timestamp = Calendar.getInstance();
			 * else timestamp = actualAccount.getBan_until_time();
			 * timestamp.set(timestamp.get(Calendar.YEAR) +
			 * Functions.byteTabToInt(4,8,donnes), timestamp.get(Calendar.MONTH)
			 * + Functions.byteTabToInt(8,10,donnes),
			 * timestamp.get(Calendar.DAY_OF_MONTH) +
			 * Functions.byteTabToInt(10,12,donnes),
			 * timestamp.get(Calendar.HOUR_OF_DAY) +
			 * Functions.byteTabToInt(12,14,donnes),
			 * timestamp.get(Calendar.MINUTE) +
			 * Functions.byteTabToInt(14,16,donnes),
			 * timestamp.get(Calendar.SECOND) +
			 * Functions.byteTabToInt(16,18,donnes));
			 */
			if (timestamp != null)
			{
				if (timestamp.getTimeInMillis() <= Calendar.getInstance()
						.getTimeInMillis())
					timestamp = null;

				/*
				 * if(actualAccount.getBan_until_time().getTimeInMillis() !=
				 * timestamp.getTimeInMillis()) { if(timestamp != null) { int
				 * buff[] = new int[16]; String timeString =
				 * Functions.calendarToString(timestamp);
				 * Login.getDbManagemtType().login_log(ip,session.getName(),"",
				 * String.format(MultilanguageManagement.getLogin_log_7() +
				 * Constants.NEWLINE, session.getAccount_id(), accountid,
				 * timestamp, (timestamp.getTimeInMillis() == 0 ?
				 * "no banishment" : timeString), ip));
				 * 
				 * Functions.intToIntTab(0x2731,0,2,buff);
				 * Functions.intToIntTab(accountid,2,6,buff); buff[6] = 1; // 0:
				 * change of statut, 1: ban
				 * Functions.longToIntTab(timestamp.getTimeInMillis(), 7,
				 * 16,buff);// status or final date of a banishment
				 * charif_sendallwos(-1, buff); KeySession tmpKey =
				 * getKeySession(accountid);
				 * tmpKey.setLogin1(tmpKey.getLogin1()+1);
				 * addKeySession(accountid,tmpKey);
				 * actualAccount.setBan_until_time(timestamp); } else {
				 * Login.getDbManagemtType().login_log(ip,session.getName(),"",
				 * String.format(MultilanguageManagement.getLogin_log_8() +
				 * Constants.NEWLINE, session.getName(), accountid, ip)); }
				 * 
				 * Login.getDbManagemtType().mmo_auth_sync(); } else {
				 * Login.getDbManagemtType().login_log(ip,session.getName(),"",
				 * String.format(MultilanguageManagement.getLogin_log_9() +
				 * Constants.NEWLINE, session.getName(), accountid, ip)); }
				 */
			}
			else
			{
				Login.getDbManagemtType().login_log(
						ip,
						session.getName(),
						"",
						String.format(MultilanguageManagement.getLogin_log_10()
								+ Constants.NEWLINE, session.getName(),
								accountid, ip));
			}
		}
		else
			Login.getDbManagemtType().login_log(
					ip,
					session.getName(),
					"",
					String.format(MultilanguageManagement.getLogin_log_11()
							+ Constants.NEWLINE, session.getName(), accountid,
							ip));

	}

	public static void changeSex(Socket_data session)
	{
		byte donnes[] = new byte[4];
		session.func_recv(donnes);
		String ip = session.getIpStr();
		int accountid = Functions.byteTabToInt(0, 4, donnes);
		Auth_data actualAccount = Login.getDbManagemtType().getUser(accountid);
		int sex;
		if (actualAccount != null)
		{
			if (actualAccount.getSex() != 2)
			{
				Login.getDbManagemtType().login_log(
						ip,
						session.getName(),
						"0",
						String.format(MultilanguageManagement.getLogin_log_12()
								+ Constants.NEWLINE, session.getName(),
								accountid, actualAccount.getSex(), ip));
			}
			else
			{
				if (actualAccount.getSex() == 0)
				{
					sex = 1;
				}
				else
				{
					sex = 0;
				}
				Login.getDbManagemtType().login_log(
						ip,
						session.getName(),
						"0",
						String.format(MultilanguageManagement.getLogin_log_13()
								+ Constants.NEWLINE, session.getName(),
								accountid, (sex == 2) ? 'S' : (sex == 1 ? 'M'
										: 'F'), ip));
				actualAccount.setLogin_id1(actualAccount.getLogin_id1() + 1);
				actualAccount.setSex((sex == 2) ? 'S' : (sex == 1 ? 'M' : 'F'));
				int buff[] = new int[16];
				Functions.intToIntTab(0x2723, 0, 2, buff);
				Functions.intToIntTab(accountid, 2, 6, buff);
				buff[7] = sex;
				charif_sendallwos(-1, buff);
				// Save
				Login.getDbManagemtType().mmo_auth_sync();

			}
		}
		else
		{
			Login.getDbManagemtType().login_log(
					ip,
					session.getName(),
					"0",
					String.format(MultilanguageManagement.getLogin_log_14()
							+ Constants.NEWLINE, session.getName(), accountid,
							ip));
		}
	}

	public static void receiveAccountReg2(Socket_data session, byte[] donnes)
	{

		int accountid = Functions.byteTabToInt(4, 8, donnes);
		Auth_data actualAccount = Login.getDbManagemtType().getUser(accountid);

		String ip = session.getIpStr();
		int length = Functions.byteTabToInt(2, 4, donnes);
		FastMap<String, String> accountReg2 = new FastMap<String, String>();
		if (actualAccount != null)
		{
			session.func_recv(donnes);
			Login.getDbManagemtType().login_log(
					ip,
					session.getName(),
					"0",
					String.format(MultilanguageManagement.getLogin_log_15()
							+ Constants.NEWLINE, session.getName(), accountid,
							ip));

			for (int j = 0, p = 13; j < Constants.ACCOUNT_REG2_NUM
					&& p < length; j++)
			{
				String key = Functions.unsignedBytesToString(donnes, p
						+ (j * 31), p + ((j + 31) * 31));
				p += 31;
				String value = Functions.unsignedBytesToString(donnes, p
						+ (j * 255), p + ((j + 255) * 255));
				p += 255;
				accountReg2.put(key, value);
			}
			actualAccount.setAccount_reg2(accountReg2);
			//
			Functions.intToByteTab(0x2729, 0, 2, donnes);
			charif_sendallwos(session.getAccount_id(), donnes);
		}
		else
		{
			Login.getDbManagemtType().login_log(
					ip,
					session.getName(),
					"0",
					String.format(MultilanguageManagement.getLogin_log_16()
							+ Constants.NEWLINE, session.getName(), accountid,
							session.getIpStr()));
		}
	}

	public static void toChangeAnEmail(Socket_data session)
	{
		Auth_data actualAccount;
		byte donnes[] = new byte[84];
		String ip = session.getIpStr();
		String actual_email, new_email;
		session.func_recv(donnes);
		int accountid = Functions.byteTabToInt(0, 4, donnes);
		actual_email = Functions.unsignedBytesToString(donnes, 4, 44).trim();
		new_email = Functions.unsignedBytesToString(donnes, 44, 84).trim();
		if (!Functions.e_mail_check(actual_email))
		{
			Login.getDbManagemtType().login_log(
					ip,
					session.getName(),
					"0",
					String.format(MultilanguageManagement.getLogin_log_17()
							+ Constants.NEWLINE, session.getName(), accountid,
							ip));
			Login.getDbManagemtType().getUser(accountid).setEmail("a@a.com");
		}
		else if (!Functions.e_mail_check(new_email))
			Login.getDbManagemtType().login_log(
					ip,
					session.getName(),
					"0",
					String.format(MultilanguageManagement.getLogin_log_18()
							+ Constants.NEWLINE, session.getName(), accountid,
							ip));
		else if (new_email.equals("a@a.com"))
			Login.getDbManagemtType().login_log(
					ip,
					session.getName(),
					"0",
					String.format(MultilanguageManagement.getLogin_log_19()
							+ Constants.NEWLINE, session.getName(), accountid,
							ip));
		else
		{
			actualAccount = Login.getDbManagemtType().getUser(accountid);
			if (actualAccount != null)
			{
				if (actualAccount.getEmail().equals(actual_email))
				{
					actualAccount.setEmail(new_email);
					Login.getDbManagemtType().login_log(
							ip,
							session.getName(),
							"0",
							String.format(
									MultilanguageManagement.getLogin_log_20()
											+ Constants.NEWLINE,
									session.getName(), accountid,
									actualAccount.getUserid(), new_email, ip));
					Login.getDbManagemtType().mmo_auth_sync();
				}
				else
					Login.getDbManagemtType().login_log(
							ip,
							session.getName(),
							"0",
							String.format(
									MultilanguageManagement.getLogin_log_21()
											+ Constants.NEWLINE,
									session.getName(), accountid,
									actualAccount.getUserid(), new_email, ip));
			}
			else
				Login.getDbManagemtType().login_log(
						ip,
						session.getName(),
						"0",
						String.format(MultilanguageManagement.getLogin_log_22()
								+ Constants.NEWLINE, session.getName(),
								accountid, ip));
		}
	}

	@Deprecated
	public static void toBecomeGM(Socket_data session)
	{

	}

	public static int tabHexToInt(byte tab[])
	{
		int t = 0;
		for (int i = 0; i < tab.length; i++)
		{
			t = Functions.parseByteToInt(tab[i])
					* ((int) Math.pow(0x100, i));
		}
		return t;
	}

	public static synchronized Auth_data removeUser(Auth_data new_account)
	{
		if (index_userid_accountid.get(new_account.getAccount_id()) != null)
		{
			online_db.remove(new_account.getAccount_id());
			index_userid_accountid.remove(new_account.getUserid());
			account_ids.remove(new Integer(new_account.getAccount_id()));
			getAuth_dats().remove(new_account.getAccount_id());
			return new_account;

		}
		return null;

	}

	// -----------------------------------------------------
	// Clear Online User Database
	// -----------------------------------------------------
	public static int online_db_final()
	{
		online_db.clear();
		return 0;
	}

	public static int add_online_user(Auth_data account)
	{
		if (!is_user_online(account.getAccount_id()))
		{
			auth_dats_connecte.put(new Integer(account.getAccount_id()),
					account);
			online_db.add(account.getAccount_id());
			account.setLastlogin(Calendar.getInstance());
			return 0;
		}
		return -1;
	}

	public static void connectionOfCharServer(Socket_data session, byte[] donnes)
	{
		byte donnesEnvoi[];

		String userId = "";
		String pass = "";
		String server_name = "";
		int ip[] = new int[4];
		int port = 0;
		userId = Functions.unsignedBytesToString(donnes, 2, 26);
		pass = Functions.unsignedBytesToString(donnes, 26, 52);

		ip[0] = donnes[54];
		ip[1] = donnes[55];
		ip[2] = donnes[56];
		ip[3] = donnes[57];

		port = Functions.unsignedByteToInt(donnes[58]) * 0x100;
		port += Functions.unsignedByteToInt(donnes[59]);

		session.setListenPort(port);
		session.setName(Functions.byteTabToString(60, 81, donnes));

		String ipWithDot = ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
		Login.getDbManagemtType().login_log(
				session.getIpStr(),
				session.getName(),
				"0",
				String.format(MultilanguageManagement.getLogin_log_27()
						+ server_name + " @ " + ip[0] + "." + ip[1] + "."
						+ ip[2] + "." + ip[3] + ":" + port + " (ip: "
						+ ipWithDot + ")"));
		Auth_data tmp;

		tmp = Login.getDbManagemtType().getUser(userId);
		if (tmp != null)
		{
			Integer id = tmp.getAccount_id();
			if (tmp.getSex() == 'S')
			{
				if (servers_connecter.get((tmp.getAccount_id() + "")) == null)
				{
					if (!pass.equals(tmp.getPass()))
					{
						Functions.showNotice(
								MultilanguageManagement.getNotice_14(),
								server_name, tmp.getAccount_id(),
								tmp.getUserid(), tmp.getPass(), ipWithDot);
						Login.getDbManagemtType().login_log(
								session.getIpStr(),
								session.getName(),
								"0",
								String.format(MultilanguageManagement
										.getLogin_log_28(), server_name, tmp
										.getAccount_id(), tmp.getUserid(), tmp
										.getPass(), ipWithDot));
					}
					else
					{
						// session.setIp(ip);

						session.setMaintenance(Functions.byteTabToInt(82, 84,
								donnes));
						session.setUsers(0);
						session.setNew_(Functions.byteTabToInt(84, 86, donnes));
						servers_connecter.put(id, session);
						char_sessions.add(session);
						auth_dats_connecte.put(id, tmp);
						donnesEnvoi = new byte[3];
						donnesEnvoi[0] = 0x11;
						donnesEnvoi[1] = 0x27;
						donnesEnvoi[2] = 0;
						session.func_send(donnesEnvoi);
						session.setFunc_parse(new FromChar());
						session.setAccount_id(id);
						tmp.setLastlogin();
						// send_GM_accounts();
						return;
					}
				}
				else
				{
					Functions.showNotice(
							MultilanguageManagement.getNotice_15(),
							server_name, tmp.getAccount_id(), tmp.getUserid(),
							tmp.getPass(), session.getIpStr());
					Login.getDbManagemtType().login_log(
							session.getIpStr(),
							session.getName(),
							"0",
							String.format(
									MultilanguageManagement.getLogin_log_29(),
									server_name, tmp.getAccount_id(),
									tmp.getUserid(), tmp.getPass(),
									session.getIpStr()));
				}
			}
		}
		else
		{
			Functions.showNotice(MultilanguageManagement.getNotice_16(),
					server_name, userId, pass, session.getIpStr());
			Login.getDbManagemtType().login_log(
					session.getIpStr(),
					session.getName(),
					"0",
					String.format(MultilanguageManagement.getLogin_log_30(),
							server_name, userId, pass, session.getIpStr()));
		}
		donnesEnvoi = new byte[3];
		donnesEnvoi[0] = 0x11;
		donnesEnvoi[1] = 0x27;
		donnesEnvoi[2] = 3;
		session.func_send(donnesEnvoi);
	}

	public static void charServerToAuthentify(Socket_data session, byte[] donnes)
	{

		int acc;
		byte response[] = new byte[25];

		acc = Functions.byteTabToInt(2, 6, donnes);
		Auth_data accountToConnect = Login.getDbManagemtType().getUser(acc);

		int login_id1 = Functions.byteTabToInt(6, 10, donnes);
		int login_id2 = Functions.byteTabToInt(10, 14, donnes);
		byte sex = donnes[14];
		int resquestid = Functions.byteTabToInt(19, 23, donnes);
		response[0] = 0x13;
		response[1] = 0x27;
		Functions.intToByteTab(acc, 2, 6, response);
		Functions.intToByteTab(login_id1, 6, 10, response);
		Functions.intToByteTab(login_id2, 10, 14, response);
		response[14] = sex;
		if (accountToConnect != null && accountToConnect.getAccount_id() == acc
				&& accountToConnect.getLogin_id1() == login_id1
				&& accountToConnect.getLogin_id2() == login_id2
				&& accountToConnect.getSex() == Functions.byteSexToChar(sex))
		{
			Functions.intToByteTab(resquestid, 16, 20, response);
			Functions.intToByteTab(accountToConnect.getVersion(), 20, 24,
					response);
			Functions.intToByteTab(accountToConnect.getClientType(), 24, 25,
					response);
			session.func_send(response);
			System.out.printf("[%d", response[0]);
			for (int i = 1; i < response.length; i++)
			{
				System.out.printf(",%d",
						Functions.unsignedByteToInt(response[i]));
			}
			System.out.println("]" + response.length);
		}
		else
		{
			response[15] = 1;
			Functions.intToByteTab(resquestid, 16, 20, response);
			session.func_send(response);
		}

	}

	public static void account_idToOnline(Socket_data session, byte[] donnes)
	{
		Integer account_id = Functions.byteTabToInt(2, 6, donnes);
		Auth_data toConnect = Login.getDbManagemtType().getUser(account_id);
		add_online_user(toConnect);
	}

	public static void account_idToOffline(Socket_data session, byte packet[])
	{
		Integer account_id = Functions.byteTabToInt(0, 4, packet);
		Auth_data toDisconnect = Login.getDbManagemtType().getUser(account_id);
		// Auth_data toDisconnect = auth_dats.get(account_id);
		if (toDisconnect != null)
		{
			auth_dats_connecte.remove(account_id);
			System.out.println(account_id);
			online_db.remove(account_id);
		}
	}

	public static void connectionOfClient(Socket_data session, byte packet[],
			boolean encrypted)
	{
		int version;
		String username;
		String password;
		byte clientType;
		Auth_data userAccount;
		byte authenticationResult = -1;
		byte[] answer;
		String MD5;
		version = Functions.byteTabToInt(2, 6, packet);
		username = Functions.byteTabToString(6, 30, packet, true);

		if (encrypted)
		{
			MD5 = session.getMd5key();
			password = Functions.byteTabToString(30, 46, packet, true);
			clientType = packet[46];
		}
		else
		{
			password = Functions.byteTabToString(30, 54, packet, true);
			clientType = packet[54];
		}
		if (username.toUpperCase().endsWith("_M")
				|| username.toUpperCase().endsWith("_F"))
		{
			if(!createAccount(username.substring(0, username.length() - 2),
					password,
					username.toUpperCase().charAt(username.length() - 1),
					session.getIpStr()))
			{
				Functions.showNotice("Cannot create account (account: %s, received "
						+ "pass: %s, ip: %s)\n", username, password,
						session.getIpStr());
			}else{
			username = username.substring(0, username.length() - 2);}
		}
		userAccount = Login.getDbManagemtType().getUser(username);
		if (userAccount != null)
		{
			userAccount.setLast_ip(session.getIpStr());
			userAccount.setClientType(clientType);
			userAccount.setVersion(version);
			authenticationResult = authentication(userAccount, password);
		}
		else
		{
			Functions.showNotice("Unknown account (account: %s, received "
					+ "pass: %s, ip: %s)\n", username, password,
					session.getIpStr());
			authenticationResult = 0; // 0 = Unregistered ID

		}
		if (authenticationResult == -1)
		{
			if (userAccount.getLevel() < ConfigurationManagement
					.getLoginAthenaConf().getMin_level_to_connect())
			{

				Functions
						.showStatus(
								"Connection refused: the minimum GM level for connection is %d (account: %s, GM level: %d).\n",
								ConfigurationManagement
										.getLoginAthenaConf().getMin_level_to_connect(), userAccount
										.getUserid(), userAccount.getLevel());
				answer = new byte[3];
				Functions.intToByteTab(0x81, 0, 2, answer);
				answer[2] = 1;// 01 = Server closed
				session.func_send(answer);
				session.close();
				return;
			}

			if (char_sessions.size() == 0)
			{
				Functions
						.showStatus(
								"Connection refused: there is no char-server online (account: %s).\n",
								userAccount.getUserid());
				answer = new byte[3];
				Functions.intToByteTab(0x81, 2, answer);
				answer[2] = 1;// 01 = Server closed
				session.func_send(answer);
				session.close();
				return;
			}
			else
			{
				if (auth_dats_connecte.get(userAccount.getAccount_id()) != null)
				{
					if (userAccount.getCharserver() != -1)
					{
						Functions.showNotice(
								"User '%s' is already online - Rejected.\n",
								userAccount.getUserid());
						byte[] toAllCharServer = new byte[6];
						Functions.intToByteTab(0x2734, 0, 2, toAllCharServer);
						Functions.intToByteTab(userAccount.getAccount_id(), 2,
								6, toAllCharServer);
						charif_sendallwos(-1, toAllCharServer);

						answer = new byte[3];
						Functions.intToByteTab(0x81, 0, 2, answer);
						answer[2] = 8;
						session.func_send(answer);
						session.close();
					}
					else
					{
						auth_dats_connecte.remove(userAccount.getAccount_id());
					}
				}
				if (userAccount.getLevel() > 0)
				{
					Functions.showStatus(
									"Connection of the GM (level:%d) account '%s' accepted.\n",
									userAccount.getLevel(),
									userAccount.getUserid());
				}
				else
				{
					Functions.showStatus(
							"Connection of the account '%s' accepted.\n",
							userAccount.getUserid());
				}
				answer = new byte[47 + 32 * char_sessions.size()];
				Functions.intToByteTab(0x69, 0, 2, answer);
				Functions.intToByteTab(answer.length, 2, 4, answer);
				Functions
						.intToByteTab(userAccount.getLogin_id1(), 4, 8, answer);
				Functions.intToByteTab(userAccount.getAccount_id(), 8, 12,
						answer);
				Functions.intToByteTab(userAccount.getLogin_id2(), 12, 16,
						answer);
				Functions.intToByteTab(userAccount.getSex(), 46, 47, answer);
				int j = 0;
				for (Socket_data cs : char_sessions)
				{
					byte[] ipTab = cs.getIpTab();

					/*
					 * Functions.byteTableToByteTab(ipTab, 47 + 32 * j, 51 + 32
					 * * j, answer);
					 */
					answer[47 + 32 * j] = 127;
					answer[48 + 32 * j] = 0;
					answer[49 + 32 * j] = 0;
					answer[50 + 32 * j] = 1;
					Functions.intToByteTab(cs.getListenPort(), 51 + 32 * j,
							53 + 32 * j, answer);
					Functions.stringToByteTable(cs.getName(), answer,
							53 + 32 * j, 73 + 32 * j);

					Functions.intToByteTab(cs.getUsers(), 73 + 32 * j,
							75 + 32 * j, answer);
					Functions.intToByteTab(cs.getMaintenance(), 75 + 32 * j,
							77 + 32 * j, answer);
					Functions.intToByteTab(cs.getNew_(), 77 + 32 * j,
							79 + 32 * j, answer);

					j++;
				}
				session.func_send(answer);
			}

		}
		else
		{

			if (ConfigurationManagement.getLoginAthenaConf().isLog_login())
			{
				String error;
				switch (authenticationResult)
				{
				case 0:
					error = "Unregistered ID.";
					break; // 0 = Unregistered ID
				case 1:
					error = "Incorrect Password.";
					break; // 1 = Incorrect Password
				case 2:
					error = "Account Expired.";
					break; // 2 = This ID is expired
				case 3:
					error = "Rejected from server.";
					break; // 3 = Rejected from Server
				case 4:
					error = "Blocked by GM.";
					break; // 4 = You have been blocked by the GM Team
				case 5:
					error = "Not latest game EXE.";
					break; // 5 = Your Game's EXE file is not the latest version
				case 6:
					error = "Banned.";
					break; // 6 = Your are Prohibited to log in until %s
				case 7:
					error = "Server Over-population.";
					break; // 7 = Server is jammed due to over populated
				case 8:
					error = "Account limit from company";
					break; // 8 = No more accounts may be connected from this
							// company
				case 9:
					error = "Ban by DBA";
					break; // 9 = MSI_REFUSE_BAN_BY_DBA
				case 10:
					error = "Email not confirmed";
					break; // 10 = MSI_REFUSE_EMAIL_NOT_CONFIRMED
				case 11:
					error = "Ban by GM";
					break; // 11 = MSI_REFUSE_BAN_BY_GM
				case 12:
					error = "Working in DB";
					break; // 12 = MSI_REFUSE_TEMP_BAN_FOR_DBWORK
				case 13:
					error = "Self Lock";
					break; // 13 = MSI_REFUSE_SELF_LOCK
				case 14:
				case 15:
					error = "Not Permitted Group";
					break; // 15 = MSI_REFUSE_NOT_PERMITTED_GROUP
				case 99:
					error = "Account gone.";
					break; // 99 = This ID has been totally erased
				case 100:
					error = "Login info remains.";
					break; // 100 = Login information remains at %s
				case 101:
					error = "Hacking investigation.";
					break; // 101 = Account has been locked for a hacking
							// investigation. Please contact the GM Team for
							// more information
				case 102:
					error = "Bug investigation.";
					break; // 102 = This account has been temporarily prohibited
							// from login due to a bug-related investigation
				case 103:
					error = "Deleting char.";
					break; // 103 = This character is being deleted. Login is
							// temporarily unavailable for the time being
				case 104:
					error = "Deleting spouse char.";
					break; // 104 = This character is being deleted. Login is
							// temporarily unavailable for the time being
				default:
					error = "Unknown Error.";
					break;
				}
				Functions.showError(error);
				// TODO log error message
				// login_log(ip, sd->userid, result, error);
			}
			answer = new byte[23];
			Functions.intToByteTab(0x6a, 0, 2, answer);
			answer[2] = authenticationResult;

			if (authenticationResult == 6)
			{
				// TODO add until date time
			}
			session.func_send(answer);
			 session.close();
		}

		// session.close();
		/*
		 * connectionOfClient(session, userAccount, userid, password,
		 * validPassword, version);
		 */
	}

	private static boolean createAccount(String userid, String password,
			char sex, String ipStr)
	{
		int len = userid.length();
		Auth_data userAccount = Login.getDbManagemtType().getUser(userid);
		if (ConfigurationManagement.getLoginAthenaConf().getNew_account_flag()
				&& UserManagement.getAccountIdCount() <= Login.END_ACCOUNT_NUM
				&& len >= 4 && password.length() >= 4 && userAccount == null)
		{
			if (System.currentTimeMillis() <= ConfigurationManagement
					.getNew_reg_tick()
					&& ConfigurationManagement.getNum_regs() >= ConfigurationManagement
							.getLoginAthenaConf().getAllowed_regs())

			{
				Functions.showNotice(MultilanguageManagement.getNotice_17(),
						ipStr);
				ConfigurationManagement.addNum_regs();
				return false;
			} 
			else
            {
                ConfigurationManagement.setNum_regs(0);
            }
            if(ConfigurationManagement.getNum_regs() == 0)
                ConfigurationManagement.setNum_regs(System.currentTimeMillis() + ConfigurationManagement.getLoginAthenaConf().getTime_allowed() *1000);
			ConfigurationManagement.addNum_regs();

            userAccount = Login.getDbManagemtType().addUser(userid.substring(0, len),password,Constants.DEFAULT_EMAIL,sex);
            ConfigurationManagement.setAuth_before_save_file(0);
		}
		return true;
	}

	private static byte authentication(Auth_data userAccount, String password)
	{
		// TODO check if ban listed and network stuff

		// Client Version check
		if (ConfigurationManagement.getLoginAthenaConf().isCheck_client_version()
				&& userAccount.getVersion() != ConfigurationManagement
						.getLoginAthenaConf().getClient_version_to_connect())
			return 5;
		if (!password.equals(userAccount.getPass()))
		{
			// check_password(sd->md5key, sd->passwdenc, sd->passwd, acc.pass)
			Functions
					.showNotice(
							"Invalid password (account: '%s', pass: '%s', received pass: '%s', ip: %s)\n",
							userAccount.getUserid(), userAccount.getPass(),
							password, userAccount.getLast_ip());
			return 1; // 1 = Incorrect Password
		}
		if (userAccount.getConnect_until_time() != 0
				&& userAccount.getConnect_until_time() < System
						.currentTimeMillis())
		{
			Functions
					.showNotice(
							"Connection refused (account: %s, pass: %s, expired ID, ip: %s)\n",
							userAccount.getUserid(), password,
							userAccount.getLast_ip());
			return 2; // 2 = This ID is expired
		}
		if (userAccount.getBan_until_time() != 0
				&& userAccount.getBan_until_time() > System.currentTimeMillis())
		{
			Functions
					.showNotice(
							"Connection refused (account: %s, pass: %s, banned until %s, ip: %s)\n",
							userAccount.getUserid(), password,
							userAccount.getLast_ip());
			return 6; // 6 = Your are Prohibited to log in
		}
		if (userAccount.getState() != 0)
		{
			Functions
					.showNotice(
							"Connection refused (account: %s, pass: %s, state: %d, ip: %s)\n",
							userAccount.getUserid(), password,
							userAccount.getState(), userAccount.getLast_ip());
			return (byte) (userAccount.getState() - 1);
		}

		int login_id = new java.security.SecureRandom().nextInt();
		while (login_id < 1)
			login_id = new java.security.SecureRandom().nextInt();
		userAccount.setLogin_id1(login_id);

		login_id = new java.security.SecureRandom().nextInt();
		while (login_id < 1)
			login_id = new java.security.SecureRandom().nextInt();
		userAccount.setLogin_id2(login_id);

		userAccount.setLastlogin();

		if (userAccount.getSex() != 2
				&& userAccount.getAccount_id() < Constants.START_ACCOUNT_NUM)
		{
			Functions
					.showWarning(
							"Account %s has account id %d! Account IDs must be over %d to work properly!\n",
							userAccount.getUserid(),
							userAccount.getAccount_id(),
							Constants.START_ACCOUNT_NUM);
		}

		return -1;
	}

	private static int authenticationNewAccount(Auth_data userAccount)
	{
		if (ConfigurationManagement.getLoginAthenaConf().getNew_account_flag())
		{

		}
		return -1;
	}

	public static int levelIsValid(int level, String GM_account_filename,
			int GM_num, int line_counter)
	{
		if (level <= 0)
		{
			Functions.showError(MultilanguageManagement.getError_16(),
					GM_account_filename, (GM_num + 1), line_counter, level);
			level = -1;
		}
		else
		{
			if (level > 99)
			{// , GM_account_filename, GM_num+1, level
				Functions.showNotice(MultilanguageManagement.getNotice_18(),
						GM_account_filename, (GM_num + 1), level);
				level = 99;
			}
		}
		return level;
	}

	// ----------------------------------------------------------------------
	// Adds a new GM using acc id and level
	// ----------------------------------------------------------------------
	/**
	 * @param account_id
	 * @param level
	 **/
	public static boolean addGM(int account_id, int level)
	{
		boolean do_add = false;

		if (Login.getDbManagemtType().getUser(account_id) != null)
		{
			do_add = true;
		}

		if (getGM(account_id) != null)
		{
			if (getGMLevel(account_id) == level)
			{
				Functions.showWarning(MultilanguageManagement.getWarning_43(),
						account_id, level);
				return false;
			}
			else
			{
				Functions.showWarning(MultilanguageManagement.getWarning_44(),
						account_id, getGMLevel(account_id), level);
				getGM(account_id).setLevel(level);
			}
			return true;
		}

		// if new account
		if (ConfigurationManagement.getGMMax() > UserManagement.getNBGM()
				&& do_add)
		{
			Auth_data nGM = new Auth_data();
			nGM.setLevel(level);
			gm_account_db.put(account_id, nGM.getLevel());
			if (UserManagement.getNBGM() >= ConfigurationManagement.getGMMax())
			{
				Functions.showWarning(MultilanguageManagement.getWarning_45(),
						ConfigurationManagement.getGMMax());
				Login.getDbManagemtType().login_log(
						"",
						"",
						"0",
						String.format(
								MultilanguageManagement.getLogin_log_46(),
								ConfigurationManagement.getGMMax()));
			}
			return true;
		}
		return false;
	}

	public static void remove_online_user(Integer account_id)
	{
		if (account_id == 99)
			online_db_final();
		else
			online_db.remove(account_id);
	}

	public static boolean is_user_online(Integer account_id)
	{
		return online_db.contains(account_id);
	}

	public static int getNBUser()
	{
		return getAuth_dats().size();
	}

	public static int getNBGM()
	{
		return gm_account_db.size();
	}

	public static int charif_sendallwos(int sfd, int[] buf)
	{

		Integer i, c = 0, fd;
		for (i = 0; i < char_sessions.size(); i++)
		{
			fd = char_sessions.get(i).getAccount_id();
			if (fd >= 0 && fd != sfd)
			{
				sessions.get(i).func_send(buf);
				c++;
			}
		}
		return c;
	}

	public static int charif_sendallwos(int sfd, byte[] buf)
	{

		Integer i, c = 0, fd;
		for (i = 0; i < char_sessions.size(); i++)
		{
			fd = char_sessions.get(i).getAccount_id();
			if (fd >= 0 && fd != sfd)
			{
				sessions.get(i).func_send(buf);
				c++;
			}
		}
		return c;
	}

	public static synchronized Auth_data addUserTXT(String userid,
			String password, String email, char sex)
	{

		Auth_data new_account = new Auth_data();

		int account_id = account_id_count++;
		new_account.setAccount_id(account_id);
		new_account.setUserid(userid);
		new_account.setSex(sex);
		new_account.setLogincount(0);
		new_account.setState(0);
		if (Functions.e_mail_check(email))
			new_account.setEmail(email);
		else
			new_account.setEmail("-");

		Calendar datetmp = Calendar.getInstance();
		datetmp.setTimeInMillis(0);
		// new_account.setBan_until_time(datetmp);

		if (ConfigurationManagement.getLoginAthenaConf().getStart_limited_time() < 0)
		{
			datetmp.setTimeInMillis(0); // unlimited
		}
		else
		{
			datetmp.setTimeInMillis(datetmp.getTimeInMillis()
					+ ConfigurationManagement.getLoginAthenaConf().getStart_limited_time());

		}
		// new_account.setConnect_until_time(datetmp);

		new_account.setLast_ip(null);

		if (ConfigurationManagement.getLoginAthenaConf().isUse_md5_passwds())
		{
			// TODO fixe creation of encryted password
			// new_account.setPass(Functions.encryptePassword(password));

		}
		else
			new_account.setPass(password);
		return addUser(new_account);
	}

	public static synchronized int isGM(long account_id)
	{
		Auth_data tmp = getAuth_dats().get(account_id);
		if (tmp != null)
		{
			return (Integer) gm_account_db.get(account_id);
		}
		tmp = null;
		return -1;
	}

	public static synchronized void setAccountIdCount(int aic)
	{
		account_id_count = aic;
	}

	public static synchronized int getAccountIdCount()
	{
		return account_id_count;
	}

	public static synchronized void addAccountIdCount()
	{
		account_id_count++;
	}

	public static synchronized void addAccountIdCount(int nb)
	{
		account_id_count += nb;
	}

	public static Auth_data getUserTxt(Integer account_id)
	{
		return auth_dats.get(account_id);
	}

	public static Auth_data getUserTxt(String user_id)
	{
		Integer accountID = index_userid_accountid.get(user_id);
		if (accountID != null)
			return getAuth_dats().get(accountID);
		else
			return null;
	}

	public static Auth_data getUserSQL(Integer account_id)
	{
		String sql = String
				.format("SELECT `%s`,`%s`,`%s`,`lastlogin`,`logincount`,`sex`,`connect_until`,`last_ip`,`ban_until`,`state`,`%s`,`email` FROM `%s` WHERE BINARY `%s`='%s'",
						MySQLConfig.getLogin_db_account_id(),
						MySQLConfig.getLogin_db_userid(),
						MySQLConfig.getLogin_db_user_pass(),
						MySQLConfig.getLogin_db_level(),
						MySQLConfig.getLogin_db(),
						MySQLConfig.getLogin_db_account_id(), account_id);
		try
		{
			ResultSet result = MySQLConfig.executeQuery(sql);
			return getUserSQL(result);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public static Auth_data getUserSQL(ResultSet result) throws SQLException
	{

		Auth_data user = null;
		if (result.next())
		{
			user = new Auth_data();
			user.setAccount_id(result.getInt(1));
			user.setUserid(result.getString(2));
			user.setPass(result.getString(3));
			Calendar lastlogin = Calendar.getInstance();
			try
			{
				String dateStr = result.getString(4);
				if (!dateStr.equals("00-00-00 00:00:00"))
					lastlogin.setTime(result.getDate(4));
				else
					lastlogin.setTimeInMillis(0);
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
			user.setLastlogin(lastlogin);
			user.setLogincount(result.getInt(5));
			user.setSex(result.getString(6).charAt(0));
			Calendar cut = Calendar.getInstance();
			cut.setTimeInMillis(result.getInt(7));
			;
			// user.setConnect_until_time(cut);
			user.setLast_ip(result.getString(8));
			Calendar but = Calendar.getInstance();
			but.setTimeInMillis(result.getInt(9));
			// user.setBan_until_time(but);
			user.setState(result.getInt(10));
			user.setLevel(result.getInt(11));
			user.setEmail(result.getString(12));
			return new Auth_data_SQL(user);
		}
		return null;
	}

	public static Auth_data getUserSQL(String user_id)
	{
		String sql = String
				.format("SELECT `%s`,`%s`,`%s`,`lastlogin`,`logincount`,`sex`,`connect_until`,`last_ip`,`ban_until`,`state`,`%s`,`email` FROM `%s` WHERE BINARY `%s`='%s'",
						MySQLConfig.getLogin_db_account_id(),
						MySQLConfig.getLogin_db_userid(),
						MySQLConfig.getLogin_db_user_pass(),
						MySQLConfig.getLogin_db_level(),
						MySQLConfig.getLogin_db(),
						MySQLConfig.getLogin_db_userid(), user_id);
		try
		{
			ResultSet result = MySQLConfig.executeQuery(sql);
			return getUserSQL(result);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public static Integer getAccountID(String user_id)
	{
		return index_userid_accountid.get(user_id);
	}

	public static void unLogServer(Socket_data socket_data)
	{
		sessions.remove(socket_data);
		char_sessions.remove(socket_data);
		online_db.remove(socket_data.getAccount_id());
	}

	public static FastMap<Integer, Auth_data> getAuth_dats()
	{
		return auth_dats;
	}

	public static FastTable<Integer> getAccountIds()
	{

		Object[] en = getAuth_dats().keySet().toArray();
		FastTable<Integer> col = new FastTable<Integer>();
		for (int i = 0; i < en.length; i++)
			col.add((Integer)en[i]);
		return col;
	}

	private static int getGMLevel(Integer id)
	{
		return gm_account_db.get(id);
	}

	private static Auth_data getGM(int account_id)
	{
		Integer level = gm_account_db.get(account_id);
		if (level != null && level > 0)
			return Login.getDbManagemtType().getUser(account_id);
		else
			return null;
	}

	public static void connectionOfClientEncrypted(Socket_data session)
	{
		Auth_data userAccount;
		byte data[] = new byte[45];
		session.func_recv(data);
		String userid = "";
		String password = "";
		int version = Functions.byteTabToInt(0, 4, data);
		userid = Functions.unsignedBytesToString(data, 4, 27).trim();
		password = Functions.unsignedBytesToString(data, 28, 16).trim();
		userAccount = Login.getDbManagemtType().getUser(userid);
		String MD5 = session.getMd5key();

		int length = userid.length();
		char lastChar = userid.toUpperCase().charAt(length - 1);
		if (userid.charAt(length - 2) == '_'
				&& (lastChar == 'F' || lastChar == 'M'))
		{
			userAccount = Login.getDbManagemtType().getUser(
					userid.substring(0, length - 2));
			if (userAccount != null)
			{
				return;
			}
		}
		else
		{
			userAccount = Login.getDbManagemtType().getUser(userid);
		}

	}

	public static void changeSexChrif_changesex(Socket_data session)
	{

		byte data[] = new byte[2];
		session.func_recv(data);
		// TODO reengenier
		int length = Functions.byteTabToInt(0, 2, data);
		data = new byte[length - 4];
		int accountid = Functions.byteTabToInt(0, 2, data);
		int sex = Functions.byteTabToInt(2, 4, data);
		Auth_data userTempo = Login.getDbManagemtType().getUser(accountid);
		String ip = session.getIpStr();

		if (userTempo != null)
		{
			Login.getDbManagemtType().login_log(
					ip,
					userTempo.getUserid(),
					"",
					String.format(MultilanguageManagement.getLogin_log_47()
							+ Constants.NEWLINE, session.getName(), accountid,
							(sex == 2) ? 'S' : (sex == 1 ? 'M' : 'F'),
							session.getIpStr()));
			userTempo.setSex((sex == 2) ? 'S' : (sex == 1 ? 'M' : 'F'));
			userTempo.setLogin_id1(userTempo.getLogin_id1() + 1);
			int buff[] = new int[16];
			Functions.intToIntTab(0x2723, 0, 2, buff);
			Functions.intToIntTab(accountid, 2, 4, buff);
			Functions.intToIntTab(sex, 2, 4, buff);
			charif_sendallwos(-1, buff);
		}
		else
		{
			Login.getDbManagemtType().login_log(
					ip,
					"none",
					"",
					String.format(MultilanguageManagement.getLogin_log_48()
							+ Constants.NEWLINE, session.getName(), accountid,
							(sex == 2) ? 'S' : (sex == 1 ? 'M' : 'F'),
							session.getIpStr()));
		}
	}

	/**
	 * login request by an admin
	 * 
	 * @param Socket_data
	 * 
	 */
	public static void administationLogin(Socket_data session)
	{
		boolean encrypted = session.func_recv() != 0;
		byte donnes[];

		if (encrypted)
			donnes = new byte[17];
		else
			donnes = new byte[25];

		session.func_recv(donnes);
	}

	public static Auth_data addUserSQL(String userid, String password,
			String email, int sex)
	{
		Auth_data_SQL new_accountSQL;
		try
		{
			new_accountSQL = new Auth_data_SQL(userid, password, email, sex);
			Auth_data new_account = addUser(new_accountSQL);
			return new_account;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public static void dynamicFailBanCheckMySQL(String ipStr)
	{
		String sql = String
				.format("SELECT count(*) FROM `%s` WHERE `ip` = '%s' AND `rcode` = '1' AND `time` > NOW() - INTERVAL %d MINUTE",
						MySQLConfig.getLoginlog_db(), ipStr, 0);
		try
		{
			ResultSet anwser = MySQLConfig.executeQuery(sql);
			if (anwser.next())
			{
				addIpToBanListMySQL(ipStr);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void addIpToBanListMySQL(String ipStr) throws SQLException
	{
		byte[] ipTab = Functions.ipStringToByteTab(ipStr);
		String sql = String
				.format("INSERT INTO `ipbanlist`(`list`,`btime`,`rtime`,`reason`) VALUES ('%d.%d.%d.*', NOW() , NOW() +  INTERVAL %d MINUTE ,'Password error ban:')",
						ipTab[0], ipTab[1], ipTab[2], ConfigurationManagement
								.getLoginAthenaConf().getDynamic_pass_failure_ban_duration());
		MySQLConfig.executeUpdate(sql);
	}

	public static void dynamicFailBanCheckTXT(String ip)
	{
		Integer nb = dynamicFailBanCheck.get(ip);
		if (nb == null)
			nb = 0;
		if (nb >= 10)
			ConfigurationManagement.getLoginAthenaConf().addToBanListTXT(ip);
		else
			dynamicFailBanCheck.put(ip, ++nb);
	}

	public static void sendUserList(Socket_data session)
	{
		byte data[] = new byte[8];
		int start = Functions.byteTabToInt(0, 4, data);
		int end = Functions.byteTabToInt(4, 8, data);

		byte dataToSend[] = new byte[(38 * account_ids.size()) + 4];
		int length = 4;
		account_ids.sort();
		for (int i = 0; account_ids.get(i) < account_ids.size()
				&& account_ids.get(i) > start && account_ids.get(i) < end; i++)
		{
			Integer accountId = account_ids.get(i);
			Auth_data account = Login.getDbManagemtType().getUser(accountId);

			Functions.intToByteTab(accountId, length, length + 4, dataToSend);
			Functions.intToByteTab(isGM(accountId), length + 4, length + 5,
					dataToSend);
			Functions.stringToByteTable(account.getUserid(), dataToSend,
					length + 5, length + 29);
			length += 38;
		}
	}

	public static void unban(Socket_data session)
	{
		byte data[] = new byte[4];
		session.func_recv(data);
		int acc = Functions.byteTabToInt(0, 4, data);
		Auth_data account = Login.getDbManagemtType().getUser(acc);
		if (account != null)
		{
			/*
			 * if(account.getBan_until_time().getTimeInMillis() != 0) {
			 * Login.getDbManagemtType()
			 * .login_log(session.getIpStr(),"","0",String.format
			 * (MultilanguageManagement.getWarning_46(), session.getName(), acc,
			 * session.getIpStr())); Calendar bantime = Calendar.getInstance();
			 * bantime.setTimeInMillis(0); account.setBan_until_time(bantime); }
			 * else { Login.getDbManagemtType().login_log(session.getIpStr(),
			 * "", "0",String.format(MultilanguageManagement.getWarning_10() ,
			 * session.getName(), acc, session.getIpStr())); }
			 */
		}
	}

	public static void debugXML()
	{

		XStream xstream = new XStream(new DomDriver());
		System.out.println(xstream.toXML(getAuth_dats()));

	}

	public static void setAuth_dats(FastMap<Integer, Auth_data> aAuth_dats)
	{
		auth_dats = aAuth_dats;
		hashIndexAuth();
	}

	private static void hashIndexAuth()
	{
		Iterator<Auth_data> it = auth_dats.values().iterator();
		while (it.hasNext())
		{
			Auth_data tmp = it.next();
			index_userid_accountid.put(tmp.getUserid(), tmp.getAccount_id());
			// all accont_ids
			account_ids.add(tmp.getAccount_id());
		}
	}

	public static void receiveAllOnlinAccounts(Socket_data session,
			byte packet[])
	{
		if (charServerIndex.get(session.getAccount_id()) != null)
		{
			// Set all chars from this char-server offline first
			for (Integer i : charServerIndex.get(session.getAccount_id()))
			{
				online_db.remove(i);
			}
		}

	}

	public static void requestAccountData(Socket_data session, byte[] packet)
	{
		Auth_data acc;
		int expiration_time = 0;
		String email = "";
		int gmlevel = 0;
		byte answer[] = new byte[51];

		int accountId = Functions.byteTabToInt(2, 6, packet);

		acc = Login.getDbManagemtType().getUser(accountId);
		if (acc == null)
		{
			Functions.showNotice(
					"Char-server '%s': account %d NOT found (ip: %s).\n",
					session.getName(), accountId, session.getIpStr());
		}
		else
		{
			email = acc.getEmail();
			expiration_time = (int) acc.getConnect_until_time();
			gmlevel = acc.getLevel();
		}

		Functions.intToByteTab(0x2717, 0, 2, answer);

		Functions.intToByteTab(accountId, 2, 6, answer);
		Functions.stringToByteTable(email, answer, 6, 46);
		Functions.intToByteTab(expiration_time, 46, 50, answer);
		Functions.intToByteTab(gmlevel, 50, 52, answer);

		session.func_send(answer);
		System.out.printf("[%d", answer[0]);
		for (int i = 1; i < answer.length; i++)
		{
			System.out.printf(",%d", Functions.unsignedByteToInt(answer[i]));
		}
		System.out.println("]" + answer.length);
	}

	public static void requestAccountReg2(Socket_data session, byte[] packet)
	{
		byte[] answer;// = new byte[13 + key1.lenght + 1 + value1.lenght + 1 +
						// key2.lenght + 1 + value2.lenght + 1 + keyN.lenght + 1
						// + valueN.lenght + 1]
		int accountId = Functions.byteTabToInt(2, 6, packet);
		int charId = Functions.byteTabToInt(6, 10, packet);
		Auth_data account = Login.getDbManagemtType().getUser(accountId);
		String regs2StrValue = "";

		if (account != null)
		{
			FastMap<String, String> regs2 = account.getAccount_reg2();
			Set<Map.Entry<String, String>> keys = regs2.entrySet();
			for (Map.Entry<String, String> me : keys)
			{
				regs2StrValue += me.getKey() + '\0' + me.getValue() + '\0';

			}
		}
		answer = new byte[13 + regs2StrValue.length()];

		Functions.intToByteTab(0x2729, 0, 2, answer);
		Functions.intToByteTab(answer.length, 2, 4, answer);
		Functions.intToByteTab(accountId, 4, 8, answer);
		Functions.intToByteTab(charId, 8, 12, answer);
		answer[12] = 1;

		Functions.stringToByteTable(regs2StrValue, answer, 13, answer.length);
		/*
		 * System.out.printf("[%d", answer[0]); for (int i = 1; i <
		 * answer.length; i++) { System.out.printf(", %d", answer[i]); }
		 * System.out.println("]");
		 */
		session.func_send(answer);
		System.out.printf("[%d", answer[0]);
		for (int i = 1; i < answer.length; i++)
		{
			System.out.printf(",%d", Functions.unsignedByteToInt(answer[i]));
		}
		System.out.println("]" + answer.length);
	}
}
