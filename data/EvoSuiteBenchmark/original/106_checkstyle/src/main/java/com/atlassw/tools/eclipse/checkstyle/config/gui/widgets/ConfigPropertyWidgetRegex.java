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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.jface.contentassist.SubjectControlContentAssistant;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.contentassist.ContentAssistHandler;

import com.atlassw.tools.eclipse.checkstyle.CheckstylePlugin;
import com.atlassw.tools.eclipse.checkstyle.Messages;
import com.atlassw.tools.eclipse.checkstyle.config.ConfigProperty;
import com.atlassw.tools.eclipse.checkstyle.config.meta.ConfigPropertyMetadata;
import com.atlassw.tools.eclipse.checkstyle.util.CheckstylePluginException;
import com.atlassw.tools.eclipse.checkstyle.util.regex.RegExContentAssistProcessor;

/**
 * A string property configuration widget.
 */
public class ConfigPropertyWidgetRegex extends ConfigPropertyWidgetAbstractBase
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

    private Composite mContents;

    private Text mTextWidget;

    private Text mRegexTestWidget;

    private String mDefaultMessage = Messages.ConfigPropertyWidgetRegex_msgRegexTestString;

    private Color mRedColor;

    private Color mGreenColor;

    private Color mTextBgColor;

    // =================================================
    // Constructors & finalizer.
    // =================================================

    /**
     * Creates the widget.
     * 
     * @param parent the parent composite
     * @param prop the property
     */
    public ConfigPropertyWidgetRegex(Composite parent, ConfigProperty prop)
    {
        super(parent, prop);
        mGreenColor = new Color(parent.getDisplay(), 219, 235, 204);
        mRedColor = new Color(parent.getDisplay(), 255, 225, 225);

    }

    // =================================================
    // Methods.
    // =================================================

    /**
     * {@inheritDoc}
     */
    protected Control getValueWidget(Composite parent)
    {

        if (mContents == null)
        {

            mContents = new Composite(parent, SWT.NULL);
            mContents.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            GridLayout layout = new GridLayout(2, false);
            layout.marginWidth = 0;
            layout.marginHeight = 0;
            mContents.setLayout(layout);

            //
            // Create a text entry field.
            //
            mTextWidget = new Text(mContents, SWT.SINGLE | SWT.BORDER);
            mTextWidget.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            mTextWidget.addKeyListener(new RegexTestListener());
            mTextBgColor = mTextWidget.getBackground();

            // integrate content assist
            ContentAssistHandler.createHandlerForText(mTextWidget, createContentAssistant());

            String initValue = getInitValue();
            if (initValue != null)
            {
                mTextWidget.setText(initValue);
            }

            mRegexTestWidget = new Text(mContents, SWT.SINGLE | SWT.BORDER);
            mRegexTestWidget.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            mRegexTestWidget.setText(mDefaultMessage);
            mRegexTestWidget.addKeyListener(new RegexTestListener());
            mRegexTestWidget.addFocusListener(new FocusListener()
            {

                public void focusGained(FocusEvent e)
                {
                    Display.getCurrent().asyncExec(new Runnable()
                    {

                        public void run()
                        {
                            if (mRegexTestWidget.getText().equals(mDefaultMessage))
                            {
                                mRegexTestWidget.selectAll();
                            }
                        }
                    });
                }

                public void focusLost(FocusEvent e)
                {
                // NOOP
                }
            });

        }

        return mTextWidget;
    }

    /**
     * {@inheritDoc}
     */
    public String getValue()
    {
        String result = mTextWidget.getText();
        if (result == null)
        {
            result = ""; //$NON-NLS-1$
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public void restorePropertyDefault()
    {
        ConfigPropertyMetadata metadata = getConfigProperty().getMetaData();
        String defaultValue = metadata.getOverrideDefault() != null ? metadata.getOverrideDefault()
                : metadata.getDefaultValue();
        mTextWidget.setText(defaultValue != null ? defaultValue : ""); //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     */
    public void validate() throws CheckstylePluginException
    {
        try
        {
            //
            // Compile the text to a regex pattern
            //
            Pattern.compile(mTextWidget.getText());
        }
        catch (PatternSyntaxException e)
        {
            CheckstylePluginException.rethrow(e, e.getLocalizedMessage());
        }
    }

    private void testRegex()
    {
        if (mDefaultMessage.equals(mRegexTestWidget.getText()))
        {
            return;
        }

        try
        {
            Pattern pattern = Pattern.compile(mTextWidget.getText());
            Matcher matcher = pattern.matcher(mRegexTestWidget.getText());
            if (matcher.find())
            {
                mRegexTestWidget.setBackground(mGreenColor);
            }
            else
            {
                mRegexTestWidget.setBackground(mRedColor);
            }

            mTextWidget.setBackground(mTextBgColor);
        }
        catch (PatternSyntaxException e)
        {
            mTextWidget.setBackground(mRedColor);
        }
    }

    /**
     * Creates the content assistant.
     * 
     * @return the content assistant
     */
    private SubjectControlContentAssistant createContentAssistant()
    {

        final SubjectControlContentAssistant contentAssistant = new SubjectControlContentAssistant();

        contentAssistant.setRestoreCompletionProposalSize(CheckstylePlugin.getDefault()
                .getDialogSettings());

        IContentAssistProcessor processor = new RegExContentAssistProcessor(true);
        contentAssistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
        contentAssistant
                .setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
        contentAssistant.setInformationControlCreator(new IInformationControlCreator()
        {
            /*
             * @see IInformationControlCreator#createInformationControl(Shell)
             */
            public IInformationControl createInformationControl(Shell parent)
            {
                return new DefaultInformationControl(parent);
            }
        });

        return contentAssistant;
    }

    /**
     * Simple key listener to test the regular expression.
     * 
     * @author Lars Koedderitzsch
     */
    private class RegexTestListener implements KeyListener
    {

        public void keyPressed(KeyEvent e)
        {
        // NOOP
        }

        public void keyReleased(KeyEvent e)
        {
            testRegex();
        }
    }
}