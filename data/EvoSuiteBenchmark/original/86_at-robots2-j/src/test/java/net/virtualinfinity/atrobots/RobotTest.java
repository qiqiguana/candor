package net.virtualinfinity.atrobots;

import junit.framework.TestCase;
import net.virtualinfinity.atrobots.arena.Arena;
import net.virtualinfinity.atrobots.compiler.AtRobotCompiler;
import net.virtualinfinity.atrobots.compiler.AtRobotCompilerOutput;
import net.virtualinfinity.atrobots.compiler.RobotFactory;
import net.virtualinfinity.atrobots.game.StandardRoundState;
import net.virtualinfinity.atrobots.robot.Robot;
import net.virtualinfinity.atrobots.robot.RobotScoreKeeper;

import java.io.File;
import java.io.IOException;

/**
 * @author Daniel Pitts
 */
public class RobotTest extends TestCase {

    public void testSittingDuck() throws IOException {
        final AtRobotCompilerOutput compile = new AtRobotCompiler().compile(new File("original/SDUCK.AT2"));
        final RobotFactory robotFactory = compile.createRobotFactory("SDUCK");
        final StandardRoundState roundState = new StandardRoundState(1, 1);
        final Robot robot = robotFactory.createRobot(roundState, 5, new RobotScoreKeeper(), new Arena(), 1);
        assertNotNull(robot);

    }
}
