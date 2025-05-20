package net.virtualinfinity.atrobots.compiler;

import java.io.*;

/**
 * Compiler which will compile source-code.
 *
 * @author Daniel Pitts
 */
public class AtRobotCompiler {

    public AtRobotCompilerOutput compile(LineNumberReader reader) throws IOException {
        return new LineNumberReaderCompiler().compile(reader);
    }
}
