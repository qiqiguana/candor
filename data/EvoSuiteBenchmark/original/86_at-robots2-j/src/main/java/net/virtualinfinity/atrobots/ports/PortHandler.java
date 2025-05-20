package net.virtualinfinity.atrobots.ports;

/**
 * @author Daniel Pitts
 */
public abstract class PortHandler {
    private PortListener portListener;

    public short read() {
        portListener.invalidPortError();
        return 0;
    }

    public void write(short value) {
        portListener.invalidPortError();
    }

    public PortHandler setPortListener(PortListener portListener) {
        this.portListener = portListener;
        return this;
    }

    protected void consumeCycles(int cycles) {
        portListener.consumeCycles(cycles);
    }
}
