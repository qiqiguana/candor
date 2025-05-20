package net.virtualinfinity.atrobots.computer;

import java.util.ArrayList;
import java.util.List;

/**
 * A handler interface for the AT-Robots virtual machine errors.
 *
 * @author Daniel Pitts
 */
public interface ComputerErrorHandler {

    void memoryBoundsError(int address);
}

/**
 * Represents the entire memory (RAM and ROM) of a {@link net.virtualinfinity.atrobots.computer.Computer}.
 *
 * @author Daniel Pitts
 */
public class Memory {

    /**
     * Read the value at the specific address
     *
     * @param address the address to read.
     * @return the value at that address, or 0 if invalid.
     */
    public short get(int address) {
        for (MemoryArray array : arrays) {
            if (address < array.size()) {
                return array.get(address);
            }
            address -= array.size();
        }
        errorHandler.memoryBoundsError(address);
        return 0;
    }
}

/**
 * Represents a block of memory of some type (RAM or ROM).
 *
 * @author Daniel Pitts
 */
public abstract class MemoryArray {

    public final int size();

    public final short get(int index);
}
