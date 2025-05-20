package net.virtualinfinity.atrobots.network;

import net.virtualinfinity.atrobots.arena.SimulationFrame;
import net.virtualinfinity.atrobots.arena.SimulationFrameBuffer;
import net.virtualinfinity.atrobots.arena.SimulationObserver;
import net.virtualinfinity.atrobots.json.SnapshotToJson;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;

/**
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class SimulationServerWorker implements SimulationObserver, Runnable {
    private SimulationFrame currentFrame;
    private volatile boolean running = true;
    private final BufferedReader reader;
    private final Writer writer;
    private final Object sync = new Object();

    public SimulationServerWorker(Reader reader, Writer writer) {
        this.reader = new BufferedReader(reader);
        this.writer = writer;
    }

    public void frameAvailable(SimulationFrameBuffer frameBuffer) {
        synchronized (sync) {
            this.currentFrame = frameBuffer.getCurrentFrame();
            sync.notifyAll();
        }
    }

    public void run() {
        try {
            while (running) {
                reader.readLine();
                final SimulationFrame frame = getCurrentFrame();
                final SnapshotToJson snapshotToJson = new SnapshotToJson();
                frame.visitAll(snapshotToJson);
                final JSONArray snapshots = snapshotToJson.getSnapshots();
                final StringWriter stringWriter = new StringWriter();
                snapshots.write(stringWriter);
                writer.append(String.valueOf(frame.isRoundOver())).append("\n");
                writer.append(stringWriter.toString()).append("\n");
                writer.flush();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SimulationFrame getCurrentFrame() throws InterruptedException {
        synchronized (sync) {
            while (currentFrame == null) {
                sync.wait();
            }
            final SimulationFrame frame = currentFrame;
            currentFrame = null;
            return frame;
        }
    }
}
