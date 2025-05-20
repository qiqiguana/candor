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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;

class TemplateSheet
{
	private final String sheetName;
	private final HSSFSheet sheet;
	private final Map<String,StaticTemplate> templateMap = new HashMap<String,StaticTemplate>();
	private final Map<String,DynamicTemplate> dynamicTemplateMap = new HashMap<String,DynamicTemplate>();
	private int lastColumn = 0;
	private int firstColumn = 0;
	private final Map<String,NamedStyle> stylesMap = new HashMap<String,NamedStyle>();
	
	public TemplateSheet(String sheetName,HSSFSheet sheet)
	{
		this.sheetName=sheetName;
		this.sheet = sheet;
	}
	
	public Template getTemplate(String tName)
	{
		Template template = dynamicTemplateMap.get(tName);
		if( template==null)
		{
			template = templateMap.get(tName);
		}
		return template;
	}
	
	public StaticTemplate getStaticTemplate(String tName)
	{
		return templateMap.get(tName);
	}

	public Collection<StaticTemplate> templates()
	{
		return templateMap.values();
	}
	
	public StaticTemplate createTemplate(int r, int c, String[] names)
	{
		String tName = names[0];
		StaticTemplate t = this.getStaticTemplate(tName);
		if (t == null)
		{
			t = new StaticTemplate(tName,sheet);
			if( names.length>1 )
			{
				Parameter[] parameters = new Parameter[names.length-1];
				for (int i = 1; i < names.length; i++)
				{
					Parameter parameter = new Parameter();
					parameter.setName(names[i]);
					parameter.setIndex(i);
					parameters[i-1] = parameter;
				}
				t.setParameters(parameters);
			}

			Reference start = new Reference(r, c);
			t.setStartReference(start);
			this.addTemplate(t);
		}
		return t;
	}

	public String getSheetName()
	{
		return sheetName;
	}

	public int getFirstColumn() 
	{
		return firstColumn;
	}

	public int getLastColumn()
	{
		return lastColumn;
	}

	public void setLastColumn(int lastColumn)
	{
		this.lastColumn = lastColumn;
	}

	public HSSFSheet sheet()
	{
		return sheet;
	}

	private StaticTemplate addTemplate(StaticTemplate t)
	{
		if( templateMap.size()==0)
		{
			firstColumn = t.start().column();
		}
		else
		{
			firstColumn = Math.min(firstColumn, t.start().column());
		}
		return templateMap.put(t.getName(),t);
	}

	public NamedStyle addStyle(NamedStyle style)
	{
		if( style!=null )
		{
			return stylesMap.put(style.getName(), style);
		}	
		return null;
	}

	public NamedStyle getStyle(String styleName)
	{
		return stylesMap.get(styleName);
	}

	public void addDynamicTemplate(DynamicTemplate dynamicTemplate)
	{
		dynamicTemplateMap.put(dynamicTemplate.getName(), dynamicTemplate);
	}

}
