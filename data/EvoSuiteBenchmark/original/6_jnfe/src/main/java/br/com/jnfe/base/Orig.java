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
 * Origem da mercadoria.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum Orig {
	
	/**
	 * Origem nacional.
	 */
	NACIONAL('0', true, "Nacional"),
	/**
	 * Origem estrangeira, importação direta.
	 */
	ESTRANGEIRA_IMPDIR('1', false, "Estrangeira, importação direta"),
	/**
	 * Origem estrangeira, adquirida nomercado interno.
	 */
	ESTRANGEIRA_ADQINT('2', false, "Estrangeira, adquirida nomercado interno");
	
	private Orig(char value, boolean nacional, String desc) {
		this.value = value;
		this.nacional = nacional;
		this.desc = desc;
	}
	
	private char value;
	private boolean nacional;
	private String desc;

	/**
	 * Valor atribuído à origem.
	 */
	public char getValue() {
		return value;
	}
	
	/**
	 * Verdadeiro se é nacional.
	 */
	public boolean isNacional() {
		return nacional;
	}
	
	/**
	 * Descrição.
	 */
	public String getDesc() {
		return desc;
	}

}
