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
