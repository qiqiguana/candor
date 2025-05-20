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

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Test pour la classe <code>FileIndexer</code>
 *
 * @author Pierre Laporte
 *         Date: 7 avr. 2010
 */
public class LineReaderTest {
    /**
     * JTailLogger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(FileIndexerTest.class);

    /**
     * Dossier de travail
     */
    private static final String WORKING_FOLDER = "target/test-classes/";


    private File createTestFile(FileEnum testFile) {
        File logFile = null;

        // Récupérer le fichier de test de base
        try {
            logFile = File.createTempFile(testFile.fileName, ".JTailPlus");
            logFile.deleteOnExit();

            LOG.debug("Remplissage du fichier {} avec {} octets de données...",
                    logFile.getAbsolutePath(),
                    testFile.fileLength);

            // Remplir le fichier avec 100Mo de données
            final BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(testFile.generateContent());
            writer.close();

            LOG.debug("Remplissage terminé.");

            Assert.assertTrue("Le fichier de test n'existe pas", logFile.exists());
        } catch (IOException e) {
            LOG.error("Erreur a la creation du fichier de test", e);
            Assert.fail(e.getLocalizedMessage());
        }

        return logFile;
    }


    private FileIndexer indexFile(File logFile) {
        LOG.debug("Indexation du fichier");
        FileIndexer fileIndexer = null;
        try {
            fileIndexer = new FileIndexer(logFile);
        } catch (FileNotFoundException e) {
            Assert.fail("Le fichier n'a pas été trouvé");
        }

        try {
            fileIndexer.index();
        } catch (InterruptedException e) {
            Assert.fail("L'indexation a échoué");
        }
        return fileIndexer;
    }


    private void closeQuietly(LineReader lineReader) {
        try {
            lineReader.close();
        } catch (IOException e) {
            LOG.warn("Une instance de LineReader n'a pas pu être fermee", e);
        }
    }


    @Test
    public void test_read_line_with_standard_file() {
        final File logFile = createTestFile(FileEnum.STANDARD_LOG_FILE);
        final FileIndexer fileIndexer = indexFile(logFile);

        LineReader lineReader = null;
        try {
            lineReader = new LineReader(logFile, fileIndexer.getIndex());

            // Récupérer les lignes 0 et 1
            final String line0 = lineReader.readLine(0);
            final String line1 = lineReader.readLine(1);
            final String lastLine = lineReader.readLine(lineReader.getIndexSize() - 1);

            // Les chaînes de caractère de référence ne doivent pas contenir le \n de fin de ligne
            final String expectedLine0 = FileEnum.STANDARD_LOG_FILE.dummyContent.format(new Integer[]{0}).trim();
            final String expectedLine1 = FileEnum.STANDARD_LOG_FILE.dummyContent.format(new Integer[]{1}).trim();
            final String expectedLastLine = "";

            // Tester la lecture
            Assert.assertEquals("La méthode readLine ne renvoie pas la ligne attendue",
                    expectedLine0,
                    line0);
            Assert.assertEquals("La méthode readLine ne renvoie pas la ligne attendue",
                    expectedLine1,
                    line1);
            Assert.assertEquals("La méthode readLine ne renvoie pas la ligne attendue",
                    expectedLastLine,
                    lastLine);

        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        } finally {
            closeQuietly(lineReader);
        }
    }

    @Test
    public void test_read_line_with_empty_file() {
        final File logFile = createTestFile(FileEnum.EMPTY_LOG_FILE);
        final FileIndexer fileIndexer = indexFile(logFile);

        LineReader lineReader = null;
        try {
            lineReader = new LineReader(logFile, fileIndexer.getIndex());

            // Récupérer la ligne 0
            final String line0 = lineReader.readLine(0);

            // La ligne attendue est vide
            final String expectedLine = "";

            // Tester la lecture
            Assert.assertEquals("La méthode readLine ne renvoie pas la ligne attendue",
                    expectedLine,
                    line0);

            // Récupérer le fichier complet
            LOG.debug("Appel de readBlock avec : (0, " + FileEnum.EMPTY_LOG_FILE.nbLines + ")");
            final List<String> block = lineReader.readBlock(0, FileEnum.EMPTY_LOG_FILE.nbLines);
            final List<String> expectedBlock = new ArrayList<String>();

            // Tester la lecture du fichier complet
            Assert.assertEquals("La méthode readBlock ne renvoie pas les lignes attendues",
                    expectedBlock,
                    block);
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        } finally {
            closeQuietly(lineReader);
        }
    }


    @Test
    public void test_read_complete_file() {
        final File logFile = createTestFile(FileEnum.STANDARD_LOG_FILE);
        final FileIndexer fileIndexer = indexFile(logFile);

        LineReader lineReader = null;
        try {
            lineReader = new LineReader(logFile, fileIndexer.getIndex());

            LOG.debug("Index : " + fileIndexer.getIndex());

            // Préparer le contenu attendu pour tout le fichier
            final List<String> expectedContent = new ArrayList<String>(FileEnum.STANDARD_LOG_FILE.nbLines);
            for (int i = 0; i < FileEnum.STANDARD_LOG_FILE.nbDummyLines; i++) {
                // Les lignes ne doivent pas contenir le caractère \n
                expectedContent.add(FileEnum.STANDARD_LOG_FILE.dummyContent.format(new Integer[]{i % 10}).trim());
            }
            // La dernière ligne du fichier de test est vide
            expectedContent.add("");

            // Récupérer le fichier complet
            LOG.debug("Appel de readBlock avec : (0, " + FileEnum.STANDARD_LOG_FILE.nbLines + ")");
            final List<String> block = lineReader.readBlock(0, FileEnum.STANDARD_LOG_FILE.nbLines);

            // Tester la lecture du fichier complet
            Assert.assertEquals("La méthode readBlock ne renvoie pas les lignes attendues",
                    expectedContent,
                    block);
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        } finally {
            closeQuietly(lineReader);
        }
    }
}
