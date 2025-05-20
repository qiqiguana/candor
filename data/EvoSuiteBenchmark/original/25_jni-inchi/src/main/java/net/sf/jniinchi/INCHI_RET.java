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
 * <p>Type-safe enumeration of InChI return codes.
 *
 * <p>InChI library return values:<br>
 * <tt>
 * SKIP     (-2)    Not used in InChI library<br>
 * EOF      (-1)    No structural data has been provided<br>
 * OKAY     (0)     Success, no errors or warnings<br>
 * WARNING  (1)     Success, warning(s) issued<br>
 * ERROR    (2)     Error: no InChI has been created<br>
 * FATAL    (3)     Severe error: no InChI has been created (typically,
 *                  memory allocation failure)<br>
 * UNKNOWN  (4)     Unknown program error<br>
 * BUSY     (5)     Previous call to InChI has not returned yet<br>
 * </tt>
 * <p>See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public enum INCHI_RET {

    /**
     * Not used in InChI library.
     */
    SKIP(-2),

    /**
     * No structural data has been provided.
     */
    EOF(-1),

    /**
     * Success; no errors or warnings.
     */
    OKAY(0),

    /**
     * Success; warning(s) issued.
     */
    WARNING(1),

    /**
     * Error: no InChI has been created.
     */
    ERROR(2),

    /**
     * Severe error: no InChI has been created (typically, memory allocation failure).
     */
    FATAL(3),

    /**
     * Unknown program error.
     */
    UNKNOWN(4),

    /**
     * Previuos call to InChI has not returned yet.
     */
    BUSY(5);



    /**
     * Internal InChI index (from inchi_api.h).
     */
    private final int indx;

    /**
     * Constructor.
     */
    private INCHI_RET(final int indx) {
        this.indx = indx;
    };

    public int getIndx() {
        return indx;
    }

    public static INCHI_RET getValue(int ret) {
        switch(ret) {
            case -2:
                return SKIP;
            case -1:
                return EOF;
            case 0:
                return OKAY;
            case 1:
                return WARNING;
            case 2:
                return ERROR;
            case 3:
                return FATAL;
            case 4:
                return UNKNOWN;
            case 5:
                return BUSY;
            default:
                return null;
        }
    }
    
}
