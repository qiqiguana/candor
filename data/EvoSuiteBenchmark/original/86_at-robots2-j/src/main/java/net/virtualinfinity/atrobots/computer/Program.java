package net.virtualinfinity.atrobots.computer;

/**
 * A Program is a ROM definition.
 *
 * @author Daniel Pitts
 */
public class Program {
    private short[] programCode;

    public Program(short[] programCode) {
        this.programCode = programCode;
    }

    /**
     * Creates a read-only memory array with the program code pre-flashed.
     *
     * @return the memory array.
     */
    public MemoryArray createProgramMemory() {
        final ReadOnlyMemoryArray readOnlyMemoryArray = new ReadOnlyMemoryArray(programCode.length);
        readOnlyMemoryArray.flash(programCode);
        return readOnlyMemoryArray;
    }
}
