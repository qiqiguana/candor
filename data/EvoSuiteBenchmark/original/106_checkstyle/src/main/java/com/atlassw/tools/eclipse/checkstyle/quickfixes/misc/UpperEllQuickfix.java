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

package com.atlassw.tools.eclipse.checkstyle.quickfixes.misc;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jface.text.IRegion;
import org.eclipse.swt.graphics.Image;

import com.atlassw.tools.eclipse.checkstyle.quickfixes.AbstractASTResolution;
import com.atlassw.tools.eclipse.checkstyle.util.CheckstylePluginImages;

/**
 * Quickfix implementation which changes lowercase 'l' occurrances in long
 * literals into uppercase 'L' to enchance readability.
 * 
 * @author Lars K�dderitzsch
 */
public class UpperEllQuickfix extends AbstractASTResolution
{

    /**
     * {@inheritDoc}
     */
    protected ASTVisitor handleGetCorrectingASTVisitor(final IRegion lineInfo,
            final int markerStartOffset)
    {
        return new ASTVisitor()
        {

            public boolean visit(NumberLiteral node)
            {
                if (containsPosition(node, markerStartOffset))
                {

                    String token = node.getToken();
                    if (token.endsWith("l")) //$NON-NLS-1$
                    {
                        token = token.replace('l', 'L');
                        node.setToken(token);
                    }
                }
                return true;
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription()
    {
        return Messages.UpperEllQuickfix_description;
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel()
    {
        return Messages.UpperEllQuickfix_label;
    }

    /**
     * {@inheritDoc}
     */
    public Image getImage()
    {
        return CheckstylePluginImages.getImage(CheckstylePluginImages.CORRECTION_CHANGE);
    }

}
