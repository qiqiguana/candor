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
 * Grupo de tributa��o ICMS.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum CST_Grupo {
	
	/**
	 * Tributada integralmente.
	 */
	_00(false, "CST", "modBC", "vBC", "pICMS", "vICMS"),
	/**
	 * Tributada e com cobran�a do ICMS por substitui��o tribut�ria.
	 */
	_10(true, "CST", "modBC", "vBC", "pICMS", "vICMS", "modBCST"),
	/**
	 * Com redu��o de base de c�lculo.
	 */
	_20(false, "CST", "modBC", "pRedBC", "vBC", "pICMS", "vICMS"),
	/**
	 * Isenta ou n�o tributada e com cobran�a do ICMS por
	 * substitui��o tribut�ria.
	 */
	_30(true, true, "CST", "modBCST", "pMVAST", "pRedBCST", "vBCST", "pICMSST", "vICMSST"),
	/**
	 * Isenta, n�o tributada ou Suspens�o
	 */
	_40(true, false, "CST", "vICMS", "motDesICMS"),
	/**
	 * Diferimento.
	 */
	_51(false, "CST", "modBC", "pRedBC", "vBC", "pICMS", "vICMS"),
	/**
	 * ICMS cobrado anteriormente por substitui��o tribut�ria.
	 */
	_60(true, "CST", "vBCST", "vICMSST", "vBCSTRet", "vICMSSTRet"),
	/**
	 * Com redu��o de base de c�lculo e cobran�a do ICMS por 
	 * substitui��o tribut�ria.
	 */
	_70(true, "CST", "modBC", "pRedBC", "vBC", "pICMS", "vICMS", "modBCST"),
	/**
	 * Outros.
	 */
	_90(true, "CST", "modBC", "vBC", "pRedBC", "pICMS", "vICMS", "modBCST"),
	/**
	 * Partilha do ICMS entre a UF de origem e UF de destino ou a UF definida na legisla��o.
	 */
	_Part(true, "CST", "modBC", "vBC", "pRedBC", "pICMS", "vICMS", "modBCST", "pBCOp"),
	/**
	 * Repasse de ICMS ST retido anteriormente em opera��es estaduais
	 * com repasses atrav�s do substituto tribut�rio.
	 */
	_ST(false, "CST", "vBCSTRet", "vICMSSTRet", "vBCSTDest", "vICMSSTDest"),
	/**
	 * Repasse de ICMS ST retido anteriormente em opera��es estaduais
	 * com repasses atrav�s do substituto tribut�rio.
	 */
	
	_SN101(true, false, "CSOSN", "vBCSTRet", "vICMSSTRet", "vBCSTDest", "vICMSSTDest"),
	/**
	 * Repasse de ICMS ST retido anteriormente em opera��es estaduais
	 * com repasses atrav�s do substituto tribut�rio.
	 */
	_SN102(true, false, "CSOSN", "vBCSTRet", "vICMSSTRet", "vBCSTDest", "vICMSSTDest"),
	/**
	 * Repasse de ICMS ST retido anteriormente em opera��es estaduais
	 * com repasses atrav�s do substituto tribut�rio.
	 */
	_SN201(true, false, "CSOSN", "vBCSTRet", "vICMSSTRet", "vBCSTDest", "vICMSSTDest"),
	/**
	 * Repasse de ICMS ST retido anteriormente em opera��es estaduais
	 * com repasses atrav�s do substituto tribut�rio.
	 */
	_SN202(true, false, "CSOSN", "vBCSTRet", "vICMSSTRet", "vBCSTDest", "vICMSSTDest"),
	/**
	 * Repasse de ICMS ST retido anteriormente em opera��es estaduais
	 * com repasses atrav�s do substituto tribut�rio.
	 */
	_SN500(true, false, "CSOSN", "vBCSTRet", "vICMSSTRet", "vBCSTDest", "vICMSSTDest"),
	/**
	 * Repasse de ICMS ST retido anteriormente em opera��es estaduais
	 * com repasses atrav�s do substituto tribut�rio.
	 */
	_SN900(true, false, "CSOSN", "vBCSTRet", "vICMSSTRet", "vBCSTDest", "vICMSSTDest"),
	
	/**
	 * N�o aplic�vel.
	 */
	_NA(false);
	
	private CST_Grupo(boolean sT, String... requiredTags) {
		this(false, sT, requiredTags);
	}
	
	private CST_Grupo(boolean isento, boolean sT, String... requiredTags) {
		this(false, isento, sT, requiredTags);
	}
	
	private CST_Grupo(boolean simples, boolean isento, boolean sT, String... requiredTags) {
		this.simples = simples;
		this.isento = isento;
		this.sT = sT;
		this.requiredTags = requiredTags;
	}
	
	private boolean simples;
	private boolean isento;
	private boolean sT;
	private String[] requiredTags;
	
	/**
	 * Grupo de tributa��o ICMS.
	 */
	public String getGroup() {
		return toString().substring(1);
	}
	
	/**
	 * Verdadeiro se simples nacional.
	 */
	public boolean isSimplesNacional() {
		return simples;
	}

	/**
	 * Verdadeiro se isento.
	 */
	public boolean isIsento() {
		return isento;
	}

	/**
	 * Verdadeiro se inclui substitui��o tribut�ria.
	 */
	public boolean isST() {
		return sT;
	}

	/**
	 * Verdadeiro se a tag � requerida neste CST.
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
