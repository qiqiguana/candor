/*
 * Fonctions.java
 *
 * Created on September 20, 2005, 3:56 PM
 *
 * Copyright (c) 2006, Francois Bradette
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
 * Translate from eAthena(c) by Francois Bradette
 */

package org.javathena.core.utiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.StringTokenizer;


/**
 * 
 * @author Francois
 */
public class Functions
{

	// public static final int PASSWORD_ACCEPTER = 1;
	/** Creates a new instance of Fonctions */
	private Functions()
	{

	}
	public static void copyfile(String srFile, String dtFile)
	{
		try
		{
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			InputStream in = new FileInputStream(f1);

			// For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
		catch (FileNotFoundException ex)
		{
			System.out
					.println(ex.getMessage() + " in the specified directory.");
			System.exit(0);
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	// Start DNS Blacklist check [Zido]
	public static int DNSBlacklistcheck(String ip)
	{
		String ipToCheck = ip + ".opm.blitzed.org";
		try
		{
			if (new Socket(ipToCheck, 80).getInetAddress().getHostName() != null)
			{
				showInfo(MultilanguageManagement.getInfo_1(), ip);
				return 3;
			}
			ipToCheck = ip + ".sbl.deltaanime.net";
			if (new Socket(ipToCheck, 80).getInetAddress().getHostName() != null)
			{
				showInfo(MultilanguageManagement.getInfo_1(), ip);
				return 3;
			}
			ipToCheck = ip + ".dnsbl.njabl.org";
			if (new Socket(ipToCheck, 80).getInetAddress().getHostName() != null)
			{
				showInfo(MultilanguageManagement.getInfo_1(), ip);
				return 3;
			}
			ipToCheck = ip + ".sbl-xbl.spamhaus.org";

			if (new Socket(ipToCheck, 80).getInetAddress().getHostName() != null)
			{
				showInfo(MultilanguageManagement.getInfo_1(), ip);
				return 3;
			}
		}
		catch (UnknownHostException ex)
		{
			return -1;
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return -1;
	}

	public static String getMd5String()
	{

		int md5keylen = new java.security.SecureRandom().nextInt() % 4 + 12;
		String tmpMd5 = "";
		for (int i = 0; i < md5keylen; i++)
		{
			tmpMd5 += (char) new java.security.SecureRandom().nextInt() % 255 + 1;
		}
		return tmpMd5;
	}

	public static String getValueFromConfigString(String strConfig, String value)
	{
		value += ":";
		if (strConfig.indexOf(value) != -1)
		{
			int start = strConfig.indexOf(":", strConfig.indexOf(value)) + 1;
			int end = strConfig.indexOf("\n", start);
			return strConfig.substring(start, end).trim();
		}
		return null;
	}

	public static int charSexToInt(char s)
	{
		switch (s)
		{
		case 'S':
		case 's':
			return 2;
		case 'M':
		case 'm':
			return 1;
		case 'F':
		case 'f':
			return 0;
		}
		return -1;
	}

	public static void showWarning(String str)
	{
		System.out.print(Constants.CL_YELLOW
				+ MultilanguageManagement.getWarning() + " " + str
				+ Constants.CL_RESET + Constants.NEWLINE);
	}

	public static void showWarning(String str, Object... param)
	{
		System.out.printf(
				Constants.CL_YELLOW + MultilanguageManagement.getWarning()
						+ " " + str + Constants.CL_RESET + Constants.NEWLINE,
				param);
	}

	public static void showError(String str)
	{
		System.out.print(Constants.CL_RED + MultilanguageManagement.getError()
				+ " " + str + Constants.CL_RESET + Constants.NEWLINE);
	}

	public static void showError(String str, Object... param)
	{
		System.out.printf(Constants.CL_RED + MultilanguageManagement.getError()
				+ " " + str + Constants.CL_RESET + Constants.NEWLINE, param);
	}

	public static void showNotice(String str)
	{
		System.out.print(MultilanguageManagement.getNotice() + " " + str
				+ Constants.NEWLINE);
	}

	public static void showNotice(String str, Object... param)
	{
		System.out.printf(MultilanguageManagement.getNotice() + " " + str
				+ Constants.NEWLINE, param);
	}

	public static void showInfo(String str)
	{
		System.out.printf(MultilanguageManagement.getInfo() + " " + str
				+ Constants.NEWLINE);
	}

	public static void showInfo(String str, Object... param)
	{
		System.out.printf(MultilanguageManagement.getInfo() + " " + str
				+ Constants.NEWLINE, param);
	}

	public static void showStatus(String str)
	{
		System.out.print(Constants.CL_GREEN
				+ MultilanguageManagement.getStatus() + " " + str
				+ Constants.CL_RESET + Constants.NEWLINE);
	}

	public static void showStatus(String str, Object... param)
	{
		System.out.printf(
				Constants.CL_GREEN + MultilanguageManagement.getStatus() + " "
						+ str + Constants.CL_RESET + Constants.NEWLINE, param);
	}

	public static void showSQL(String str)
	{
		System.out.printf(Constants.CL_MAGENTA + "[SQL]: " + str
				+ Constants.CL_RESET + Constants.NEWLINE);
	}

	public static void showSQL(String str, Object... param)
	{
		System.out.printf(Constants.CL_MAGENTA + "[SQL]: " + str
				+ Constants.CL_RESET + Constants.NEWLINE, param);
	}

	public static void showDebug(String str)
	{
		System.out.printf(Constants.CL_CYAN + "[Debug]: " + str
				+ Constants.CL_RESET + Constants.NEWLINE);
	}

	public static void showDebug(String str, Object... param)
	{
		System.out.printf(Constants.CL_CYAN + "[Debug]: " + str
				+ Constants.CL_RESET + Constants.NEWLINE, param);
	}

	public static void showFatalError(String str)
	{
		System.out.printf(Constants.CL_CYAN + "[Fatal Error]: " + str
				+ Constants.CL_RESET + Constants.NEWLINE);
	}

	public static void showFatalError(String str, Object... param)
	{
		System.out.printf(Constants.CL_CYAN + "[Fatal Error]: " + str
				+ Constants.CL_RESET + Constants.NEWLINE, param);

		System.exit(0);
	}

	public static InetAddress stringToInet(String str)
			throws UnknownHostException
	{
		return InetAddress.getLocalHost();
	}

	public static void intToByteTab(int toParse, int length, byte[] byteTab)
	{
		intToByteTab(toParse, 0, length, byteTab);
	}

	public static void intToByteTab(int aInt, int startInd, int endInd,
			byte[] aByteTab)
	{
		for (int i = startInd; i < endInd && aInt != 0; i++)
		{
			aByteTab[i] = (byte) (aInt % 256);
			aInt /= 256;
		}
	}

	public static void intToIntTab(int aInt, int startInd, int endInd,
			int[] aIntTab)
	{
		for (int i = startInd; i < endInd && aInt != 0; i++)
		{
			aIntTab[i] = (aInt % 256);
			aInt /= 256;
		}
	}

	public static double byteTabToDouble(int start, int end, byte[] bytesTab)
	{
		double reponse = 0;
		for (int i = start, j = 1; i < end; i++, j *= 0x100)
		{
			reponse += unsignedByteToInt(bytesTab[i]) * j;
		}
		return reponse;
	}

	public static int byteTabToInt(int start, int end, byte[] bytesTab)
	{
		int reponse = 0;
		for (int i = start, j = 1; i < end; i++, j *= 0x100)
		{
			reponse += unsignedByteToInt(bytesTab[i]) * j;
		}
		return reponse;
	}

	public static String readConf(File ficher) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(ficher));

		String contenu = "";
		String lu = null;
		lu = in.readLine();
		while (lu != null)
		{
			if (lu.indexOf("//") != -1)
				lu = lu.substring(0, lu.indexOf("//"));
			contenu += lu + "\n";
			lu = in.readLine();
		}

		in.close();
		return contenu;
	}

	public static String calendarToString(Calendar tv)
	{
		if (tv == null)
			return "0";
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);
		nf.setMaximumIntegerDigits(2);
		return tv.get(Calendar.YEAR) + "-" + nf.format(tv.get(Calendar.MONTH))
				+ "-" + nf.format(tv.get(Calendar.DAY_OF_MONTH)) + " "
				+ nf.format(tv.get(Calendar.HOUR_OF_DAY)) + ":"
				+ nf.format(tv.get(Calendar.MINUTE)) + ":"
				+ nf.format(tv.get(Calendar.SECOND)) + ":"
				+ nf.format(tv.get(Calendar.MILLISECOND));
	}

	/*
	 * public static String bytesToString() { return ""; }
	 */
	public static void stringToByteTable(String str, byte bTable[], int start,
			int end)
	{
		for (int i = start, j = 0; i < end && j < str.length(); i++, j++)
			bTable[i] = (byte) str.charAt(j);

	}

	public static int unsignedByteToInt(byte bti)
	{
		if (bti < 0)
			return (bti + 256);
		return bti;
	}

	public static String unsignedBytesToString(byte[] bTable, int startIndex,
			int endIndex)
	{
		String result = "";
		for (int i = startIndex; i <= endIndex && bTable[i] != 0; i++)
		{
			result += (char) parseByteToInt(bTable[i]);
		}
		return result;
	}
    public static int parseByteToInt(byte value)
    {
        int val = value;
        if(value < 0)
        {
            val = (value + 256);
        }
        return val;
    }
    
    public static byte parseIntToByte(int value) throws Exception
    {
        
        byte val = 0;
        if(value > 127)
            value = (value - 256);
        if(value > Byte.MIN_VALUE && value < Byte.MAX_VALUE)
            val =(byte)value;
        else
            throw new Exception("Value doit etre inferieur a 256 et superieur a -128");
        return val;
    }
	public static void doubleToByteTab(long toParse, int start, int end,
			byte[] bytesTab)
	{
		for (int i = start, j = 0; i < end; i++, j++)
		{
			bytesTab[i] = ((byte) (toParse % 0x100));
			toParse -= (toParse % 0x100);
			toParse /= 0x100;
		}
	}

	public static void longToIntTab(long toParse, int start, int end,
			int[] bytesTab)
	{
		for (int i = start, j = 0; i < end; i++, j++)
		{
			bytesTab[i] = ((int) (toParse % 0x100));
			toParse -= (toParse % 0x100);
			toParse /= 0x100;
		}
	}

	public static boolean e_mail_check(String email)
	{
		if (email.length() < 3 || email.length() > 39)
			return false;

		if (email.indexOf("@") == -1 || email.indexOf(".") == -1)
			return false;

		if (email.indexOf("@.") != -1 || email.indexOf("..") != -1
				|| email.indexOf("@") == 0 || email.indexOf(" ") != -1
				|| email.indexOf(";") != -1)
			return false;

		// all correct
		return true;
	}

	public static void showMessage(String string)
	{
		System.out.print(string);
	}

	public static void showMessage(String string, Object... param)
	{
		System.out.printf(string, param);
	}

	public static String encryptePassword(String md5key,
			String passwordAccount, String encryptedPassword, int passwdenc)
			throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance(Constants.ALGORITHM);
		switch (passwdenc)
		{
		// case 0:
		case 1:
			md.update((md5key + encryptedPassword).getBytes("8859_1"));
			break;
		case 2:
			md.update((encryptedPassword + md5key).getBytes("8859_1"));
			break;
		default:
			return null;
		}
		return new String(md.digest());
	}

	public static boolean checkEncryptedPassword(String md5key,
			String passwordAccount, String encryptedPassword, int passwdenc)
			throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md = MessageDigest.getInstance(Constants.ALGORITHM);
		switch (passwdenc)
		{
		// case 0:
		case 1:
			md.update((md5key + encryptedPassword).getBytes("8859_1"));
			return md.digest().equals(passwordAccount.getBytes("8859_1"));
		case 2:
			md.update((encryptedPassword + md5key).getBytes("8859_1"));
			return md.digest().equals(passwordAccount.getBytes("8859_1"));
		default:
			return false;
		}
	}

	public static byte[] ipStringToByteTab(String ipStr)
	{
		StringTokenizer ipStrT = new StringTokenizer(ipStr, ".");

		byte ip[] = new byte[4];

		for (int i = 0; i < ip.length; i++)
		{
			ip[i] = Byte.parseByte(ipStrT.nextElement().toString());
		}
		return ip;
	}

	public static byte[] ipStringToByteTab(String ipStr, byte gTab[], int start)
	{
		StringTokenizer ipStrT = new StringTokenizer(ipStr, ".");

		for (int i = start; i < (start + 4); i++)
		{
			gTab[i] = Byte.parseByte(ipStrT.nextElement().toString());
		}
		return gTab;
	}

	public static byte[] subByteTab(byte tab[], int start, int end)
	{
		byte tabTo[] = new byte[end - start];
		for (int i = start; i < end; i++)
		{
			tabTo[i] = tab[i];
		}
		return tabTo;
	}

	public static PrintWriter open_log(String filePath)
	{
		PrintWriter out = null;
		try
		{
			File log_fp = new File(filePath);

			if (!log_fp.exists())
			{
				if (!log_fp.getParentFile().exists())
				{
					log_fp.getParentFile().mkdir();
				}
				log_fp.createNewFile();
			}
			// autoflush append
			out = new PrintWriter(new FileWriter(log_fp, true), true);
			out.println();

		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return out;
	}
	
	public static String byteTabToString(int start, int end, byte[] buf)
	{
		return byteTabToString(start, end, buf, false);
	}
	public static String byteTabToString(int start, int end, byte[] buf,boolean stopToZero)
	{
		String str = "";
		for (int i = start; i < end &&(buf[i] != 0 || !stopToZero); i++)
		{
			if (buf[i] != 0)
				str += (char) buf[i];
		}
		return str;
	}

	public static short byteTabToShort(int start, int end, byte[] tab)
	{
		short reponse = 0;
		for (int i = start, j = 1; i < end; i++, j *= 0x100)
		{
			reponse += unsignedByteToInt(tab[i]) * j;
		}
		return reponse;
	}

	public static Calendar stringToCalendar(String timestamp)
	{
		Calendar resultDate = Calendar.getInstance();
		String[] dateTime = timestamp.split(" ");
		String[] date = dateTime[0].split("-");
		String[] time = dateTime[1].split(":");
		resultDate.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
				Integer.parseInt(date[2]), Integer.parseInt(time[0]),
				Integer.parseInt(time[1]), Integer.parseInt(time[2]));
		return resultDate;
	}

	public static void byteTableToByteTab(byte[] param, int start, int end,
			byte[] answer)
	{
		for (int j = 0, i = start; i < end; i++, j++)
		{
			answer[i] = param[j];
		}
	}

	public static char byteSexToChar(byte sex) 
	{
		return (sex == 0?'F':(sex == 1?'M':'S'));
	}
	/**
	 * ------------------------------------------------- 
	 * // Return numerical value of a switch configuration 
	 * // on/off, english, fran?ais, deutsch, espa?ol 
	 * //-----------------------------------------------
	 **/
	public static int config_switch(String str)
	{
		if (str.equals("on") || str.equals("yes") || str.equals("oui")
				|| str.equals("ja") || str.equals("si") || str.equals("1"))
			return 1;
		if (str.equals("off") || str.equals("no") || str.equals("non")
				|| str.equals("nein") || str.equals("0"))
			return 0;

		return -1;
	}

}
