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
 * FSNamespaceContext.java
 *
 * Created on 24 April 2008, 14:33
 *
 */

package net.sf.sugar.fspath.xpath;

import java.util.Iterator;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

/**
 *
 * @author kbishop
 * @version $Id$
 */
public class FSNamespaceContext implements NamespaceContext {
    
    /** Creates a new instance of FSNamespaceContext */
    public FSNamespaceContext() {
    }

    public Iterator getPrefixes(String namespaceURI) {
        throw new UnsupportedOperationException();
    }

    public String getPrefix(String namespaceURI) {
        throw new UnsupportedOperationException();
    }

    public String getNamespaceURI(String prefix) {
        
        if (prefix == null) throw new NullPointerException("Null prefix");
        else if ("fs".equals(prefix)) return "http://sugar.sourceforge.net/safari/fspath";
        else if ("xml".equals(prefix)) return XMLConstants.XML_NS_URI;
        return XMLConstants.NULL_NS_URI;
    }
    
}
