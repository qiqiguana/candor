/*
 * Parse_login.java
 *
 * Created on October 3, 2005, 3:53 PM
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

package org.javathena.login.parse;

import org.javathena.core.data.IParse;
import org.javathena.core.data.Socket_data;
import org.javathena.core.utiles.Functions;
import org.javathena.login.Login;
import org.javathena.login.UserManagement;
import org.javathena.utiles.ConfigurationManagement;

/**
 * 
 * @author Francois
 */
public class FromClient implements IParse
{
	public static final int PACKAGE_TO_SMALL = -257;
	public static final int ALIVE_PACKET = 0x200;
	public static final int ALIVE_PACKET_ENCRYPTED = 0x0204;

	public static final int NEW_CONNECTION_OF_CLIENT = 0x0277;

	public static final int CODING_KEY = 0x01db;
	public static final int CODING_KEY_ADMINISTRATION = 0x791a;
	public static final int CONNECTION_OF_CHAR_SERVER = 0x2710;
	public static final int CONNECTION_OF_CLIENT = 0x0064;
	
	public static final int VERSION = 0x7530;
	public static final int END_OF_CONNECTION = 0x7532;
	public static final int CONNECTION_OF_LOGIN_ADMINISTRATION = 0x7918;

	/** Creates a new instance of Parse_login */

	public FromClient()
	{

	}

	public int parse(Socket_data session, byte packet[])
	{
		String ip = session.getIpStr();
		int commande = packet[0] | (packet[1] * 256);
		boolean encrypted = false;
		System.out.printf("ToLogin %x\n", commande);

		if (ConfigurationManagement.getLoginAthenaConf().isIpban())
		{
			if (Login.getDbManagemtType().isIpBan(session.getIpStr()))
			{
				Functions.showWarning("packet from banned ip : %s\n", ip);
				session.setEof(1);
				return -1;
			}
			else
			{
				Functions.showInfo("packet from ip (ban check ok) : %s", ip);
			}

		}

		switch (commande)
		{
		case PACKAGE_TO_SMALL:
			Functions.showInfo("end");
			session.close();
			return 0;
			// session.setEof(1);
			// break;
		case ALIVE_PACKET:

			break;
		case ALIVE_PACKET_ENCRYPTED:

			break;
		// request client login (md5-hashed password)
		case 0x01dd: // S 01dd <version>.L <username>.24B <password hash>.16B
						// <clienttype>.B
			if (packet.length < 47)
				return 0;
			UserManagement.connectionOfClient(session, packet, true);
			break;
		case 0x01fa: // S 01fa <version>.L <username>.24B <password hash>.16B
						// <clienttype>.B <?>.B(index of the connection in the
						// clientinfo file (+10 if the command-line contains
						// "pc"))
			if (packet.length < 48)
				return 0;
			UserManagement.connectionOfClient(session, packet, true);
			break;
		case 0x027c: // S 027c <version>.L <username>.24B <password hash>.16B
						// <clienttype>.B <?>.13B(junk)
			if (packet.length < 60)
				return 0;

			UserManagement.connectionOfClient(session, packet, true);
			break;
			// request client login (raw password)
			 // S 0064 <version>.L <username>.24B <password>.24B<clienttype>.B
		case CONNECTION_OF_CLIENT:
			if (packet.length < 55)
				return 0;
			encrypted = true;
			UserManagement.connectionOfClient(session, packet, false);
			break;
			// S 0277 <version>.L <username>.24B <password>.24B<clienttype>.B <ip address>.16B <adapter address>.13B
		case 0x0277: 
			if (packet.length < 84)
				return 0;
			UserManagement.connectionOfClient(session, packet, false);
			break;
		case 0x02b0:
			// S 02b0 <version>.L <username>.24B <password>.24B <clienttype>.B
			// <ip address>.16B <adapter address>.13B <g_isGravityID>.B
			if (packet.length < 85)
				return 0;
			UserManagement.connectionOfClient(session, packet, false);
			break;
		case CODING_KEY:
			Login.codingKey(session);
			break;
		case CODING_KEY_ADMINISTRATION:
			Login.codingKeyAdministration(session);
			break;
		case CONNECTION_OF_CHAR_SERVER:
			UserManagement.connectionOfCharServer(session, packet);
			break;
		case VERSION:
			Login.version(session);
			break;
		case END_OF_CONNECTION:
			Login.getDbManagemtType().login_log(ip, "", "",
					String.format("End of connection (ip: " + ip + ")"));
			Functions.showWarning("End of connection (ip: %s)", ip);
			session.setEof(1);
			break;
		case CONNECTION_OF_LOGIN_ADMINISTRATION:
			UserManagement.administationLogin(session);
			Functions.showWarning("%d parse_login : niy", commande);
			break;
		default:
			return 0;
		}

		return 0;
	}

}