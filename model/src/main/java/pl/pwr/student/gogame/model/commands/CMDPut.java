package pl.pwr.student.gogame.model.commands;

public class CMDPut extends ClientCommand {
  public int x;
  public int y;

  public CMDPut(Integer x, Integer y, Integer playerId) {
    super(CommandType.PUT, playerId);
    this.x = x;
    this.y = y;
  }
  
  public CMDPut(Integer x, Integer y) {
    super(CommandType.PUT, null);
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return this.commandType.name() + "," + this.playerId + "," + this.x + "," + this.y;
  }
}
