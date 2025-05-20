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

package com.atlassw.tools.eclipse.checkstyle.properties;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.atlassw.tools.eclipse.checkstyle.Messages;
import com.atlassw.tools.eclipse.checkstyle.config.CheckConfigurationWorkingCopy;
import com.atlassw.tools.eclipse.checkstyle.config.ICheckConfiguration;
import com.atlassw.tools.eclipse.checkstyle.config.gui.CheckConfigurationConfigureDialog;
import com.atlassw.tools.eclipse.checkstyle.config.gui.CheckConfigurationLabelProvider;
import com.atlassw.tools.eclipse.checkstyle.config.gui.CheckConfigurationViewerSorter;
import com.atlassw.tools.eclipse.checkstyle.projectconfig.FileMatchPattern;
import com.atlassw.tools.eclipse.checkstyle.projectconfig.FileSet;
import com.atlassw.tools.eclipse.checkstyle.util.CheckstyleLog;
import com.atlassw.tools.eclipse.checkstyle.util.CheckstylePluginException;

/**
 * Simple file sets editor producing only one file set that contains all files.
 * Only the check configuration can be chosen.
 * 
 * @author Lars K�dderitzsch
 */
public class SimpleFileSetsEditor implements IFileSetsEditor
{

    //
    // attributes
    //

    /** viewer to display the known checkstyle configurations. */
    private ComboViewer mComboViewer;

    /** used to display the config description. */
    private Text mTxtConfigDescription;

    /** button to open the check configuration preferences page. */
    private Button mBtnManageConfigs;

    private List mFileSets;

    private FileSet mDefaultFileSet;

    private Controller mController;

    private CheckstylePropertyPage mPropertyPage;

    //
    // constructor
    //

    /**
     * Creates the SimpleFileSetsEditor.
     * 
     * @param propsPage the property page
     */
    public SimpleFileSetsEditor(CheckstylePropertyPage propsPage)
    {
        mPropertyPage = propsPage;
    }

    //
    // methods
    //

    /**
     * {@inheritDoc}
     */
    public void setFileSets(List fileSets) throws CheckstylePluginException
    {
        mFileSets = fileSets;

        ICheckConfiguration config = null;
        if (mFileSets.size() > 0)
        {
            config = ((FileSet) mFileSets.get(0)).getCheckConfig();
        }

        if (config == null)
        {
            CheckConfigurationWorkingCopy[] allConfigs = mPropertyPage
                    .getProjectConfigurationWorkingCopy().getGlobalCheckConfigWorkingSet()
                    .getWorkingCopies();
            if (allConfigs.length > 0)
            {
                config = (ICheckConfiguration) allConfigs[0];
            }
        }

        mDefaultFileSet = new FileSet(Messages.SimpleFileSetsEditor_nameAllFileset, config);
        mDefaultFileSet.getFileMatchPatterns().add(new FileMatchPattern(".")); //$NON-NLS-1$
        mFileSets.clear();
        mFileSets.add(mDefaultFileSet);
    }

    /**
     * {@inheritDoc}
     */
    public List getFileSets()
    {
        return mFileSets;
    }

    /**
     * {@inheritDoc}
     */
    public Control createContents(Composite parent) throws CheckstylePluginException
    {

        mController = new Controller();

        // group composite containing the config settings
        Group configArea = new Group(parent, SWT.NULL);
        configArea.setText(Messages.SimpleFileSetsEditor_titleSimpleConfig);
        configArea.setLayout(new FormLayout());

        this.mBtnManageConfigs = new Button(configArea, SWT.PUSH);
        this.mBtnManageConfigs.setText(Messages.SimpleFileSetsEditor_btnManageConfigs);
        this.mBtnManageConfigs.addSelectionListener(mController);
        FormData fd = new FormData();
        fd.top = new FormAttachment(0, 3);
        fd.right = new FormAttachment(100, -3);
        this.mBtnManageConfigs.setLayoutData(fd);

        mComboViewer = new ComboViewer(configArea);
        mComboViewer.getCombo().setVisibleItemCount(10);
        mComboViewer.setContentProvider(new CheckConfigurationContentProvider());
        mComboViewer.setLabelProvider(new CheckConfigurationLabelProvider());
        mComboViewer.setSorter(new CheckConfigurationViewerSorter());
        mComboViewer.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        mComboViewer.addSelectionChangedListener(mController);
        fd = new FormData();
        fd.left = new FormAttachment(0, 3);
        fd.top = new FormAttachment(0, 3);
        fd.right = new FormAttachment(mBtnManageConfigs, -3, SWT.LEFT);
        // fd.right = new FormAttachment(100, -3);
        mComboViewer.getCombo().setLayoutData(fd);

        // Description
        Label lblConfigDesc = new Label(configArea, SWT.LEFT);
        lblConfigDesc.setText(Messages.SimpleFileSetsEditor_lblDescription);
        fd = new FormData();
        fd.left = new FormAttachment(0, 3);
        fd.top = new FormAttachment(mComboViewer.getCombo(), 3, SWT.BOTTOM);
        fd.right = new FormAttachment(100, -3);
        lblConfigDesc.setLayoutData(fd);

        this.mTxtConfigDescription = new Text(configArea, SWT.LEFT | SWT.WRAP | SWT.MULTI
                | SWT.READ_ONLY | SWT.BORDER | SWT.VERTICAL);
        fd = new FormData();
        fd.left = new FormAttachment(0, 3);
        fd.top = new FormAttachment(lblConfigDesc, 0, SWT.BOTTOM);
        fd.right = new FormAttachment(100, -3);
        fd.bottom = new FormAttachment(100, -3);
        this.mTxtConfigDescription.setLayoutData(fd);

        // init the check configuration combo
        mComboViewer.setInput(mPropertyPage.getProjectConfigurationWorkingCopy());
        if (mDefaultFileSet.getCheckConfig() != null)
        {
            mComboViewer.setSelection(new StructuredSelection(mDefaultFileSet.getCheckConfig()));
        }

        return configArea;
    }

    /**
     * {@inheritDoc}
     */
    public void refresh()
    {
        mComboViewer.refresh();
    }

    /**
     * Controller for this file set editor.
     * 
     * @author Lars K�dderitzsch
     */
    private class Controller implements SelectionListener, ISelectionChangedListener
    {

        /**
         * @see SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        public void widgetSelected(SelectionEvent e)
        {
            if (mBtnManageConfigs == e.widget)
            {
                ICheckConfiguration config = mDefaultFileSet.getCheckConfig();

                if (config != null)
                {
                    IProject project = (IProject) mPropertyPage.getElement();

                    try
                    {
                        config.getCheckstyleConfiguration();

                        CheckConfigurationWorkingCopy workingCopy = (CheckConfigurationWorkingCopy) config;

                        CheckConfigurationConfigureDialog dialog = new CheckConfigurationConfigureDialog(
                                mTxtConfigDescription.getShell(), workingCopy);
                        dialog.setBlockOnOpen(true);
                        dialog.open();
                    }
                    catch (CheckstylePluginException ex)
                    {
                        CheckstyleLog.warningDialog(mPropertyPage.getShell(), Messages.bind(
                                Messages.CheckstylePreferencePage_msgProjectRelativeConfigNoFound,
                                project, config.getLocation()), ex);
                    }
                }
            }
        }

        /**
         * @see SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
         */
        public void widgetDefaultSelected(SelectionEvent e)
        {
        // NOOP
        }

        /**
         * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
         */
        public void selectionChanged(SelectionChangedEvent event)
        {
            IStructuredSelection selection = (IStructuredSelection) event.getSelection();
            ICheckConfiguration config = (ICheckConfiguration) selection.getFirstElement();

            if (config != null)
            {
                mDefaultFileSet.setCheckConfig(config);
                mTxtConfigDescription.setText(config.getDescription() != null ? config
                        .getDescription() : ""); //$NON-NLS-1$
            }
            else
            {
                mComboViewer.setSelection(new StructuredSelection(mComboViewer.getElementAt(0)));
            }

            mPropertyPage.getContainer().updateButtons();
        }
    }
}