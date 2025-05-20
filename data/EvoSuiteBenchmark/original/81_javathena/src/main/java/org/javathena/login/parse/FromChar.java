/*
 * Parse_fromchar.java
 *
 * Created on October 3, 2005, 4:16 PM
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

package org.javathena.login.parse;

import java.net.*;

import org.javathena.core.data.IParse;
import org.javathena.core.data.Socket_data;
import org.javathena.core.utiles.Functions;
import org.javathena.login.Login;
import org.javathena.login.UserManagement;

/**
 * 
 * @author Francois
 */
public class FromChar implements IParse
{
	/*
	 * long moyenne; int nb;
	 */
	public static final int END_OF_CONNECTION = -257;
	// public static final int RELOAD_GM_ACCOUNT = 0x2709;
	public static final int ACCOUNT_AUTHENTIFY = 0x2712;// 0x2710;
	public static final int NUMBER_OF_USER = 0x2714;
	public static final int EMAIL_CREATION = 0x2715;
	public static final int REQUEST_ACCOUNT_DATA = 0x2716;
	// public static final int EMAIL_LIMITED_TIME = 0x2716;

	public static final int PING_REQUEST = 0x2719;
	public static final int CHANGE_AN_EMAIL = 0x2722;
	public static final int STATUS_CHANGE = 0x2724;
	public static final int BAN_REQUEST = 0x2725;
	public static final int CHANGE_SEX = 0x2727;
	public static final int ACCOUNT_REG2 = 0x2728;
	public static final int UNBAN = 0x272a;
	public static final int ACCOUNT_TO_ONLINE = 0x272b;
	public static final int ACCOUNT_TO_OFFLINE = 0x272c;
	public static final int ONLINE_ACCOUNT_LIST = 0x272d;
	// Request account_reg2
	public static final int REQUEST_ACCOUNT_REG2 = 0x272e;
	public static final int WAN_UPDATE = 0x2736;
	public static final int REQUEST_ALL_OFFLINE = 0x2737;
	// public static final int CHANGE_SEX_CHARIF = 0x3000;

	/**
	 *  Packet parsing for char-servers
	 */
	/** Creates a new instance of Parse_fromchar */
	public FromChar()
	{

	}

	public int parse( Socket_data session, byte packet[])
	{

		int commande = packet[0] | (packet[1] * 256);

		System.out.printf("FromChar %x\n", commande);
		
		switch (commande)
		{
		case -257:
			Functions.showWarning("Disconnection of the char server: %s",
					session.getName());
			session.setEof(1);
			break;
			// request from char-server to authenticate an account
		case ACCOUNT_AUTHENTIFY:
			UserManagement.charServerToAuthentify(session, packet);
			break;
		case NUMBER_OF_USER:
			UserManagement.numberOfUser(session, packet);
			break;
		case EMAIL_CREATION:
			// we receive a e-mail creation of an account with a default e-mail
			// (no answer)
			UserManagement.emailCreation(session);
			break;
		case REQUEST_ACCOUNT_DATA:
			UserManagement.requestAccountData(session, packet);
			break;
		case PING_REQUEST:
			session.func_send(new byte[]{0x18,0x27});
			break;
		// Map server send information to change an email of an account via
		// char-server
		case CHANGE_AN_EMAIL: // 0x2722 <account_id>.L <actual_e-mail>.40B
								// <new_e-mail>.40B
			UserManagement.toChangeAnEmail(session);
			break;
		// Receiving of map-server via char-server a status change resquest (by
		// Yor)
		case STATUS_CHANGE:
			UserManagement.statusChange(session, packet);
			break;
		case BAN_REQUEST: // Receiving of map-server via char-server a ban
							// resquest (by Yor)
			UserManagement.banResquest(session);
			break;
		case CHANGE_SEX: // Change of sex (sex is reversed)
			UserManagement.changeSex(session);
			break;
		case ACCOUNT_REG2: // We receive account_reg2 from a char-server, and we
							// send them to other

			System.out.println("ACCOUNT_REG2");
			UserManagement.receiveAccountReg2(session, packet);
			break;
		case UNBAN: // Receiving of map-server via char-server a unban resquest
					// (by Yor)
			UserManagement.unban(session);
			break;
		case ACCOUNT_TO_ONLINE: // Set account_id to online
			UserManagement.account_idToOnline(session, packet);
			break;
		case ONLINE_ACCOUNT_LIST:
			UserManagement.receiveAllOnlinAccounts(session, packet);
			break;
		case ACCOUNT_TO_OFFLINE: // Set account_id to offline [Wizputer]
			UserManagement.account_idToOffline(session, packet);
			break;
		case REQUEST_ACCOUNT_REG2:
			UserManagement.requestAccountReg2(session, packet);
			break;
		case WAN_UPDATE:

			Functions.showWarning("Not implemented yet");
			break;
		case REQUEST_ALL_OFFLINE:

			Functions.showWarning("Not implemented yet");
			break;
		default:
			if (commande < 0)
				System.exit(0);
			Login.logUnknownPackets(session);

		}
		return 0;
	}

}
