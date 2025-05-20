//============================================================================
//
// Copyright (C) 2002-2007  David Schneider, Lars K�dderitzsch
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
//============================================================================

package com.atlassw.tools.eclipse.checkstyle.config.configtypes;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.JavaCore;

import com.puppycrawl.tools.checkstyle.PropertyResolver;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Property resolver that tries to resolve values from classpath variables.
 * 
 * @author Lars K�dderitzsch
 */
public class ClasspathVariableResolver implements PropertyResolver
{
    /**
     * {@inheritDoc}
     */
    public String resolve(String aName) throws CheckstyleException
    {

        IPath var = JavaCore.getClasspathVariable(aName);
        return var != null ? var.toOSString() : null;
    }
}
