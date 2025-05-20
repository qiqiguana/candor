package net.virtualinfinity.atrobots.arena;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class SimulationFrameBuffer {
    protected Collection<SimulationObserver> observers = new ArrayList<SimulationObserver>();
    protected volatile SimulationFrame currentFrame;

    public SimulationFrame getCurrentFrame() {
        return currentFrame;
    }

    public void addSimulationObserver(final SimulationObserver observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeSimulationObserver(SimulationObserver observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    protected void setFrame(SimulationFrame frame) {
        currentFrame = frame;
        synchronized (observers) {
            for (SimulationObserver observer : observers) {
                observer.frameAvailable(SimulationFrameBuffer.this);
            }
        }
    }
}
