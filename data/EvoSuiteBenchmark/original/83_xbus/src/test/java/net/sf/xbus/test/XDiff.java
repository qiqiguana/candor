package net.sf.xbus.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import jlibdiff.Diff;
import jlibdiff.Hunk;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;

/**
 * TODO Kommentierung
 */
public class XDiff
{
	private static final char WILDCARD = '?';

	static public void main(String[] args)
	{
		String actFile = Constants.XBUS_HOME + "/configuration.csv";
		String refFile = Constants.XBUS_HOME + "/configuration_2004_10_07.csv";
		String diffFile = Constants.XBUS_HOME + "/configuration.diff";

		try
		{
			diff(actFile, refFile, diffFile);
		}
		catch (XException e)
		{}
	}

	/**
	 * Compares InFile or OutFile vs. ReferenceFile and saves all differences in
	 * a .diff-File
	 * 
	 * @param actFilename the path name with to compared file
	 * @param refFilename the path name with contain reference file
	 * @param diffFilename the path name, there is create the difference file
	 * @return a boolean true if there are no differences, false if there are
	 *         differences
	 */
	static public boolean diff(String actFilename, String refFilename,
			String diffFilename) throws XException
	{
		boolean noDiff = true;

		Diff differ = new Diff();
		try
		{
			differ.diffFile(actFilename, refFilename);
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TESTDRIVER,
					Constants.PACKAGE_TESTDRIVER_TESTDRIVER, "999", e);
		}

		try
		{
			int diffNo = 0;
			if (differ.getHunks().size() > 0)
			{
				Vector hunks = differ.getHunks();

				/*
				 * Check first if there are really differences
				 */
				for (Enumeration e = hunks.elements(); e.hasMoreElements();)
				{
					Hunk hunk = (Hunk) e.nextElement();

					if (!isHunkSimilar(hunk))
					{
						noDiff = false;
					}
				}

				/*
				 * If there are differences, write them into the diff file
				 */
				if (!noDiff)
				{
					FileWriter diffWriter = new FileWriter(diffFilename);

					diffWriter.write(Constants.getDateFormat().format(
							new Date())
							+ Constants.LINE_SEPERATOR
							+ Constants.LINE_SEPERATOR);

					diffWriter.write("Outfile  : " + actFilename
							+ Constants.LINE_SEPERATOR);
					diffWriter.write("Reference: " + refFilename
							+ Constants.LINE_SEPERATOR
							+ Constants.LINE_SEPERATOR);

					int i = 1;
					for (Enumeration e = hunks.elements(); e.hasMoreElements();)
					{
						// write the error in diff file
						Hunk hunk = (Hunk) e.nextElement();

						int actFileNo = hunk.numLines(0);
						int refFileNo = hunk.numLines(1);
						int max = actFileNo;
						if (refFileNo > max)
						{
							max = refFileNo;
						} // if (refFileNo > max)

						diffWriter.write(Constants.LINE_SEPERATOR + i
								+ ". block, number of differences: " + max
								+ Constants.LINE_SEPERATOR);
						diffWriter.write("----------------------------------"
								+ Constants.LINE_SEPERATOR);

						diffNo += max;

						diffWriter.write(Constants.LINE_SEPERATOR
								+ "*** Outfile,   lines " + hunk.lowLine(0)
								+ " - " + hunk.highLine(0)
								+ Constants.LINE_SEPERATOR);

						for (int j = 1; j <= actFileNo; j++)
						{
							if (hunk.relNum(0, j - 1) != null)
							{
								diffWriter.write(hunk.relNum(0, j - 1));
							}
							else
							{
								diffWriter.write("<null>"
										+ Constants.LINE_SEPERATOR);
							}
						} // for (int j = 1; j <= actFileNo; j++)
						diffWriter.write(Constants.LINE_SEPERATOR
								+ "*** Reference, lines " + hunk.lowLine(1)
								+ " - " + hunk.highLine(1)
								+ Constants.LINE_SEPERATOR);
						for (int k = 1; k <= refFileNo; k++)
						{
							if (hunk.relNum(1, k - 1) != null)
							{
								diffWriter.write(hunk.relNum(1, k - 1));
							}
							else
							{
								diffWriter.write("<null>"
										+ Constants.LINE_SEPERATOR);
							}
						} // for (int k = 1; k <= refFileNo; k++)
						i++;
					}
					diffWriter.close();
				}
			}
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TESTDRIVER,
					Constants.PACKAGE_TESTDRIVER_TESTDRIVER, "999", e);
		}

		return noDiff;
	}

	static private boolean isHunkSimilar(Hunk hunk)
	{
		int actFileLines = hunk.numLines(0);
		int refFileLines = hunk.numLines(1);

		if (actFileLines != refFileLines)
		{
			/*
			 * different number of lines in this hunk => hunk is not similar
			 */
			return false;
		}

		String actLine = null;
		String refLine = null;

		for (int i = 0; i < actFileLines; i++)
		{
			actLine = hunk.relNum(0, i);
			refLine = hunk.relNum(1, i);

			if ((actLine == null) && (refLine == null))
			{
				/*
				 * both lines are null => hunk is similar
				 */
				return true;
			}

			if (((actLine == null) && (refLine != null))
					|| ((actLine != null) && (refLine == null)))
			{
				/*
				 * one line is null, the other not null => hunk is not similar
				 */
				return false;
			}

			if (actLine.length() != refLine.length())
			{
				/*
				 * lines have different length => hunk is not similar
				 */
				return false;
			}

			for (int k = 0; k < actLine.length(); k++)
			{
				if ((refLine.charAt(k) != WILDCARD)
						&& (actLine.charAt(k) != refLine.charAt(k)))
				{
					/*
					 * reference character is no wildcard and char are different =>
					 * hunk is not similar
					 */
					return false;
				}
			}
		}

		/*
		 * no differences found => hunk is similar
		 */
		return true;
	}

}
