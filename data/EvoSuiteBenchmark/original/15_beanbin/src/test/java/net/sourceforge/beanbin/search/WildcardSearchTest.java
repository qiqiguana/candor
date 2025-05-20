package net.sourceforge.beanbin.search;

import junit.framework.TestCase;

public class WildcardSearchTest extends TestCase {
	public void testBasic() throws Exception {
		WildcardSearch wild = new WildcardSearch("pre*");
		assertTrue(wild.doesMatch("preview"));
		assertFalse(wild.doesMatch("blah"));
		
		wild = new WildcardSearch("*pre*");
		assertTrue(wild.doesMatch("dupreview"));
		assertFalse(wild.doesMatch("blah"));
		assertTrue(wild.doesMatch("dupre"));
		assertTrue(wild.doesMatch("preview"));
		
		wild = new WildcardSearch("p*e*");
		assertTrue(wild.doesMatch("preview"));
		assertFalse(wild.doesMatch("post"));
		assertTrue(wild.doesMatch("postmen"));
		assertTrue(wild.doesMatch("preview"));
		assertFalse(wild.doesMatch("bprep"));
		assertTrue(wild.doesMatch("prep"));
		assertTrue(wild.doesMatch("prep school stinks"));
		assertTrue(wild.doesMatch("pranks need not go undone"));
		assertFalse(wild.doesMatch("punks cant go to church"));
		
		wild = new WildcardSearch("*p*e*");
		assertTrue(wild.doesMatch("bprep"));
		assertFalse(wild.doesMatch("p"));
		assertTrue(wild.doesMatch("bob jones folks are preps"));
		assertTrue(wild.doesMatch("bob jones people"));
		assertFalse(wild.doesMatch("bob jones dorks are geeks"));
		
		wild = new WildcardSearch("bob jones*e");
		assertFalse(wild.doesMatch("bob jones folks are preps"));
		assertTrue(wild.doesMatch("bob jones people"));
		assertFalse(wild.doesMatch("bob jones dorks are geeks"));
		
		wild = new WildcardSearch("bob jones*preps");
		assertTrue(wild.doesMatch("bob jones folks are preps"));
		assertFalse(wild.doesMatch("bob jones people"));
		assertFalse(wild.doesMatch("huntsville high kids are preps"));
		
		wild = new WildcardSearch("bob*folks*preps");
		assertTrue(wild.doesMatch("bob jones folks are preps"));
		assertFalse(wild.doesMatch("bob jones kids are preps"));
		assertFalse(wild.doesMatch("bob jones people"));
		assertFalse(wild.doesMatch("huntsville high kids are preps"));
		
		wild = new WildcardSearch("*et*t*");
		assertTrue(wild.doesMatch("getString"));
		assertFalse(wild.doesMatch("getId"));
		
		wild = new WildcardSearch("getString");
		assertTrue(wild.doesMatch("getString"));
		assertFalse(wild.doesMatch("getId"));
		
		wild = new WildcardSearch("*");
		assertTrue(wild.doesMatch("getString"));
		assertTrue(wild.doesMatch("getId"));
	}
}
