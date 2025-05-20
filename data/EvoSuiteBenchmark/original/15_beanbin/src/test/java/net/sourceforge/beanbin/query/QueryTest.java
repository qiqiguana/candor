package net.sourceforge.beanbin.query;

import java.util.List;

import net.sourceforge.beanbin.query.Conditional;
import net.sourceforge.beanbin.query.Criteria;
import net.sourceforge.beanbin.query.Query;
import net.sourceforge.beanbin.query.SearchType;


import junit.framework.TestCase;

public class QueryTest extends TestCase {
	public void testBasic() throws Exception {
		Criteria crit = new Criteria("prop", "term", SearchType.EQUALS);
		Query query = new Query(crit);
		query.add(new Criteria("prop2", "term2", SearchType.CONTAINS));		
		query.and(new Criteria("prop3", "term3", SearchType.GREATERTHAN));
		query.or(new Criteria("prop4", "term4", SearchType.GREATERTHANOREQUALTO));
		
		List<Criteria> criterias = query.getCriterias();
		assertEquals(4, criterias.size());
		assertNull(criterias.get(0).getPreviousCondition());
		assertEquals(Conditional.AND, criterias.get(1).getPreviousCondition());
		assertEquals(Conditional.AND, criterias.get(2).getPreviousCondition());
		assertEquals(Conditional.OR, criterias.get(3).getPreviousCondition());
	}
	
	public void testToString() throws Exception {
		Criteria crit = new Criteria("prop", "term", SearchType.EQUALS);
		Query query = new Query(crit);
		query.add(new Criteria("prop2", "term2", SearchType.CONTAINS));		
		query.or(new Criteria("prop4", "term4", SearchType.GREATERTHANOREQUALTO));
		assertEquals("prop EQUALS term AND prop2 CONTAINS term2 OR prop4 GREATERTHANOREQUALTO term4", query.toString());
	}
}
