package net.virtualinfinity.atrobots.compiler;

import net.virtualinfinity.atrobots.atsetup.*;
import net.virtualinfinity.atrobots.computer.DebugInfo;
import net.virtualinfinity.atrobots.computer.Program;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;

/**
 * A line-lexer visiter which creates a compiled output.
 *
 * @author Daniel Pitts
 */
public class LineNumberReaderCompiler {
    private final Errors errors = new Errors();
    private final List<Short> programCode = new ArrayList<Short>();
    private final Map<String, Symbol> symbols = new HashMap<String, Symbol>();
    private final Collection<UnresolvedToken> unresolved = new ArrayList<UnresolvedToken>();
    private final Map<String, Integer> configs = new HashMap<String, Integer>();
    private final List<String> lines = new ArrayList<String>();
    private final List<Integer> instructionLineNumber = new ArrayList<Integer>();
    private final DebugInfo debugInfo = new DebugInfo();
    private int variables;
    private String message = "";
    private int maxProcessorSpeed;

    {
        setConfig(HardwareSpecification.SCANNER, 5);
        setConfig(HardwareSpecification.WEAPON, 2);
        setConfig(HardwareSpecification.ARMOR, 2);
        setConfig(HardwareSpecification.ENGINE, 2);
        setConfig(HardwareSpecification.HEATSINKS, 1);
        setConfig(HardwareSpecification.MINES, 0);
        setConfig(HardwareSpecification.SHIELD, 0);
    }

    private static final int BUILT_IN_SYMBOL_LINENUMBER = -1;

    private LineVisitor createLineVisitor() {
        return new MyLineVisitor();
    }

    public AtRobotCompilerOutput compile(LineNumberReader reader) throws IOException {
        createLineLexer(reader).visitAllLines();
        resolve();
        return createCompilerOutput();
    }

    private AtRobotLineLexer createLineLexer(LineNumberReader reader) {
        return new AtRobotLineLexer(reader, createLineVisitor());
    }

    {
        addConstant(Short.MAX_VALUE, "MAXINT", BUILT_IN_SYMBOL_LINENUMBER);
        addConstant(Short.MIN_VALUE, "MININT", BUILT_IN_SYMBOL_LINENUMBER);
        addConstants(AtRobotPort.values(), AtRobotMicrocodes.CONSTANT);
        addConstants(AtRobotInstruction.values(), AtRobotMicrocodes.CONSTANT);
        addConstants(AtRobotInterrupt.values(), AtRobotMicrocodes.CONSTANT);
        addConstants(AtRobotRegister.values(), AtRobotMicrocodes.REFERENCE);
    }

    private void addConstants(AtRobotSymbol[] values, short microcode) {
        for (AtRobotSymbol symbol : values) {
            for (String name : symbol.getSymbolNames()) {
                addSymbol(name, microcode, symbol.getSymbolValue(), BUILT_IN_SYMBOL_LINENUMBER);
            }
        }
    }

    private class MyLineVisitor implements LineVisitor {
        public void expectedDigit(int column, int lineNumber) {
            errors.add("Expected digit.", lineNumber, column);
        }

        public void expectedDirectiveName(int column, int lineNumber) {
            errors.add("Expected directive.", lineNumber, column);
        }

        public void unexpectedCharacter(int column, int lineNumber) {
            errors.add("Unexpected character", lineNumber, column);
        }

        public void invalidVariableNameChar(int column, int lineNumber) {
            errors.add("Invalid character in variable name", lineNumber, column);
        }

        public void expectedDeviceName(int column, int lineNumber) {
            errors.add("Expected device name", lineNumber, column);
        }

        public void expectedMoreTokens(int lineNumber) {
            errors.add("Expected more tokens on line", lineNumber);
        }

        public void invalidNumber(int lineNumber) {
            errors.add("Not a valid number", lineNumber);
        }

        public void defineVariable(String variableName, int lineNumber) {
            addReference(variables + 128, variableName, lineNumber);
            variables++;
        }

        public void numberedLabel(int value, int lineNumber) {
            programCode.add((short) value);
            alignProgram();
            programCode.set(programCode.size() - 1, AtRobotMicrocodes.NUMBERED_LABEL);
            markLineNumber(lineNumber);
        }

        public void maxProcessorSpeed(int speed) {
            maxProcessorSpeed = speed;
        }

        public void setMessage(String message) {
            LineNumberReaderCompiler.this.message = message;
        }

        public void setConfig(String name, int value) {
            LineNumberReaderCompiler.this.setConfig(name, value);
        }


        public void machineCode(int[] values, int lineNumber) {
            for (int value : values) {
                programCode.add((short) value);
            }
            alignProgram();
            markLineNumber(lineNumber);
        }

        public void label(String label, int lineNumber) {
            addSymbol(label, AtRobotMicrocodes.RESOLVED_LABEL, programCode.size() / 4, lineNumber);
        }

        public void tokenizedLine(List<Token> tokens, int lineNumber) {
            addTokensToProgram(tokens);
            alignProgram();
            addMicrocodeToProgram(tokens);
            markLineNumber(lineNumber);
        }

        public void unknownDirective(String directive, int lineNumber) {
            errors.add("Unknown directive: #" + directive, lineNumber);
        }

        public void appendRawLine(String line) {
            lines.add(line);
        }
    }

    private void setConfig(String name, int value) {
        configs.put(name.toLowerCase(), value);
    }


    private void alignProgram() {
        while (programCode.size() % 4 != 0) {
            programCode.add((short) 0);
        }
    }

    private void markLineNumber(int lineNumber) {
        debugInfo.setLineForInstructionPointer(instructionLineNumber.size(), lineNumber + ": " + lines.get(lines.size() - 1));
        instructionLineNumber.add(lineNumber);
    }

    private void addSymbol(String name, short microcode, int value, int lineNumber) {
        final String lowerCaseName = name.toLowerCase();
        if (symbols.containsKey(lowerCaseName)) {
            duplicateSymbol(lowerCaseName, symbols.get(lowerCaseName), lineNumber);
        }
        symbols.put(lowerCaseName, new Symbol(microcode, (short) value, lineNumber));
        if (microcode == AtRobotMicrocodes.REFERENCE) {
            debugInfo.addVariable(value, name);
        }
    }

    public void resolve() {
        int sum = 0;
        for (int value : configs.values()) {
            sum += value;
        }
        if (sum > 12) {
            errors.info("Config points too high. " + sum + " out of a max of 12.");
        }
        for (UnresolvedToken entry : unresolved) {
            if (!entry.getToken().isUnresolved(symbols)) {
                resolve(entry.getAddress(), entry.getToken());
            } else {
                errors.add("Unresolved symbol: " + entry.getToken().toString(), entry.getToken().getLineNumber());
            }
        }
    }

    private Program createProgram() {
        if (hasErrors()) {
            return null;
        } else {
            return new Program(getProgramCode());
        }
    }

    private HardwareSpecification createHardwareSpecification() {
        if (hasErrors()) {
            return null;
        } else {
            return new HardwareSpecification(configs);
        }
    }

    public AtRobotCompilerOutput createCompilerOutput() {
        return new AtRobotCompilerOutput(getErrors(), createProgram(), createHardwareSpecification(), getMaxProcessorSpeed(), getDebugInfo(), getMessage());
    }

    private DebugInfo getDebugInfo() {
        return debugInfo;
    }

    private String getMessage() {
        return message;
    }

    private Errors getErrors() {
        return errors;
    }

    private int getMaxProcessorSpeed() {
        return maxProcessorSpeed;
    }


    private void addMicrocodeToProgram(List<Token> tokens) {
        programCode.set(programCode.size() - 1, getMicrocodeFor(tokens));
    }

    private void addTokensToProgram(List<Token> tokens) {
        for (Token token : tokens) {
            addTokenToProgram(token);
        }
    }

    private void addTokenToProgram(Token token) {
        programCode.add(token.getValue(symbols));
        if (token.isUnresolved(symbols)) {
            unresolved.add(new UnresolvedToken(programCode.size() - 1, token));
        }
    }

    private short getMicrocodeFor(List<Token> tokens) {
        short microcode = 0;
        for (ListIterator<Token> iterator = tokens.listIterator(tokens.size()); iterator.hasPrevious(); ) {
            microcode = (short) ((microcode << 4) | iterator.previous().getMicrocode(symbols));
        }
        return microcode;
    }

    private void addReference(int value, String variableName, int lineNumber) {
        addSymbol(variableName, AtRobotMicrocodes.REFERENCE, value, lineNumber);
    }

    private void addConstant(int value, String name, int lineNumber) {
        addSymbol(name, AtRobotMicrocodes.CONSTANT, value, lineNumber);
    }

    private void resolve(int address, Token token) {
        adjustValue(address, token);
        adjustMicrocode(address, token);
    }

    private void adjustValue(int address, Token token) {
        programCode.set(address, token.getValue(symbols));
    }

    private void adjustMicrocode(int address, Token token) {
        final int microcodeIndex = address | 3;
        programCode.set(microcodeIndex, replaceMicrocdeNibble(address & 3, programCode.get(microcodeIndex), token.getMicrocode(symbols)));
    }

    private short replaceMicrocdeNibble(int opNumber, short oldMicrocode, short nibble) {
        return (short) ((oldMicrocode & (0xFFFF0FFF >> ((3 - opNumber) << 2))) | ((nibble & 0xF) << (opNumber << 2)));
    }

    private short[] getProgramCode() {
        final short[] programCode = new short[this.programCode.size()];
        int i = 0;
        for (short value : this.programCode) {
            programCode[i++] = value;
        }
        return programCode;
    }

    private void duplicateSymbol(String lowerCaseName, Symbol symbol, int lineNumber) {
        errors.add("Duplicate symbol: " + lowerCaseName + "." + (symbol.getLineNumber() >= 0 ? " Original declaration on line " + symbol.getLineNumber() : ""), lineNumber);
    }

    private boolean hasErrors() {
        return errors.hasErrors();
    }

    private static class UnresolvedToken {
        private final int address;
        private final Token token;

        private UnresolvedToken(int address, Token token) {
            this.address = address;
            this.token = token;
        }

        public int getAddress() {
            return address;
        }

        public Token getToken() {
            return token;
        }
    }
}
