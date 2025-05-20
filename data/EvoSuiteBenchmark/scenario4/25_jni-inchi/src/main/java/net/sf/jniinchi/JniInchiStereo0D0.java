package net.sf.jniinchi;

/**
 * Encapsulates properites of InChI Stereo Parity.  See <tt>inchi_api.h</tt>.
 *
 * @author Sam Adams
 */
public class JniInchiStereo0D {

    /**
     * Indicates non-existent (central) atom. Value from inchi_api.h.
     */
    public static final int NO_ATOM = -1;

    /**
     * Neighbouring atoms.
     */
    private JniInchiAtom[] neighbors = new JniInchiAtom[4];

    /**
     * Central atom.
     */
    private JniInchiAtom centralAtom;

    /**
     * Stereo parity type.
     */
    private INCHI_STEREOTYPE type;

    /**
     * Parity.
     */
    private INCHI_PARITY parity;

    /**
     * Second parity (for disconnected systems).
     */
    private INCHI_PARITY disconParity = INCHI_PARITY.NONE;

    /**
     * Constructor.  See <tt>inchi_api.h</tt> for details of usage.
     *
     * @see createNewTetrahedralStereo0D()
     * @see createNewDoublebondStereo0D()
     *
     * @param atC    Central atom
     * @param at0    Neighbour atom 0
     * @param at1    Neighbour atom 1
     * @param at2    Neighbour atom 2
     * @param at3    Neighbour atom 3
     * @param type          Stereo parity type
     * @param parity    Parity
     */
    public JniInchiStereo0D(final JniInchiAtom atC, final JniInchiAtom at0, final JniInchiAtom at1, final JniInchiAtom at2, final JniInchiAtom at3, final INCHI_STEREOTYPE type, final INCHI_PARITY parity) {
    }

    JniInchiStereo0D(final JniInchiAtom atC, final JniInchiAtom at0, final JniInchiAtom at1, final JniInchiAtom at2, final JniInchiAtom at3, final int type, final int parity) {
    }

    /**
     * Set second parity (for disconnected systems)
     * @param parity
     */
    public void setDisconnectedParity(final INCHI_PARITY parity);

    /**
     * Returns central atom of stereo parity.
     * @return
     */
    public JniInchiAtom getCentralAtom();

    /**
     * Returns neighboring atoms of stereo parity.
     * @return
     */
    public JniInchiAtom[] getNeighbors();

    public JniInchiAtom getNeighbor(int i);

    /**
     * Returns parity.
     * @return
     */
    public INCHI_PARITY getParity();

    /**
     * Returns disconnected parity.
     * @return
     */
    public INCHI_PARITY getDisconnectedParity();

    /**
     * Returns type of stereochemistry.
     * @return
     */
    public INCHI_STEREOTYPE getStereoType();

    /**
     * Generates string representation of information on stereo parity,
     * for debugging purposes.
     */
    public String getDebugString();

    /**
     * Outputs information on stereo parity, for debugging purposes.
     */
    public void debug();

    /**
     * <p>Convenience method for generating 0D stereo parities at tetrahedral
     * atom centres.
     *
     * <p><b>Usage notes from <i>inchi_api.h</i>:</b>
     * <pre>
     *  4 neighbors
     *
     *           X                    neighbor[4] : {#W, #X, #Y, #Z}
     *           |                    central_atom: #A
     *        W--A--Y                 type        : INCHI_StereoType_Tetrahedral
     *           |
     *           Z
     *  parity: if (X,Y,Z) are clockwize when seen from W then parity is 'e' otherwise 'o'
     *  Example (see AXYZW above): if W is above the plane XYZ then parity = 'e'
     *
     *  3 neighbors
     *
     *             Y          Y       neighbor[4] : {#A, #X, #Y, #Z}
     *            /          /        central_atom: #A
     *        X--A  (e.g. O=S   )     type        : INCHI_StereoType_Tetrahedral
     *            \          \
     *             Z          Z
     *
     *  parity: if (X,Y,Z) are clockwize when seen from A then parity is 'e',
     *                                                         otherwise 'o'
     *  unknown parity = 'u'
     *  Example (see AXYZ above): if A is above the plane XYZ then parity = 'e'
     *  This approach may be used also in case of an implicit H attached to A.
     *
     *  ==============================================
     *  Note. Correspondence to CML 0D stereo parities
     *  ==============================================
     *  a list of 4 atoms corresponds to CML atomRefs4
     *
     *  tetrahedral atom
     *  ================
     *  CML atomParity > 0 <=> INCHI_PARITY_EVEN
     *  CML atomParity < 0 <=> INCHI_PARITY_ODD
     *
     *                               | 1   1   1   1  |  where xW is x-coordinate of
     *                               | xW  xX  xY  xZ |  atom W, etc. (xyz is a
     *  CML atomParity = determinant | yW  yX  yY  yZ |  'right-handed' Cartesian
     *                               | zW  zX  xY  zZ |  coordinate system)
     * </pre>
     *
     * @param atC    Central atom
     * @param at0    Neighbour atom 0
     * @param at1    Neighbour atom 1
     * @param at2    Neighbour atom 2
     * @param at3    Neighbour atom 3
     * @param parity Parity
     */
    public static JniInchiStereo0D createNewTetrahedralStereo0D(final JniInchiAtom atC, final JniInchiAtom at0, final JniInchiAtom at1, final JniInchiAtom at2, final JniInchiAtom at3, INCHI_PARITY parity);

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
     * @param at0    Neighbour atom 0
     * @param at1    Neighbour atom 1
     * @param at2    Neighbour atom 2
     * @param at3    Neighbour atom 3
     * @param parity Parity
     * @return
     */
    public static JniInchiStereo0D createNewDoublebondStereo0D(final JniInchiAtom at0, final JniInchiAtom at1, final JniInchiAtom at2, final JniInchiAtom at3, final INCHI_PARITY parity);

    int getInchiStereoType();

    int getInchiParity();
}
