package net.virtualinfinity.atrobots.computer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the entire memory (RAM and ROM) of a {@link net.virtualinfinity.atrobots.computer.Computer}.
 *
 * @author Daniel Pitts
 */
public class Memory {

    public int size() {
        int size = 0;
        for (MemoryArray array : arrays) {
            size += array.size();
        }
        return size;
    }
}
