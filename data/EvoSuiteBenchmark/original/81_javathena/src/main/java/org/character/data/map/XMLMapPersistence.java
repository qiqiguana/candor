package org.character.data.map;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javolution.util.FastMap;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


import org.javathena.core.data.IndexedFastMap;
import org.javathena.core.data.PersistenteData;

public class XMLMapPersistence implements PersistenteData<IndexedFastMap<Integer, String>>
{
	private final static String DEFAULT_FILE = "db/map_index.xml";

	private String fileDB = DEFAULT_FILE;

	private XStream xstream;

	public XMLMapPersistence()
	{
		xstream = new XStream(new DomDriver());
	}

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
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileDB)));
		out.write(xstream.toXML(data.getData()));
		out.close();

	}
 
	@SuppressWarnings("unchecked")
	@Override
	public IndexedFastMap<Integer, String> load() throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(new File(fileDB)));
		IndexedFastMap<Integer, String> ifm = new IndexedFastMap<Integer, String>();
		String lu = "";
		String line = null;
		 while ((line = in.readLine())!= null)
		 {
			lu += line;
		}
		in.close();
		ifm.setData((FastMap<Integer, String>) xstream.fromXML(lu));
		ifm.setIndexedData(MapIndex.makeIndex(ifm.getData()));
		return ifm;
	}

}
