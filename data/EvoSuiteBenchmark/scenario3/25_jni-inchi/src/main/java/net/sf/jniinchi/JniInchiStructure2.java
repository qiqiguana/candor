package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Adams
 */
public class JniInchiStructure {

    /**
     * Adds 0D stereo parity to inchi molecule.
     *
     * @param parity Parity to add
     * @return Added parity
     */
    @SuppressWarnings("unchecked")
    public JniInchiStereo0D addStereo0D(JniInchiStereo0D parity);
}
