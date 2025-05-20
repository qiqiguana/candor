package net.virtualinfinity.atrobots.interrupts;

import net.virtualinfinity.atrobots.arena.Arena;
import net.virtualinfinity.atrobots.computer.InterruptHandler;
import net.virtualinfinity.atrobots.computer.MemoryCell;

/**
 * @author Daniel Pitts
 */
public class GetGameInfoInterrupt extends InterruptHandler {
    private final MemoryCell activeRobots;
    private final MemoryCell currentRound;
    private final MemoryCell totalRounds;
    private final short totalRoundsValue;
    private final short roundNumberValue;
    private final Arena arena;

    public GetGameInfoInterrupt(MemoryCell activeRobots, MemoryCell currentRound, MemoryCell totalRounds, int totalRoundsValue, int roundNumber, Arena arena) {
        this.activeRobots = activeRobots;
        this.currentRound = currentRound;
        this.totalRounds = totalRounds;
        this.totalRoundsValue = (short) totalRoundsValue;
        this.roundNumberValue = (short) roundNumber;
        this.arena = arena;
    }

    public void handleInterrupt() {
        activeRobots.set((short) arena.countActiveRobots());
        currentRound.set(roundNumberValue);
        totalRounds.set(totalRoundsValue);
    }
}
