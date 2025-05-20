/*
 * Copyright(C) 2008-2009 Dmitriy Kumshayev. <dq@mail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.templateit;

import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

class WorkbookParser
{
	private static final Logger logger = Logger.getLogger(WorkbookParser.class);

	private final HSSFWorkbook workbook;
	private final TemplateWorkbook tWorkbook;

	private TemplateSheet tSheet;

	public WorkbookParser(HSSFWorkbook workbook) 
	{
		this.workbook = workbook;
		this.tWorkbook = new TemplateWorkbook();
	}
	
	public TemplateWorkbook parse()
	{
		return parse(null);
	}
	public TemplateWorkbook parse(Set<String> excludedSheetNames)
	{
		int nSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < nSheets; i++)
		{
			HSSFSheet sheet = workbook.getSheetAt(i);
			String sheetName = workbook.getSheetName(i);
			if (excludedSheetNames!=null && !excludedSheetNames.contains(sheetName))
			{
				parseSheet(sheetName, sheet);
			}
		}
		return tWorkbook;
	}

	private void parseSheet(String sheetName, HSSFSheet sheet)
	{
		logger.debug("Parsing <" + sheetName + ">");
		tSheet = tWorkbook.createTemplateSheet(sheetName,sheet);

		int lastRow = sheet.getLastRowNum();
		StaticTemplate template = null;

		for (int r = 0; r <= lastRow; r++)
		{
			HSSFRow row = sheet.getRow(r);
			if (row != null)
			{
				tSheet.setLastColumn(Math.max(row.getLastCellNum(), tSheet
						.getLastColumn()));
				// POI does not provide a way to walk through 
				// all available cell comments, so we reserve
				// first 20 columns 
				int lastColumn = Math.max(tSheet.getLastColumn(), 20);

				for (int c = 0; c <= lastColumn; c++)
				{
					boolean templateEndFound = false;
					HSSFComment cellComment = sheet.getCellComment(r, c);
					if (cellComment != null)
					{
						HSSFRichTextString hstring = cellComment.getString();
						if (hstring != null)
						{
							String comm = hstring.toString();
							if (comm != null)
							{
								if (logger.isTraceEnabled())
								{
									String ct = comm.replace('\n', ' ').trim();
									logger.trace("comment @(" + r + "," + c + "): '" + ct + "'");
								}
								String[] names = null;
								if ((names = OpMatcher.matchTemplateBegin(comm)) != null)
								{
									if (logger.isTraceEnabled())
									{
										logger.trace("@template_begin @(" + r + "," + c + ")");
									}
									template = tSheet.createTemplate(r, c, names);
								}

								if (OpMatcher.matchTemplateEnd(comm))
								{
									if (logger.isTraceEnabled())
									{
										logger.trace("@template_end @(" + r + "," + c + ")");
									}
									if( template != null )
									{
										template.setEndReference(new Reference(r, c));
									}
									else
									{
										logger.warn("@template_end without @template_begin");
									}
									templateEndFound = true;
								}

								String paramName = OpMatcher.matchTemplateParameter(comm);
								if (paramName != null )
								{
									if( template != null )
									{
										int relRow = r-template.start().row();			
										int relCol = c-template.start().column();			
										template.createParameter(paramName, relRow, relCol);
									}
									else
									{
										logger.warn("Cannot create parameter '"+paramName+"'");
									}
								}
								
								NamedStyle style = OpMatcher.matchStyle(comm);
								if( style != null )
								{
									style.setRow(r);
									style.setColumn(c);
									tSheet.addStyle(style);
								}
							}

							if( templateEndFound )
							{
								// reset template
								template = null;
							}
						}
					}
				}
			}
		}

		processMergeRegions(sheet);
	}

	private void processMergeRegions(HSSFSheet sheet)
	{
		// identify MergeRegions and assign them to corresponding template sections
		int numMergedRegions = sheet.getNumMergedRegions();
		for (int i = 0; i < numMergedRegions; i++)
		{
			CellRangeAddress mr = sheet.getMergedRegion(i);
			Reference start = new Reference(mr.getFirstRow(), mr.getFirstColumn());
			Reference end = new Reference(mr.getLastRow(), mr.getLastColumn());
			MergeRegion mreg = new MergeRegion(start, end);
			if (logger.isTraceEnabled())
			{
				logger.trace("Merge region @(" + mreg + ")");
			}

			for (StaticTemplate template : tSheet.templates())
			{
				if (mreg.contains(template.end()))
				{
					template.setEndReference(mreg.end());

					if (logger.isTraceEnabled())
					{
						logger.trace("Template " + template.getName() + " extended to ("
								+ template + ")");
					}
				}

				if (template.contains(mreg))
				{
					template.addMergeRegion(mreg);
				}
			}
		}
	}

}
