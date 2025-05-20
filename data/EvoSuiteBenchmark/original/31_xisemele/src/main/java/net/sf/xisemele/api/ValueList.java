package net.sf.xisemele.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import net.sf.xisemele.exception.FormatterNotConfiguredException;

/**
 * Definição de interface que abstrai uma lista de {@link Value}.
 * 
 * @author Carlos Eduardo Coral.
 */
public interface ValueList extends Serializable {

   /**
    * Converte os valores para uma lista de <code>java.lang.Byte</code>.
    * 
    * @return
    *       lista de <code>java.lang.Byte</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<Byte> asByte();

   /**
    * Converte os valores para uma lista de <code>java.lang.Short</code>.
    * 
    * @return
    *       lista de <code>java.lang.Short</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<Short> asShort();

   /**
    * Converte os valores para uma lista de <code>java.lang.Integer</code>.
    * 
    * @return
    *       lista de <code>java.lang.Integer</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<Integer> asInteger();

   /**
    * Converte os valores para uma lista de <code>java.lang.Long</code>.
    * 
    * @return
    *       lista de <code>java.lang.Long</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<Long> asLong();

   /**
    * Converte os valores para uma lista de <code>java.lang.Float</code>.
    * 
    * @return
    *       lista de <code>java.lang.Float</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<Float> asFloat();

   /**
    * Converte os valores para uma lista de <code>java.lang.Double</code>.
    * 
    * @return
    *       lista de <code>java.lang.Double</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<Double> asDouble();

   /**
    * Converte os valores para uma lista de <code>java.math.BigInteger</code>.
    * 
    * @return
    *       lista de <code>java.math.BigInteger</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<BigInteger> asBigInteger();

   /**
    * Converte os valores para uma lista de <code>java.math.BigDecimal</code>.
    * 
    * @return
    *       lista de <code>java.math.BigDecimal</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<BigDecimal> asBigDecimal();

   /**
    * Converte os valores para uma lista de <code>java.lang.Boolean</code>.
    * 
    * @return
    *       lista de <code>java.lang.Boolean</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<Boolean> asBoolean();

   /**
    * Converte os valores para uma lista de <code>java.lang.String</code>.
    * 
    * @return
    *       lista de <code>java.lang.String</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<String> asString();

   /**
    * Converte os valores para uma lista de <code>java.util.Date</code>.
    * 
    * @return
    *       lista de <code>java.util.Date</code> contendo os valores correspondentes da
    *       lista de determinados elementos XML.
    */
   List<Date> asDate();
   
   /**
    * Converte os valores para uma lista de <code>java.util.Date</code> de acordo com o padrão especificado
    * por parâmetro.
    * 
    * @param pattern
    *       <code>java.lang.String</code> contendo o padrão que será aplicado na conversão do valor.
    *       
    * @return
    *       lista de <code>java.util.Date</code> contendo os valores correspondentes da lista
    *       de determinados elementos XML.
    */
   List<Date> asDate(String pattern);

   /**
    * Converte os valores para uma lista do tipo especificado por parâmetro.
    * 
    * <p>
    * Esse método espera que um {@link Formatter formatador} correspondente ao tipo especificado
    * por parâmetro tenha sido configurado em {@link Xisemele} através do método {@link Xisemele#setFormatter(Formatter)}.  
    * </p>
    * 
    * @param <T>
    *       <i>Generics</i> correspondente ao tipo que será convertido.
    *       
    * @param type
    *       <code>Class</code> correspondente ao tipo em que os valores serão convertidos.
    *       
    * @return
    *       lista de <code>T</code> contendo valores correspondentes aos elementos XML.
    *     
    * @throws FormatterNotConfiguredException
    *       exceção disparada caso não haja um {@link Formatter} configurado para o tipo especificado
    *       por parâmetro.
    */
   <T> List<T> asType(Class<T> type) throws FormatterNotConfiguredException;
}