package net.virtualinfinity.atrobots.computer;

/**
 * Represents one cell in a memory.
 *
 * @author Daniel Pitts
 */
public class MemoryCell {
    private final Memory memory;
    private final int address;

    public MemoryCell(Memory memory, int address) {
        this.memory = memory;
        this.address = address;
    }

    public short signed() {
        return memory.get(address);
    }

    public int unsigned() {
        return memory.unsigned(address);
    }

    public void set(short value) {
        memory.set(address, value);
    }

    public void decrement() {
        memory.decrement(address);
    }

    public void increment() {
        memory.increment(address);
    }

    public void or(short value) {
        memory.or(address, value);
    }

    public void and(short value) {
        memory.and(address, value);
    }

    public String toString() {
        return "@" + address + "=" + this.signed();
    }
}
