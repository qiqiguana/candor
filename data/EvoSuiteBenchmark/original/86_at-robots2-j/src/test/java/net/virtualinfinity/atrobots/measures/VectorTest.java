package net.virtualinfinity.atrobots.measures;

import junit.framework.TestCase;

/**
 * @author Daniel Pitts
 */
public class VectorTest extends TestCase {
    private static final double DELTA = Math.ulp(0d);

    public void testCartesianAngles() {
        assertEquals(AngleTest.NORTH_BYGREES, createCartesian(AngleTest.NORTH_COSINE, AngleTest.NORTH_SINE).getAngle().getBygrees());
        assertEquals(AngleTest.EAST_BYGREES, createCartesian(AngleTest.EAST_COSINE, AngleTest.EAST_SINE).getAngle().getBygrees());
        assertEquals(AngleTest.SOUTH_BYGREES, createCartesian(AngleTest.SOUTH_COSINE, AngleTest.SOUTH_SINE).getAngle().getBygrees());
        assertEquals(AngleTest.WEST_BYGREES, createCartesian(AngleTest.WEST_COSINE, AngleTest.WEST_SINE).getAngle().getBygrees());
    }

    private static Vector createCartesian(double cosine, double sine) {
        return Vector.createCartesian((cosine), (sine));
    }

    public void testPolar() {
        assertEquals(0, Vector.createPolar(AbsoluteAngle.fromBygrees(0), (1)).getX(), DELTA);
        assertEquals(-1, Vector.createPolar(AbsoluteAngle.fromBygrees(0), (1)).getY(), DELTA);

        assertEquals(1, Vector.createPolar(AbsoluteAngle.fromBygrees(64), (1)).getX(), DELTA);
        assertEquals(0, Vector.createPolar(AbsoluteAngle.fromBygrees(64), (1)).getY(), DELTA);

        assertEquals(0, Vector.createPolar(AbsoluteAngle.fromBygrees(128), (1)).getX(), DELTA);
        assertEquals(1, Vector.createPolar(AbsoluteAngle.fromBygrees(128), (1)).getY(), DELTA);

        assertEquals(-1, Vector.createPolar(AbsoluteAngle.fromBygrees(192), (1)).getX(), DELTA);
        assertEquals(0, Vector.createPolar(AbsoluteAngle.fromBygrees(192), (1)).getY(), DELTA);

    }

    public void testDot() {
        final Vector right5 = createCartesian(5, 0);
        final Vector down5 = createCartesian(0, 5);
        final Vector diagnal = createCartesian(5, 5);
        assertEquals(0, right5.dot(down5), DELTA);
        assertEquals(25, right5.dot(right5), DELTA);
        assertEquals(25, right5.dot(diagnal), DELTA);
    }

    public void testProjection() {
        final Vector down5 = createCartesian(0, 5);
        final Vector down75 = createCartesian(0, 75);
        final Vector diagnal5 = createCartesian(5, 5);
        final Vector diagnal37dot5 = createCartesian(37.5, 37.5);
        assertEquals(down5.getX(), diagnal5.projectOnto(down75).getX(), DELTA);
        assertEquals(down5.getY(), diagnal5.projectOnto(down75).getY(), DELTA);
        assertEquals(diagnal37dot5.getX(), down75.projectOnto(diagnal5).getX(), DELTA);
        assertEquals(diagnal37dot5.getY(), down75.projectOnto(diagnal5).getY(), DELTA);
    }


}
