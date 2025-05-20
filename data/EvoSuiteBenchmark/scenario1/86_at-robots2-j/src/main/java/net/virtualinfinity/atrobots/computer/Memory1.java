package net.virtualinfinity.atrobots.computer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the entire memory (RAM and ROM) of a {@link net.virtualinfinity.atrobots.computer.Computer}.
 *
 * @author Daniel Pitts
 */
public class Memory {

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
