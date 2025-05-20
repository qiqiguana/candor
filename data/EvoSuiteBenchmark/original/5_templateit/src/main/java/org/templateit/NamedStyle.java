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

class NamedStyle
{
	private final String name;
	private final boolean hasParam;
	private int row;
	private int column;
	
	public NamedStyle(String name, boolean hasParam)
	{
		this.name = name;
		this.hasParam = hasParam;
	}

	public String getName()
	{
		return name;
	}

	public boolean hasParam()
	{
		return hasParam;
	}

	public int getRow()
	{
		return row;
	}

	public int getColumn()
	{
		return column;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public void setColumn(int column)
	{
		this.column = column;
	}

	public String toString()
	{
		return "@style("+getName()+","+hasParam()+")";
	}
}
