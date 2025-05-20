package de.paragon.explorer.model;

import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.figure.ExplorerFigureBuilder;
import de.paragon.explorer.figure.ListBoxFigure;
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.gui.ExplorerDrawingPanel;
import de.paragon.explorer.util.ExplorerManager;

public class ExplorerFigureBuilder {

    public ExplorerFigure createNewExplorerFigure();
}

public final class ExplorerModelBuilder {

    /**
     * Kommentar: Diese Methode erzeugt ein neues ExplorerModel, eine neue
     * ExplorerFigure und verknuepft diese beiden.
     */
    public ExplorerModel createNewExplorerModelWithFigure() {
        ExplorerModel explModl;
        ExplorerFigure explFig;
        explModl = new ExplorerModel();
        explFig = this.getExplorerFigureBuilder().createNewExplorerFigure();
        this.connect(explModl, explFig);
        return explModl;
    }

    private ExplorerFigureBuilder getExplorerFigureBuilder();

    /**
     * Kommentar: Diese Methode geht davon aus, dass lediglich die fertig
     * erstellte ListBoxFigure noch innerhalb der Explorer- Figure positioniert
     * werden muss.
     */
    private void connect(ExplorerModel explModl, ExplorerFigure explFig);
}
