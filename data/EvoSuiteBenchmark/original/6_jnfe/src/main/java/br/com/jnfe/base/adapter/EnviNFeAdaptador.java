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

package br.com.jnfe.base.adapter;



/**
 * Estrat�gia para adaptar a interface local do lote de envio da NFe 
 * (i.e. a utilizada na camada de persist�ncia) com aquela
 * definida pelos pacotes PLxxx.
 * 
 * @param <P> tipo do lote de NFe que ser� persistido, normalmente
 *            br.com.jnfe.EnvNFe.
 * @param <X> o tipo Xml do lote de NFe, normalmente gerado ap�s a compila��o 
 *            de um pacote PLxxx com o utilit�rio xjc (JAXB).
 *  
 * @author Mauricio Fernandes de Castro
 */
public interface EnviNFeAdaptador<P, X> {
	
	/**
	 * Cria um objeto que pode ser usado como lote de NFes para envio.
	 * 
	 * @param envNFe
	 */
	public X newEnviNFe(P envNFe);

}
