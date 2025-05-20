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
 *    permission of Intalio.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Intalio. Exolab is a registered
 *    trademark of Intalio.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY INTALIO AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * INTALIO OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2001,2003 (C) Intalio Inc. All Rights Reserved.
 *
 * $Id: Version.java,v 1.1 2004/11/26 01:51:02 tanderson Exp $
 */


package org.exolab.jms.util;

import java.util.Properties;


/**
 * This class provides version information from the <tt>openjms.version</tt>
 * configuration file.
 *
 * @author <a href="mailto:tima@intalio.com">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2004/11/26 01:51:02 $
 */
public final class Version {

    /**
     * The vendor name
     */
    public static final String VENDOR_NAME;

    /**
     * The vendor URL
     */
    public static final String VENDOR_URL;

    /**
     * The version number
     */
    public static final String VERSION;

    /**
     * The major version number
     */
    public static final int MAJOR_VERSION;

    /**
     * The minor version number
     */
    public static final int MINOR_VERSION;

    /**
     * The build date
     */
    public static final String BUILD_DATE;

    /**
     * The product title
     */
    public static final String TITLE;

    /**
     * The copyright message
     */
    public static final String COPYRIGHT;

    /**
     * The name of the version configuration file
     */
    public static final String FILE_NAME = "openjms.version";

    /**
     * The name of the default configuration file as a resource
     */
    private static final String RESOURCE_NAME = "/org/exolab/jms/" + FILE_NAME;

    /**
     * Default constructor
     */
    private Version() {
    }

    /**
     * Displays all version information
     */
    public static void main(String args[]) {
        System.err.println(TITLE + " " + VERSION + " (" + BUILD_DATE + ")");
        System.err.println(VENDOR_NAME + " (" + VENDOR_URL + ")");
        System.err.println(COPYRIGHT);
    }

    static {
        Properties properties = new Properties();
        int major = -1;
        int minor = -1;

        // Get version informatation from the JAR.
        try {
            properties.load(Version.class.getResourceAsStream(RESOURCE_NAME));
            major = Integer.parseInt(properties.getProperty("version.major"));
            minor = Integer.parseInt(properties.getProperty("version.minor"));
        } catch (Exception exception) {
            // This should never happen
            System.err.println("Error loading " + RESOURCE_NAME + ": " +
                exception.getMessage());
        }

        VENDOR_NAME = properties.getProperty("version.vendorName");
        VENDOR_URL = properties.getProperty("version.vendorUrl");
        VERSION = properties.getProperty("version");

        MAJOR_VERSION = major;
        MINOR_VERSION = minor;
        BUILD_DATE = properties.getProperty("build.date");

        TITLE = properties.getProperty("version.title");
        COPYRIGHT = properties.getProperty("version.copyright");
    }

} //-- Version
