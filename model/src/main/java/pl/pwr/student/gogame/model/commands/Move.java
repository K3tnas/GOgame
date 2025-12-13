package pl.pwr.student.gogame.model.commands;

public class Move extends Command {
  public int x, y;

  public Move(int x, int y) {
    this.x = x;
    this.y = y;
    this.commandType = CommandType.MOVE;
  }
}
