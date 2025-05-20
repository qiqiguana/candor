package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Adams
 */
public class JniInchiStructure {

    @SuppressWarnings("unchecked")
    public JniInchiStereo0D addStereo0D(JniInchiStereo0D parity) {
        stereoList.add(parity);
        return parity;
    }
}
