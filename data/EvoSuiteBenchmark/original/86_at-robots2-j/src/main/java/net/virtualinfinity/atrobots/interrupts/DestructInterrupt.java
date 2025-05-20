package net.virtualinfinity.atrobots.interrupts;

import net.virtualinfinity.atrobots.computer.InterruptHandler;

/**
 * @author Daniel Pitts
 */
public class DestructInterrupt extends InterruptHandler {
    private final Destructable destructable;

    public DestructInterrupt(Destructable destructable) {
        this.destructable = destructable;
    }

    public void handleInterrupt() {
        destructable.destruct();
    }
}
