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

import org.junit.Test;

/**
 * @author sea36
 */
public class TestExamples {

    @Test
    public void testExampleStructure2InchiDiChloroethene() throws JniInchiException {

        // START SNIPPET: structure2inchi-dichloroethene
        // Example input - 2D E-1,2-dichloroethene
        JniInchiInput input = new JniInchiInput();
        //
        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(2.866, -0.250, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(3.732, 0.250, 0.000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(2.000, 2.500, 0.000, "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(4.598, -0.250, 0.000, "Cl"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);
        //
        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a4, INCHI_BOND_TYPE.SINGLE));
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        // END SNIPPET: structure2inchi-dichloroethene

    }


    @Test
    public void testExampleStructure2InchiAlanine() throws JniInchiException {

        // START SNIPPET: structure2inchi-alanine
        // Example input - 0D D-Alanine
        JniInchiInput input = new JniInchiInput();
        //
        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
        JniInchiAtom a7 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "H"));
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);
        //
        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a7, INCHI_BOND_TYPE.SINGLE));
        //
        // Add stereo parities
        input.addStereo0D(JniInchiStereo0D
                .createNewTetrahedralStereo0D(a1, a3, a4, a7, a2, INCHI_PARITY.EVEN));
        //
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        // END SNIPPET: structure2inchi-alanine

    }


    @Test
    public void testInchi2InchiHydrogen() throws JniInchiException {

        // START SNIPPET: inchi2inchi-hydrogen
        // Input InChI with fixed-hydrogen layer
        String inchiIn = "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/"
            + "t2-/m0/s1/f/h5H";
        //
        JniInchiOutput output = JniInchiWrapper.getInchiFromInchi(
                new JniInchiInputInchi(inchiIn));
        String inchiOut = output.getInchi();
        // Output InChI:
        //   InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1
        // END SNIPPET: inchi2inchi-hydrogen

    }


    @Test
    public void testInchi2InchiCompress() throws JniInchiException {

        // START SNIPPET: inchi2inchi-compress
        String inchi = "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)";
        //
        // Compress InChI
        JniInchiOutput cout = JniInchiWrapper.getInchiFromInchi(
                new JniInchiInputInchi(inchi, "-compress"));
        String compressedInchi = cout.getInchi();
        // compressedInchi = InChI=1/C3H7NO2/cABBCC/hB1D2A3,1EF
        //
        // Uncompress InChI
        JniInchiOutput ucout = JniInchiWrapper.getInchiFromInchi(
                new JniInchiInputInchi(compressedInchi));
        String uncompressedInchi = ucout.getInchi();
        // uncompressedInchi = InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)
        // END SNIPPET: inchi2inchi-compress

    }


    @Test
    public void testInchi2Structure() throws JniInchiException {

        // START SNIPPET: inchi2structure
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C2H6/c1-2/h1-2H3");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        //
        INCHI_RET retStatus = output.getReturnStatus();
        int nat = output.getNumAtoms();
        int nbo = output.getNumBonds();
        int nst = output.getNumStereo0D();
        //
        JniInchiAtom at0 = output.getAtom(0);
        // END SNIPPET: inchi2structure

    }

    @Test
    public void testInchi2InchiKey() throws JniInchiException {

        // START SNIPPET: inchi2inchikey
        JniInchiOutputKey output = JniInchiWrapper.getInchiKey("InChI=1S/C2H6/c1-2/h1-2H3");
        String key = output.getKey();
        // END SNIPPET: inchi2inchikey

    }

    @Test
    public void testCheckInchi() throws JniInchiException {

        // START SNIPPET: checkinchi
        boolean strict = true;
        INCHI_STATUS status = JniInchiWrapper.checkInchi("InChI=1S/C2H6/c1-2/h1-2H3", strict);
        // END SNIPPET: checkinchi

    }

    @Test
    public void testCheckInchiKey() throws JniInchiException {

        // START SNIPPET: checkinchikey
        INCHI_KEY_STATUS status = JniInchiWrapper.checkInchiKey("OTMSDBZUPAUEDD-UHFFFAOYSA-N");
        // END SNIPPET: checkinchikey

    }

    @Test
    public void testAuxinfo2Input() throws JniInchiException {

        // START SNIPPET: auxinfo2input
        JniInchiInputData data = JniInchiWrapper.getInputFromAuxInfo("AuxInfo=1/1/N:4,1,2,3,5,6/"
                +"E:(5,6)/it:im/rA:6CCNCOO/rB:s1;N1;s1;s2;d2;/rC:264,968,0;295,985,0;233,986,0;"
                +"264,932,0;326,967,0;295,1021,0;");
        INCHI_RET ret = data.getReturnValue();
        JniInchiInput input = data.getInput();
        // END SNIPPET: auxinfo2input

    }

}
