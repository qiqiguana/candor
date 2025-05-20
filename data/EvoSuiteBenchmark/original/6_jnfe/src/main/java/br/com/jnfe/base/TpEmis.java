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
 * Forma de Emiss�o da NF-e.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum TpEmis {
	
	/**
	 * Emiss�o em conting�ncia com impress�o do DANFE em Formul�rio de
	 * Seguran�a.
	 */
	NORMAL('1'),
	/**
	 * Emiss�o em conting�ncia com impress�o do DANFE em Formul�rio de
	 * Seguran�a.
	 */
	CONTINGENCIA_FS('2'),
	/**
	 * Emiss�o em conting�ncia no Sistema de Conting�ncia do Ambiente 
	 * Nacional - SCAN.
	 */
	CONTINGENCIA_SCAN('3'),
	/**
	 * Emiss�o em conting�ncia com envio da Declara��o Pr�via de Emiss�o 
	 * em Conting�ncia � DPEC.
	 */
	CONTINGENCIA_DPEC('4'),
	/**
	 * Emiss�o em conting�ncia com impress�o do DANFE em Formul�rio de 
	 * Seguran�a para Impress�o de Documento Auxiliar de Documento Fiscal 
	 * Eletr�nico (FS-DA).
	 */
	CONTINGENCIA_FSDA('5');
	
	private char value;
	
	private TpEmis(char value) {
		this.value = value;
	}
	
	/**
	 * Valor literal atribu�do � forma de Emiss�o da NF-e.
	 */
	public char getValue() {
		return this.value;
	}

}
