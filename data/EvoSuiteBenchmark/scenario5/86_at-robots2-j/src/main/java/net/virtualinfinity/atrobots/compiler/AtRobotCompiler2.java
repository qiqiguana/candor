package net.virtualinfinity.atrobots.compiler;

import java.io.*;

/**
 * Compiler which will compile source-code.
 *
 * @author Daniel Pitts
 */
public class AtRobotCompiler {

    /**
     * Compile the source read from the given reader.
     *
     * @param in the reader
     * @return a CompilerOutput object.
     * @throws IOException if there is a problem reading from the reader.
     */
    public AtRobotCompilerOutput compile(Reader in) throws IOException {
        if (in instanceof LineNumberReader) {
            return compile((LineNumberReader) in);
        }
        final LineNumberReader reader = new LineNumberReader(in);
        try {
            return compile(reader);
        } finally {
            reader.close();
        }
    }

    /**
     * Compile the source read from the given reader.
     *
     * @param reader the reader
     * @return a CompilerOutput object.
     * @throws IOException if there is a problem reading from the reader.
     */
    public AtRobotCompilerOutput compile(LineNumberReader reader) throws IOException;
}
