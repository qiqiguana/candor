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
package dash.test.basics.obtain;

import junit.framework.TestCase;
import dash.examples.component.IComponent;
import dash.examples.obtainConsumers.ObtainFieldConsumer;
import dash.examples.obtainConsumers.ObtainMethodConsumer;
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

	public void testObtainFieldOnce() throws Exception {
		ObtainFieldConsumer sumer = new ObtainFieldConsumer();
		
		IComponent comp = sumer.comp;
		assertNotNull(comp);
		
		IComponent comp2 = sumer.comp;
		assertEquals(comp, comp2);
	}
	
	public void testObtainMethodOnce() throws Exception {
		ObtainMethodConsumer sumer = new ObtainMethodConsumer();
		
		IComponent comp = sumer.getCompMethod();
		assertNotNull(comp);
		
		IComponent comp2 = sumer.getCompMethod();
		assertEquals(comp, comp2);
	}
	
}
