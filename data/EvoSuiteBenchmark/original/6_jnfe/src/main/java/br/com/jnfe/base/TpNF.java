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
 * Tipo do documento fiscal.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum TpNF {
	
	/**
	 * Tipo entrada. 
	 */
	ENTRADA('0'),
	/**
	 * Tipo sa�da.
	 */
	SAIDA('1');

	private char value;
	
	private TpNF(char value) {
		this.value = value;
	}
	
	/**
	 * Literal que identifica o tipo do documento fiscal.
	 */
	public char getValue() {
		return this.value;
	}
	
}
