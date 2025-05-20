package fr.pingtimeout.jtail.gui.action;

import fr.pingtimeout.jtail.gui.view.OpenFileDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: vadmin
 * Date: Sep 6, 2010
 * Time: 9:56:00 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CancelAction extends JTailAbstractAction {
    /**
     * Icône associée à l'action.
     */
    private static final ImageIcon CANCEL_ICON = new ImageIcon(CancelAction.class.getResource("x16.png"));

    public CancelAction() {
        super(bundle.getString("action.cancel.label"),
                bundle.getString("action.cancel.tooltip"),
                bundle.getString("action.cancel.mnemonic"),
                bundle.getString("action.cancel.accelerator"),
                CANCEL_ICON);
    }
}
