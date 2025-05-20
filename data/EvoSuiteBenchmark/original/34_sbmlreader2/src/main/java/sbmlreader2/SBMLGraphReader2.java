package sbmlreader2;

/*
 Copyright (c) 2006, 2007, The Cytoscape Consortium (www.cytoscape.org)

 The Cytoscape Consortium is:
 - Institute for Systems Biology
 - University of California San Diego
 - Memorial Sloan-Kettering Cancer Center
 - Institut Pasteur
 - Agilent Technologies

 This library is free software; you can redistribute it and/or modify it
 under the terms of the GNU Lesser General Public License as published
 by the Free Software Foundation; either version 2.1 of the License, or
 any later version.

 This library is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 documentation provided hereunder is on an "as is" basis, and the
 Institute for Systems Biology and the Whitehead Institute
 have no obligations to provide maintenance, support,
 updates, enhancements or modifications.  In no event shall the
 Institute for Systems Biology and the Whitehead Institute
 be liable to any party for direct, indirect, special,
 incidental or consequential damages, including lost profits, arising
 out of the use of this software and its documentation, even if the
 Institute for Systems Biology and the Whitehead Institute
 have been advised of the possibility of such damage.  See
 the GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
*/


import java.io.IOException;
import java.util.*;

import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import cytoscape.data.Semantics;
import cytoscape.data.readers.AbstractGraphReader;
import cytoscape.data.readers.GraphReader;
import cytoscape.visual.*;

import org.sbml.libsbml.SBMLDocument;
import org.sbml.libsbml.SBMLReader;
import org.sbml.libsbml.Model;

import org.sbml.libsbml.ListOfSpecies;
import org.sbml.libsbml.Species;
import org.sbml.libsbml.ListOfReactions;
import org.sbml.libsbml.Reaction;
import org.sbml.libsbml.ListOfSpeciesReferences;
import org.sbml.libsbml.SpeciesReference;

import org.sbml.libsbml.KineticLaw;
import org.sbml.libsbml.ListOfParameters;
import org.sbml.libsbml.Parameter;

/*
 * Created on September 27, 2005, 9:23 AM
 * This generates the Dialog for the loading of an SBML file and does the processing
 * of the SBML file using the java SBML2 library.
 * @author  W.P.A. Ligtenberg, Eindhoven University of Technology
 * @author  Mike Smoot 
 *  
 * Adapted for libSBML on January 2010
 * @author Matthias Koenig
 * 
 */

/**
 * @author  W.P.A. Ligtenberg, Eindhoven University of Technology
 * @author  Mike Smoot
 * @author  Matthias Koenig
 */
public class SBMLGraphReader2 extends AbstractGraphReader implements GraphReader {
	ArrayList<Integer> nodeIds;
	ArrayList<Integer> edgeIds;
	long level;
	long version;
	
	
	/**
	 * Creates a new SBMLGraphReader object.
	 *
	 * @param filename  DOCUMENT ME!
	 */
	public SBMLGraphReader2(String filename) {
		super(filename);
	}

	
	@SuppressWarnings("serial")
	public class SBMLError extends Exception{}
	
	/**
	 *  DOCUMENT ME!
	 *
	 * @throws IOException DOCUMENT ME!
	 */
	public void read() throws IOException {
		
		CyAttributes nodeAttributes = Cytoscape.getNodeAttributes();
		//TODO: Read the edge attributes (especially stoichiometry)
		CyAttributes edgeAttributes = Cytoscape.getEdgeAttributes();

		nodeIds = new ArrayList<Integer>();
		edgeIds = new ArrayList<Integer>();

		// Read the document
	    SBMLReader reader     = new SBMLReader();
	    SBMLDocument document = reader.readSBML(fileName);
	    if (document.getNumErrors() > 0)
	    {
	      document.printErrors();
	      //TODO: if errors abort the process (show errors in error log of cytoscape)
	      throw new IOException("SBML errors in SBML L" + level + "V" + version);
	    }
	    
	    // Read the SBML information
	    level = document.getLevel();
	    version = document.getVersion();
	    
	    // Get the SBML model
		Model model = document.getModel();

		// Get all the species in the network
		ListOfSpecies speciesList = model.getListOfSpecies();
		Species species;
		for (int i=0; i<speciesList.size(); ++i) {
			species = speciesList.get(i);
			// create the nodes
			CyNode node = Cytoscape.getCyNode(species.getId(), true);
			// create the node attributes
			nodeAttributes.setAttribute(species.getId(), "sbml name", species.getName());
			nodeAttributes.setAttribute(species.getId(), "sbml type", "species");
			nodeAttributes.setAttribute(species.getId(), "sbml id", species.getId());

			// optional attrs, but they'll never be null
			nodeAttributes.setAttribute(species.getId(), "sbml initial concentration",
			                            new Double(species.getInitialConcentration()));
			nodeAttributes.setAttribute(species.getId(), "sbml initial amount",
			                            new Double(species.getInitialAmount()));
			nodeAttributes.setAttribute(species.getId(), "sbml charge",
			                            new Integer(species.getCharge()));
			String comp = species.getCompartment();
			if ( comp != null )
				nodeAttributes.setAttribute(species.getId(), "sbml compartment", comp);

			nodeIds.add(node.getRootGraphIndex());
		}

		// Get all reactions and create a node
		ListOfReactions reactionList = model.getListOfReactions();
		Reaction reaction;
		for (int i=0; i<reactionList.size(); ++i) {
			reaction = reactionList.get(i);
			CyNode node = Cytoscape.getCyNode(reaction.getId(), true);
			nodeAttributes.setAttribute(reaction.getId(), "sbml type", "reaction");
			nodeAttributes.setAttribute(reaction.getId(), "sbml id", reaction.getId());
			
			String rname = reaction.getName();
			if ( rname != null )
				nodeAttributes.setAttribute(reaction.getId(), "sbml name", rname);

			nodeIds.add(node.getRootGraphIndex());

			//Get all products and link them to the reaction node
			//Here the edges are created
			ListOfSpeciesReferences productList = reaction.getListOfProducts();
			SpeciesReference speciesRef;
			for (int k=0; k<productList.size(); ++k) {
				speciesRef = (SpeciesReference) productList.get(k);
				CyNode product = Cytoscape.getCyNode(speciesRef.getSpecies(), false);
				CyEdge edge = Cytoscape.getCyEdge(node, product, Semantics.INTERACTION,
				                                  "reaction-product", true);
				edgeAttributes.setAttribute(edge.getIdentifier(), "stoichiometry", speciesRef.getStoichiometry());
				edgeIds.add(edge.getRootGraphIndex());
			}

			//Get all reactants and link them to the reaction node
			ListOfSpeciesReferences reactantList = reaction.getListOfReactants();
			for (int k=0; k<reactantList.size(); ++k) {
				speciesRef = (SpeciesReference) reactantList.get(k);
				CyNode reactant = Cytoscape.getCyNode(speciesRef.getSpecies(), false);
				CyEdge edge = Cytoscape.getCyEdge(node, reactant, Semantics.INTERACTION,
				                                  "reaction-reactant", true);
				edgeAttributes.setAttribute(edge.getIdentifier(), "stoichiometry", speciesRef.getStoichiometry());
				edgeIds.add(edge.getRootGraphIndex());
			}

			//Get all modifiers and link them to the reaction node
			//TODO: problems with casting
			ListOfSpeciesReferences modifierList = reaction.getListOfModifiers();
			for (int k=0; k<modifierList.size(); ++k) {
				speciesRef = (SpeciesReference) modifierList.get(k);
				CyNode modifier = Cytoscape.getCyNode(speciesRef.getSpecies(), false);
				CyEdge edge = Cytoscape.getCyEdge(modifier, node, Semantics.INTERACTION,
				                                  "reaction-modifier", true);
				edgeAttributes.setAttribute(edge.getIdentifier(), "stoichiometry", speciesRef.getStoichiometry());
				edgeIds.add(edge.getRootGraphIndex());
			}

			
			// Read the kinetic law information
			KineticLaw law = reaction.getKineticLaw();
			if ( law == null )
				continue;
			ListOfParameters parameterList = law.getListOfParameters();

			if ( parameterList == null )
				continue;
			Parameter p;
			for (int k=0; k<parameterList.size(); ++k) {
				p = parameterList.get(k);
				String id = p.getName();
				String units = p.getUnits();
				double value = p.getValue();
				nodeAttributes.setAttribute(reaction.getId(), "kineticLaw-"+id, new Double(value));
				if (units != null)
					nodeAttributes.setAttribute(reaction.getId(), "kineticLaw-"+id+"-units", units);
			}
		}
	}

	/**
	 *  Sets the SBML specific style.
	 *
	 * @param network DOCUMENT ME!
	 */
	public void doPostProcessing(CyNetwork network) {
		// Set SBML specific visual style
		VisualMappingManager manager = Cytoscape.getVisualMappingManager();
		CalculatorCatalog catalog = manager.getCalculatorCatalog();

		VisualStyle vs = catalog.getVisualStyle(SBMLVisualStyleFactory.SBMLReader_VS);

		if (vs == null) {
			vs = SBMLVisualStyleFactory.createVisualStyle(network);
			catalog.addVisualStyle(vs);
		}

		manager.setVisualStyle(vs);
		Cytoscape.getCurrentNetworkView().setVisualStyle(vs.getName());
		Cytoscape.getCurrentNetworkView().applyVizmapper(vs);
	}

	/**
	 *  DOCUMENT ME!
	 *
	 * @return  DOCUMENT ME!
	 */
	public int[] getNodeIndicesArray() {
		int[] nodes = new int[nodeIds.size()];

		for (int i = 0; i < nodes.length; i++)
			nodes[i] = nodeIds.get(i).intValue();

		return nodes;
	}

	/**
	 *  DOCUMENT ME!
	 *
	 * @return  DOCUMENT ME!
	 */
	public int[] getEdgeIndicesArray() {
		int[] edges = new int[edgeIds.size()];

		for (int i = 0; i < edges.length; i++)
			edges[i] = edgeIds.get(i).intValue();

		return edges;
	}
}
