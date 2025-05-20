package net.virtualinfinity.atrobots.network;

import net.virtualinfinity.atrobots.arena.SimulationFrameBuffer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class Server implements Runnable {
    private volatile SimulationFrameBuffer buffer;
    private final ServerSocket listener;

    public Server(ServerSocket listener) {
        this.listener = listener;
    }

    public SimulationFrameBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(SimulationFrameBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while (true) {
            try {
                final Socket accept = listener.accept();
                final SimulationServerWorker worker = new SimulationServerWorker(new InputStreamReader(accept.getInputStream(), "UTF-8"), new OutputStreamWriter(accept.getOutputStream(), "UTF-8"));
                buffer.addSimulationObserver(worker);
                new Thread(worker).start();
            } catch (IOException e) {
            }

        }
    }
}
