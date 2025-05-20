package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Adams
 */
public class JniInchiStructure {

    /**
     * Adds bond to inchi molecule.
     *
     * @param bond Bond to add
     * @return Added bond
     */
    @SuppressWarnings("unchecked")
    public JniInchiBond addBond(JniInchiBond bond);
}
