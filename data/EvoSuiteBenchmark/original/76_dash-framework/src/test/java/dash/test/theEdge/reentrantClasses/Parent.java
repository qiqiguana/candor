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
package dash.test.theEdge.reentrantClasses;

import dash.Build;
import dash.Component;
import dash.Obtain;

@Component
public class Parent {

	@Obtain("grand_key")
	public Object grand;

	
	boolean alreadyBuilt = false;
	
	@Obtain(offerAsKey={"parent_key"})
	public Object getParentComp() {
		
		if (alreadyBuilt)
			throw new IllegalStateException();
		alreadyBuilt = true;
		
		return "parent " + grand;
	}
	
	@Build
	public Child getChild() {
		return new Child();
	}

}
