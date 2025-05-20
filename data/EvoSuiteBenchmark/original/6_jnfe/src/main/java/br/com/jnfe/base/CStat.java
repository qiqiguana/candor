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
 * Resultados do processamento.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum CStat {
	
	/** 
	 * Autorizado o uso da NF-e 
	 * **/
	_100("Autorizado o uso da NF-e", SitNFe.AUTORIZADA),
	/** 
	 * Cancelamento de NF-e homologado 
	 * **/
	_101("Cancelamento de NF-e homologado", SitNFe.CANCELADA),
	/** 
	 * Inutiliza��o de n�mero homologado 
	 * **/
	_102("Inutiliza��o de n�mero homologado", SitNFe.REJEITADA),
	/** 
	 * Lote recebido com sucesso 
	 * **/
	_103("Lote recebido com sucesso", SitNFe.PENDENTE),
	/** 
	 * Lote processado 
	 * **/
	_104("Lote processado", SitNFe.PENDENTE),
	/** 
	 * Lote em processamento 
	 * **/
	_105("Lote em processamento", SitNFe.PENDENTE),
	/** 
	 * Lote n�o localizado 
	 * **/
	_106("Lote n�o localizado", SitNFe.REJEITADA),
	/** 
	 * Servi�o em Opera��o 
	 * **/
	_107("Servi�o em Opera��o", SitNFe.INDEFINIDA),
	/** 
	 * Servi�o Paralisado Momentaneamente (curto prazo) 
	 * **/
	_108("Servi�o Paralisado Momentaneamente (curto prazo)", SitNFe.INDEFINIDA),
	/** 
	 * Servi�o Paralisado sem Previs�o 
	 * **/
	_109("Servi�o Paralisado sem Previs�o", SitNFe.INDEFINIDA),
	/** 
	 * Uso Denegado 
	 * **/
	_110("Uso Denegado", SitNFe.DENEGADA),
	/** 
	 * Consulta cadastro com uma ocorr�ncia 
	 * **/
	_111("Consulta cadastro com uma ocorr�ncia", SitNFe.INDEFINIDA),
	/** 
	 * Consulta cadastro com mais de uma ocorr�ncia 
	 * **/
	_112("Consulta cadastro com mais de uma ocorr�ncia", SitNFe.INDEFINIDA),
	
	
	_201("O numero m�ximo de numera��o de NF-e a inutilizar ultrapassou o limite", SitNFe.REJEITADA),
	_202("Falha no reconhecimento da autoria ou integridade do arquivo digital", SitNFe.REJEITADA),
	_203("Emissor n�o habilitado para emiss�o da NF-e", SitNFe.REJEITADA),
	_204("Duplicidade de NF-e", SitNFe.REJEITADA),
	_205("NF-e est� denegada na base de dados da SEFAZ", SitNFe.REJEITADA),
	_206("NF-e j� est� inutilizada na Base de dados da SEFAZ", SitNFe.REJEITADA),
	_207("CNPJ do emitente inv�lido", SitNFe.REJEITADA),
	_208("CNPJ do destinat�rio inv�lido", SitNFe.REJEITADA),	
	_209("IE do emitente inv�lida", SitNFe.REJEITADA),
	_210("IE do destinat�rio inv�lida", SitNFe.REJEITADA),
	_211("IE do substituto inv�lida", SitNFe.REJEITADA),
	_212("Data de emiss�o NF-e posterior a data de recebimento", SitNFe.REJEITADA),
	_213("CNPJ-Base do Emitente difere do CNPJ-Base do Certificado Digital", SitNFe.REJEITADA),
	_214("Tamanho da mensagem excedeu o limite estabelecido", SitNFe.REJEITADA),
	_215("Falha no schema XML", SitNFe.REJEITADA),
	_216("Chave de Acesso difere da cadastrada", SitNFe.REJEITADA),
	_217("NF-e n�o consta na base de dados da SEFAZ", SitNFe.REJEITADA),
	_218("NF-e j� esta cancelada na base de dados da SEFAZ", SitNFe.REJEITADA),
	_219("Circula��o da NF-e verificada", SitNFe.REJEITADA),
	_220("NF-e autorizada h� mais de 7 dias (168 horas)", SitNFe.REJEITADA),
	_221("Confirmado o recebimento da NF-e pelo destinat�rio", SitNFe.REJEITADA),
	_222("Protocolo de Autoriza��o de Uso difere do cadastrado", SitNFe.REJEITADA),
	_223("CNPJ do transmissor do lote difere do CNPJ do transmissor da consulta", SitNFe.REJEITADA),
	_224("A faixa inicial � maior que a faixa final", SitNFe.REJEITADA),
	_225("Falha no Schema XML da NFe", SitNFe.REJEITADA),
	_226("C�digo da UF do Emitente diverge da UF autorizadora", SitNFe.REJEITADA),
	_227("Erro na Chave de Acesso - Campo ID", SitNFe.REJEITADA),
	_228("Data de Emiss�o muito atrasada", SitNFe.REJEITADA),
	_229("IE do emitente n�o informada", SitNFe.REJEITADA),
	_230("IE do emitente n�o cadastrada", SitNFe.REJEITADA),
	_231("IE do emitente n�o vinculada ao CNPJ", SitNFe.REJEITADA),
	_232("IE do destinat�rio n�o informada", SitNFe.REJEITADA),
	_233("IE do destinat�rio n�o cadastrada", SitNFe.REJEITADA),
	_234("IE do destinat�rio n�o vinculada ao CNPJ", SitNFe.REJEITADA),
	_235("Inscri��o SUFRAMA inv�lida", SitNFe.REJEITADA),
	_236("Chave de Acesso com d�gito verificador inv�lido", SitNFe.REJEITADA),
	_237("CPF do destinat�rio inv�lido", SitNFe.REJEITADA),
	_238("Cabe�alho - Vers�o do arquivo XML superior a Vers�o vigente", SitNFe.REJEITADA),
	_239("Cabe�alho - Vers�o do arquivo XML n�o suportada", SitNFe.REJEITADA),
	_240("Cancelamento/Inutiliza��o - Irregularidade Fiscal do Emitente", SitNFe.REJEITADA),
	_241("Um n�mero da faixa j� foi utilizado", SitNFe.REJEITADA),
	_242("Cabe�alho - Falha no Schema XML", SitNFe.REJEITADA),
	_243("XML Mal Formado", SitNFe.REJEITADA),
	_244("CNPJ do Certificado Digital difere do CNPJ da Matriz e do CNPJ do Emitente", SitNFe.REJEITADA),
	_245("CNPJ Emitente n�o cadastrado", SitNFe.REJEITADA),
	_246("CNPJ Destinat�rio n�o cadastrado", SitNFe.REJEITADA),
	_247("Sigla da UF do Emitente diverge da UF autorizadora", SitNFe.REJEITADA),
	_248("UF do Recibo diverge da UF autorizadora", SitNFe.REJEITADA),
	_249("UF da Chave de Acesso diverge da UF autorizadora", SitNFe.REJEITADA),
	_250("UF diverge da UF autorizadora", SitNFe.REJEITADA),
	_251("UF/Munic�pio destinat�rio n�o pertence a SUFRAMA", SitNFe.REJEITADA),
	_252("Ambiente informado diverge do Ambiente de recebimento", SitNFe.REJEITADA),
	_253("Digito Verificador da chave de acesso composta inv�lida", SitNFe.REJEITADA),
	_254("NF-e complementar n�o possui NF referenciada", SitNFe.REJEITADA),
	_255("NF-e complementar possui mais de uma NF referenciada", SitNFe.REJEITADA),
	_256("Uma NF-e da faixa j� est� inutilizada na Base de dados da SEFAZ", SitNFe.REJEITADA),
	_257("Solicitante n�o habilitado para emiss�o da NF-e", SitNFe.REJEITADA),
	_258("CNPJ da consulta inv�lido", SitNFe.REJEITADA),
	_259("CNPJ da consulta n�o cadastrado como contribuinte na UF", SitNFe.REJEITADA),
	_260("IE da consulta inv�lida", SitNFe.REJEITADA),
	_261("IE da consulta n�o cadastrada como contribuinte na UF", SitNFe.REJEITADA),
	_262("UF n�o fornece consulta por CPF", SitNFe.REJEITADA),
	_263("CPF da consulta inv�lido", SitNFe.REJEITADA),
	_264("CPF da consulta n�o cadastrado como contribuinte na UF", SitNFe.REJEITADA),
	_265("Sigla da UF da consulta difere da UF do Web Service", SitNFe.REJEITADA),
	_266("S�rie utilizada n�o permitida no Web Service", SitNFe.REJEITADA),
	_267("NF Complementar referencia uma NF-e inexistente", SitNFe.REJEITADA),
	_268("NF Complementar referencia uma outra NF-e Complementar", SitNFe.REJEITADA),
	_269("CNPJ Emitente da NF Complementar difere do CNPJ da NF Referenciada", SitNFe.REJEITADA),
	_270("C�digo Munic�pio do Fato Gerador: d�gito inv�lido", SitNFe.REJEITADA),
	_271("C�digo Munic�pio do Fato Gerador: difere da UF do emitente", SitNFe.REJEITADA),
	_272("C�digo Munic�pio do Emitente: d�gito inv�lido", SitNFe.REJEITADA),
	_273("C�digo Munic�pio do Emitente: difere da UF do emitente", SitNFe.REJEITADA),
	_274("C�digo Munic�pio do Destinat�rio: d�gito inv�lido", SitNFe.REJEITADA),
	_275("C�digo Munic�pio do Destinat�rio: difere da UF do Destinat�rio", SitNFe.REJEITADA),
	_276("C�digo Munic�pio do Local de Retirada: d�gito inv�lido", SitNFe.REJEITADA),
	_277("C�digo Munic�pio do Local de Retirada: difere da UF do Local de Retirada", SitNFe.REJEITADA),
	_278("C�digo Munic�pio do Local de Entrega: d�gito inv�lido", SitNFe.REJEITADA),
	_279("C�digo Munic�pio do Local de Entrega: difere da UF do Local de Entrega", SitNFe.REJEITADA),
	_280("Certificado Transmissor inv�lido", SitNFe.REJEITADA),
	_281("Certificado Transmissor Data Validade", SitNFe.REJEITADA),
	_282("Certificado Transmissor sem CNPJ", SitNFe.REJEITADA),
	_283("Certificado Transmissor - erro Cadeia de Certifica��o", SitNFe.REJEITADA),
	_284("Certificado Transmissor revogado", SitNFe.REJEITADA),
	_285("Certificado Transmissor difere ICP-Brasil", SitNFe.REJEITADA),
	_286("Certificado Transmissor erro no acesso a LCR", SitNFe.REJEITADA),
	_287("C�digo Munic�pio do FG - ISSQN: d�gito inv�lido", SitNFe.REJEITADA),
	_288("C�digo Munic�pio do FG - Transporte: d�gito inv�lido", SitNFe.REJEITADA),
	_289("C�digo da UF informada diverge da UF solicitada", SitNFe.REJEITADA),
	_290("Certificado Assinatura inv�lido", SitNFe.REJEITADA),
	_291("Certificado Assinatura Data Validade", SitNFe.REJEITADA),
	_292("Certificado Assinatura sem CNPJ", SitNFe.REJEITADA),
	_293("Certificado Assinatura - erro Cadeia de Certifica��o", SitNFe.REJEITADA),
	_294("Certificado Assinatura revogado", SitNFe.REJEITADA),
	_295("Certificado Assinatura difere ICP-Brasil", SitNFe.REJEITADA),
	_296("Certificado Assinatura erro no acesso a LCR", SitNFe.REJEITADA),
	_297("Assinatura difere do calculado", SitNFe.REJEITADA),
	_298("Assinatura difere do padr�o do Projeto", SitNFe.REJEITADA),
	_299("XML da �rea de cabe�alho com codifica��o diferente de UTF-8", SitNFe.REJEITADA),
	_401("CPF do remetente inv�lido", SitNFe.REJEITADA),
	_402("XML da �rea de dados com codifica��o diferente de UTF-8", SitNFe.REJEITADA),
	_403("O grupo de informa��es da NF-e avulsa � de uso exclusivo do Fisco", SitNFe.REJEITADA),
	_404("Uso de prefixo de namespace n�o permitido", SitNFe.REJEITADA),
	_405("C�digo do pa�s do emitente: d�gito inv�lido", SitNFe.REJEITADA),
	_406("C�digo do pa�s do destinat�rio: d�gito inv�lido", SitNFe.REJEITADA),
	_407("O CPF s� pode ser informado no campo emitente para a NF-e avulsa", SitNFe.REJEITADA),
	_453("Ano de inutiliza��o n�o pode ser superior ao Ano atual", SitNFe.REJEITADA),
	_454("Ano de inutiliza��o n�o pode ser inferior a 2006", SitNFe.REJEITADA),
	_478("Local da entrega n�o informado para faturamento direto de ve�culos novos", SitNFe.REJEITADA),
	_999("Erro n�o catalogado (informar a mensagem de erro capturado no tratamento da exce��o)", SitNFe.REJEITADA),
	_301("Uso Denegado : Irregularidade fiscal do emitente", SitNFe.DENEGADA),
	_302("Uso Denegado : Irregularidade fiscal do destinat�rio", SitNFe.DENEGADA);
	
	
	private CStat(String xMotivo, SitNFe sitNFe) {
		this.xMotivo = xMotivo;
		this.sitNFe = sitNFe;
	}
	
	private String xMotivo;
	private SitNFe sitNFe;
	
	/**
	 * Representa��o do resultado do processamento.
	 * 
	 * <p>
	 * Substitu�do (overriden) para eliminar o primeiro
	 * caractere da defini��o.
	 * </p>
	 */
	public String toString() {
		return super.toString().substring(1);
	}
	
	/**
	 * Descri��o do resultado do processamento.
	 */
	public String getMotivo() {
		return this.xMotivo;
	}
	
	/**
	 * Situa��o associada a cStat.
	 */
	public SitNFe getSitNFe() {
		return sitNFe;
	}

}
