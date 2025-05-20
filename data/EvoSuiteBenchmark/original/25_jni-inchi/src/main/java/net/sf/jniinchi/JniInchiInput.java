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
 * Encapsulates structure input for InChI generation.
 * @author Sam Adams
 */
public class JniInchiInput extends JniInchiStructure {

    /**
     * Options string,
     */
    protected String options;

    /**
     * Constructor.
     * @throws JniInchiException
     */
    public JniInchiInput() {
        this.options = "";
    }

    /**
     * Constructor.
     * @param opts    Options string.
     * @throws JniInchiException
     */
    public JniInchiInput(final String opts) throws JniInchiException {
        this.options = opts == null ? "" : JniInchiWrapper.checkOptions(opts);
    }

    /**
     * Constructor.
     * @param opts    List of options.
     * @throws JniInchiException
     */
    public JniInchiInput(List opts) throws JniInchiException {
        this.options = JniInchiWrapper.checkOptions(opts);
    }

    /**
     * Constructor.
     * @throws JniInchiException
     */
    public JniInchiInput(JniInchiStructure struct) {
        this();
        setStructure(struct);
    }

    /**
     * Constructor.
     * @throws JniInchiException
     */
    public JniInchiInput(JniInchiStructure struct, String opts) throws JniInchiException {
        this(opts);
        setStructure(struct);
    }

    /**
     * Returns options string.
     * @return
     */
    public String getOptions() {
        return options;
    }
}
