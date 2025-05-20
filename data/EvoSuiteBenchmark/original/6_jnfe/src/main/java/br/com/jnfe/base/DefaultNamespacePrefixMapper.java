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

package br.com.jnfe.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * Requerido para eliminar o prefixo "ns2:" durante o "marshalling".
 * 
 * @author Mauricio Fernandes de Castro
 */
public class DefaultNamespacePrefixMapper extends NamespacePrefixMapper {

	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		logger.info("nameSpaceUri="+namespaceUri+", "+suggestion+" ("+requirePrefix+")");
		if (namespaceUri.equals("http://www.portalfiscal.inf.br/nfe")) {
			return "nf";
		}
		return null;
	}
	
	@Override
	public String[] getContextualNamespaceDecls() {
		return new String[] {"sec", "http://www.w3.org/2000/09/xmldsig#"};
	}
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultNamespacePrefixMapper.class);

}
