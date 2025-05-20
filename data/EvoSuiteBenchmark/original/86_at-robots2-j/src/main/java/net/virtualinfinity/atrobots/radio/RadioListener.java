package net.virtualinfinity.atrobots.radio;

/**
 * @author Daniel Pitts
 */
public interface RadioListener {
    void radioSignal(RadioListener source, int channel, short value);
}
