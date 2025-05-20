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

import junit.framework.TestCase;

import org.junit.Assert;

public class OpMatcherTest extends TestCase
{
	public void testMmatchTemplateBegin()
	{
		
		String[] testInput = 
		{"\nhello \n@template_begin\n \t(\n t1\n,\np1\n,\n p2 \n,\np3) \n\n\n",
				"@template_begin(t1,p1,p2,p3)",
				"@template_begin(t1)",
				"@template_begin( p1 )",
				"@tbegin(a)",
				"@tbegin( a , b , c )",
				"fsdf @tbegin( a , b , c ) fsdfsda ",
				"",
				null,
		};
		int[] testResultCheck =		{4,4,1,1,1,3,3,0,0};
		
		for (int i = 0; i < testResultCheck.length; i++)
		{
			String[] res = OpMatcher.matchTemplateBegin(testInput[i]);
			if( res == null)
			{
				Assert.assertEquals(0,testResultCheck[i]);
			}
			else
			{
				Assert.assertEquals(testResultCheck[i],res.length);
			}
		}
	  
	}

	public void testStyleMatcher()
	{
		
		String[] testInput = 
		{
				"@style( A )",
				"@style( B )",
				"@style( C , false )",
				"@style( D , true )",
				"@style( E , no )",
		};
		NamedStyle[] expectedResult =
		{
				new NamedStyle("A",false),
				new NamedStyle("B",false),
				new NamedStyle("C",false),
				new NamedStyle("D",true),
				null,
		};
		
		for (int i = 0; i < testInput.length; i++)
		{
			NamedStyle style = OpMatcher.matchStyle(testInput[i]);
			if( style == null)
			{
				Assert.assertNull(expectedResult[i]);
			}
			else
			{
				Assert.assertEquals(expectedResult[i].getName(),style.getName());
				Assert.assertEquals(expectedResult[i].hasParam(),style.hasParam());
			}
		}
	  
	}
}
