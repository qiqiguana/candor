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
 * Modalidade de frete.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum ModFrete {
	
	/**
	 *  Por conta do emitente.
	 */
	EMITENTE('0'),
	/**
	 *  Por conta do destinat�rio.
	 */
	DESTINATARIO('1'),
	/**
	 *  Por conta de terceiros.
	 */
	TERCEIROS('2'),
	/**
	 *  Sem frete.
	 */
	SEM_FRETE('9');
	
	private ModFrete(char value) {
		this.value = value;
	}
	
	private char value;
	
	/**
	 * Valor atribu�do ao frete.
	 */
	public char getValue() {
		return value;
	}

}
