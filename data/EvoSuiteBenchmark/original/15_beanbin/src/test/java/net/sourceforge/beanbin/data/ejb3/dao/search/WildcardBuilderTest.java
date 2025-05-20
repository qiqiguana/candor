package net.sourceforge.beanbin.data.ejb3.dao.search;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sourceforge.beanbin.data.ejb3.dao.Parameters;
import net.sourceforge.beanbin.query.Criteria;
import net.sourceforge.beanbin.query.SearchType;

public class WildcardBuilderTest extends TestCase {
	public void testBasic() throws Exception {
		WildcardBuilder builder = new WildcardBuilder();
		
		Map<String, Parameters> map = new HashMap<String, Parameters>();		
		Criteria criteria = new Criteria("prop", "term*", SearchType.EQUALS);		
		builder.initialize(criteria, map);
		
		assertEquals("prop like 'term%'", builder.getJPAQL());		
		
		criteria = new Criteria("prop", "*term*", SearchType.EQUALS);		
		builder.initialize(criteria, map);
		assertEquals("prop like '%term%'", builder.getJPAQL());		
	}
	
	public void testEscapeValues() throws Exception {
		WildcardBuilder builder = new WildcardBuilder();
		
		Map<String, Parameters> map = new HashMap<String, Parameters>();		
		Criteria criteria = new Criteria("prop", "bri*man's", SearchType.EQUALS);		
		builder.initialize(criteria, map);
		
		assertEquals("prop like 'bri%man\\'s'", builder.getJPAQL());
		
		criteria = new Criteria("prop", "*i % k*", SearchType.EQUALS);		
		builder.initialize(criteria, map);
		assertEquals("prop like '%i \\% k%'", builder.getJPAQL());
		
		criteria = new Criteria("prop", "*i \\* k*", SearchType.EQUALS);		
		builder.initialize(criteria, map);
		assertEquals("prop like '%i * k%'", builder.getJPAQL());
	}
	
	public void testEqualsIntegeration() throws Exception {
		EqualsBuilder builder = new EqualsBuilder();
		
		Map<String, Parameters> map = new HashMap<String, Parameters>();
		Criteria criteria = new Criteria("prop", "*term*", SearchType.EQUALS);		
		builder.initialize(criteria, map);
		assertEquals("prop like '%term%'", builder.getJPAQL());
		
		criteria = new Criteria("prop", "i \\* k", SearchType.EQUALS);		
		builder.initialize(criteria, map);
		assertEquals("prop = :prop0", builder.getJPAQL());
		assertEquals("i * k", builder.getParameters().get("prop").getTerm(0));
	}
}
