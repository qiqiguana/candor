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
	 * Inutilização de número homologado 
	 * **/
	_102("Inutilização de número homologado", SitNFe.REJEITADA),
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
	 * Lote não localizado 
	 * **/
	_106("Lote não localizado", SitNFe.REJEITADA),
	/** 
	 * Serviço em Operação 
	 * **/
	_107("Serviço em Operação", SitNFe.INDEFINIDA),
	/** 
	 * Serviço Paralisado Momentaneamente (curto prazo) 
	 * **/
	_108("Serviço Paralisado Momentaneamente (curto prazo)", SitNFe.INDEFINIDA),
	/** 
	 * Serviço Paralisado sem Previsão 
	 * **/
	_109("Serviço Paralisado sem Previsão", SitNFe.INDEFINIDA),
	/** 
	 * Uso Denegado 
	 * **/
	_110("Uso Denegado", SitNFe.DENEGADA),
	/** 
	 * Consulta cadastro com uma ocorrência 
	 * **/
	_111("Consulta cadastro com uma ocorrência", SitNFe.INDEFINIDA),
	/** 
	 * Consulta cadastro com mais de uma ocorrência 
	 * **/
	_112("Consulta cadastro com mais de uma ocorrência", SitNFe.INDEFINIDA),
	
	
	_201("O numero máximo de numeração de NF-e a inutilizar ultrapassou o limite", SitNFe.REJEITADA),
	_202("Falha no reconhecimento da autoria ou integridade do arquivo digital", SitNFe.REJEITADA),
	_203("Emissor não habilitado para emissão da NF-e", SitNFe.REJEITADA),
	_204("Duplicidade de NF-e", SitNFe.REJEITADA),
	_205("NF-e está denegada na base de dados da SEFAZ", SitNFe.REJEITADA),
	_206("NF-e já está inutilizada na Base de dados da SEFAZ", SitNFe.REJEITADA),
	_207("CNPJ do emitente inválido", SitNFe.REJEITADA),
	_208("CNPJ do destinatário inválido", SitNFe.REJEITADA),	
	_209("IE do emitente inválida", SitNFe.REJEITADA),
	_210("IE do destinatário inválida", SitNFe.REJEITADA),
	_211("IE do substituto inválida", SitNFe.REJEITADA),
	_212("Data de emissão NF-e posterior a data de recebimento", SitNFe.REJEITADA),
	_213("CNPJ-Base do Emitente difere do CNPJ-Base do Certificado Digital", SitNFe.REJEITADA),
	_214("Tamanho da mensagem excedeu o limite estabelecido", SitNFe.REJEITADA),
	_215("Falha no schema XML", SitNFe.REJEITADA),
	_216("Chave de Acesso difere da cadastrada", SitNFe.REJEITADA),
	_217("NF-e não consta na base de dados da SEFAZ", SitNFe.REJEITADA),
	_218("NF-e já esta cancelada na base de dados da SEFAZ", SitNFe.REJEITADA),
	_219("Circulação da NF-e verificada", SitNFe.REJEITADA),
	_220("NF-e autorizada há mais de 7 dias (168 horas)", SitNFe.REJEITADA),
	_221("Confirmado o recebimento da NF-e pelo destinatário", SitNFe.REJEITADA),
	_222("Protocolo de Autorização de Uso difere do cadastrado", SitNFe.REJEITADA),
	_223("CNPJ do transmissor do lote difere do CNPJ do transmissor da consulta", SitNFe.REJEITADA),
	_224("A faixa inicial é maior que a faixa final", SitNFe.REJEITADA),
	_225("Falha no Schema XML da NFe", SitNFe.REJEITADA),
	_226("Código da UF do Emitente diverge da UF autorizadora", SitNFe.REJEITADA),
	_227("Erro na Chave de Acesso - Campo ID", SitNFe.REJEITADA),
	_228("Data de Emissão muito atrasada", SitNFe.REJEITADA),
	_229("IE do emitente não informada", SitNFe.REJEITADA),
	_230("IE do emitente não cadastrada", SitNFe.REJEITADA),
	_231("IE do emitente não vinculada ao CNPJ", SitNFe.REJEITADA),
	_232("IE do destinatário não informada", SitNFe.REJEITADA),
	_233("IE do destinatário não cadastrada", SitNFe.REJEITADA),
	_234("IE do destinatário não vinculada ao CNPJ", SitNFe.REJEITADA),
	_235("Inscrição SUFRAMA inválida", SitNFe.REJEITADA),
	_236("Chave de Acesso com dígito verificador inválido", SitNFe.REJEITADA),
	_237("CPF do destinatário inválido", SitNFe.REJEITADA),
	_238("Cabeçalho - Versão do arquivo XML superior a Versão vigente", SitNFe.REJEITADA),
	_239("Cabeçalho - Versão do arquivo XML não suportada", SitNFe.REJEITADA),
	_240("Cancelamento/Inutilização - Irregularidade Fiscal do Emitente", SitNFe.REJEITADA),
	_241("Um número da faixa já foi utilizado", SitNFe.REJEITADA),
	_242("Cabeçalho - Falha no Schema XML", SitNFe.REJEITADA),
	_243("XML Mal Formado", SitNFe.REJEITADA),
	_244("CNPJ do Certificado Digital difere do CNPJ da Matriz e do CNPJ do Emitente", SitNFe.REJEITADA),
	_245("CNPJ Emitente não cadastrado", SitNFe.REJEITADA),
	_246("CNPJ Destinatário não cadastrado", SitNFe.REJEITADA),
	_247("Sigla da UF do Emitente diverge da UF autorizadora", SitNFe.REJEITADA),
	_248("UF do Recibo diverge da UF autorizadora", SitNFe.REJEITADA),
	_249("UF da Chave de Acesso diverge da UF autorizadora", SitNFe.REJEITADA),
	_250("UF diverge da UF autorizadora", SitNFe.REJEITADA),
	_251("UF/Município destinatário não pertence a SUFRAMA", SitNFe.REJEITADA),
	_252("Ambiente informado diverge do Ambiente de recebimento", SitNFe.REJEITADA),
	_253("Digito Verificador da chave de acesso composta inválida", SitNFe.REJEITADA),
	_254("NF-e complementar não possui NF referenciada", SitNFe.REJEITADA),
	_255("NF-e complementar possui mais de uma NF referenciada", SitNFe.REJEITADA),
	_256("Uma NF-e da faixa já está inutilizada na Base de dados da SEFAZ", SitNFe.REJEITADA),
	_257("Solicitante não habilitado para emissão da NF-e", SitNFe.REJEITADA),
	_258("CNPJ da consulta inválido", SitNFe.REJEITADA),
	_259("CNPJ da consulta não cadastrado como contribuinte na UF", SitNFe.REJEITADA),
	_260("IE da consulta inválida", SitNFe.REJEITADA),
	_261("IE da consulta não cadastrada como contribuinte na UF", SitNFe.REJEITADA),
	_262("UF não fornece consulta por CPF", SitNFe.REJEITADA),
	_263("CPF da consulta inválido", SitNFe.REJEITADA),
	_264("CPF da consulta não cadastrado como contribuinte na UF", SitNFe.REJEITADA),
	_265("Sigla da UF da consulta difere da UF do Web Service", SitNFe.REJEITADA),
	_266("Série utilizada não permitida no Web Service", SitNFe.REJEITADA),
	_267("NF Complementar referencia uma NF-e inexistente", SitNFe.REJEITADA),
	_268("NF Complementar referencia uma outra NF-e Complementar", SitNFe.REJEITADA),
	_269("CNPJ Emitente da NF Complementar difere do CNPJ da NF Referenciada", SitNFe.REJEITADA),
	_270("Código Município do Fato Gerador: dígito inválido", SitNFe.REJEITADA),
	_271("Código Município do Fato Gerador: difere da UF do emitente", SitNFe.REJEITADA),
	_272("Código Município do Emitente: dígito inválido", SitNFe.REJEITADA),
	_273("Código Município do Emitente: difere da UF do emitente", SitNFe.REJEITADA),
	_274("Código Município do Destinatário: dígito inválido", SitNFe.REJEITADA),
	_275("Código Município do Destinatário: difere da UF do Destinatário", SitNFe.REJEITADA),
	_276("Código Município do Local de Retirada: dígito inválido", SitNFe.REJEITADA),
	_277("Código Município do Local de Retirada: difere da UF do Local de Retirada", SitNFe.REJEITADA),
	_278("Código Município do Local de Entrega: dígito inválido", SitNFe.REJEITADA),
	_279("Código Município do Local de Entrega: difere da UF do Local de Entrega", SitNFe.REJEITADA),
	_280("Certificado Transmissor inválido", SitNFe.REJEITADA),
	_281("Certificado Transmissor Data Validade", SitNFe.REJEITADA),
	_282("Certificado Transmissor sem CNPJ", SitNFe.REJEITADA),
	_283("Certificado Transmissor - erro Cadeia de Certificação", SitNFe.REJEITADA),
	_284("Certificado Transmissor revogado", SitNFe.REJEITADA),
	_285("Certificado Transmissor difere ICP-Brasil", SitNFe.REJEITADA),
	_286("Certificado Transmissor erro no acesso a LCR", SitNFe.REJEITADA),
	_287("Código Município do FG - ISSQN: dígito inválido", SitNFe.REJEITADA),
	_288("Código Município do FG - Transporte: dígito inválido", SitNFe.REJEITADA),
	_289("Código da UF informada diverge da UF solicitada", SitNFe.REJEITADA),
	_290("Certificado Assinatura inválido", SitNFe.REJEITADA),
	_291("Certificado Assinatura Data Validade", SitNFe.REJEITADA),
	_292("Certificado Assinatura sem CNPJ", SitNFe.REJEITADA),
	_293("Certificado Assinatura - erro Cadeia de Certificação", SitNFe.REJEITADA),
	_294("Certificado Assinatura revogado", SitNFe.REJEITADA),
	_295("Certificado Assinatura difere ICP-Brasil", SitNFe.REJEITADA),
	_296("Certificado Assinatura erro no acesso a LCR", SitNFe.REJEITADA),
	_297("Assinatura difere do calculado", SitNFe.REJEITADA),
	_298("Assinatura difere do padrão do Projeto", SitNFe.REJEITADA),
	_299("XML da área de cabeçalho com codificação diferente de UTF-8", SitNFe.REJEITADA),
	_401("CPF do remetente inválido", SitNFe.REJEITADA),
	_402("XML da área de dados com codificação diferente de UTF-8", SitNFe.REJEITADA),
	_403("O grupo de informações da NF-e avulsa é de uso exclusivo do Fisco", SitNFe.REJEITADA),
	_404("Uso de prefixo de namespace não permitido", SitNFe.REJEITADA),
	_405("Código do país do emitente: dígito inválido", SitNFe.REJEITADA),
	_406("Código do país do destinatário: dígito inválido", SitNFe.REJEITADA),
	_407("O CPF só pode ser informado no campo emitente para a NF-e avulsa", SitNFe.REJEITADA),
	_453("Ano de inutilização não pode ser superior ao Ano atual", SitNFe.REJEITADA),
	_454("Ano de inutilização não pode ser inferior a 2006", SitNFe.REJEITADA),
	_478("Local da entrega não informado para faturamento direto de veículos novos", SitNFe.REJEITADA),
	_999("Erro não catalogado (informar a mensagem de erro capturado no tratamento da exceção)", SitNFe.REJEITADA),
	_301("Uso Denegado : Irregularidade fiscal do emitente", SitNFe.DENEGADA),
	_302("Uso Denegado : Irregularidade fiscal do destinatário", SitNFe.DENEGADA);
	
	
	private CStat(String xMotivo, SitNFe sitNFe) {
		this.xMotivo = xMotivo;
		this.sitNFe = sitNFe;
	}
	
	private String xMotivo;
	private SitNFe sitNFe;
	
	/**
	 * Representação do resultado do processamento.
	 * 
	 * <p>
	 * Substituído (overriden) para eliminar o primeiro
	 * caractere da definição.
	 * </p>
	 */
	public String toString() {
		return super.toString().substring(1);
	}
	
	/**
	 * Descrição do resultado do processamento.
	 */
	public String getMotivo() {
		return this.xMotivo;
	}
	
	/**
	 * Situação associada a cStat.
	 */
	public SitNFe getSitNFe() {
		return sitNFe;
	}

}
