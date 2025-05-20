package net.sf.xbus.test;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.bootstrap.JavaReceiverBootstrap;
import net.sf.xbus.bootstrap.ReceiverSingleBootstrap;

/**
 * Automated tests of the complete xBus' functionality.
 */
public class TestxBus extends TestCase
{
	protected static final String TEST = Constants.XBUS_HOME
			+ Constants.FILE_SEPERATOR + "test";

	protected static final String TEST_REF = TEST + Constants.FILE_SEPERATOR
			+ "reference";

	public static final String TEST_RUN = TEST + Constants.FILE_SEPERATOR
			+ "run";

	protected static final String TEST_DIFF = TEST + Constants.FILE_SEPERATOR
			+ "report" + Constants.FILE_SEPERATOR + "diff";

	/**
	 * Default constructor
	 */
	public TestxBus(String name)
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

		// XML with simple routing
		suite.addTest(new TestxBus("test0001"));

		// RecordTypes with fixed field length
		suite.addTest(new TestxBus("test0002"));

		// XML with complex routing
		suite.addTest(new TestxBus("test0003"));

		// JavaReceiver, JavaTransformer and Invoke
		suite.addTest(new TestxBus("test0004"));

		// DBSender
		suite.addTest(new TestxBus("test0005"));

		// SOAP
		suite.addTest(new TestxBus("test0006"));

		// HTTP Streaming
		suite.addTest(new TestxBus("test0007"));

		// Additional address information
		suite.addTest(new TestxBus("test0008"));

		// ProgramSender
		suite.addTest(new TestxBus("test0009"));

		// FileLineReader/-Writer
		suite.addTest(new TestxBus("test0010"));

		// FileLineReader/-Writer with Transformation
		suite.addTest(new TestxBus("test0011"));

		// FTPReceiver
		suite.addTest(new TestxBus("test0012"));

		// LDAPReceiver currently not tested !!!
		// suite.addTest(new TestxBus("test0013"));

		// ByteArrayList in RecordTypeMessage
		suite.addTest(new TestxBus("test0014"));

		// Email with POP3XMLReceiver
		suite.addTest(new TestxBus("test0015"));

		// Email with POP3XMLReceiverThread
		suite.addTest(new TestxBus("test0016"));

		// Email with POP3Receiver
		suite.addTest(new TestxBus("test0017"));

		// Email with POP3ReceiverThread
		suite.addTest(new TestxBus("test0018"));

		// HTTPParameterSender/Receiver with URL without result
		suite.addTest(new TestxBus("test0019"));

		// TextXSLTTransformer
		suite.addTest(new TestxBus("test0020"));

		// HTTPParameterSender/Receiver without URL with result
		suite.addTest(new TestxBus("test0021"));

		// CSV message without description, default settings
		suite.addTest(new TestxBus("test0022"));

		// CSV message without description, special settings
		suite.addTest(new TestxBus("test0023"));

		// HTTP authentication
		suite.addTest(new TestxBus("test0024"));

		// XML with content based routing
		suite.addTest(new TestxBus("test0025"));

		// CSV message with description, default settings
		suite.addTest(new TestxBus("test0026"));

		// HTTPParameterSender/Receiver with WILDCARD
		suite.addTest(new TestxBus("test0027"));

		return suite;

	}

	/**
	 * XML with simple routing
	 */
	public void test0001()
	{
		String inFileName = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0001Infile.xml";

		String outFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0001Outfile.xml";
		String refFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0001Outfile.xml";
		String diffFile = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0001Outfile.diff";

		/*
		 * Infile has to be deleted
		 */
		File inFile = new File(inFileName);
		if (inFile.exists())
		{
			fail(inFileName + " should have been deleted");
		}

		/*
		 * Check Outfile for differences
		 */
		checkFiles(outFile, refFile, diffFile);
	}

	/**
	 * RecordTypes with fixed field length
	 */
	public void test0002()
	{
		String inFileWildcard = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0002Infile.dat.200*";

		String outFileDat = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0002Outfile.dat";
		String refFileDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0002Outfile.dat";
		String diffFileDat = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0002Dat.diff";

		String outFileXML = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0002Outfile.xml";
		String refFileXML = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0002Outfile.xml";
		String diffFileXML = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0002XML.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0002PartsOrderIncoming" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check wether the Infile has been renamed
		 */
		FileAcceptor acceptor;
		try
		{
			acceptor = new FileAcceptor(inFileWildcard);
			String[] files = (new File(TEST_RUN)).list(acceptor);
			if (files.length < 1)
			{
				fail("No file matching " + inFileWildcard + " found");
			}
			if (files.length > 1)
			{
				fail("More than one file matching " + inFileWildcard + " found");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}

		/*
		 * Check both Outfiles for differences
		 */
		checkFiles(outFileDat, refFileDat, diffFileDat);
		checkFiles(outFileXML, refFileXML, diffFileXML);
	}

	/**
	 * XML with complex routing
	 */
	public void test0003()
	{
		String outFile1 = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0003Outfile1.xml";
		String refFile1 = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0003Outfile1.xml";
		String diffFile1 = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0003Outfile1.diff";

		String outFile2 = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0003Outfile2.xml";
		String refFile2 = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0003Outfile2.xml";
		String diffFile2 = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0003Outfile2.diff";

		checkFiles(outFile1, refFile1, diffFile1);
		checkFiles(outFile2, refFile2, diffFile2);
	}

	/**
	 * JavaReceiver, JavaTransformer and Invoke
	 */
	public void test0004()
	{
		final String REQUEST_STRING = "Hello World!";
		final String DESTINATION = "Test0004InData";

		final String RESPONSE_STRING = "!dlroW olleH";

		try
		{
			Object response = JavaReceiverBootstrap.receive(DESTINATION,
					REQUEST_STRING);

			assertEquals(RESPONSE_STRING, response);
		}
		catch (Throwable e)
		{
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * DBSender
	 */
	public void test0005()
	{
		String inFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0005Infile.xml";
		String inRefFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0005Infile.xml";
		String inDiffFile = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0005Infile.diff";

		String outFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0005Outfile.xml";
		String refFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0005Outfile.xml";
		String diffFile = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0005Outfile.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0005InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check infile for differences
		 */
		checkFiles(inFile, inRefFile, inDiffFile);

		/*
		 * Check outfile for differences
		 */
		checkFiles(outFile, refFile, diffFile);
	}

	/**
	 * SOAP
	 */
	public void test0006()
	{
		String outFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0006Outfile.xml";
		String refFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0006Outfile.xml";
		String diffFile = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0006Outfile.diff";

		/*
		 * Check outfile for differences
		 */
		checkFiles(outFile, refFile, diffFile);
	}

	/**
	 * HTTP Streaming
	 */
	public void test0007()
	{
		String outFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0007Outfile.bmp";
		String refFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0007Outfile.bmp";

		// /*
		// * Calling the FileReceiver
		// */
		// String[] args = { "Test0007InFile" };
		// ReceiverSingleBootstrap.main(args);

		/*
		 * Check length of outfile and reference file
		 */

		long outLength = new File(outFile).length();
		long referenceLength = new File(refFile).length();
		assertEquals(outLength, referenceLength);
	}

	/**
	 * Additional address information
	 */
	public void test0008()
	{
		String outFile1 = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0008Out1.dat";
		String refFile1 = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0008Out1.dat";
		String diffFile1 = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0008Out1.diff";

		String outFile2 = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0008Out2.dat";
		String refFile2 = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0008Out2.dat";
		String diffFile2 = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0008Out2.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0008InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check outfiles for differences
		 */
		checkFiles(outFile1, refFile1, diffFile1);
		checkFiles(outFile2, refFile2, diffFile2);
	}

	/**
	 * Additional address information
	 */
	public void test0009()
	{
		String outFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0009Outfile.dat";
		String refFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0009Outfile.dat";
		String diffFile = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0009Outfile.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0009Incoming" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check outfiles for differences
		 */
		checkFiles(outFile, refFile, diffFile);
	}

	/**
	 * FileLineReader/-Writer
	 */
	public void test0010()
	{
		String outFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0010Outfile.xml";
		String refFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0010Outfile.xml";
		String diffFile = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0010Outfile.diff";

		/*
		 * Check outfiles for differences
		 */
		checkFiles(outFile, refFile, diffFile);
	}

	/**
	 * FileLineReader/-Writer with Transformation
	 */
	public void test0011()
	{
		String outFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0011Outfile.xml";
		String refFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0011Outfile.xml";
		String diffFile = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0011Outfile.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0011InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check outfiles for differences
		 */
		checkFiles(outFile, refFile, diffFile);
	}

	/**
	 * FTPReceiver
	 */
	public void test0012()
	{
		String inFileName = TEST_RUN + Constants.FILE_SEPERATOR
				+ "test/Test0012Infile_2.xml";

		String outFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "test/Test0012Outfile_SecondDealer.xml";
		String refFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0012Outfile_SecondDealer.xml";
		String diffFile = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0012Outfile_SecondDealer.diff";

		/*
		 * Calling the FTPReceiver
		 */
		String[] args =
		{ "Test0012InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Infile has to be deleted
		 */
		File inFile = new File(inFileName);
		if (inFile.exists())
		{
			fail(inFileName + " should have been deleted");
		}

		/*
		 * Check outfiles for differences
		 */
		checkFiles(outFile, refFile, diffFile);
	}

	/**
	 * LDAPSender
	 */
	public void test0013()
	{
		String outFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0013Outfile.xml";
		String refFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0013Outfile.xml";
		String diffFile = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0013Outfile.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0013InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check outfiles for differences
		 */
		checkFiles(outFile, refFile, diffFile);
	}

	/**
	 * RecordTypes with fixed field length
	 */
	public void test0014()
	{
		String inFileWildcard = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0014Infile.dat.200*";

		String outFileDat = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0014Outfile.dat";
		String refFileDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0014Outfile.dat";
		String diffFileDat = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0014Dat.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0014PartsOrderIncoming" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check wether the Infile has been renamed
		 */
		FileAcceptor acceptor;
		try
		{
			acceptor = new FileAcceptor(inFileWildcard);
			String[] files = (new File(TEST_RUN)).list(acceptor);
			if (files.length < 1)
			{
				fail("No file matching " + inFileWildcard + " found");
			}
			if (files.length > 1)
			{
				fail("More than one file matching " + inFileWildcard + " found");
			}
		}
		catch (XException e)
		{
			fail(e.getMessage());
		}

		/*
		 * Check both Outfiles for differences
		 */
		checkFiles(outFileDat, refFileDat, diffFileDat);
	}

	/**
	 * Email with POP3XMLReceiver
	 */
	public void test0015()
	{
		String outFileDat = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0015Outfile.xml";
		String refFileDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0015Outfile.xml";
		String diffFileDat = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0015Outfile.diff";

		/*
		 * There is a delay of approx. 5 seconds, before mails are delivered
		 */
		int delayTime = 5;
		try
		{
			Trace.info("Waiting " + delayTime + " seconds for email delivery");
			Thread.sleep(delayTime * 1000);
		}
		catch (InterruptedException ie)
		{
			/*
			 * do nothing
			 */
		}

		/*
		 * Calling the POP3XMLReceiver
		 */
		String[] args =
		{ "Test0015EmailReceive" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check Outfile for differences
		 */
		checkFiles(outFileDat, refFileDat, diffFileDat);
	}

	/**
	 * Email with POP3XMLReceiverThread
	 */
	public void test0016()
	{
		String outFileDat = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0016Outfile.xml";
		String refFileDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0016Outfile.xml";
		String refFileAltDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0016Outfile_alt.xml";
		String diffFileDat = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0016Outfile.diff";

		/*
		 * Check Outfile for differences
		 */
		checkFilesAlt(outFileDat, refFileDat, refFileAltDat, diffFileDat);
	}

	/**
	 * Email with POP3Receiver
	 */
	public void test0017()
	{
		String outFileDat = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0017Outfile.xml";
		String refFileDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0017Outfile.xml";
		String diffFileDat = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0017Outfile.diff";

		/*
		 * Calling the POP3Receiver
		 */
		String[] args =
		{ "Test0017EmailReceive" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check Outfile for differences
		 */
		checkFiles(outFileDat, refFileDat, diffFileDat);
	}

	/**
	 * Email with POP3ReceiverThread
	 */
	public void test0018()
	{
		String outFileDat = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0018Outfile.xml";
		String refFileDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0018Outfile.xml";
		String diffFileDat = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0018Outfile.diff";

		/*
		 * Check Outfile for differences
		 */
		checkFiles(outFileDat, refFileDat, diffFileDat);
	}

	/**
	 * HTTPParameterSender/Receiver
	 */
	public void test0019()
	{
		String outFileDat = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0019Outfile.xml";
		String refFileDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0019Outfile.xml";
		String diffFileDat = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0019Outfile.diff";

		/*
		 * Calling the POP3Receiver
		 */
		String[] args =
		{ "Test0019InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check Outfile for differences
		 */
		checkFiles(outFileDat, refFileDat, diffFileDat);
	}

	/**
	 * XML with simple routing
	 */
	public void test0020()
	{
		String outFile = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0020Outfile.xml";
		String refFile = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0020Outfile.xml";
		String diffFile = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0020Outfile.diff";

		/*
		 * Check Outfile for differences
		 */
		checkFiles(outFile, refFile, diffFile);
	}

	/**
	 * Example 21: HTTPParameterReceiver with result
	 */
	public void test0021()
	{
		final String DESTINATION = "Test0021InData";

		final String REQUEST_STRING = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(
				"<Test0021InData>").append("<Name>John Doe</Name>").append(
				"<Salary></Salary>").append("<Rank>Software Engineer</Rank>")
				.append("</Test0021InData>").toString();

		final String RESPONSE_STRING = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n").append(
				"<Test0021ParamReceive>\r\n").append(
				"<Name>John Doe</Name>\r\n").append(
				"<Rank>Software Engineer</Rank>\r\n").append("<Salary/>\r\n")
				.append("</Test0021ParamReceive>\r\n").toString();

		try
		{
			Object response = JavaReceiverBootstrap.receive(DESTINATION,
					REQUEST_STRING);

			assertEquals(RESPONSE_STRING, response);
		}
		catch (Throwable e)
		{
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * CSV message without description, default settings
	 */
	public void test0022()
	{
		String outFileDat = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0022Outfile.csv";
		String refFileDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0022Outfile.csv";
		String diffFileDat = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0022Outfile.diff";

		String outFileXML = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0022Outfile.xml";
		String refFileXML = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0022Outfile.xml";
		String diffFileXML = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0022XML.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0022Infile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check both Outfiles for differences
		 */
		checkFiles(outFileDat, refFileDat, diffFileDat);
		checkFiles(outFileXML, refFileXML, diffFileXML);
	}

	/**
	 * CSV message without description, special settings
	 */
	public void test0023()
	{
		String outFileDat = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0023Outfile.csv";
		String refFileDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0023Outfile.csv";
		String diffFileDat = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0023Outfile.diff";

		String outFileXML = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0023Outfile.xml";
		String refFileXML = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0023Outfile.xml";
		String diffFileXML = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0023XML.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0023Infile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check both Outfiles for differences
		 */
		checkFiles(outFileDat, refFileDat, diffFileDat);
		checkFiles(outFileXML, refFileXML, diffFileXML);
	}

	/**
	 * HTTP authentication
	 */
	public void test0024()
	{
		String outFileXML = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0024Outfile.xml";
		String refFileXML = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0024Outfile.xml";
		String diffFileXML = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0024Outfile.diff";

		/*
		 * Check Outfilesfor differences
		 */
		checkFiles(outFileXML, refFileXML, diffFileXML);
	}

	/**
	 * FTPReceicerThread
	 */
	public void test0025()
	{
		String outFileXML = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0025Outfile.xml";
		String refFileXML = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0025Outfile.xml";
		String diffFileXML = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0025Outfile.diff";

		/*
		 * Check Outfilesfor differences
		 */
		checkFiles(outFileXML, refFileXML, diffFileXML);
	}

	/**
	 * CSV message without description, default settings
	 */
	public void test0026()
	{
		String outFileXML1 = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0026Outfile_one.xml";
		String refFileXML1 = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0026Outfile_one.xml";
		String diffFileXML1 = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0026XML_one.diff";

		String outFileXML2 = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0026Outfile_two.xml";
		String refFileXML2 = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0026Outfile_two.xml";
		String diffFileXML2 = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0026XML_two.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0026Infile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check both Outfiles for differences
		 */
		checkFiles(outFileXML1, refFileXML1, diffFileXML1);
		checkFiles(outFileXML2, refFileXML2, diffFileXML2);
	}

	/**
	 * HTTPParameterSender/Receiver with WILDCARD
	 */
	public void test0027()
	{
		String outFileDat = TEST_RUN + Constants.FILE_SEPERATOR
				+ "Test0027Outfile_xxx.xml";
		String refFileDat = TEST_REF + Constants.FILE_SEPERATOR
				+ "Test0027Outfile_xxx.xml";
		String diffFileDat = TEST_DIFF + Constants.FILE_SEPERATOR
				+ "Test0027Outfile_xxx.diff";

		/*
		 * Calling the FileReceiver
		 */
		String[] args =
		{ "Test0027InFile" };
		ReceiverSingleBootstrap.main(args);

		/*
		 * Check Outfile for differences
		 */
		checkFiles(outFileDat, refFileDat, diffFileDat);
	}

	private void checkFiles(String outFile, String refFile, String diffFile)
	{
		if (!new File(outFile).isFile())
		{
			fail("Output file " + outFile + " doesn't exist");
		}
		if (!new File(refFile).isFile())
		{
			fail("Reference file " + refFile + " doesn't exist");
		}

		try
		{
			boolean ok = XDiff.diff(outFile, refFile, diffFile);

			if (!ok)
			{
				fail("Files are different, take a look into directory diff!");
			}
		}
		catch (Throwable e)
		{
			fail("Exception: " + e.getMessage());
		}
	}

	private void checkFilesAlt(String outFile, String refFile,
			String refFileAlt, String diffFile)
	{
		if (!new File(outFile).isFile())
		{
			fail("Output file " + outFile + " doesn't exist");
		}
		if (!new File(refFile).isFile())
		{
			fail("Reference file " + refFile + " doesn't exist");
		}
		if (!new File(refFileAlt).isFile())
		{
			fail("Alternative reference file " + refFileAlt + " doesn't exist");
		}

		try
		{
			boolean ok = XDiff.diff(outFile, refFile, diffFile)
					|| XDiff.diff(outFile, refFileAlt, diffFile);

			if (!ok)
			{
				fail("Files are different, take a look into directory diff!");
			}
		}
		catch (Throwable e)
		{
			fail("Exception: " + e.getMessage());
		}
	}
}
