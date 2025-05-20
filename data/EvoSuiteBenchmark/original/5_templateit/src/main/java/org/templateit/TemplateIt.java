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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.templateit.util.DelimitedFileReader;

import com.lowagie.text.DocumentException;

public class TemplateIt
{
	private static final Logger logger = Logger
			.getLogger(TemplateIt.class);

	public static void main(String[] args) throws IOException, DocumentException
	{
		if (args.length > 0)
		{
			File directory = new File(args[0]);
			if (args.length > 1)
			{
				for (int i = 1; i < args.length; i++)
				{
					String name = args[i];
					File csvFile = new File(directory, name + ".csv");
					File templateFile = new File(directory, name + "Template.xls");
					File outputFile = new File(directory, name + ".xls");
					Iterator<String[]> iterator = new DelimitedFileReader(csvFile, "\t");
					TemplateProcessor tp = new TemplateProcessor(templateFile);
					tp.process(iterator, outputFile);

					PdfWriter writer = new PdfWriter(new FileInputStream(outputFile));
					writer.writePdf(new FileOutputStream(new File(name + ".pdf")));			
				}
			}
			else
			{
				logger.error("Missing Template name argument");
				usage();
			}
		}
		else
		{
			logger.error("Missing <directory> argument");
			usage();
		}

		logger.debug("Done");
	}

	private static void usage()
	{
		System.out.println("Usage: Main <directory> <Name1> [<Name2> ...]");
		System.out.println("");
		System.out.println("    <directory> - location of <NameN>Template.xls template files\n");
		System.out.println("                  and corresponding tab-separated data files (<NameN>.csv)");
		System.exit(2);
	}
}
