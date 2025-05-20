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


public class LoadNativeLibraryException extends JniInchiException {

private static final long serialVersionUID = 2L;

    /**
     * Constructor.
     */
    public LoadNativeLibraryException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message
     */
    public LoadNativeLibraryException(final String message) {
        super(message);
    }

	public LoadNativeLibraryException(Throwable ex) {
		super(ex);
	}

}
