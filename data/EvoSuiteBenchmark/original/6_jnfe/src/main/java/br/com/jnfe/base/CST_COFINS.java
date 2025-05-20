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
 * Código da situação tributária do COFINS.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum CST_COFINS {
	
	/**
	 * Base de cálculo = valor da operação alíquota normal 
	 * (cumulativo/nãocumulativo).
	 */
	COFINS_01("01", "Aliq", "Alíquota normal", false, "vBC", "pCOFINS", "vCOFINS"),
	/**
	 * Base de cálculo = valor da operação 
	 * (alíquota diferenciada).
	 */
	COFINS_02("02", "Aliq", "Alíquota diferenciada", false, "vBC", "pCOFINS", "vCOFINS"),
	/**
	 * Base de cálculo = quantidade vendida x alíquota por 
	 * unidade de produto.
	 */
	COFINS_03("03", "Qtde", "Alíquota por unidade de produto", false, "qBCProd", "vAliqProd", "vCOFINS"),
	/**
	 * Tributação monofásica (alíquota zero).
	 */
	COFINS_04("04", "NT",   "Tributação monofásica", true),
	/**
	 * Alíquota zero.
	 */
	COFINS_06("06", "NT",   "Alíquota zero", true),
	/** 
	 * Operação Isenta da contribuição;
	 */
	COFINS_07("07", "NT",   "Isento da contribuição", true),
	/**
	 * Operação sem Incidência da contribuição.
	 */
	COFINS_08("08", "NT",   "Sem Incidência da contribuição", true),
	/**
	 * Operação com suspensão da contribuição.
	 */
	COFINS_09("09", "NT",   "Com suspensão da contribuição", true),
	/**
	 * Outras operações.
	 */
	COFINS_99("99", "Outr", "Outras operações", false, "vBC", "pCOFINS", "qBCProd", "vAliqProd", "vCOFINS");
	
	private CST_COFINS(String value, String group, String desc, boolean isento, String... requiredTags) {
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
	 * Código atribuído ao CST COFINS.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Grupo de CST COFINS.
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
