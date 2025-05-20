package geo.google.mapping;
/**
 * A functor class.
 * 
 * @author jliang
 */
public interface Functor<ReturnType,ArgType, ExceptionType extends Exception> {
  public ReturnType execute (ArgType arg) throws ExceptionType;
}
