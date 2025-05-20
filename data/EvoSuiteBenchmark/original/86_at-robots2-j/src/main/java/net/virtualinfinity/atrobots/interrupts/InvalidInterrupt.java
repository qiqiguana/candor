package net.virtualinfinity.atrobots.interrupts;

import net.virtualinfinity.atrobots.computer.Computer;
import net.virtualinfinity.atrobots.computer.InterruptHandler;

/**
 * @author Daniel Pitts
 */
public class InvalidInterrupt extends InterruptHandler {
    private final Computer computer;

    public InvalidInterrupt(Computer computer) {
        this.computer = computer;
    }

    public void handleInterrupt() {
        computer.invalidInterruptError(computer.getOperandValue(1));
    }
}
