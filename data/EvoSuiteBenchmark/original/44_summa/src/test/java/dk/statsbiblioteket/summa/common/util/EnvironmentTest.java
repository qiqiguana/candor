/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package dk.statsbiblioteket.summa.common.util;

import junit.framework.TestCase;

/**
 * Unit tests for the {@link Environment} class
 */
public class EnvironmentTest extends TestCase {

    /** Test escape one system properties. */ 
    public void testEscapeOneSysProp() {
        String home = System.getProperty("user.home");
        String source = "${user.home}";

        assertEquals(home,
                     Environment.escapeSystemProperties(source));
    }

    /** Test escape two system properties. */
    public void testEscapeTwoSysProps() {
        String home = System.getProperty("user.home");
        String ioTmpdir = System.getProperty("java.io.tmpdir");
        String source = "${user.home} ${java.io.tmpdir}";

        assertEquals(home + " " + ioTmpdir,
               Environment.escapeSystemProperties(source));
    }

    /** Test escape two system properties with spaces. */
    public void testEscapeTwoSysPropsPreserveSpaces() {
        String home = System.getProperty("user.home");
        String ioTmpdir = System.getProperty("java.io.tmpdir");
        String source = " ${user.home}  ${java.io.tmpdir} ";

        assertEquals(" " + home + "  " + ioTmpdir + " ",
               Environment.escapeSystemProperties(source));
    }

    /**
     * Unknown system. props should not be touched
     */
    public void testEscapeNonExistingSysProp () {
        String source = "${user.homer}";

        assertEquals(source,
                     Environment.escapeSystemProperties(source));
    }

    /** Test unclosed system properties. */
    public void testUnclosedSysProp() {
        String source = "${user.home";

        assertEquals(source,
                     Environment.escapeSystemProperties(source));
    }

    /** Test null in properties. */
    public void testNull() {
        assertEquals(null,
                     Environment.escapeSystemProperties((String)null));
        assertEquals(null,
                     Environment.escapeSystemProperties((String[])null));
        assertEquals(null,
                     Environment.escapeSystemProperties((Iterable<String>)null));
    }

    /**
     * Check java version test.
     * @throws Exception If error.
     */
    public void testCheckJavaVersion() throws Exception {
        //System.out.println("Current JVM version: "
        //                   + Environment.checkJavaVersion());
        // TODO assert  
    }
}

