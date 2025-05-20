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

import java.util.List;

/**
 * Encapsulates input for InChI to structure conversion.
 * @author Sam Adams
 */
public class JniInchiInputInchi {

    /**
     * InChI ASCIIZ string to be converted to a strucure
     */
    protected String inchiString;

    /**
     * InChI options: space-delimited
     */
    protected String options;

    /**
     * Constructor.
     * @param inchi InChI string
     * @param opts  Options
     */
    public JniInchiInputInchi(final String inchi) {
        this.inchiString = inchi;
        this.options = "";
    }

    /**
     * Constructor.
     * @param inchi    InChI string
     * @param opts    Options
     */
    public JniInchiInputInchi(final String inchi, final String opts) throws JniInchiException {
        this.inchiString = inchi;
        this.options = JniInchiWrapper.checkOptions(opts);
    }

    /**
     * Constructor.
     * @param inchi    InChI string
     * @param opts    Options
     */
    public JniInchiInputInchi(final String inchi, List opts) throws JniInchiException {
        this.inchiString = inchi;
        this.options = JniInchiWrapper.checkOptions(opts);
    }

    /**
     * Returns options string.
     * @return
     */
    public String getOptions() {
        return options;
    }

    /**
     * Returns options string.
     * @return
     */
    public String getInchi() {
        return inchiString;
    }
}
