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

package org.templateit.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of Iterator interface. Reads an input file line by line and
 * parses each one as fields separated by a delimiter. For each line the fields
 * are stored in a String array and made accessible via Iterator interface.
 * 
 * @author Dmitriy Kumshayev
 */
public class DelimitedFileReader implements Iterator<String[]>
{
	private String delimiter;

	private BufferedReader reader;

	private String nextLine = null;

	private boolean hasNext = true;

	public DelimitedFileReader(File file) throws FileNotFoundException
	{
		this(file, ",");
	}

	public DelimitedFileReader(File file, String delimiter)	throws FileNotFoundException
	{
		reader = new BufferedReader(new FileReader(file));
		this.delimiter = delimiter;
	}

	public boolean hasNext()
	{
		if (hasNext)
		{
			if (nextLine == null)
			{
				try
				{
					nextLine = reader.readLine();
				}
				catch (IOException e)
				{
					nextLine = null;
				}

				if (nextLine == null)
				{
					hasNext = false;
				}
			}
		}
		return hasNext;
	}

	public String[] next()
	{
		if (hasNext())
		{
			String[] next = nextLine.split(delimiter);
			nextLine = null;
			return next;
		}
		else
		{
			throw new NoSuchElementException();
		}
	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}
