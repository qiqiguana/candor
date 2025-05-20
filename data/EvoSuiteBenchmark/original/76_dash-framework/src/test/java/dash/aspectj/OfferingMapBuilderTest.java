/*
* Copyright (C) 2005  John D. Heintz
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU Library General Public License
* as published by the Free Software Foundation; either version 2.1
* of the License.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Library General Public License for more details.
*
* John D. Heintz can be reached at: jheintz@pobox.com 
*/
package dash.aspectj;

import java.util.Map;

import dash.aspectj.OfferingMapBuilder;
import dash.aspectj.memberClosures.MemberClosure;
import dash.examples.component.IComponent;
import dash.examples.offering.OfferField;

import junit.framework.TestCase;

public class OfferingMapBuilderTest extends TestCase {
	public void testFields() throws Exception {
		Map<Object, MemberClosure> map = OfferingMapBuilder.buildOfferedMap(OfferField.class);
		assertNotNull(map);
		
		assertEquals(3, map.size());
		
		assertTrue(map.containsKey("foo"));
		assertTrue(map.containsKey("bar"));
		assertTrue(map.containsKey(IComponent.class));
		
		
		assertEquals("foo_comp", map.get("foo").getName());
	}
}
