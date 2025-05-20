package org.character.data.map;

import org.javathena.core.data.Socket_data;

import javolution.util.FastMap;
import javolution.util.FastTable;

public class MapServerConnection
{
	private static FastTable<Socket_data> mapServers;
	private static FastMap<Integer,Socket_data> IndexMapServers;
	
	static
	{
		mapServers = new FastTable<Socket_data>();
		IndexMapServers = new FastMap<Integer, Socket_data>();
	} 
	public static void add(Socket_data nServer)
	{
		mapServers.add(nServer);
		IndexMapServers.put(nServer.getAccount_id(), nServer);
	}
}
