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

import java.text.MessageFormat;

/**
 * Fichier de test pour le FileIndexer.
 *
 * @author Pierre Laporte
 *         Date: 5 avr. 2010
 */
public enum FileEnum {
    /**
     * Fichier de test vide. Les attributs de ce fichier sont :
     * <ul>
     * <li>Nom du fichier - <code>logTest.log</code></li>
     * <li>Nombre de lignes factices écrites dans le fichier - <code>0</code></li>
     * <li>Taille de chaque ligne (en caractères) - <code>0</code></li>
     * <li>Taille du fichier (en octets) - <code>0</code></li>
     * <li>Ligne factice - vide</li>
     * <li>Nombre de caractères de retour à la ligne dans le fichier - <code>0</code></i></li>
     */
    EMPTY_LOG_FILE("emptyTestFile.log", 0, 0, 0, "", 0),

    /**
     * Fichier de test léger. Les attributs de ce fichier sont :
     * <ul>
     * <li>Nom du fichier - <code>standardTestFile.log</code></li>
     * <li>Nombre de lignes factices écrites dans le fichier - <code>8</code></li>
     * <li>Taille de chaque ligne (en caractères) - <code>8</code></li>
     * <li>Taille du fichier (en octets) - <code>64</code></li>
     * <li>Ligne factice - <code>Ligne {0}\n</code> <i>(avec {0} un nombre entre 0 et 9)</i></li>
     * <li>Nombre de caractères de retour à la ligne dans le fichier - <code>9</code></i></li>
     * </ul>
     */
    STANDARD_LOG_FILE("standardTestFile.log", 8, 8, 64, "Ligne {0}\n", 9),

    /**
     * Fichier de test volumineux. Les attributs de ce fichier sont :
     * <ul>
     * <li>Nom du fichier - <code>hugeTestFile.log</code></li>
     * <li>Nombre de lignes factices écrites dans le fichier - <code>1048576</code></li>
     * <li>Taille de chaque ligne (en caractères) - <code>10</code></li>
     * <li>Taille du fichier (en octets) - <code>10 Mo</code> <i>(10485760 octets)</i></li>
     * <li>Ligne factice - <code>Ligne   {0}\n</code> <i>(avec {0} un nombre entre 0 et 9)</i></li>
     * <li>Nombre de caractères de retour à la ligne dans le fichier - <code>1048577</code></i></li>
     * </ul>
     */
    HUGE_FILE("hugeTestFile.log", 1048576, 10, 10485760, "Ligne   {0}\n", 1048577);

    /**
     * Le nom du fichier.
     */
    public final String fileName;

    /**
     * Le nombre de lignes attendues pour le fichier.
     */
    public final int nbDummyLines;

    /**
     * La taille attendue pour le fichier.
     */
    public final int fileLength;

    /**
     * La longueur de chaque ligne du fichier
     */
    public final int lineLength;

    /**
     * Contenu d'une ligne du fichier.
     */
    public final MessageFormat dummyContent;

    /**
     * Nombre de caractères "\n" dans le fichier
     */
    public final int nbLines;

    private FileEnum(String fileName, int nbDummyLines, int lineLength, int fileLength, String dummyContent, int nbLines) {
        this.fileName = fileName;
        this.nbDummyLines = nbDummyLines;
        this.lineLength = lineLength;
        this.fileLength = fileLength;
        this.dummyContent = new MessageFormat(dummyContent);
        this.nbLines = nbLines;
    }

    public String generateContent() {
        final StringBuffer resultString = new StringBuffer();

        for (int lineNumber = 0; lineNumber < this.nbDummyLines; lineNumber++) {
            resultString.append(this.dummyContent.format(new Integer[]{lineNumber % 10}));
        }

        return resultString.toString();
    }
}
