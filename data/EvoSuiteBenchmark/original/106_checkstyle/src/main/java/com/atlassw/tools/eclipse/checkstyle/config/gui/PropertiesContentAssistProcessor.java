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

package com.atlassw.tools.eclipse.checkstyle.config.gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.contentassist.IContentAssistSubjectControl;
import org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor;
import org.eclipse.jface.contentassist.SubjectControlContextInformationValidator;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import com.atlassw.tools.eclipse.checkstyle.Messages;

/**
 * Provides content assist for builtin properties.
 * 
 * @author Lars K�dderitzsch
 */
public class PropertiesContentAssistProcessor implements IContentAssistProcessor,
        ISubjectControlContentAssistProcessor
{

    /** The context information validator. */
    private IContextInformationValidator mValidator = new SubjectControlContextInformationValidator(
            this);

    /**
     * {@inheritDoc}
     */
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public char[] getCompletionProposalAutoActivationCharacters()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public char[] getContextInformationAutoActivationCharacters()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getErrorMessage()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public IContextInformationValidator getContextInformationValidator()
    {
        return mValidator;
    }

    /**
     * {@inheritDoc}
     */
    public ICompletionProposal[] computeCompletionProposals(
            IContentAssistSubjectControl contentAssistSubjectControl, int documentOffset)
    {
        List proposals = new ArrayList();

        String basedir = "${basedir}"; //$NON-NLS-1$
        String projectLoc = "${project_loc}"; //$NON-NLS-1$
        String workspaceLoc = "${workspace_loc}"; //$NON-NLS-1$
        String configLoc = "${config_loc}"; //$NON-NLS-1$
        String samedir = "${samedir}"; //$NON-NLS-1$

        // TODO translate the descriptions

        proposals.add(new CompletionProposal(basedir, documentOffset, 0, basedir.length(), null,
                basedir, null, Messages.PropertiesContentAssistProcessor_basedir));
        proposals.add(new CompletionProposal(projectLoc, documentOffset, 0, projectLoc.length(),
                null, projectLoc, null, Messages.PropertiesContentAssistProcessor_projectLoc));
        proposals.add(new CompletionProposal(workspaceLoc, documentOffset, 0,
                workspaceLoc.length(), null, workspaceLoc, null,
                Messages.PropertiesContentAssistProcessor_workspaceLoc));
        proposals.add(new CompletionProposal(configLoc, documentOffset, 0, configLoc.length(),
                null, configLoc, null, Messages.PropertiesContentAssistProcessor_configLoc));
        proposals.add(new CompletionProposal(samedir, documentOffset, 0, samedir.length(), null,
                samedir, null, Messages.PropertiesContentAssistProcessor_samedir));

        return (ICompletionProposal[]) proposals.toArray(new ICompletionProposal[proposals.size()]);
    }

    /**
     * {@inheritDoc}
     */
    public IContextInformation[] computeContextInformation(
            IContentAssistSubjectControl contentAssistSubjectControl, int documentOffset)
    {
        return null;
    }

}
