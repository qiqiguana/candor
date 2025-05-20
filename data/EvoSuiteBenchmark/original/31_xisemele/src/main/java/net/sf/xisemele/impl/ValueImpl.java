package net.sf.xisemele.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import net.sf.xisemele.api.Formatter;
import net.sf.xisemele.api.Value;
import net.sf.xisemele.exception.FormatterNotConfiguredException;

/**
 * Implementação de {@link Value}.
 * 
 * @author Carlos Eduardo Coral
 */
class ValueImpl implements Value {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Instância de {@link Factory} usada para obter a instância de {@link DateFormatter}.
    */
   private final Factory factory;
   
   /**
    * Instância de {@link FormatterProvider} que irá fornecer as instâncias de
    * {@link Formatter} para cada tipo solicitado em {@link #asType(Class)}. 
    */
   private final FormatterProvider formatterProvider;
   
   /**
    * Valor que a instância de {@link Value} abstrai.
    */
   private final String value;
   
   /**
    * Cria uma nova instância de {@link ValueImpl}.
    * 
    * @param factory
    *       instância de {@link Factory} usada para obter a instância de {@link DateFormatter}. 
    * 
    * @param provider
    *       instância de {@link FormatterProvider} que {@link ValueImpl} irá usar para obter o formatador para determinado tipo 
    *       especificado no método {@link #asType(Class)}. 
    *          
    * @param value
    *       <code>String</code> contendo o valor que será convertido por {@link ValueImpl}.
    */
   ValueImpl(final Factory factory, final FormatterProvider provider, final String value) {
      this.factory = factory;
      this.formatterProvider = provider;
      this.value = value;
   }
   
   /**
    * {@inheritDoc}
    */
   public Byte asByte() {
      return parse(Byte.class, new Parser<Byte>() {
         public Byte parse() {
            return Byte.valueOf(value);
         }
      });
   }
   
   /**
    * {@inheritDoc}
    */
   public Short asShort() {
      return parse(Short.class, new Parser<Short>() {
        public Short parse() {
           return Short.valueOf(value);
        }
      });
   }
   
   /**
    * {@inheritDoc}
    */
   public Integer asInteger() {
      return parse(Integer.class, new Parser<Integer>() {
         public Integer parse() {
            return Integer.valueOf(value);
         }
      });
   }
   
   /**
    * {@inheritDoc}
    */
   public Long asLong() {
      return parse(Long.class, new Parser<Long>() {
         public Long parse() {
            return Long.valueOf(value);
         }
      });
   }
   
   /**
    * {@inheritDoc}
    */
   public Float asFloat() {
      return parse(Float.class, new Parser<Float>() {
         public Float parse() {
            return Float.valueOf(value);
         }
      });
   }
   
   /**
    * {@inheritDoc}
    */
   public Double asDouble() {
      return parse(Double.class, new Parser<Double>() {
         public Double parse() {
            return Double.valueOf(value);
         }
      });
   }
   
   /**
    * {@inheritDoc}
    */
   public BigInteger asBigInteger() {
      return parse(BigInteger.class, new Parser<BigInteger>() {
         public BigInteger parse() {
            return new BigInteger(value);
         }
      });
   }
   
   /**
    * {@inheritDoc}
    */
   public BigDecimal asBigDecimal() {
      return parse(BigDecimal.class, new Parser<BigDecimal>() {
         public BigDecimal parse() {
            return new BigDecimal(value);
         }
      });
   }
   
   /**
    * {@inheritDoc}
    */
   public Boolean asBoolean() {
      return parse(Boolean.class, new Parser<Boolean>() {
         public Boolean parse() {
            return Boolean.valueOf(value);
         }
      });
   }
   
   /**
    * {@inheritDoc}
    */
   public String asString() {
      return parse(String.class, new Parser<String>() {
         public String parse() {
            return value;
         }
      });
   }

   /**
    * {@inheritDoc}
    */
   public Date asDate() {
      return parse(Date.class, new Parser<Date>() {
         public Date parse() {
            throw new InternalError();
         }
      });
   }
   
   /**
    * {@inheritDoc}
    */
   public Date asDate(final String pattern) {
      return (Date) factory.createDateFormatter(pattern).parse(value);
   }
   
   /**
    * {@inheritDoc}
    */
   public <T> T asType(final Class<T> type) throws FormatterNotConfiguredException {
      return parse(type, new Parser<T>() {
         public T parse() {
            throw new FormatterNotConfiguredException(type);
         }
      });
   }
   
   /**
    * Retorna mesmo valor que {@link #asString()}.
    * 
    * @return
    *       <code>java.lang.String</code> contendo o valor de {@link ValueImpl}.
    */
   @Override
   public String toString() {
      return value;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o) {
      if (o instanceof ValueImpl) {
         ValueImpl valueImpl = (ValueImpl) o;
         return value.equals(valueImpl.value);
      }
      return false;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return value.hashCode();
   }
   
   /**
    * Efetua o <i>parse</i> do valor para o tipo especificado caso seja encontrado um {@link Formatter} para esse tipo. 
    * 
    * @param <T>
    *       tipo do {@link Formatter} que será usado para efetuar o <i>parse</i>.
    *        
    * @param type
    *       <code>java.lang.Class</code> correspondente ao tipo do {@link Formatter}.
    *       
    * @param parser
    *       instância de {@link Parser} que será usada caso não encontre um {@link Formatter} para o tipo específico
    *       
    * @return
    *       instância do tipo T correspondente ao valor convertido.
    */
   private <T> T parse(Class<T> type, Parser<T> parser) {
      Formatter<T> formatter = formatterProvider.getFormatter(type);
      if (formatter != null) {
         return (T) formatter.parse(value);
      } else {
         return parser.parse();
      }
   }

   /**
    * Definição de interface para efetuar <i>parse</i> de tipo específico.
    */
   private interface Parser<T> {
      
      /**
       * Efetua o <i>parse</i> do valor para o tipo específico.
       * 
       * @return
       *       instância de T correspondente ao valor convertido.
       */
      T parse();
   }
}

