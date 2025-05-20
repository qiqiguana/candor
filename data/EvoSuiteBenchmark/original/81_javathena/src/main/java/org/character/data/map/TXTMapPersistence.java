package org.character.data.map;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;


import org.javathena.core.data.IndexedFastMap;
import org.javathena.core.data.PersistenteData;

public class TXTMapPersistence implements PersistenteData<IndexedFastMap<Integer, String>>
{

	private final static String DEFAULT_FILE = "db/map_index.txt";

	private String fileDB = DEFAULT_FILE;

	public String getFileDB()
	{
		return fileDB;
	}

	public void setFileDB(String fileDB)
	{
		this.fileDB = fileDB;
	}

	@Override
	public void save(IndexedFastMap<Integer, String> data) throws IOException
	{
		String content = "";
		PrintWriter out = null;
		
		for(Entry<Integer,String> entry:data.getData().entrySet())
		{
			content += entry.getValue() + " " + entry.getKey();
		}
		out = new PrintWriter(new BufferedWriter(new FileWriter(fileDB)));
		out.write(content);
		out.close();
	}

	@Override
	public IndexedFastMap<Integer, String> load() throws IOException
	{
		IndexedFastMap<Integer, String> ifm = new IndexedFastMap<Integer, String>();
		BufferedReader in = new BufferedReader(new FileReader(new File(fileDB)));
		
		String line = null;
		String splitedLine[] = null;
		int index = -1;
		while ((line = in.readLine()) != null)
		{
			if (!line.startsWith("//") && line.length() != 0)
			{
				if (line.contains("//"))
				{
					line = line.substring(0, line.indexOf("//"));
				}
				splitedLine = line.split(" ");
				
				if(splitedLine.length == 2)
				{
					index = Integer.parseInt(splitedLine[1]);
				}
				else if(splitedLine.length == 1)
				{
					index++;
				}
				ifm.put(index, splitedLine[0]);
			}
		}
		in.close();
		return ifm;
	}

}
