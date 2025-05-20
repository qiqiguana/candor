package geo.google.mapping;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
/**
 * A <code>FunctionChain</code> is simply a chain of functions.
 * When the <code>execute()</code> is call, the argument of <code>execute()</code> 
 * will be passed to the first function in the chain, the returned value of the first 
 * function will be further passed along as the argument of the second function in the chain. 
 * The returned value of the second function will be passed to the third function and so on
 * until the end of the chain.
 * <br />
 * For example:<br />
 * if you have a function <code>Add1</code> that adds 1 to an integer, then you can create a function chain 
 * for adding 2, adding 3 and so on by chaining the <code>Add1</code> function.
 * <br />
 * <pre>
 * Function<Integer, Integer> add1 = new Function<Integer, Integer>(){
 *        public Integer execute(Integer arg) {
 *          return arg+1;
 *        }
 *      };
 * FunctionChain<Integer> add2 = new FunctionChain<Integer>(new Function[]{add1, add1});
 * FunctionChain<Integer> add3 = new FunctionChain<Integer>(add1, add2);
 * </pre>   
 * 
 * @author jliang
 */
@SuppressWarnings("unchecked")
public class FunctionChain<ReturnType>{
  
  private Functor _head = null;
  private FunctionChain _tail = null;
  
  @SuppressWarnings("unchecked")
  public FunctionChain(List<Functor> functions){
    if(CollectionUtils.isNotEmpty(functions)){
      _head =  functions.get(0);
      _tail = new FunctionChain(functions.subList(1, functions.size()));
    }
  }
  
  public FunctionChain(Functor[] functions){
    this(functions, 0);
  }
  
  private FunctionChain(Functor[] functions, int startIndex){
    if(startIndex < functions.length){
      _head = functions[startIndex];
    }
    if(startIndex < functions.length - 1){
      _tail = new FunctionChain(functions, startIndex+1);
    }
  }
  
  public FunctionChain(Functor head, FunctionChain tail) {
    super();
    _head = head;
    _tail = tail;
  }

  public Functor getHead() {
    return _head;
  }

  public void setHead(Functor head) {
    _head = head;
  }

  public FunctionChain getTail() {
    return _tail;
  }

  public void setTail(FunctionChain tail) {
    _tail = tail;
  }

  
  /**
   * Execute function chain with <code>arg</code> as an argument.
   * @throws Throwable 
   */
  @SuppressWarnings("unchecked")
  public ReturnType execute(Object arg) throws Exception{
    Object o = arg;
    if(_head != null){
      o = _head.execute(arg);
    }
    if(_tail != null){
      o = _tail.execute(o);
    }
    return (ReturnType)o;
  }


  @SuppressWarnings("unchecked")
  public static FunctionChain getChain(Functor func){
    return new FunctionChain(func, null);
  }
  
}