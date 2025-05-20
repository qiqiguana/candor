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
 * C�digo da situa��o tribut�ria do ICMS, engloba ICMS normal e SN.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum C_ICMS {
	
	/**
	 * Tributada integralmente.
	 */
	_00(CST_Grupo._00),
	/**
	 * Tributada e com cobran�a do ICMS por substitui��o tribut�ria.
	 */
	_10(CST_Grupo._10),
	/**
	 * Com redu��o de base de c�lculo.
	 */
	_20(CST_Grupo._20),
	/**
	 * Isenta ou n�o tributada e com cobran�a do ICMS por
	 * substitui��o tribut�ria.
	 */
	_30(CST_Grupo._30),
	/**
	 * Isenta.
	 */
	_40(CST_Grupo._40),
	/**
	 * N�o tributada.
	 */
	_41(CST_Grupo._40),
	/**
	 * Suspens�o.
	 */
	_50(CST_Grupo._40),
	/**
	 * Diferimento.
	 */
	_51(CST_Grupo._51),
	/**
	 * ICMS cobrado anteriormente por substitui��o tribut�ria.
	 */
	_60(CST_Grupo._60),
	/**
	 * Com redu��o de base de c�lculo e cobran�a do ICMS por 
	 * substitui��o tribut�ria.
	 */
	_70(CST_Grupo._70),
	/**
	 * Outros (ICMS normal).
	 */
	_90(CST_Grupo._90),
	/**
	 * Partilha.
	 */
	_Part(CST_Grupo._Part),
	/**
	 * Tributada pelo Simples nacional com permiss�o de cr�dito.
	 */
	_101(CST_Grupo._SN101),
	/**
	 * Tributada pelo Simples nacional sem permiss�o de cr�dito.
	 */
	_102(CST_Grupo._SN102),
	/**
	 * Isen��o do Simples nacional por faixa de receita bruta.
	 */
	_103(CST_Grupo._SN102),
	/**
	 * Tributada pelo Simples nacional com permiss�o de cr�dito e
	 * com cobran�a de ICMS por substitui��o tribut�ria.
	 */
	_201(CST_Grupo._SN201),
	/**
	 * Tributada pelo Simples nacional sem permiss�o de cr�dito e
	 * com cobran�a de ICMS por substitui��o tribut�ria.
	 */
	_202(CST_Grupo._SN202),
	/**
	 * Isen��o do Simples nacional por faixa de receita bruta e
	 * com cobran�a de ICMS por substitui��o tribut�ria.
	 */
	_203(CST_Grupo._SN202),
	/**
	 * Imune.
	 */
	_300(CST_Grupo._SN102),
	/**
	 * N�o tributada pelo Simples nacional.
	 */
	_400(CST_Grupo._SN102),
	/**
	 * ICMS cobrado anteriormente por ST.
	 */
	_500(CST_Grupo._SN500),
	/**
	 * Outros.
	 */
	_900(CST_Grupo._SN900);
	
	private C_ICMS(CST_Grupo cstGrupo) {
		this.cstGrupo = cstGrupo;		
	}
	
	private CST_Grupo cstGrupo;
	
	/**
	 * Grupo de tributa��o ICMS.
	 */
	public CST_Grupo getCstGrupo() {
		return cstGrupo;
	}
	
	/**
	 * Grupo de tributa��o ICMS.
	 */
	public String getGroup() {
		return getCstGrupo().getGroup();
	}
	
	/**
	 * Verdadeiro se isento.
	 */
	public boolean isIsento() {
		return getCstGrupo().isIsento();
	}

	/**
	 * Verdadeiro se inclui substitui��o tribut�ria.
	 */
	public boolean isST() {
		return getCstGrupo().isST();
	}

	/**
	 * Verdadeiro se simples nacional.
	 */
	public boolean isSimplesNacional() {
		return getCstGrupo().isSimplesNacional();
	}

	/**
	 * Verdadeiro se a tag � requerida neste CST.
	 * 
	 * @param tag
	 */
	public boolean isRequiredTag(String tag) {
		return getCstGrupo().isRequiredTag(tag);
	}

	/**
	 * Representa��o do c�digo do ICMS.
	 * 
	 * <p>
	 * Substitu�do (overriden) para eliminar o primeiro
	 * caractere da defini��o.
	 * </p>
	 */
	public String toString() {
		return super.toString().substring(1);
	}
}
