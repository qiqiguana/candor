package net.virtualinfinity.atrobots.computer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the entire memory (RAM and ROM) of a {@link net.virtualinfinity.atrobots.computer.Computer}.
 *
 * @author Daniel Pitts
 */
public class Memory {

    private final List<MemoryArray> arrays = new ArrayList<MemoryArray>();

    private ComputerErrorHandler errorHandler;

    /**
     * Bitwise-or the value at the given location with the given value.
     *
     * @param address the address
     * @param value   the value to or.
     */
    public void or(int address, short value);

    /**
     * Bitwise-and the value at the given location with the given value.
     *
     * @param address the address
     * @param value   the value to or.
     */
    public void and(int address, short value);

    public ComputerErrorHandler getErrorHandler();

    /**
     * Get the total size of this memory.
     *
     * @return the size.
     */
    public int size();

    /**
     * Add the next section of memory.
     *
     * @param array a section of memory.
     */
    public void addMemoryArray(MemoryArray array);

    /**
     * Read the value at the specific address
     *
     * @param address the address to read.
     * @return the value at that address, or 0 if invalid.
     */
    public short get(int address);

    public int unsigned(int index);

    /**
     * writes the value at the specific address
     *
     * @param address the address to write.
     * @param value   the value to write at that address.
     */
    public void set(int address, short value);

    public MemoryCell getCell(int index);

    public void decrement(int address);

    public void increment(int address);

    public void setErrorHandler(ComputerErrorHandler errorHandler);
}
