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

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiAtom {

    protected static JniInchiAtom getNewTestAtom() {
        return new JniInchiAtom(1, 2, 3, "C");
    }


    /**
     * Test JniInchiAtom constructor.
     *
     */
    @Test
    public void testJniInchiAtomConstructor() {
        JniInchiAtom atom = getNewTestAtom();
        // Check configured parameters
        assertEquals(1.0, atom.getX(), 1E-6);
        assertEquals(2.0, atom.getY(), 1E-6);
        assertEquals(3.0, atom.getZ(), 1E-6);
        assertEquals("C", atom.getElementType());

        // Check default values set correctly
        assertEquals(0, atom.getCharge());
        assertEquals(-1, atom.getImplicitH());
        assertEquals(0, atom.getImplicitProtium());
        assertEquals(0, atom.getImplicitDeuterium());
        assertEquals(0, atom.getImplicitTritium());
        assertEquals(0, atom.getIsotopicMass());
        assertEquals(INCHI_RADICAL.NONE, atom.getRadical());
    }

    /**
     * Test setCharge.
     *
     */
    @Test
    public void testSetCharge() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setCharge(+1);
        assertEquals(+1, atom.getCharge());
    }

    /**
     * Test setRadical.
     *
     */
    @Test
    public void testSetRadical() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setRadical(INCHI_RADICAL.DOUBLET);
        assertEquals(INCHI_RADICAL.DOUBLET, atom.getRadical());
    }

    /**
     * Test setIsotopicMass.
     *
     */
    @Test
    public void testSetIsotopicMass() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setIsotopicMass(13);
        assertEquals(13, atom.getIsotopicMass());
    }

    /**
     * Test setIsotopicMassShift.
     *
     */
    @Test
    public void testSetIsotopicMassShift() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setIsotopicMassShift(+1);
        assertEquals(JniInchiAtom.ISOTOPIC_SHIFT_FLAG + 1, atom.getIsotopicMass());
    }

    /**
     * Test setImplicitH.
     *
     */
    @Test
    public void testSetImplictH() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setImplicitH(3);
        assertEquals(3, atom.getImplicitH());
    }

    /**
     * Test setImplicitProtium.
     *
     */
    @Test
    public void testSetImplictProtium() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setImplicitProtium(2);
        assertEquals(2, atom.getImplicitProtium());
    }

    /**
     * Test setImplicitDeuterium.
     *
     */
    @Test
    public void testSetImplictDeuterium() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setImplicitDeuterium(2);
        assertEquals(2, atom.getImplicitDeuterium());
    }

    /**
     * Test setImplicitTritium.
     *
     */
    @Test
    public void testSetImplictTritium() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setImplicitTritium(2);
        assertEquals(2, atom.getImplicitTritium());
    }

    /**
     * Test getElementType.
     *
     */
    @Test
    public void testGetElementType() {
        JniInchiAtom atom = getNewTestAtom();
        assertEquals("C", atom.getElementType());
    }

    /**
     * Test getCharge.
     *
     */
    @Test
    public void testGetCharge() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setCharge(+1);
        assertEquals(+1, atom.getCharge());
    }

    /**
     * Test getRadical.
     *
     */
    @Test
    public void testGetRadical() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setRadical(INCHI_RADICAL.TRIPLET);
        assertEquals(INCHI_RADICAL.TRIPLET, atom.getRadical());
    }

    /**
     * Test getX.
     *
     */
    @Test
    public void testGetX() {
        JniInchiAtom atom = getNewTestAtom();
        assertEquals(1.0, atom.getX(), 1E-6);
    }

    /**
     * Test getY.
     *
     */
    @Test
    public void testGetY() {
        JniInchiAtom atom = getNewTestAtom();
        assertEquals(2.0, atom.getY(), 1E-6);
    }

    /**
     * Test getZ.
     *
     */
    @Test
    public void testGetZ() {
        JniInchiAtom atom = getNewTestAtom();
        assertEquals(3.0, atom.getZ(), 1E-6);
    }

    /**
     * Test getImplicitH.
     *
     */
    @Test
    public void testGetImplicitH() {
        JniInchiAtom atom = getNewTestAtom();
        assertEquals(-1, atom.getImplicitH());
        atom.setImplicitH(3);
        assertEquals(3, atom.getImplicitH());
    }

    /**
     * Test getImplicitProtium.
     *
     */
    @Test
    public void testGetImplicitProtium() {
        JniInchiAtom atom = getNewTestAtom();
        assertEquals(0, atom.getImplicitProtium());
        atom.setImplicitProtium(2);
        assertEquals(2, atom.getImplicitProtium());
    }

    /**
     * Test getImplicitDeuterium.
     *
     */
    @Test
    public void testGetImplicitDeuterium() {
        JniInchiAtom atom = getNewTestAtom();
        assertEquals(0, atom.getImplicitDeuterium());
        atom.setImplicitDeuterium(2);
        assertEquals(2, atom.getImplicitDeuterium());
    }

    /**
     * Test getImplicitTritium.
     *
     */
    @Test
    public void testGetImplicitTritium() {
        JniInchiAtom atom = getNewTestAtom();
        assertEquals(0, atom.getImplicitTritium());
        atom.setImplicitTritium(2);
        assertEquals(2, atom.getImplicitTritium());
    }

    @Test
    public void testNullElementSymbol() {
        try {
            new JniInchiAtom(0, 0, 0, null);
            fail("Null element symbol");
        } catch (NullPointerException e) {
            // pass
        }
    }

}
