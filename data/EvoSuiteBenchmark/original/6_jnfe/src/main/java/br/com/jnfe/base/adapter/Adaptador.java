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



/**
 * Estratégia para adaptar a interface local de um objeto NFe 
 * (i.e., o objeto utilizado na camada de persistência) com aquela
 * definida pelos pacotes PLxxx.
 * 
 * @param <P> o objeto persistente.
 * @param <X> o tipo capaz de gerar a saída desejada, tal como Result, OutputStream, etc.
 *  
 * @author Mauricio Fernandes de Castro
 */
public interface Adaptador<P, X> {
	
	/**
	 * Cria um objeto que pode ser usado para envio.
	 * 
	 * @param obj
	 * @throws Exception 
	 */
	public X adapta(P obj) throws Exception;

}
