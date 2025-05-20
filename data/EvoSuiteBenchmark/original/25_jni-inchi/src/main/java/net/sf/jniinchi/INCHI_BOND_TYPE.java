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
 * Enumeration of InChI bond type definitions.
 * Corresponds to <tt>inchi_BondType</tt> in <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public enum INCHI_BOND_TYPE {


    NONE(0),

    /**
     * Single bond.
     */
    SINGLE(1),

    /**
     * Double bond.
     */
    DOUBLE(2),

    /**
     * Triple bond.
     */
    TRIPLE(3),

    /**
     * Alternating (single-double) bond. Avoid where possible.
     */
    ALTERN(4);



    /**
     * Internal InChI index (from inchi_api.h).
     */
    private final int indx;

    /**
     * Constructor.
     */
    private INCHI_BOND_TYPE(final int indx) {
        this.indx = indx;
    }

    public int getIndx() {
        return indx;
    }

    public static INCHI_BOND_TYPE getValue(int btype) {
        switch (btype) {
            case 0:
                return NONE;
            case 1:
                return SINGLE;
            case 2:
                return DOUBLE;
            case 3:
                return TRIPLE;
            case 4:
                return ALTERN;
            default:
                return null;
        }
    }
    
}
