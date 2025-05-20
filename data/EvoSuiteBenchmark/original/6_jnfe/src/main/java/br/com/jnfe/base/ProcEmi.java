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

/**
 * Identificador do processo de emissão da NF-e.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum ProcEmi {
	
	/**
	 * Emissão de NF-e com aplicativo do contribuinte.
	 */
	APLICATIVO_CONTRIB('0'),
	/**
	 * Emissão de NF-e avulsa pelo Fisco
	 */
	AVULSA_FISCO('1'),
	/**
	 * Emissão de NF-e avulsa, pelo contribuinte com seu 
	 * certificado digital, através do site do Fisco.
	 */
	AVULSA_CONTRIB('2'),
	/**
	 * Emissão de NF-e pelo contribuinte com aplicativo 
	 * fornecido pelo Fisco.
	 */
	APLICATIVO_FISCO('3');

	private char value;
	
	private ProcEmi(char value) {
		this.value = value;
	}
	
	/**
	 * Literal que identifica o processo de emissão da NF-e.
	 */
	public char getValue() {
		return this.value;
	}
	
}
