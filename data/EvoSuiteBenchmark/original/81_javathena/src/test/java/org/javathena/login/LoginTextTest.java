package org.javathena.login;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.javathena.core.utiles.Functions;
import org.javathena.login.parse.FromClient;
import org.javathena.utiles.ConfigurationManagement;

import junit.framework.TestCase;

public class LoginTextTest extends TestCase
{
	private Login lServer;

	private final String TEST_DB_PATH_FILE = "src/test/test.txt";
	private final String TEST_BACKUP_DB_PATH_FILE = "src/test/test_bk.txt";

	public void setUp()
	{

		System.gc();
		File db = new File(TEST_DB_PATH_FILE);
		if (db.exists())
		{
			db.delete();
		}
		Functions.copyfile(TEST_BACKUP_DB_PATH_FILE, TEST_DB_PATH_FILE);
		System.gc();
		new Thread(new TestLoginServerRun()).start();
	}

	

	public void tearDown()
	{
		lServer.do_final();
	}

	class TestLoginServerRun implements Runnable
	{
		public void run()
		{
			lServer = new Login();

			ConfigurationManagement.getLoginAthenaConf().setAccount_filename(
					TEST_DB_PATH_FILE);
			lServer.do_init();
		}
	}

	/**
	 * Send a raw login request That suppose that you don't have start char
	 * server and you have a valid configuration file
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 * 
	 * */
	public void testRawPasswordLoginNoCharServer() throws UnknownHostException,
			IOException
	{
		byte[] message = new byte[55];
		byte[] response = null;
		int version = 25;
		Functions.intToByteTab(FromClient.CONNECTION_OF_CLIENT, 0, 2, message);
		Functions.intToByteTab(version, 2, 6, message);
		Functions.stringToByteTable("test", message, 6, 30);
		Functions.stringToByteTable("test", message, 30, 54);
		message[54] = 14;

		Socket connexion = new Socket(ConfigurationManagement
				.getLoginAthenaConf().getBind_ip_str(), ConfigurationManagement
				.getLoginAthenaConf().getLogin_port());
		connexion.getOutputStream().write(message);
		while (connexion.getInputStream().available() == 0);
		response = new byte[connexion.getInputStream().available()];
		connexion.getInputStream().read(response);
		assertEquals(3, response.length);
		assertEquals(0, response[1]);
		assertEquals(0x81, Functions.unsignedByteToInt(response[0]));
		assertEquals(1, response[2]);

		connexion.close();
	}

	/**
	 * Send a raw login request That suppose that you don't have a character
	 * with the username test ant the password 4534354 server and you have a
	 * valid configuration file
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 * 
	 * */
	public void testRawPasswordLoginInvalidePassword()
			throws UnknownHostException, IOException
	{
		byte[] message = new byte[55];
		byte[] response = null;
		int version = 25;
		Functions.intToByteTab(FromClient.CONNECTION_OF_CLIENT, 0, 2, message);
		Functions.intToByteTab(version, 2, 6, message);
		Functions.stringToByteTable("test", message, 6, 30);
		Functions.stringToByteTable("4534354", message, 30, 54);
		message[54] = 14;

		Socket connexion = new Socket(ConfigurationManagement
				.getLoginAthenaConf().getBind_ip_str(), ConfigurationManagement
				.getLoginAthenaConf().getLogin_port());
		connexion.getOutputStream().write(message);
		int lenght = 0;
		while ((lenght = connexion.getInputStream().available()) == 0);
		response = new byte[lenght];
		connexion.getInputStream().read(response);
		assertEquals(23, response.length);
		assertEquals(0, response[1]);
		assertEquals(0x6a, response[0]);
		assertEquals(1, response[2]);
		connexion.close();
	}

}
