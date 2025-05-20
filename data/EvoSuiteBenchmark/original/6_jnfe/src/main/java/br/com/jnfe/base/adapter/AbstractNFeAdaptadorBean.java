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

package br.com.jnfe.base.adapter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Classe base para os beans de adaptadores.
 * 
 * <p>
 * Os esquemas reconhecidos pelos servi�os das diversas secretarias de
 * fazenda est�o sujeitos a revis�es frequentes, publicadas atrav�s de pacotes
 * de libera��o (PLxxx). Para garantir alguma estabilidade ao modelo de dom�nio
 * adotado pelo jNFe, � necess�rio um conjunto de adaptadores, cuja responsabilidade
 * passa a ser traduzir "do" modelo liberado em um certo PLxxx "para" o modelo de 
 * dom�nio jNFe, ou vice-versa.
 * </p>
 * 
 * @author Mauricio Fernandes de Castro
 */
public abstract class AbstractNFeAdaptadorBean implements InitializingBean {
	
	private Format formatador2Digitos;
	private Format formatador3Digitos;
	private Format formatador4Digitos;
	private char separadorDecimal = '.';
	private RoundingMode modoDeArredondamento;

	/**
	 * Formatador para n�meros com 2 d�gitos
	 */
	public Format getFormatador2Digitos() {
		return this.formatador2Digitos;
	}
	protected void setFormatador2Digitos(Format formatador2Digitos) {
		this.formatador2Digitos = formatador2Digitos;
	}
	
	/**
	 * Formatador para n�meros com 3 d�gitos
	 */
	public Format getFormatador3Digitos() {
		return this.formatador3Digitos;
	}
	protected void setFormatador3Digitos(Format formatador3Digitos) {
		this.formatador3Digitos = formatador3Digitos;
	}
	
	/**
	 * Formatador para n�meros com 4 d�gitos
	 */
	public Format getFormatador4Digitos() {
		return this.formatador4Digitos;
	}
	protected void setFormatador4Digitos(Format formatador4Digitos) {
		this.formatador4Digitos = formatador4Digitos;
	}
	
	/**
	 * O separador decimal a ser usado.
	 * 
	 * <p>
	 * Caso n�o seja definido, ser� '.'.
	 * </p>
	 */
	public char getSeparadorDecimal() {
		return separadorDecimal;
	}
	public void setSeparadorDecimal(char separadorDecimal) {
		this.separadorDecimal = separadorDecimal;
	}

	/**
	 * Modo de arredondamento.
	 * 
	 * <p>
	 * Caso n�o seja definido, ser� RoundingMode.DOWN.
	 * </p>
	 */
	public RoundingMode getModoDeArredondamento() {
		return modoDeArredondamento;
	}
	public void setModoDeArredondamento(RoundingMode modoDeArredondamento) {
		this.modoDeArredondamento = modoDeArredondamento;
	}
	
	/**
	 * Formata com 2 d�gitos.
	 */
	public String f2d(Object numero) {
		if (numero!=null) {
			return getFormatador2Digitos().format(numero);
		}
		return null;
	}

	/**
	 * Formata com 3 d�gitos.
	 */
	public String f3d(Object numero) {
		if (numero!=null) {
			return getFormatador3Digitos().format(numero);
		}
		return null;
	}

	/**
	 * Formata com 4 d�gitos.
	 */
	public String f4d(Object numero) {
		if (numero!=null) {
			return getFormatador4Digitos().format(numero);
		}
		return null;
	}

	/**
	 * Implementa a interface <code>InitializingBean</code> para realizar
	 * a��es ap�s a inicializa��o desta classe pelo contexto Spring.
	 */
	public void afterPropertiesSet() throws Exception {
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(getSeparadorDecimal());
		
		if (getModoDeArredondamento()==null) {
			setModoDeArredondamento(RoundingMode.DOWN);
		}
		
		DecimalFormat d2 = new DecimalFormat("0.00", symbols);
		d2.setRoundingMode(getModoDeArredondamento());
		setFormatador2Digitos(d2);
		
		DecimalFormat d3 = new DecimalFormat("0.000", symbols);
		d3.setRoundingMode(getModoDeArredondamento());
		setFormatador3Digitos(d3);
		
		DecimalFormat d4 = new DecimalFormat("0.0000", symbols);
		d4.setRoundingMode(getModoDeArredondamento());
		setFormatador4Digitos(d4);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Formatadores decimais para 0"+getSeparadorDecimal()+"00 e " +
					"0"+getSeparadorDecimal()+"0000 definidos com arredondamento "+getModoDeArredondamento());
		}
	}
	
	/**
	 * Calcula o d�gito verificador.
	 * 
	 * @param chNFe
	 */
	public int calculaDV(String chNFe) {
		int soma = calculaSomaDV(chNFe);
		int dv = 11 - (soma % 11);
		dv = dv > 9 ? 0 : dv;
		logger.debug("D�gito verificador calculado para {} � {}.", chNFe, dv);
		return dv;
	}

	public int calculaSomaDV(String chNFe) {
		if (chNFe.length() != 43) {
			throw new IllegalArgumentException(
					"Comprimento da chave '"+chNFe+"' precisa ser 43, mas � "
							+ chNFe.length());
		}
		int soma = 0;
		for (int i = 0; i < 43; i++) {
			soma += Character.getNumericValue(chNFe.charAt(i))
					* convertePosPeso(i + 1, 43);
		}
		return soma;
	}

	public int convertePosPeso(int posicao, int comprimento) {
		return ((comprimento - posicao) % 8) + 2;
	}

	private static final Logger logger = LoggerFactory.getLogger(AbstractNFeAdaptadorBean.class);
	
}
