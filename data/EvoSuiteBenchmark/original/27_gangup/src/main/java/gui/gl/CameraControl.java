/* $Id: CameraControl.java,v 1.6 2004/05/04 21:33:58 emill Exp $
 *
 *
 * @author Joel Andersson <bja@kth.se>
 * @author Emil Lundström <emill@kth.se>
 * @version $Revision: 1.6 $
 */

package gui.gl;

import static gui.gl.Util3D.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javax.vecmath.*;
import com.xith3d.scenegraph.*;
import com.xith3d.datatypes.*;
import java.awt.event.*;

public class CameraControl implements MouseListener, MouseMotionListener {
						 
    private int startDragY;
    private int startDragX;

    // locks camera movement
    private boolean locked = false;
    
    private Coord3f rot = new Coord3f(); // temporary holder
    private Coord3f mov = new Coord3f(); // temporary holder
    private Coord3f rotStart = new Coord3f();
    private Coord3f movStart = new Coord3f();

    private float MAX_POS_X =  5.0f;
    private float MIN_POS_X = -5.0f;
    private float MAX_POS_Z =  5.0f;
    private float MIN_POS_Z = -5.0f;
    
    private float MAX_ANG_X = -0.2f;
    private float MIN_ANG_X = (float)-Math.PI/2;

    /** The grid coordinates where the dragging started. */
    private Point3f dragStart;

    /** The world coordinates where the crosshair of dragging started. */
    private Point3f dragCrossStart;

    private Camera camera;
    private Canvas3D canvas;
    private World grid;

    public CameraControl(Camera cam, Canvas3D can, World g) {
	camera = cam;
	canvas = can;
	grid = g;
	camera.getRotation(rot);
	camera.getTranslation(mov);
    }

    public void mouseClicked(MouseEvent ev) {}

    public void mousePressed(MouseEvent ev) {
	switch(ev.getModifiers()) {
	    case MouseEvent.BUTTON1_MASK:  leftPress(ev);   break;
	    case MouseEvent.BUTTON2_MASK:  middlePress(ev); break;
	    case MouseEvent.BUTTON3_MASK:  rightPress(ev);  break;
	}
    }

    public void mouseDragged(MouseEvent ev) {
	switch(ev.getModifiers()) {
	    case MouseEvent.BUTTON1_MASK: leftDrag(ev);   break;
	    case MouseEvent.BUTTON2_MASK: middleDrag(ev); break;
	    case MouseEvent.BUTTON3_MASK: rightDrag(ev);  break;
	}
    }

    private void leftPress(MouseEvent ev) {}
    
    private void middlePress(MouseEvent ev) {
        startDragY = ev.getY(); 
        startDragX = ev.getX();

	int w = canvas.get3DPeer().getWidth();
	int h = canvas.get3DPeer().getHeight();

	dragCrossStart = grid.intersect(castRay(canvas,w/2,h/2));
	grid.getTransform().transform(dragCrossStart);

	movStart.set(mov);
	rotStart.set(rot);
    }


    private void rightPress(MouseEvent ev) {
	PickRay r = castRay(canvas, ev.getX(), ev.getY());
	dragStart = grid.intersect(r);

	movStart.set(mov);
	rotStart.set(rot);
    }

    public void mouseReleased(MouseEvent ev) {
	locked = false;
    }

    public void mouseMoved(MouseEvent ev) {}

    public void mouseEntered(MouseEvent ev) {}
    public void mouseExited(MouseEvent ev) {}

    private void leftDrag(MouseEvent ev) {}

    private void middleDrag(MouseEvent ev) {

	float v = -(ev.getX() - startDragX)/200f;
	float v2 = -(ev.getY() - startDragY)/200f;
	Point3f p = dragCrossStart;
	
	//camera.getTranslation(mov);
	mov.set(movStart);

	Transform3D t = new Transform3D();
	Vector3f tmp = new Vector3f();

	if (rotStart.x + v2 > MAX_ANG_X) {
	    v2 = MAX_ANG_X - rotStart.x;
	}
	else if (rotStart.x + v2 < MIN_ANG_X) {
	    v2 = MIN_ANG_X - rotStart.x;
	}


	camera.getRotation(rot);
	rot.y = rotStart.y + v;
	rot.x = rotStart.x + v2;
	camera.setRotation(rot);

	tmp.set(p);
	tmp.negate();
	t.setTranslation(tmp);
	t.transform(mov);

	//t.setIdentity();
	t.setEuler(new Vector3f(0,-rotStart.y,0));
	t.transform(mov);
	
	//t.setIdentity();
	t.setEuler(new Vector3f(v2,v+rotStart.y,0));
	t.transform(mov);
    
	t.setIdentity();
	tmp.set(p);
	t.setTranslation(tmp);
	t.transform(mov);


	camera.setTranslation(mov);

    }

    private void rightDrag(MouseEvent ev) {
	/*
	PickRay r = castRay(canvas, ev.getX(), ev.getY());
	Point3f p = grid.intersect(r);

	// this is correct.
	// p.x - startDrag.x represents the distance moved since last
	// update, since mov is updated to make p.x - startDrag.x = 0.
	// it may however be the wrong way of doing it, if for p is
	// calculated incorrectly at one point for some reason the
	// entire dragging session will be corrupted. if this
	// calculation was instead based on the total dragged distance
	// and movStart.x, this could be avoided.
	camera.getTranslation(mov);
	mov.x -= (p.x - dragStart.x);
	mov.z -= (p.z - dragStart.z);
	clampPosition(mov);
	camera.setTranslation(mov);
	*/
    }

    private void clampPosition(Tuple3f p) {
	p.x = p.x < MIN_POS_X ? MIN_POS_X : p.x > MAX_POS_X ? MAX_POS_X : p.x;
	p.z = p.z < MIN_POS_Z ? MIN_POS_Z : p.z > MAX_POS_Z ? MAX_POS_Z : p.z;
    }


    /**
     * Makes the camera fly to the destination. Call this within a
     * thread that can handle being asleep for a long time :).
     */
    public void flyTo(Coord3f movdest, Vector3f rotdest,
		      int steps, int sleeptime) {
	if (!locked) {
	    locked = true; // stop user from rotating
	    movStart.set(mov);
	    rotStart.set(rot);
	
	    for (int i=1; i <= steps; i++) {
		mov.interpolate(movStart,movdest,i/(float)steps);
		rot.interpolate(rotStart,rotdest,i/(float)steps);
		try {
		    Thread.sleep(sleeptime);
		} catch (Exception e) {
		    // interruptedexception
		}
	    }
	    
	    locked = false;
	}
    }
}
    
