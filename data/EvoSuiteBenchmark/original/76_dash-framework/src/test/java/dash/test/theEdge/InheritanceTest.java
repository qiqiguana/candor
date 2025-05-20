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

import dash.examples.provider.TestProvider;
import dash.providerFactory.ProviderFactory;
import dash.test.theEdge.inheritence.Bar;
import dash.test.theEdge.inheritence.Foo;
import dash.test.theEdge.inheritence.IFoo;
import junit.framework.TestCase;

public class InheritanceTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		ProviderFactory.threadLocalProvider.set(new TestProvider());
	}
	
	public void testFoo() throws Exception {
		IFoo foo = new Foo();
		
		// Non-Type Annotation don't inherit!
		// but we better document the ins and outs for posterity
		assertNull(foo.getComponent());
		assertNotNull(foo.getComponent2());
		assertNull(foo.getComponent3());
	}
	
	public void testBar() throws Exception {
		IFoo foo = new Bar();
		
		assertNotNull(foo.getComponent2());
		assertNotNull(foo.getComponent3());
	}

}
