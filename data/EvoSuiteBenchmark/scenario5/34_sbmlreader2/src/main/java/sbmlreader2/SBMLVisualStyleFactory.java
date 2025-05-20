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
     *  DOCUMENT ME!
     *
     * @param network DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public static VisualStyle createVisualStyle(CyNetwork network) {
        VisualMappingManager vmManager = Cytoscape.getVisualMappingManager();
        NodeAppearanceCalculator nodeAppCalc = new NodeAppearanceCalculator();
        EdgeAppearanceCalculator edgeAppCalc = new EdgeAppearanceCalculator();
        CalculatorCatalog calculatorCatalog = vmManager.getCalculatorCatalog();
        // ------------------------------ Set node shapes ---------------------------//
        DiscreteMapping disMapping = new DiscreteMapping(NodeShape.RECT, ObjectMapping.NODE_MAPPING);
        disMapping.setControllingAttributeName(NODE_TYPE_ATT, network, false);
        disMapping.putMapValue("species", NodeShape.DIAMOND);
        disMapping.putMapValue("reaction", NodeShape.ELLIPSE);
        Calculator shapeCalculator = new BasicCalculator("SBMLReader Shape Calculator", disMapping, VisualPropertyType.NODE_SHAPE);
        nodeAppCalc.setCalculator(shapeCalculator);
        // ------------------------------ Set the label ------------------------------//
        // Display the value for geneName as a label
        String cName = "sbml name";
        Calculator nlc = calculatorCatalog.getCalculator(VisualPropertyType.NODE_LABEL, cName);
        if (nlc == null) {
            PassThroughMapping m = new PassThroughMapping(new String(), cName);
            nlc = new BasicCalculator(cName, m, VisualPropertyType.NODE_LABEL);
        }
        nodeAppCalc.setCalculator(nlc);
        //--------------------- Set the size of the nodes --------------------------//
        //Discrete mapping on nodeType
        Double speciesNodeSize = new Double(30);
        Double reactionNodeSize = new Double(30);
        DiscreteMapping sizeMapping = new DiscreteMapping(reactionNodeSize, ObjectMapping.NODE_MAPPING);
        sizeMapping.setControllingAttributeName(NODE_TYPE_ATT, network, false);
        sizeMapping.putMapValue("species", speciesNodeSize);
        sizeMapping.putMapValue("reaction", reactionNodeSize);
        Calculator sizeCalculator = new BasicCalculator("SBMLReader Size Calculator", sizeMapping, VisualPropertyType.NODE_SIZE);
        nodeAppCalc.setCalculator(sizeCalculator);
        nodeAppCalc.setNodeSizeLocked(true);
        // ------------------------------ Set edge arrow shape ---------------------------//
        DiscreteMapping arrowMapping = new DiscreteMapping(ArrowShape.DELTA, ObjectMapping.NODE_MAPPING);
        arrowMapping.setControllingAttributeName(EDGE_TYPE_ATT, network, false);
        arrowMapping.putMapValue("reaction-product", ArrowShape.ARROW);
        arrowMapping.putMapValue("reaction-reactant", ArrowShape.NONE);
        arrowMapping.putMapValue("reaction-modifier", ArrowShape.CIRCLE);
        Calculator edgeArrowCalculator = new BasicCalculator("SBMLReader Edge Arrow Calculator", arrowMapping, VisualPropertyType.EDGE_SRCARROW_SHAPE);
        edgeAppCalc.setCalculator(edgeArrowCalculator);
        // ------------------------------ Set edge colour ---------------------------//
        DiscreteMapping edgeColorMapping = new DiscreteMapping(Color.BLACK, ObjectMapping.NODE_MAPPING);
        edgeColorMapping.setControllingAttributeName(EDGE_TYPE_ATT, network, false);
        edgeColorMapping.putMapValue("reaction-product", Color.GREEN);
        edgeColorMapping.putMapValue("reaction-reactant", Color.RED);
        edgeColorMapping.putMapValue("reaction-modifier", Color.BLACK);
        Calculator edgeColorCalculator = new BasicCalculator("SBMLReader Edge Color Calculator", edgeColorMapping, VisualPropertyType.EDGE_COLOR);
        edgeAppCalc.setCalculator(edgeColorCalculator);
        //------------------------- Create a visual style -------------------------------//
        GlobalAppearanceCalculator gac = vmManager.getVisualStyle().getGlobalAppearanceCalculator();
        VisualStyle visualStyle = new VisualStyle(SBMLReader_VS, nodeAppCalc, edgeAppCalc, gac);
        return visualStyle;
    }
}
