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

public class TestJniInchiStereo0D {

    private static JniInchiAtom atC = new JniInchiAtom(0, 0, 0, "C");
    private static JniInchiAtom at0 = new JniInchiAtom(0, 0, 0, "N");
    private static JniInchiAtom at1 = new JniInchiAtom(0, 0, 0, "O");
    private static JniInchiAtom at2 = new JniInchiAtom(0, 0, 0, "S");
    private static JniInchiAtom at3 = new JniInchiAtom(0, 0, 0, "F");

    protected static JniInchiStereo0D getTestStereo0D() {
        return new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.ODD);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.JniInchiStereo0D(JniInchiAtom, JniInchiAtom, JniInchiAtom, JniInchiAtom, JniInchiAtom, INCHI_STEREOTYPE, INCHI_PARITY)'
     */
    @Test
    public void testJniInchiStereo0D() {
        JniInchiStereo0D stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.ODD);
        Assert.assertEquals(atC, stereo.getCentralAtom());
        JniInchiAtom[] neighbors = stereo.getNeighbors();
        Assert.assertEquals(at0, neighbors[0]);
        Assert.assertEquals(at1, neighbors[1]);
        Assert.assertEquals(at2, neighbors[2]);
        Assert.assertEquals(at3, neighbors[3]);
        Assert.assertEquals(INCHI_STEREOTYPE.TETRAHEDRAL, stereo.getStereoType());
        Assert.assertEquals(INCHI_PARITY.ODD, stereo.getParity());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.setDisconnectedParity(INCHI_PARITY)'
     */
    @Test
    public void testSetDisconnectedParity() {
        JniInchiStereo0D stereo = getTestStereo0D();
        Assert.assertEquals(INCHI_PARITY.NONE, stereo.getDisconnectedParity());
        stereo.setDisconnectedParity(INCHI_PARITY.EVEN);
        Assert.assertEquals(INCHI_PARITY.EVEN, stereo.getDisconnectedParity());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.getCentralAtom()'
     */
    @Test
    public void testGetCentralAtom() {
        JniInchiStereo0D stereo = new JniInchiStereo0D(null, null, null, null, null, null, null);
        Assert.assertNull(stereo.getCentralAtom());
        stereo = new JniInchiStereo0D(atC, null, null, null, null, null, null);
        Assert.assertEquals(atC, stereo.getCentralAtom());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.getNeighbors()'
     */
    @Test
    public void testGetNeighbors() {
        JniInchiStereo0D stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.EVEN);
        JniInchiAtom[] neighbours = {at0, at1, at2, at3};
        Assert.assertEquals(neighbours, stereo.getNeighbors());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.getParity()'
     */
    @Test
    public void testGetParity() {
        JniInchiStereo0D stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.EVEN);
        Assert.assertEquals(INCHI_PARITY.EVEN, stereo.getParity());
        stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.ODD);
        Assert.assertEquals(INCHI_PARITY.ODD, stereo.getParity());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.getStereoType()'
     */
    @Test
    public void testGetStereoType() {
        JniInchiStereo0D stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.EVEN);
        Assert.assertEquals(INCHI_STEREOTYPE.TETRAHEDRAL, stereo.getStereoType());
        stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.ALLENE, INCHI_PARITY.EVEN);
        Assert.assertEquals(INCHI_STEREOTYPE.ALLENE, stereo.getStereoType());
    }
}
