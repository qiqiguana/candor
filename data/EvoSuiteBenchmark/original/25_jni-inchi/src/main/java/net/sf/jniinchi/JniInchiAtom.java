/*
 * Copyright 2006-2011 Sam Adams <sea36 at users.sourceforge.net>
 *
 * This file is part of JNI-InChI.
 *
 * JNI-InChI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JNI-InChI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JNI-InChI.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jniinchi;

/**
 * Encapsulates properties of InChI Atom.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class JniInchiAtom {

    /**
     * Indicates relative rather than absolute isotopic mass. Value
     * from inchi_api.h.
     */
    protected static final int ISOTOPIC_SHIFT_FLAG = 10000;

    /**
     * Atom x-coordinate.
     */
    private double x;

    /**
     * Atom y-coordinate.
     */
    private double y;

    /**
     * Atom z-coordinate.
     */
    private double z;

    /**
     * Chemical element symbol eg C, O, Fe, Hg.
     */
    private String elname;

    /**
     * Number of implicit hydrogens on atom. If set to -1, InChI will add
     * implicit H automatically.
     */
    private int implicitH = -1;

    /**
     * Number of implicit protiums (isotopic 1-H) on atom.
     */
    private int implicitP = 0;

    /**
     * Number of implicit deuteriums (isotopic 2-H) on atom.
     */
    private int implicitD = 0;

    /**
     * Number of implicit tritiums (isotopic 3-H) on atom.
     */
    private int implicitT = 0;

    /**
     * Mass of isotope. If set to 0, no isotopic mass set; otherwise, isotopic
     * mass, or ISOTOPIC_SHIFT_FLAG + (mass - average atomic mass).
     */
    private int isotopic_mass = 0;

    /**
     * Radical status of atom.
     */
    private INCHI_RADICAL radical = INCHI_RADICAL.NONE;

    /**
     * Charge on atom.
     */
    private int charge = 0;

    /**
     * <p>Create new atom.
     *
     * <p>Coordinates and element symbol must be set (unknown
     * coordinates/dimensions should be set to zero).  All other
     * parameters are initialised to default values:
     * <p>
     * <tt>
     *    Num Implicit H = 0<br>
     *    Num Implicit 1H = 0<br>
     *    Num Implicit 2H = 0<br>
     *    Num Implicit 3H = 0<br>
     *    Isotopic mass = 0 (non isotopic)<br>
     *    Radical status = NONE  (radical status not defined)
     * </tt>
     *
     * @param x     x-coordinate
     * @param y     y-coordinate
     * @param z     z-coordinate
     * @param el    Chemical element symbol
     * @throws NullPointerException - if the element symbol is null.
     */
    public JniInchiAtom(final double x, final double y, final double z, final String el) {
        this.x = x;
        this.y = y;
        this.z = z;

        if (el == null) {
        	throw new NullPointerException("Chemical element must not be null");
        }
        this.elname = el;
    }


    /**
     * Convenience method to create a new atom with zero coordinates.
     * @param el
     */
    public JniInchiAtom(final String el) {
        this(0.0, 0.0, 0.0, el);
    }


    /**
     * Sets charge on atom.
     *
     * @param charge
     */
    public void setCharge(final int charge) {
        this.charge = charge;
    }

    /**
     * Sets radical status of atom.
     *
     * @param radical
     */
    public void setRadical(final INCHI_RADICAL radical) {
        this.radical = radical;
    }

    /**
     * Sets isotopic mass. If set to 0, non-isotopic.
     *
     * @param mass  Isotopic mass
     */
    public void setIsotopicMass(final int mass) {
        this.isotopic_mass = mass;
    }

    /**
     * Sets isotopic mass, relative to standard mass.
     *
     * @param shift  Isotopic mass minus average atomic mass
     */
    public void setIsotopicMassShift(final int shift) {
        this.isotopic_mass = ISOTOPIC_SHIFT_FLAG + shift;
    }

    /**
     * Sets number of implicit hydrogens on atom. If set to -1, InChI will add
     * implicit H automatically.
     *
     * @param n  Number of implicit hydrogen
     */
    public void setImplicitH(final int n) {
        this.implicitH = n;
    }

    /**
     * Sets number of implicit protium (1H) on atom.
     * @param n  Number of implicit protium
     */
    public void setImplicitProtium(final int n) {
        this.implicitP = n;
    }

    /**
     * Sets number of implicit deuterium (2H) on atom.
     *
     * @param n  Number of implicit deuterium
     */
    public void setImplicitDeuterium(final int n) {
        this.implicitD = n;
    }

    /**
     * Sets number of implicit tritium (3H) on atom.
     * @param n  Number of implicit tritium
     */
    public void setImplicitTritium(final int n) {
        this.implicitT = n;
    }

    /**
     * Returns chemical element symbol of atom.
     * @return
     */
    public String getElementType() {
        return elname;
    }

    /**
     * Returns charge on atom.
     * @return
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Returns radical state of atom.
     * @return
     */
    public INCHI_RADICAL getRadical() {
        return radical;
    }
    
    /**
     * Returns atom's X-coordinate.
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     * Returns atom's Y-coordinate.
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Returns atom's Z-coordinate.
     * @return
     */
    public double getZ() {
        return z;
    }

    /**
     * Returns number of implicit hydrogens on atom.
     * @return
     */
    public int getImplicitH() {
        return implicitH;
    }

    /**
     * Returns number of implicit protiums (1H) on atom.
     * @return
     */
    public int getImplicitProtium() {
        return implicitP;
    }

    /**
     * Returns number of implicit deuteriums (2H) on atom.
     * @return
     */
    public int getImplicitDeuterium() {
        return implicitD;
    }

    /**
     * Returns number of implicit tritiums (3H) on atom.
     * @return
     */
    public int getImplicitTritium() {
        return implicitT;
    }

    /**
     * Returns isotopic mass of atom.
     * @return
     */
    public int getIsotopicMass() {
        return isotopic_mass;
    }

    
    int getInchiRadical() {
    	return radical.getIndx();
    }
    
    void setInchiRadical(int radical) {
    	this.radical = INCHI_RADICAL.getValue(radical);
    }

    
    /**
     * Generates string representation of information on atom,
     * for debugging purposes.
     */
    public String getDebugString() {
        return "InChI Atom: "
            + elname
            + " [" + x + "," + y + "," + z + "] "
            + "Charge:" + charge + " // "
            + "Iso Mass:" + isotopic_mass + " // "
            + "Implicit H:" + implicitH
            + " P:" + implicitP
            + " D:" + implicitD
            + " T:" + implicitT
            + " // Radical: " + radical;
    }

    /**
     * Outputs information on atom, for debugging purposes.
     */
    public void debug() {
        System.out.println(getDebugString());
    }
}
