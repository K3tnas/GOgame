package pl.pwr.student.gogame.model.states;

import java.util.ArrayList;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Field;
import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.utilities.GameInfo;
import pl.pwr.student.gogame.model.utilities.Player;

public abstract class GameState {

  protected final GameInfo gameInfo;
  protected Team team;

  public GameState(GameInfo gameInfo) {
    this.gameInfo = gameInfo;
  }

  public GameState putStone(int x, int y, String id) {
    if (id != getPlayerByTeam(team).getId()) return this;
    if (!gameInfo.ruleset().meetsRules(gameInfo.board(), x, y, team)) return this;

    gameInfo.board().saveCurrState();
    gameInfo.board().getField(x, y).setTeam(team);
    int captives = collectStones();
    getPlayerByTeam(team).addCaptives(captives);

    return (this instanceof WhiteTurn ? new BlackTurn(gameInfo) : new WhiteTurn(gameInfo));
  }

  public GameState pass(String id) {
    if (id != getPlayerByTeam(team).getId()) return this;

    gameInfo.passHistory().addPass();
    gameInfo.board().saveCurrState();

    if (gameInfo.passHistory().isGameOver()) return chooseWinner();

    return (this instanceof WhiteTurn ? new BlackTurn(gameInfo) : new WhiteTurn(gameInfo));
  }

  public GameState surrender(String id) {
    if (id == gameInfo.blackPlayer().getId()) return new BlackPlayerWon(gameInfo);
    return new WhitePlayerWon(gameInfo);
  }

  protected GameState chooseWinner() {

    // TODO:

    return new BlackPlayerWon(gameInfo);
  }

  private int collectStones() {
    final Board board = gameInfo.board();
    final int size = board.getSize();
    final ArrayList<Field> toKill = new ArrayList<>();
    int counter = 0;
    Field temp;

    // WARNING: not optimal
    for (int i = 1; i <= size; i++) {
      for (int j = 1; j <= size; j++) {
        temp = board.getField(i, j);
        if (temp.getTeam() == team || temp.getTeam() == Team.EMPTY) continue;

        if (temp.isBreathless()) {
          toKill.add(temp);
        }
      }
    }

    for (Field f : toKill) {
      f.setTeam(Team.EMPTY);
      counter++;
    }

    return counter;
  }

  private final Player getPlayerByTeam(Team team) {
    switch (team) {
      case BLACK:
        return gameInfo.blackPlayer();

      case WHITE:
        return gameInfo.whitePlayer();

      default:
        return null;
    }
  }
}
