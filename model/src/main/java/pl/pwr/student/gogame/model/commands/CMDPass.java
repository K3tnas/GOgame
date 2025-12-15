package pl.pwr.student.gogame.model.commands;

public class CMDPass extends Command {
  public CMDPass(Integer playerId) {
    super(CommandType.PASS, playerId);
  }

  @Override
  public String toString() {
    return this.commandType.name() + "," + this.playerId;
  }
}
