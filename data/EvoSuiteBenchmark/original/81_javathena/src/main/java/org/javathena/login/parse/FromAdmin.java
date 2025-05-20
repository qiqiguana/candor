/*
 * ParseConsole.java
 *
 * Created on March 24, 2006, 6:06 PM
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

package org.javathena.login.parse;

import org.javathena.core.data.IParse;
import org.javathena.core.data.Socket_data;
import org.javathena.core.utiles.Functions;
import org.javathena.login.Login;
import org.javathena.login.UserManagement;

/**
 * 
 * @author darksid_1@hotmail.com
 */
public class FromAdmin implements IParse
{

	/** Creates a new instance of ParseConsole */
	public FromAdmin()
	{
	}

	public int parse(Socket_data session, byte packet[])
	{
		if (session.getEof() == 1)
		{
			Functions.showNotice(
					"Remote administration has disconnected (session #%d).\n",
					session.getAccount_id());
			return 0;
		}
		int commande = packet[0] + packet[1] * 256;
		commande += session.func_recv();

		while (commande != -1)
		{
			commande += session.func_recv() * 256;
			switch (commande)
			{
			case -1:
				return 0;
			case 0x7530:
				Login.getDbManagemtType().login_log(
								"",
								"",
								"0",
								String.format(
										"'ladmin': Sending of the server version (ip: %s)",
										session.getIpStr()));
				Login.version(session);
				break;
			case 0x7532:
				session.setEof(1);
				break;
			case 0x7920:
				UserManagement.sendUserList(session);
				break;
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
				System.out.println("L'administration a distance n'est pas implementer");
			}
			commande = session.func_recv();
		}
		return 0;
	}

	public int parse(byte[] data, Socket_data session, Login hote)
	{
		return 0;
	}

}
