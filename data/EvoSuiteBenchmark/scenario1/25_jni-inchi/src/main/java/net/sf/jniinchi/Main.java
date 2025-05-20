package net.sf.jniinchi;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Simple test class, for debugging purposes.
 *
 * @author sea36
 */
public class Main {

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
}
