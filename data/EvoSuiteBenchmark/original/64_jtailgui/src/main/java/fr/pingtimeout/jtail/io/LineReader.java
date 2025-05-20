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

import fr.pingtimeout.jtail.io.index.FileIndex;
import fr.pingtimeout.jtail.util.JTailLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO PLA : commenter.
 *
 * @author Pierre Laporte
 *         Date: 7 avr. 2010
 */
public class LineReader {
    /**
     * Constante pour la lecture uniquement.
     */
    private static final String READONLY_MODE = "r"; //NON-NLS

    /**
     * L'index des lignes du fichier.
     */
    private final FileIndex index;

    /**
     * Le fichier à lire.
     */
    private final File file;

    /**
     * Flux utilisé pour lire le fichier.
     */
    private final RandomAccessFile randomAccess;

    /**
     * Constructeur d'un LineReader à partir d'un index des lignes du fichier.
     * L'index doit indiquer la position des caractères de début de chaque ligne du fichier.
     *
     * @param file  le fichier à lire
     * @param index l'index à utiliser
     * @throws java.io.FileNotFoundException si le fichier n'existe pas
     */
    public LineReader(
            File file,
            FileIndex index
    ) throws FileNotFoundException {
        JTailLogger.debug("Creating LineReader for file : {} with {} indexed lines", //NON-NLS
                file.toString(), index.getIndexSize());

        this.index = index;
        this.file = file;
        this.randomAccess = new RandomAccessFile(file, READONLY_MODE);
    }

    /**
     * Ferme le flux ouvert pour lire le fichier.
     *
     * @throws IOException si une erreur survient
     */
    public void close() throws IOException {
        JTailLogger.debug("Closing stream for file : {}", //NON-NLS
                file.toString());
        this.randomAccess.close();
    }

    /**
     * Renvoie le nombre de lignes indexées.
     *
     * @return le nombre de lignes indexées
     */
    public int getIndexSize() {
        return this.index.getIndexSize();
    }

    /**
     * Lit la ligne dont le numéro est passé en paramètre.
     * Le numéro de la ligne doit être compris entre 0 et le nombre de lignes indexées.
     *
     * @param lineNumber le numéro de la ligne à lire. Doit être compris entre 0 et le nombre de lignes indexées.
     * @return la ligne correspondant au numéro passé en paramètre
     * @throws IOException si une erreur survient pendant la lecture
     */
    public String readLine(int lineNumber) throws IOException {
        this.randomAccess.seek(this.index.getOffsetOfLine(lineNumber));

        if (this.randomAccess.getFilePointer() == this.randomAccess.length()) {
            return "";
        } else {
            return this.randomAccess.readLine();
        }
    }

    /**
     * Lit les lignes comprise entre <code>fromLine</code> et <code>toLine</code> (exclus).
     *
     * @param fromLine Le numéro de la première ligne à lire
     * @param toLine   Le numéro de la dernière ligne à lire (exclus)
     * @return Les lignes du fichier dont le numéro est compris entre les deux paramètres
     * @throws IOException si une erreur survient pendant la lecture
     */
    public List<String> readBlock(int fromLine, int toLine) throws IOException {
        final int nbLines = toLine - fromLine;

        final List<String> result = new ArrayList<String>();
        final long fileLength = this.randomAccess.length();

        this.randomAccess.seek(this.index.getOffsetOfLine(fromLine));
        for (int i = 0; i < nbLines; i++) {
            final String line;
            if (this.randomAccess.getFilePointer() == fileLength) {
                line = "";
                result.add(line);
                break;
            } else {
                line = this.randomAccess.readLine();
                result.add(line);
            }
        }

        return result;
    }
}
