/*
 * Copyright 2006-2011 Sam Adams <sea36 at users.sourceforge.net>
 *
 * This file is part of JNI-InChI.
 *
 * JNI-InChI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JNI-InChI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JNI-InChI.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jniinchi;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiOutputStructure {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getReturnStatus()'
     */
    @Test
    public void testGetReturnStatus() {
        JniInchiOutputStructure output = new JniInchiOutputStructure(INCHI_RET.OKAY);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getMessage()'
     */
    @Test
    public void testGetMessage() {
        JniInchiOutputStructure output = new JniInchiOutputStructure(INCHI_RET.OKAY);
        output.setMessage("Test message");
        Assert.assertEquals("Test message", output.getMessage());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getLog()'
     */
    @Test
    public void testGetLog() {
        JniInchiOutputStructure output = new JniInchiOutputStructure(INCHI_RET.OKAY);
        output.setLog("Test log");
        Assert.assertEquals("Test log", output.getLog());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getWarningFlags()'
     */
    @Test
    public void testGetWarningFlags() {
        JniInchiOutputStructure output = new JniInchiOutputStructure(INCHI_RET.OKAY);
        long[][] flags = {{1, 2}, {3, 4}};
        output.setWarningFlags(flags);
        Assert.assertEquals(flags, output.getWarningFlags());
    }

}
