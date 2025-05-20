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
 * Enumeration of InChI 0D parity types.
 * Corresponds to <tt>inchi_StereoType0D</tt> in <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public enum INCHI_STEREOTYPE {


    /**
     * None.
     */
    NONE(0),

    /**
     * Stereogenic bond >A=B< or cumulene >A=C=C=B<.
     */
    DOUBLEBOND(1),

    /**
     * Tetrahedral atom.
     */
    TETRAHEDRAL(2),

    /**
     * Allene.
     */
    ALLENE(3);



    /**
     * Internal InChI index (from inchi_api.h).
     */
    private final int indx;

    /**
     * Constructor.
     */
    private INCHI_STEREOTYPE(final int indx) {
        this.indx = indx;
    }

    public int getIndx() {
        return indx;
    }

    public static INCHI_STEREOTYPE getValue(int type) {
		switch (type) {
			case 0:
                return NONE;
			case 1:
                return DOUBLEBOND;
            case 2:
                return TETRAHEDRAL;
			case 3:
                return ALLENE;
			default:
				return null;
		}
	}
    
}
