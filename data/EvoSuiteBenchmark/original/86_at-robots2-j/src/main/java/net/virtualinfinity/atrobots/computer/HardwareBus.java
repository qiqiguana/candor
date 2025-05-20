package net.virtualinfinity.atrobots.computer;

import net.virtualinfinity.atrobots.measures.Heat;
import net.virtualinfinity.atrobots.measures.Temperature;
import net.virtualinfinity.atrobots.ports.PortHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Represents all the hardware connected to a single robot.
 *
 * @author Daniel Pitts
 */
public class HardwareBus {

    private Map<Integer, PortHandler> ports;
    private Map<Integer, InterruptHandler> interrupts;
    private final Collection<Resettable> resettables = new ArrayList<Resettable>();
    private final Collection<ShutdownListener> shutdownListeners = new ArrayList<ShutdownListener>();
    private Restartable autoShutdownTarget;
    private Temperature autoShutDown = Temperature.fromLogScale(350);
    private Heat heat;

    /**
     * Get the port handler map.
     *
     * @return map of port numbers to port handler.
     */
    public Map<Integer, PortHandler> getPorts() {
        return ports;
    }

    /**
     * Set the port handler map.
     *
     * @param ports map of port numbers to port handler.
     */
    public void setPorts(Map<Integer, PortHandler> ports) {
        this.ports = ports;
    }

    /**
     * Get the interrupt handler map.
     *
     * @return map of interrupt numbers to interrupt handler.
     */
    public Map<Integer, InterruptHandler> getInterrupts() {
        return interrupts;
    }

    /**
     * Set the interrupt handler map.
     *
     * @param interrupts map of interrupt numbers to interrupt handler.
     */
    public void setInterrupts(Map<Integer, InterruptHandler> interrupts) {
        this.interrupts = interrupts;
    }

    /**
     * Call a specific interrupt.
     *
     * @param interruptNumber the interrupt to execute.
     */
    public void callInterrupt(int interruptNumber) {
        interrupts.get(interruptNumber).call();
    }

    /**
     * Read from a specific port.
     *
     * @param portNumber the port to read from
     * @return the value read.
     */
    public short readPort(int portNumber) {
        return ports.get(portNumber).read();
    }

    /**
     * Write to a specific port
     *
     * @param portNumber the port number
     * @param value      the value to write.
     */
    public void writePort(int portNumber, short value) {
        ports.get(portNumber).write(value);
    }

    /**
     * Reset all resetables in this hardward bus.
     */
    public void reset() {
        for (Resettable resettable : resettables) {
            resettable.reset();
        }
    }

    /**
     * Register a resetable.
     *
     * @param resettable a resetible to get reset when this bus is reset.
     */
    public void addResetable(Resettable resettable) {
        resettables.add(resettable);
    }

    public void addShutdownListener(ShutdownListener shutdownListener) {
        shutdownListeners.add(shutdownListener);
    }

    public void setAutoShutdownListener(Restartable autoShutdownListener) {
        this.autoShutdownTarget = autoShutdownListener;
    }

    /**
     * Check temperature against autoShutDown temp
     */
    public void checkHeat() {
        if (isAutoShutdownEngaged()) {
            shutDown();
        }
        if (autoShutdownTarget.isShutDown() && isAutoStartupEngaged()) {
            autoShutdownTarget.startUp();
        }
    }

    private boolean isAutoStartupEngaged() {
        return heat.getTemperature().compareTo(autoShutDown.minus(Temperature.fromLogScale(50))) < 0;
    }

    private boolean isAutoShutdownEngaged() {
        return heat.getTemperature().compareTo(autoShutDown) >= 0;
    }

    private void startUp() {
        autoShutdownTarget.startUp();
    }

    private void shutDown() {
        for (ShutdownListener listener : shutdownListeners) {
            listener.shutDown();
        }
    }

    /**
     * Get the temperature that shuts down computer.
     *
     * @return the temperature that shuts down computer.
     */
    public int getShutdownLevel() {
        return (int) Math.round(autoShutDown.getLogScale());
    }


    /**
     * Set the temperature that shuts down computer.
     *
     * @param value the temperature that shuts down computer.
     */
    public void setShutdownLevel(int value) {
        autoShutDown = Temperature.fromLogScale(value);
    }

    public void setHeat(Heat heat) {
        this.heat = heat;
    }
}
