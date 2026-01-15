package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.utilities.GameInfo;

/** BLackTurn */
public class BlackTurn extends GameState {

  public BlackTurn(GameInfo gameInfo) {
    super(gameInfo);
    this.team = Team.BLACK;
  }
}
