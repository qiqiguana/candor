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
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ConfigurationLoader.java,v 1.3 2005/10/20 14:13:25 tanderson Exp $
 */
package org.exolab.jms.config;

import java.io.IOException;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;


/**
 * The ConfigurationLoader loads a {@link Configuration} document and populates
 * unset items with those provided by {@link DefaultConfiguration}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/10/20 14:13:25 $
 * @see Configuration
 */
public class ConfigurationLoader {

    /**
     * Construct a new <code>ConfigurationLoader</code>.
     */
    public ConfigurationLoader() {
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
    public Configuration load(String path)
            throws IOException, MarshalException, ValidationException {
        return ConfigurationReader.read(path);
    }

    /**
     * Loads unpopulated elements in the supplied configuration with default
     * values. The default values are provided by {@link DefaultConfiguration}.
     *
     * @param config the configuration
     * @return the configuration, with unpopulated elements set to the defaults
     * @throws IOException         for any I/O error
     * @throws MarshalException    if there is an error during unmarshalling
     * @throws ValidationException if there is a validation error
     */
    public Configuration load(Configuration config)
            throws IOException, MarshalException, ValidationException {
        return ConfigurationReader.setDefaults(config);
    }

}
