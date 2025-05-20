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
 * C�digo da situa��o tribut�ria do IPI.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum CST_IPI {
	
	/**
	 * Entrada com recupera��o de cr�dito.
	 */
	IPI_00("00", "Trib", "Entrada com recupera��o de cr�dito", false),
	/**
	 * Entrada tributada com al�quota zero.
	 */
	IPI_01("01", "NT",   "Entrada tributada com al�quota zero", true),
	/**
	 * Entrada isenta.
	 */
	IPI_02("02", "NT",   "Entrada isenta", true),
	/**
	 * Entrada n�o-tributada.
	 */
	IPI_03("03", "NT",   "Entrada n�o-tributada", true),
	/**
	 * Entrada imune.
	 */
	IPI_04("04", "NT",   "Entrada imune", true),
	/**
	 * Entrada com suspens�o.
	 */
	IPI_05("05", "NT",   "Entrada com suspens�o", true),
	/**
	 * Outras entradas.
	 */
	IPI_49("49", "Trib", "Outras entradas", false),
	/**
	 * Sa�da tributada.
	 */
	IPI_50("50", "Trib", "Sa�da tributada", false),
	/**
	 * Sa�da tributada com al�quota zero
	 */
	IPI_51("51", "NT",   "Sa�da tributada com al�quota zero", true),
	/**
	 * Sa�da isenta.
	 */
	IPI_52("52", "NT",   "Sa�da isenta", true),
	/**
	 * Sa�da n�o-tributada.
	 */
	IPI_53("53", "NT",   "Sa�da n�o-tributada", true),
	/**
	 * Sa�da imune.
	 */
	IPI_54("54", "NT",   "Sa�da imune", true),
	/**
	 * Sa�da com suspens�o.
	 */
	IPI_55("55", "NT",   "Sa�da com suspens�o", true),
	/**
	 * Outras sa�das.
	 */
	IPI_99("99", "Trib", "Outras sa�das", false);
	
	private CST_IPI(String value, String group, String desc, boolean isento) {
		this.value = value;
		this.group = group;
		this.desc = desc;
		this.isento = isento;
	}
	
	private String value;
	private String group;
	private String desc;
	private boolean isento;
	
	/**
	 * C�digo atribu�do ao CST IPI.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Grupo de CST IPI.
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * Descri��o.
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * Verdadeiro se isento.
	 */
	public boolean isIsento() {
		return isento;
	}

}
