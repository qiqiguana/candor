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

package fr.pingtimeout.jtail.io.index;

import fr.pingtimeout.jtail.io.FileEnum;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: plaporte
 * Date: 23 août 2010
 * Time: 14:59:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFileIndexTest {
    /**
     * Dossier de travail
     */
    private static final String WORKING_FOLDER = "target/test-classes/";

    /**
     * JTailLogger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractFileIndexTest.class);

    private static File testFile;

    public abstract FileIndex createFileIndex(File file);

    /**
     * Prépare les fichiers de test.
     */
    @BeforeClass
    public static void beforeClass() {
        // Récupérer le fichier de test de base
        try {
            testFile = File.createTempFile(FileEnum.STANDARD_LOG_FILE.fileName, ".JTailPlus");
            testFile.deleteOnExit();

            LOG.debug("Fichier de log : " + testFile.getAbsolutePath());

            LOG.debug("Remplissage du fichier de log avec "
                    + FileEnum.STANDARD_LOG_FILE.fileLength
                    + " octets de données...");

            // Remplir le fichier avec 100Mo de données
            final BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
            writer.write(FileEnum.STANDARD_LOG_FILE.generateContent());
            writer.close();

            LOG.debug("Remplissage terminé.");

            Assert.assertTrue("Le fichier de test n'existe pas", testFile.exists());
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void testGetIndexSize() {
        final FileIndex fileIndex = createFileIndex(testFile);
        Assert.assertEquals("L'index n'est pas vide après création", 0, fileIndex.getIndexSize());

        fileIndex.setOffsetOfLine(0, 0);
        Assert.assertEquals("L'index ne contient pas 1 ligne après ajout", 1, fileIndex.getIndexSize());

        fileIndex.setOffsetOfLine(0, 0);
        Assert.assertEquals("L'index ne contient pas 1 ligne après écrasement", 1, fileIndex.getIndexSize());

        fileIndex.clear();
        Assert.assertEquals("L'index n'est pas vide après vidage", 0, fileIndex.getIndexSize());
    }

    @Test
    public void testSetOffsetOfLine() {
        final FileIndex fileIndex = createFileIndex(testFile);

        fileIndex.setOffsetOfLine(0, 0);
        Assert.assertEquals("L'index ne renvoie pas un offset correct après insertion", 0, fileIndex.getOffsetOfLine(0));

        fileIndex.setOffsetOfLine(0, 0);
        Assert.assertEquals("L'index ne renvoie pas un offset correct après écrasement", 0, fileIndex.getOffsetOfLine(0));

        fileIndex.setOffsetOfLine(1, 100);
        Assert.assertEquals("L'index ne renvoie pas un offset correct après écrasement", 100, fileIndex.getOffsetOfLine(1));

        fileIndex.setOffsetOfLine(0, 0);
        Assert.assertEquals("L'index ne renvoie pas un offset correct après écrasement", 0, fileIndex.getOffsetOfLine(0));
        Assert.assertEquals("L'index ne renvoie pas un offset correct après écrasement", 100, fileIndex.getOffsetOfLine(1));

        fileIndex.clear();
        fileIndex.setOffsetOfLine(0, 0);
        fileIndex.setOffsetOfLine(1, 100);
        fileIndex.setOffsetOfLine(2, 200);
        fileIndex.setOffsetOfLine(1, 100);
        Assert.assertEquals("L'index ne renvoie pas un offset correct après écrasement", 0, fileIndex.getOffsetOfLine(0));
        Assert.assertEquals("L'index ne renvoie pas un offset correct après écrasement", 100, fileIndex.getOffsetOfLine(1));
        Assert.assertEquals("L'index ne renvoie pas un offset correct après écrasement", 200, fileIndex.getOffsetOfLine(2));
    }

    @Test
    public void testGet() {
        final FileIndex fileIndex = createFileIndex(testFile);

        Assert.assertEquals("L'index ne renvoie pas le nom du fichier", testFile.getName(), fileIndex.getFileName());
    }
}
