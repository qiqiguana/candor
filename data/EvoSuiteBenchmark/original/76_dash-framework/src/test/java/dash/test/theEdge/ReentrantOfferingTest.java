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
package dash.test.theEdge;

import dash.providerFactory.ProviderFactory;
import dash.test.theEdge.reentrantClasses.Child;
import dash.test.theEdge.reentrantClasses.GrandParent;
import dash.test.theEdge.reentrantClasses.Parent;
import junit.framework.TestCase;

public class ReentrantOfferingTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testObtainOfferedComponent() throws Exception {
		ProviderFactory.threadLocalProvider.remove();
		
		GrandParent grand_parent = new GrandParent();
		
		Parent parent = grand_parent.getParent();
		
		Child child = parent.getChild();
		assertNotNull(child);
		Child child2 = parent.getChild();
		assertEquals(child, child2);
		
		Object chComp = child.str;
		assertNotNull(chComp);
		assertEquals("parent grand", child.str);
	}

	public void testObtainOfferedComponentOnlyOnce() throws Exception {
		ProviderFactory.threadLocalProvider.remove();
		
		GrandParent grand_parent = new GrandParent();
		
		// access grand comp
		assertEquals("grand", grand_parent.getGrandComp());
		
		Parent parent = grand_parent.getParent();
		
		Child child = parent.getChild();
		assertNotNull(child);
		Child child2 = parent.getChild();
		assertEquals(child, child2);
		
		Object chComp = child.str;
		assertNotNull(chComp);
		assertEquals("parent grand", child.str);
	}

	public void testObtainOfferedComponentFromTwoProperties() throws Exception {
		ProviderFactory.threadLocalProvider.remove();
		
		GrandParent grand_parent = new GrandParent();
		
		// access grand comp
		assertEquals("grand", grand_parent.getGrandComp());
		
		Parent parent = grand_parent.getParent();
		
		Child child = parent.getChild();
		assertNotNull(child);
		Child child2 = parent.getChild();
		assertEquals(child, child2);
		
		Object chComp = child.str;
		assertNotNull(chComp);
		assertEquals("parent grand", child.str);
		
		Object chComp2 = child.str2;
		assertNotNull(chComp2);
		assertEquals("parent grand", child.str2);
	}

}
