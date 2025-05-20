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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.atlassw.tools.eclipse.checkstyle.CheckstylePlugin;
import com.atlassw.tools.eclipse.checkstyle.Messages;
import com.atlassw.tools.eclipse.checkstyle.config.CheckConfigurationTester;
import com.atlassw.tools.eclipse.checkstyle.config.CheckConfigurationWorkingCopy;
import com.atlassw.tools.eclipse.checkstyle.config.ResolvableProperty;
import com.atlassw.tools.eclipse.checkstyle.util.CheckstyleLog;
import com.atlassw.tools.eclipse.checkstyle.util.CheckstylePluginException;
import com.atlassw.tools.eclipse.checkstyle.util.CheckstylePluginImages;
import com.atlassw.tools.eclipse.checkstyle.util.SWTUtil;
import com.atlassw.tools.eclipse.checkstyle.util.table.EnhancedTableViewer;
import com.atlassw.tools.eclipse.checkstyle.util.table.ITableComparableProvider;
import com.atlassw.tools.eclipse.checkstyle.util.table.ITableSettingsProvider;

/**
 * Dialog to show/edit the properties (name, location, description) of a check
 * configuration. Also used to create new check configurations.
 * 
 * @author Lars K�dderitzsch
 */
public class ResolvablePropertiesDialog extends TitleAreaDialog
{

    //
    // attributes
    //

    /** The properties table. */
    private EnhancedTableViewer mTableViewer;

    /** Button to add a new property. */
    private Button mBtnAdd;

    /** Button to edit an existing property. */
    private Button mBtnEdit;

    /** Button to remove properties. */
    private Button mBtnRemove;

    /** Button to find unresolved properties. */
    private Button mBtnFind;

    /** The controller for this dialog. */
    private Controller mController = new Controller();

    /** the check configuration. */
    private CheckConfigurationWorkingCopy mCheckConfig;

    /** The list of properties. */
    private List mResolvableProperties;

    //
    // constructor
    //

    /**
     * Creates the properties dialog for check configurations.
     * 
     * @param parent the parent shell
     * @param checkConfig the check configuration to edit
     */
    public ResolvablePropertiesDialog(Shell parent, CheckConfigurationWorkingCopy checkConfig)
    {
        super(parent);
        setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
        mCheckConfig = checkConfig;
    }

    //
    // methods
    //

    /**
     * Get the check configuration from the editor.
     * 
     * @return the check configuration
     * @throws CheckstylePluginException if the data is not valid
     */
    public CheckConfigurationWorkingCopy getCheckConfiguration() throws CheckstylePluginException
    {
        return mCheckConfig;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#create()
     */
    public void create()
    {
        super.create();
        initialize();

        SWTUtil.addResizeSupport(this, CheckstylePlugin.getDefault().getDialogSettings(),
                ResolvablePropertiesDialog.class.getName());
    }

    /**
     * Creates the dialogs main contents.
     * 
     * @param parent the parent composite
     */
    protected Control createDialogArea(Composite parent)
    {

        // set the logo
        this.setTitleImage(CheckstylePluginImages.getImage(CheckstylePluginImages.PLUGIN_LOGO));
        this.setTitle(Messages.ResolvablePropertiesDialog_titleMessageArea);
        this.setMessage(Messages.ResolvablePropertiesDialog_msgAdditionalProperties);

        Composite composite = (Composite) super.createDialogArea(parent);

        Composite contents = new Composite(composite, SWT.NULL);
        contents.setLayout(new GridLayout(2, false));
        GridData fd = new GridData(GridData.FILL_BOTH);
        contents.setLayoutData(fd);

        Table table = new Table(contents, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
        table.setLayoutData(new GridData(GridData.FILL_BOTH));

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableLayout tableLayout = new TableLayout();
        table.setLayout(tableLayout);

        TableColumn column1 = new TableColumn(table, SWT.NULL);
        column1.setText(Messages.ResolvablePropertiesDialog_colName);
        tableLayout.addColumnData(new ColumnWeightData(50));

        TableColumn column2 = new TableColumn(table, SWT.NULL);
        column2.setText(Messages.ResolvablePropertiesDialog_colValue);
        tableLayout.addColumnData(new ColumnWeightData(50));

        mTableViewer = new EnhancedTableViewer(table);
        PropertiesLabelProvider multiProvider = new PropertiesLabelProvider();
        mTableViewer.setLabelProvider(multiProvider);
        mTableViewer.setTableComparableProvider(multiProvider);
        mTableViewer.setTableSettingsProvider(multiProvider);
        mTableViewer.installEnhancements();

        mTableViewer.setContentProvider(new ArrayContentProvider());
        mTableViewer.addDoubleClickListener(mController);
        mTableViewer.getTable().addKeyListener(mController);

        Composite buttonBar = new Composite(contents, SWT.NULL);
        GridLayout layout = new GridLayout(1, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        buttonBar.setLayout(layout);
        GridData gd = new GridData();
        gd.verticalAlignment = GridData.BEGINNING;
        buttonBar.setLayoutData(gd);

        mBtnAdd = new Button(buttonBar, SWT.PUSH);
        mBtnAdd.setText(Messages.ResolvablePropertiesDialog_btnAdd);
        mBtnAdd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        mBtnAdd.addSelectionListener(mController);

        mBtnEdit = new Button(buttonBar, SWT.PUSH);
        mBtnEdit.setText(Messages.ResolvablePropertiesDialog_btnEdit);
        mBtnEdit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        mBtnEdit.addSelectionListener(mController);

        mBtnRemove = new Button(buttonBar, SWT.PUSH);
        mBtnRemove.setText(Messages.ResolvablePropertiesDialog_btnRemove);
        mBtnRemove.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        mBtnRemove.addSelectionListener(mController);

        return composite;
    }

    /**
     * {@inheritDoc}
     */
    protected Control createButtonBar(Composite parent)
    {

        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(3, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        mBtnFind = new Button(composite, SWT.PUSH);
        mBtnFind.setText(Messages.ResolvablePropertiesDialog_btnFind);
        GridData gd = new GridData();
        gd.horizontalAlignment = GridData.BEGINNING;
        gd.horizontalIndent = 5;
        mBtnFind.setLayoutData(gd);
        mBtnFind.addSelectionListener(mController);

        Control buttonBar = super.createButtonBar(composite);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalAlignment = GridData.END;
        buttonBar.setLayoutData(gd);

        return composite;
    }

    /**
     * {@inheritDoc}
     */
    protected void configureShell(Shell newShell)
    {

        super.configureShell(newShell);
        newShell.setText(Messages.ResolvablePropertiesDialog_titleDialog);
    }

    /**
     * {@inheritDoc}
     */
    protected Point getInitialSize()
    {
        return new Point(650, 500);
    }

    /**
     * {@inheritDoc}
     */
    protected void okPressed()
    {

        // check for properties without value - these must be fixed before
        // OK'ing
        Iterator it = mResolvableProperties.iterator();
        while (it.hasNext())
        {

            ResolvableProperty prop = (ResolvableProperty) it.next();

            if (StringUtils.trimToNull(prop.getValue()) == null)
            {
                this.setErrorMessage(NLS.bind(
                        Messages.ResolvablePropertiesDialog_msgMissingPropertyValue, prop
                                .getPropertyName()));
                return;
            }
        }

        mCheckConfig.getResolvableProperties().clear();
        mCheckConfig.getResolvableProperties().addAll(mResolvableProperties);
        super.okPressed();
    }

    /**
     * Initialize the dialogs controls with the data.
     */
    private void initialize()
    {
        // clone the properties so that changes don't directly reflect back into
        // the configuration
        mResolvableProperties = new ArrayList();
        Iterator it = mCheckConfig.getResolvableProperties().iterator();
        while (it.hasNext())
        {
            mResolvableProperties.add(((ResolvableProperty) it.next()).clone());
        }

        mTableViewer.setInput(mResolvableProperties);
    }

    /**
     * Controller for this dialog.
     * 
     * @author Lars K�dderitzsch
     */
    private class Controller implements SelectionListener, IDoubleClickListener, KeyListener
    {

        public void widgetSelected(SelectionEvent e)
        {
            if (mBtnAdd == e.widget)
            {
                openPropertyItemEditor(null);
            }
            else if (mBtnEdit == e.widget && !mTableViewer.getSelection().isEmpty())
            {
                IStructuredSelection selection = (IStructuredSelection) mTableViewer.getSelection();

                ResolvableProperty prop = (ResolvableProperty) selection.getFirstElement();
                openPropertyItemEditor(prop);
            }
            else if (mBtnRemove == e.widget)
            {
                removePropertyItems();
            }
            else if (mBtnFind == e.widget)
            {
                findPropertyItems();
            }
        }

        public void widgetDefaultSelected(SelectionEvent e)
        {
        // NOOP
        }

        public void doubleClick(DoubleClickEvent event)
        {
            if (!event.getSelection().isEmpty())
            {
                IStructuredSelection selection = (IStructuredSelection) mTableViewer.getSelection();

                ResolvableProperty prop = (ResolvableProperty) selection.getFirstElement();
                openPropertyItemEditor(prop);
            }
        }

        /**
         * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
         */
        public void keyReleased(KeyEvent e)
        {
            if (e.widget == mTableViewer.getTable())
            {
                if (e.character == SWT.DEL)
                {
                    removePropertyItems();
                }
                if (e.character == ' ')
                {
                    IStructuredSelection selection = (IStructuredSelection) mTableViewer
                            .getSelection();

                    ResolvableProperty prop = (ResolvableProperty) selection.getFirstElement();
                    openPropertyItemEditor(prop);
                }
            }
        }

        public void keyPressed(KeyEvent e)
        {
        // NOOP
        }

        private void openPropertyItemEditor(ResolvableProperty prop)
        {

            if (prop == null)
            {
                ResolvableProperty newProp = new ResolvableProperty(null, null);

                ResolvablePropertyEditDialog dialog = new ResolvablePropertyEditDialog(getShell(),
                        newProp);
                if (Dialog.OK == dialog.open())
                {
                    mResolvableProperties.add(newProp);
                    mTableViewer.refresh();
                }
            }
            else
            {
                ResolvablePropertyEditDialog dialog = new ResolvablePropertyEditDialog(getShell(),
                        prop);
                if (Dialog.OK == dialog.open())
                {
                    mTableViewer.refresh();
                }
            }
        }

        private void removePropertyItems()
        {
            boolean confirm = MessageDialog.openQuestion(getShell(),
                    Messages.ResolvablePropertiesDialog_titleRemoveConfirmation,
                    Messages.ResolvablePropertiesDialog_msgRemoveConfirmation);
            if (confirm)
            {

                IStructuredSelection selection = (IStructuredSelection) mTableViewer.getSelection();
                mResolvableProperties.removeAll(selection.toList());
                mTableViewer.refresh();
            }
        }

        private void findPropertyItems()
        {

            CheckConfigurationWorkingCopy clone = (CheckConfigurationWorkingCopy) mCheckConfig
                    .clone();
            clone.getResolvableProperties().clear();
            clone.getResolvableProperties().addAll(mResolvableProperties);

            CheckConfigurationTester tester = new CheckConfigurationTester(clone);

            try
            {
                List unresolvedProps = tester.getUnresolvedProperties();

                // filter props already in the dialogs list
                Iterator it = unresolvedProps.iterator();
                while (it.hasNext())
                {

                    ResolvableProperty prop = (ResolvableProperty) it.next();

                    Iterator it2 = mResolvableProperties.iterator();
                    while (it2.hasNext())
                    {

                        if (prop.getPropertyName().equals(
                                ((ResolvableProperty) it2.next()).getPropertyName()))
                        {
                            it.remove(); // remove the current entry
                            break;
                        }
                    }
                }

                if (!unresolvedProps.isEmpty())
                {

                    StringBuffer buf = new StringBuffer();
                    it = unresolvedProps.iterator();
                    while (it.hasNext())
                    {
                        buf.append("\t${").append( //$NON-NLS-1$
                                ((ResolvableProperty) it.next()).getPropertyName()).append("}\n"); //$NON-NLS-1$
                    }

                    boolean confirm = MessageDialog.openQuestion(getShell(),
                            Messages.ResolvablePropertiesDialog_titleFoundProperties, NLS.bind(
                                    Messages.ResolvablePropertiesDialog_msgFoundProperties, buf));
                    if (confirm)
                    {
                        mResolvableProperties.addAll(unresolvedProps);
                        mTableViewer.refresh();
                    }
                }
                else
                {
                    MessageDialog
                            .openInformation(
                                    getShell(),
                                    Messages.ResolvablePropertiesDialog_titleNoUnresolvedProps,
                                    Messages.ResolvablePropertiesDialog_msgNoUnresolvedProps);
                }
            }
            catch (CheckstylePluginException e)
            {
                CheckstyleLog.errorDialog(getShell(), e, true);
            }
        }

    }

    /**
     * Label provider for the check configuration table. Implements also support
     * for table sorting and storing of the table settings.
     * 
     * @author Lars K�dderitzsch
     */
    private class PropertiesLabelProvider extends LabelProvider implements ITableLabelProvider,
            ITableComparableProvider, ITableSettingsProvider
    {

        /**
         * {@inheritDoc}
         */
        public String getColumnText(Object element, int columnIndex)
        {
            String result = element.toString();
            if (element instanceof ResolvableProperty)
            {
                ResolvableProperty prop = (ResolvableProperty) element;
                if (columnIndex == 0)
                {
                    result = prop.getPropertyName();
                }
                if (columnIndex == 1)
                {
                    result = prop.getValue();
                }
            }
            return result;
        }

        /**
         * {@inheritDoc}
         */
        public Image getColumnImage(Object element, int columnIndex)
        {
            return columnIndex == 0 ? getImage(element) : null;
        }

        /**
         * {@inheritDoc}
         */
        public Comparable getComparableValue(Object element, int col)
        {
            return getColumnText(element, col);
        }

        /**
         * {@inheritDoc}
         */
        public IDialogSettings getTableSettings()
        {
            String concreteViewId = ResolvablePropertiesDialog.class.getName();

            IDialogSettings workbenchSettings = CheckstylePlugin.getDefault().getDialogSettings();
            IDialogSettings settings = workbenchSettings.getSection(concreteViewId);

            if (settings == null)
            {
                settings = workbenchSettings.addNewSection(concreteViewId);
            }

            return settings;
        }
    }
}