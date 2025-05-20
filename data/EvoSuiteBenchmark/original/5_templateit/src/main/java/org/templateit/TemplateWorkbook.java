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

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * This class encapsulates a map of {@link TemplateSheet} gathered 
 * by {@link WorkbookParser} from the template workbook.
 * @author Dmitriy Kumshayev
 *
 */
final class TemplateWorkbook
{
	private Map<String, TemplateSheet> sheets = new HashMap<String, TemplateSheet>();

	public TemplateSheet getTemplateSheet(String sheetName)
	{
		return sheets.get(sheetName);
	}

	/**
	 * Set {@link TemplateSheet} 
	 * @param sheetName
	 * @param sheetData
	 * @return
	 */
	public TemplateSheet setSheetTemplateData(String sheetName,
			TemplateSheet sheetData)
	{
		return sheets.put(sheetName, sheetData);
	}
	
	public TemplateSheet createTemplateSheet(String sheetName, HSSFSheet sheet)
	{
		TemplateSheet tSheet = getTemplateSheet(sheetName);
		if (tSheet == null)
		{
			tSheet = new TemplateSheet(sheetName,sheet);
			setSheetTemplateData(sheetName, tSheet);
		}
		return tSheet;
	}
	
}
