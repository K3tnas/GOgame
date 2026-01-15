package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.utilities.GameInfo;

public class WhiteTurn extends GameState {

  public WhiteTurn(GameInfo gameInfo) {
    super(gameInfo);
    this.team = Team.WHITE;
  }
}
