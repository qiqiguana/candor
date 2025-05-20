package com.imsmart.report;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import com.imsmart.misc.MDate;
import com.imsmart.misc.MProperties;

public class MReportCSV implements MReport
{

	private String reportFilePath;

	private MProperties properties;

	public MReportCSV()
	{
		properties = MProperties.getInstance();
		reportFilePath = properties.getPropertyValue(MProperties.REPORT_DIR);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addLine(List columns)
	{
		String reportFileName = MDate.now(MDate.DATE_ONLY) + ".csv";
		try
		{
			PrintWriter out = openReportFile(reportFileName);
			StringBuffer line = new StringBuffer();
			for (Iterator iterator = columns.iterator(); iterator.hasNext();)
			{
				Object column = (Object) iterator.next();

				line.append(column.toString());
				if (iterator.hasNext())
				{
					line.append(",");
				}
			}
			out.println(line.toString());
			closeReportFile(out);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public PrintWriter openReportFile(String fileName) throws Exception
	{
		FileWriter output = new FileWriter(reportFilePath + "/" + fileName,
				true);
		PrintWriter out = new PrintWriter(output, true);
		return out;
	}

	public void closeReportFile(PrintWriter out) throws Exception
	{
		out.close();
	}

}
