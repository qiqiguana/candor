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
 * Tipo de s�rie.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum TpSerie {
	
	/**
	 * Zero significa s�rie �nica
	 */
	UNICA(0, 1),
	/**
	 * S�rie normal
	 */
	NORMAL(1, 900),
	/**
	 * Reservado para para emiss�o em contig�ncia na RFB.
	 */
	CONTINGENCIA(900, 1000);
	
	private TpSerie(int serieIni, int serieFin) {
		this.serieIni = serieIni;
		this.serieFin = serieFin;
	}
	
	private int serieIni;
	private int serieFin;

	/**
	 * Limite inicial da s�rie.
	 */
	public int getSerieIni() {
		return serieIni;
	}
	
	/**
	 * Limite final da s�rie.
	 */
	public int getSerieFin() {
		return serieFin;
	}
	
	/**
	 * Determina o tipo de s�rie a partir dos valores iniciais e finais 
	 * estabelecidos para ela.
	 * 
	 * @param serie
	 */
	public static TpSerie getTpSerie(int serie) {
		for (TpSerie tpSerie: TpSerie.values()) {
			if (serie >= tpSerie.getSerieIni() && serie < tpSerie.getSerieFin()) {
				return tpSerie;
			}
		}
		throw new IllegalArgumentException("N�o h� tipo de s�rie para esta s�rie: "+serie+".");
	}

}
