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
 * Tipos de pagamento.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum IndPag {
	
	/**
	 * Pagamento à vista.
	 */
	PGTO_A_VISTA('0'),
	/**
	 * Pagamento à prazo.
	 */
	PGTO_A_PRAZO('1'),
	/**
	 * Outro tipo de pagamento.
	 */
	PGTO_OUTROS('2');

	private char value;
	
	private IndPag(char value) {
		this.value = value;
	}
	
	/**
	 * Valor literal atribuído ao tipo de pagamento.
	 */
	public char getValue() {
		return this.value;
	}
}
