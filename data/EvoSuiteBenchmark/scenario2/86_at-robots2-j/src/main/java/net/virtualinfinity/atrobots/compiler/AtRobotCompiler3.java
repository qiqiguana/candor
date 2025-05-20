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
     * @param reader the reader
     * @return a CompilerOutput object.
     * @throws IOException if there is a problem reading from the reader.
     */
    public AtRobotCompilerOutput compile(LineNumberReader reader) throws IOException {
        return new LineNumberReaderCompiler().compile(reader);
    }
}
