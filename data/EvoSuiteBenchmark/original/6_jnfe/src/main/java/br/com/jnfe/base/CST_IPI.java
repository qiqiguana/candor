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
 * Código da situação tributária do IPI.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum CST_IPI {
	
	/**
	 * Entrada com recuperação de crédito.
	 */
	IPI_00("00", "Trib", "Entrada com recuperação de crédito", false),
	/**
	 * Entrada tributada com alíquota zero.
	 */
	IPI_01("01", "NT",   "Entrada tributada com alíquota zero", true),
	/**
	 * Entrada isenta.
	 */
	IPI_02("02", "NT",   "Entrada isenta", true),
	/**
	 * Entrada não-tributada.
	 */
	IPI_03("03", "NT",   "Entrada não-tributada", true),
	/**
	 * Entrada imune.
	 */
	IPI_04("04", "NT",   "Entrada imune", true),
	/**
	 * Entrada com suspensão.
	 */
	IPI_05("05", "NT",   "Entrada com suspensão", true),
	/**
	 * Outras entradas.
	 */
	IPI_49("49", "Trib", "Outras entradas", false),
	/**
	 * Saída tributada.
	 */
	IPI_50("50", "Trib", "Saída tributada", false),
	/**
	 * Saída tributada com alíquota zero
	 */
	IPI_51("51", "NT",   "Saída tributada com alíquota zero", true),
	/**
	 * Saída isenta.
	 */
	IPI_52("52", "NT",   "Saída isenta", true),
	/**
	 * Saída não-tributada.
	 */
	IPI_53("53", "NT",   "Saída não-tributada", true),
	/**
	 * Saída imune.
	 */
	IPI_54("54", "NT",   "Saída imune", true),
	/**
	 * Saída com suspensão.
	 */
	IPI_55("55", "NT",   "Saída com suspensão", true),
	/**
	 * Outras saídas.
	 */
	IPI_99("99", "Trib", "Outras saídas", false);
	
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
	 * Código atribuído ao CST IPI.
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
	 * Descrição.
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
