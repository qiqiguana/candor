package net.sf.sugar.scl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 07-Sep-2008
 * Time: 21:52:24
 */
public class DefaultLocalReferenceProcessor implements LocalReferenceProcessor {

    private final XPathFactory xPathFactory;

    public DefaultLocalReferenceProcessor() {
        this.xPathFactory = XPathFactory.newInstance();
    }

    public void processLocalReferences(List<LocalReference> localRefs) throws LocalReferenceException {
        //loop through each local ref until they are all resolved
        int lastResolvedCount = 0;
        LocalReferenceException lastException = new LocalReferenceException("unable to resolve local references");

        while (true) {
            for (LocalReference r : localRefs) {
                if (!r.isResolved()) {
                    if (r instanceof LocalAttributeReference) {
                        try {
                            processLocalAttributeReference((LocalAttributeReference) r);
                        } catch (LocalReferenceException lre) {
                            lastException = lre;
                        }
                    } else {
                        try {
                            processLocalReference(r);
                        } catch (LocalReferenceException lre) {
                            lastException = lre;
                        }
                    }
                }
            }

            int newResolvedCount = getResolvedCount(localRefs);

            if (newResolvedCount == localRefs.size()) {
                return;
            }
            
            if (!(newResolvedCount > lastResolvedCount)) {
                throw lastException;
            } else {
                lastResolvedCount = newResolvedCount;
            }
        }
    }

    private int getResolvedCount(Iterable<LocalReference> localRefs) {
        int resolvedCount = 0;
        for (LocalReference localRef : localRefs) {
            if (localRef.isResolved()) {
                resolvedCount++;
            }
        }
        return resolvedCount;
    }

    private void processLocalAttributeReference(LocalAttributeReference localAttributeRef) throws LocalReferenceException {
        try {
            XPath xPath = this.xPathFactory.newXPath();
            Element parentElement = localAttributeRef.getParentElement();
            Document ownerDocument = parentElement.getOwnerDocument();

            String resolvedValue = (String) xPath.evaluate(localAttributeRef.getXPathQuery(),
                    ownerDocument,
                    XPathConstants.STRING);

            System.out.println("result of query : " + localAttributeRef.getXPathQuery() + " : " + resolvedValue);

            if (resolvedValue == null || "".equals(resolvedValue)) {
                throw new LocalReferenceException("expresison resolved to an empty value");
            }

            Element newElement = ownerDocument.createElement(localAttributeRef.getAttributeName());
            newElement.setTextContent(resolvedValue);
            parentElement.appendChild(newElement);
            localAttributeRef.setResolved();

        } catch (XPathException xpe) {
            throw new LocalReferenceException("an XPathException was thrown : " + xpe, xpe);
        }
    }

    private void processLocalReference(LocalReference localRef) throws LocalReferenceException {
        try {
            XPath xPath = this.xPathFactory.newXPath();
            Element parentElement = localRef.getParentElement();
            Document ownerDocument = parentElement.getOwnerDocument();

            NodeList resolvedNodes = (NodeList) xPath.evaluate(localRef.getXPathQuery(),
                    ownerDocument,
                    XPathConstants.NODESET);

            System.out.println("result of query : " + localRef.getXPathQuery() + " : " + resolvedNodes.toString());

            if (resolvedNodes.getLength() < 1) {
                throw new LocalReferenceException("expresison resolved to an empty value");
            }

            for (int i = 0; i < resolvedNodes.getLength(); i++) {
                Node clone = resolvedNodes.item(i).cloneNode(true);
                parentElement.appendChild(clone);
            }

            localRef.setResolved();

        } catch (XPathException xpe) {
            throw new LocalReferenceException("an XPathException was thrown : " + xpe, xpe);
        }
    }
}
