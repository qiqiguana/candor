import java.util.Vector;

interface Canonicizable {
    
    public Vector<Character> process(Vector<Character> procText);

}

abstract class Preprocessor implements Canonicizable {

    abstract public Vector<Character> process(Vector<Character> procText);

}
