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
 * Finalidade de emissão da NF-e.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum FinNFe {
	
	/**
	 * .
	 */
	NORMAL('1'),
	/**
	 * .
	 */
	COMPLEMENTAR('2'),
	/**
	 * .
	 */
	AJUSTE('3');
	
	private char value;
	
	private FinNFe(char value) {
		this.value = value;
	}
	
	/**
	 * Valor literal atribuído à finalidade de emissão da NF-e.
	 */
	public char getValue() {
		return this.value;
	}

}
