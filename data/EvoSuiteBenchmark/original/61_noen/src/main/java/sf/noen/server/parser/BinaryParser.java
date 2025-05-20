package sf.noen.server.parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Teemu Kanstrén
 */
public class BinaryParser implements Runnable {
  private final InputStream in;
  private boolean running = true;

  public BinaryParser(InputStream in) {
    this.in = new BufferedInputStream(in);
  }

  //parse each msg andstore to db.. is there some point to do this actually? it becomes the same as the python one
  //and implementing anything else is hard...
  public void stop() {
    running = false;
  }

  public void run() {
    while (running) {
      try {
        int b = in.read();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public int parseInteger() {
    throw new RuntimeException("Not implemented");
  }

  public boolean parseBoolean() {
    throw new RuntimeException("Not implemented");
  }

  public boolean parseByte() {
    throw new RuntimeException("Not implemented");
  }

  public boolean parseText() {
    throw new RuntimeException("Not implemented");
  }
}
