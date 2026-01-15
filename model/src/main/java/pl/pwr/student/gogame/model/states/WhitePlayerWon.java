package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.utilities.GameInfo;

/** WhitePlayerWon */
public class WhitePlayerWon extends GameState {

  public WhitePlayerWon(GameInfo gameInfo) {
    super(gameInfo);
  }

  @Override
  public GameState putStone(int x, int y, String id) {
    System.out.println("Bro, game's over");
    return this;
  }

  @Override
  public GameState pass(String id) {
    System.out.println("Bro, game's over");
    return this;
  }
}
