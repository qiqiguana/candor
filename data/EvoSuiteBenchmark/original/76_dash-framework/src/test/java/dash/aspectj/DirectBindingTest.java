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

import dash.ComponentProvider;
import dash.aspectj.Binding;
import dash.aspectj.boundAccessors.BoundAccessor;
import dash.aspectj.memberClosures.MemberClosure;
import dash.aspectj.memberClosures.MethodClosure;
import dash.examples.obtainConsumers.ObtainMethodConsumer;
import dash.examples.provider.TestProvider;
import dash.providerFactory.ProviderFactory;
import junit.framework.TestCase;

public class DirectBindingTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();

		ProviderFactory.threadLocalProvider.set(new TestProvider());

	}

	public void testDirectBindCall() throws Exception {
		ObtainMethodConsumer sumer = new ObtainMethodConsumer();
		
		MemberClosure closure = new MethodClosure("getCompMethod", sumer.getClass());

		BoundAccessor accessor = Binding.bind((ComponentProvider)sumer, closure, false);

		Object value = accessor.getReference();
		
		assertNotNull(value);
	}
}
