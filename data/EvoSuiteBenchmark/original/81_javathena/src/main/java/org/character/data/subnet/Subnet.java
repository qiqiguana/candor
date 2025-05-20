package org.character.data.subnet;

import java.io.IOException;

import javolution.util.FastTable;

import org.javathena.core.data.PersistenteData;

public class Subnet
{
	private String subNet;
	private String mapIP;
	private String charIP;
	private PersistenteData<FastTable<Subnet>> persistenceMethod;
	private static FastTable<Subnet> subnets;
	static
	{
		subnets = new FastTable<Subnet>();
	}

	public Subnet(String subNet, String charIP, String mapIP)
	{
		this.subNet = subNet;
		this.mapIP = mapIP;
		this.charIP = charIP;
	}

	public void save() throws IOException
	{
		persistenceMethod.save(subnets);
	}

	public FastTable<Subnet> load() throws IOException
	{
		return persistenceMethod.load();
	}

	public static Subnet get(int index)
	{
		return subnets.get(index);
	}

	public PersistenteData<FastTable<Subnet>> getPersistenceMethod()
	{
		return persistenceMethod;
	}

	public void setPersistenceMethod(
			PersistenteData<FastTable<Subnet>> persistenceMethod)
	{
		this.persistenceMethod = persistenceMethod;
	}

	public String getSubNet()
	{
		return subNet;
	}

	public void setSubNet(String subNet)
	{

		this.subNet = subNet;
	}

	public String getMapIP()
	{
		return mapIP;
	}

	public void setMapIP(String mapIP)
	{
		this.mapIP = mapIP;
	}

	public String getCharIP()
	{
		return charIP;
	}

	public void setCharIP(String charIP)
	{
		this.charIP = charIP;
	}

	public String toString()
	{
		return this.subNet + ":" + this.charIP + ":" + this.mapIP;
	}

	public static int hashIP(byte addr[])
	{
		int address = addr[3] & 0xFF;
		address |= ((addr[2] << 8) & 0xFF00);
		address |= ((addr[1] << 16) & 0xFF0000);
		address |= ((addr[0] << 24) & 0xFF000000);
		return address;
	}
}
