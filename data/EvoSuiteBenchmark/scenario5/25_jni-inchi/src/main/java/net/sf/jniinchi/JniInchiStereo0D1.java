package net.sf.jniinchi;

/**
 * Encapsulates properites of InChI Stereo Parity.  See <tt>inchi_api.h</tt>.
 *
 * @author Sam Adams
 */
public class JniInchiStereo0D {

    /**
     * <p>Convenience method for generating 0D stereo parities at stereogenic
     * double bonds.
     *
     * <p><b>Usage notes from <i>inchi_api.h</i>:</b>
     * <pre>
     *  =============================================
     *  stereogenic bond >A=B< or cumulene >A=C=C=B<
     *  =============================================
     *
     *                              neighbor[4]  : {#X,#A,#B,#Y} in this order
     *  X                           central_atom : NO_ATOM
     *   \            X      Y      type         : INCHI_StereoType_DoubleBond
     *    A==B         \    /
     *        \         A==B
     *         Y
     *
     *  parity= 'e'    parity= 'o'   unknown parity = 'u'
     *
     *  ==============================================
     *  Note. Correspondence to CML 0D stereo parities
     *  ==============================================
     *
     *  stereogenic double bond and (not yet defined in CML) cumulenes
     *  ==============================================================
     *  CML 'C' (cis)      <=> INCHI_PARITY_ODD
     *  CML 'T' (trans)    <=> INCHI_PARITY_EVEN
     * </pre>
     *
     * @param at0 Neighbour atom 0
     * @param at1 Neighbour atom 1
     * @param at2 Neighbour atom 2
     * @param at3 Neighbour atom 3
     * @param parity Parity
     * @return
     */
    public static JniInchiStereo0D createNewDoublebondStereo0D(final JniInchiAtom at0, final JniInchiAtom at1, final JniInchiAtom at2, final JniInchiAtom at3, final INCHI_PARITY parity) {
        JniInchiStereo0D stereo = new JniInchiStereo0D(null, at0, at1, at2, at3, INCHI_STEREOTYPE.DOUBLEBOND, parity);
        return stereo;
    }
}
