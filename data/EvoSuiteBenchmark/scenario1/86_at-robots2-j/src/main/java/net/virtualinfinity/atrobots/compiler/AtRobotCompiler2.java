package net.virtualinfinity.atrobots.compiler;

import java.io.*;

/**
 * Compiler which will compile source-code.
 *
 * @author Daniel Pitts
 */
public class AtRobotCompiler {

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
}
