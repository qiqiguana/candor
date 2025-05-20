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
 * Enumeration of InChI radical definitions.
 * Corresponds to <tt>inchi_Radical</tt> in <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public enum INCHI_RADICAL {

    /**
     * No radical status recorded.
     */
    NONE(0),

    /**
     * Singlet.
     */
    SINGLET(1),

    /**
     * Doublet.
     */
    DOUBLET(2),

    /**
     * Triplet.
     */
    TRIPLET(3);


    /**
     * Internal InChI index (from inchi_api.h).
     */
    private final int indx;

    /**
     * Constructor.
     * @param indx  index of radical.
     */
    private INCHI_RADICAL(final int indx) {
        this.indx = indx;
    }

   /**
    * Returns index.
    * @return
    */
    public int getIndx() {
        return indx;
    }

    public static INCHI_RADICAL getValue(int rad) {
        switch (rad) {
            case 0:
                return NONE;
            case 1:
                return SINGLET;
            case 2:
                return DOUBLET;
            case 3:
                return TRIPLET;
            default:
                return null;
        }
    }
    
}
