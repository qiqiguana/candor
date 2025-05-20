package net.virtualinfinity.atrobots;

import junit.framework.TestCase;
import net.virtualinfinity.atrobots.measures.AbsoluteAngle;
import net.virtualinfinity.atrobots.measures.AngleBracket;
import net.virtualinfinity.atrobots.measures.RelativeAngle;

/**
 * @author Daniel Pitts
 */
public class AngleBracketTest extends TestCase {
    private AngleBracket angleBracket;

    protected void setUp() throws Exception {
        super.setUp();
        angleBracket = AngleBracket.around(AbsoluteAngle.fromBygrees(0), RelativeAngle.fromBygrees(5));
    }

    public void testContainment() {
        assertTrue(angleBracket.contains(AbsoluteAngle.fromBygrees(0)));
        assertTrue(angleBracket.contains(AbsoluteAngle.fromBygrees(5)));
        assertTrue(angleBracket.contains(AbsoluteAngle.fromBygrees(-5)));
        assertFalse(angleBracket.contains(AbsoluteAngle.fromBygrees(6)));
        assertFalse(angleBracket.contains(AbsoluteAngle.fromBygrees(-6)));
    }

    public void testFractionTo() {
        assertEquals(0, angleBracket.fractionTo(AbsoluteAngle.fromBygrees(5)), 0.000001);
        assertEquals(.5, angleBracket.fractionTo(AbsoluteAngle.fromBygrees(0)), 0.000001);
        assertEquals(1, angleBracket.fractionTo(AbsoluteAngle.fromBygrees(-5)), 0.000001);
    }
}