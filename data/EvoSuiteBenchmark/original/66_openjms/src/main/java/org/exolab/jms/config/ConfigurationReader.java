/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ConfigurationReader.java,v 1.1 2005/10/20 14:13:25 tanderson Exp $
 */
package org.exolab.jms.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;


/**
 * The ConfigurationReader reads a {@link Configuration} document and populates
 * unset items with those provided by {@link DefaultConfiguration}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/10/20 14:13:25 $
 * @see Configuration
 */
public final class ConfigurationReader {

    /**
     * The default configuration path, as a resource.
     */
    private static final String DEFAULT_CONFIG = "openjms_defaults.xml";


    /**
     * Prevent construction of utility class.
     */
    private ConfigurationReader() {
    }

    /**
     * Loads the configuration information from the specified file. Unpopulated
     * configuration elements will be set to those provided by {@link
     * DefaultConfiguration}.
     *
     * @param path the path to the file
     * @return the configuration
     * @throws IOException         for any I/O error
     * @throws MarshalException    if there is an error during unmarshalling
     * @throws ValidationException if there is a validation error
     */
    public static Configuration read(String path)
            throws IOException, MarshalException, ValidationException {
        InputStream stream = new FileInputStream(path);
        return read(stream);
    }

    /**
     * Loads the configuration from a stream.
     * Unpopulated configuration elements will be set to those provided by
     * {@link  DefaultConfiguration}.
     *
     * @param stream the stream to read from
     * @return the configuration
     * @throws IOException         for any I/O error
     * @throws MarshalException    if there is an error during unmarshalling
     * @throws ValidationException if there is a validation error
     */
    public static Configuration read(InputStream stream)
            throws IOException, MarshalException, ValidationException {
        Configuration result = null;
        Unmarshaller unmarshaller = new Unmarshaller(Configuration.class);
        InputStreamReader reader = new InputStreamReader(stream);
        AttributeExpander handler = new AttributeExpander(reader);
        result = (Configuration) unmarshaller.unmarshal(handler);
        return setDefaults(result);
    }

    /**
     * Sets unpopulated elements in the supplied configuration with default
     * values. The default values are provided by {@link DefaultConfiguration}.
     *
     * @param config the configuration
     * @return the configuration, with unpopulated elements set to the defaults
     * @throws IOException         for any I/O error
     * @throws MarshalException    if there is an error during unmarshalling
     * @throws ValidationException if there is a validation error
     */
    public static Configuration setDefaults(Configuration config)
            throws IOException, MarshalException, ValidationException {
        DefaultConfiguration defaults = getDefaults();

        if (config.getServerConfiguration() == null) {
            config.setServerConfiguration(defaults.getServerConfiguration());
        }
        if (config.getConnectors() == null) {
            config.setConnectors(defaults.getConnectors());
        }
        if (config.getLoggerConfiguration() == null) {
            config.setLoggerConfiguration(defaults.getLoggerConfiguration());
        }
        if (config.getTcpConfiguration() == null) {
            config.setTcpConfiguration(defaults.getTcpConfiguration());
        }
        if (config.getTcpsConfiguration() == null) {
            config.setTcpsConfiguration(defaults.getTcpsConfiguration());
        }
        if (config.getRmiConfiguration() == null) {
            config.setRmiConfiguration(defaults.getRmiConfiguration());
        }
        if (config.getHttpConfiguration() == null) {
            config.setHttpConfiguration(defaults.getHttpConfiguration());
        }
        if (config.getHttpsConfiguration() == null) {
            config.setHttpsConfiguration(defaults.getHttpsConfiguration());
        }
        if (config.getMessageManagerConfiguration() == null) {
            config.setMessageManagerConfiguration(
                    defaults.getMessageManagerConfiguration());
        }
        if (config.getSchedulerConfiguration() == null) {
            config.setSchedulerConfiguration(
                    defaults.getSchedulerConfiguration());
        }
        if (config.getGarbageCollectionConfiguration() == null) {
            config.setGarbageCollectionConfiguration(
                    defaults.getGarbageCollectionConfiguration());
        }
        if (config.getSecurityConfiguration() == null) {
            config.setSecurityConfiguration(
                    defaults.getSecurityConfiguration());
        }
        if (config.getServerConfiguration().getEmbeddedJNDI()) {
            // populate the JNDI configuration with the default values for
            // the connector
            config.setJndiConfiguration(
                    JndiConfigurationFactory.create(config));
        } else if (config.getJndiConfiguration() == null) {
            throw new ValidationException(
                    "JndiConfiguration must be provided when "
                    + "ServerConfiguration/embeddedJNDI is false");
        }
        return config;
    }

    /**
     * Returns the default configuration, loaded from {@link #DEFAULT_CONFIG}.
     *
     * @return the default configuration
     * @throws IOException         for any I/O error
     * @throws MarshalException    if there is an error during unmarshalling
     * @throws ValidationException if there is a validation error
     */
    private static DefaultConfiguration getDefaults()
            throws IOException, MarshalException, ValidationException {

        DefaultConfiguration result = null;
        InputStream source = Configuration.class.getResourceAsStream(
                DEFAULT_CONFIG);
        if (source == null) {
            throw new IOException(
                    "Failed to find default configuration: " + DEFAULT_CONFIG);
        }
        try {
            Unmarshaller stream = new Unmarshaller(DefaultConfiguration.class);
            AttributeExpander handler = new AttributeExpander(
                    new InputStreamReader(source));
            result = (DefaultConfiguration) stream.unmarshal(handler);
        } finally {
            try {
                source.close();
            } catch (IOException ignore) {
            }
        }
        return result;
    }

}
