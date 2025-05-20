package net.virtualinfinity.atrobots.computer;

/**
 * A Program is a ROM definition.
 *
 * @author Daniel Pitts
 */
public class Program {

    private short[] programCode;

    public Program(short[] programCode) {
    }

    /**
     * Creates a read-only memory array with the program code pre-flashed.
     *
     * @return the memory array.
     */
    public MemoryArray createProgramMemory();
}
