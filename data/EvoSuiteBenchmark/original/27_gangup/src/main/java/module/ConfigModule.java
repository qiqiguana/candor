/* $Id: ConfigModule.java,v 1.3 2004/05/05 14:15:47 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.3 $
 *
 * Reference: http://java.sun.com/xml/jaxp/dist/1.1/docs/tutorial/index.html
 */

package module;

import java.util.Hashtable;
import java.io.IOException;
import java.io.File;

import java.util.ResourceBundle;
import java.util.Locale;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult; 

import org.xml.sax.*;
import org.w3c.dom.*;

@cvs(file     = "$RCSfile: ConfigModule.java,v $",
     revision = "$Revision: 1.3 $",
     date     = "$Date: 2004/05/05 14:15:47 $",
     author   = "$Author: emill $",
     tag      = "$Name:  $",
     build    = "")

@mod(name     = "ConfigModule",
     topics   = "CONFIG",
     cmds     = "DUMP READ WRITE RELOAD GET GETS SET SETS",
     desc     = "")

/**
 * This class provides a centralized access point to configuration files
 * for other modules. Currently it is capable of loading and saving a
 * configuration, as well as adding and modifying module variables.
 */
public class ConfigModule extends AbstractModule {

    /** Reference to the the document containing the configuration. */
    private Document config;

    /** The root element of the document. */
    private Element root;

    /** Lookup table for quickly accessing XML-nodes. */
    private Hashtable<String,Element> table;

    /** Current path to the configuration file. */
    private String fileName = null;

    /**
     * Creates a new instance of the ConfigModule class.
     *
     */
    public ConfigModule()
	throws ModuleRegisterException {}

    /**
     * Creates a new instance of the ConfigModule class.
     * @param krn the Kernel associated with this Module.
     */
    public ConfigModule(Kernel krn)
	throws ModuleRegisterException {
	super(krn);
    }

    /**
     *
     *
     */
    protected void init() {
	table = new Hashtable<String,Element>();
    }

    /**
     * Reads a configuration from the specified file.
     * @param fileName The file name that contains the configuration.
     */
    public void read(String fileName) {
	
	DocumentBuilder builder;
	DocumentBuilderFactory factory;
	NodeList list;

	try {

	    factory = DocumentBuilderFactory.newInstance();
	    factory.setValidating(true);

	    builder = factory.newDocumentBuilder();
	    config = builder.parse(fileName);

	    /* hash all variables ... why are we using xml? */

	    root = config.getDocumentElement();
	    list = config.getElementsByTagName("variable");

	    for (int i=0; i<list.getLength(); i++) {
		Element e = (Element) list.item(i);
		Element p = (Element) e.getParentNode();

		table.put(p.getAttribute("name") + "." +
			  e.getAttribute("name"), e);
	    }

	    /* this can of course be done more efficiently */

	    list = config.getElementsByTagName("section");

	    for (int i=0; i<list.getLength(); i++) {
		Element e = (Element) list.item(i);
		table.put(e.getAttribute("name"), e);
	    }

	} catch (SAXException e) {

	    Exception x = e.getException();
	    if (x == null) {
		x = e;
	    } 
	    x.printStackTrace();

	} catch (ParserConfigurationException e) {
	    e.printStackTrace();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Writes the configuration to the specified file.
     * @param fileName The name of the file that is to be written.
     */
    public void write(String fileName) {

	try {

	    File file = new File(fileName);

	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer transformer = factory.newTransformer();

	    DOMSource source = new DOMSource(config);
	    StreamResult result = new StreamResult(file);

	    transformer.transform(source, result);
	    file = null;

	} catch (TransformerConfigurationException e) {

	    Throwable x = e;

	    if (e.getException() != null) {
		x = e.getException();
	    }
	    x.printStackTrace();
      
	} catch (TransformerException e) {

	    Throwable x = e;

	    if (e.getException() != null) {
		x = e.getException();
	    }
	    x.printStackTrace();
           
	}

    }

    /**
     * Returns the value of the specified key in this configuration.
     * @param mod The section that contains the variable.
     * @param var The variable to lookup.
     * @return The value of the specified key. 
     */
    public String getCVar(String sec, String var) {
	Element e = table.get(sec + "." + var);
	if (e != null) {
	    return e.getAttribute("value");
	}
	return "";
    }

    /**
     * Returns the values of the specified keys in this configuration,
     * or, if no keys were specified, returns the values of all keys.
     *
     * @param sec The section that contains the variable.
     * @param var The variable to lookup.
     * @return The value of the specified key. 
     */
    public String getCVars(String sec, String vars) {

	String vals = "";

	/* Only modulename specified, read all variables! */
	
	if (vars == null || vars.length() == 0) {

	    Element e = table.get(sec);
	    NodeList list = e.getElementsByTagName("variable");

	    for (int i=0; i<list.getLength(); i++) {
		e = (Element) list.item(i);
		vals += e.getAttribute("name")  + ":" +
		        e.getAttribute("value") + "\n";
	    }

	} else { /* Both modulename and variables specified */

	    String[] vlist = vars.split(",");

	    for (int i=0; i<vlist.length; i++) {
		vals += getCVar(sec,vlist[i]) + "\n";
	    }
	}

	return vals;
    }

    /**
     * Sets the value of the specified variable to val.
     *
     * @param sec The section that contains the variable.
     * @param var The variable to set.
     * @param val The new value of the variable.
     */
    public void setCVar(String sec, String var, String val) {
	Element e = table.get(sec + "." + var);
	if (e == null) {
	    Element m = table.get(sec);
	    if (m == null) {
		m = config.createElement("section");
		m.setAttribute("name", sec);
		root.appendChild(m);
	    }
	    e = config.createElement("variable");
	    e.setAttribute("name", var);
	    m.appendChild(e);
	}
	table.put(sec + "." + var, e);
	e.setAttribute("value", val);
    }

    /**
     * This method is invoked once for every Message in the input queue.
     * 
     * @param msg The Message that is to be processed.
     */
    protected void processMessage(Message msg) {

	String[] cmd = ((String) msg.body).split(" ");
	Message r = msg.reply(null);

	System.err.println("ConfigModule.processMessage(): " + msg.body);

	/* process module specific messages */

	if (cmd[0].equals("READ")) {
	    if (cmd.length == 2) {
		fileName = cmd[1];
		read(fileName);
	    } else {
		r.header = "WARNING";
		r.body = locale.getString("CFG_READ_WRONG_NARGS");
		sendMessage(r);
	    }
	}

	if (cmd[0].equals("WRITE")) {
	    if (cmd.length == 2) {
		fileName = cmd[1];
	    }
	    write(fileName);
	}

	if (cmd[0].equals("RELOAD") && fileName != null) {
	    read(fileName);
	}

	if (cmd[0].equals("DUMP")) {
	    System.err.println(table);
	}

	if (cmd[0].trim().equals("GET")) {
	    String var = getCVar(cmd[1].trim(), cmd[2].trim());
	    if (var != null) {
		r.body = var;
		sendMessage(r);
	    }
	} else if (cmd[0].trim().equals("SET")) {
	    setCVar(cmd[1].trim(), cmd[2].trim(), cmd[3].trim());
	}

	/* Get multiple variables in a single request. */

	if (cmd[0].trim().equals("GETS")) {

	    try {

		String mod = cmd[1].trim();
		String vals;

		if (cmd.length > 2) {
		    vals = getCVars(mod,cmd[2].trim());
		} else {
		    vals = getCVars(mod,null);
		}
		
		r.body = vals;
		sendMessage(r);

	    } catch (IndexOutOfBoundsException e) {
		e.printStackTrace(System.err);
	    } catch (Exception e) {
		e.printStackTrace(System.err);
	    }
	}
    }
}
