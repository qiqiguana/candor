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
 * Interface extraída a partir dos endereços da nFe.
 * 
 * <p>
 * Após a compilação do schema e a correspondente geração de 
 * classes Java para o JAXB, é necessário modificá-las (onde 
 * aparecer a definição de um endereço) para que implementem 
 * esta interface.</p>
 * 
 * @param <U> a classe que representa a UF
 * @author Mauricio Fernandes de Castro
 */
public interface Endereco<U> {

	/**
	 * Logradouro.
	 */
	public abstract String getXLgr();
	public abstract void setXLgr(String value);

	/**
	 * Número.
	 */
	public abstract String getNro();
	public abstract void setNro(String value);

	/**
	 * Complemento.
	 */
	public abstract String getXCpl();
	public abstract void setXCpl(String value);

	/**
	 * Bairro.
	 */
	public abstract String getXBairro();
	public abstract void setXBairro(String value);

	/**
	 * Código do município.
	 */
	public abstract String getCMun();
	public abstract void setCMun(String value);

	/**
	 * Município.
	 */
	public abstract String getXMun();
	public abstract void setXMun(String value);

	/**
	 * Unidade da federação.
	 */
	public abstract U getUF();
	public abstract void setUF(U value);

	/**
	 * CEP.
	 */
	public abstract String getCEP();
	public abstract void setCEP(String value);

	/**
	 * Código do país.
	 */
	public abstract String getCPais();
	public abstract void setCPais(String value);

	/**
	 * País.
	 */
	public abstract String getXPais();
	public abstract void setXPais(String value);

	/**
	 * Fone.
	 */
	public abstract String getFone();
	public abstract void setFone(String value);

}