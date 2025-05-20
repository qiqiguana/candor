/**
 * Socket_data.java
 *
 * Created on September 22, 2005, 8:46 PM
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

package org.javathena.core.data;

import org.javathena.core.utiles.Functions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author darksid_1@htomail.com
 * @param <SessionType>
 */
public class Socket_data extends TimerTask
{
	private int account_id;
	private int maintenance;

	private Calendar rdata_tick;
	private IParse func_parse;

	private String name;
	private Excecutable func_console;
	private Excecutable func_recv;
	private Excecutable func_send;
	private String md5key;

	private Socket client_addr;
	private SessionType type;
	private int users;
	private InputStream in;
	private OutputStream out;
	private int listenPort;
	private int eof;
	private static Timer t = new Timer("Socket");
	private int new_;


	public Socket_data(Socket client_addr, IParse parsefunction)
	{

		this.func_parse = parsefunction;
		this.client_addr = client_addr;
		// UserManagement.addSession(this);
		// System.out.println(client_addr.isClosed());
		try
		{
			if (client_addr != null)
			{
				in = client_addr.getInputStream();
				out = client_addr.getOutputStream();
				
				t.schedule(this, 1, 1);
				t.purge();
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	
	/** Creates a new instance of Socket_data */
	public Socket_data(Socket client_addr)
	{
		this(client_addr, null);
	}

	public void close()
	{

		try
		{
			this.cancel();
			out.flush();
			in.close();
			out.close();
			if (!client_addr.isClosed())
				client_addr.close();
			cancel();
			t.purge();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public synchronized void run()
	{
		try
		{
			byte packet[];
			if (client_addr.isClosed())
			{
				this.close();
			}
			out.flush();
			if( in.available() != 0)
			{
				packet = new byte[in.available()];
				in.read(packet);
				func_parse.parse(this, packet);
			}
			

		}
		catch (SocketException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public int func_recv()
	{
		try
		{

			if (!client_addr.isClosed())
				return in.read();
			else
			{
				System.out.println("Socket closed");
			}
			return -1;
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return -1;
		}
	}

	public int func_recv(byte tab[])
	{
		try
		{
			if (!client_addr.isClosed())
				return in.read(tab);
			else
			{
				System.out.println("Socket closed");
			}
			return -1;
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return -1;
		}
	}

	public void func_send(byte byte_tab[])
	{

		try
		{
			if (!client_addr.isClosed())
			{
				out.write(byte_tab);
				out.flush();
			}
		}
		catch (IOException ex)
		{
			close();
			Functions.showWarning(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void func_send(int[] integers)
	{
		try
		{
			if (client_addr.isClosed())
			{
				for (int i = 0; i < integers.length; i++)
					out.write(integers[i]);
				out.flush();
			}

		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public void func_send(int integer)
	{
		try
		{

			if (!client_addr.isClosed())
			{
				out.write(integer);
				out.flush();
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public int func_parse(Socket_data session, byte packet[])
			throws IOException
	{
		return func_parse.parse(session, packet);
	}

	public Object func_console(Object... args)
	{
		return func_console.executeO(args);
	}

	public Calendar getRdata_tick()
	{
		return rdata_tick;
	}

	public void setRdata_tick(Calendar rdata_tick)
	{
		this.rdata_tick = rdata_tick;
	}

	public Socket getClient_addr()
	{
		return client_addr;
	}

	public void setClient_addr(Socket client_addr)
	{
		if (client_addr != null)
		{
			this.client_addr = client_addr;
			try
			{
				in = client_addr.getInputStream();
				out = client_addr.getOutputStream();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public Excecutable getFunc_recv()
	{
		return func_recv;
	}

	public void setFunc_recv(Excecutable func_recv)
	{
		this.func_recv = func_recv;
	}

	public Excecutable getFunc_send()
	{
		return func_send;
	}

	public void setFunc_send(Excecutable func_send)
	{
		this.func_send = func_send;
	}

	public IParse getFunc_parse()
	{
		return func_parse;
	}

	public void setFunc_parse(IParse func_parse)
	{
		this.func_parse = func_parse;
	}

	public Excecutable getFunc_console()
	{
		return func_console;
	}

	public void setFunc_console(Excecutable func_console)
	{
		this.func_console = func_console;
	}

	public SessionType getType()
	{
		return type;
	}

	public void setType(SessionType type)
	{
		this.type = type;
	}

	public int getUsers()
	{
		return users;
	}

	public void setUsers(int users)
	{
		this.users = users;
	}

	public int getMaintenance()
	{
		return maintenance;
	}

	public int getNew_()
	{
		return new_;
	}

	public void setNew_(int new_)
	{
		this.new_ = new_;
	}

	public void setMaintenance(int maintenance)
	{
		this.maintenance = maintenance;
	}

	/*
	 * public String getName() { return name; }
	 */
	/*
	 * public void setName(String name) { if (name.equals("")) this.name = name;
	 * else this.name = name; }
	 */

	public int getListenPort()
	{
		return listenPort;
	}

	public void setListenPort(int listenPort)
	{
		this.listenPort = listenPort;
	}

	public Integer getAccount_id()
	{
		return account_id;
	}

	public void setAccount_id(int account_id)
	{
		this.account_id = account_id;
	}

	public String getMd5key()
	{
		return md5key;
	}

	public void setMd5key(String md5key)
	{
		this.md5key = md5key;
	}

	public String getIpStr()
	{
		return client_addr.getLocalAddress().getHostAddress();
	}

	public byte[] getIpTab()
	{
		return client_addr.getLocalAddress().getAddress();
	}

	public int getEof()
	{
		return eof;
	}

	public void setEof(int eof)
	{
		this.eof = eof;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
