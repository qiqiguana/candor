package net.virtualinfinity.atrobots.debugger;

import net.virtualinfinity.atrobots.atsetup.AtRobotInstruction;
import net.virtualinfinity.atrobots.atsetup.AtRobotInterrupt;
import net.virtualinfinity.atrobots.atsetup.AtRobotPort;
import net.virtualinfinity.atrobots.computer.Computer;
import net.virtualinfinity.atrobots.computer.Microcode;

import java.io.IOException;

/**
 * TODO: Describe this class.
 *
 * @author Daniel Pitts
 */
public class DebugConsole {
    private final Debugger debugger;
    private final Console console;

    private DebugConsole(Debugger debugger, Console console) {
        this.console = console;
        this.debugger = debugger;
    }

    public static DebugConsole create(Console console) {
        return create(console, new Debugger());
    }

    public static DebugConsole create(Console console, Debugger debugger) {
        return new DebugConsole(debugger, console).start();
    }

    private void inputLoop() throws IOException {
        for (String line = readline(); line != null; line = readline()) {
            handleLine(line);
        }
    }

    private void handleLine(String line) {
        try {
            handleInput(line);
        } catch (Exception e) {
            console.handleException(e);
            e.printStackTrace();
        }
    }

    private void handleInput(String line) throws InterruptedException {
        if (line.trim().length() == 0) {
            debugger.step();
            return;
        }
        final String[] tokens = line.split("\\s++");
        if (tokens[0].equalsIgnoreCase("pause")) {
            if (tokens.length > 1) {
                debugger.pause(Integer.parseInt(tokens[1]));
            } else {
                debugger.pause();
            }
        } else if (tokens[0].equalsIgnoreCase("go")) {
            debugger.go();
        } else if (tokens[0].equalsIgnoreCase("step")) {
            debugger.step();
        } else if (tokens[0].equalsIgnoreCase("break")) {
            if (tokens.length > 1) {
                debugger.setBreakpoint(Integer.parseInt(tokens[1]));
            } else {
                debugger.setBreakpoint();
            }
        } else if (tokens[0].equalsIgnoreCase("unbreak")) {
            if (tokens.length > 1) {
                debugger.clearBreakpoint(Integer.parseInt(tokens[1]));
            } else {
                debugger.clearBreakpoint();
            }
        } else if (tokens[0].equalsIgnoreCase("entrant")) {
            if (tokens.length > 1) {
                debugger.setDefaultEntrant(Integer.parseInt(tokens[1]));
            } else {
                debugger.resetDefaultEntrant();
            }
        } else if (tokens[0].equalsIgnoreCase("unbreak")) {
        } else {
            println(null, "Unknown command.");
        }
    }

    private void printState(Computer computer) {
        println(computer, computer.getRegisters());
        println(computer, computer.getInstructionPointer() + ": " + instructionString(computer));
    }

    private String instructionString(Computer computer) {
        if (isInterruptInstruction(computer.getOperandValue(0))) {
            return operatorString(computer) + " " + interruptString(computer);
        }
        if (isPortInstruction(computer.getOperandValue(0))) {
            return operatorString(computer) + " " + portString(computer) + ", " + operandString(computer, 2);
        }
        return operatorString(computer) + " " + operandString(computer, 1) + ", " + operandString(computer, 2);
    }

    private String portString(Computer computer) {
        return AtRobotPort.nameOf(computer.getOperandValue(1)) + "(" + operandString(computer, 1) + ")";
    }

    private String interruptString(Computer computer) {
        return AtRobotInterrupt.nameOf(computer.getOperandValue(1)) + "(" + operandString(computer, 1) + ")";
    }

    private boolean isInterruptInstruction(short operandValue) {
        return AtRobotInstruction.INT.value == operandValue;
    }

    private boolean isPortInstruction(short operandValue) {
        return AtRobotInstruction.IPO.value == operandValue || AtRobotInstruction.OPO.value == operandValue;
    }

    private static String operandString(Computer computer, int operand) {
        Microcode microcode = computer.getMicrocode(operand);
        switch (microcode) {
            case DoubleDereference:
                return "[" + variableString(computer, operand) + "]=[" + computer.getDeferencedValue(operand) + "]=" +
                        computer.getDoubleDereferencedValue(operand);
            case Dereference:
                return variableString(computer, operand) + "=" + computer.getDeferencedValue(operand);
            case NumberedLabel:
                return ":" + computer.getConstant(operand);
            case ResolvedLabel:
                return labelString(computer, operand);
            case UnresolvedLabel:
                return "!<unknown>";
            case Constant:
                return String.valueOf(computer.getConstant(operand));
            default:
            case Invalid:
                return "<invalid>";
        }
    }

    private static String labelString(Computer computer, int operand) {
        return "!" + computer.getConstant(operand);
    }

    private static String variableString(Computer computer, int operand) {
        return computer.getDebugInfo().getVariableName(computer.getConstant(operand));
    }

    private static String operatorString(Computer computer) {
        Microcode microcode = computer.getMicrocode(0);
        if (!microcode.isValid()) {
            return "<invalid>";
        }
        final short value = microcode.getValue(computer, 0);
        if (microcode == Microcode.NumberedLabel) {
            return ":" + value;
        }
        return AtRobotInstruction.nameOf(value);
    }


    private DebugConsole start() {
        debugger.setBreakpointHandler(new MyBreakpointHandler());
        final Thread thread = new Thread("Debugger") {
            public void run() {
                try {
                    inputLoop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        return this;
    }

    public Debugger getDebugger() {
        return debugger;
    }

    private class MyBreakpointHandler implements BreakpointHandler {
        public void handleBreakpoint(Computer computer) {
            printState(computer);
        }
    }

    public void println(Computer computer, Object o) {
        if (computer != null) {
            console.println("#" + computer.getId() + ": " + computer.getName() + "} " + o);
        } else {
            console.println(" } " + o);
        }
    }

    public String readline() throws IOException {
        return console.readline();
    }
}
