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

import fr.pingtimeout.jtail.io.index.RomFileIndex;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Test pour la classe <code>FileIndexer</code>
 *
 * @author Pierre Laporte
 *         Date: 5 avr. 2010
 */
public class FileIndexerTest {
    /**
     * JTailLogger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(FileIndexerTest.class);

    /**
     * Fichier de log de test contenant 4 lignes de 8 octets chacune.
     */
    private static File logFile;

    /**
     * Fichier de test contenant 1.048.576 lignes de 100 octets chacune.
     */
    private static File hugeFile;

    /**
     * Fichier de test contenant 1.048.576 lignes de 100 octets chacune et pouvant être mis à jour par un test.
     */
    private static File updatableFile;

    /**
     * Dossier de travail
     */
    private static final String WORKING_FOLDER = "target/test-classes/";

    /**
     * Prépare les fichiers de test.
     */
    @BeforeClass
    public static void beforeClass() {
        // Récupérer le fichier de test de base
        try {
            logFile = File.createTempFile(FileEnum.STANDARD_LOG_FILE.fileName, ".JTailPlus");
            logFile.deleteOnExit();

            LOG.debug("Fichier de log : " + logFile.getAbsolutePath());

            LOG.debug("Remplissage du fichier de log avec "
                    + FileEnum.STANDARD_LOG_FILE.fileLength
                    + " octets de données...");

            // Remplir le fichier avec 100Mo de données
            final BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(FileEnum.STANDARD_LOG_FILE.generateContent());
            writer.close();

            LOG.debug("Remplissage terminé.");

            Assert.assertTrue("Le fichier de test n'existe pas", logFile.exists());
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }

        // Créer un gros fichier de test
        try {
            hugeFile = File.createTempFile(FileEnum.HUGE_FILE.fileName, ".JTailPlus");
            hugeFile.deleteOnExit();

            LOG.debug("Fichier de log : " + hugeFile.getAbsolutePath());

            LOG.debug("Remplissage du fichier de log avec "
                    + FileEnum.HUGE_FILE.fileLength
                    + " octets de données...");

            // Remplir le fichier avec 100Mo de données
            final BufferedWriter writer = new BufferedWriter(new FileWriter(hugeFile));
            writer.write(FileEnum.HUGE_FILE.generateContent());
            writer.close();

            LOG.debug("Remplissage terminé.");

            Assert.assertTrue("Le fichier de test n'existe pas", hugeFile.exists());
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }

        // Créer un gros fichier de test avec possibilité de mise à jour
        try {
            updatableFile = File.createTempFile(FileEnum.STANDARD_LOG_FILE.fileName, ".JTailPlus");
            updatableFile.deleteOnExit();

            LOG.debug("Fichier de log : " + updatableFile.getAbsolutePath());

            LOG.debug("Remplissage du fichier de log avec "
                    + FileEnum.STANDARD_LOG_FILE.fileLength
                    + " octets de données...");

            // Remplir le fichier avec 100Mo de données
            final BufferedWriter writer = new BufferedWriter(new FileWriter(updatableFile));
            writer.write(FileEnum.STANDARD_LOG_FILE.generateContent());
            writer.close();

            LOG.debug("Remplissage terminé.");

            Assert.assertTrue("Le fichier de test n'existe pas", updatableFile.exists());
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    /**
     * Teste l'indexation du fichier de test.
     */
    @Test
    public void testIndexing() {
        LOG.debug("Test d'indexation");
        final FileIndexer fileIndexer;
        try {
            fileIndexer = new FileIndexer(logFile);
        } catch (FileNotFoundException e) {
            Assert.fail("Le fichier n'a pas été trouvé");

            // Note : Cette instruction ne sera jamais executée
            return;
        }

        Assert.assertEquals("La taille du fichier renvoyée par l'indexeur est incorrecte",
                logFile.length(),
                fileIndexer.getFile().length());

        final long startTime = System.currentTimeMillis();
        try {
            fileIndexer.index();
        } catch (InterruptedException e) {
            Assert.fail("L'indexation a échoué");
        }
        final long endTime = System.currentTimeMillis();

        Assert.assertEquals("L'indexeur n'a pas trouvé "
                + FileEnum.STANDARD_LOG_FILE.nbLines + " lignes pour le fichier",
                FileEnum.STANDARD_LOG_FILE.nbLines,
                fileIndexer.getIndex().getIndexSize());
        Assert.assertEquals("L'indexeur n'a pas trouvé "
                + FileEnum.STANDARD_LOG_FILE.fileLength + " octets pour le fichier",
                FileEnum.STANDARD_LOG_FILE.fileLength,
                fileIndexer.getFile().length());

        // Comparer à l'index de référence : il doit y avoir un \n tous les 8 caractères
        for (int i = 0; i < FileEnum.STANDARD_LOG_FILE.nbLines; i++) {
            Assert.assertEquals("L'indexeur n'a pas renvoyé le résultat attendu pour l'index",
                    i * FileEnum.STANDARD_LOG_FILE.lineLength,
                    fileIndexer.getIndex().getOffsetOfLine(i));
        }

        LOG.debug("Indexation effectuée en " + (endTime - startTime) + "ms");
    }

    /**
     * Teste l'indexation du fichier de test.
     */
    //@Test
    public void testIndexingHugeFile() {
        LOG.debug("Test d'indexation");
        final FileIndexer fileIndexer;
        try {
            fileIndexer = new FileIndexer(hugeFile, RomFileIndex.class);
        } catch (FileNotFoundException e) {
            Assert.fail("Le fichier n'a pas été trouvé");

            // Note : Cette instruction ne sera jamais executée
            return;
        }

        final long startTime = System.currentTimeMillis();
        try {
            fileIndexer.index();
        } catch (InterruptedException e) {
            Assert.fail("L'indexation a échoué");
        }
        final long endTime = System.currentTimeMillis();

        Assert.assertEquals("L'indexeur n'a pas trouvé "
                + FileEnum.HUGE_FILE.nbLines + " lignes pour le fichier",
                FileEnum.HUGE_FILE.nbLines,
                fileIndexer.getIndex().getIndexSize());
        Assert.assertEquals("L'indexeur n'a pas trouvé "
                + FileEnum.HUGE_FILE.fileLength + " octets pour le fichier",
                FileEnum.HUGE_FILE.fileLength,
                fileIndexer.getFile().length());

        // Comparer à l'index de référence : il doit y avoir un \n tous les 8 caractères
        for (int i = 0; i < FileEnum.HUGE_FILE.nbLines; i++) {
            Assert.assertEquals("L'indexeur n'a pas renvoyé le résultat attendu pour la ligne " + i,
                    i * FileEnum.HUGE_FILE.lineLength,
                    fileIndexer.getIndex().getOffsetOfLine(i));
        }


        LOG.debug("Indexation effectuée en " + (endTime - startTime) + "ms");
    }

    /**
     * Test la mise à jour de l'index après modification du fichier.
     */
    @Test
    public void testUpdateIndex() {
        LOG.debug("Indexation du fichier de test");

        final FileIndexer fileIndexer;
        try {
            fileIndexer = new FileIndexer(updatableFile, RomFileIndex.class);
        } catch (FileNotFoundException e) {
            Assert.fail("Le fichier n'a pas été trouvé");

            // Note : Cette instruction ne sera jamais executée
            return;
        }

        final long indexationStartTime = System.currentTimeMillis();
        try {
            fileIndexer.index();
        } catch (InterruptedException e) {
            Assert.fail("L'indexation a échoué");

            // Note : Cette instruction ne sera jamais executée
            return;
        }
        final long indexationEndTime = System.currentTimeMillis();

        Assert.assertEquals("L'indexeur n'a pas trouvé "
                + FileEnum.STANDARD_LOG_FILE.nbLines + " lignes pour le fichier",
                FileEnum.STANDARD_LOG_FILE.nbLines,
                fileIndexer.getIndex().getIndexSize());

        LOG.debug("Indexation effectuée en " + (indexationEndTime - indexationStartTime) + "ms");
        LOG.debug("Contenu de l'index : " + fileIndexer.getIndex().toString());

        // Compléter le fichier avec deux fois son contenu
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(updatableFile, true));
            writer.write(FileEnum.STANDARD_LOG_FILE.generateContent());
            writer.write(FileEnum.STANDARD_LOG_FILE.generateContent());
            writer.close();
        } catch (IOException e) {
            Assert.fail("La mise à jour du fichier a échoué");

            // Note : Cette instruction ne sera jamais executée
            return;
        }

        final long reindexationStartTime = System.currentTimeMillis();
        try {
            fileIndexer.indexFromLine(FileEnum.STANDARD_LOG_FILE.nbLines - 3);
        } catch (InterruptedException e) {
            Assert.fail("La réindexation a échoué");

            // Note : Cette instruction ne sera jamais executée
            return;
        }
        final long reindexationEndTime = System.currentTimeMillis();

        // Le contenu de test finit par un \n sur lequel la répétition vient s'ajouter
        // Il y a donc deux lignes qui sont complétées
        Assert.assertEquals("L'indexeur n'a pas trouvé "
                + (FileEnum.STANDARD_LOG_FILE.nbLines * 3 - 2) + " lignes pour le fichier",
                (FileEnum.STANDARD_LOG_FILE.nbLines * 3 - 2),
                fileIndexer.getIndex().getIndexSize());

        LOG.debug("Réindexation effectuée en " + (reindexationEndTime - reindexationStartTime) + "ms");
        LOG.debug("Contenu de l'index : " + fileIndexer.getIndex().toString());

        for (int i = 0; i < FileEnum.STANDARD_LOG_FILE.nbLines; i++) {
            Assert.assertEquals("L'indexeur n'a pas renvoyé le résultat attendu pour la ligne " + i,
                    i * FileEnum.STANDARD_LOG_FILE.lineLength,
                    fileIndexer.getIndex().getOffsetOfLine(i));
        }
        for (int i = 0; i < FileEnum.STANDARD_LOG_FILE.nbLines; i++) {
            final int currentLine = i + FileEnum.STANDARD_LOG_FILE.nbLines;
            final long expectedOffset = FileEnum.STANDARD_LOG_FILE.nbLines * FileEnum.STANDARD_LOG_FILE.lineLength
                    + i * FileEnum.STANDARD_LOG_FILE.lineLength;

            Assert.assertEquals("L'indexeur n'a pas renvoyé le résultat attendu pour la ligne " + currentLine,
                    expectedOffset,
                    fileIndexer.getIndex().getOffsetOfLine(currentLine));
        }

    }
}
