package net.virtualinfinity.atrobots.computer;

import java.util.ArrayList;
import java.util.List;

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
    public short get(int address);
}
