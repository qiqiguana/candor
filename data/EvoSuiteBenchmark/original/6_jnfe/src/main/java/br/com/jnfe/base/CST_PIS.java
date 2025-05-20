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
 * Código da situação tributária do PIS.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum CST_PIS {
	
	/**
	 * Base de cálculo = valor da operação alíquota normal 
	 * (cumulativo/nãocumulativo).
	 */
	PIS_01("01", "Aliq", "Alíquota normal", false, "vBC", "pPIS", "vPIS"),
	/**
	 * Base de cálculo = valor da operação 
	 * (alíquota diferenciada).
	 */
	PIS_02("02", "Aliq", "Alíquota diferenciada", false, "vBC", "pPIS", "vPIS"),
	/**
	 * Base de cálculo = quantidade vendida x alíquota por 
	 * unidade de produto.
	 */
	PIS_03("03", "Qtde", "Alíquota por unidade de produto", false, "qBCProd", "vAliqProd", "vPIS"),
	/**
	 * Tributação monofásica (alíquota zero).
	 */
	PIS_04("04", "NT",   "Tributação monofásica", true),
	/**
	 * Alíquota zero.
	 */
	PIS_06("06", "NT",   "Alíquota zero", true),
	/** 
	 * Operação Isenta da contribuição;
	 */
	PIS_07("07", "NT",   "Isento da contribuição", true),
	/**
	 * Operação sem Incidência da contribuição.
	 */
	PIS_08("08", "NT",   "Sem Incidência da contribuição", true),
	/**
	 * Operação com suspensão da contribuição.
	 */
	PIS_09("09", "NT",   "Com suspensão da contribuição", true),
	/**
	 * Outras operações.
	 */
	PIS_99("99", "Outr", "Outras operações", false, "vBC", "pPIS", "qBCProd", "vAliqProd", "vPIS");
	
	private CST_PIS(String value, String group, String desc, boolean isento, String... requiredTags) {
		this.value = value;
		this.group = group;
		this.desc = desc;
		this.isento = isento;
		this.requiredTags = requiredTags;
	}
	
	private String value;
	private String group;
	private String desc;
	private boolean isento;
	private String[] requiredTags;
	
	/**
	 * Código atribuído ao CST PIS.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Grupo de CST PIS.
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

	/**
	 * Verdadeiro se a tag é requerida neste CST.
	 * 
	 * @param tag
	 */
	public boolean isRequiredTag(String tag) {
		for (String requiredTag: requiredTags) {
			if (tag.equals(requiredTag)) {
				return true;
			}
		}
		return false;
	}

}
