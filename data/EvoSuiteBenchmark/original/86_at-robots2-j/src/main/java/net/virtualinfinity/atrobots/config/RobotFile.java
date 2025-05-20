package net.virtualinfinity.atrobots.config;

import net.virtualinfinity.atrobots.compiler.AtRobotCompiler;
import net.virtualinfinity.atrobots.compiler.AtRobotCompilerOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * TODO: Describe this class.
 *
 * @author Daniel Pitts
 */
public class RobotFile extends RobotSource {
    private File file;
    private String defaultName;

    public RobotFile(File file) throws IOException {
        setFile(file);
    }

    public void setFile(File file) throws IOException {
        defaultName = null;
        if (!file.exists()) {
            throw new FileNotFoundException(file.getPath());
        }
        this.file = file.getCanonicalFile();
        defaultName = removeEndIgnoreCase(file.getName(), ".at2");
    }

    public static String removeEndIgnoreCase(String s, String suffix) {
        if (s == null) {
            return null;
        }
        if (s.toLowerCase().endsWith(suffix)) {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }

    @Override
    protected String getDefaultName() {
        return defaultName;
    }

    protected AtRobotCompilerOutput compile(AtRobotCompiler compiler) throws IOException {
        return compiler.compile(file);
    }
}
