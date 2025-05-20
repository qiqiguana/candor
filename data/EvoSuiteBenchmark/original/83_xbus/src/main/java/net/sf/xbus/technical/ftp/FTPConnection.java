package net.sf.xbus.technical.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.Hashtable;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * <code>FTPConnection</code> manages connections to FTP servers and provide
 * methods for read and write access of files via FTP.
 */
public class FTPConnection
{
	static final private String CHAPTER_FTPCONNECTION = "FTPConnection";

	/*
	 * Variables used to implement the Singleton pattern
	 */
	private static Hashtable mFTPConnections = new Hashtable();

	private static final Object classLock = FTPConnection.class;

	/*
	 * Other variables
	 */
	private String mName = null;

	private FTPClient mFTPClient = null;

	private boolean mOpen = false;

	private String mHost = null;

	private int mPort = 0;

	/**
	 * The constructor is private, instances of <code>FTPConnection</code> can
	 * only be generated via the method <code>getInstance()</code>. Each
	 * instance is put in a <code>Hashtable</code> with the concatenation of
	 * the thread name and the connection name as the key.
	 */
	private FTPConnection(String name)
	{
		mName = name;
		mFTPConnections.put(getFullName(name), this);
	}

	/**
	 * Delivers an open instance of <code>FTPConnection</code>.
	 * <p />
	 * 
	 * If it is the first call with this name for the actual thread, a new
	 * <code>FTPConnection</code> object will be created. Subsequent calls in
	 * this thread will deliver the object, that has been created by the first
	 * call.
	 * 
	 * @param name
	 *            name of the FTPConnection
	 * @return an open FTPConnection
	 * @throws XException
	 *             if something goes wrong
	 */
	public static FTPConnection getInstance(String name) throws XException
	{
		synchronized (classLock)
		{
			FTPConnection ftpConnection = (FTPConnection) mFTPConnections
					.get(getFullName(name));

			if (ftpConnection == null)
			{
				ftpConnection = new FTPConnection(name);
			}

			ftpConnection.open(false);
			return ftpConnection;
		}
	}

	/**
	 * Opens a connection to a FTP server, does the login and sets the file
	 * type.
	 * 
	 * @param force
	 *            Forces the connection to be opened
	 * @throws XException
	 *             if something goes wrong
	 */
	private void open(boolean force) throws XException
	{
		if ((!mOpen) || force)
		{
			mFTPClient = new FTPClient();

			/*
			 * Connect to the FTP Server
			 */
			Configuration config = Configuration.getInstance();
			mHost = config.getValue(CHAPTER_FTPCONNECTION, mName, "Host");
			mPort = config.getValueAsIntOptional(CHAPTER_FTPCONNECTION, mName,
					"Port");

			try
			{
				if (mPort > 0)
				{
					mFTPClient.connect(mHost, mPort);
				}
				else
				{
					mFTPClient.connect(mHost);
				}

				int reply = getReplyCode();

				if (!FTPReply.isPositiveCompletion(reply))
				{
					close();
					Vector params = new Vector(2);
					params.add(mHost);
					params.add(getReplyString());
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_FTP, "1", params);
				}

				mOpen = true;

				/*
				 * Login to the FTP server
				 */
				String user = config.getValue(CHAPTER_FTPCONNECTION, mName,
						"User");
				String password = config.getValueOptional(
						CHAPTER_FTPCONNECTION, mName, "Password");
				String account = config.getValueOptional(CHAPTER_FTPCONNECTION,
						mName, "Account");

				boolean successful = false;
				if (account == null)
				{
					successful = mFTPClient.login(user, password);
				}
				else
				{
					successful = mFTPClient.login(user, password, account);
				}

				if (!successful)
				{
					close();
					Vector params = new Vector(3);
					params.add(mHost);
					params.add(user);
					if (account == null)
					{
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_FTP, "2", params);

					}
					else
					{
						params.add(user);
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_FTP, "3", params);
					}

				}

				/*
				 * Set the file type
				 */
				int fileTypeInt = 0;
				String fileTypeString = config.getValueOptional(
						CHAPTER_FTPCONNECTION, mName, "FileType");
				if (fileTypeString == null)
				{
					fileTypeString = "ASCII";
				}
				if (fileTypeString.toUpperCase().equals("ASCII"))
				{
					fileTypeInt = FTP.ASCII_FILE_TYPE;
				}
				else if (fileTypeString.toUpperCase().equals("BINARY"))
				{
					fileTypeInt = FTP.BINARY_FILE_TYPE;
				}
				else
				{
					close();
					Vector params = new Vector(1);
					params.add(fileTypeString);
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_FTP, "2", params);
				}
				mFTPClient.setFileType(fileTypeInt);
			}
			catch (XException e)
			{
				close();
				throw e;
			}
			catch (SocketException e)
			{
				close();
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FTP, "0", e);
			}
			catch (IOException e)
			{
				close();
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FTP, "0", e);
			}
		}
	}

	/**
	 * Closes the connection to the FTP server
	 */
	public void close()
	{
		if (mOpen)
		{
			try
			{
				if (mFTPClient != null)
				{
					mFTPClient.disconnect();
				}
			}
			catch (Exception e)
			{
				Trace.warn("Cannot disconnect from FTP server for connection "
						+ mName);
			}
		}

		mOpen = false;
	}

	/**
	 * Returns the reply code send by the FTP server after an operation
	 */
	private int getReplyCode()
	{
		return mFTPClient.getReplyCode();
	}

	/**
	 * Returns the reply string send by the FTP server after an operation
	 */
	private String getReplyString()
	{
		return mFTPClient.getReplyString();
	}

	/**
	 * Retrieves the content of a file from the FTP server.
	 * 
	 * @param workDir
	 *            working directory where the file is stored, may be null
	 * @param fileName
	 *            name of the file without any directory information
	 * @param encoding
	 *            encoding of the file
	 * @return content of the file
	 * @throws XException
	 *             if something goes wrong, e.g. the file doesn't exist
	 */
	public String retrieveFile(String workDir, String fileName, String encoding)
			throws XException
	{
		try
		{
			changeWorkDir(workDir);

			ByteArrayOutputStream outStream = new ByteArrayOutputStream();

			boolean successful = mFTPClient.retrieveFile(fileName, outStream);
			if (!successful)
			{
				outStream.close();
				Vector params = new Vector(2);
				params.add(fileName);
				params.add(getReplyString());
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FTP, "5", params);
			}
			String retString = outStream.toString(encoding);
			outStream.close();
			return retString;
		}
		catch (Throwable e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_FTP,
					"0", e);
		}

	}

	/**
	 * Changes the working directory used for file operations.
	 * 
	 * @param workDir
	 *            name of the new working directory
	 * @throws XException
	 *             if something goes wrong
	 */
	private void changeWorkDirInternal(String workDir) throws FTPException,
			IOException
	{
		if (!mFTPClient.changeWorkingDirectory(workDir))
		{
			throw new FTPException();
		}
	}

	/**
	 * Changes the working directory used for file operations.
	 * 
	 * @param workDir
	 *            name of the new working directory
	 * @throws XException
	 *             if something goes wrong
	 */
	private void changeWorkDir(String workDir) throws XException
	{
		try
		{
			if (workDir != null)
			{
				changeWorkDirInternal(workDir);
			}
		}
		catch (Exception e1)
		{
			try
			{
				/*
				 * Reopen connection and try a second time
				 */
				Trace
						.warn("FTP connection may be gone, will try a second time");
				open(true);
				changeWorkDirInternal(workDir);
			}
			catch (FTPException e)
			{
				Vector params = new Vector(1);
				params.add(workDir);
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FTP, "12", params);
			}
			catch (IOException e)
			{

				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FTP, "0");
			}

		}
	}

	/**
	 * Writes the given data to a file on the FTP server. Existing files will be
	 * overwritten.
	 * 
	 * @param data
	 *            new content of the file on the server
	 * @param workDir
	 *            working directory where the file shall be stored, may be null
	 * @param fileName
	 *            name of the file without any directory information
	 * @param encoding
	 *            encoding of the file
	 * @throws XException
	 *             if something goes wrong, e.g. the working directory doesn't
	 *             exist
	 */
	public void storeFile(String data, String workDir, String fileName,
			String encoding) throws XException
	{
		try
		{
			changeWorkDir(workDir);

			ByteArrayInputStream inStream = new ByteArrayInputStream(data
					.getBytes(encoding));
			boolean successful = mFTPClient.storeFile(fileName, inStream);
			inStream.close();
			if (!successful)
			{
				Vector params = new Vector(2);
				params.add(fileName);
				params.add(getReplyString());
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FTP, "9", params);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_FTP,
					"0", e);
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_FTP,
					"0", e);
		}
	}

	/**
	 * Renames a file on the FTP server.
	 * 
	 * @param workDir
	 *            working directory, may be null
	 * @param fromFile
	 *            old name of the file, without any directory information
	 * @param toFile
	 *            new name of the file, without any directory information
	 * @throws XException
	 *             if something goes wrong, e.g. the <code>fromFile</code>
	 *             doesn't exist
	 */
	public void rename(String workDir, String fromFile, String toFile)
			throws XException
	{
		try
		{
			changeWorkDir(workDir);

			boolean successful = mFTPClient.rename(fromFile, toFile);
			if (!successful)
			{
				Vector params = new Vector(3);
				params.add(fromFile);
				params.add(toFile);
				params.add(getReplyString());
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FTP, "7", params);
			}
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_FTP,
					"0", e);
		}
	}

	/**
	 * Deletes a file on the FTP server.
	 * 
	 * @param workDir
	 *            working directory, may be null
	 * @param fileName
	 *            name of the file that shall be deleted, without any directory
	 *            information
	 * @throws XException
	 *             if something goes wrong, e.g. the file doesn't exist
	 */
	public void delete(String workDir, String fileName) throws XException
	{
		if (mOpen)
		{
			try
			{
				changeWorkDir(workDir);

				boolean successful = mFTPClient.deleteFile(fileName);
				if (!successful)
				{
					Vector params = new Vector(2);
					params.add(fileName);
					params.add(getReplyString());
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_FTP, "8", params);
				}
			}
			catch (IOException e)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FTP, "0", e);
			}
		}
	}

	/**
	 * Checks if a file exists on the FTP server.
	 * 
	 * @param workDir
	 *            working directory, may be null
	 * @param fileName
	 *            name of the file, without any directory information
	 * @return <code>true</code> if the file exists, <code>false</code> if
	 *         not
	 * @throws XException
	 *             if something goes wrong
	 */
	public boolean existsFile(String workDir, String fileName)
			throws XException
	{
		try
		{
			changeWorkDir(workDir);

			String[] files = mFTPClient.listNames(fileName);
			if ((files != null) && (files.length == 1)
					&& (fileName.equals(files[0])))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_FTP,
					"0", e);
		}
	}

	/**
	 * Returns a string containing host, port, working directory and name of the
	 * file, mainly used for tracing.
	 * 
	 * @param workDir
	 *            working directory, may be null
	 * @param fileName
	 *            name of the file, without any directory information
	 * @return string as described above
	 */
	public String getFTPName(String workDir, String fileName)
	{
		StringBuffer buffer = new StringBuffer("ftp://").append(mHost);
		if (mPort > 0)
		{
			buffer.append(":").append(mPort);
		}
		if (workDir != null)
		{
			buffer.append(workDir);
		}
		buffer.append("/");
		buffer.append(fileName);

		return buffer.toString();
	}

	/**
	 * Extracts the name of the directory from a complete file name.
	 * 
	 * @param name
	 *            complete name of a file, containing directory and file name
	 * @return name of the directory with leading slash, <code>null</code> if
	 *         the name doesn't contain any directory information
	 */
	static public String getWorkingDirectory(String name)
	{
		String workDir = null;
		int index = 0;

		if ((index = name.lastIndexOf("/")) >= 0)
		{
			workDir = name.substring(0, index);
		}

		if ((workDir != null) && (!workDir.startsWith("/")))
		{
			StringBuffer newWorkDir = new StringBuffer(workDir.length() + 1);
			newWorkDir.append("/").append(workDir);
			workDir = newWorkDir.toString();
		}

		return workDir;
	}

	/**
	 * Extracts the name of the file itself from a complete file name.
	 * 
	 * @param name
	 *            complete name of a file, containing directory and file name
	 * @return name of the file itself
	 * @throws XException
	 *             if the name of the file can't be extracted, e.g. the given
	 *             name ends with a slash
	 */
	static public String getFileName(String name) throws XException
	{
		String fileName = null;
		int index = 0;
		int lastPos = name.length() - 1;

		if ((index = name.lastIndexOf("/")) < 0)
		{
			/*
			 * no slash => no directory
			 */
			fileName = name;
		}
		else
		{
			if (index < lastPos)
			{
				/*
				 * There is at least one character after the slash
				 */
				fileName = name.substring(index + 1);
			}
		}

		if (fileName == null)
		{
			Vector params = new Vector(1);
			params.add(name);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_FTP,
					"11", params);
		}

		return fileName;
	}

	static private String getFullName(String name)
	{
		return new StringBuffer().append(name).append(".").append(
				Thread.currentThread().getName()).toString();
	}
}
