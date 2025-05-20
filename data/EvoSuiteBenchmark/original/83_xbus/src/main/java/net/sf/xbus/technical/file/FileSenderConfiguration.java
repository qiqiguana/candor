package net.sf.xbus.technical.file;

import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * TODO Kommentierung
 */
public class FileSenderConfiguration
{

	/**
	 * The file paths. Only multiple ones in case of broadcast. Otherwise only
	 * the first array element is filled.
	 */
	private String[] mFilenames = null;

	private String mResolution = null;
	private String mEncoding = null;

	/**
	 * Reads some configuration entries and stores them in class variables.
	 * 
	 * @throws XException if something goes wrong
	 */
	public FileSenderConfiguration(XBUSSystem xbusSystem) throws XException
	{
		Configuration config = Configuration.getInstance();
		mResolution = retrieveResolution(xbusSystem.getName());
		mEncoding = retrieveEncoding(xbusSystem.getName());
		String filenameString = config.getValue(Constants.CHAPTER_SYSTEM,
				xbusSystem.getName(), Constants.KEY_FILENAME);
		if (xbusSystem.getBroadcast())
			xbusSystem.getBroadcastData(filenameString);
		mFilenames = xbusSystem.replaceAllMarkers(filenameString);
	}

	public String getEncoding()
	{
		return mEncoding;
	}

	public String[] getFileNames()
	{
		return mFilenames;
	}

	public String getResolution()
	{
		return mResolution;
	}
	/**
	 * Reads conflict resolution (resolved action when the file already exists)
	 * for the given system name from the configuration and checks its
	 * conformity with the allowed ones:
	 * <dl>
	 * <dd>Append
	 * <dd>Overwrie
	 * <dd>Error
	 * </dl>
	 * 
	 * @param system Sytem name which file resolution must be read.
	 * @return conflict resolution (Append, Overwrite or Error) as String
	 * @exception XException if resolution is false or an errors occurs
	 */
	public String retrieveResolution(String system) throws XException
	{
		String resolution = Configuration.getInstance().getValue(
				Constants.CHAPTER_SYSTEM, system, Constants.KEY_SEND_RESOL);

		if (!resolution.equals(Constants.WRITE_APPEND)
				&& !resolution.equals(Constants.WRITE_ERROR)
				&& !resolution.equals(Constants.WRITE_OVERWRITE)
				&& !resolution.equals(Constants.WRITE_RENAME))
		{
			List params = new Vector();
			params.add(resolution);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "28", params);
		}

		return resolution;
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
		String configEncoding = Configuration.getInstance().getValueOptional(
				Constants.CHAPTER_SYSTEM, system, Constants.KEY_ENCODING);

		return (configEncoding == null)
				? Constants.SYS_ENCODING
				: configEncoding;
	}
}
