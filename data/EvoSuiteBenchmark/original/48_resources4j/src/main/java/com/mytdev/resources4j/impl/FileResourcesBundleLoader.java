/*
 * FileResourcesBundleLoader.java
 *
 *  Copyright 2010 Yann D'Isanto.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.mytdev.resources4j.impl;

import com.mytdev.resources4j.interfaces.Resources;
import com.mytdev.resources4j.interfaces.ResourcesBundle;
import com.mytdev.resources4j.interfaces.ResourcesBundleLoader;
import com.mytdev.resources4j.ResourcesException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

/**
 * A File based ResourcesBundleLoader.
 * @author Yann D'Isanto
 */
public class FileResourcesBundleLoader implements ResourcesBundleLoader {

    private static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
    private File directory;
    private String fileBasename;
    private int dateFormatStyle = DateFormat.MEDIUM;
    private DateFormat dateFormat = null;
    private Charset charset = DEFAULT_CHARSET;

    public FileResourcesBundleLoader(String directoryPath, String fileBasename) {
        this(new File(directoryPath), fileBasename);
    }

    public FileResourcesBundleLoader(Class<?> c, String fileBasename) {
        this(c.getPackage().getName().replace(".", "/"), fileBasename);
    }

    public FileResourcesBundleLoader(File directory, String fileBasename) {
        this.directory = directory;
        this.fileBasename = fileBasename;
    }

    @Override
    public ResourcesBundle loadBundle() throws ResourcesException {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("not a directory: " + directory);
        }
        Map<Locale, Resources> bundle = new HashMap<Locale, Resources>();
        try {
            // load root locale file
            Resources rootResources = new AbstractResources(
                    loadProperties(
                    new File(directory, fileBasename + ".properties")),
                    dateFormat == null ? DateFormat.getDateInstance(dateFormatStyle) : dateFormat) {

                @Override
                protected BufferedImage loadImage(String key) throws IOException {
                    return ImageIO.read(new File(directory, key));
                }
            };
            bundle.put(Locale.ROOT, rootResources);

            // load languages file
            Map<String, Resources> languagesBunble = new HashMap<String, Resources>();
            File[] files = directory.listFiles(new FilenameFilter() {

                private Pattern filenamePattern = Pattern.compile(
                        Pattern.quote(getFileBasename() + "_")
                        + "[a-zA-Z]{2}" + Pattern.quote(".properties"));

                @Override
                public boolean accept(File dir, String name) {
                    return filenamePattern.matcher(name).matches();
                }
            });
            for (File file : files) {
                String language = file.getName().replaceAll(
                        "(^" + Pattern.quote(fileBasename + "_") + ")|(\\.properties$)",
                        "");
                Locale locale = new Locale(language);
                Resources resources = new ChildResources(loadProperties(file),
                        rootResources, 
                        dateFormat == null ? DateFormat.getDateInstance(dateFormatStyle) : dateFormat) {

                    @Override
                    protected BufferedImage loadImage(String key) throws IOException {
                        return ImageIO.read(new File(directory, key));
                    }
                };
                bundle.put(locale, resources);
                languagesBunble.put(language, resources);
            }

            // load countries file
            files = directory.listFiles(new FilenameFilter() {

                private Pattern filenamePattern = Pattern.compile(
                        Pattern.quote(getFileBasename() + "_")
                        + "[a-zA-Z]{2}_[a-zA-Z]{2}"
                        + Pattern.quote(".properties"));

                @Override
                public boolean accept(File dir, String name) {
                    return filenamePattern.matcher(name).matches();
                }
            });
            for (File file : files) {
                String[] localeStr = file.getName().replaceAll(
                        "(^" + Pattern.quote(fileBasename + "_") + ")|(\\.properties$)",
                        "").split("_");
                String language = localeStr[0];
                String country = localeStr[1];
                Locale locale = new Locale(language, country);
                Resources languageResources = languagesBunble.get(language);
                Resources resources = new ChildResources(loadProperties(file),
                        languageResources,
                        dateFormat == null ? DateFormat.getDateInstance(dateFormatStyle) : dateFormat) {

                    @Override
                    protected BufferedImage loadImage(String key) throws IOException {
                        return ImageIO.read(new File(directory, key));
                    }
                };
                bundle.put(locale, resources);
            }
        } catch (IOException ex) {
            throw new ResourcesException(ex);
        }
        return new MapResourcesBunble(bundle);
    }

    private Map<String, String> loadProperties(File file) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(file);
        try {

            properties.load(new InputStreamReader(fis, charset));
        } finally {
            fis.close();
        }
        Map<String, String> propertiesMap = new HashMap<String, String>();
        for (String key : properties.stringPropertyNames()) {
            propertiesMap.put(key, properties.getProperty(key));
        }
        return propertiesMap;
    }

    /**
     * @return the directory
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * @param directory the directory to set
     */
    public void setDirectory(File directory) {
        this.directory = directory;
    }

    /**
     * @return the fileBasename
     */
    public String getFileBasename() {
        return fileBasename;
    }

    /**
     * @param fileBasename the fileBasename to set
     */
    public void setFileBasename(String fileBasename) {
        this.fileBasename = fileBasename;
    }

    /**
     * @return the dateFormatStyle
     */
    public int getDateFormatStyle() {
        return dateFormatStyle;
    }

    /**
     * @param dateFormatStyle the dateFormatStyle to set
     */
    public void setDateFormatStyle(int dateFormatStyle) {
        this.dateFormatStyle = dateFormatStyle;
    }

    /**
     * @return the dateFormatPattern
     */
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * @param dateFormatPattern the dateFormatPattern to set
     */
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * @return the charset
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
