package sbmlreader2;

import cytoscape.*;
import cytoscape.visual.*;
import cytoscape.visual.calculators.*;
import cytoscape.visual.mappings.*;
import java.awt.Color;

/**
 * VisualStyleFactory.java
 * This class defines the visualstyle in Cytoscape for the SBMLReader plugin.
 *
 * @author W.P.A. Ligtenberg, Eindhoven University of Technology
 */
public class SBMLVisualStyleFactory {

    /**
     */
    public static final String SBMLReader_VS = "SBMLReader Style";

    /**
     */
    public static final String NODE_TYPE_ATT = "sbml type";

    /**
     */
    public static final String EDGE_TYPE_ATT = "interaction";

    /**
     *  DOCUMENT ME!
     *
     * @param network DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static VisualStyle createVisualStyle(CyNetwork network);
}
