package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Adams
 */
public class JniInchiStructure {

    public JniInchiAtom getAtom(final int i) {
        return (JniInchiAtom) atomList.get(i);
    }
}
