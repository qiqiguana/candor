/*
 * @(#)PluginManager.java
 * Created on 2005-8-27
 * Inspirento, Copyright AllenStudio, All Rights Reserved
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package com.allenstudio.ir.core;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Manages the plugins in Inspirento.
 * The plugins is currently just the types
 * of notes. A note-type, i.e. the plugin,
 * becomes available only if it is registered
 * in the plugin registration file(by default,
 * stored at "config\plugin.xml").
 * 
 * @version 27/8/2005
 * @author Allen Chue
 */
public class PluginManager {
    
    public static void main(String[] args) {

        System.out.println(PluginManager.getInstance().getAllPluginNames());
    }
    
    public static final String PLUGIN_REGISTRY = "plugins.xml";
    
    private static final String PLUGIN_DTD =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<!-- DTD for plugin registry -->"                +
        "<!ELEMENT plugins ( plugin* ) >"+
        "<!ELEMENT plugin (#PCDATA) >" +
        "<!ATTLIST plugin " +
            " class ID #REQUIRED" +
            " title CDATA #REQUIRED >";
    
    private static PluginManager instance = null;


    private Document source;
        
    /**
     * Private constructor for singleton use.
     */
    private PluginManager() {
        try {
            source = loadDocument(new File(
                    ConfigurationManager.CONFIG_DIRECTORY + System.getProperty("file.separator") +
                    PLUGIN_REGISTRY));
        } catch (SAXException e) {
            System.out.println("Fatal error! Cannot parse the plugin registry file.");
            e.printStackTrace();
            System.exit(-5);
        } catch (IOException e) {
            System.out.println("Fatal error! Cannot read the plugin registry file.");
            e.printStackTrace();
            System.exit(-6);
        }
    }
    
    private static Document loadDocument(File in)
            throws SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setValidating(true);
        dbf.setCoalescing(true);
        dbf.setIgnoringComments(true);
        
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            db.setEntityResolver(new Resolver());
            db.setErrorHandler(new EH());
            return db.parse(in);
        } catch (ParserConfigurationException e) {
            System.out.println("Fatal error! Parser configuration error occured.");
            e.printStackTrace();
            throw new Error(e);
        }
    }
    
    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        
        return instance;
    }
    
    /**
     * Gets all plugins' name.
     * The name of a plugin is the title attribute in the
     * plugin registry corresponding to this very plugin.
     * @return All the names 
     */
    public String[] getAllPluginNames() {
        NodeList allList = source.getChildNodes();
        Element rootElement = (Element)allList.item(1);//rootElement is <plugins> tag
        
        NodeList plugins = rootElement.getChildNodes();
        int num = plugins.getLength();//# of plugins
        String[] pluginNames = new String[num];
        for (int i = 0; i < num; i++) {
            pluginNames[i] = ((Element)plugins.item(i)).getAttribute("title");
        }
        
        return pluginNames;
    }
    
    /**
     * Returns all plugins' class definition.
     * This is just the class attribute to each
     * "&lt;plugin&gt; entry. The program uses
     * reflection to get their instances.
     * @return all plugins' class definition in an array
     */
    public String[] getPlugins() {
        NodeList allList = source.getChildNodes();
        Element rootElement = (Element)allList.item(1);//rootElement is <plugins> tag
        
        NodeList plugins = rootElement.getChildNodes();
        int num = plugins.getLength();//# of plugins
        String[] pluginNames = new String[num];
        for (int i = 0; i < num; i++) {
            pluginNames[i] = ((Element)plugins.item(i)).getAttribute("class");
        }
        
        return pluginNames;
    }
    
    /**
     * Entity resolver.
     * 
     * @author Allen Chue
     */
    private static class Resolver implements EntityResolver {
        public InputSource resolveEntity(String pid, String sid)
            throws SAXException
        {
            InputSource is;
            is = new InputSource(new StringReader(PLUGIN_DTD));
            return is;
        }
    }
    
    /**
     * Error handler
     * 
     * @author Allen Chue
     */
    private static class EH implements ErrorHandler {
        public void error(SAXParseException x) throws SAXException {
            throw x;
        }
        public void fatalError(SAXParseException x) throws SAXException {
            throw x;
        }
        public void warning(SAXParseException x) throws SAXException {
            throw x;
        }
    }
}
