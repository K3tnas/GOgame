package pl.pwr.student.gogame.model.commands;

public class CMDPass extends Command {
  public CMDPass(Boolean isFromBlackPlayer) {
    super(CommandType.PASS, isFromBlackPlayer);
  }
}