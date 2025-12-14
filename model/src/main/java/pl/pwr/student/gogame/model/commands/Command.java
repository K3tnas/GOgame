package pl.pwr.student.gogame.model.commands;

public abstract class Command {
  public final CommandType commandType;
  public final int playerId;

  protected Command(CommandType cmdType, int playerId) {
    this.commandType = cmdType;
    this.playerId = playerId;
  }
}
