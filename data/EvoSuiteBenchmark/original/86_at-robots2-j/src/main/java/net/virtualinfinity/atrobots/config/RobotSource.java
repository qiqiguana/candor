package net.virtualinfinity.atrobots.config;

import net.virtualinfinity.atrobots.compiler.AtRobotCompiler;
import net.virtualinfinity.atrobots.compiler.AtRobotCompilerOutput;
import net.virtualinfinity.atrobots.compiler.RobotFactory;

import java.io.IOException;

/**
 * Describes a source for an entrant.
 *
 * @author Daniel Pitts
 */
public abstract class RobotSource {
    private AtRobotCompiler compiler = new AtRobotCompiler();

    public final String getName() {
        return getDefaultName();
    }

    protected String getDefaultName() {
        return null;
    }

    public final RobotFactory createFactory() throws IOException {
        return compile(getCompiler()).createRobotFactory(getName());
    }

    protected abstract AtRobotCompilerOutput compile(AtRobotCompiler compiler) throws IOException;

    public AtRobotCompiler getCompiler() {
        return compiler;
    }

    @Override
    public String toString() {
        return getName();
    }
}
