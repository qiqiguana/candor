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
    public void or(int address, short value) {
        for (MemoryArray array : arrays) {
            if (address < array.size()) {
                array.or(address, value);
                return;
            }
            address -= array.size();
        }
        errorHandler.memoryBoundsError(address);
    }

    /**
     * Bitwise-and the value at the given location with the given value.
     *
     * @param address the address
     * @param value   the value to or.
     */
    public void and(int address, short value) {
        for (MemoryArray array : arrays) {
            if (address < array.size()) {
                array.and(address, value);
                return;
            }
            address -= array.size();
        }
        errorHandler.memoryBoundsError(address);
    }

    public ComputerErrorHandler getErrorHandler() {
        return errorHandler;
    }

    /**
     * Get the total size of this memory.
     *
     * @return the size.
     */
    public int size() {
        int size = 0;
        for (MemoryArray array : arrays) {
            size += array.size();
        }
        return size;
    }

    /**
     * Add the next section of memory.
     *
     * @param array a section of memory.
     */
    public void addMemoryArray(MemoryArray array) {
        arrays.add(array);
        array.setErrorHandler(errorHandler);
    }

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

    public int unsigned(int index) {
        return get(index) & 0xFFFF;
    }

    /**
     * writes the value at the specific address
     *
     * @param address the address to write.
     * @param value   the value to write at that address.
     */
    public void set(int address, short value) {
        for (MemoryArray array : arrays) {
            if (address < array.size()) {
                array.put(address, value);
                return;
            }
            address -= array.size();
        }
        errorHandler.memoryBoundsError(address);
    }

    public MemoryCell getCell(int index) {
        return new MemoryCell(this, index);
    }

    public void decrement(int address) {
        for (MemoryArray array : arrays) {
            if (address < array.size()) {
                array.decrement(address);
                return;
            }
            address -= array.size();
        }
        errorHandler.memoryBoundsError(address);
    }

    public void increment(int address) {
        for (MemoryArray array : arrays) {
            if (address < array.size()) {
                array.increment(address);
                return;
            }
            address -= array.size();
        }
        errorHandler.memoryBoundsError(address);
    }

    public void setErrorHandler(ComputerErrorHandler errorHandler) {
        for (MemoryArray array : arrays) {
            array.setErrorHandler(errorHandler);
        }
        this.errorHandler = errorHandler;
    }
}
