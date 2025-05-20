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
 * Servi�os disponibilizados pelos portais das Secretarias 
 * de Fazenda Estaduais.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum TpServico {
	
	RECEPCAO("Recep��o"),
	RET_RECEPCAO("Consulta de processamento"),
	CANCELAMENTO("Cancelamento"),
	INUTILIZACAO("Inutiliza��o"),
	CONSULTA_SIT("Consulta situa��o"),
	CONSULTA_SERV("Consulta servi�o"),
	CONSULTA_CAD("Consulta cadastro"),
	RECEPCAO_RPS("Recep��o de lote RPS", 'M');
	
	/**
	 * Construtor.
	 * 
	 * @param nome
	 */
	private TpServico(String nome) {
		this(nome, 'E');
	}
	
	/**
	 * Construtor de origem.
	 * 
	 * @param nome
	 * @param origemDoServico
	 */
	private TpServico(String nome, char origemDoServico) {
		this.nome = nome;
		this.origemDoServico = origemDoServico;
	}
	
	private String nome;
	private char origemDoServico;
	
	/**
	 * Nome atribu�do ao servi�o.
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * Origem do Servi�o, estadual ou municipal (E|M).
	 */
	public char getOrigemDoServico() {
		return origemDoServico;
	}

}
