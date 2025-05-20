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

package com.atlassw.tools.eclipse.checkstyle.quickfixes;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.progress.UIJob;

import com.atlassw.tools.eclipse.checkstyle.CheckstylePlugin;
import com.atlassw.tools.eclipse.checkstyle.Messages;
import com.atlassw.tools.eclipse.checkstyle.builder.CheckstyleMarker;

/**
 * Job implementation that tries to fix all Checkstyle markers in a file.
 * 
 * @author Lars K�dderitzsch
 */
public class FixCheckstyleMarkersJob extends UIJob
{

    private IFile mFile;

    /**
     * Creates the job.
     * 
     * @param file the file to fix
     */
    public FixCheckstyleMarkersJob(IFile file)
    {
        super(Messages.FixCheckstyleMarkersJob_title);
        this.mFile = file;
    }

    /**
     * {@inheritDoc}
     */
    public IStatus runInUIThread(IProgressMonitor monitor)
    {

        try
        {

            CheckstyleMarkerResolutionGenerator generator = new CheckstyleMarkerResolutionGenerator();

            IMarker[] markers = mFile.findMarkers(CheckstyleMarker.MARKER_ID, true,
                    IResource.DEPTH_INFINITE);

            for (int i = 0, size = markers.length; i < size; i++)
            {

                ICheckstyleMarkerResolution[] resolutions = (ICheckstyleMarkerResolution[]) generator
                        .getResolutions(markers[i]);

                for (int j = 0, size2 = resolutions.length; j < size2; j++)
                {
                    resolutions[j].run(markers[i]);

                    break; // stop after the first fix for this marker

                }
            }
        }
        catch (CoreException e)
        {
            return new Status(IStatus.ERROR, CheckstylePlugin.PLUGIN_ID, IStatus.OK,
                    e.getMessage(), e);
        }

        return Status.OK_STATUS;
    }
}
