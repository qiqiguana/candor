/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2000 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: DatabaseFilter.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
 *
 * Date         Author  Changes
 * $Date	    jimm    Created
 */


package org.exolab.jms.tools.admin;

import java.io.File;

import javax.swing.filechooser.FileFilter;


/**
 * A file filter used by the file chooser to resitrict displayed files
 * to files with a ".db" suffix only.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 */
public class DatabaseFilter extends FileFilter {

    /**
     * The default constructor does nothing.
     *
     */
    public DatabaseFilter() {

    }


    /**
     * All files in a directory are passed to this method to determine
     * if they should be displayed.
     * The method will allow the display of all directories and all files
     * with the "*.db" suffix.
     *
     * @param f The file to be checked for display.
     * @return boolean true if the file should be displayed.
     *
     */
    public boolean accept(File f) {
        boolean accept = f.isDirectory();

        if (!accept) {
            String suffix = getSuffix(f);

            if (suffix != null) {
                accept = suffix.equals("db");
            }
        }
        return accept;
    }

    /**
     * The file suffix to allow display.
     *
     * @return String The suffix of all files which can be displayed
     *
     */
    public String getDescription() {
        return "Database Files(*.db)";
    }


    /**
     * Get the suffix of the given file. If the file has no suffix return null
     *
     * @param f The file to check.
     * @return String the file suffix
     *
     */
    public String getSuffix(File f) {
        String s = f.getPath();
        String suffix = null;

        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            suffix = s.substring(i + 1).toLowerCase();
        }
        return suffix;
    }


} // End DatabaseFilter
