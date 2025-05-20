package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Adams
 */
public class JniInchiStructure {

    @SuppressWarnings("unchecked")
    public JniInchiBond addBond(JniInchiBond bond) {
        bondList.add(bond);
        return bond;
    }
}
