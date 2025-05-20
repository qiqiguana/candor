package org.character.data.subnet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javolution.util.FastTable;

import org.javathena.core.data.PersistenteData;
import org.javathena.core.utiles.Functions;

public class TXTSubnet implements PersistenteData<FastTable<Subnet>>
{
	private final static String DEFAULT_FILE = "conf/subnet_athena.conf";

	private String fileConfig = DEFAULT_FILE;

	public String getFileConfig()
	{
		return fileConfig;
	}

	public void setFileConfig(String fileConfig)
	{
		this.fileConfig = fileConfig;
	}

	@Override
	public void save(FastTable<Subnet> data) throws IOException
	{
		PrintWriter out = null;
		out = new PrintWriter(new BufferedWriter(new FileWriter(fileConfig)));
		for (Subnet s : data)
		{
			out.write(s.toString());
		}
		out.close();
	}

	@Override
	public FastTable<Subnet> load() throws IOException
	{
		FastTable<Subnet> data = new FastTable<Subnet>();
		File fp;
		String lu = null;
		StringTokenizer strToken = null;
		fp = new File(fileConfig);

		if (fp == null || !fp.exists())
		{
			return null;
		}
		try
		{
			lu = Functions.readConf(fp);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

		strToken = new StringTokenizer(lu, "\n");
		while (strToken.hasMoreTokens())
		{
			StringTokenizer currStrToken = new StringTokenizer(
					strToken.nextToken(), "\n");

			data.add(new Subnet(currStrToken.nextToken(), currStrToken
					.nextToken(), currStrToken.nextToken()));
		}
		return data;
	}

}
