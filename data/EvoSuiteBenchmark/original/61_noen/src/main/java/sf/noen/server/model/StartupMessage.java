package sf.noen.server.model;

/**
 * @author Teemu Kanstrén
 */
//java bytes are really masked 4 byte integers so we only use integers where bytes are needed..
public class StartupMessage {
  public final int msgId = 0xff;
  private final int byteorder;
  private final int protocolVersion;
  //the following commented fields were removed from version 3
//  private final String projectName;
//  private final String projectVersion;
//  private final String scenarioName;
//  private final String targetName;
  private final String description;
  private final int accuracy;
  private final long timestamp;
  private final int scenarioId;

  public StartupMessage(int byteorder, int protocolVersion, String description, int accuracy, long timestamp, int scenarioId) {
    this.byteorder = byteorder;
    this.protocolVersion = protocolVersion;
    this.description = description;
    this.accuracy = accuracy;
    this.timestamp = timestamp;
    this.scenarioId = scenarioId;
  }


}
