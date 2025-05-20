package net.virtualinfinity.atrobots.computer;

import net.virtualinfinity.atrobots.ports.CycleSource;

/**
 * @author Daniel Pitts
 */
public abstract class InterruptHandler {
    private int cost;
    private CycleSource cycleSource;

    protected abstract void handleInterrupt();

    public final InterruptHandler costs(int cost) {
        this.cost = cost;
        return this;
    }

    public void setCycleSource(CycleSource cycleSource) {
        this.cycleSource = cycleSource;
    }

    public final void call() {
        cycleSource.consumeCycles(cost);
        handleInterrupt();
    }
}
