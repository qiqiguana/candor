import java.util.ArrayList;
import java.util.List;

import com.momed.report.MReportCSV;

public class TestReportCSV
{
	public static void main(String args[])
	{
		MReportCSV csvReport = new MReportCSV();
		List columns = new ArrayList();
		columns.add("image001.tif");
		columns.add("03/03/2008");
		columns.add("5:45 pm");
		columns.add("success");
		csvReport.addLine(columns);
	}
}
