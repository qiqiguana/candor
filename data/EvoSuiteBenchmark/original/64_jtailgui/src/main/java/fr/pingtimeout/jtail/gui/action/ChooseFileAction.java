package fr.pingtimeout.jtail.gui.action;

import fr.pingtimeout.jtail.gui.model.OpenFileModel;
import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ResourceBundle;

/**
 * Action dédiée à la sélection d'un fichier par l'utilisateur.
 * <p/>
 * Cette action ne s'occupe pas de la sélection d'un type d'index ou de l'indexation du fichier choisi !!
 */
public class ChooseFileAction extends JTailAbstractAction {
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    /**
     * Le FileChooser utilisé
     */
    private JFileChooser fileChooser;

    /**
     * Le modèle utilisé pour la saisie de fichiers
     */
    private final OpenFileModel openFileModel;

    /**
     * Icône associée à l'action.
     */
    private static final ImageIcon CHOOSE_FILE_ICON = new ImageIcon(OpenFileAction.class.getResource("open16.png"));

    public ChooseFileAction(OpenFileModel openFileModel) {
        super(bundle.getString("action.browse.label"),
                bundle.getString("action.browse.tooltip"),
                bundle.getString("action.browse.mnemonic"),
                CHOOSE_FILE_ICON);

        this.openFileModel = openFileModel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JTailLogger.debug("Action 'Open' performed"); //NON-NLS

        // Créer le file chooser s'il n'existe pas
        if (this.fileChooser == null) {
            this.fileChooser = new JFileChooser();

            this.fileChooser.addChoosableFileFilter(new LogFileFilter());
            this.fileChooser.setAcceptAllFileFilterUsed(true);
        }

        //Afficher le FileChooser
        final int returnVal = this.fileChooser.showDialog(null, bundle.getString("action.open.label"));

        //Si l'utilisateur n'a pas validé, sortir
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            JTailLogger.debug("The user did not approve the choice of a file"); //NON-NLS
            return;
        }

        // Extraire le résultat
        final File file = this.fileChooser.getSelectedFile();
        JTailLogger.debug("The user selected {}", file.toString()); //NON-NLS

        this.openFileModel.setFile(file);
    }
}
