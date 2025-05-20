package net.sourceforge.beanbin.query;

import java.io.Serializable;

public enum SearchType implements Serializable {
	EQUALS,
	LESSTHAN,
	LESSTHANOREQUALTO,
	GREATERTHAN,
	GREATERTHANOREQUALTO,
	CONTAINS,
	DOESNOTEQUAL
}
