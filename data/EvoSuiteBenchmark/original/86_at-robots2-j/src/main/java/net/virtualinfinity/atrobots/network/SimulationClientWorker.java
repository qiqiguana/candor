package net.virtualinfinity.atrobots.network;

import net.virtualinfinity.atrobots.arena.FrameBuilder;
import net.virtualinfinity.atrobots.json.JsonToSnapshots;
import net.virtualinfinity.atrobots.snapshots.ArenaObjectSnapshot;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

/**
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class SimulationClientWorker extends FrameBuilder implements Runnable {
    private final BufferedReader reader;
    private final Writer writer;
    private volatile boolean running = true;

    public SimulationClientWorker(BufferedReader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void run() {
        try {
            while (running) {
                writer.append("waiting\n").flush();
                final String roundOver = reader.readLine();
                beginFrame("true".equals(roundOver));
                final Collection<ArenaObjectSnapshot> snapshots = new JsonToSnapshots().getSnapshots(new JSONArray(reader.readLine()));
                for (ArenaObjectSnapshot snapshot : snapshots) {
                    addObject(snapshot);
                }
                endFrame();
            }
        } catch (IOException e) {
        } catch (JSONException e) {
        }
    }
}
