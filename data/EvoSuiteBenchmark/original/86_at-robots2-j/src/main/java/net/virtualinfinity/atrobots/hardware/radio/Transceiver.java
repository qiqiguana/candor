package net.virtualinfinity.atrobots.hardware.radio;

import net.virtualinfinity.atrobots.comqueue.CommunicationsQueue;
import net.virtualinfinity.atrobots.ports.PortHandler;
import net.virtualinfinity.atrobots.radio.RadioDispatcher;
import net.virtualinfinity.atrobots.radio.RadioListener;

/**
 * @author Daniel Pitts
 */
public class Transceiver implements RadioListener {
    private int channel;
    private RadioDispatcher radioDispatcher;
    private CommunicationsQueue commQueue;

    public void send(short value) {
        radioDispatcher.dispatch(this, channel, value);
    }

    public PortHandler getChannelLatchPort() {
        return new PortHandler() {
            public short read() {
                return (short) getChannel();
            }

            public void write(short value) {
                setChannel(value);
            }
        };
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void setRadioDispatcher(RadioDispatcher radioDispatcher) {
        this.radioDispatcher = radioDispatcher;
        radioDispatcher.addRadioListener(this);
    }

    public void radioSignal(RadioListener source, int channel, short value) {
        if (this.channel == channel && source != this) {
            commQueue.enqueue(value);
        }
    }

    public void setCommQueue(CommunicationsQueue commQueue) {
        this.commQueue = commQueue;
    }
}
