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

public class TestJniInchiStructure {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumAtoms()'
     */
    @Test
    public void testGetNumAtoms() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumAtoms());
        structure.addAtom(TestJniInchiAtom.getNewTestAtom());
        Assert.assertEquals(1, structure.getNumAtoms());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumBonds()'
     */
    @Test
    public void testGetNumBonds() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumBonds());
        structure.addBond(TestJniInchiBond.getTestBond());
        Assert.assertEquals(1, structure.getNumBonds());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumStereo0D()'
     */
    @Test
    public void testGetNumStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumStereo0D());
        structure.addStereo0D(TestJniInchiStereo0D.getTestStereo0D());
        Assert.assertEquals(1, structure.getNumStereo0D());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addAtom(JniInchiAtom)'
     */
    @Test
    public void testAddAtom() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumAtoms());
        structure.addAtom(TestJniInchiAtom.getNewTestAtom());
        Assert.assertEquals(1, structure.getNumAtoms());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addBond(JniInchiBond)'
     */
    @Test
    public void testAddBond() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumBonds());
        structure.addBond(TestJniInchiBond.getTestBond());
        Assert.assertEquals(1, structure.getNumBonds());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addParity(JniInchiStereo0D)'
     */
    @Test
    public void testAddStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumStereo0D());
        structure.addStereo0D(TestJniInchiStereo0D.getTestStereo0D());
        Assert.assertEquals(1, structure.getNumStereo0D());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getAtom(int)'
     */
    @Test
    public void testGetAtom() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiAtom atom = TestJniInchiAtom.getNewTestAtom();
        structure.addAtom(atom);
        Assert.assertEquals(atom, structure.getAtom(0));
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getBond(int)'
     */
    @Test
    public void testGetBond() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiBond bond = TestJniInchiBond.getTestBond();
        structure.addBond(bond);
        Assert.assertEquals(bond, structure.getBond(0));
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getStereo0D(int)'
     */
    @Test
    public void testGetStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiStereo0D stereo = TestJniInchiStereo0D.getTestStereo0D();
        structure.addStereo0D(stereo);
        Assert.assertEquals(stereo, structure.getStereo0D(0));
    }

}
