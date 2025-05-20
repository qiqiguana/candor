package com.imsmart.report;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.imsmart.misc.MDate;
import com.imsmart.misc.MProperties;

public class MReportHTML implements MReport
{
	MProperties properties;
	String reportFilePath;

	public MReportHTML()
	{
		properties = MProperties.getInstance();
		reportFilePath = properties.getPropertyValue(MProperties.REPORT_DIR);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addLine(List columns) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public PrintWriter openReportFile(String fileName) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	

	public void generateHTML_old(String csvFileNameWithPath)
	{
		String htmlFileName = MDate.now(MDate.DATE_ONLY) + ".html";
		StringBuffer html = new StringBuffer();
		html.append("<html><body>");
		html.append("<center>");
		html.append("<table border=\"1\" width=\"75%\" CELLSPACING=0>");
		try
		{
			FileInputStream fis = new FileInputStream(reportFilePath + "/"
					+ csvFileNameWithPath);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";

			FileWriter output = new FileWriter(reportFilePath + "/"
					+ htmlFileName);
			PrintWriter out = new PrintWriter(output, true);

			while ((line = br.readLine()) != null)
			{
				String cols[] = line.split(",");
				html.append("<tr>");
				for (int i = 0; i < cols.length; i++)
				{
					html.append("<td>");
					html.append(cols[i]);
					html.append("</td>");
				}
				html.append("</tr>");
			}
			html.append("</center></table></body></html>");

			out.println(html);
			out.close();
			br.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
