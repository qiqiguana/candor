package net.virtualinfinity.atrobots.interrupts;

import net.virtualinfinity.atrobots.computer.Computer;
import net.virtualinfinity.atrobots.computer.InterruptHandler;

/**
 * @author Daniel Pitts
 */
public class ResetInterrupt extends InterruptHandler {
    private final Computer computer;

    public ResetInterrupt(Computer computer) {
        this.computer = computer;
    }

    public void handleInterrupt() {
        computer.reset();
    }
}
