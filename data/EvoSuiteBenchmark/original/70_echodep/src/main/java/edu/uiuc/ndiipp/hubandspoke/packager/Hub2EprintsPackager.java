/**
 * Hub2EprintsPackager.java
 * 
 * $Revision: 846 $
 * 
 * $Date: 2009-04-22 18:45:17 +0100 (Wed, 22 Apr 2009) $
 * 
 * Copyright (c) 2009, University Library, University of Illinois at 
 * Urbana-Champaign. All rights reserved. 
 * 
 * Developed by: The Hub and Spoke Project Group 
 *               University of Illinois Urbana-Champaign Library
 *               http://dli.grainger.uiuc.edu/echodep/hands/ 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to 
 * the following conditions: 
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimers. 
 * 
 * - Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimers in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * - Neither the names of The Hub and Spoke Project Group, University of 
 * Illinois Urbana-Champaign Library, nor the names of its contributors may 
 * be used to endorse or promote products derived from this Software 
 * without specific prior written permission. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE. 
 */

package edu.uiuc.ndiipp.hubandspoke.packager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.dom.DOMResult;
// import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

// for xpath additions
// import javax.xml.xpath.* ;
import javax.xml.xpath.XPath ;
import javax.xml.xpath.XPathFactory ;
import javax.xml.xpath.XPathConstants ;
import javax.xml.xpath.XPathExpressionException ;
import javax.xml.namespace.NamespaceContext ;
import java.util.Iterator ;

// uncomment if get prefixes for xpath from xml document 
// import com.sun.org.apache.xml.internal.utils.PrefixResolver ;
// import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault ;

import org.apache.log4j.Logger;
// import org.jdom.xpath.XPath;
import org.w3c.dom.Document ;
import org.w3c.dom.Node ;
import org.w3c.dom.Element ;
import org.w3c.dom.DocumentFragment ;

// import org.w3c.dom.NodeList ;
import org.xml.sax.SAXException;

import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSAppender;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PREMISEventType;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PREMISIdentifierType;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PREMISLinkingAgentRole;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PackageType;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSZipUtils;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsDivType.Mptr;
import gov.loc.mods.ModsModsDocument;
import gov.loc.premis.PremisAgentDocument;
import gov.loc.premis.PremisEventDocument;

/**
 * <p>Class responsible for transforming a hub package into a package 
 * ingestable by Eprints. Expects to be fed a METS file conformant to the 
 * EchoDep profile (http://www.loc.gov/standards/mets/profiles/00000015.xml)</p>
 *  
 * <p>Any files not referenced in the METS file will not be 
 *  packaged for Eprints ingestion.</p>
 * 
 * @author Robert Manaster
 * @version $LastChangedRevision: 846 $
 * @see <a href="http://www.loc.gov/standards/mets/profiles/00000015.xml">ECHODep 
 *   Generic METS Profile for Preservation and Digital Repository Interoperability</a>
 *
 */
public class Hub2EprintsPackager extends FromHubPackager {
	
	//TODO do something with this!
	private static Logger log = Logger.getLogger(Hub2EprintsPackager.class.getName());
	
	/**
	 * Command line interface to transform a hub package into a package 
	 * ingestable by Eprints
	 * 
	 * USAGE: Hub2EprintsPackager /path/to/hub/package /output/dir eprintsAssignedHandle 
	 *   
	 * @param args - path to the hub package, path to the output directory, Eprints handle
	 */
	public static void main(String[] args) {
		Hub2EprintsPackager h2e = new Hub2EprintsPackager();
		if (args.length != 3 ){
			System.out.println("Please supply the correct number of arguments.");
			usage();
			return;
		}
		
		try{
			if (h2e.createRepositoryPackage(args[0], args[1], args[2]) != null){
				System.out.println("Packaging complete.");
			}else{
				System.out.println("Errors occurred!");
			}
		}catch(Exception e){
			System.out.println("testing... Exception caught") ;
			System.out.println("ERROR: "+ e.getMessage());
			e.printStackTrace();
			usage();
			return;
		}
	}
	
	/**
	 * Carry out the transformation on the given package. If output directory is not
	 * given it creates the package in the same directory as the METS file.
	 * 
	 * @param pathtomets - location of the METS file
	 * @param id - handle retrieved from the POST method of LRCRUD
	 * @throws HaSPackagerException 
	 * @throws HaSMETSProfileException 
	 */
	public File createRepositoryPackage(String pathtomets, String id) 
	throws HaSMETSProfileException, PackagerException {
		return createRepositoryPackage(pathtomets, null, id);
	}
	
	/**
	 * Carry out the transformation on the given package. If passed a string,
	 * instantiates a MaSMETSProfile object and validates it before continuing.
	 * 
	 * @param pathtomets - location of the METS file
	 * @param id - handle retrieved from the POST method of LRCRUD
	 * @throws HaSMETSProfileException 
	 * @throws PackagerException 
	 */
	public File createRepositoryPackage(String pathtomets, String output_path, String id) 
	throws HaSMETSProfileException, PackagerException {
		System.out.println("testing... About to validate") ;
		HaSMasterMETSProfile mastermets = HaSMasterMETSProfileFactory
					.newInstance(new File(pathtomets));
		HaSMETSProfile hs = mastermets.getMostRecentEchoDepMETS();
		HaSMETSAppender app  = new HaSMETSAppender("ProfileValidation");
		
//		Skip all this		
//		try {
//			 MetsMdSecType primRep = hs.getPrimaryRepresentation();
//		} catch (HaSMETSProfileException e) {
//		 
//			/* create a new primary rep */
//			MetsMdSecType primRep = hs.addNewPrimaryRepresentation(PREMISIdentifierType.HANDLE, id);
//
//			/* SEE use of getPrimaryRepresentation in HaSMETSProfile.java... need below? 
//			/* 
//			PremisObjectDocument primRepObj = (PremisObjectDocument) hs.getMDSecXmlObject(primRep);
//		    PremisObjectDocument.Object.ObjectIdentifier primID = primRepObj.getObject().addNewObjectIdentifier();
//		    primID.setObjectIdentifierValue(id);
//		    primID.setObjectIdentifierType(PREMISIdentifierType.HANDLE.toString());
//		    hs.setMDSecXmlObject(primRep, primRepObj);
//            */ 
//			
//		    hs.getMetsDocument().getMets().setOBJID(id);
//
//		    /*  
//  		    // set the modified date to now.. .cannot here since not visible
//		    hs.setLastModifiedToCurrent();
//		    */
//		}		
		
		if (hs.validateProfile(PackageType.SIP, app)){
			return createRepositoryPackage(mastermets, output_path, id);
		}else{
			String events = app.getAllEventsAsXmlText();
			//TODO Write this to a log file and not the system
			System.out.println(events);
			log.error(events);
			return null;
		}
	}
	
	/**
	 * Carry out the transformation on the given package. If passed a string,
	 * instantiates a MaSMETSProfile object and validates it before continuing.
	 * 
	 * @param hubpackage - HaSMETSProfile object
	 * @param id - handle retrieved from the POST method of LRCRUD
	 * @throws HaSMETSProfileException 
	 * @throws PackagerException 
	 */
	public File createRepositoryPackage(HaSMasterMETSProfile mastermets,
            String id) throws HaSMETSProfileException, PackagerException {
		return createRepositoryPackage(mastermets, null, id);
	}
	
	/**
	 * Carry out the transformation on the given package
	 * 
	 * @param hubpackage - HaSMETSProfile object
	 * @param output_dir - Place to put the zip
	 * @param id - handle retrieved from the POST method of LRCRUD
	 * @throws HaSMETSProfileException 
	 * @throws PackagerException 
	 */
	public File createRepositoryPackage(HaSMasterMETSProfile mastermets, String output_dir, String id) 
	throws PackagerException, HaSMETSProfileException{
		
		HaSMETSProfile hubpackage = mastermets.getMostRecentEchoDepMETS();
		
		System.out.println("testing... In processing") ;
		String hubbase = hubpackage.getBaseURI().getPath();
		String hubpackageName = hubbase.substring(hubbase.lastIndexOf("/")+1) ;
		hubbase = hubbase.substring(0, hubbase.lastIndexOf("/"));
		

		System.out.println("testing... Begin: Set primary Id on... hubbase="+hubbase) ;
		System.out.println("id="+id+" handle="+PREMISIdentifierType.HANDLE) ;
		hubpackage.setNewPrimaryID(id, PREMISIdentifierType.HANDLE);
		System.out.println("testing... Done: Set primary Id") ;
		
//		System.setProperty("javax.xml.transform.TransformerFactory",
//			"net.sf.saxon.TransformerFactoryImpl");
		File dest = null;
		ZipOutputStream zip;

		try {
			// System.out.println("output_directory: " + output_dir) ;
			// System.out.println("Name of file should be: " + formatHandleForFileName(id)+".zip") ;
			if (output_dir!=null){ //put it where specified
				dest = new File(output_dir+File.separatorChar+formatHandleForFileName(id)+".zip");
				zip = new ZipOutputStream(new FileOutputStream(dest));
			}else{ //put the zip in the same directory as the HS METS file
				dest = new File(hubbase+File.separatorChar+formatHandleForFileName(id)+".zip");
				zip = new ZipOutputStream(new FileOutputStream(dest));
			}
		} catch (FileNotFoundException e) {
			log.error("Output directory not found.");
			throw new PackagerException("Output directory not found.", e);
		}
		addFilesToZip(mastermets, zip);
		
		//FIXME pulling the location of the HaS installation from the environmental variable for now
		//FIXME may want to use config files in the future
		File xslt = new File(System.getenv("HS_HOME")+File.separatorChar+
				"xslt"+File.separatorChar+"mods2eprints.xsl");
	    		
		/* For testing... */
		/* 
		File xslt = new File(output_dir+File.separatorChar+"mods2eprints.xsl") ;
        */

		StreamSource xsltsource = new StreamSource(xslt);
		//get the MODS to transform into Eprints 
		MetsMdSecType dmd = hubpackage.getPrimaryDmdSec();
		ModsModsDocument mods = (ModsModsDocument)hubpackage.getMDSecXmlObject(dmd);
		DOMSource xmlSource = new DOMSource(mods.getDomNode());


//		try {
			try {
				TransformerFactory tfact = TransformerFactory.newInstance();
				Transformer tform = tfact.newTransformer(xsltsource);
				
				// we will write the transform to a DOM Tree because JAXP does not want to allow
				// multiple transforms on a single source
				// Write a Result to a DOM tree (inserted into the supplied Document)

				DOMResult res = new DOMResult();

				// for testing... output the transformation result to screen
				// Result res = new StreamResult(System.out);
				// System.out.println("testing... Before transformation") ;

				// cannot do transformation via saxon8... it won't handle it
//				String prevFactory = System.getProperty("javax.xml.parsers.DocumentBuilderFactory") ;
//				System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
//				"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
								
				// Do the transformation
				tform.transform(xmlSource, res);
				
				// for testing...
				// System.out.println("testing... After transformation") ;
				Node resultNode = res.getNode();

			    // Map prefixes (in xpath expression) to URIs explicitly
			    NamespaceContext ctx = new NamespaceContext() {
			    	public String getNamespaceURI(String prefix) {
			    		String uri;
			    			if (prefix.equals("ep"))
			    				uri = "http://eprints.org/ep2/data/2.0" ;
			    			else
			    				uri = null;
			                return uri;
			        }
			    	
			    	// Dummy implementation - not used!
			    	public Iterator getPrefixes(String val) {
			    		return null;
			    	}
			
			    	// Dummy implemenation - not used!
			    	public String getPrefix(String uri) {
			    		return null;
			    	}
			    };

				// Alternative way to xpath name-spacing (does not work):
				//
				//  Can Xalan's tool work on existing node and with saxon8?...
				//  It looks as if Saxon8 cannot handle empty (e.g., default)
				//  namespace in xpath expression
				//
				//   Caveat: maps prefixes in xml document and since prefixes
				//           in xpath expression must match the xml document, if
				//           xml document changes, prefixes in xpath must change.
				
				// Map prefixes in xml document to URIs, using Xalan's
			    // document-extracting mapping tool and wrapping
			    // it in a nice JAXP shell
				//   NOTE: Prefixes in xml Document must all be defined on 
				//         in root node since it will not look beyond root.
				
				// final PrefixResolver resolver =
			    //           new PrefixResolverDefault(resultNode);

				
				// NamespaceContext ctx = new NamespaceContext() {
			    //      public String getNamespaceURI(String prefix) {
			    //         return resolver.getNamespaceForPrefix(prefix);
			    //     }
			         
			         // Dummy implementation - not used!
			    //     public Iterator getPrefixes(String val) {
			    //    	 return null;
			    //     }
			         
			         // Dummy implementation - not used!
			    //     public String getPrefix(String uri) {
			    //    	 return null;
			    //     }
			    //};
			    
				// use of xpath
				XPath xpath = XPathFactory.newInstance().newXPath() ;
				xpath.setNamespaceContext(ctx) ; 
				
				// load EPrints Id...
			    String expression = "//ep:eprintid" ;
			    Node epNode = (Node) xpath.evaluate(expression, resultNode, XPathConstants.NODE);
			    // EPrints Id from LRCrud Location Header
			    String [] id_split = null;
			    id_split = id.split("/") ;
			    String epId = id_split[(id_split.length - 1)] ;
			    epNode.setTextContent(epId);

			    // load document info...
			    // get node from xml DOM tree
			    expression = "//ep:documents" ;
			    epNode = (Node) xpath.evaluate(expression, resultNode, XPathConstants.NODE);

			    // get document information
			    MetsFileType ft[] = hubpackage.getAllFiles();
				for (int i = 0; i < ft.length; i++) {
					// get the filename
					File tempFileObject = new File(ft[i].getFLocatArray(0).getHref());
					String fileName = tempFileObject.getName() ;
					
					Long fileSize = ft[i].getSIZE() ;
				    String fileFormat = ft[i].getMIMETYPE() ; 

			        // create a fragment node for this document
				    try{
				        //Create instance of DocumentBuilderFactory
				        DocumentBuilderFactory myFactory = DocumentBuilderFactory.newInstance();
				        //Get the DocumentBuilder
				        DocumentBuilder docBuilder = myFactory.newDocumentBuilder();
				        //Create blank DOM Document
				        Document myDoc = docBuilder.newDocument() ;
				        // NOTE: either need to turn off namespace or to add
				        //       http://eprints.org/ep2/data/2.0
				        
			            //create the root element
				        Element root = myDoc.createElement("document") ;
				        //add it to DOM tree
				        myDoc.appendChild(root);
				        
				        // position node
				        Element temp = myDoc.createElement("position") ;
				        String str = String.valueOf(i + 1);
				        temp.setTextContent(str) ;
				        root.appendChild( temp );
				        
				        // format node
				        temp = myDoc.createElement("format") ;
				        temp.setTextContent(fileFormat) ;
				        root.appendChild( temp );
				        
				        //language node
				        temp = myDoc.createElement("language") ;
				        temp.setTextContent("en") ;
				        root.appendChild( temp );
				
				        //security node
				        temp = myDoc.createElement("security") ;
				        temp.setTextContent("public") ;
				        root.appendChild( temp );

				        // Create and add filename node
				        Element file = myDoc.createElement("file") ;
				        temp = myDoc.createElement("filename") ;
				        temp.setTextContent(fileName) ;
				        file.appendChild( temp ) ;
				        temp = myDoc.createElement("filesize") ;
				        str = String.valueOf(fileSize);
				        temp.setTextContent(str) ;
				        file.appendChild( temp ) ;
				        temp = myDoc.createElement("data") ;
				        temp.setAttribute("href", fileName) ;
				        file.appendChild( temp ) ;
				        
				        
				        Element files = myDoc.createElement("files") ;
				        files.appendChild( file );
				        
				        root.appendChild( files );
				        
				        
						// add this node as fragment child to epNode
				        // First, Create a documentFragment
				        DocumentFragment docFrag = myDoc.createDocumentFragment();
				        docFrag.appendChild(root);
				        
				        Node oneDocNode = epNode.getOwnerDocument().importNode(docFrag, true);
						epNode.appendChild(oneDocNode);
				        				        
				    	}catch(Exception e){
				          System.out.println(e.getMessage());
				        }
				}

				// load H&S Master Mets document information
			    try{
			        //Create instance of DocumentBuilderFactory
			        DocumentBuilderFactory myFactory = DocumentBuilderFactory.newInstance();
			        //Get the DocumentBuilder
			        DocumentBuilder docBuilder = myFactory.newDocumentBuilder();
			        //Create blank DOM Document
			        Document myDoc = docBuilder.newDocument() ;
			        // NOTE: either need to turn off namespace or to add
			        //       http://eprints.org/ep2/data/2.0
			        
		            //create the root element
			        Element root = myDoc.createElement("document") ;
			        //add it to DOM tree
			        myDoc.appendChild(root);
			        
			        // position node
			        Element temp = myDoc.createElement("position") ;
			        String str = String.valueOf(ft.length + 1);
			        temp.setTextContent(str) ;
			        root.appendChild( temp );
			        
			        // format node
			        temp = myDoc.createElement("format") ;
			        temp.setTextContent("other") ;
			        root.appendChild( temp );
			        
			        // format description
			        temp = myDoc.createElement("formatdesc") ;
			        temp.setTextContent("Hub-n-spoke MASTER METS file") ;
			        root.appendChild( temp );
			        
			        //language node
			        temp = myDoc.createElement("language") ;
			        temp.setTextContent("en") ;
			        root.appendChild( temp );
			
			        //security node
			        temp = myDoc.createElement("security") ;
			        temp.setTextContent("public") ;
			        root.appendChild( temp );

			        // Create and add filename node
			        Element file = myDoc.createElement("file") ;
			        temp = myDoc.createElement("filename") ;
			        temp.setTextContent(HaSConstants.MASTER_METS_FILE_NAME) ;
			        file.appendChild( temp ) ;
   		            temp = myDoc.createElement("filesize") ;
			        // Get file SIZE
			        File ourMets = new File(mastermets.getBaseURI()) ;
				    // assumes file is in the present directory...
				    long metsFileSize = ourMets.length() ;
				    String metsFileSize_str = String.valueOf(metsFileSize);
		            temp.setTextContent(metsFileSize_str) ;
		            file.appendChild( temp ) ;
			        temp = myDoc.createElement("data") ;
			        temp.setAttribute("href", HaSConstants.MASTER_METS_FILE_NAME) ;
			        file.appendChild( temp ) ;
			        			        
			        Element files = myDoc.createElement("files") ;
			        files.appendChild( file );
			        
			        root.appendChild( files );
			        			        
					// add this node as fragment child to epNode
			        // First, Create a documentFragment
			        DocumentFragment docFrag = myDoc.createDocumentFragment();
			        docFrag.appendChild(root);
			        
			        Node oneDocNode = epNode.getOwnerDocument().importNode(docFrag, true);
					epNode.appendChild(oneDocNode);
			        
			        }catch(Exception e){
			          System.out.println(e.getMessage());
			        }
			        
				// Lastly, but not leastly, load H&S Mets document information
			    try{
			        //Create instance of DocumentBuilderFactory
			        DocumentBuilderFactory myFactory = DocumentBuilderFactory.newInstance();
			        //Get the DocumentBuilder
			        DocumentBuilder docBuilder = myFactory.newDocumentBuilder();
			        //Create blank DOM Document
			        Document myDoc = docBuilder.newDocument() ;
			        // NOTE: either need to turn off namespace or to add
			        //       http://eprints.org/ep2/data/2.0
			        
		            //create the root element
			        Element root = myDoc.createElement("document") ;
			        //add it to DOM tree
			        myDoc.appendChild(root);
			        
			        // position node
			        Element temp = myDoc.createElement("position") ;
			        String str = String.valueOf(ft.length + 1);
			        temp.setTextContent(str) ;
			        root.appendChild( temp );
			        
			        // format node
			        temp = myDoc.createElement("format") ;
			        temp.setTextContent("other") ;
			        root.appendChild( temp );
			        
			        // format description
			        temp = myDoc.createElement("formatdesc") ;
			        temp.setTextContent("Hub-n-spoke METS file") ;
			        root.appendChild( temp );
			        
			        //language node
			        temp = myDoc.createElement("language") ;
			        temp.setTextContent("en") ;
			        root.appendChild( temp );
			
			        //security node
			        temp = myDoc.createElement("security") ;
			        temp.setTextContent("public") ;
			        root.appendChild( temp );

			        // Create and add filename node
			        Element file = myDoc.createElement("file") ;
			        temp = myDoc.createElement("filename") ;
			        temp.setTextContent(hubpackageName) ;
			        file.appendChild( temp ) ;
			        temp = myDoc.createElement("filesize") ;
			        // NO FILE SIZE right now, since we still have to write to
			        //    Mets... So, this dmdsec will NOT be totally correct
			        file.appendChild( temp ) ;
			        temp = myDoc.createElement("data") ;
			        temp.setAttribute("href", hubpackageName) ;
			        file.appendChild( temp ) ;
			        			        
			        Element files = myDoc.createElement("files") ;
			        files.appendChild( file );
			        
			        root.appendChild( files );
			        			        
					// add this node as fragment child to epNode
			        // First, Create a documentFragment
			        DocumentFragment docFrag = myDoc.createDocumentFragment();
			        docFrag.appendChild(root);
			        
			        Node oneDocNode = epNode.getOwnerDocument().importNode(docFrag, true);
					epNode.appendChild(oneDocNode);
			        
			        }catch(Exception e){
			          System.out.println(e.getMessage());
			        }
//			    System.setProperty("javax.xml.parsers.DocumentBuilderFactory", prevFactory);
				
				//Serialize this DOM document (without METS file size) to a file
				File dc = new File(hubbase+File.separatorChar+"eprints.xml");
				FileOutputStream fout = new FileOutputStream(dc);

				// for testing... output the transformation result to screen
				// Result toScreen = new StreamResult(System.out);
				
				TransformerFactory tfactOut = TransformerFactory.newInstance();
				Transformer fileOut = tfactOut.newTransformer() ;
				DOMSource myDomSource = new DOMSource(resultNode); 
				// For testing... to screen 
				// fileOut.transform(myDomSource, toScreen) ;
				Result finRes = new StreamResult(fout);
				fileOut.transform(myDomSource, finRes);

				// For testing...
				// System.out.println("testing... After dom to file transformation") ;          
	
				//add transformed file as an ALTERNATE_DMDSEC to the METS
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(dc); 

				MetsMdSecType altdmd = hubpackage.addAlternateDmdSec
					(MetsMdSecType.MdWrap.MDTYPE.DC, null, doc);	
				
				//now create PREMIS events for the transformation
				PremisEventDocument event = HaSMETSProfile.createBasicPremisEvent
					(PREMISEventType.METADATA_TRANSFORMATION, "Transformed to Eprints " +
							"compliant core"); 
				PremisAgentDocument[] agents = {
					hubpackage.getDefaultHumanAgent(), 
					hubpackage.getDefaultSoftwareAgent()
					};
				PREMISLinkingAgentRole[] roles = {
					PREMISLinkingAgentRole.EVENT_INITIATOR,
					PREMISLinkingAgentRole.SOFTWARE_USED
					};
				hubpackage.addEventToXmlObject(altdmd, event, agents, roles);

				// Now, put in METS file size
				// cannot do xpath and update DOM via saxon8... it won't handle it
//				System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
//				"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
					
			    // set node from xml DOM tree for Mets file
			    // expression = "//ep:file[ep:filename='echodepmets.xml']/ep:filesize" ;
			    expression = "//file[filename='"+hubpackageName+"']/filesize" ;
			    epNode = (Node) xpath.evaluate(expression, resultNode, XPathConstants.NODE);
			    File ourMets = new File(hubpackage.getBaseURI()) ;
			    // assumes file is in the present directory...
			    long metsFileSize = ourMets.length() ;
			    String str = String.valueOf(metsFileSize);
		        epNode.setTextContent(str) ;

//		        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", prevFactory);
				
				//Serialize this DOM document (WITH METS file size) to a file (overwrite it)
				dc = new File(hubbase+File.separatorChar+"eprints.xml");
				fout = new FileOutputStream(dc);

				myDomSource = new DOMSource(resultNode);
				Result finResFinal = new StreamResult(fout);
				fileOut.transform(myDomSource, finResFinal);

				// For testing...
				// System.out.println("testing... After dom to file transformation") ;          

				//add transformed file to the zip
				HaSZipUtils.addFilesToZip(dc, zip);
			
			} catch (TransformerConfigurationException e1) {
			    // TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (TransformerFactoryConfigurationError e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (TransformerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace(System.out);
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (XPathExpressionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
//		} catch (Exception e) {
//			throw new PackagerException("Error transforming MODS metadata.", e);
//		}

		// Save local METS and Master Mets files to Zip	
		Mptr mptrs[] = mastermets.getAllMptrs();
		try {
			for (Mptr mptr : mptrs) {
				HaSZipUtils.addFilesToZip(new File(mastermets.getBaseURI()
						.resolve(mptr.getHref())), zip);
			}
			// compute checksums & size while saving mastermets file
			mastermets.save() ;
			// update mastermets object with new checksums & size
			mastermets.update_checksum_latest_mets() ;
			// recreate mastermets file with correct checksums & size
			HaSZipUtils.addFilesToZip(new File(mastermets.getBaseURI()), zip);
		} catch (IOException e) {
			e.printStackTrace();
			throw new PackagerException(
					"Problem saving the METS file to the zip.", e);
		}
		try {
			zip.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new PackagerException(e.getMessage());
		}
		return dest;
	}
	
	private static String formatHandleForFileName(String handle){
		if (handle.contains("/")){
			handle = handle.replaceAll("/", "_");
		}
		if (handle.contains(":")){
			handle = handle.replaceAll(":", "_");
		}
		return handle;
	}
	
	/**
     * Prints hints on how to use the program from the command line.
     */
	private static void usage(){
		System.out.println("");
	    System.out.println("====================================================");
	    System.out.println("USAGE: Hub2EprintsTransformer /path/to/hub/package /output/dir eprints-handle"); 
	    System.out.println("Example: Hub2EprintsTransformer d:\\staging\\hubandspokemets.xml d:\\out 00101/3343"); 
	    System.out.println("====================================================");
	}

}
