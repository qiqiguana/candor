package net.sf.xbus.base.core.config;

import java.util.Hashtable;

/**
 * If a class should be used by the
 * {@link net.sf.xbus.base.core.config.Configuration} to read entries from a
 * source, it has to implement the interface <code>ConfigSource</code>.
 */
public interface ConfigSource
{
	/**
	 * Reads all entries from a source and fills a three-level hierarchie of
	 * <code>Hashtables</code>.
	 * <p>
	 * 
	 * The returned <code>Hashtable</code> has the <b>chapters</b> as the
	 * keys. For every chapter exists a <code>Hashtable</code> with the names
	 * of <b>sections</b> as the keys. Every section has a
	 * <code>Hashtable</code> with the <b>keys</b> and their values as
	 * <code>Strings</code>.
	 */
	public Hashtable readCache();

}
