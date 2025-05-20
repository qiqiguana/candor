/* $Id: FrameStats.java,v 1.2 2004/04/27 19:26:21 bja Exp $ */

package gui.gl;

// use the hidden high-resolution timer
import sun.misc.Perf;

import com.xith3d.render.CanvasPeer;
import com.xith3d.scenegraph.Canvas3D;
import com.xith3d.scenegraph.View;
import com.xith3d.spatial.bounds.Frustum;
import com.xith3d.scenegraph.Transform3D;
import com.xith3d.scenegraph.Node;
import com.xith3d.scenegraph.BranchGroup;
import com.xith3d.scenegraph.TransformGroup;
import com.xith3d.scenegraph.Leaf;
import com.xith3d.scenegraph.NodeUpdater;
import com.xith3d.datatypes.Coord3f;

/**
 * This class provides various statistics for the rendered frame, such as
 * frame rate, frame time, and number of triangles. It uses Sun's builtin
 * timer, so it may not be compatible with other implementations.
 *
 * @author Jens Lehmann
 * @author Joel Andersson <bja@kth.se>
 */
public class FrameStats {

    /** */
    private static FrameStats instance;

    // the associated canvas.
    private Canvas3D canvas;

    // the hight-resolution timer object we are using to work
    private Perf perf = sun.misc.Perf.getPerf();

    // the number of ticks per second
    private long ticksPerSecond = perf.highResFrequency();

    // temporary variables used to calculate the framerate
    private long lastSecondTicks = 0;
    private long newFrameTicks = 0;
    private int frameCounter = 0;
    private int frameRate = 0;

    // specifies if the counter should update automatically or not
    private boolean autoUpdate = true;

    // specifies whether this node is attached to the scene or not
    private boolean attached = false;

    // the number of rendered triangles.
    private long triangles = 0;

    /*
     * Creates a new instance of this class. New instances are created with
     * the newInstance method.
     */
    private FrameStats() {
	this(null);
    }

    /**
     * Creates a new instance of this class. New instances are created with
     * the newInstance method.
     *
     * @param c the associated canvas.
     */
    private FrameStats(Canvas3D c) {
	this.canvas = c;
	if (instance == null) {
	    instance = this;
	}
    }

    /**
     * Update the frame rate and triangle count variables. Method must be 
     * called by everytime a frame has been rendered.
     */
    public void nextFrame() {

        if (newFrameTicks == 0) {

            // start time measurement
            lastSecondTicks = perf.highResCounter();
            newFrameTicks = lastSecondTicks;
            frameCounter = 0;

        } else {

            // update number of frames and ticks
            frameCounter++;
            newFrameTicks = perf.highResCounter();

            // update the counter every second
            if (newFrameTicks - lastSecondTicks > ticksPerSecond) {

		// copy the number of frames rendered.
		frameRate = frameCounter;
		
                // set the frame counter back
                frameCounter = 0;

                // add a "second"
                lastSecondTicks += ticksPerSecond;
            }
        }

	// Get number of triangles rendered.
	triangles = canvas.get3DPeer().getTriangles();
    }

    /**
     * Returns the number of frames rendered per second.
     * @return the number of frames rendered per second.
     */
    public int getFrameRate() {
	return frameRate;
    }

    /**
     *
     *
     */
    public long getTicksPerSecond() {
	return ticksPerSecond;
    }

    /**
     * Returns the number of rendered triangles.
     * @return the number of rendered triangles.
     */
    public long getTriangleCount() {
	return triangles;
    }

    public void setAutoUpdate(boolean auto) {
	autoUpdate = auto;
    }

    public boolean getAutoUpdate() {
	return autoUpdate;
    }

    public void attachScene(BranchGroup scene) {}

    public boolean getAttached() {
	return attached;
    }

    public static FrameStats newInstance(View view) {
	instance = new FrameStats(view.getCanvas3D(0));
	return instance;
    }

    public static FrameStats getInstance() {
	return instance;
    }
}
