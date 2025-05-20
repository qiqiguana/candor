package de.paragon.explorer.model;

import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.figure.ExplorerFigureBuilder;
import de.paragon.explorer.figure.ListBoxFigure;
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.gui.ExplorerDrawingPanel;
import de.paragon.explorer.util.ExplorerManager;

public final class ExplorerModelBuilder {

    public ExplorerModel createNewExplorerModelWithFigure() {
        ExplorerModel explModl;
        ExplorerFigure explFig;
        explModl = new ExplorerModel();
        explFig = this.getExplorerFigureBuilder().createNewExplorerFigure();
        this.connect(explModl, explFig);
        return explModl;
    }
}
