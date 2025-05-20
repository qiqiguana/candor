package net.virtualinfinity.atrobots.debugger;

import java.io.IOException;

/**
 * TODO: Describe this class.
 *
 * @author Daniel Pitts
 */
public class ConsoleImpl implements Console {
    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final ConsoleOutput error;

    public ConsoleImpl(ConsoleInput input, ConsoleOutput output, ConsoleOutput error) {
        this.input = input;
        this.output = output;
        this.error = error;
    }

    public void println(Object o) {
        output.println(o);
    }

    public String readline() throws IOException {
        try {
            println("> ");
            return input.readLine();
        } catch (IOException e) {
            println("IO Exception handling debug command: " + e.getLocalizedMessage());
        }
        return null;
    }

    public void handleException(Exception e) {
        error.handleExceptione(e);
    }
}
