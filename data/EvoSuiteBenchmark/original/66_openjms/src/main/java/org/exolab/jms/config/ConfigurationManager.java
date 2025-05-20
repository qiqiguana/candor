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
 * Copyright 2000-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ConfigurationManager.java,v 1.2 2005/08/30 05:07:11 tanderson Exp $
 */
package org.exolab.jms.config;

import java.io.File;

import org.exolab.jms.config.types.SchemeType;


/**
 * The ConfigurationManager manages provides class methods for setting and
 * getting the configuration file. It should be set by the application main line
 * through {@link #setConfig} and subsequently accessed by other components
 * through {@link #getConfig}.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 05:07:11 $
 * @deprecated no replacement
 */
public class ConfigurationManager {

    /**
     * The loaded configuration
     */
    private static Configuration _config = null;

    /**
     * Load the configuration file
     *
     * @param path xml config file conforming to openjms.xsd schema
     * @throws FileDoesNotExistException  if the file does not exist
     * @throws ConfigurationFileException if the file is not well-formed
     */
    public static synchronized void setConfig(String path)
            throws FileDoesNotExistException, ConfigurationFileException {
        File config = new File(path);

        if (config.exists()) {
            ConfigurationLoader loader = new ConfigurationLoader();
            try {
                _config = loader.load(path);
            } catch (Exception exception) {
                throw new ConfigurationFileException(
                        "Error occured in " + path + " " + exception);
            }
        } else {
            throw new FileDoesNotExistException(
                    "Configuration file " + path + " does not exist.");
        }
    }

    /**
     * Set the configuration
     *
     * @param config the configuration
     */
    public static synchronized void setConfig(Configuration config) {
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }
        _config = config;
    }

    /**
     * Returns the configuration
     *
     * @return the configuration
     * @throws IllegalStateException if the configuration has not been
     *                               initialised
     */
    public static synchronized Configuration getConfig() {
        if (_config == null) {
            throw new IllegalStateException(
                    "Configuration manager has not been initialised");
        }
        return _config;
    }

    /**
     * Returns the connector configuration for the supplied scheme
     *
     * @param scheme the connector scheme
     * @return the connector configuration for the supplied scheme, or null, if
     *         no configuration exists
     * @throws IllegalArgumentException if scheme is null
     * @throws IllegalStateException    if the configuration is not initialised
     * @deprecated
     */
    public static Connector getConnector(SchemeType scheme) {
        if (scheme == null) {
            throw new IllegalArgumentException("Argument scheme is null");
        }
        Connector result = null;
        Configuration config = getConfig();
        Connector[] connectors = config.getConnectors().getConnector();
        for (int i = 0; i < connectors.length; ++i) {
            if (connectors[i].getScheme().equals(scheme)) {
                result = connectors[i];
                break;
            }
        }
        return result;
    }

    /**
     * Returns the default connector. This is the first configured connector in
     * the configuration.
     *
     * @return the default connector
     * @throws IllegalStateException if the configuration is not initialised
     * @see #getConnector(SchemeType)
     * @deprecated This method relies on users knowing that the first connector
     *             is the one that will be used.
     */
    public static Connector getConnector() {
        Configuration config = getConfig();
        return config.getConnectors().getConnector(0);
    }

}
