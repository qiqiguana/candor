package net.sourceforge.beanbin.search;


public class WildcardSearch {
	private String term;

	public WildcardSearch(String searchTerm) {
		this.term = searchTerm;
	}
	
	public boolean doesMatch(String value) {
		if(term.equals("") || value.equals("")) {
			return false;			
		}
		
		int pos = 0;
		
		for(int i = 0; i < term.length(); ++i) {			
			if(term.charAt(i) == '*') {
				if(i + 1 == term.length()) {
					return true;
				}
				
				int nextWildcard = term.indexOf("*", i + 1);
				String subterm;
				if(nextWildcard == -1) {
					subterm = term.substring(i + 1);
				} else {
					subterm = term.substring(i + 1, nextWildcard);	
				}
				
				pos = value.indexOf(subterm, pos);
				if(pos == -1) {
					return false;
				}				
			} else {
				if(term.charAt(i) != value.charAt(pos++)) {
					return false;
				}
			}
		}
		
		int lastIndex = term.lastIndexOf("*");
		if(lastIndex != -1) {
			String lastTerm = term.substring(lastIndex + 1);
			return value.lastIndexOf(lastTerm) + lastTerm.length() == value.length();
		}

		return true;
	}
}
