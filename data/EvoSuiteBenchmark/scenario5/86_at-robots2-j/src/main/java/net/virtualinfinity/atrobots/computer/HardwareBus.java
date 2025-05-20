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

    /**
     * Read from a specific port.
     *
     * @param portNumber the port to read from
     * @return the value read.
     */
    public short readPort(int portNumber) {
        return ports.get(portNumber).read();
    }
}

/**
 * @author Daniel Pitts
 */
public abstract class PortHandler {

    public short read();
}
