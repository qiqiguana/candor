package net.virtualinfinity.atrobots.computer;

/**
 * A Program is a ROM definition.
 *
 * @author Daniel Pitts
 */
public class Program {

    public MemoryArray createProgramMemory() {
        final ReadOnlyMemoryArray readOnlyMemoryArray = new ReadOnlyMemoryArray(programCode.length);
        readOnlyMemoryArray.flash(programCode);
        return readOnlyMemoryArray;
    }
}
