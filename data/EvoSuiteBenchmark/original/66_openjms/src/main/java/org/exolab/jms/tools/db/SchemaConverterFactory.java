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
 * Copyright 2002-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: SchemaConverterFactory.java,v 1.1 2004/11/26 01:51:16 tanderson Exp $
 */
package org.exolab.jms.tools.db;

import java.sql.Connection;

import org.exolab.jms.tools.db.migration.V061toV072SchemaConverter;
import org.exolab.jms.tools.db.migration.V072toV076SchemaConverter;


/**
 * A factory for {@link SchemaConverter} instances
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:16 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class SchemaConverterFactory {

    /**
     * Supported schema versions
     */
    private static final String VERSION_0_6_1 = "V0.6.1";
    private static final String VERSION_0_7_2 = "V0.7.2";
    private static final String VERSION_0_7_6 = "V0.7.6";


    public static SchemaConverter create(String fromVersion, String toVersion,
                                         Connection connection) {
        SchemaConverter result = null;
        if (fromVersion.equals(VERSION_0_6_1) &&
            toVersion.equals(VERSION_0_7_2)) {
            result = new V061toV072SchemaConverter(connection);
        }
        if (fromVersion.equals(VERSION_0_7_2) &&
            toVersion.equals(VERSION_0_7_6)) {
            result = new V072toV076SchemaConverter(connection);
        }

        return result;
    }

} //-- SchemaConverterFactory
