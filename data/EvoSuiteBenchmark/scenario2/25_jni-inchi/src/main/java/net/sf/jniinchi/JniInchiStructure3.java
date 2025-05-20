package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Adams
 */
public class JniInchiStructure {

    /**
     * Returns atom from structure.
     *
     * @param i Index of atom to return.
     * @return
     */
    public JniInchiAtom getAtom(final int i) {
        return (JniInchiAtom) atomList.get(i);
    }
}
