package pl.pwr.student.gogame.model.commands;

public class CMDPass extends Command {
  public CMDPass(int playerId) {
    super(CommandType.PASS, playerId);
  }
}
