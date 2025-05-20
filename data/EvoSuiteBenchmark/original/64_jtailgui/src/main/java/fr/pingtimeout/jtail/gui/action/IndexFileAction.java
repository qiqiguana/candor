package fr.pingtimeout.jtail.gui.action;

import fr.pingtimeout.jtail.gui.controller.FileIndexerWorker;
import fr.pingtimeout.jtail.gui.message.ExceptionHandler;
import fr.pingtimeout.jtail.gui.message.InformationHandler;
import fr.pingtimeout.jtail.gui.message.MessageHandler;
import fr.pingtimeout.jtail.gui.message.UIMessage;
import fr.pingtimeout.jtail.gui.model.JTailMainModel;
import fr.pingtimeout.jtail.gui.model.JTailModel;
import fr.pingtimeout.jtail.gui.model.OpenFileModel;
import fr.pingtimeout.jtail.gui.view.OpenFileDialog;
import fr.pingtimeout.jtail.io.FileIndexer;
import fr.pingtimeout.jtail.io.LineReader;
import fr.pingtimeout.jtail.io.index.FileIndex;
import fr.pingtimeout.jtail.io.index.RamFileIndex;
import fr.pingtimeout.jtail.io.index.RomFileIndex;
import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: vadmin
 * Date: Sep 18, 2010
 * Time: 1:49:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexFileAction extends JTailAbstractAction implements Observer {
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    /**
     * Icône associée à l'action.
     */
    private static final ImageIcon OPEN_ICON = new ImageIcon(OpenFileAction.class.getResource("add16.png"));

    /**
     * Le modèle de l'application
     */
    private final JTailMainModel jTailMainModel;

    /**
     * Le modèle associé à l'ouverture de fichier.
     */
    private final OpenFileModel openFileModel;


    public IndexFileAction(JTailMainModel jTailMainModel, OpenFileModel openFileModel) {
        super(bundle.getString("action.open.label"),
                bundle.getString("action.open.tooltip"),
                bundle.getString("action.open.mnemonic"),
                bundle.getString("action.open.accelerator"),
                OPEN_ICON);
        this.jTailMainModel = jTailMainModel;
        this.openFileModel = openFileModel;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        JTailLogger.debug("File : {}", openFileModel.getFile()); //NON-NLS
        JTailLogger.debug("Index type : {}", openFileModel.getIndexType()); //NON-NLS

        // Hide the dialog
        Component dialog = (Component)actionEvent.getSource();
        while(!(dialog instanceof JDialog)) {
            dialog = dialog.getParent();
        }
        dialog.setVisible(false);

        if(openFileModel.getFile() == null) {
            InformationHandler.handle(true, UIMessage.INFO_NO_FILE_SELECTED);
            return;
        }

        Class<? extends FileIndex> indexerClass = null;
        switch (openFileModel.getIndexType()) {
            case MEMORY_BASED:
                indexerClass = RamFileIndex.class;
                break;
            case FILE_BASED:
                indexerClass = RomFileIndex.class;
                break;
        }

        // Préparer l'indexation du fichier
        final FileIndexer fileIndexer;
        final FileIndexerWorker fileIndexerWorker;

        try {
            fileIndexer = new FileIndexer(openFileModel.getFile(), indexerClass);
            fileIndexer.addObserver(this);
        } catch (FileNotFoundException e) {
            ExceptionHandler.handle(e, UIMessage.ERROR_FILE_NOT_FOUND, openFileModel.getFile().getName());
            return;
        }

        fileIndexerWorker = new FileIndexerWorker(fileIndexer);

        // Démarrer l'indexation
        fileIndexerWorker.execute();
    }

    @Override
    public void update(Observable o, Object arg) {
        JTailLogger.debug("Notification received : {} emitted by {}", //NON-NLS
                arg, o);

        // Lorsque l'indexation est terminée, créer le JTailModel et l'ajouter dans l'application
        if ((Integer) arg == 100) {
            final FileIndexer fileIndexer = (FileIndexer) o;
            final File file = fileIndexer.getFile();
            try {
                final LineReader lineReader = new LineReader(file, fileIndexer.getIndex());
                final JTailModel model = new JTailModel(file, lineReader);
                this.jTailMainModel.add(model);
            } catch (FileNotFoundException e) {
                ExceptionHandler.handle(e, UIMessage.ERROR_FILE_NOT_FOUND, file.getName());
            } catch (Exception e) {
                ExceptionHandler.handle(e, UIMessage.ERROR_GENERIC_MESSAGE);
            }
        }
    }
}
