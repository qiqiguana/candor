/* $Id: PickRay.java,v 1.2 2004/04/17 22:40:54 bja Exp $ */

package gui.gl;

import javax.vecmath.*;

public class PickRay {

    private Point3f origin;
    private Vector3f direction;

    public PickRay(Point3f p, Vector3f v) {
	origin = p;
	direction = v;
    }

    public Point3f getOrigin() {
	return new Point3f(origin);
    }

    public Vector3f getDirection() {
	return new Vector3f(direction);
    }
}
