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
 * Copyright 2003-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: Main.java,v 1.4 2005/04/02 15:05:36 tanderson Exp $
 */
package org.exolab.jms.plugins.proxygen;

import java.net.URLClassLoader;
import java.net.URL;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;


/**
 * Helper class to invoke the proxy generator from the command line.
 *
 * @version     $Revision: 1.4 $ $Date: 2005/04/02 15:05:36 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public final class Main {

    /**
     * Prevent construction of helper class.
     */
    private Main() {
    }

    /**
     * Invokes the proxy generator.
     *
     * @param args the command line arguments
     * @throws Exception for any error
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1 || args.length > 2) {
            usage();
            System.exit(1);
        }
        String name = args[0];
        ClassLoader loader = Main.class.getClassLoader();

        if (args.length == 2) {
            String classpath = args[1];
            StringTokenizer paths = new StringTokenizer(
                    classpath, File.pathSeparator);
            URL[] urls = new URL[paths.countTokens()];
            for (int i = 0; i < urls.length; ++i) {
                File file = new File(paths.nextToken());
                if (!file.exists()) {
                    throw new FileNotFoundException(file.getPath());
                } 
                urls[i] = file.getCanonicalFile().toURL();
            }
            loader = new URLClassLoader(urls);
        }
        Class clazz = loader.loadClass(name);
        ProxyGenerator generator = new ProxyGenerator(clazz, null);
        generator.generate(System.out);
    }

    /**
     * Prints usage information.
     */
    private static void usage() {
        System.err.println("usage: " + Main.class.getName()
                           + " <classname> [classpath]");
    }

}
