package net.virtualinfinity.atrobots.network;

import net.virtualinfinity.atrobots.arena.SimulationFrameBuffer;
import net.virtualinfinity.atrobots.gui.ArenaWindowBuilder;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class Client extends ArenaWindowBuilder implements Runnable {
    private final SimulationFrameBuffer frameBuffer;

    public Client(SimulationFrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket(InetAddress.getLocalHost(), 2001);
        final SimulationClientWorker clientWorker = new SimulationClientWorker(new BufferedReader(new InputStreamReader(socket.getInputStream())), new OutputStreamWriter(socket.getOutputStream()));
        EventQueue.invokeLater(new Client(clientWorker));
        clientWorker.run();
    }

    public void run() {
        initializeWindow();
        frameBuffer.addSimulationObserver(arenaPane);
        frameBuffer.addSimulationObserver(robotStatusPane);
    }

    @Override
    protected void buildMenuBar() {
        menubar.add(createViewMenu());
    }

    @Override
    protected void registerCloseListener() {
    }
}
