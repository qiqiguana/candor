/*
 * ReflectionServiceImpl.java
 * Copyright 2008-2009 Patrick Meade
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sourceforge.nekomud.reflect;

import java.lang.reflect.Field;

import net.sourceforge.nekomud.service.ReflectionService;

public class ReflectionServiceImpl implements ReflectionService
{
	@Override
	public Object get(Object o, String path)
	{
		String[] pathElements = path.split("\\.");
		if(pathElements.length == 1) {
			Field[] fields = o.getClass().getDeclaredFields();
			for(Field field : fields) {
				System.err.println(field.getName());
				if(pathElements[0].equals(field.getName())) {
					field.setAccessible(true);
					try {
						field.get(o);
					} catch(IllegalAccessException e) {
						return null;
					}
					return o;
				}
			}
			return null;
		} else {
			Object x = get(o, pathElements[0]);
			if(x != null) {
				Object y = get(x, path.substring(path.indexOf('.')+1, path.length()));
				return y;
			}
			return null;
		}
	}
}
