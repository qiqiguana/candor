package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Adams
 */
public class JniInchiStructure {

    /**
     * List of atoms.
     */
    private List atomList = new ArrayList();

    /**
     * List of bonds.
     */
    private List bondList = new ArrayList();

    /**
     * List of stero parities.
     */
    private List stereoList = new ArrayList();

    /**
     * Returns number of atoms in structure.
     */
    public int getNumAtoms();

    /**
     * Returns number of bonds in structure.
     */
    public int getNumBonds();

    /**
     * Returns number of stereo parities in strucuture.
     */
    public int getNumStereo0D();

    /**
     * Adds atom to inchi molecule.
     *
     * @param atom  Atom to add
     * @return      Added atom
     */
    @SuppressWarnings("unchecked")
    public JniInchiAtom addAtom(JniInchiAtom atom);

    /**
     * Convenience method to add multiple atoms to molecule.
     * @param atoms
     */
    public void addAtoms(JniInchiAtom... atoms);

    /**
     * Adds bond to inchi molecule.
     *
     * @param bond  Bond to add
     * @return      Added bond
     */
    @SuppressWarnings("unchecked")
    public JniInchiBond addBond(JniInchiBond bond);

    /**
     * Convenience method to add multiple bonds to molecule.
     * @param bonds
     */
    public void addBonds(JniInchiBond... bonds);

    /**
     * Adds 0D stereo parity to inchi molecule.
     *
     * @param parity  Parity to add
     * @return        Added parity
     */
    @SuppressWarnings("unchecked")
    public JniInchiStereo0D addStereo0D(JniInchiStereo0D parity);

    /**
     * Returns atom from structure.
     * @param i    Index of atom to return.
     * @return
     */
    public JniInchiAtom getAtom(final int i);

    /**
     * Returns bond from structure.
     * @param i    Index of bond to return.
     * @return
     */
    public JniInchiBond getBond(final int i);

    /**
     * Returns stereo parity from structure.
     * @param i    Index of stereo parity to return.
     * @return
     */
    public JniInchiStereo0D getStereo0D(final int i);

    public void setStructure(JniInchiStructure structure);

    int getAtomIndex(JniInchiAtom atom);

    int getStereo0DIndex(JniInchiStereo0D stereo);
}
