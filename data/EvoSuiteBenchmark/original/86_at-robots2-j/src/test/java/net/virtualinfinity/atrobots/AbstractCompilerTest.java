package net.virtualinfinity.atrobots;

import junit.framework.TestCase;
import net.virtualinfinity.atrobots.arena.Arena;
import net.virtualinfinity.atrobots.arena.RoundState;
import net.virtualinfinity.atrobots.compiler.AtRobotCompiler;
import net.virtualinfinity.atrobots.compiler.AtRobotCompilerOutput;
import net.virtualinfinity.atrobots.compiler.RobotFactory;
import net.virtualinfinity.atrobots.computer.Computer;
import net.virtualinfinity.atrobots.game.Game;
import net.virtualinfinity.atrobots.robot.Robot;
import net.virtualinfinity.atrobots.robot.RobotScoreKeeper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Daniel Pitts
 */
public abstract class AbstractCompilerTest extends TestCase {
    protected AtRobotCompiler compiler;
    protected PrintStream source;
    private ByteArrayOutputStream out;
    private Game game;
    protected Robot robot;
    protected AtRobotCompilerOutput compilerOutput;
    protected Computer computer;

    public void setUp() throws IOException {
        compiler = new AtRobotCompiler();
        out = new ByteArrayOutputStream(1024);
        source = new PrintStream(out);
        game = new Game(1);
    }

    public void tearDown() throws IOException {
        out.close();
        source.close();

    }

    protected void compile() {
        try {
            out.flush();
            final ByteArrayInputStream stream = new ByteArrayInputStream(out.toByteArray());
            try {
                compilerOutput = compiler.compile(stream);
                compilerOutput.getErrors().dumpErrors();
                if (compilerOutput.hasErrors()) {
                    return;
                }
                final RobotFactory entrant = new RobotFactory("test", compilerOutput.getProgram(), compilerOutput.getHardwareSpecification(), compilerOutput.getDebugInfo(), compilerOutput.getMaxProcessorSpeed(), compilerOutput.getMessage()) {
                    @Override
                    public Robot createRobot(RoundState roundState, int maxProcessorSpeed, RobotScoreKeeper robotScoreKeeper, Arena arena, int robotId) {
                        robot = super.createRobot(roundState, maxProcessorSpeed, robotScoreKeeper, arena, robotId);
                        return robot;
                    }
                };
                game.addEntrant(entrant);
                game.nextRound();
                computer = robot.getComputer();
            } finally {
                stream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
