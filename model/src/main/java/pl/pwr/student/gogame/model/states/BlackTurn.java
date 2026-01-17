package pl.pwr.student.gogame.model.states;

import java.util.ArrayList;
import pl.pwr.student.gogame.model.board.*;
import pl.pwr.student.gogame.model.utilities.GameInfo;
import pl.pwr.student.gogame.model.utilities.Player;

/** BLackTurn */
public class BlackTurn extends GameState {
  private final Team team;

  public BlackTurn(GameInfo gameInfo) {
    super(gameInfo);
    this.team = Team.BLACK;
  }

  public GameState putStone(int x, int y, String id) {
    if (team != getTeamByPlayerId(id)) return this;
    if (!gameInfo.ruleset().meetsRules(gameInfo.board(), x, y, team)) return this;

    gameInfo.board().saveCurrState();
    gameInfo.board().getField(x, y).setTeam(team);
    collectStones();
    getPlayerByTeam(team).setMayPass(true);

    return new WhiteTurn(gameInfo);
  }

  public GameState pass(String id) {
    if (team != getTeamByPlayerId(id) || !getPlayerByTeam(team).mayPass()) return this;

    gameInfo.passHistory().addPass();
    gameInfo.board().saveCurrState();

    if (gameInfo.passHistory().isGameOver()) return new Proposition(gameInfo);

    return new WhiteTurn(gameInfo);
  }

  private void collectStones() {
    final Board board = gameInfo.board();
    final int size = board.getSize();
    final ArrayList<Field> toKill = new ArrayList<>();
    final Player p = getPlayerByTeam(team);
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
      p.addCaptive();
    }
  }
}
