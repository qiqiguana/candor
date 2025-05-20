package net.virtualinfinity.atrobots.compiler;

import java.io.*;

/**
 * Compiler which will compile source-code.
 *
 * @author Daniel Pitts
 */
public class AtRobotCompiler {

    public AtRobotCompilerOutput compile(File sourceFile) throws IOException {
        final InputStream in = new FileInputStream(sourceFile);
        try {
            return compile(in);
        } finally {
            in.close();
        }
    }
}
