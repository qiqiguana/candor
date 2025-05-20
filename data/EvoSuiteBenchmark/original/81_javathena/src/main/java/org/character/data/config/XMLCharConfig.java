package org.character.data.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.javathena.core.data.PersistenteData;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLCharConfig implements PersistenteData<CharConfig>
{
    private final static String DEFAULT_FILE = "conf/char_athena.xml";

    private String fileConfig = DEFAULT_FILE;

    private XStream xstream;

    public XMLCharConfig()
    {
	xstream = new XStream(new DomDriver());
    }

    @Override
    public void save(CharConfig data) throws IOException
    {
	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileConfig)));
	out.write(xstream.toXML(data));
	out.close();
    }

    @Override
    public CharConfig load() throws IOException
    {
	BufferedReader in = new BufferedReader(new FileReader(new File(fileConfig)));
	String lu = "";
	String line = null;
	while ((line = in.readLine()) != null)
	{
	    lu += line;
	}
	in.close();
	return (CharConfig) xstream.fromXML(lu);
    }

    public String getFileConfig()
    {
	return fileConfig;
    }

    public void setFileConfig(String fileConfig)
    {
	this.fileConfig = fileConfig;
    }

}
