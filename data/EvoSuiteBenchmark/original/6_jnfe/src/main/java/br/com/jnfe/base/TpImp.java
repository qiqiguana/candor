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
 * Formato de Impressão do DANFE.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum TpImp {
	
	/**
	 * Impressão em formato "retrato".
	 */
	RETRATO('1'),
	/**
	 * Impressão em formato "paisagem".
	 */
	PAISAGEM('2');
	
	private char value;
	
	private TpImp(char value) {
		this.value = value;
	}
	
	/**
	 * Valor literal atribuído ao formato de Impressão do DANFE.
	 */
	public char getValue() {
		return this.value;
	}

}
