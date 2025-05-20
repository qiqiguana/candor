package net.virtualinfinity.atrobots.interrupts;

import net.virtualinfinity.atrobots.computer.InterruptHandler;
import net.virtualinfinity.atrobots.computer.MemoryCell;
import net.virtualinfinity.atrobots.hardware.turret.Turret;

/**
 * @author Daniel Pitts
 */
public class SetKeepshiftInterrupt extends InterruptHandler {
    private final Turret turret;
    private final MemoryCell toggler;

    public SetKeepshiftInterrupt(Turret turret, MemoryCell toggler) {
        this.turret = turret;
        this.toggler = toggler;
    }

    public void handleInterrupt() {
        turret.setKeepshift(toggler.signed() != 0);
    }
}
