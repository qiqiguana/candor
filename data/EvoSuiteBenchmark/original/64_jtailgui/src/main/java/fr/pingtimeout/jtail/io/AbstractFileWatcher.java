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

package fr.pingtimeout.jtail.io;

import java.io.File;
import java.util.TimerTask;

/**
 * Watcher générique permettant de déclencher un traitement sur modification d'un fichier.
 *
 * @author Pierre Laporte
 *         Date: 11 mai 2010
 */
public abstract class AbstractFileWatcher extends TimerTask {
    /**
     * Le fichier monitoré.
     */
    protected final File file;

    /**
     * La date de dernière modification du fichier.
     */
    private long lastModifiedTime;

    /**
     * Constructeur de AbstractFileWatcher.
     *
     * @param file le fichier à monitorer
     */
    public AbstractFileWatcher(File file) {
        this.file = file;

        // Le fichier n'a pas encore été pris en compte
        // La méthode run sera donc appelée au moins une fois.
        this.lastModifiedTime = 0;
    }

    @Override
    public void run() {
        long timeStamp = file.lastModified();

        if (this.lastModifiedTime != timeStamp) {
            this.lastModifiedTime = timeStamp;
            fileChanged();
        }
    }

    /**
     * Méthode exécutée lorsque le fichier monitoré est modifié.
     */
    protected abstract void fileChanged();
}