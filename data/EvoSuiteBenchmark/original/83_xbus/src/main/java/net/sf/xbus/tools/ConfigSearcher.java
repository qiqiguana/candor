package net.sf.xbus.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import net.sf.xbus.base.core.ASCIITokenizer;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.strings.XStringSupport;
import net.sf.xbus.base.core.trace.Trace;

/**
 * TODO: short description of the purpose of this class
 */
public class ConfigSearcher
{
	public static final String CSV_FILENAME = Constants.XBUS_HOME
			+ "/configuration.csv";

	public static void main(String[] args)
	{
		File srcDir = new File(Constants.XBUS_HOME + "/src");
		ConfigSearcher searcher = new ConfigSearcher();
		List entries = searcher.getEntriesFromDirectory(srcDir);
		searcher.writeCSVFile(entries, CSV_FILENAME);

		for (Iterator it = entries.iterator(); it.hasNext();)
		{
			Entry entry = (Entry) it.next();
			Trace.info(entry.file + "|" + entry.method + "|" + entry.chapter
					+ "|" + entry.section + "|" + entry.key);
		}

	}

	public List getEntriesFromDirectory(File path)
	{
		if (!path.isDirectory())
		{
			Trace.error(path.getAbsolutePath() + " is not a directory");
			System.exit(1);
		}

		List retList = new Vector();

		if (!path.getName().endsWith("CVS"))
		{
			// Trace.debug("Processing " + path.getAbsolutePath());

			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				if (files[i].isDirectory())
				{
					retList.addAll(getEntriesFromDirectory(files[i]));
				}
				else if (files[i].isFile()
						&& (files[i].getName().endsWith(".java")))
				{
					retList.addAll(getEntriesFromFile(files[i]));
				}
			}
		}

		return retList;
	}

	public void writeCSVFile(List entries, String filename)
	{
		TreeSet set = new TreeSet(entries);

		StringBuffer strBuffer = new StringBuffer(
				"Class;Method;Chapter;Section;Key;Type;Optional")
				.append(Constants.LINE_SEPERATOR);
		Entry entry = null;
		for (Iterator it = set.iterator(); it.hasNext();)
		{
			entry = (Entry) it.next();
			strBuffer.append(entry.file).append(";").append(entry.method)
					.append(";").append(entry.chapter).append(";").append(
							entry.section).append(";").append(entry.key)
					.append(";").append(entry.type).append(";").append(
							entry.optional).append(Constants.LINE_SEPERATOR);
		}

		BufferedWriter buffOut = null;
		try
		{
			buffOut = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename)));
			buffOut.write(strBuffer.toString());
		}
		catch (Exception e)
		{
			Trace.error(e);
			System.exit(1);
		}
		finally
		{
			if (buffOut != null)
			{
				try
				{
					buffOut.close();
				}
				catch (IOException e1)
				{
					// do nothing
				}
			}
		}
	}

	private List getEntriesFromFile(File file)
	{
		if (!file.isFile())
		{
			Trace.error(file.getName() + " is not a file");
			System.exit(1);
		}

		List retList = new Vector();

		String content = readFile(file);
		content = XStringSupport.replaceAll(content, " ", "");
		content = XStringSupport.replaceAll(content, Constants.LINE_SEPERATOR,
				"");
		content = XStringSupport.replaceAll(content, "\t", "");

		int lastPosition = 0;
		int start;
		while ((start = content.indexOf("getValue", lastPosition)) > 0)
		{
			int openBrace = content.indexOf("(", start);
			String method = content.substring(start, openBrace);

			int closeBrace = content.indexOf(")", openBrace);
			String params = content.substring(openBrace + 1, closeBrace);
			if (params.indexOf("(") > 0)
			{
				content = XStringSupport.replaceFirst(content, ")", "",
						openBrace);
				closeBrace = content.indexOf(")", openBrace);
				params = content.substring(openBrace + 1, closeBrace);
			}
			// Trace.debug(file.getName() + " | " + method + " | " + params);
			retList.add(new Entry(file.getName(), method, params));

			lastPosition = closeBrace + 1;
		}

		return retList;
	}

	private String readFile(File file)
	{
		StringBuffer retBuffer = new StringBuffer();
		String zeile;
		BufferedReader buffReader = null;
		try
		{

			buffReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));

			if ((zeile = buffReader.readLine()) == null)
			{
				buffReader.close();
				return "";
			}
			else
			{
				retBuffer.append(zeile);
			}

			while ((zeile = buffReader.readLine()) != null)
			{
				retBuffer.append(Constants.LINE_SEPERATOR);
				retBuffer.append(zeile);
			}
		}
		catch (IOException e)
		{
			Trace.error(e);
			System.exit(1);
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
					// explicitly do nothing
				}
			}
		}

		return retBuffer.toString();
	}

	private class Entry implements Comparable
	{
		public String file;
		public String method;
		public String chapter = "";
		public String section = "";
		public String key = "";
		public String type = "";
		public String optional = "";

		public Entry(String inFile, String inMethod, String inParameter)
		{
			file = XStringSupport.replaceAll(inFile, ".java", "");
			method = inMethod;
			String fourthParam;

			if (method.toUpperCase().indexOf("BOOLEAN") >= 0)
			{
				type = "Boolean";
			}
			else if (method.toUpperCase().indexOf("INT") >= 0)
			{
				type = "Integer";
			}
			else if (method.toUpperCase().indexOf("LONG") >= 0)
			{
				type = "Long";
			}
			else
			{
				type = "String";
			}

			if (method.toUpperCase().indexOf("OPTIONAL") >= 0)
			{
				optional = "X";
			}

			ASCIITokenizer tok = new ASCIITokenizer(inParameter, ",");
			if (tok.countTokens() < 3)
			{
				Trace.warn("Parameter must have at least three tokens, "
						+ inFile + " |" + inMethod + "|" + inParameter);
			}
			else
			{
				chapter = tok.nextToken();
				section = tok.nextToken();
				key = tok.nextToken();
				if (((fourthParam = tok.nextToken()) != null)
						&& (fourthParam.length() > 0))
				{
					Trace.warn("Parameter has more than three tokens, "
							+ inFile + " | " + inMethod + " | " + inParameter);
					chapter = "";
					section = "";
					key = "";

				}
			}
		}

		/**
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		public int compareTo(Object arg0)
		{
			int retValue = 0;
			if (arg0 instanceof Entry)
			{
				Entry entry = (Entry) arg0;
				if ((retValue = entry.file.compareTo(this.file)) == 0)
				{
					if ((retValue = entry.chapter.compareTo(this.chapter)) == 0)
					{
						if ((retValue = entry.section.compareTo(this.section)) == 0)
						{
							retValue = entry.key.compareTo(this.key);
						}
					}
				}

			}
			return retValue;
		}
	}
}
