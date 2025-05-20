/*
 * Copyright (c) 2010 Pierre Laporte.
 *
 * This file is part of JTailPlus.
 *
 * JTailPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTailPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTailPlus.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.pingtimeout.jtail.gui.action;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ResourceBundle;

/**
 * Filtre sur les fichiers texte (*.txt) et les fichiers de log (*.log)
 */
class LogFileFilter extends FileFilter {
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    private static final String LOG_FILE = "log"; //NON-NLS

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }

        final String fileExt;
        final String fileName = file.getName();
        final int lastPoint = fileName.lastIndexOf('.');

        if (lastPoint > 0 && lastPoint < fileName.length() - 1) {
            fileExt = fileName.substring(lastPoint + 1).toLowerCase();
            if (fileExt.equals(LOG_FILE)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return bundle.getString("log.files.description");
    }
}