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
 * Enumeration of InChI 2D stereo definitions.
 * Corresponds to <tt>inchi_BondStereo2D</tt> in <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public enum INCHI_BOND_STEREO {


    /**
     * No 2D stereo definition recorded.
     */
    NONE(0),

    /**
     * Stereocenter-related; positive: the sharp end points to this atom.
     */
    SINGLE_1UP(1),

    /**
     * Stereocenter-related; positive: the sharp end points to this atom.
     */
    SINGLE_1EITHER(4),

    /**
     * Stereocenter-related; positive: the sharp end points to this atom.
     */
    SINGLE_1DOWN(6),

    /**
     * Stereocenter-related; negative: the sharp end points to the opposite atom.
     */
    SINGLE_2UP(-1),

    /**
     * Stereocenter-related; negative: the sharp end points to the opposite atom.
     */
    SINGLE_2EITHER(-4),

    /**
     * Stereocenter-related; negative: the sharp end points to the opposite atom.
     */
    SINGLE_2DOWN(-6),

    /**
     * Unknown stereobond geometry.
     */
    DOUBLE_EITHER(3);




    /**
     * Internal InChI index (from inchi_api.h).
     */
    private final int indx;

    /**
     * Constructor.
     */
    private INCHI_BOND_STEREO(final int indx) {
        this.indx = indx;
    }

    public int getIndx() {
        return indx;
    }


    public static INCHI_BOND_STEREO getValue(int bster) {
        switch (bster) {
            case 0:
                return NONE;
            case 1:
                return SINGLE_1UP;
            case 4:
                return SINGLE_1EITHER;
            case 6:
                return SINGLE_1DOWN;
            case 3:
                return DOUBLE_EITHER;
            case -1:
                return SINGLE_2UP;
            case -4:
                return SINGLE_2EITHER;
            case -6:
                return SINGLE_2DOWN;
            default:
                return null;
        }
    }
    
}
