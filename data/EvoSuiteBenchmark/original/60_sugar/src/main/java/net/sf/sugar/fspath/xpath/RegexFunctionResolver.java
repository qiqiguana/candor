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
 * RegexFunctionResolver.java
 *
 * Created on 23 April 2008, 21:57
 *
 */

package net.sf.sugar.fspath.xpath;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;

/**
 *  This class is to be passed to an XPath instance in order to allow the 
 *  XPath instance to resolve calls to the function re:matches(String, String) .
 *
 *  Calls to this XPath funciton will invoke the RegexFunction XPathFunction implementation 
 *  defined in this package.
 * 
 *  @author kbishop
 *  @version $Id$
 */
public class RegexFunctionResolver implements javax.xml.xpath.XPathFunctionResolver {
   
    private QName regexQName;
    
    /** Creates a new instance of RegexFunctionResolver */
    public RegexFunctionResolver() {
        this.regexQName = new QName(null, "matches", "re");
    }

    public XPathFunction resolveFunction(QName functionName, int arity) {
        
        if (arity == 2 
            //&& this.regexQName.getPrefix().equals(functionName.getPrefix())
            && this.regexQName.getLocalPart().equals(functionName.getLocalPart())) {
            
            return new RegexFunction();
        } 
        
        return null;
    }
    
}
