package org.character.data.subnet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javolution.util.FastTable;
import org.javathena.core.data.PersistenteData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLSubnet implements PersistenteData<FastTable<Subnet>>
{
	private final static String DEFAULT_FILE = "conf/subnet_athena.xml";
	private XStream xstream;
	private String fileConfig = DEFAULT_FILE;
	public XMLSubnet(){xstream = new XStream(new DomDriver());}
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
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileConfig)));
		out.write(xstream.toXML(data));
		out.close();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public FastTable<Subnet> load() throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(new File(fileConfig)));
		String lu = "";
		String line = null;
		 while ((line = in.readLine())!= null)
		 {
			lu += line;
		}
		in.close();
		return (FastTable<Subnet>) xstream.fromXML(lu);
	}

}
