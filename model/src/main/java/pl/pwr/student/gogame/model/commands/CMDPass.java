package pl.pwr.student.gogame.model.commands;

public class CMDPass extends ClientCommand {
  public CMDPass(Integer playerId) {
    super(CommandType.PASS, playerId);
  }
  
  public CMDPass() {
    super(CommandType.PASS, null);
  }

  @Override
  public String toString() {
    return this.commandType.name() + "," + this.playerId;
  }
}
