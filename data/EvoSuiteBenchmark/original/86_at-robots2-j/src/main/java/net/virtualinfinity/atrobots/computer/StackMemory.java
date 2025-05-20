package net.virtualinfinity.atrobots.computer;

/**
 * Memory, which is separate from main memory, and used as a stack.
 *
 * @author Daniel Pitts
 */
public class StackMemory {
    private final MemoryArray stackMemory;
    private final MemoryCell stackPointer;

    public StackMemory(MemoryCell stackPointer, int stackSize) {
        this.stackPointer = stackPointer;
        stackMemory = new RandomAccessMemoryArray(stackSize);
    }

    public short pop() {
        stackPointer.decrement();
        final short value = stackMemory.get(stackPointer.signed());
        return value;
    }

    public void push(short value) {
        stackPointer.increment();
        stackMemory.put(stackPointer.signed() - 1, value);
    }

    public void reset() {
        stackMemory.clear();
    }
}
