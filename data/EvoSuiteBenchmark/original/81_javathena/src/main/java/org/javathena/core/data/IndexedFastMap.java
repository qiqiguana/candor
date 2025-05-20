package org.javathena.core.data;

import javolution.util.FastMap;

public class IndexedFastMap<T, F>
{
	private FastMap<T, F> data;
	private FastMap<F, T> indexedData;

	public IndexedFastMap()
	{
		data = new FastMap<T, F>();
		indexedData = new FastMap<F, T>();
	}
	
	public void put(T key, F value)
	{
		data.put(key, value);
		indexedData.put(value, key);
	}
	
	public FastMap<F, T> getIndexedData()
	{
		return indexedData;
	}

	public void setIndexedData(FastMap<F, T> indexedData)
	{
		this.indexedData = indexedData;
	}

	public FastMap<T, F> getData()
	{
		return data;
	}

	public void setData(FastMap<T, F> data)
	{
		this.data = data;
	}

	
}
