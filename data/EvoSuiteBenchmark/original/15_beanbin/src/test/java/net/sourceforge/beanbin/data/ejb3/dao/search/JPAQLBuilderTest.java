package net.sourceforge.beanbin.data.ejb3.dao.search;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sourceforge.beanbin.data.ejb3.dao.Parameters;
import net.sourceforge.beanbin.query.Criteria;
import net.sourceforge.beanbin.query.SearchType;

public class JPAQLBuilderTest extends TestCase {
	public void testEquals() throws Exception {
		EqualsBuilder builder = new EqualsBuilder();
		
		Map<String, Parameters> map = new HashMap<String, Parameters>();		
		Criteria criteria = new Criteria("prop", "term", SearchType.EQUALS);		
		builder.initialize(criteria, map);
		
		assertEquals("prop = :prop0", builder.getJPAQL());		
		assertEquals("term", builder.getParameters().get("prop").getTerm(0));
		
		criteria = new Criteria("prop", "term2", SearchType.EQUALS);		
		builder.initialize(criteria, map);
		assertEquals("prop = :prop1", builder.getJPAQL());		
		assertEquals("term2", builder.getParameters().get("prop").getTerm(1));
	}
	
	public void testContains() throws Exception {
		ContainsBuilder builder = new ContainsBuilder();
		
		Map<String, Parameters> map = new HashMap<String, Parameters>();		
		Criteria criteria = new Criteria("prop", "term", SearchType.CONTAINS);		
		builder.initialize(criteria, map);
		
		assertEquals("prop like '%term%'", builder.getJPAQL());				
	}
	
	public void testDoesNotEqual() throws Exception {
		DoesNotEqualBuilder builder = new DoesNotEqualBuilder();
		
		Map<String, Parameters> map = new HashMap<String, Parameters>();		
		Criteria criteria = new Criteria("prop", "term", SearchType.DOESNOTEQUAL);		
		builder.initialize(criteria, map);
		
		assertEquals("prop != :prop0", builder.getJPAQL());		
		assertEquals("term", builder.getParameters().get("prop").getTerm(0));
		
		criteria = new Criteria("prop", "term2", SearchType.DOESNOTEQUAL);		
		builder.initialize(criteria, map);
		assertEquals("prop != :prop1", builder.getJPAQL());		
		assertEquals("term2", builder.getParameters().get("prop").getTerm(1));
	}
	
	public void testLessThan() throws Exception {
		LessThanBuilder builder = new LessThanBuilder();
		
		Map<String, Parameters> map = new HashMap<String, Parameters>();		
		Criteria criteria = new Criteria("prop", 10, SearchType.LESSTHAN);		
		builder.initialize(criteria, map);
		
		assertEquals("prop < :prop0", builder.getJPAQL());		
		assertEquals(10, builder.getParameters().get("prop").getTerm(0));
		
		criteria = new Criteria("prop", 5, SearchType.LESSTHANOREQUALTO);		
		builder.initialize(criteria, map);
		assertEquals("prop <= :prop1", builder.getJPAQL());		
		assertEquals(5, builder.getParameters().get("prop").getTerm(1));
	}
	
	public void testGreaterThan() throws Exception {
		GreaterThanBuilder builder = new GreaterThanBuilder();
		
		Map<String, Parameters> map = new HashMap<String, Parameters>();		
		Criteria criteria = new Criteria("prop", 10, SearchType.GREATERTHAN);		
		builder.initialize(criteria, map);
		
		assertEquals("prop > :prop0", builder.getJPAQL());		
		assertEquals(10, builder.getParameters().get("prop").getTerm(0));
		
		criteria = new Criteria("prop", 5, SearchType.GREATERTHANOREQUALTO);		
		builder.initialize(criteria, map);
		assertEquals("prop >= :prop1", builder.getJPAQL());		
		assertEquals(5, builder.getParameters().get("prop").getTerm(1));
	}
}
