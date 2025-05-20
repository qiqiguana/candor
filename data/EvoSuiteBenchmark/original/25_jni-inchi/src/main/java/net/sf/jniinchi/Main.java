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


import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Simple test class, for debugging purposes.
 * @author sea36
 */
public class Main {

    /**
     * Provide test structure.
     * @return
     */
    public static JniInchiStructure getTestMolecule() {
        JniInchiStructure struct = new JniInchiStructure();
        JniInchiAtom a1 = struct.addAtom(new JniInchiAtom(264.0, 968.0, 0.0, "C"));
        JniInchiAtom a2 = struct.addAtom(new JniInchiAtom(295.0, 985.0, 0.0, "C"));
        JniInchiAtom a3 = struct.addAtom(new JniInchiAtom(233.0, 986.0, 0.0, "N"));
        JniInchiAtom a4 = struct.addAtom(new JniInchiAtom(264.0, 932.0, 0.0, "C"));
        JniInchiAtom a5 = struct.addAtom(new JniInchiAtom(326.0, 967.0, 0.0, "O"));
        JniInchiAtom a6 = struct.addAtom(new JniInchiAtom(295.0, 1021.0, 0.0, "O"));
        a1.setImplicitH(1);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);
        struct.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        struct.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE)).setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1DOWN);
        struct.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        struct.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        struct.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));

        return struct;
    }

    public static void runChecks() throws JniInchiException {
        System.err.println("Loading native code");
        System.err.println();

        try {
        	JniInchiWrapper.loadLibrary();

        	System.err.println();
        	System.err.println("Native code version: " + JniInchiWrapper.LibInchiGetVersion());
        	System.err.println();

            System.err.println("Running checks");
            System.err.println();

            System.err.println("Generating InChI from structure");

            JniInchiStructure mol = getTestMolecule();
            JniInchiOutput out1 = JniInchiWrapper.getInchi(new JniInchiInput(mol));
            if ("InChI=1S/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1".equals(out1.getInchi())) {
                System.err.println(" - OKAY");
            } else {
                System.err.println(" - ERROR");
                System.err.println(out1.getInchi());
            }

            System.err.println("Generating structure from InChI");
            JniInchiOutputStructure out2 = JniInchiWrapper.getStructureFromInchi(new JniInchiInputInchi("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)"));
            if (out2.getNumAtoms() == 6 && out2.getNumBonds() == 5) {
                System.err.println(" - OKAY");
            } else {
                System.err.println(" - ERROR");
            }

            System.err.println("Converting structure back to InChI");
            JniInchiOutput out3 = JniInchiWrapper.getInchi(new JniInchiInput(out2));
            if ("InChI=1S/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)".equals(out3.getInchi())) {
                System.err.println(" - OKAY");
            } else {
                System.err.println(" - ERROR");
                System.err.println(out3.getInchi());
            }
        } catch (Exception e) {
            System.err.println(" - ERROR");
            e.printStackTrace();
        }

//        System.err.println("Running memtest");
//        for (int i = 0; i < 1000000; i++) {
//            if (i % 1000 == 0) {
//                System.err.println(i+"\t"+((int)(Runtime.getRuntime().totalMemory()/1024)));
//            }
//            JniInchiStructure mol = getTestMolecule();
//            JniInchiWrapper.getInchi(new JniInchiInput(mol));
//        }

        System.err.println();
        System.err.println("Checks done.");
        System.err.println();
    }

    public static void main(final String[] args) throws Exception {
    	
        // Output header message
        System.err.println();
        System.err.println("** JniInchi debugger **");
        System.err.println();
        
        // Set up logging
		Logger root = Logger.getRootLogger();
		if (args.length == 1 && "-debug".equals(args[0])) {
			root.setLevel(Level.ALL);
		} else {
			root.setLevel(Level.INFO);
		}
		root.removeAllAppenders();
		
		PatternLayout layout = new PatternLayout("%-5p %c - %m%n");
		
		// Create console appender
		ConsoleAppender aconn = new ConsoleAppender(layout, ConsoleAppender.SYSTEM_ERR);
		aconn.setThreshold(Level.ALL);
		root.addAppender(aconn);

        runChecks();
    }
}
