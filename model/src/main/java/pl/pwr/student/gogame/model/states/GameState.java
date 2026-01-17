package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.utilities.GameInfo;
import pl.pwr.student.gogame.model.utilities.Player;

public abstract class GameState {
  protected final GameInfo gameInfo;

  public GameState(GameInfo gameInfo) {
    this.gameInfo = gameInfo;
  }

  public GameState putStone(int x, int y, String id) {
    return this;
  }

  public GameState pass(String id) {
    return this;
  }

  public GameState acceptProposition(String id) {
    return this;
  }

  public GameState declineProposition(String id) {
    return this;
  }

  public GameState proposeDeadField(int x, int y, String id) {
    return this;
  }

  public GameState surrender(String id) {
    if (id == gameInfo.blackPlayer().getId()) return new BlackPlayerWon(gameInfo);
    return new WhitePlayerWon(gameInfo);
  }

  protected final Player getPlayerByTeam(Team team) {
    switch (team) {
      case BLACK:
        return gameInfo.blackPlayer();

      case WHITE:
        return gameInfo.whitePlayer();

      default:
        return null;
    }
  }

  protected final Team getTeamByPlayerId(String id) {
    if (id == gameInfo.whitePlayer().getId()) return Team.WHITE;
    if (id != gameInfo.blackPlayer().getId())
      throw new IllegalArgumentException("No such player in this game");
    return Team.BLACK;
  }
}
