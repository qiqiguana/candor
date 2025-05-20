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

    /**
     * Provide test structure.
     *
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
}

/**
 * Encapsulates properties of InChI Atom.  See <tt>inchi_api.h</tt>.
 *
 * @author Sam Adams
 */
public class JniInchiAtom {

    /**
     * Sets number of implicit hydrogens on atom. If set to -1, InChI will add
     * implicit H automatically.
     *
     * @param n Number of implicit hydrogen
     */
    public void setImplicitH(final int n);
}

/**
 * Encapsulates properties of InChI Bond.  See <tt>inchi_api.h</tt>.
 *
 * @author Sam Adams
 */
public class JniInchiBond {

    /**
     * Set 2D stereo definition.
     *
     * @param stereo Bond 2D stereo definition
     */
    public void setStereoDefinition(INCHI_BOND_STEREO stereo);
}

/**
 * @author Sam Adams
 */
public class JniInchiStructure {

    /**
     * Adds atom to inchi molecule.
     *
     * @param atom Atom to add
     * @return Added atom
     */
    @SuppressWarnings("unchecked")
    public JniInchiAtom addAtom(JniInchiAtom atom);

    /**
     * Adds bond to inchi molecule.
     *
     * @param bond  Bond to add
     * @return      Added bond
     */
    @SuppressWarnings("unchecked")
    public JniInchiBond addBond(JniInchiBond bond);
}
