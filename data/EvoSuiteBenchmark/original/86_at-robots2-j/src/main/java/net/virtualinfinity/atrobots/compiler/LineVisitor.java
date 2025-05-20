package net.virtualinfinity.atrobots.compiler;

import java.util.List;

/**
 * @author Daniel Pitts
 */
public interface LineVisitor {
    void expectedDigit(int column, int lineNumber);

    void numberedLabel(int value, int lineNumber);

    void expectedDirectiveName(int column, int lineNumber);

    void unexpectedCharacter(int column, int lineNumber);

    void invalidVariableNameChar(int column, int lineNumber);

    void defineVariable(String variableName, int lineNumber);

    void maxProcessorSpeed(int speed);

    void setMessage(String message);

    void setConfig(String name, int value);

    void expectedDeviceName(int column, int lineNumber);

    void expectedMoreTokens(int lineNumber);

    void invalidNumber(int lineNumber);

    void machineCode(int[] values, int lineNumber);

    void label(String line, int lineNumber);

    void tokenizedLine(List<Token> tokens, int lineNumber);

    void unknownDirective(String directive, int lineNumber);

    void appendRawLine(String line);
}
