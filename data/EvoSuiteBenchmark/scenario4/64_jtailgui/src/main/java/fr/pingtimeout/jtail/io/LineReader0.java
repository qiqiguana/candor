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

    //NON-NLS
    private static final String READONLY_MODE = "r";

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
    public LineReader(File file, FileIndex index) throws FileNotFoundException {
    }

    /**
     * Ferme le flux ouvert pour lire le fichier.
     *
     * @throws IOException si une erreur survient
     */
    public void close() throws IOException;

    /**
     * Renvoie le nombre de lignes indexées.
     *
     * @return le nombre de lignes indexées
     */
    public int getIndexSize();

    /**
     * Lit la ligne dont le numéro est passé en paramètre.
     * Le numéro de la ligne doit être compris entre 0 et le nombre de lignes indexées.
     *
     * @param lineNumber le numéro de la ligne à lire. Doit être compris entre 0 et le nombre de lignes indexées.
     * @return la ligne correspondant au numéro passé en paramètre
     * @throws IOException si une erreur survient pendant la lecture
     */
    public String readLine(int lineNumber) throws IOException;

    /**
     * Lit les lignes comprise entre <code>fromLine</code> et <code>toLine</code> (exclus).
     *
     * @param fromLine Le numéro de la première ligne à lire
     * @param toLine   Le numéro de la dernière ligne à lire (exclus)
     * @return Les lignes du fichier dont le numéro est compris entre les deux paramètres
     * @throws IOException si une erreur survient pendant la lecture
     */
    public List<String> readBlock(int fromLine, int toLine) throws IOException;
}
