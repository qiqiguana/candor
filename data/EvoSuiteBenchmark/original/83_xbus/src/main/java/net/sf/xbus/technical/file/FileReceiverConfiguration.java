package net.sf.xbus.technical.file;

import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;

/**
 * Makes some configuration parameters accessible to different Receivers dealing
 * with files.
 */
public class FileReceiverConfiguration
{
	private int mMaxLineLength;
	private String mConfigFilename = null;
	private String mResolution = null;
	private String mEncoding = null;
	private int mLineLength;
	private String mOnError = null;

	/**
	 * Reads some configuration entries and stores them in class variables.
	 * 
	 * @param system name of the system under which the parameters are stored
	 * @throws XException if something goes wrong
	 */
	public FileReceiverConfiguration(String system) throws XException
	{
		Configuration config = Configuration.getInstance();
		mResolution = retrieveResolution(config, system);
		mOnError = retrieveOnError(config, system);
		mConfigFilename = config.getValue(Constants.CHAPTER_SYSTEM, system,
				Constants.KEY_FILENAME);
		mEncoding = retrieveEncoding(system);
		mLineLength = config.getValueAsIntOptional(Constants.CHAPTER_SYSTEM,
				system, "RecordLineLength");
		mMaxLineLength = config.getValueAsIntOptional(Constants.CHAPTER_SYSTEM,
				system, "MaxLineLength");
	}

	/**
	 * Returns the parameter <code>FileName</code>.
	 */
	public String getFileName()
	{
		return mConfigFilename;
	}

	/**
	 * Returns the parameter <code>Encoding</code>.
	 */
	public String getEncoding()
	{
		return mEncoding;
	}

	/**
	 * Returns the parameter <code>RecordLineLength</code>.
	 */
	public int getLineLength()
	{
		return mLineLength;
	}

	/**
	 * Returns the parameter <code>MaxLineLength</code>.
	 */
	public int getMaxLineLength()
	{
		return mMaxLineLength;
	}

	/**
	 * Returns the parameter <code>OnError</code>.
	 */
	public String getOnError()
	{
		return mOnError;
	}

	/**
	 * Returns the parameter <code>FinalResolution</code>.
	 */
	public String getResolution()
	{
		return mResolution;
	}

	/**
	 * Reads final resolution (resolved action with the file after that it was
	 * read) for the for the given system name from the standard configuration
	 * and checks its conformity with the allowed ones:
	 * <p>
	 * <dl>
	 * <dd><code>Preserve</code> &nbsp;-file is remained without
	 * modifications
	 * <dd><code>Rename</code> &nbsp;&nbsp;&nbsp;- file is renamed
	 * <dd><code>Delete</code> &nbsp;&nbsp;&nbsp;- file is deleted
	 * </dl>
	 * 
	 * @param config Instance of the
	 *            {@link net.sf.xbus.base.core.config.Configuration}
	 * @param system Sytem name which resoltion must be read.
	 * @return exist resolution (Preserve, Rename or Delete) as String
	 * @exception XException if resolution is falsh or any errors occurs
	 */
	private String retrieveResolution(Configuration config, String system)
			throws XException
	{
		String resolution = config.getValue(Constants.CHAPTER_SYSTEM, system,
				Constants.KEY_RECEIVE_RESOL);
		if (!resolution.equals(Constants.READ_PRESERVE)
				&& !resolution.equals(Constants.READ_RENAME)
				&& !resolution.equals(Constants.READ_DELETE))
		{
			List params = new Vector();
			params.add(mResolution);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "14", params);
		}
		return resolution;
	}

	/**
	 * Reads the action which should happen with the file after an error for the
	 * for the given system name from the standard configuration and checks its
	 * conformity with the allowed ones:
	 * <p>
	 * <dl>
	 * <dd><code>Preserve</code> &nbsp;-file is remained without
	 * modifications
	 * <dd><code>Rename</code> &nbsp;&nbsp;&nbsp;- file is renamed
	 * <dd><code>Delete</code> &nbsp;&nbsp;&nbsp;- file is deleted
	 * </dl>
	 * 
	 * @param config Instance of the
	 *            {@link net.sf.xbus.base.core.config.Configuration}
	 * @param system Sytem name which resoltion must be read.
	 * @return exist resolution (Preserve, Rename or Delete) as String
	 * @exception XException if resolution is falsh or any errors occurs
	 */
	private String retrieveOnError(Configuration config, String system)
			throws XException
	{
		String onError = config.getValue(Constants.CHAPTER_SYSTEM, system,
				"OnError");
		if (!onError.equals(Constants.READ_PRESERVE)
				&& !onError.equals(Constants.READ_RENAME)
				&& !onError.equals(Constants.READ_DELETE))
		{
			List params = new Vector();
			params.add(onError);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "15", params);
		}
		return onError;
	}

	/**
	 * Reads the file encoding for the given system name from the
	 * {@link net.sf.xbus.base.core.config.Configuration}. If this one is
	 * non-existent, returns default value from the system property.
	 * 
	 * @param system name which encoding must be read
	 * @return file encoding from the standart configuration or default value
	 *         from the system property
	 * @exception XException if any errors occurs.
	 */
	private String retrieveEncoding(String system) throws XException
	{
		Configuration config = Configuration.getInstance();
		String configEncoding = config.getValueOptional(
				Constants.CHAPTER_SYSTEM, system, Constants.KEY_ENCODING);

		return (configEncoding == null)
				? Constants.SYS_ENCODING
				: configEncoding;
	}
}
