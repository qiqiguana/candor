package net.sf.xbus.technical.as400;

import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.timeoutcall.TimedCallable;
import net.sf.xbus.base.core.trace.Trace;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.CharConverter;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileInputStream;
import com.ibm.as400.access.IFSFileOutputStream;
import com.ibm.as400.access.ProgramParameter;
import com.ibm.as400.access.QSYSObjectPathName;
import com.ibm.as400.access.SequentialFile;

/**
 * Class <code>AS400FileBase</code> is the abstract base class for the
 * {@link AS400FileReceiver} and {@link AS400FileSender} classes.
 * <p>
 * It provides the methods for the following:
 * <ul>
 * <li>Renaming files and members</li>
 * <li>Deleting files and members</li>
 * <li>Copying files</li>
 * <li>Locking files for different types of access</li>
 * <li>Calling programs</li>
 * </ul>.
 * </p>
 */
public abstract class AS400FileBase
{

	/**
	 * The char converter according to actual string encoding. It is
	 * <code>protected</code> to grant access by the subclasses.
	 */
	protected CharConverter mConverter;

	/**
	 * Returns the encoding of the data type from the
	 * {@link net.sf.xbus.base.core.config.Configuration}. If this one is
	 * non-existent, the method returns the default value from the AS400 object
	 * that corresponds to the job CCSID of the server.
	 * <p>
	 * 
	 * @param as400System AS400 object for the server that holds the files
	 * @param system system name for which encoding must be read
	 * @return character encoding from Configuration or default encoding that
	 *         corresponds to the job CCSID of the server.
	 * @exception XException if encoding cannot be determined neither from the
	 *                configurationnor by accesing the AS400
	 */
	static public String getEncoding(AS400 as400System, String system)
			throws XException
	{
		Configuration config = Configuration.getInstance();
		String configEncoding = null;
		configEncoding = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				system, Constants.KEY_ENCODING);

		if (configEncoding == null)
		{
			try
			{
				configEncoding = as400System.getJobCCSIDEncoding();
			} // try
			catch (Exception ex)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "0", ex);
			} // catch
		} // if (configEncoding == null)
		return configEncoding;
	} // getEncoding(AS400 as400System, String system)

	/**
	 * Sets a lock on the file to prevent other users from accessing the file
	 * while it is in use.
	 * <p>
	 * The following type of lock is used:
	 * <p>
	 * <code>WRITE_EXCLUSIVE_LOCK</code>&nbsp;&nbsp;&nbsp;The current Java
	 * program changes the file, and no other program can access the file.
	 * <p>
	 * <i>Note:</i> If a lock has already been obtained for this file, no
	 * action is taken.
	 * 
	 * @param as400System The AS400 system to which to connect. The system
	 *            cannot be null.
	 * @param lockFilename The integrated file system pathname of the file to be
	 *            locked. The name cannot be null.
	 * @exception XException if locking did not work
	 */
	protected void setLock(AS400 as400System, String lockFilename)
			throws XException
	{
		try
		{
			SequentialFile seqFile = new SequentialFile(as400System,
					lockFilename);
			seqFile.lock(SequentialFile.WRITE_EXCLUSIVE_LOCK);
		} // try
		catch (Exception ex)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", ex);
		} // catch
	} // setLock(AS400 as400System, String lockFilename)

	/**
	 * Releases all locks acquired via the {@link #setLock(AS400, String)}
	 * method.
	 * <p>
	 * If no locks have been explicitly obtained, no action is taken.
	 * 
	 * @param as400System The AS400 system to which to connect. The system
	 *            cannot be null.
	 * @param lockFilename The integrated file system pathname of the file to be
	 *            locked. The name cannot be null.
	 * @exception XException if the file lock can not be released
	 */
	protected void releaseLock(AS400 as400System, String lockFilename)
			throws XException
	{
		SequentialFile lockFile = new SequentialFile(as400System, lockFilename);
		if (lockFile.getExplicitLocks().length > 0)
		{ // There are locks.
			try
			{
				lockFile.releaseExplicitLocks();
			} // try
			catch (Exception ex)
			{
				List params = new Vector();
				params.add(lockFilename);
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "6", params);
			} // catch
		} // if (lockFile.getExplicitLocks().length > 0)
	} // releaseLock(AS400 as400System, String lockFilename)

	/**
	 * Return a path name with a new member name. The QSYSObjectPathName
	 * provides an integrated file system path name that represents an object in
	 * the QSYS library file system.
	 * <p>
	 * <i>For example:</i><br>
	 * <dl>
	 * <dd>/QSYS.<b>LIB</b>/QGPL.<b>LIB</b>/ACCOUNTS.<b>FILE</b>/PAYABLE.<b>MBR</b>
	 * </dl>
	 * <p>
	 * This method parses the given QSYSObjectPathName object to extract the
	 * library, object, member, and object type and builds integrated file
	 * system path name with <b>new member</b> as follows:
	 * <p>
	 * <dl>
	 * <dd>/QSYS.LIB/<b>OLDLIB</b>.LIB/<b>OLDFILE</b>.FILE/<b>NEWMEMBER</b>.MBR
	 * </dl>
	 * 
	 * @param qsysPathname represents old object in the QSYS library file system
	 * @param newMember new member name, cannot exceed 10 characters in length!
	 *            Must start with a letter.
	 * @return fully qualified integrated file system path of the new object in
	 *         the QSYS file system
	 */
	protected String getRenamedMember(QSYSObjectPathName qsysPathname,
			String newMember)
	{
		return new QSYSObjectPathName(qsysPathname.getLibraryName(),
				qsysPathname.getObjectName(), newMember, qsysPathname
						.getObjectType()).getPath();
	}

	/**
	 * Renames the integrated file system object specified by the <i>source</i>
	 * path name to have the path name of <i>dest</i>.
	 * 
	 * @param as400System AS400 system to which to connect. The system cannot be
	 *            null
	 * @param sourceFilename old abstract pathname of the integrated file system
	 *            object
	 * @param destFilename new abstract pathname of the integrated file system
	 *            object
	 * @exception XException if an error occurs while communicating with the
	 *                server.
	 */
	protected void renameFile(AS400 as400System, String sourceFilename,
			String destFilename) throws XException
	{
		IFSFile srcFile = new IFSFile(as400System, sourceFilename);
		IFSFile destFile = new IFSFile(as400System, destFilename);

		// construct temp name and temp member for destination file
		String tempName = new StringBuffer(destFile.getParent()).append(
				Constants.FILE_SEPERATOR).append("xbusTemp.MBR").toString();
		IFSFile tempFile = null;
		try
		{
			// 1. rename destination file to temp one if it exists
			if (destFile.exists())
			{
				tempFile = new IFSFile(as400System, tempName);
				if (tempFile.exists())
					if (!tempFile.delete())
					{
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_AS400, "7",
								(List) null);
					}
				if (!destFile.renameTo(tempFile))
				{
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_AS400, "8", (List) null);
				}
				// Reinitialize to check existance of the renamed member
				// Return value of renameTo is not reliable. It may be <true>
				// even if
				// the renaming was not successful.
				tempFile = new IFSFile(as400System, tempName);
				// Make sure that renaming was successful.
				if (!tempFile.exists())
				{
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_AS400, "8", (List) null);
				}
				// Reinitialise to allow further renaming
				destFile = new IFSFile(as400System, destFilename);
			} // if (destFile.exists())

			boolean renameBackTempFile = false;
			// Should the renaming of the destination member be reversed?

			// 2. rename source file to dest file
			if (srcFile.renameTo(destFile))
			{
				// Reinitialise to check existance of the destination file
				// Return value of renameTo is not reliable. It may be <true>
				// even if
				// the renaming was not successful.
				destFile = new IFSFile(as400System, destFilename);
				// Make sure that renaming was successful.
				if (!destFile.exists())
					renameBackTempFile = true;
				// delete temp member
				if (tempFile != null && tempFile.exists())
					deleteMember(as400System, tempName);
			} // if (srcFile.renameTo(destFile))
			else
				// Reverse initial renaming of the destination file member
				renameBackTempFile = true;

			// 3. Rename the destination file member bach to its original name
			// if
			// any error prevented the renaming of the source file.
			// Only necessary if the destination filemember existed before and
			// was
			// renamed to a temporyry member name.
			if (tempFile != null && renameBackTempFile)
			{ // Reverse initial renaming of the destination file member
				// Reinitialise to allow backwards renaming
				destFile = new IFSFile(as400System, destFilename);
				// rename back temp member to destination member
				tempFile.renameTo(destFile);

				List params = new Vector();
				params.add(sourceFilename);
				params.add(destFilename);
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "10", params);
			} // if (tempFile!=null && renameBackTempFile)
		} // try
		catch (Exception ex)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", ex);
		} // catch
	} // renameFile(AS400 as400System,String sourceFilename,String
		// destFilename)

	/**
	 * This method uses the installable file system classes to copy a file from
	 * one directory to another on the server.
	 * 
	 * @param as400System AS400 object for the server which holds the files.
	 * @param sourceName name the source file to be copied
	 * @param targetName name the target file to be copied to
	 * @exception XException if file processing cannot be completed
	 */
	protected void copyFile(AS400 as400System, String sourceName,
			String targetName) throws XException
	{
		byte[] buffer = new byte[1024 * 64];

		IFSFileInputStream source = null;
		IFSFileOutputStream target = null;
		try
		{
			// Open the source file for exclusive access.
			source = new IFSFileInputStream(as400System, sourceName,
					IFSFileInputStream.SHARE_NONE);

			IFSFile targetFile = new IFSFile(as400System, targetName);
			if (!targetFile.exists())
			{
				targetFile.createNewFile();
			} // if (!targetFile.exists())

			// Open the target file for exclusive access.
			target = new IFSFileOutputStream(as400System, targetName,
					IFSFileOutputStream.SHARE_NONE, false);

			// Read the first 64K bytes from the source file.
			int bytesRead = source.read(buffer);

			// While there is data in the source file copy the data from
			// the source file to the target file.
			while (bytesRead > 0)
			{
				target.write(buffer, 0, bytesRead);
				bytesRead = source.read(buffer);
			}

			// Clean up by closing the source and target file.
			source.close();
			target.close();
		} // try
		catch (Exception e)
		{
			// If any of the above operations failed trace error
			// and output the exception.
			Trace.error("Copy failed");
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		} // catch
	} // copyFile(AS400 as400System,String sourceName,String targetName)

	/**
	 * <code>deleteFile</code> deletes a file on the AS400 file system. This
	 * method is used for files in the AS400 inegrated file system sense.
	 * 
	 * @see #deleteMember(AS400, String)
	 * @param mAS400System the AS400 to interact with
	 * @param fileName the path of the file to delete
	 * @throws XException if the file cannot be deleted for any reason
	 */
	protected void deleteFile(AS400 mAS400System, String fileName)
			throws XException
	{
		SequentialFile file = new SequentialFile(mAS400System, fileName);
		try
		{
			file.delete();
		} // try
		catch (Exception ex)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", ex);
		} // catch
	} // deleteFile(AS400 mAS400System, String fileName)

	/**
	 * Deletes a member of a sequential file on the AS/400 file system. The
	 * surrounding file will continue to exist.
	 * 
	 * @param as400System the AS/400 to interact with
	 * @param fileName the name of the file and member to delete
	 * @throws XException if the member cannot be deleted for any reason
	 */
	protected void deleteMember(AS400 as400System, String fileName)
			throws XException
	{
		SequentialFile srcFile = new SequentialFile(as400System, fileName);
		try
		{
			srcFile.deleteMember();
		} // try
		catch (Exception ex)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", ex);
		} // catch
	} // deleteMember(AS400 as400System, String fileName)

	/**
	 * Calls an AS400 program.
	 * 
	 * @param as400 the AS400 to interact with
	 * @param programName the path of the program to call
	 * @param parameters the input and output paremeters of the program call
	 * @param timeout seconds before program will be stopped
	 * @throws XException if the AS400 issues an error
	 */
	protected void callAS400Program(AS400 as400, String programName,
			ProgramParameter[] parameters, int timeout) throws XException
	{
		AS400ProgramCaller caller = new AS400ProgramCaller(as400, programName,
				parameters, mConverter);
		TimedCallable tc = new TimedCallable(caller, timeout);
		try
		{
			tc.call();
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		}
	} // callAS400Program(AS400 as400,String programName,ProgramParameter[]
		// parameters)

} // AS400FileBase
