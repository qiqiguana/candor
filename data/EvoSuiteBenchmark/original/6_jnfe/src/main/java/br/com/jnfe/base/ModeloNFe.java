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
 * Modelos de NF.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum ModeloNFe {
	
	/**
	 * NF-e, emitida em substituição ao modelo 1 ou 1A.
	 */
	_55,
	/**
	 * NF modelo 1 ou 1A.
	 */
	_01,
	/**
	 * Cupom Fiscal emitido por máquina registradora (não ECF).
	 */
	_2B,
	/**
	 * Cupom Fiscal PDV (não ECF).
	 */
	_2C,
	/**
	 * Cupom Fiscal ECF.
	 */
	_2D;
	
	@Override
	public String toString() {
		return name().substring(1);
	}

}
