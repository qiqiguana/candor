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
    public Map<Integer, PortHandler> getPorts();

    /**
     * Set the port handler map.
     *
     * @param ports map of port numbers to port handler.
     */
    public void setPorts(Map<Integer, PortHandler> ports);

    /**
     * Get the interrupt handler map.
     *
     * @return map of interrupt numbers to interrupt handler.
     */
    public Map<Integer, InterruptHandler> getInterrupts();

    /**
     * Set the interrupt handler map.
     *
     * @param interrupts map of interrupt numbers to interrupt handler.
     */
    public void setInterrupts(Map<Integer, InterruptHandler> interrupts);

    /**
     * Call a specific interrupt.
     *
     * @param interruptNumber the interrupt to execute.
     */
    public void callInterrupt(int interruptNumber);

    /**
     * Read from a specific port.
     *
     * @param portNumber the port to read from
     * @return the value read.
     */
    public short readPort(int portNumber);

    /**
     * Write to a specific port
     *
     * @param portNumber the port number
     * @param value      the value to write.
     */
    public void writePort(int portNumber, short value);

    /**
     * Reset all resetables in this hardward bus.
     */
    public void reset();

    /**
     * Register a resetable.
     *
     * @param resettable a resetible to get reset when this bus is reset.
     */
    public void addResetable(Resettable resettable);

    public void addShutdownListener(ShutdownListener shutdownListener);

    public void setAutoShutdownListener(Restartable autoShutdownListener);

    /**
     * Check temperature against autoShutDown temp
     */
    public void checkHeat();

    private boolean isAutoStartupEngaged();

    private boolean isAutoShutdownEngaged();

    private void startUp();

    private void shutDown();

    /**
     * Get the temperature that shuts down computer.
     *
     * @return the temperature that shuts down computer.
     */
    public int getShutdownLevel();

    /**
     * Set the temperature that shuts down computer.
     *
     * @param value the temperature that shuts down computer.
     */
    public void setShutdownLevel(int value);

    public void setHeat(Heat heat);
}
