package net.sf.jniinchi;

/**
 * Encapsulates properites of InChI Stereo Parity.  See <tt>inchi_api.h</tt>.
 *
 * @author Sam Adams
 */
public class JniInchiStereo0D {

    public static JniInchiStereo0D createNewTetrahedralStereo0D(final JniInchiAtom atC, final JniInchiAtom at0, final JniInchiAtom at1, final JniInchiAtom at2, final JniInchiAtom at3, INCHI_PARITY parity) {
        JniInchiStereo0D stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, parity);
        return stereo;
    }
}
