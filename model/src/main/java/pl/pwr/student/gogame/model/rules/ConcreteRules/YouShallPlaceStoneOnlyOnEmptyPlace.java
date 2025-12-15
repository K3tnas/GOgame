package pl.pwr.student.gogame.model.rules.ConcreteRules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.rules.Rule;

public class YouShallPlaceStoneOnlyOnEmptyPlace implements Rule {

  @Override
  public Boolean meetsRule(Board board, int x, int y, boolean isBlack) {
    return board.getStone(x, y) == null;
  }
}
