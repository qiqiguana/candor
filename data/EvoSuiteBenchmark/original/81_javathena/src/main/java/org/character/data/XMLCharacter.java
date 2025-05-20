package org.character.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.javathena.core.data.IndexedFastMap;
import org.javathena.core.data.PersistenteData;
import org.javathena.core.data.ROCharacter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLCharacter implements PersistenteData<IndexedFastMap<Integer, ROCharacter>>
{

    private final static String DEFAULT_FILE = "save/athena.xml";
    private String fileConfig = DEFAULT_FILE;
    private XStream xstream;

    public XMLCharacter()
    {
	xstream = new XStream(new DomDriver());
    }

    @SuppressWarnings("unchecked")
    @Override
    public IndexedFastMap<Integer, ROCharacter> load() throws IOException
    {
	BufferedReader in = new BufferedReader(new FileReader(new File(fileConfig)));
	String lu = "";
	String line = null;
	while ((line = in.readLine()) != null)
	{
	    lu += line;
	}
	in.close();
	return (IndexedFastMap<Integer, ROCharacter>) xstream.fromXML(lu);
    }

    @Override
    public void save(IndexedFastMap<Integer, ROCharacter> data) throws IOException
    {
	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileConfig)));
	out.write(xstream.toXML(data));
	out.close();

    }

}
