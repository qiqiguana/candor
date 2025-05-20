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
 * Modalidade de determinação da base de cálculo do ICMS.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum ModBC {

	/**
	 * Margem Valor Agregado (%).
	 */
	MARGEM('0'),
	/**
	 * Pauta (Valor).
	 */
	PAUTA('1'),
	/**
	 * Preço Tabelado Máx. (valor).
	 */
	TABELA('2'),
	/**
	 * Valor da operação.
	 */
	OPERACAO('3');
	
	private ModBC(char value) {
		this.value = value;
	}
	
	private char value;
	
	/**
	 * Valor atribuído à modalidade.
	 */
	public char getValue() {
		return this.value;
	}
}
