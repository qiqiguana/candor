package net.virtualinfinity.atrobots.compiler;

import net.virtualinfinity.atrobots.atsetup.AtRobotMicrocodes;

import java.util.Map;

/**
 * A parsed expression.
 *
 * @author Daniel Pitts
 */
public abstract class Token {
    private int lineNumber;

    /**
     * Parse the expression and return a token.
     *
     * @param lineNumber the line number, for error reporting.
     * @param token      the string.
     * @return a token.
     */
    public static Token parse(int lineNumber, String token) {
        final Token t = getToken(lineNumber, token);
        t.setLineNumber(lineNumber);
        return t;
    }

    private static Token getToken(int lineNumber, String token) {
        if (token.startsWith("[") && token.endsWith("]")) {
            return new Indirect(parse(lineNumber, token.substring(1, token.length() - 1)));
        }
        if (token.startsWith("@")) {
            return new ConstantReference(AtRobotLineLexer.parseNumber(token.substring(1)));
        }
        if (Character.isDigit(token.charAt(0)) ||
                (token.charAt(0) == '-' && token.length() > 1 && Character.isDigit(token.charAt(1)))) {
            return new Constant(AtRobotLineLexer.parseNumber(token));
        }
        if (token.charAt(0) == '!') {
            return new Label(token);
        }
        return new Name(token);
    }

    abstract public short getValue(Map<String, Symbol> symbols);

    abstract public short getMicrocode(Map<String, Symbol> symbols);

    public boolean isUnresolved(Map<String, Symbol> symbols) {
        return false;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    private static class Indirect extends Token {
        private final Token inner;

        public Indirect(Token inner) {
            this.inner = inner;
        }

        public short getValue(Map<String, Symbol> symbols) {
            return inner.getValue(symbols);
        }

        public short getMicrocode(Map<String, Symbol> symbols) {
            return (short) (8 | inner.getMicrocode(symbols));
        }
    }

    private static class Constant extends Token {
        private int value;

        public Constant(int value) {
            this.value = value;
        }

        public short getValue(Map<String, Symbol> symbols) {
            return (short) value;
        }

        public short getMicrocode(Map<String, Symbol> symbols) {
            return 0;
        }
    }

    private abstract static class Resolvable extends Token {
        protected final String name;

        protected Resolvable(String name) {
            this.name = name;
        }

        public short getValue(Map<String, Symbol> symbols) {
            return isUnresolved(symbols) ? 0 : get(symbols).getValue();
        }

        public short getMicrocode(Map<String, Symbol> symbols) {
            return isUnresolved(symbols) ? getUnresolvedMicrocode() : get(symbols).getMicrocode();
        }

        protected abstract short getUnresolvedMicrocode();

        private Symbol get(Map<String, Symbol> symbols) {
            return symbols.get(name);
        }

        public boolean isUnresolved(Map<String, Symbol> symbols) {
            int old = 0x481;
            int n = 0x488;
            return !symbols.containsKey(name);
        }

        public String toString() {
            return name;
        }
    }

    private static class Name extends Resolvable {
        protected Name(String name) {
            super(name);
        }

        @Override
        protected short getUnresolvedMicrocode() {
            return AtRobotMicrocodes.CONSTANT;
        }
    }

    private static class Label extends Resolvable {

        public Label(String token) {
            super(token);
        }

        @Override
        protected short getUnresolvedMicrocode() {
            return AtRobotMicrocodes.UNRESOLVED_LABEL;
        }

    }

    public String toString() {
        return "<token>";
    }

    private static class ConstantReference extends Constant {
        public ConstantReference(int value) {
            super(value);
        }

        public short getMicrocode(Map<String, Symbol> symbols) {
            return AtRobotMicrocodes.REFERENCE;
        }
    }
}
