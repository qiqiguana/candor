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

public class TestJniInchiOutput {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutput.getReturnStatus()'
     */
    @Test
    public void testGetReturnStatus() {
        JniInchiOutput output = new JniInchiOutput(INCHI_RET.OKAY, null, null, null, null);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutput.getInchi()'
     */
    @Test
    public void testGetInchi() {
        JniInchiOutput output = new JniInchiOutput(null, "Inchi=1/C6H6/c1-2-4-6-5-3-1/h1-6H", null, null, null);
        Assert.assertEquals("Inchi=1/C6H6/c1-2-4-6-5-3-1/h1-6H", output.getInchi());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutput.getAuxInfo()'
     */
    @Test
    public void testGetAuxInfo() {
        JniInchiOutput output = new JniInchiOutput(null, null, "AuxInfo=1/0/N:1,2,6,3,5,4/E:(1,2,3,4,5,6)/rA:6nCCCCCC/rB:d1;s2;d3;s4;s1d5;/rC:-.7145,.4125,0;-.7145,-.4125,0;0,-.825,0;.7145,-.4125,0;.7145,.4125,0;0,.825,0;", null, null);
        Assert.assertEquals("AuxInfo=1/0/N:1,2,6,3,5,4/E:(1,2,3,4,5,6)/rA:6nCCCCCC/rB:d1;s2;d3;s4;s1d5;/rC:-.7145,.4125,0;-.7145,-.4125,0;0,-.825,0;.7145,-.4125,0;.7145,.4125,0;0,.825,0;", output.getAuxInfo());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutput.getMessage()'
     */
    @Test
    public void testGetMessage() {
        JniInchiOutput output = new JniInchiOutput(null, null, null, "Test message", null);
        Assert.assertEquals("Test message", output.getMessage());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutput.getLog()'
     */
    @Test
    public void testGetLog() {
        JniInchiOutput output = new JniInchiOutput(null, null, null, null, "Test log");
        Assert.assertEquals("Test log", output.getLog());
    }
}
