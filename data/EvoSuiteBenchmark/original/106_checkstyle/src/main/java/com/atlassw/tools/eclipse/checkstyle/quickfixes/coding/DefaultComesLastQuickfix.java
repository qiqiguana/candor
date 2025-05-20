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

package com.atlassw.tools.eclipse.checkstyle.quickfixes.coding;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jface.text.IRegion;
import org.eclipse.swt.graphics.Image;

import com.atlassw.tools.eclipse.checkstyle.quickfixes.AbstractASTResolution;
import com.atlassw.tools.eclipse.checkstyle.util.CheckstylePluginImages;

/**
 * Quickfix implementation that moves the default case of a switch statement to
 * the last position.
 * 
 * @author Lars K�dderitzsch
 */
public class DefaultComesLastQuickfix extends AbstractASTResolution
{

    /**
     * {@inheritDoc}
     */
    protected ASTVisitor handleGetCorrectingASTVisitor(final IRegion lineInfo,
            final int markerStartOffset)
    {

        return new ASTVisitor()
        {

            public boolean visit(SwitchCase node)
            {

                if (containsPosition(lineInfo, node.getStartPosition()))
                {

                    if (node.isDefault() && !isLastSwitchCase(node))
                    {
                        SwitchStatement switchStatement = (SwitchStatement) node.getParent();

                        List defaultCaseStatements = new ArrayList();
                        defaultCaseStatements.add(node);

                        // collect all statements belonging to the default case
                        int defaultStatementIndex = switchStatement.statements().indexOf(node);
                        for (int i = defaultStatementIndex + 1; i < switchStatement.statements()
                                .size(); i++)
                        {
                            ASTNode tmpNode = (ASTNode) switchStatement.statements().get(i);

                            if (!(tmpNode instanceof SwitchCase))
                            {
                                defaultCaseStatements.add(tmpNode);
                            }
                            else
                            {
                                break;
                            }
                        }

                        // move the statements to the end of the statement list
                        switchStatement.statements().removeAll(defaultCaseStatements);
                        switchStatement.statements().addAll(defaultCaseStatements);
                    }
                }
                return true;
            }

            private boolean isLastSwitchCase(SwitchCase switchCase)
            {

                SwitchStatement switchStatement = (SwitchStatement) switchCase.getParent();

                // collect all statements belonging to the default case
                int defaultStatementIndex = switchStatement.statements().indexOf(switchCase);
                for (int i = defaultStatementIndex + 1; i < switchStatement.statements().size(); i++)
                {
                    ASTNode tmpNode = (ASTNode) switchStatement.statements().get(i);

                    if (tmpNode instanceof SwitchCase)
                    {
                        return false;
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
        return Messages.DefaultComesLastQuickfix_description;
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel()
    {
        return Messages.DefaultComesLastQuickfix_label;
    }

    /**
     * {@inheritDoc}
     */
    public Image getImage()
    {
        return CheckstylePluginImages.getImage(CheckstylePluginImages.CORRECTION_CHANGE);
    }
}
