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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class WorkbookParserTest extends TestCase 
{
	public void testParser() throws IOException
	{
		File file = new File("src/test/resources/SalesReceiptTemplate.xls");
		System.out.println(file.getAbsolutePath());
		FileInputStream in = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		WorkbookParser p = new WorkbookParser(workbook);
		p.parse();
	}

}
