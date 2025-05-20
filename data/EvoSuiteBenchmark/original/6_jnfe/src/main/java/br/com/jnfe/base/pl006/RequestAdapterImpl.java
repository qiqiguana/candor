package br.com.jnfe.base.pl006;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.xml.transform.StringResult;
import org.w3c.dom.Document;

import br.com.jnfe.base.adapter.RequestAdapter;
import br.com.jnfe.base.adapter.dom.AbstractNFeDOMAdapter;


/**
 * Implementação padrão da interface <code>RequestAdapter</code>.
 * 
 * @author mauriciofernandesdecastro
 */
public class RequestAdapterImpl extends AbstractNFeDOMAdapter implements RequestAdapter {
	
	public static final String VERSAO_DADOS = "2.00";
	public static final String PREFIXO_NAMESPACE = "http://www.portalfiscal.inf.br/nfe/wsdl/";
	
	public String newCabec() {
		return new StringBuilder("<versaoDados>")
	    .append(VERSAO_DADOS)
	    .append("</versaoDados>")
	    .toString();
	}

	/**
	 * Prepara uma solicitação.
	 * 
	 * @param servicoRemoto
	 * @param qualificadorAbreviado
	 * @param content
	 */
	public Source newRequest(String servicoRemoto, String qualificadorAbreviado, String content) {
		String nameSpaceUri = new StringBuilder(PREFIXO_NAMESPACE).append(qualificadorAbreviado).toString();
		XMLStreamWriter writer = null;
		Document doc = newDocument(nameSpaceUri, servicoRemoto);
		try {
			XMLOutputFactory output = XMLOutputFactory.newInstance();
			output.setProperty("javax.xml.stream.isNamespaceAware", new Boolean(false));
			writer = output.createXMLStreamWriter(new DOMResult(doc.getDocumentElement()));
			
			writer.writeStartElement("nfeDadosMsg");
			writer.writeCharacters(content);
			writer.writeEndElement();
			
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException("Impossível gerar nova requisição, ", e);
		}

		if (logger.isTraceEnabled()) {
			try {
				Result stringResult = new StringResult();
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer();
				transformer.transform(new DOMSource(doc), stringResult);
				logger.trace("+++ A NOVA REQUISIÇÃO É: {}", stringResult);
			} catch (TransformerException e) {
				throw new IllegalArgumentException("Impossível gerar nova requisição, ", e);
			}
		}
		
		return new DOMSource(doc);
	}
	
	private static final Logger logger = LoggerFactory.getLogger(RequestAdapterImpl.class);
	
}
