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
 * Copyright 2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JVM.java,v 1.2 2008/01/07 13:00:18 tanderson Exp $
 */
package org.exolab.jms.net.jvm;

import java.util.Enumeration;
import java.util.Properties;


/**
 * Helper for starting a new JVM.
 *
 * @version     $Revision: 1.2 $ $Date: 2008/01/07 13:00:18 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class JVM extends Executor {

    /**
     * Construct a new <code>JVM</code>, specifying the classpath
     * Output will be directed to System.out and System.err
     *
     * @param mainClass the fully qualified name of the main class
     * @param classpath the classpath. If <code>null</code>, uses the
     * value of the <code>"java.class.path"</code> system property
     * @param sysProps system properties to pass to the JVM.
     * May be <code>null</code>
     * @param args the command line arguments. May be <code>null</code>
     */
    public JVM(String mainClass, String classpath, Properties sysProps,
               String args) {
        super(getCommand(mainClass, classpath, sysProps, args));
    }

    /**
     * Generates the command line for the JVM
     *
     * @param mainClass the fully qualified name of the main class
     * @param classpath the classpath. If <code>null</code>, uses the
     * value of the <code>"java.class.path"</code> system property
     * @param sysProps system properties to pass to the JVM.
     * @param args the command line arguments. May be <code>null</code>
     */
    private static String getCommand(String mainClass, String classpath,
                                     Properties sysProps, String args) {
        if (classpath == null) {
            classpath = System.getProperty("java.class.path");
        }
        StringBuffer command = new StringBuffer("java -cp ");
        command.append("\"").append(classpath).append("\"");
        command.append(" ");
        if (sysProps != null) {
            Enumeration names = sysProps.propertyNames();
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = sysProps.getProperty(name);
                command.append("-D");
                command.append(name);
                command.append("=");
                command.append(value);
                command.append(" ");
            }
        }
        command.append(mainClass);
        if (args != null) {
            command.append(" ");
            command.append(args);
        }
        return command.toString();
    }
}
