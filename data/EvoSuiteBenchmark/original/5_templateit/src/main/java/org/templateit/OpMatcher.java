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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class OpMatcher
{
	private static final String TBEGIN1 = "@template_begin";
	private static final String TBEGIN2 = "@tbegin";
	private static final String beginParams = "\\s*\\(\\s*((\\p{Alpha}\\w*)(\\s*,\\s*\\p{Alpha}\\w*)*)\\s*\\)";
	
	private static final Pattern templateBeginPattern1 = Pattern.compile(TBEGIN1+beginParams);
	private static final Pattern templateBeginPattern2 = Pattern.compile(TBEGIN2+beginParams);

	/**
	 * Match a string with
	 * <em>#template_begin(name, param1, param2, ..., paramN)</em> instruction.
	 * 
	 * @param text
	 *          being matched
	 * 
	 * @return if matched, return a String array, where the first element is
	 */
	public static String[] matchTemplateBegin(String text)
	{
		String[] names = null;
		if (text != null)
		{
			Matcher m = null;
			if( text.indexOf(TBEGIN1) != -1 )
			{
				m = templateBeginPattern1.matcher(text);
			}
			else if ( text.indexOf(TBEGIN2) != -1)
			{
				m = templateBeginPattern2.matcher(text);
			}
			if( m != null )
			{
				if (m.groupCount() == 3)
				{
					boolean matches = m.find();
					if (matches)
					{
						String group = m.group(1);
						if (group != null)
						{
							names = group.split("\\s*,\\s*");
						}
					}
				}
			}
		}
		return names;
	}

	public static boolean matchTemplateEnd(String text)
	{
		return text != null && (text.indexOf("@template_end") != -1)
				|| (text.indexOf("@tend") != -1);
	}

	private static final Pattern parameterNumberPattern = Pattern
			.compile("#([1-9]\\p{Digit}*)");

	private static final Pattern parameterNamePattern = Pattern
			.compile("#(\\p{Alpha}\\w*)");

	public static String matchTemplateParameter(String text)
	{
		String parameterName = null;
		if (text != null)
		{
			if (text.indexOf("#") != -1)
			{
				Matcher fieldNameMatcher = parameterNamePattern.matcher(text);
				boolean found = fieldNameMatcher.find();
				if (found && fieldNameMatcher.groupCount() == 1)
				{
					parameterName = fieldNameMatcher.group(1);
				}
				else
				{
					Matcher fieldNumberMatcher = parameterNumberPattern.matcher(text);
					found = fieldNumberMatcher.find();
					if (found && fieldNumberMatcher.groupCount() == 1)
					{
						parameterName = fieldNumberMatcher.group(1);
					}
				}
			}
		}
		return parameterName;
	}

	private static final Pattern templateNamePattern = Pattern
			.compile("\\p{Alpha}\\w*");

	public static boolean matchTemplateName(String text)
	{
		if (text != null)
		{
			return templateNamePattern.matcher(text).matches();
		}
		else
		{
			return false;
		}
	}
	
	private static final Pattern stylePattern = Pattern.compile("@style\\s*\\(\\s*(\\p{Alpha}\\w*)\\s*\\)");
	private static final Pattern styleWithParamPattern = Pattern.compile("@style\\s*\\(\\s*(\\p{Alpha}\\w*)\\s*,\\s*((true)|(false))\\s*\\)");

	public static NamedStyle matchStyle(String text)
	{
		NamedStyle style = null;
		String styleName = null;
		if (text != null)
		{
			Matcher matcher = stylePattern.matcher(text);
			boolean found = matcher.find();
			if (found && matcher.groupCount() == 1)
			{
				styleName = matcher.group(1);
				style = new NamedStyle(styleName,false);
			}
			else
			{
				matcher = styleWithParamPattern.matcher(text);
				found = matcher.find();
				if (found && matcher.groupCount() == 4)
				{
					styleName = matcher.group(1);
					String paramFlag = matcher.group(2);
					boolean flag = Boolean.parseBoolean(paramFlag);
					style = new NamedStyle(styleName,flag);
				}
			}
		}
		return style;
	}
	

}
