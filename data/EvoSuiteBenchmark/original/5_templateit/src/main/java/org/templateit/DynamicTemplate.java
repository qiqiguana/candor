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

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class DynamicTemplate extends Template
{

	private int height = 0;
	private int width = 0;
	private final List<NamedStyle> styles;
	
	public DynamicTemplate(String name, HSSFSheet sheet, int height, List<NamedStyle> styles)
	{
		super(name, sheet);
		this.styles = styles;
		this.height = height;
		this.width = styles.size()/height;
		for( int i=0; i!=styles.size();i++)
		{
			NamedStyle style = styles.get(i);
			if( style.hasParam() )
			{
				int r = i/width;
				int c = i%width;
				createParameter(""+(i+1), r, c);
			}
		}
	}

	@Override
	public int height()
	{
		return height;
	}

	@Override
	public int width()
	{
		return width;
	}

	public Reference absoluteReference(int r, int c)
	{
		NamedStyle style = styles.get(r*width()+c);
		return new Reference(style.getRow(),style.getColumn());
	}

	@Override
	public int getRowHeight(int r)
	{
		int rh = 0;
		for( int c=0; c<width(); c++ )
		{
			Reference ar = absoluteReference(r, c);
			HSSFRow row = sheet.getRow(ar.row());
			if(row != null )
			{
				rh = Math.max(rh, row.getHeight());
			}
		}
		return rh;
	}

	@Override
	public HSSFCell getCell(int r, int c)
	{
		if(c>=0&&c<width()&&r>=0&&r<height())
		{
			Reference ar = absoluteReference(r, c);
			HSSFRow tRow = sheet.getRow(ar.row());
			if( tRow != null )
			{
				return tRow.getCell(ar.column());
			}
		}
		return null;
	}

}
