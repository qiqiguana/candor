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
 * Modalidade de determinação da base de cálculo do ICMS substituição 
 * tributária.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum ModBCST {

	/**
	 * Preço tabelado ou máximo sugerido.
	 */
	TABELA('0'),
	/**
	 * Lista negativa (valor).
	 */
	LISTA_NEGATIVA('1'),
	/**
	 * Lista positiva (valor).
	 */
	LISTA_POSITIVA('2'),
	/**
	 * Lista neutra (valor).
	 */
	LISTA_NEUTRA('3'),
	/**
	 * Margem Valor Agregado (%).
	 */
	OPERACAO('4'),
	/**
	 * Pauta (valor).
	 */
	PAUTA('5');
	
	private ModBCST(char value) {
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
