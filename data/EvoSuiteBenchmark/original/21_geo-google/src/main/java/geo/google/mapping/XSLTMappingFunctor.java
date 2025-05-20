package geo.google.mapping;

import geo.google.GeoException;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Use and XSLT to convert an KML xml doc to other format.
 * @since 1.2
 * @author jliang
 *
 */
public class XSLTMappingFunctor implements XmlMappingFunctor<String>{

  private Source _xsltSource;
  
  public XSLTMappingFunctor(Source xsltSource) {
    super();
    _xsltSource = xsltSource;
  }

  public Source getXsltSource() {
    return _xsltSource;
  }

  public void setXsltSource(Source xsltSource) {
    _xsltSource = xsltSource;
  }

  public String execute(String xml) throws GeoException {

    Source source = new StreamSource(xml);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Result res = new StreamResult(out);  
    TransformerFactory transFact = TransformerFactory.newInstance();
    Transformer trans;
    
    try {
      trans = transFact.newTransformer(_xsltSource);
      trans.transform(source, res);
    }
    catch (TransformerConfigurationException e) {
      throw new GeoException(e); 
    }
    catch (TransformerException e) {
      throw new GeoException(e);
    }

    return out.toString();
  }
  
}