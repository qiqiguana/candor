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

package fr.pingtimeout.jtail.gui.controller;

import fr.pingtimeout.jtail.io.AbstractFileWatcher;
import fr.pingtimeout.jtail.io.FileIndexer;

import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 12 mai 2010
 * Time: 10:00:40
 * To change this template use File | Settings | File Templates.
 */
public class UpdateIndexFileWatcher extends AbstractFileWatcher {
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    /**
     * L'indexeur à invoquer lors de la mise à jour du fichier.
     */
    private final FileIndexer fileIndexer;

    /**
     * La taille du fichier à la précédente indexation.
     */
    private long fileSize;

    public UpdateIndexFileWatcher(FileIndexer fileIndexer) {
        super(fileIndexer.getFile());

        this.fileIndexer = fileIndexer;
        this.fileSize = fileIndexer.getFileLength();
    }


    @Override
    protected void fileChanged() {
        // Récupérer la nouvelle taille du fichier
        final long newFileSize = fileIndexer.getFileLength();

        if (newFileSize > this.fileSize) {
            // Le fichier contient plus de données qu'à la précédente indexation
            // Il faut réindexer à partir de la dernière ligne

            processIndexation(this.fileIndexer.getIndex().getIndexSize() - 1);
        } else if (newFileSize == this.fileSize) {
            // Le fichier contient le même volume de données qu'à la précédente indexation
            // Réindexer complètement le fichier

            processIndexation(0);
        } else {
            // Le fichier contient moins de données qu'à la précédente indexation
            // Il faut le réindexer complètement

            processIndexation(0);
        }
    }

    void processIndexation(int fromLine) {
        
           // this.fileIndexer.indexFromLine(fromLine);
    }
}
