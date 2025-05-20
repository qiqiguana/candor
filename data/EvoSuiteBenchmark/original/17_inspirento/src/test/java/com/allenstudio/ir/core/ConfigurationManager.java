/*
 * @(#)ConfigurationManager.java
 * Created on 2005-8-10
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

import java.util.*;
import java.io.*;

import com.allenstudio.ir.util.*;

/**
 * Manages the configuration for Inspirento.<br>
 * This manager uses XML format to store information.
 * The configuration file is, by default, saved in the
 * "config" directory and named "config.xml". Clearly,
 * this class should be a singleton, so we use
 * {@link #getInstance()} to get an instance and call
 * other instance methods to get the settings needed
 * by Inspirento, such as "window.size", "window.location",
 * and etc.<br>
 * The program first tries to get the configuration from
 * this <code>ConfigurationManager</code>. If it fails to
 * get any key, it uses the default settings presetted in
 * the protected <code>default</code> field.
 * 
 * @author Allen Chue
 */
public class ConfigurationManager extends Properties {
    
    public static final String CONFIG_DIRECTORY = "config";
    
    public static final String CONFIG_FILE = "config.xml";
    
    public static final String COMMON_PREFIX = "Inspirento.";
    
    private static ConfigurationManager instance = null;
    
    private XmlIO xmlIO;
        
    /**
     * Private constructor for singleton use.
     */
    private ConfigurationManager() {
        initDefaultSettings();
        
        readIn();
    }
    
    public static ConfigurationManager getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new ConfigurationManager();
            return instance;
        }
    }
    
    public void readIn() {
        try {
            File configFile = new File(
                    CONFIG_DIRECTORY + 
                    System.getProperty("file.separator") + 
                    CONFIG_FILE);//$NON-NLS-1$
            if (configFile.exists()) {
                FileInputStream configStream = new FileInputStream(configFile);
                xmlIO = new XmlIO();
                xmlIO.load(configStream);
                configStream.close();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file" +
                    " supposed to be at \"config\\config.xml\"" +
                    "\nDefault settings will be stored as the replacement.");//$NON-NLS-1$
            writeDefaultsToFile();
            e.printStackTrace();
        }
    }
    
    public void writeBack() {
        try {
            FileOutputStream configFile = new FileOutputStream(
                    CONFIG_DIRECTORY + 
                    System.getProperty("file.separator") + 
                    CONFIG_FILE);
            xmlIO.write(configFile);
            configFile.close();
        } catch (Exception e) {
            System.out.println("Cannot write configuration file" +
                    " to \"config\\config.xml\"");//$NON-NLS-1$
            e.printStackTrace();
        }
    }
    
    /**
     * Uses XML parser to get the specified property.
     * If there is no such a key, the method returns
     * <code>null</code>.
     * @param key the key of the property
     * @return the property value
     */
    @Override
    public synchronized String getProperty(String key) {
        String value = xmlIO.getRoot().getElement(Constants.PROJECT_NAME +
                "." + getPath(key)[0]).getAttribute(getPath(key)[1]);
        if (value == null) {//Perhaps some element is lost in the file
            value = defaults.getProperty(key);
            setProperty(key, value);//null value has no side effect
            new Thread(){
                @Override
                public void run() {
                    writeBack();
                }
            }.start();
        }
        
        return value;
    }
        
    @Override
    public synchronized Object setProperty(String key, String value) {
        xmlIO.getRoot().getElement(Constants.PROJECT_NAME +
                "." + getPath(key)[0]).addAttribute(getPath(key)[1], value);
        
        return value;
    }
    
    /**
     * When the configuration file is lost, this method
     * is used to write the default settings stored in
     * the program itself to file.
     *
     */
    private void writeDefaultsToFile() {
        Enumeration keys = defaults.keys();
        
        XmlElement xe = new XmlElement(Constants.PROJECT_NAME);
        xmlIO = new XmlIO(xe);
        for (; keys.hasMoreElements(); ) {
            String pathText = (String)keys.nextElement();

            String[] path = getPath(pathText);
            //Test if the element to be modified exists
            XmlElement elementAdded = xe.getElement(path[0]);
            if (elementAdded == null){
                elementAdded = xe.addSubElement(path[0]);
            }
            elementAdded.addAttribute(path[1], defaults.getProperty(pathText));
            
        }
        
        try {
            FileOutputStream configFile = new FileOutputStream(
                    CONFIG_DIRECTORY + 
                    System.getProperty("file.separator") + 
                    CONFIG_FILE);//$NON-NLS-1$
            xmlIO.write(configFile);
            configFile.close();
        } catch (Exception e) {
            System.out.println("Cannot write configuration file" +
                    " to \"config\\config.xml\"");//$NON-NLS-1$
            e.printStackTrace();
        }
    }
    
    /**
     * Returns an string array of length 2.
     * The parameter <code>pathText</code> is supposed to
     * be a string separated with dots. For example,
     * "Inspirento.window.location" is a valid parameter.
     * This method puts the token after the last dot in
     * the second position of the result array, and the
     * remaining string(excluding the last dot) in the first
     * position of the result array. It is a rivate helping method.
     * <br>
     * Example: getPath("Inspirento.window.location") returns
     * the array {"Inspirento.window", "location"}.<br>
     * <em>No format checking is done in this method! <code>
     * ArrayOutOfBoundsException</code> will be thrown
     * when no dots are found in the string.</em>
     * @param pathText the path text to be processed
     * @return an array containing the result
     */
    private static String[] getPath(String pathText) {
        int dotPos = pathText.lastIndexOf('.');
        
        String[] result = new String[2];
        result[0] = pathText.substring(0, dotPos);
        result[1] = pathText.substring(dotPos + 1);
        
        return result;
    }
    
    private void initDefaultSettings() {
        String[] configDefaults = {
                "window.location", "400,300",
                "window.size", "450,300"
        };
        defaults = new Properties();
        for(int i = 0, max = configDefaults.length; i < max; i += 2) {
            String value = configDefaults[i + 1];
            defaults.setProperty(configDefaults[i], value);
        }
    }
}
