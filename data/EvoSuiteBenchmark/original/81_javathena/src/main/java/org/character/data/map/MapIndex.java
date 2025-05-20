package org.character.data.map;

import java.io.IOException;
import java.util.Map.Entry;

import javolution.util.FastMap;

import org.javathena.core.data.IndexedFastMap;
import org.javathena.core.data.PersistenteData;

public class MapIndex
{

	private static PersistenteData<IndexedFastMap<Integer, String>> persistenceMethod;

	private static IndexedFastMap<Integer, String> maps;

	public MapIndex()
	{
		persistenceMethod = new XMLMapPersistence();
	}

	static
	{
		maps = new IndexedFastMap<Integer, String>();
	}

	public static Integer get(String key)
	{
		return maps.getIndexedData().get(key);
	}

	public static String get(Integer key)
	{
		return maps.getData().get(key);
	}

	public void save()
	{
		try
		{
			persistenceMethod.save(maps);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void load()
	{
		try
		{
			maps = persistenceMethod.load();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setPersistenceMethod(
			PersistenteData<IndexedFastMap<Integer, String>> aPersistenceMethod)
	{
		persistenceMethod = aPersistenceMethod;
	}

	public static FastMap<String, Integer> makeIndex(
			FastMap<Integer, String> data)
	{
		FastMap<String, Integer> index = new FastMap<String, Integer>(
				data.size());
		for (Entry<Integer, String> e : data.entrySet())
		{
			index.put(e.getValue(), e.getKey());
		}
		return index;
	}

}
