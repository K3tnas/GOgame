package pl.pwr.student.gogame.model.commands;

public class CMDMove extends Command {
  public int x;
  public int y;

  public CMDMove(int x, int y, Boolean isFromBlackPlayer) {
    super(CommandType.MOVE, isFromBlackPlayer);
    this.x = x;
    this.y = y;
  }
}
