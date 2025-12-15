package pl.pwr.student.gogame.model.commands;

public class CMDMove extends Command {
  public int x;
  public int y;

  public CMDMove(int x, int y, Integer playerId) {
    super(CommandType.PUT, playerId);
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toString'");
  }
}
