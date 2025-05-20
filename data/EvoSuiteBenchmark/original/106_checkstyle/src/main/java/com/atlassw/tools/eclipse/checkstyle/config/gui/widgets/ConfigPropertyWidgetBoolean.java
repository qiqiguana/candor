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

package com.atlassw.tools.eclipse.checkstyle.config.gui.widgets;

//=================================================
// Imports from java namespace
//=================================================

//=================================================
// Imports from javax namespace
//=================================================

//=================================================
// Imports from com namespace
//=================================================
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.atlassw.tools.eclipse.checkstyle.config.ConfigProperty;
import com.atlassw.tools.eclipse.checkstyle.config.meta.ConfigPropertyMetadata;

/**
 * Boolean configuration widget.
 */
public class ConfigPropertyWidgetBoolean extends ConfigPropertyWidgetAbstractBase
{
    // =================================================
    // Public static final variables.
    // =================================================

    // =================================================
    // Static class variables.
    // =================================================

    // =================================================
    // Instance member variables.
    // =================================================

    private Button mCheckbox;

    // =================================================
    // Constructors & finalizer.
    // =================================================

    /**
     * Creates the widget.
     * 
     * @param parent the parent composite
     * @param prop the property
     */
    public ConfigPropertyWidgetBoolean(Composite parent, ConfigProperty prop)
    {
        super(parent, prop);
    }

    // =================================================
    // Methods.
    // =================================================

    /**
     * @see ConfigPropertyWidgetAbstractBase#getValueWidget(org.eclipse.swt.widgets.Composite)
     */
    protected Control getValueWidget(Composite parent)
    {
        if (mCheckbox == null)
        {

            //
            // Create a check box for selecting true or false.
            //

            mCheckbox = new Button(parent, SWT.CHECK);
            mCheckbox.setLayoutData(new GridData());

            String initValue = getInitValue();
            mCheckbox.setSelection(Boolean.valueOf(initValue).booleanValue());

        }
        return mCheckbox;
    }

    /**
     * {@inheritDoc}
     */
    public String getValue()
    {
        return "" + mCheckbox.getSelection(); //$NON-NLS-1$
    }

    /**
     * @see ConfigPropertyWidgetAbstractBase#restorePropertyDefault()
     */
    public void restorePropertyDefault()
    {
        ConfigPropertyMetadata metadata = getConfigProperty().getMetaData();
        String defaultValue = metadata.getOverrideDefault() != null ? metadata.getOverrideDefault()
                : metadata.getDefaultValue();
        mCheckbox.setSelection(Boolean.valueOf(defaultValue).booleanValue());
    }
}