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

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution2;

/**
 * Interface for a quickfix implementation for checkstyle markers.
 * 
 * @author Lars K�dderitzsch
 */
public interface ICheckstyleMarkerResolution extends IMarkerResolution2
{

    /**
     * Checks if this quickfix can actually fix the marker occurrance.
     * 
     * @param marker the marker to potentially be fixed.
     * @return <code>true</code> if this quickfix can fix the marker,
     *         <code>false</code> otherwise.
     */
    boolean canFix(IMarker marker);

    /**
     * Sets if the quickfix automatically commits the changes (saves the file).
     * 
     * @param autoCommit <code>true</code> if changes are automatically
     *            committed
     */
    void setAutoCommitChanges(boolean autoCommit);
}
