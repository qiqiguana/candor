package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Adams
 */
public class JniInchiStructure {

    public JniInchiBond getBond(final int i) {
        return (JniInchiBond) bondList.get(i);
    }
}
