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
 * Forma de Emissão da NF-e.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum TpEmis {
	
	/**
	 * Emissão em contingência com impressão do DANFE em Formulário de
	 * Segurança.
	 */
	NORMAL('1'),
	/**
	 * Emissão em contingência com impressão do DANFE em Formulário de
	 * Segurança.
	 */
	CONTINGENCIA_FS('2'),
	/**
	 * Emissão em contingência no Sistema de Contingência do Ambiente 
	 * Nacional - SCAN.
	 */
	CONTINGENCIA_SCAN('3'),
	/**
	 * Emissão em contingência com envio da Declaração Prévia de Emissão 
	 * em Contingência – DPEC.
	 */
	CONTINGENCIA_DPEC('4'),
	/**
	 * Emissão em contingência com impressão do DANFE em Formulário de 
	 * Segurança para Impressão de Documento Auxiliar de Documento Fiscal 
	 * Eletrônico (FS-DA).
	 */
	CONTINGENCIA_FSDA('5');
	
	private char value;
	
	private TpEmis(char value) {
		this.value = value;
	}
	
	/**
	 * Valor literal atribuído à forma de Emissão da NF-e.
	 */
	public char getValue() {
		return this.value;
	}

}
