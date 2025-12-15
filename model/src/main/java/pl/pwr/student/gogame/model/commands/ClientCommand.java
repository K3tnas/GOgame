package pl.pwr.student.gogame.model.commands;

import pl.pwr.student.gogame.model.exceptions.MangledMessageException;

public abstract class ClientCommand {
  public final CommandType commandType;
  public Integer playerId;

  public ClientCommand(CommandType cmdType, Integer playerId) {
    this.commandType = cmdType;
    this.playerId = playerId;
  }

  public abstract String toString();

  public static ClientCommand fromString(String in) throws MangledMessageException {
    if (in.length() == 0) {
      throw new MangledMessageException("Empty message cannot be parsed to Command");
    }
    String[] arguments = in.split(",");

    if (arguments.length < 2) {
      throw new MangledMessageException("Message must follow [PUT,PASS],playerId(,putX,putY) format");
    }

    String commandTypeName = arguments[0];
    Integer playerId;
    try {
      playerId = Integer.parseInt(arguments[1]);
    } catch (NumberFormatException e) {
      throw new MangledMessageException("Player ID must be decimal integer");
    }

    ClientCommand cmd;
    if ("PUT".equals(commandTypeName)) {
      if (arguments.length < 4) {
        throw new MangledMessageException("Missing position (x,y) arguments in PUT command");
      }

      Integer x, y;
      try {
        x = Integer.parseInt(arguments[2]);
        y = Integer.parseInt(arguments[3]);
      } catch (NumberFormatException e) {
        throw new MangledMessageException("x,y position arguments must be decimal integers");
      }

      cmd = new CMDPut(x, y, playerId);
      return cmd;
    }

    if ("PASS".equals(commandTypeName)) {
      cmd = new CMDPass(playerId);
      return cmd;
    }

    throw new MangledMessageException("Unknown CommandType name: " + arguments[0]);
  }
}
