package br.com.jnfe.base.adapter.dom;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import br.com.jnfe.base.adapter.AbstractNFeAdapter;

/**
 * Base para adaptadores que fornecem um objeto DOM.
 * 
 * @author mauriciofernandesdecastro
 */
public abstract class AbstractNFeDOMAdapter extends AbstractNFeAdapter {
	
	/**
	 * Conveniente para criar um documento vazio com um namespace e elemento raiz.
	 * 
	 * @param qualifiedName
	 */
	protected Document newDocument(String qualifiedName) {
		return newDocument(getNamespaceURI(), qualifiedName);
	}

	/**
	 * Conveniente para criar um documento vazio com um namespace e elemento raiz.
	 * 
	 * @param namespaceURI
	 * @param qualifiedName
	 */
	protected Document newDocument(String namespaceURI, String qualifiedName) {
		try {
		    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		    DOMImplementation di = documentBuilder.getDOMImplementation();
			return di.createDocument(namespaceURI, qualifiedName, null);
		} catch (ParserConfigurationException e) {
			throw new IllegalArgumentException("Impossível criar novo documento, ", e);
		}
	}
	
	// collabs 
	
    private DocumentBuilderFactory documentBuilderFactory;
    
	@Resource(name="documentBuilderFactory")
	public void setDocumentBuilder(DocumentBuilderFactory documentBuilderFactory) {
		this.documentBuilderFactory = documentBuilderFactory;
	}
	
}
