package net.virtualinfinity.atrobots.radio;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Pitts
 */
public class RadioDispatcher {
    private final List<RadioListener> radioListeners = new ArrayList<RadioListener>();

    public void addRadioListener(RadioListener radioListener) {
        radioListeners.add(radioListener);
    }

    public void dispatch(RadioListener source, int channel, short value) {
        for (RadioListener listener : radioListeners) {
            listener.radioSignal(source, channel, value);
        }
    }
}
