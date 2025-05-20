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
package dash.test.basics.build;

import junit.framework.TestCase;
import dash.examples.buildConsumers.BuildFieldConsumer;
import dash.examples.buildConsumers.BuildMethodConsumer;
import dash.examples.buildConsumers.ChildComponent;
import dash.examples.buildConsumers.ParentComponent;
import dash.examples.component.IComponent;
import dash.examples.provider.TestProvider;
import dash.providerFactory.ProviderFactory;

public class BasicGoalTests extends TestCase {

	public static void main(String[] args) {
	}

	protected void setUp() throws Exception {
		super.setUp();
		ProviderFactory.threadLocalProvider.set(new TestProvider());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBuildFieldOnce() throws Exception {
		BuildFieldConsumer sumer = new BuildFieldConsumer();
		
		IComponent comp = sumer.comp;
		assertNotNull(comp);
		
		IComponent comp2 = sumer.comp;
		assertEquals(comp, comp2);
	}
	
	public void testBuildMethodOnce() throws Exception {
		BuildMethodConsumer sumer = new BuildMethodConsumer();
		
		IComponent comp = sumer.getCompMethod();
		assertNotNull(comp);
		
		IComponent comp2 = sumer.getCompMethod();
		assertEquals(comp, comp2);
	}

	public void testObtainOfferedComponent() throws Exception {
		ProviderFactory.threadLocalProvider.remove();
		
		ParentComponent parent = new ParentComponent();
		
		ChildComponent child = parent.getChild();
		assertNotNull(child);
		ChildComponent child2 = parent.getChild();
		assertEquals(child, child2);
		
		Object chComp = child.comp;
		assertNotNull(chComp);
		assertEquals(chComp, child.comp);
		
		assertNotNull(parent.getComp());
		
		assertEquals(parent.getComp(), child.comp);
	}
}
