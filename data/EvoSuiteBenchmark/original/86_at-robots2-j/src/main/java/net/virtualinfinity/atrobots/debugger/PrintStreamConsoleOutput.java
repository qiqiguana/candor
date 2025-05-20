package net.virtualinfinity.atrobots.debugger;

import java.io.PrintStream;

/**
 * TODO: Describe this class.
 *
 * @author Daniel Pitts
 */
public class PrintStreamConsoleOutput implements ConsoleOutput {
    private final PrintStream output;

    public PrintStreamConsoleOutput(PrintStream output) {
        this.output = output;
    }

    public void println(Object o) {
        output.println(o);
    }

    public void handleExceptione(Exception e) {
        e.printStackTrace(output);
    }
}
