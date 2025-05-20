package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

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
}
