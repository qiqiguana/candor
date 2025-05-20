package net.virtualinfinity.atrobots.computer;

/**
 * A Memory Array which can only be read from.
 *
 * @author Daniel Pitts
 */
public class ReadOnlyMemoryArray extends MemoryArray {

    public ReadOnlyMemoryArray(int blockSize) {
        super(blockSize);
    }

    public void put(int index, short value) {
        getErrorHandler().writeToRomError();
    }

    public void flash(short[] data) {
        System.arraycopy(data, 0, cells, 0, Math.min(data.length, size()));
    }

    public void decrement(int index) {
        getErrorHandler().writeToRomError();
    }

    public void increment(int index) {
        getErrorHandler().writeToRomError();
    }

    public void or(int index, short value) {
        getErrorHandler().writeToRomError();
    }

    public void and(int index, short value) {
        getErrorHandler().writeToRomError();
    }

    public void clear() {
        getErrorHandler().writeToRomError();
    }
}
