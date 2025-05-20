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
package dash.examples.offering;

import dash.Component;
import dash.Obtain;
import dash.examples.component.IComponent;

@Component
public class OfferField {

	@Obtain(offerAsKey={"foo"})
	public IComponent foo_comp;

	@Obtain(offerAsKey={"bar"})
	public IComponent bar_comp;

	@Obtain(offerAs={IComponent.class})
	public IComponent cls_comp;
}
