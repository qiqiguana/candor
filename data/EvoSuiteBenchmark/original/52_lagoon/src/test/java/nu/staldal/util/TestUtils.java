package nu.staldal.util;

// import java.util.*;

import junit.framework.*;

public class TestUtils extends TestCase
{
    public TestUtils(String name)
    {
        super(name);
    }
	
    public void testEncodePathAsIdentifier()
    {
		assertEquals("SunAndMoon4711", Utils.encodePathAsIdentifier("SunAndMoon4711"));
		assertEquals("Sun_38_Moon", Utils.encodePathAsIdentifier("Sun&Moon"));
		assertEquals("_49_SunAndMoon", Utils.encodePathAsIdentifier("1SunAndMoon"));
    }
		
}
