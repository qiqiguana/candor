package net.sf.sugar.scl;

import static net.sf.sugar.scl.DefaultIncludeProcessor.SupportedSchemes.*;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.w3c.dom.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import static net.sf.sugar.scl.SCLParser.SCL_ROOT_ELEMENT;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 02-Aug-2008
 * Time: 15:29:27
 * <p/>
 * process SCL includes according to the following rules
 * <p/>
 * 1)  URI's beginning with 'http' or 'https' will be loaded over the network.
 * 2)  URI's beginning with 'file' wil be loaded from the filesystem.
 * 3)  URI's beginning with 'classpath' will be loaded by the ContextClassloader.
 *
 * @author kbishop
 * @version $Id$
 */
public class DefaultIncludeProcessor implements IncludeProcessor {

    /**
     *  The one and only root SCL file which was the first to be parsed.
     *
     */
    private final URI rootSCLFile;

    private final Document rootDocument;

    private final XPathFactory xPathFactory;

    private final Map<URI, IncludeHolder> includeCache = new HashMap<URI, IncludeHolder>();
    
    /**
     * A location to cache all the includes which have been read or downloaded.
     * We do this so we can handle self references and cyclical references appropriately.
     */
    private final List<Include> includes = new ArrayList<Include>();


    public static enum SupportedSchemes {
        http, jar, classpath, file
    }

    public DefaultIncludeProcessor(URI rootSCLFile, Document rootDocument) {
        this.xPathFactory = XPathFactory.newInstance();
        this.rootSCLFile = rootSCLFile;
        this.rootDocument = rootDocument;
    }

    public void prepareInclude(final Element parentElement, String includeLocation, final URI parentLocation) throws IncludeException {
        final URI includeLocationURI = resolveIncludeLocation(includeLocation, parentLocation);

        if (!this.includeCache.containsKey(includeLocationURI)) {
            this.includeCache.put(includeLocationURI, new IncludeHolder());
        }

        final IncludeHolder holder = this.includeCache.get(includeLocationURI);

        Include include = new Include() {
            {
                setParentURI(parentLocation);
                setParentElement(parentElement);
                setIncludeURI(includeLocationURI);
                setIncludeHolder(holder);
            }
        };

        includes.add(include);
    }

    public void prepareInclude(final Element parentElement, String includeLocation, final URI parentLocation, final String xpathQuery) throws IncludeException {
        final URI includeLocationURI = resolveIncludeLocation(includeLocation, parentLocation);

        if (!this.includeCache.containsKey(includeLocationURI)) {
            this.includeCache.put(includeLocationURI, new IncludeHolder());
        }

        final IncludeHolder holder = this.includeCache.get(includeLocationURI);

        IncludePartial include = new IncludePartial() {
            {
                setParentURI(parentLocation);
                setParentElement(parentElement);
                setIncludeURI(includeLocationURI);
                setXPathQuery(xpathQuery);
                setIncludeHolder(holder);
            }
        };

        includes.add(include);
    }

    public void prepareIncludeValue(final Element parentElement,
                                    final String propertyName,
                                    String includeLocation,
                                    final URI parentLocation,
                                    final String xpathQuery) throws IncludeException {
        
        final URI includeLocationURI = resolveIncludeLocation(includeLocation, parentLocation);

        if (!this.includeCache.containsKey(includeLocationURI)) {
            this.includeCache.put(includeLocationURI, new IncludeHolder());
        }

        final IncludeHolder holder = this.includeCache.get(includeLocationURI);

        IncludeValue include = new IncludeValue() {
            {
                setParentURI(parentLocation);
                setParentElement(parentElement);
                setIncludeURI(includeLocationURI);
                setXPathQuery(xpathQuery);
                setIncludeHolder(holder);
                setPropertyName(propertyName);
            }
        };

        includes.add(include);
    }

    /**
     * Uses the SCL Parser and Lexer to process the include.
     *
     * @param includeLocationURI - a String to be parsed into a URI, representing the location of the include to process.
     * @return Document - an XML DOM representation of the SCL configuration.
     * @throws IncludeException - thrown if there is an I/O problem or an ANTLR specific problem.
     */
    Document processInclude(URI includeLocationURI) throws IncludeException {

        InputStream is = this.loadInclude(includeLocationURI);

        SCLLexer lexer;

        try {
            lexer = new SCLLexer(new ANTLRInputStream(is));

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SCLParser parser = new SCLParser(tokens);
            parser.setRootSCLFile(includeLocationURI);
            parser.setIncludeProcessor(this);

            return parser.scl();

        } catch (RecognitionException re) {
            throw new IncludeException("unable to parse include [" + includeLocationURI + "]", re);
        } catch (IOException ioe) {
            throw new IncludeException("an error occurred while parsing include [" + includeLocationURI + "]", ioe);
        }
    }

    private URI resolveIncludeLocation(String includeLocation, URI parentLocation) throws IncludeException {
        URI includeLocationURI;
        try {
            includeLocationURI = parentLocation.resolve(new URI(includeLocation));

            //deal this jar:file type URIs differently as this is broken in the JRE
            if (jar.name().equals(parentLocation.getScheme())) {
                String parentQuery = parentLocation.toString();
                String[] components = parentQuery.split("!");
                String newInnerJarPath = new URI(components[1]).resolve(includeLocation).toString();
                includeLocationURI = new URI(components[0] + "!" + newInnerJarPath);
            }

        } catch (URISyntaxException urise) {
            throw new IncludeException("unable to include file [" + includeLocation + "]", urise);
        }
        return includeLocationURI;
    }

    /**
     * This method takes the include location and the parent location, and attempts to resolve the location of the
     * include based on the URI of the parent.
     * <p/>
     * For instance, if the parent URI is http://foo.com/root.scl and that file includes 'inc1.scl' , then the resolved
     * URI for the include will be http://foo.com/inc1.scl
     *
     * @param includeLocation - a String which will b parsed into a URI in order to resolve the include's location.
     * @return java.io.InputStream
     * @throws IncludeException - an exception notifying the caller that it was unable to load the include
     */
    InputStream loadInclude(URI includeLocation) throws IncludeException {

        String scheme = includeLocation.getScheme();

        if (http.name().equals(scheme)) {
            return loadIncludeFromHttp(includeLocation);
        }

        if (classpath.name().equals(scheme)) {
            return loadIncludeFromClasspath(includeLocation);
        }

        if (jar.name().equals(scheme)) {
            return loadIncludeFromJar(includeLocation);
        }

        if (file.name().equals(scheme)) {
            return loadIncludeFromFilesystem(includeLocation);
        }

        throw new IncludeException("include statements must use the following schemes "
                + Arrays.toString(SupportedSchemes.values()));

    }

    private InputStream loadIncludeFromJar(URI uri) throws IncludeException {
        try {
            return (uri.toURL().openConnection()).getInputStream();
        } catch (MalformedURLException murle) {
            throw new IncludeException("unable to load the include [" + uri + "] it is an invalid URI : ", murle);
        } catch (IOException ioe) {
            throw new IncludeException("unable to load the include [" + uri + "]", ioe);
        }
    }

    private InputStream loadIncludeFromClasspath(URI uri)  {
        String path = uri.getPath();
        return getClass().getClassLoader().getResourceAsStream(path);
    }

    private InputStream loadIncludeFromFilesystem(URI uri) throws IncludeException {
        try {
            return new FileInputStream(new File(uri));
        } catch (IOException ioe) {
            throw new IncludeException("unable to load include [" + uri + "] from filesystem : ", ioe);
        }
    }

    private InputStream loadIncludeFromHttp(URI uri) throws IncludeException {
        try {
            return uri.toURL().openStream();
        } catch (IOException ioe) {
            throw new IncludeException("unable to load include [" + uri + "] over http : ", ioe);
        }
    }

    /**
     * This method does the clever work of connecting each parsed DOM to the parent document that included it.
     *
     * When the parser finds an include during the parse, it calls the relevant prepareInclude() method,
     * prepareInclude() adds the relevant information to a global list of includes.
     * Once the rest of the document has been parsed, then this method is called.
     *
     * Resolution involves the following steps :
     * <ol>
     * <li>Find the includes to be processed for this document.</li>
     * <li>Check for circular dependencies.  (i.e. any include which eventually leads to being included itself)<li>
     * <li>Process the include (i.e. start to parse it) and store the resulting DOM in the relevant <code>Include</code> object.<li>
     * <li>In the case of a circular depencency being found, there is no need to parse that include as the document will have already been parsed.
     *     Instead we just call findBenignDocument() which will return the required DOM, but without any of its includes (as they wont be processed).<li>
     * <li>Once the relevant DOM objects have been built each one is then added to the relevent element in the parent document.
     * In the case of includes which are filtered by an XPath expression, the resulting nodes of the XPath expression are added to the parent DOM.</li>
     * <li>Set the 'resolved' status of the Include to true, to prevent it being picked up again.</li>
     * </ol>
     *
     * @param currentDocument - the document currently being precessed by the parser.
     * @throws IncludeException - thrown if the include procesing fails or if any XPath queries break.
     */
    public void resolveIncludes(Document currentDocument) throws IncludeException {

        //copy the include cache as processing the includes will modify it
        List<Include> snapshot = findUnresolvedIncludes(currentDocument);

        for (Include include : snapshot) {
            Include circularInclude = this.getCircularInclude(include, include.getParentURI());

            if ((circularInclude != null)) {

                Document benignDocument = findBenignDocument(circularInclude);
                include.getIncludeHolder().setDocument(benignDocument);

            } else if (include.getIncludeHolder().getDocument() == null) {
                Document includedDocument = this.processInclude(include.getIncludeURI());
                include.getIncludeHolder().setDocument(includedDocument);
            }
        }

        for (Include include : snapshot) {
            Element parentElement = include.getParentElement();

            if (include instanceof IncludeValue) {
                IncludeValue includeValue = (IncludeValue)include;
                String xPathQuery = includeValue.getXPathQuery();

                try {
                    XPath xpath = this.xPathFactory.newXPath();
                    String value = (String)xpath.evaluate("/"+ SCL_ROOT_ELEMENT + "/" + xPathQuery, include.getIncludeHolder().getDocument().getDocumentElement(), XPathConstants.STRING);
                    Element property = currentDocument.createElement(includeValue.getPropertyName());
                    property.setTextContent(value);
                    parentElement.appendChild(property);
                    
                } catch (XPathExpressionException xpee) {
                    throw new IncludeException("unable to evaluate XPath query : " + xpee, xpee);
                }
            } else {
                NodeList resolvedNodes;

                if (include instanceof IncludePartial) {
                    String xpathQuery = ((IncludePartial)include).getXPathQuery();

                    try {
                        XPath xpath = this.xPathFactory.newXPath();
                        resolvedNodes = (NodeList)xpath.evaluate("/"+ SCL_ROOT_ELEMENT + "/" + xpathQuery, include.getIncludeHolder().getDocument().getDocumentElement(), XPathConstants.NODESET);
                    } catch (XPathExpressionException xpee) {
                        throw new IncludeException("unable to evaluate XPath query : " + xpee, xpee);
                    }
                } else {
                    resolvedNodes = include.getIncludeHolder().getDocument().getDocumentElement().getChildNodes();
                }

                for (int i = 0; i < resolvedNodes.getLength(); i++) {
                    Node importedNode = currentDocument.importNode(resolvedNodes.item(i), true);
                    parentElement.appendChild(importedNode);
                }
            }

            include.setFullyResolved();
        }
    }

    public void loadUnparsedInclude(Element parentElement,
                                    String propertyName,
                                    String includeLocation,
                                    URI parentLocation) throws IncludeException {

        final URI includeLocationURI = resolveIncludeLocation(includeLocation, parentLocation);

        InputStream is = this.loadInclude(includeLocationURI);

        StringBuilder builder = new StringBuilder(256);
        int charRead = 0;

        try {
            while ((charRead = is.read()) != -1) {
                builder.append((char)charRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element property = parentElement.getOwnerDocument().createElement(propertyName);
        CDATASection cdata = parentElement.getOwnerDocument().createCDATASection(builder.toString());
        property.appendChild(cdata);
        parentElement.appendChild(property);
    }

    /**
     * This method finds includes contained in the global list of includes, which belong to the DOM being currently
     * processed AND are not already processed.
     * 
     * @param currentDocument - the docuement to find the nresolved includes for.
     * @return List<Include> - the list of unprocessed includes for the current document.
     */
    private List<Include> findUnresolvedIncludes(Document currentDocument) {
        List<Include> unresolvedIncludes = new ArrayList<Include>();
        for (Include include : this.includes) {
            Document includeParentDocument = include.getParentElement().getOwnerDocument();

            if (!include.isFullyResolved() && includeParentDocument.equals(currentDocument)) {
                unresolvedIncludes.add(include);
            }
        }
        return unresolvedIncludes;
    }

    /**
     *  Detects circular references in the include graph by identifying if the parent of the current include
     *  exists in the includeCache but is unresolved and also has children which are unresolved.
     *
     *
     *
     * @param include - the include to check fo circular dependencies on.
     * @param parentURI - the URI of
     * @return include - the
     */
    private Include getCircularInclude(Include include, URI parentURI) {
        for (Include i : this.includes) {
            if (i.getIncludeURI().equals(parentURI) && i.getIncludeHolder().getDocument() == null) {
                if(i.getIncludeURI().equals(include.getIncludeURI())) {
                    return include;
                } else {
                    return this.getCircularInclude(include, i.getParentURI());
                }
            }
        }
        return null;
    }

    /**
     *  This is effectively the Document at the beginning of a circular include.
     *  Once the circular include has been located, then we need to find any include which is a child of this one as
     *  that will contain the reference to the Document (via the parent element) that we need.
     *
     *  At this point we will expect that the document will be built but any includes will not be present, hence the term
     *  'Benign' (as in self limiting) .
     *
     *  If the include is of the root document then that is returned instead. It is assumed that an instance of this object
     *  is constructed with a reference to the root URI and root DOM document (which is created by instantiating the SCLParser).
     * 
     * @param circularInclude - the include which we are checking.
     * @return - Document the benign version of the document to include.
     */
    private Document findBenignDocument(Include circularInclude) {
        if (circularInclude.getIncludeURI().equals(this.rootSCLFile)) {
            return this.rootDocument;
        } else {
            for (Include includeChild : this.includes) {
                if (includeChild.getParentURI().equals(circularInclude.getIncludeURI())) {
                    return includeChild.getParentElement().getOwnerDocument();
                }
            }
        }
        return null;
    }
}


