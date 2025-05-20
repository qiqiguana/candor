package net.sourceforge.beanbin.query;

public class IndexCriteria extends Criteria {

	private static final long serialVersionUID = 4757352877149751187L;
	

	public IndexCriteria(String property, String term) {
		super(property, term, SearchType.EQUALS);
	}
}
