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

    public String readLine(int lineNumber) throws IOException {
        this.randomAccess.seek(this.index.getOffsetOfLine(lineNumber));
        if (this.randomAccess.getFilePointer() == this.randomAccess.length()) {
            return "";
        } else {
            return this.randomAccess.readLine();
        }
    }
}
