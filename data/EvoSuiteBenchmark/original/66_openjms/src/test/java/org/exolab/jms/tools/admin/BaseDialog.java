/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2000 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: BaseDialog.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
 *
 * Date         Author  Changes
 * $Date	    jimm    Created
 */

package org.exolab.jms.tools.admin;

import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


/**
 * A simple dialog to collect information for creating queue/topics and
 * consumers.
 *
 * <P>For consumers only a name is needed.
 *
 * <P>For queue/topics a name and a simple radio button to select if this
 * queue/topic is a Queue or a topic.
 *
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @see         AdminMgr
 */
abstract public class BaseDialog extends JDialog {

    // The name chosen for this object
    protected String name_;

    // shared gui fields
    protected JTextField displayText;

    // The two possible states of theis dialog.
    final static public int CANCELED = 1;
    final static public int CONFIRMED = 2;

    // The command status used to shutdown this window.
    protected int status_;


    /**
     * Creates new form BaseDialog
     *
     * @param parent The parent form.
     */
    BaseDialog(JFrame parent) {
        super(parent, true);
        initComponents();
        pack();
    }

    /**
     * Create all the gui components that comprise this form, and setup all
     * action handlers.
     */
    protected abstract void initComponents();

    /**
     *	Closes the dialog
     *
     * @param evt the window event that triggered this call.
     *
     */
    protected void closeDialog(WindowEvent evt) {
        setVisible(false);
        dispose();
    }

    /**
     * Whether this dialog was confirmed or canceled.
     *
     * @return boolena true if the OK button was pressed.
     *
     */
    public boolean isConfirmed() {
        return status_ == CONFIRMED;
    }

    /**
     * Get the name selected for this queue/topic or consumer.
     *
     * @return String The name entered by the user
     */
    public String getName() {
        return name_;
    }

    /**
     * The OK button was pressed. Get the name and confirm its not null.
     * if it is null or empty display an error dialog.
     *
     * if a name was entered, get and store the name and the queue or topic
     * selection, close the dialog and record that OK was pressed.
     *
     */
    protected void confirm() {
        name_ = displayText.getText();

        if (name_ == null || name_.equals("")) {
            JOptionPane.showMessageDialog
                (this, "A name must be suplied", "Create Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            status_ = CONFIRMED;
            setVisible(false);
            dispose();
        }
    }

    /**
     * The cancel button was pressed. Close the GUI, and recored that cancel
     * was pressed.
     *
     */
    protected void cancel() {
        status_ = CANCELED;
        setVisible(false);
        dispose();
    }

} // End BaseDialog
