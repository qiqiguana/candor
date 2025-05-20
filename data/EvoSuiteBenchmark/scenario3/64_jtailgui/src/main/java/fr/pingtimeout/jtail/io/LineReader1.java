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
     * Lit les lignes comprise entre <code>fromLine</code> et <code>toLine</code> (exclus).
     *
     * @param fromLine Le numéro de la première ligne à lire
     * @param toLine Le numéro de la dernière ligne à lire (exclus)
     * @return Les lignes du fichier dont le numéro est compris entre les deux paramètres
     * @throws IOException si une erreur survient pendant la lecture
     */
    public List<String> readBlock(int fromLine, int toLine) throws IOException;
}
