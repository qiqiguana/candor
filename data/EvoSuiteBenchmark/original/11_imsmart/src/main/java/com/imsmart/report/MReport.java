package com.imsmart.report;

import java.io.PrintWriter;
import java.util.List;

public interface MReport
{
	public PrintWriter openReportFile(String fileName) throws Exception;

	public void addLine(List columns) throws Exception;
}
