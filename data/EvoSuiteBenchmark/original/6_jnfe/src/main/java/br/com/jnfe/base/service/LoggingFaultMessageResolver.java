/* Copyright 2005 I Serv Consultoria Empresarial Ltda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.jnfe.base.service;

import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.FaultMessageResolver;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.soap12.Soap12Fault;
import org.springframework.xml.transform.StringResult;

/**
 * Útil para visualizar o conteúdo de falhas SOAP.
 * 
 * @author Mauricio Fernandes de Castro
 */
public class LoggingFaultMessageResolver implements FaultMessageResolver {

	public void resolveFault(WebServiceMessage message) throws IOException {
		try {
	        SoapBody body = ((SoapMessage) message).getSoapBody();
	        Soap12Fault soapFault = (Soap12Fault) body.getFault();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			StringResult result = new StringResult();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(soapFault.getSource(), result);
			logger.warn("Soap fault: "+result);
		} catch (Exception e) {
			logger.warn("Impossível processar Soap fault: ", e);
		}
	}
	
	public static final Logger logger = LoggerFactory.getLogger(LoggingFaultMessageResolver.class);

}
