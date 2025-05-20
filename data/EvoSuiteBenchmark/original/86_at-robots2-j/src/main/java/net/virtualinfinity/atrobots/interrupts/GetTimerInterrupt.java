package net.virtualinfinity.atrobots.interrupts;

import net.virtualinfinity.atrobots.arena.RoundTimer;
import net.virtualinfinity.atrobots.computer.InterruptHandler;
import net.virtualinfinity.atrobots.computer.MemoryCell;

/**
 * @author Daniel Pitts
 */
public class GetTimerInterrupt extends InterruptHandler {
    private final MemoryCell lowCell;
    private final MemoryCell highCell;
    private final RoundTimer roundTimer;

    public GetTimerInterrupt(MemoryCell lowCell, MemoryCell highCell, RoundTimer roundTimer) {
        super();
        this.lowCell = lowCell;
        this.highCell = highCell;
        this.roundTimer = roundTimer;
    }

    public void handleInterrupt() {
        final int cycles = roundTimer.getTime().getCycles();
        lowCell.set((short) cycles);
        highCell.set((short) (cycles >>> 16));
    }
}
