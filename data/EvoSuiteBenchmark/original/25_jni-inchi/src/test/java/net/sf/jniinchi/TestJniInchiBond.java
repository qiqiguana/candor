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

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiBond extends TestCase {

    private static JniInchiAtom atO = new JniInchiAtom(0, 0, 0, "O");
    private static JniInchiAtom atT = new JniInchiAtom(1.21, 0, 0, "O");

    protected static JniInchiBond getTestBond() {
        return new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.JniInchiBond(JniInchiAtom, JniInchiAtom, INCHI_BOND_TYPE, INCHI_BOND_STEREO)'
     */
    @Test
    public void testJniInchiBondJniInchiAtomJniInchiAtomINCHI_BOND_TYPEINCHI_BOND_STEREO() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE, INCHI_BOND_STEREO.DOUBLE_EITHER);
        Assert.assertEquals(atO, bond.getOriginAtom());
        Assert.assertEquals(atT, bond.getTargetAtom());
        Assert.assertEquals(INCHI_BOND_TYPE.DOUBLE, bond.getBondType());
        Assert.assertEquals(INCHI_BOND_STEREO.DOUBLE_EITHER, bond.getBondStereo());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.JniInchiBond(JniInchiAtom, JniInchiAtom, INCHI_BOND_TYPE)'
     */
    @Test
    public void testJniInchiBondJniInchiAtomJniInchiAtomINCHI_BOND_TYPE() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(atO, bond.getOriginAtom());
        Assert.assertEquals(atT, bond.getTargetAtom());
        Assert.assertEquals(INCHI_BOND_TYPE.DOUBLE, bond.getBondType());
        Assert.assertEquals(INCHI_BOND_STEREO.NONE, bond.getBondStereo());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.setStereoDefinition(INCHI_BOND_STEREO)'
     */
    @Test
    public void testSetStereoDefinition() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(INCHI_BOND_STEREO.NONE, bond.getBondStereo());
        bond.setStereoDefinition(INCHI_BOND_STEREO.DOUBLE_EITHER);
        Assert.assertEquals(INCHI_BOND_STEREO.DOUBLE_EITHER, bond.getBondStereo());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.getOriginAtom()'
     */
    @Test
    public void testGetOriginAtom() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(atO, bond.getOriginAtom());
        bond = new JniInchiBond(atT, atO, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(atT, bond.getOriginAtom());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.getTargetAtom()'
     */
    @Test
    public void testGetTargetAtom() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(atT, bond.getTargetAtom());
        bond = new JniInchiBond(atT, atO, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(atO, bond.getTargetAtom());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.getBondType()'
     */
    @Test
    public void testGetBondType() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(INCHI_BOND_TYPE.DOUBLE, bond.getBondType());
        bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.SINGLE);
        Assert.assertEquals(INCHI_BOND_TYPE.SINGLE, bond.getBondType());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.getBondStereo()'
     */
    @Test
    public void testGetBondStereo() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE, INCHI_BOND_STEREO.DOUBLE_EITHER);
        Assert.assertEquals(INCHI_BOND_STEREO.DOUBLE_EITHER, bond.getBondStereo());
        bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.SINGLE, INCHI_BOND_STEREO.SINGLE_1UP);
        Assert.assertEquals(INCHI_BOND_STEREO.SINGLE_1UP, bond.getBondStereo());
    }

}
