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

import java.text.Collator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.atlassw.tools.eclipse.checkstyle.CheckstylePlugin;
import com.atlassw.tools.eclipse.checkstyle.config.ConfigProperty;
import com.atlassw.tools.eclipse.checkstyle.config.meta.ConfigPropertyMetadata;

/**
 * Configuration widget for selecting multiple values with check boxes.
 */
public class ConfigPropertyWidgetMultiCheck extends ConfigPropertyWidgetAbstractBase implements
        IPreferenceChangeListener
{
    // =================================================
    // Public static final variables.
    // =================================================

    /** Resource bundle containing the token translations. */
    private static final ResourceBundle TOKEN_BUNDLE = PropertyResourceBundle
            .getBundle("com.atlassw.tools.eclipse.checkstyle.config.gui.token"); //$NON-NLS-1$

    // =================================================
    // Static class variables.
    // =================================================

    // =================================================
    // Instance member variables.
    // =================================================

    private CheckboxTableViewer mTable;

    private boolean mTranslateTokens;

    private boolean mSortTokens = true;

    private List mTokens;

    // =================================================
    // Constructors & finalizer.
    // =================================================

    /**
     * Creates the widget.
     * 
     * @param parent the parent composite
     * @param prop the property
     */
    public ConfigPropertyWidgetMultiCheck(Composite parent, ConfigProperty prop)
    {
        super(parent, prop);
        mTokens = new ArrayList(prop.getMetaData().getPropertyEnumeration());
    }

    // =================================================
    // Methods.
    // =================================================

    /**
     * {@inheritDoc}
     */
    protected Control getValueWidget(Composite parent)
    {

        if (mTable == null)
        {

            IPreferencesService prefStore = Platform.getPreferencesService();
            mTranslateTokens = prefStore.getBoolean(CheckstylePlugin.PLUGIN_ID,
                    CheckstylePlugin.PREF_TRANSLATE_TOKENS, true, null);
            mSortTokens = prefStore.getBoolean(CheckstylePlugin.PLUGIN_ID,
                    CheckstylePlugin.PREF_SORT_TOKENS, false, null);

            IEclipsePreferences instanceScope = new InstanceScope()
                    .getNode(CheckstylePlugin.PLUGIN_ID);
            instanceScope.addPreferenceChangeListener(this);

            mTable = CheckboxTableViewer.newCheckList(parent, SWT.V_SCROLL | SWT.BORDER);
            mTable.setContentProvider(new ArrayContentProvider());
            mTable.setLabelProvider(new TokenLabelProvider());

            installSorter(mSortTokens);

            mTable.setInput(mTokens);
            mTable.setCheckedElements(getInitialValues().toArray());

            GridData gd = new GridData(GridData.FILL_BOTH);
            gd.heightHint = 150;
            mTable.getControl().setLayoutData(gd);

            // deregister the listener on widget dipose
            mTable.getControl().addDisposeListener(new DisposeListener()
            {

                public void widgetDisposed(DisposeEvent e)
                {
                    IEclipsePreferences prefStore = new InstanceScope()
                            .getNode(CheckstylePlugin.PLUGIN_ID);
                    prefStore.removePreferenceChangeListener(ConfigPropertyWidgetMultiCheck.this);
                }
            });
        }

        return mTable.getControl();
    }

    /**
     * {@inheritDoc}
     */
    public String getValue()
    {
        StringBuffer buffer = new StringBuffer(""); //$NON-NLS-1$

        Object[] checkedElements = mTable.getCheckedElements();

        for (int i = 0; i < checkedElements.length; i++)
        {

            if (i > 0)
            {
                buffer.append(","); //$NON-NLS-1$
            }
            buffer.append(checkedElements[i]);
        }
        return buffer.toString();
    }

    private List getInitialValues()
    {
        List result = new LinkedList();
        StringTokenizer tokenizer = new StringTokenizer(getInitValue(), ","); //$NON-NLS-1$
        while (tokenizer.hasMoreTokens())
        {
            result.add(tokenizer.nextToken().trim());
        }

        return result;
    }

    private void installSorter(boolean sort)
    {
        if (sort)
        {
            Collator collator = Collator.getInstance(CheckstylePlugin.getPlatformLocale());
            mTable.setSorter(new ViewerSorter(collator));
        }
        else
        {
            mTable.setSorter(null);
        }
        mTable.refresh();
    }

    /**
     * @see ConfigPropertyWidgetAbstractBase#restorePropertyDefault()
     */
    public void restorePropertyDefault()
    {
        ConfigPropertyMetadata metadata = getConfigProperty().getMetaData();
        String defaultValue = metadata.getOverrideDefault() != null ? metadata.getOverrideDefault()
                : metadata.getDefaultValue();
        List result = new LinkedList();

        if (defaultValue != null)
        {
            StringTokenizer tokenizer = new StringTokenizer(defaultValue, ","); //$NON-NLS-1$
            while (tokenizer.hasMoreTokens())
            {
                result.add(tokenizer.nextToken().trim());
            }
        }

        // clear current checked state
        mTable.setCheckedElements(new Object[0]);

        mTable.setCheckedElements(result.toArray());
    }

    /**
     * {@inheritDoc}
     */
    public void preferenceChange(PreferenceChangeEvent event)
    {
        if (CheckstylePlugin.PREF_TRANSLATE_TOKENS.equals(event.getKey()))
        {
            mTranslateTokens = Boolean.valueOf((String) event.getNewValue()).booleanValue();
            mTable.refresh(true);
        }
        if (CheckstylePlugin.PREF_SORT_TOKENS.equals(event.getKey()))
        {
            mSortTokens = Boolean.valueOf((String) event.getNewValue()).booleanValue();
            installSorter(mSortTokens);
        }
    }

    /**
     * Label provider to translate checkstyle tokens into readable form.
     * 
     * @author Lars K�dderitzsch
     */
    private class TokenLabelProvider extends LabelProvider
    {

        /**
         * {@inheritDoc}
         */
        public String getText(Object element)
        {
            String translation = null;
            if (!mTranslateTokens)
            {
                translation = "" + element; //$NON-NLS-1$
            }
            else
            {
                try
                {
                    translation = TOKEN_BUNDLE.getString((String) element);
                }
                catch (MissingResourceException e)
                {
                    translation = "" + element; //$NON-NLS-1$
                }
            }
            return translation;
        }

    }

}