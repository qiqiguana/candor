package net.virtualinfinity.atrobots.compiler;

import java.io.*;

/**
 * Compiler which will compile source-code.
 *
 * @author Daniel Pitts
 */
public class AtRobotCompiler {

    public AtRobotCompilerOutput compile(InputStream in) throws IOException {
        final Reader reader = new InputStreamReader(in);
        try {
            return compile(reader);
        } finally {
            reader.close();
        }
    }
}
