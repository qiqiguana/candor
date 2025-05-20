package net.virtualinfinity.atrobots.interrupts;

import net.virtualinfinity.atrobots.arenaobjects.ArenaObject;
import net.virtualinfinity.atrobots.computer.InterruptHandler;
import net.virtualinfinity.atrobots.computer.MemoryCell;

/**
 * @author Daniel Pitts
 */
public class LocateInterrupt extends InterruptHandler {
    private final ArenaObject object;
    private final MemoryCell xHolder;
    private final MemoryCell yHolder;

    public LocateInterrupt(ArenaObject object, MemoryCell xHolder, MemoryCell yHolder) {
        this.object = object;
        this.xHolder = xHolder;
        this.yHolder = yHolder;
    }

    public void handleInterrupt() {
        xHolder.set((short) Math.round(object.getPosition().getX()));
        yHolder.set((short) Math.round(object.getPosition().getY()));
    }
}
