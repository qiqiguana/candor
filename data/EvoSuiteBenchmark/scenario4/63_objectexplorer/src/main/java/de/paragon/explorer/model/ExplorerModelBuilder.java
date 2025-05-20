package de.paragon.explorer.model;

import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.figure.ExplorerFigureBuilder;
import de.paragon.explorer.figure.ListBoxFigure;
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.gui.ExplorerDrawingPanel;
import de.paragon.explorer.util.ExplorerManager;

public final class ExplorerModelBuilder {

    private static ExplorerModelBuilder singleton;

    public static ExplorerModelBuilder getInstance();

    private static ExplorerModelBuilder getSingleton();

    private static void setSingleton(ExplorerModelBuilder builder);

    private ExplorerModelBuilder() {
    }

    /**
     * Kommentar: Als erstes wird ein neues ExplorerModel erzeugt. Danach wird
     * eine ExplorerFigure erzeugt. Diese wird dann mit dem ExplorerModel
     * verknuepft. Generell gilt: Der Builder, der eine Figur oder ein Model
     * frisch erzeugt hat, ist fuer die Verknuepfung der unmittelbar zuvor
     * erzeugten Figur bzw. dem unmittelbar zuvor erzeugten Model zustaendig.
     * Nach der Verknuepfung von ExplorerModel und ExplorerFigure wird ein neues
     * ObjectModel erzeugt. Anschliessend werden ObjectModel und ExplorerModel
     * verknuepft. Dann wird eine neue ListBoxFigure erzeugt. Diese wird dann
     * mit dem ObjectModel verknuepft. Schliesslich schliesst sich der Kreis mit
     * der Verknuepfung von ListBoxFigure und Explorer-Figure. Diese wird
     * vorgenommen von dem ExplorerFigureBuilder. Anschliessend wird das
     * ObjectModel weiter ausgebaut. Eine Beschreibung hierzu siehe im
     * ObjectModelBuilder in der Methode buildObjectModel(ObjectModel objModl,
     * Object object)! Ganz zum Schluss wird die fertige ListBoxFigure in der
     * ExplorerFigure plaziert, das heisst, ihre Position innerhalb der
     * ExplorerFigure wird festgelegt. Danach wird sie gezeichnet und ein neuer
     * EventConverter wird erzeugt, bei dem die ExplorerFigure eingetragen wird.
     */
    public ExplorerDrawingPanel add1stModel(Object object);

    /**
     * Kommentar: Als erstes wird ein neues ExplorerModel erzeugt. Danach wird
     * eine ExplorerFigure erzeugt. Diese wird dann mit dem ExplorerModel
     * verknuepft. Generell gilt: Der Builder, der eine Figur oder ein Model
     * frisch erzeugt hat, ist fuer die Verknuepfung der unmittelbar zuvor
     * erzeugten Figur bzw. dem unmittelbar zuvor erzeugten Model zustaendig.
     * Nach der Verknuepfung von ExplorerModel und ExplorerFigure wird ein neues
     * ObjectModel erzeugt. Anschliessend werden ObjectModel und ExplorerModel
     * verknuepft. Dann wird eine neue ListBoxFigure erzeugt. Diese wird dann
     * mit dem ObjectModel verknuepft. Schliesslich schliesst sich der Kreis mit
     * der Verknuepfung von ListBoxFigure und Explorer-Figure. Diese wird
     * vorgenommen von dem ExplorerFigureBuilder. Anschliessend wird das
     * ObjectModel weiter ausgebaut. Eine Be- schreibung hierzu siehe im
     * ObjectModelBuilder in der Methode buildObjectModel(ObjectModel objModl,
     * Object object)! Ganz zum Schluss wird die fertige ListBoxFigure in der
     * ExplorerFigure plaziert, das heisst, ihre Position innerhalb der
     * ExplorerFigure wird festgelegt. Danach wird sie gezeichnet und ein neuer
     * EventConverter wird erzeugt, bei dem die ExplorerFigure eingetragen wird.
     */
    public void addModel(ExplorerModel explModl, Object object);

    private void addNewConnectedObjectModelFor(TextBoxFigure tbf);

    /**
     * Kommentar: Diese Methode geht davon aus, dass lediglich die fertig
     * erstellte ListBoxFigure noch innerhalb der Explorer- Figure positioniert
     * werden muss.
     */
    private void connect(ExplorerModel explModl, ExplorerFigure explFig);

    private de.paragon.explorer.event.ExplorerFrameEventConverter createNewEventConverter(ExplorerFigure explFig);

    /**
     * Kommentar: Diese Methode erzeugt ein neues ExplorerModel, eine neue
     * ExplorerFigure und verknuepft diese beiden.
     */
    public ExplorerModel createNewExplorerModelWithFigure();

    private ObjectModel createNewObjectModel(ExplorerModel explModl);

    private ObjectModel createNewObjectModel(TextBoxFigure tbf);

    private de.paragon.explorer.util.ConnectionBuilder getConnectionBuilder();

    private ExplorerFigureBuilder getExplorerFigureBuilder();

    public ExplorerManager getExplorerManager();

    private ObjectModelBuilder getObjectModelBuilder();

    /**
     * Kommentar: Diese Methode geht davon aus, dass existieren: 1.
     * ExplorerFigure verknuepft mit einem ExplorerModel; 2. Mindestens eine
     * fertige ListBoxFigure. Diese Methode macht: Sie ist dann aufzurufen, wenn
     * weder eine zu referenzierende ListBoxFigure noch die dazugehoerige
     * ConnectionFigure da ist. Sie erzeugt ein neues ObjectModel incl.
     * ListBoxFigure; baut dieses ObjectModel aus und fuegt sie abhaengig von
     * dem AttributeModel, das es referenziert, in die ExplorerFigure ein.
     * Anschliessend fuegt sie die verbindende ConnectionFigure ein. "add" steht
     * hierbei fuer create, build und draw.
     */
    public void handleAttributeModel(TextBoxFigure tbf);

    /**
     * Kommentar: Diese Methode geht davon aus, dass noch alle Verknuepfungen
     * existieren, also noch nicht geloescht worden ist. Zunaechst wird das
     * Objektmodell mit der zugehoerigen ListBoxFigure von der ExplorerFigure
     * entfernt. Ein Objektmodell zu loeschen bedeutet, es aus der Liste
     * objectModels des ExplorerModels zu entfernen. Dann geschieht Analoges mit
     * den Connections, die auf das Modell zeigen oder vom Modell wegzeigen.
     */
    public void removeFromExplorer(ObjectModel objModl);
}
