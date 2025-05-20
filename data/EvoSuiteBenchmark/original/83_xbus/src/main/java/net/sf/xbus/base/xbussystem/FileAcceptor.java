package net.sf.xbus.base.xbussystem;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;

/**
 * Class <code>FileAcceptor</code> implements <code>FilenameFilter</code>
 * interface to filter filenames.<br>
 * Its instances are used to filter directory listings in the
 * {@link java.io.File#list(java.io.FilenameFilter)}method. All file names in
 * the returned array must satisfy this filter.
 * <p>
 * <i>Note:</i>&nbsp;A name satisfies the filter if and only if the value true
 * results when the {@link #accept(File, String)} method is invoked on this
 * abstract pathname and the name of a file or directory in the directory that
 * it denotes.
 * <p>
 * 
 * @author Fleckenstein
 */
public class FileAcceptor implements FilenameFilter
{
	/**
	 * Stores the name of the file before separator(WILDCARD)
	 */
	String mPrefix = null;
	/**
	 * Stores the name of the file after separator(WILDCARD) or empty string if
	 * the name ends with separator(WILDCARD)
	 */
	String mPostfix = null;
	/**
	 * Stores all replacements(WILDCARDS)
	 */
	Vector mReplacements = new Vector();

	/**
	 * Initializes new Filename filter for the given abstract pathname with
	 * checking it on observance of a Separator(WILDCARD) and storing the prefix
	 * and postfix of this filename in to class variables.
	 * 
	 * @param pathname abstract path name to be filtered
	 * @exception XException if the filename doesn't contain a seperator or has
	 *                more than one seperator
	 */
	public FileAcceptor(String pathname) throws XException
	{
		// store the name of the file or directory denoted by this abstract
		// pathname.
		// This is just the last name in the pathname's name sequence.
		String filename = new File(pathname).getName();

		// check if this name contains one and only one WILDCARD
		int seperatorIndex = filename.indexOf(XBUSSystem.FILENAME_WILDCARD);
		if (seperatorIndex < 0)
		{
			List params = new Vector();
			params.add(filename);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_BASE, Constants.PACKAGE_BASE_XBUSSYSTEM,
					"1", params);
		} // if (seperatorIndex < 0)

		if (seperatorIndex != filename
				.lastIndexOf(XBUSSystem.FILENAME_WILDCARD))
		{
			List params = new Vector();
			params.add(filename);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_BASE, Constants.PACKAGE_BASE_XBUSSYSTEM,
					"2", params);
		} // if (seperatorIndex !=
			// filename.lastIndexOf(XBUSSystem.FILENAME_WILDCARD))

		// save prefix (name before WILDCARD) and postfix (name after WILDCARD
		// or "" if the filename ends with the WILDCARD) in class variablen
		mPrefix = filename.substring(0, seperatorIndex);
		if (filename.length() > seperatorIndex
				+ XBUSSystem.FILENAME_WILDCARD.length())
		{
			mPostfix = filename.substring(seperatorIndex
					+ XBUSSystem.FILENAME_WILDCARD.length(), filename.length());
		} // then (filename.length()
		else
		{
			mPostfix = "";
		} // else (filename.length()
	} // FileAcceptor(String pathname)

	/**
	 * Tests whether or not the specified file should be included in a file
	 * list. If the file is satisfyd this filter, that stores this method the
	 * placeholder to be replace late (substring between prefix and postfix) in
	 * vector.
	 * 
	 * @see java.io.FilenameFilter#accept(File, String)
	 * @param dir the directory in which the file was found.
	 * @param filename the name of the file to be tested
	 * @return true if and only if filename should be included in the file list;
	 *         false otherwise.
	 */
	public boolean accept(File dir, String filename)
	{
		boolean result = false;
		if (filename.startsWith(mPrefix) && filename.endsWith(mPostfix))
		{ // File name is matches the pattern.
			mReplacements.add(filename.substring(mPrefix.length(), filename
					.lastIndexOf(mPostfix)));
			result = true;
		} // if (filename.startsWith(mPrefix) && filename.endsWith(mPostfix))
		return result;
	} // accept(File dir, String filename)

	/**
	 * Returns the list with all replacements (WILDCARDS)
	 * 
	 * @return list with all replacements
	 */

	public List getReplacements()
	{
		return mReplacements;
	} // getReplacements()

} // FileAcceptor

