package net.virtualinfinity.atrobots.arena;

/**
 * @author Daniel Pitts
 */
public interface SimulationObserver {
    void frameAvailable(SimulationFrameBuffer frameBuffer);
}
