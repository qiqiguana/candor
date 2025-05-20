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
 * The <code>FileIndex</code> class is a data structure that associates a number with an offset in a given file.
 * It can be used to track the position of every line in a file. Basically, this class is only an associative array.
 * It doesn't handle the indexation of a file.
 *
 * @author Pierre Laporte
 */
public interface FileIndex {

    /**
     * Return the offset of the given line in the indexed file.
     *
     * @param lineNumber the line whose offset shall be retrieved
     * @return the offset of the given line.
     */
    long getOffsetOfLine(int lineNumber);
}

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
    public String readLine(int lineNumber) throws IOException {
        this.randomAccess.seek(this.index.getOffsetOfLine(lineNumber));
        if (this.randomAccess.getFilePointer() == this.randomAccess.length()) {
            return "";
        } else {
            return this.randomAccess.readLine();
        }
    }
}
