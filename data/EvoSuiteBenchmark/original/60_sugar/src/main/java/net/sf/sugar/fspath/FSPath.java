/**
 * Copyright 2008 (C) Keith Bishop (bishi@users.sourceforge.net)
 *
 * All rights reserved.
 *
 * This file is part of FSPath.
 *
 * FSPath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FSPath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FSPath.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * FSPath.java
 *
 * Created on 18 September 2006, 00:03
 *
 */

package net.sf.sugar.fspath;

import java.io.File;
import java.util.List;
import javax.xml.namespace.QName;


/**
 *  FSPath
 *
 *  An object which once instantiated, creates a DOM representation of the filesystem.
 *  this can then be queried using an FSPath expression.
 *
 *
 * @author keith
 * @version $Id$
 */
public interface FSPath {

	/**
	 *  Retrieves a list of <code>FSPathResult</code> objects based on the FSPath query provided.
	 *
	 *  Once the list of File objects is retrieved, it is then possible to call methods on the
	 *  resulting FSPathResultList in order to delete, copy, rename and move each of the files.
	 *
	 *  Although the most common use case will result in returning a list of java.io.File objects (each wrapped by an FSPathResult object).
	 *  It is possible to pass a query which returns a list of String, Double, Boolean or Date objects
	 */
    public FSPathResultList query(String expression);

	/**
	 *  Retrieves a list of <code>FSPathResult</code> objects based on the FSPath query provided.
	 *  The return type is used to specify what the supplied expression will return.
	 *
	 *  For instance the following FSPath expression would return a single numerical value:
	 *  <pre>
	 *  count(//file)
	 *  </pre>
     *
	 *  @see javax.xml.xpath.XPathConstants for which return types are available.
	 *
	 */
    public FSPathResultList query(String expression, QName returnType);

	/**
	 *  Returns the directory from which the FSPath object performs it's searches.
	 */
    public File getRootDirectory() ;

}
