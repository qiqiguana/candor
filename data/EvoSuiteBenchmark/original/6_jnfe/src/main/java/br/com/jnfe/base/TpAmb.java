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
 * Enumera��o de tipos de ambiente.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum TpAmb {
	
	/**
	 * Ambiente de produ��o.
	 */
	PRODUCAO('1'),
	/**
	 * Ambiente de homologa��o.
	 */
	HOMOLOGACAO('2');
	
	private char value;
	
	private TpAmb(char value) {
		this.value = value;
	}
	
	/**
	 * Valor literal atribu�do ao tipo de ambiente.
	 */
	public char getValue() {
		return this.value;
	}

	/**
	 * Valor literal atribu�do ao tipo de ambiente como String.
	 */
	public String getValueAsString() {
		return String.valueOf(this.value);
	}
	
	/**
	 * Resolve TpAmb a partir do valor atribu�do.
	 * 
	 * @param value
	 */
	public static TpAmb valueOf(char value) {
		if (value=='1') {
			return PRODUCAO;
		}
		if (value=='2') {
			return HOMOLOGACAO;
		}
		throw new IllegalArgumentException("Valor inv�lido para TpAmb");
	}

}
