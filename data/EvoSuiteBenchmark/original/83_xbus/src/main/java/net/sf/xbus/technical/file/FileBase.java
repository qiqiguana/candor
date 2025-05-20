package net.sf.xbus.technical.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;

/**
 * Class <code>FileBase</code> is the abstract base class for the
 * {@link FileReceiver}and {@link FileSender}classes.
 * <p>
 * It provides the methods for the copying, renaming and deleting physical
 * files.
 */
public abstract class FileBase
{
	/**
	 * Reads the file encoding for the given system name from the
	 * {@link net.sf.xbus.base.core.config.Configuration}. If this one is
	 * non-existent, returns default value from the system property.
	 * 
	 * @param system name which encoding must be read
	 * @return file encoding from the standart configuration or default value
	 *         from the system property
	 * @exception XException if any error occurs.
	 */
	static public String getEncoding(String system) throws XException
	{
		Configuration config = Configuration.getInstance();
		String configEncoding = config.getValueOptional(
				Constants.CHAPTER_SYSTEM, system, Constants.KEY_ENCODING);

		return (configEncoding == null)
				? Constants.SYS_ENCODING
				: configEncoding;

	}

	/**
	 * Renames one file to another denoted by these abstract pathnames.
	 * 
	 * @param srcFilename abstract pathname for the file to be renamed
	 * @param destFilename abstract pathname for the named file
	 * @exception XException if any error occurs
	 */

	protected void renameFile(String srcFilename, String destFilename)
			throws XException
	{
		File srcFile = new File(srcFilename);
		File destFile = new File(destFilename);

		// delete destination file if it exist
		deleteFile(destFilename);

		// rename src file to destination
		try
		{
			if (!srcFile.renameTo(destFile))
			{
				Vector params = new Vector();
				params.add(srcFilename);
				params.add(destFilename);
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FILE, "32", params);
			}
		}
		catch (SecurityException e)
		{
			Vector params = new Vector();
			params.add(srcFilename);
			params.add(destFilename);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "32", params);
		}
	}

	/**
	 * Copies data by opening FileInput/OutputStreams from the one file to
	 * another denoted by these abstract pathnames
	 * 
	 * @param sourceFile abstract pathname of the source file
	 * @param targetFile abstract pathname of the target file
	 * @exception XException if any error occurs
	 */
	protected void copyFile(String sourceFile, String targetFile)
			throws XException
	{
		FileChannel srcChannel = null;
		FileOutputStream fos = null;
		FileChannel dstChannel = null;

		try
		{
			// Get a file channel for the SourceFile
			File lockSourceFile = new File(sourceFile);
			srcChannel = new RandomAccessFile(lockSourceFile, "r")
					.getChannel();

			// Create channel on the destination
			fos = new FileOutputStream(targetFile);
			dstChannel = fos.getChannel();

			// Copy file contents from source to destination
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
		finally
		{
			try
			{
				if (srcChannel != null)
				{
					srcChannel.close();
				}
				if (fos != null)
				{
					fos.close();
				}
				if (dstChannel != null)
				{
					dstChannel.close();
				}
			}
			catch (IOException e)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FILE, "0", e);
			}
		}
	}
	/**
	 * Deletes the file or directory denoted by this abstract pathname. If this
	 * pathname denotes a directory, then the directory must be empty in order
	 * to be deleted.
	 * 
	 * @param srcFilename the name of the file to be deleted
	 * @exception XException if something goes wrong
	 */

	protected void deleteFile(String srcFilename) throws XException
	{
		File srcFile = new File(srcFilename);
		if (srcFile.exists())
		{
			try
			{
				if (!srcFile.delete())
				{
					Trace.error(new StringBuffer().append(
							"unable to delete the original file: ").append(
							srcFilename).toString());
				}
			}
			catch (SecurityException e)
			{
				Vector params = new Vector();
				params.add(srcFile.toString());
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FILE, "32", params);
			}
		}
	}

	/**
	 * @param filename name of the file
	 * @return the length of the given file
	 */
	protected long getFileLength(String filename)
	{
		File file = new File(filename);
		return file.length();
	}

	/**
	 * @param filename name of the file
	 * @return the last byte of the given file
	 * @throws XException if something goes wrong
	 */
	protected int getLastByteOfFile(String filename) throws XException
	{
		int lastByte = -1;
		try
		{
			File file = new File(filename);
			if (file.length() > 0)
			{
				FileInputStream readingFile;
				readingFile = new FileInputStream(filename);
				readingFile.skip(file.length() - 1);
				lastByte = readingFile.read();
				readingFile.close();
			}
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
		return lastByte;
	}

}
