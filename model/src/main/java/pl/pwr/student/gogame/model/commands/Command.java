package pl.pwr.student.gogame.model.commands;

public abstract class Command {
  public CommandType commandType;
  public Boolean isFromBlackPlayer;

  protected Command(CommandType cmdType, Boolean isFromBlackPlayer) {
    this.commandType = cmdType;
    this.isFromBlackPlayer = isFromBlackPlayer;
  }
}
