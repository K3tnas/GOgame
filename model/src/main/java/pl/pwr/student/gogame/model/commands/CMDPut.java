package pl.pwr.student.gogame.model.commands;

public class CMDPut extends Command {
  public int x;
  public int y;

  public CMDPut(int x, int y, int playerId) {
    super(CommandType.PUT, playerId);
    this.x = x;
    this.y = y;
  }
}
