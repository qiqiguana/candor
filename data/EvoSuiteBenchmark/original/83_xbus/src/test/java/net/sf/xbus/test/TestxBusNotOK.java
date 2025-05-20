package net.sf.xbus.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.bootstrap.JavaReceiverBootstrap;
import net.sf.xbus.bootstrap.ReceiverSingleBootstrap;
import net.sf.xbus.protocol.xml.XMLMessageImplementation;

/**
 * Automated tests of the complete xBus' functionality.
 */
public class TestxBusNotOK extends TestxBus
{
	protected static final String TEST = Constants.XBUS_HOME
			+ Constants.FILE_SEPERATOR + "test";

	protected static final String TEST_REF = TEST + Constants.FILE_SEPERATOR
			+ "reference";

	public static final String TEST_RUN = TEST + Constants.FILE_SEPERATOR
			+ "run";

	protected static final String TEST_DIFF = TEST + Constants.FILE_SEPERATOR
			+ "report" + Constants.FILE_SEPERATOR + "diff";

	protected static final String ERROR_FILE = TEST_RUN
			+ Constants.FILE_SEPERATOR + "Errors.txt";

	/**
	 * Default constructor
	 */
	public TestxBusNotOK(String name)
	{
		super(name);
	}

	/**
	 * A test runner expects a static method suite as the entry point to get a
	 * test to run
	 */
	public static Test suite()
	{
		TestSuite suite = new TestSuite();

		// Error in Sender
		suite.addTest(new TestxBusNotOK("test1001"));

		// System-dependent DTD check fails
		suite.addTest(new TestxBusNotOK("test1002"));

		// System-dependent Schema check fails
		suite.addTest(new TestxBusNotOK("test1003"));

		// MQReceiver with OnError=Delete
		suite.addTest(new TestxBusNotOK("test1004"));

		// MQReceiver with OnError=Preserve
		suite.addTest(new TestxBusNotOK("test1005"));

		// POP3Receiver with OnError=Delete
		suite.addTest(new TestxBusNotOK("test1006"));

		// POP3Receiver with OnError=Preserve
		suite.addTest(new TestxBusNotOK("test1007"));

		// FileReceiverService Preserve / Preserve
		suite.addTest(new TestxBusNotOK("test1008"));

		// FileReceiverService Preserve / Delete
		suite.addTest(new TestxBusNotOK("test1009"));

		// FileReceiverService Preserve / Rename
		suite.addTest(new TestxBusNotOK("test1010"));

		// FileReceiverService Delete / Preserve
		suite.addTest(new TestxBusNotOK("test1011"));

		// FileReceiverService Delete / Delete
		suite.addTest(new TestxBusNotOK("test1012"));

		// FileReceiverService Delete / Rename
		suite.addTest(new TestxBusNotOK("test1013"));

		// FTPReceiver Preserve
		suite.addTest(new TestxBusNotOK("test1014"));

		// FTPReceiver Delete
		suite.addTest(new TestxBusNotOK("test1015"));

		// FTPReceiver Rename
		suite.addTest(new TestxBusNotOK("test1016"));

		// HTTP Authentication
		suite.addTest(new TestxBusNotOK("test1017"));

		return suite;

	}

	/**
	 * Error in Sender
	 */
	public void test1001()
	{
		String inFileXML = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test1001Infile.xml";
		String outFileXML = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test1001Outfile.xml";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test1001InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * The inFile must exist
		 */
		if (!new File(inFileXML).isFile())
		{
			fail("File " + inFileXML + " doesn't exist anymore");
		}

		/*
		 * The outFile must not exist
		 */
		if (new File(outFileXML).isFile())
		{
			fail("File " + outFileXML + " doesn't exist anymore");
		}

		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		assertTrue("Test1001InFile not found in " + ERROR_FILE, errors
				.indexOf("Test1001InFile") >= 0);
		assertTrue("Test1001Outfile not found in " + ERROR_FILE, errors
				.indexOf("Test1001Outfile") >= 0);
	}

	/**
	 * System-dependent DTD check fails
	 */
	public void test1002()
	{
		String inFileXML = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test1002Infile.xml";

		try
		{
			XBUSSystem source = new XBUSSystem("Test1002InFile");
			String xmlString = readFile(inFileXML);
			XMLMessageImplementation xmlMessage = new XMLMessageImplementation(
					source);
			xmlMessage.setRequestText(xmlString, source);
		}
		catch (XException e)
		{
			assertEquals("I_00_004_0 Element type \"Title\" must be declared.",
					e.getMessage());
			return;
		}

		fail("Exception of DTD check should have been thrown.");

	}

	/**
	 * System-dependent Schema check fails
	 */
	public void test1003()
	{
		String inFileXML = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test1003Infile.xml";

		try
		{
			XBUSSystem source = new XBUSSystem("Test1003InFile");
			String xmlString = readFile(inFileXML);
			XMLMessageImplementation xmlMessage = new XMLMessageImplementation(
					source);
			xmlMessage.setRequestText(xmlString, source);
		}
		catch (XException e)
		{
			assertEquals(
					"I_00_004_0 cvc-complex-type.2.4.d: Invalid content was found starting with element 'Salary'. No child element is expected at this point.",
					e.getMessage());
			return;
		}

		fail("Exception of DTD check should have been thrown.");

	}

	/**
	 * MQReceiver with OnError=Delete
	 */
	public void test1004()
	{
		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1004BufferQueue");
		if (occurences <= 0)
		{
			fail("Test1004BufferQueue not found in " + ERROR_FILE);
		}
		else if (occurences > 1)
		{
			fail("Test1004BufferQueue found " + occurences + " times in "
					+ ERROR_FILE);
		}

		/*
		 * Check the DeletedMessageStore
		 */
		try
		{
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1004BufferQueue");
			if (countFiles <= 0)
			{
				fail("No file found in DeletedMessageStore");
			}
			else if (countFiles > 1)
			{
				fail("More than one file in DeletedMessageStore");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * MQReceiver with OnError=Preserve
	 */
	public void test1005()
	{
		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1005BufferQueue");
		if (occurences != 4)
		{
			fail("Test1005BufferQueue found " + occurences + " times in "
					+ ERROR_FILE + ", should be 4");
		}

		occurences = findOccurences(
				errors,
				"Stopping ReceiverThread Test1005BufferQueue because of maximum amount of errors!");
		if (occurences != 1)
		{
			fail("Stopping Test1005BufferQueue found " + occurences
					+ " times in " + ERROR_FILE + ", should be 1");
		}

		/*
		 * Check the DeletedMessageStore
		 */
		try
		{
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1005BufferQueue");
			if (countFiles > 0)
			{
				fail("File found in DeletedMessageStore");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * POP3Receiver with OnError=Delete
	 */
	public void test1006()
	{
		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1006EmailReceive");
		if (occurences <= 0)
		{
			fail("Test1006EmailReceive not found in " + ERROR_FILE);
		}
		else if (occurences > 1)
		{
			fail("Test1006EmailReceive found " + occurences + " times in "
					+ ERROR_FILE);
		}

		/*
		 * Check the DeletedMessageStore
		 */
		try
		{
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1006EmailReceive");
			if (countFiles <= 0)
			{
				fail("No file found in DeletedMessageStore");
			}
			else if (countFiles > 1)
			{
				fail("More than one file in DeletedMessageStore");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * POP3Receiver with OnError=Preserve
	 */
	public void test1007()
	{
		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1007EmailReceive");
		if (occurences != 4)
		{
			fail("Test1007EmailReceive found " + occurences + " times in "
					+ ERROR_FILE + ", should be 4");
		}

		occurences = findOccurences(
				errors,
				"Stopping ReceiverThread Test1007EmailReceive because of maximum amount of errors!");
		if (occurences != 1)
		{
			fail("Stopping Test1007EmailReceive found " + occurences
					+ " times in " + ERROR_FILE + ", should be 1");
		}

		/*
		 * Check the DeletedMessageStore
		 */
		try
		{
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1007EmailReceive");
			if (countFiles > 0)
			{
				fail("File found in DeletedMessageStore");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * FileReceiverService Preserve / Preserve
	 */
	public void test1008()
	{
		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1008InFile");
		if (occurences != 5)
		{
			fail("Test1008InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 5");
		}

		occurences = findOccurences(errors,
				"Stopping ReceiverThread Test1008InFile because of maximum amount of errors!");
		if (occurences != 1)
		{
			fail("Stopping Test1008InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}

		try
		{
			/*
			 * Check the DeletedMessageStore
			 */
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1008");
			if (countFiles > 0)
			{
				fail("File found in DeletedMessageStore");
			}

			/*
			 * Check the inFile
			 */
			countFiles = findFiles(TEST_RUN, "Test1008Infile");
			if (countFiles != 1)
			{
				fail("File Test1008InFile not found");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * FileReceiverService Preserve / Delete
	 */
	public void test1009()
	{
		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1009InFile_1");
		if (occurences != 1)
		{
			fail("Test1009InFile_1 found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}
		occurences = findOccurences(errors, "Test1009InFile_2");
		if (occurences != 1)
		{
			fail("Test1009InFile_2 found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}

		occurences = findOccurences(errors,
				"Stopping ReceiverThread Test1009InFile");
		if (occurences != 0)
		{
			fail("Stopping Test1009InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 0");
		}

		try
		{
			/*
			 * Check the DeletedMessageStore
			 */
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1009InFile_1");
			if (countFiles != 1)
			{
				fail("File Test1009InFile_1* not found in DeletedMessageStore");
			}
			countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1009InFile_2");
			if (countFiles != 1)
			{
				fail("File Test1009InFile_2* not found in DeletedMessageStore");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * FileReceiverService Preserve / Rename
	 */
	public void test1010()
	{
		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1010InFile");
		if (occurences != 1)
		{
			fail("Test1010InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}

		occurences = findOccurences(errors,
				"Stopping ReceiverThread Test1010 because of maximum amount of errors!");
		if (occurences != 0)
		{
			fail("Stopping Test1010InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 0");
		}

		try
		{
			/*
			 * Check the DeletedMessageStore
			 */
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1010");
			if (countFiles > 0)
			{
				fail("File found in DeletedMessageStore");
			}

			/*
			 * Check the inFile
			 */
			countFiles = findFiles(TEST_RUN, "Test1010Infile.xml.200");
			if (countFiles != 1)
			{
				fail("Renamed file Test1010Infile... found " + countFiles
						+ " times, should be 1");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * FileReceiverService Delete / Preserve
	 */
	public void test1011()
	{
		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1011InFile");
		if (occurences != 5)
		{
			fail("Test1011InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 5");
		}

		occurences = findOccurences(errors,
				"Stopping ReceiverThread Test1011InFile because of maximum amount of errors!");
		if (occurences != 1)
		{
			fail("Stopping Test1011InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}

		try
		{
			/*
			 * Check the DeletedMessageStore
			 */
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1011");
			if (countFiles > 0)
			{
				fail("File found in DeletedMessageStore");
			}

			/*
			 * Check the inFile
			 */
			countFiles = findFiles(TEST_RUN, "Test1011Infile");
			if (countFiles != 1)
			{
				fail("File Test1011InFile not found");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * FileReceiverService Delete / Delete
	 */
	public void test1012()
	{
		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1012InFile_1");
		if (occurences != 1)
		{
			fail("Test1012InFile_1 found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}
		occurences = findOccurences(errors, "Test1012InFile_2");
		if (occurences != 1)
		{
			fail("Test1012InFile_2 found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}

		occurences = findOccurences(errors,
				"Stopping ReceiverThread Test1012InFile");
		if (occurences != 0)
		{
			fail("Stopping Test1012InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 0");
		}

		try
		{
			/*
			 * Check the DeletedMessageStore
			 */
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1012InFile_1");
			if (countFiles != 1)
			{
				fail("File Test1012InFile_1* not found in DeletedMessageStore");
			}
			countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1012InFile_2");
			if (countFiles != 1)
			{
				fail("File Test1012InFile_2* not found in DeletedMessageStore");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * FileReceiverService Delete / Rename
	 */
	public void test1013()
	{
		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1013InFile");
		if (occurences != 1)
		{
			fail("Test1013InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}

		occurences = findOccurences(errors,
				"Stopping ReceiverThread Test1013 because of maximum amount of errors!");
		if (occurences != 0)
		{
			fail("Stopping Test1013InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 0");
		}

		try
		{
			/*
			 * Check the DeletedMessageStore
			 */
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1013");
			if (countFiles > 0)
			{
				fail("File found in DeletedMessageStore");
			}

			/*
			 * Check the inFile
			 */
			countFiles = findFiles(TEST_RUN, "Test1013Infile.xml.200");
			if (countFiles != 1)
			{
				fail("Renamed file Test1013Infile... found " + countFiles
						+ " times, should be 1");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * FTPReceiver/ Preserve
	 */
	public void test1014()
	{
		/*
		 * Calling the FTPReceiver
		 */
		String[] args =
		{ "Test1014InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1014InFile");
		if (occurences != 1)
		{
			fail("Test1014InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}

		try
		{
			/*
			 * Check the DeletedMessageStore
			 */
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1014");
			if (countFiles > 0)
			{
				fail("File found in DeletedMessageStore");
			}

			/*
			 * Check the inFile
			 */
			countFiles = findFiles(
					TEST_RUN + Constants.FILE_SEPERATOR + "test",
					"Test1014Infile");
			if (countFiles != 1)
			{
				fail("File Test1014Infile not found");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * FTPReceiver Delete
	 */
	public void test1015()
	{
		/*
		 * Calling the FTPReceiver
		 */
		String[] args =
		{ "Test1015InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1015InFile");
		if (occurences != 1)
		{
			fail("Test1015InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}
		try
		{
			/*
			 * Check the DeletedMessageStore
			 */
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1015InFile");
			if (countFiles != 1)
			{
				fail("File Test1015InFile not found in DeletedMessageStore");
			}

			/*
			 * Check the inFile
			 */
			countFiles = findFiles(
					TEST_RUN + Constants.FILE_SEPERATOR + "test",
					"Test1015Infile");
			if (countFiles != 0)
			{
				fail("File Test1015Infile... found " + countFiles
						+ " times, should be 0");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * FTPReceiver Rename
	 */
	public void test1016()
	{
		/*
		 * Calling the FTPReceiver
		 */
		String[] args =
		{ "Test1016InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check the errorfile
		 */
		String errors = null;
		try
		{
			errors = readFile(ERROR_FILE);
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
		int occurences = findOccurences(errors, "Test1016InFile");
		if (occurences != 1)
		{
			fail("Test1016InFile found " + occurences + " times in "
					+ ERROR_FILE + ", should be 1");
		}

		try
		{
			/*
			 * Check the DeletedMessageStore
			 */
			int countFiles = findFiles(
					Configuration.getInstance().getValue(
							Constants.CHAPTER_BASE, "DeletedMessageStore",
							"Directory"), "Test1016");
			if (countFiles > 0)
			{
				fail("File found in DeletedMessageStore");
			}

			/*
			 * Check the inFile
			 */
			countFiles = findFiles(
					TEST_RUN + Constants.FILE_SEPERATOR + "test",
					"Test1016Infile.xml.200");
			if (countFiles != 1)
			{
				fail("Renamed file Test1016Infile... found " + countFiles
						+ " times, should be 1");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * HTTP Authentication
	 */
	public void test1017()
	{
		try
		{
			JavaReceiverBootstrap.receive("Test1017Receiver",
					"Test Authentication");
		}
		catch (XException e)
		{

			assertEquals(e.getMessage(),
					"E_01_006_3 Catched exception: E_01_004_1 Response from HTTP-Send: 401.");
			return;
		}

		fail("Exception should have been thrown");
	}

	/**
	 * Reads a file.
	 * 
	 * @param filename
	 *            the name of the file including the directory
	 * @return the content of the file
	 */
	private String readFile(String filename) throws XException
	{
		StringBuffer retBuffer = new StringBuffer();
		File sourceFile = new File(filename);
		String zeile;
		BufferedReader buffReader = null;

		try
		{
			buffReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(sourceFile)));

			if ((zeile = buffReader.readLine()) != null)
			{
				retBuffer.append(zeile);
			} // if((zeile = buffReader.readLine()) != null)

			while ((zeile = buffReader.readLine()) != null)
			{
				retBuffer.append(Constants.LINE_SEPERATOR);
				retBuffer.append(zeile);
			} // while ((zeile = buffReader.readLine()) != null)
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TESTDRIVER,
					Constants.PACKAGE_TESTDRIVER_TESTDRIVER, "0", e);
		}
		finally
		{
			if (buffReader != null)
			{
				try
				{
					buffReader.close();
				}
				catch (IOException e1)
				{
					// do nothing
				}
			}
		}

		return retBuffer.toString();
	}

	private int findFiles(String dir, String filename)
	{
		int retCount = 0;

		File[] files = new File(dir).listFiles();
		for (int i = 0; i < files.length; i++)
		{
			if (files[i].getName().startsWith(filename))
			{
				retCount++;
			}
		}
		return retCount;
	}

	private int findOccurences(String text, String toFind)
	{
		int retCount = 0;
		int lastPos = 0;
		while ((lastPos = text.indexOf(toFind, lastPos)) >= 0)
		{
			retCount++;
			lastPos = lastPos + toFind.length();
		}
		return retCount;
	}
}
