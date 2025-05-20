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

package com.atlassw.tools.eclipse.checkstyle.quickfixes.blocks;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jface.text.IRegion;
import org.eclipse.swt.graphics.Image;

import com.atlassw.tools.eclipse.checkstyle.quickfixes.AbstractASTResolution;
import com.atlassw.tools.eclipse.checkstyle.util.CheckstylePluginImages;

/**
 * Quickfix implementation that removes nested blocks.
 * 
 * @author Lars K�dderitzsch
 */
public class AvoidNextedBlocksQuickfix extends AbstractASTResolution
{

    /**
     * {@inheritDoc}
     */
    protected ASTVisitor handleGetCorrectingASTVisitor(final IRegion lineInfo,
            final int markerStartOffset)
    {

        return new ASTVisitor()
        {

            public boolean visit(Block node)
            {

                if (containsPosition(lineInfo, node.getStartPosition()))
                {

                    if (node.getParent() instanceof Block)
                    {

                        List statements = ((Block) node.getParent()).statements();
                        int index = statements.indexOf(node);

                        statements.remove(node);
                        statements.addAll(index, ASTNode.copySubtrees(node.getAST(), node
                                .statements()));

                    }
                    else if (node.getParent() instanceof SwitchStatement)
                    {

                        List statements = ((SwitchStatement) node.getParent()).statements();
                        int index = statements.indexOf(node);

                        statements.remove(node);
                        statements.addAll(index, ASTNode.copySubtrees(node.getAST(), node
                                .statements()));
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
        return Messages.AvoidNextedBlocksQuickfix_description;
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel()
    {
        return Messages.AvoidNextedBlocksQuickfix_label;
    }

    /**
     * {@inheritDoc}
     */
    public Image getImage()
    {
        return CheckstylePluginImages.getImage(CheckstylePluginImages.CORRECTION_REMOVE);
    }
}
