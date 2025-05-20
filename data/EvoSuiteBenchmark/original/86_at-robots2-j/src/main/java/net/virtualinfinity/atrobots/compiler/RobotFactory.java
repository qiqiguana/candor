package net.virtualinfinity.atrobots.compiler;

import net.virtualinfinity.atrobots.arena.Arena;
import net.virtualinfinity.atrobots.arena.RoundState;
import net.virtualinfinity.atrobots.computer.*;
import net.virtualinfinity.atrobots.debugger.*;
import net.virtualinfinity.atrobots.robot.Robot;
import net.virtualinfinity.atrobots.robot.RobotScoreKeeper;

/**
 * Represents an entrant in a game.
 *
 * @author Daniel Pitts
 */
public class RobotFactory {

    private static final int ROBOT_STACK_SIZE = 256;
    private static final int LOWER_MEMORY_BLOCK_SIZE = 1024;
    private static final Debugger DEBUGGER = DebugConsole.create(RobotFactory.getSystemConsole()).getDebugger();
    protected final String name;
    protected final Program program;
    protected final HardwareSpecification hardwareSpecification;
    protected final DebugInfo debugInfo;
    protected final int maxProcessorSpeed;
    protected final String message;
    private volatile boolean debug;

    public RobotFactory(String name, Program program, HardwareSpecification hardwareSpecification, DebugInfo debugInfo, int maxProcessorSpeed, String message) {
        this.message = message;
        this.program = program;
        this.name = name;
        this.maxProcessorSpeed = maxProcessorSpeed;
        this.debugInfo = debugInfo;
        this.hardwareSpecification = hardwareSpecification;

    }

    private static Console getSystemConsole() {
        return new ConsoleImpl(new ReaderConsoleInput(System.in), new PrintStreamConsoleOutput(System.out), new PrintStreamConsoleOutput(System.err));
    }

    /**
     * Create a robot.
     *
     * @param roundState
     * @param maxProcessorSpeed
     * @param robotScoreKeeper
     * @param arena
     * @param robotId
     * @return the robot to enter.
     */
    public Robot createRobot(RoundState roundState, int maxProcessorSpeed, RobotScoreKeeper robotScoreKeeper, Arena arena, int robotId) {
        final Robot robot = new Robot(name, robotId, robotScoreKeeper);
        robot.addRobotListener(robotScoreKeeper);
        final RandomAccessMemoryArray lowerMemoryBlock = new RandomAccessMemoryArray(LOWER_MEMORY_BLOCK_SIZE);
        robot.setComputer(createComputer(lowerMemoryBlock, maxProcessorSpeed));
        final RobotConfigurer robotConfigurer = new RobotConfigurer();
        robotConfigurer.setRobot(robot);
        hardwareSpecification.buildRobotConfigurer(robotConfigurer);
        robotConfigurer.setLowerMemoryArray(lowerMemoryBlock);
        robotConfigurer.wireRobotComponents(arena, roundState);
        if (debug) {
            robot.getComputer().setDebugListener(DEBUGGER);
        }
        return robot;
    }

    private Computer createComputer(RandomAccessMemoryArray lowerMemoryBlock, int maxProcessorSpeed) {
        return new Computer(createMemory(lowerMemoryBlock), ROBOT_STACK_SIZE, getProcessorSpeed(maxProcessorSpeed), debugInfo);
    }

    private Memory createMemory(RandomAccessMemoryArray lowerMemoryBlock) {
        final Memory memory = new Memory();
        memory.addMemoryArray(lowerMemoryBlock);
        memory.addMemoryArray(program.createProgramMemory());
        return memory;
    }

    private int getProcessorSpeed(int maxProcessorSpeed) {
        return Math.max(this.maxProcessorSpeed, maxProcessorSpeed);
    }

    public RobotFactory setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RobotFactory{" +
                "name='" + name + '\'' +
                '}';
    }
}
