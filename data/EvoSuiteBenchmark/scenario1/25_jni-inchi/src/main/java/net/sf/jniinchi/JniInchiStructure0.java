package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Adams
 */
public class JniInchiStructure {

    @SuppressWarnings("unchecked")
    public JniInchiAtom addAtom(JniInchiAtom atom) {
        atomList.add(atom);
        return atom;
    }
}
