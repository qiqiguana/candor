package fr.pingtimeout.jtail.gui.view;

import fr.pingtimeout.jtail.gui.action.CancelAction;
import fr.pingtimeout.jtail.gui.action.ChooseFileAction;
import fr.pingtimeout.jtail.gui.action.IndexFileAction;
import fr.pingtimeout.jtail.gui.action.OpenFileAction;
import fr.pingtimeout.jtail.gui.controller.SelectIndexTypeListener;
import fr.pingtimeout.jtail.gui.model.OpenFileModel;
import fr.pingtimeout.jtail.gui.model.OpenFileModelEvent;
import fr.pingtimeout.jtail.io.index.FileIndex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class OpenFileDialog extends JDialog implements Observer {
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    private final OpenFileModel openFileModel;

    private final JTextField fileTextField;
    private final JButton browseButton;
    private final JButton cancelButton;
    private final JButton openButton;
    private final JComboBox indexType;

    public OpenFileDialog(
            OpenFileModel openFileModel,
            ChooseFileAction chooseFileAction,
            SelectIndexTypeListener selectIndexTypeListener,
            IndexFileAction indexFileAction) {
        super((JFrame) null, bundle.getString("action.open.tooltip"), true);

        this.openFileModel = openFileModel;
        this.openFileModel.addObserver(this);

        this.fileTextField = new JTextField();
        this.fileTextField.setEditable(false);
        this.browseButton = new JButton(chooseFileAction);
        this.openButton = new JButton(indexFileAction);
        this.cancelButton = new JButton(new CancelAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        });
        this.indexType = new JComboBox(
                new Object[]{
                        bundle.getString(FileIndex.Type.MEMORY_BASED.getLabelKey()),
                        bundle.getString(FileIndex.Type.FILE_BASED.getLabelKey())
                }
        );
        this.indexType.addActionListener(selectIndexTypeListener);
        this.indexType.setSelectedIndex(0);

        final GridBagLayout gridBagLayout = new GridBagLayout();
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(5, 10, 5, 10);
        this.setLayout(gridBagLayout);

        // First row
        gridBagConstraints.weightx = 0;
        final JLabel fileNameLabel = new JLabel(bundle.getString("label.file.name"));
        gridBagLayout.setConstraints(fileNameLabel, gridBagConstraints);
        this.add(fileNameLabel);

        gridBagConstraints.weightx = 1;
        gridBagLayout.setConstraints(this.fileTextField, gridBagConstraints);
        this.add(this.fileTextField);

        gridBagConstraints.weightx = 0;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagLayout.setConstraints(this.browseButton, gridBagConstraints);
        this.add(this.browseButton);

        // Second row
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0;
        final JLabel indexTypeLabel = new JLabel(bundle.getString("label.index.type"));
        gridBagLayout.setConstraints(indexTypeLabel, gridBagConstraints);
        this.add(indexTypeLabel);

        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagLayout.setConstraints(this.indexType, gridBagConstraints);
        this.add(this.indexType);

        // Third row
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagLayout.setConstraints(this.openButton, gridBagConstraints);
        this.add(this.openButton);

        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagLayout.setConstraints(this.cancelButton, gridBagConstraints);
        this.add(this.cancelButton);

        this.pack();

        Dimension newPreferredSize = new Dimension(getPreferredSize());
        newPreferredSize.width = 400;
        this.setPreferredSize(newPreferredSize);

        this.pack();
    }

    public void setSelectedFile(File file) {
        this.fileTextField.setText(file.getAbsolutePath());
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable == this.openFileModel) {
            OpenFileModelEvent event = ((OpenFileModelEvent) o);
            switch(event.eventType) {
                case FILE_CHANGED:
                    this.fileTextField.setText(event.file.getAbsolutePath());
                    break;
                case INDEX_TYPE_CHANGED:
                    break;
            }
        }
    }
}
