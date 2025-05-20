/*
 * Created on Dec 13, 2004
 *
 */
package org.heal.module.oai.provider.basic;

import org.heal.module.oai.provider.OAISet;

/**
 * @author Seth Wright
 *
 */
public class BasicSet implements OAISet {

	private final String spec;
	private final String name;
	private final String[] descriptions;

	public BasicSet(final String spec,final String name,final String[] descriptions) {
		this.spec = spec;
		this.name = name;
		this.descriptions = descriptions;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAISet#getSpec()
	 */
	public String getSpec() {
		return spec;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAISet#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAISet#getDescriptions()
	 */
	public String[] getDescriptions() {
		return descriptions;
	}

}
