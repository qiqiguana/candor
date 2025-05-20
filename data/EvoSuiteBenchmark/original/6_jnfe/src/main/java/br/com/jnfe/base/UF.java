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
 * Unidades da federação.
 * 
 * <p>
 * Esta enumeração visa preservar uma arquitetura para estados 
 * (ou províncias) e municípios baseada em códigos (siglas) que, 
 * possam ser adicionados a um endereço sem confundir um leitor 
 * humano: "Curitiba, PR" e não "Curitiba, 41".
 * </p>
 * 
 * <p>
 * O caso de uso típico requer:</p>
 * <code>
 * String cUF = UF.valueOf(codigoLegivel).getCUF();
 * </code>
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum UF {
	
	RO("11", "Rondônia"),
	AC("12", "Acre"),
	AM("13", "Amazonas"),
	RR("14", "Roraima"),
	PA("15", "Pará"),
	AP("16", "Amapá"),
	TO("17", "Tocantins"),
	MA("21", "Maranhão"),
	PI("22", "Piauí"),
	CE("23", "Ceará"),
	RN("24", "Rio Grande do Norte"),
	PB("25", "Paraíba"),
	PE("26", "Pernambuco"),
	AL("27", "Alagoas"),
	SE("28", "Sergipe"),
	BA("29", "Bahia"),
	MG("31", "Minas Gerais"),
	ES("32", "Espírito Santo"),
	RJ("33", "Rio de Janeiro"),
	SP("35", "São Paulo"),
	PR("41", "Paraná"),
	SC("42", "Santa Catarina"),
	RS("43", "Rio Grande do Sul"),
	MS("50", "Mato Grosso do Sul"),
	MT("51", "Mato Grosso"),
	GO("52", "Goiás"),
	DF("53", "Distrito Federal");
	
	private UF(String cUF, String nomeUF) {
		this.cUF = cUF;
		this.nomeUF = nomeUF;
	}
	
	private String cUF;
	private String nomeUF;
	
	/**
	 * Código de UF do IBGE.
	 */
	public String getCUF() {
		return cUF;
	}
	
	/**
	 * Nome da unidade da federação.
	 */
	public String getNomeUF() {
		return nomeUF;
	}
	
	/**
	 * Conveniente para ler o literal da UF a partir de cUF.
	 * 
	 * @param cUF
	 */
	public static UF getUF(String cUF) {
		for (UF uf: UF.values()) {
			if (uf.getCUF().equals(cUF)) {
				return uf;
			}
		}
		return null;
	}

}
