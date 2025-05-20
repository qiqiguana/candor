package net.sf.xisemele.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import net.sf.xisemele.exception.FormatterNotConfiguredException;

/**
 * Definição de interface que abstrai determinado valor em determinado elemento ou 
 * atributo XML.
 * 
 * <p>
 * Essa interface contém métodos de conversão para os tipos básico Java e para
 * determinado tipo específico através do método {@link #asType(Class)}.
 * </p>
 * 
 * @author Carlos Eduardo Coral.
 */
public interface Value extends Serializable {

   /**
    * Converte o valor para <code>java.lang.Byte</code>.
    * 
    * @return
    *       <code>java.lang.Byte</code> correspondente ao valor de determinado elemento ou atributo XML.
    */
   Byte asByte();

   /**
    * Converte o valor para <code>java.lang.Short</code>.
    * 
    * @return
    *       <code>java.lang.Short</code> correspondente ao valor de determinado elemento ou atributo XML.
    */
   Short asShort();

   /**
    * Converte o valor para <code>java.lang.Integer</code>.
    * 
    * @return
    *       <code>java.lang.Integer</code> correspondente ao valor de determinado elemento ou atributo XML.
    */
   Integer asInteger();

   /**
    * Converte o valor para <code>java.lang.Long</code>.
    * 
    * @return
    *       <code>java.lang.Long</code> correspondente ao valor de determinado elemento ou atributo XML.
    */
   Long asLong();

   /**
    * Converte o valor para <code>java.lang.Float</code>.
    * 
    * @return
    *       <code>java.lang.Float</code> correspondente ao valor de determinado elemento ou atributo XML.
    */
   Float asFloat();

   /**
    * Converte o valor para <code>java.lang.Double</code>.
    * 
    * @return
    *       <code>java.lang.Double</code> correspondente ao valor de determinado elemento ou atributo XML.
    */
   Double asDouble();

   /**
    * Converte o valor para <code>java.math.BigInteger</code>.
    * 
    * @return
    *       <code>java.math.BigInteger</code>  correspondente ao valor de determinado elemento ou atributo XML.
    */
   BigInteger asBigInteger();

   /**
    * Converte o valor para <code>java.math.BigDecimal</code>.
    * 
    * @return
    *       <code>java.math.BigDecimal</code> correspondente ao valor de determinado elemento ou atributo XML.
    */
   BigDecimal asBigDecimal();

   /**
    * Converte o valor para <code>java.lang.Boolean</code>.
    * 
    * @return
    *       <code>java.lang.Boolean</code> correspondente ao valor de determinado elemento ou atributo XML.
    */
   Boolean asBoolean();

   /**
    * Retorna o valor como <code>java.lang.String</code>.
    * 
    * @return
    *       <code>java.lang.String</code> contendo o valor de determinado elemento ou atributo XML.
    */
   String asString();

   /**
    * Converte o valor para <code>java.util.Date</code>.
    * 
    * <p>
    * Esse método espera que o formato do valor do elemento ou atributo XML esteja de acordo com o 
    * padrão configurado em {@link Xisemele} através do método {@link Xisemele#setDatePattern(String)}.
    * </p>
    * 
    * @return
    *       <code>java.util.Date</code> correspondente ao valor de determinado elemento ou atributo XML. 
    */
   Date asDate();
   
   /**
    * Converte o valor para <code>java.util.Date</code> de acordo com o padrão especificado por parâmetro.
    * 
    * @param pattern
    *       <code>java.lang.String</code> contendo o padrão que será aplicado na conversão do valor
    *       para <code>java.util.Date</code>.
    *       
    * @return
    *       <code>java.util.Date</code> correspondente ao valor de determinado elemento ou atributo XML.
    */
   Date asDate(String pattern);

   /**
    * Converte o valor para o tipo especificado por parâmetro.
    * 
    * <p>
    * Esse método espera que um {@link Formatter formatador} correspondente ao tipo especificado
    * por parâmetro tenha sido configurado em {@link Xisemele} através do método {@link Xisemele#setFormatter(Formatter)}.  
    * </p>
    * 
    * @param <T>
    *       <i>Generics</i> correspondente ao tipo a ser convertido.
    *          
    * @param type
    *       <code>java.lang.Class</code> correspondente ao tipo em que o valor deverá ser convertido. 
    *          
    * @return
    *       instância de <code>T</code> correspondente ao valor de determinado elemento ou atributo XML.
    *       
    * @throws FormatterNotConfiguredException
    *       exceção disparada caso não haja um {@link Formatter} configurado para o tipo especificado
    *       por parâmetro.
    */
   <T> T asType(Class<T> type) throws FormatterNotConfiguredException;
}