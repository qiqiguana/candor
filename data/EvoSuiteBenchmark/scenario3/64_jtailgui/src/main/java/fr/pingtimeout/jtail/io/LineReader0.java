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
     * Lit la ligne dont le numéro est passé en paramètre.
     * Le numéro de la ligne doit être compris entre 0 et le nombre de lignes indexées.
     *
     * @param lineNumber le numéro de la ligne à lire. Doit être compris entre 0 et le nombre de lignes indexées.
     * @return la ligne correspondant au numéro passé en paramètre
     * @throws IOException si une erreur survient pendant la lecture
     */
    public String readLine(int lineNumber) throws IOException;
}
