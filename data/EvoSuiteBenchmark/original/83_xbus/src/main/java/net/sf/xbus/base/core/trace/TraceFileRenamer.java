package net.sf.xbus.base.core.trace;

import java.io.File;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;

/**
 * The <code>TraceFileRenamer</code> class is for renaming the trace file by
 * adding a timestamp.<br>
 * This serves for preventing to large trace files. The class just consists of a
 * <code>main</code> method. This method checks the actual trace file size
 * against a configured maximally desired size and renames the file if its size
 * is too big.<br>
 * The next trace entry after a rename will construct a new trace file with the
 * configured original name.<br>
 * If no trace file name or no maximal length specification is found in the
 * configuration, nothing is done.
 * 
 * @author Stephan Düwel
 */
public class TraceFileRenamer
{

	public static void main(String[] args)
	{
		Configuration config;
		// to get the path of the trace file and the "critical" file size
		try
		{
			config = Configuration.getInstance();
			String fileName = config.getValueOptional("Base", "Trace",
					"Filename");
			// trace file name
			if (fileName != null)
			{ // file name given
				long maxFileSize = config.getValueAsLongOptional("Base",
						"Trace", "MaxFileSize");
				// maximal size
				if (maxFileSize > 0)
				{ // maximal size specified
					String filePath = Constants.XBUS_HOME
							+ Constants.FILE_SEPERATOR + "log"
							+ Constants.FILE_SEPERATOR + fileName;
					File configFile = new File(filePath);
					if (configFile.exists()
							&& configFile.length() > maxFileSize)
					{
						File newFile = new File(filePath
								+ Constants.getDateAsString());
						if (!configFile.renameTo(newFile))
							Trace.error("Trace file could not be renamed.");
					} // if (configFile.exists() &&
						// configFile.length()>maxFileSize)
				} // if (maxFileSize>0)
			} // if (fileName!=null)
		} // try
		catch (XException e)
		{ // Any exception during accessing the configuration is ignored.
		} // catch
	} // main(String[] args)

} // TraceFileRenamer
