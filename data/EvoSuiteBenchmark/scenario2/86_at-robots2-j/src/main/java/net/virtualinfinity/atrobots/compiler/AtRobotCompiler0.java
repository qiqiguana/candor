package net.virtualinfinity.atrobots.compiler;

import java.io.*;

/**
 * Compiler which will compile source-code.
 *
 * @author Daniel Pitts
 */
public class AtRobotCompiler {

    /**
     * Compile the specific file.
     *
     * @param sourceFile the source file.
     * @return a CompilerOutput object.
     * @throws IOException if there is a problem reading from the file.
     */
    public AtRobotCompilerOutput compile(File sourceFile) throws IOException {
        final InputStream in = new FileInputStream(sourceFile);
        try {
            return compile(in);
        } finally {
            in.close();
        }
    }
}
