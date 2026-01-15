package pl.pwr.student.gogame.model.rules.concreteRules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.rules.Rule;

public class YouShallBeInsideTheBoard implements Rule {

  @Override
  public Boolean meetsRule(Board board, int x, int y, Team team) {
    int size = board.getSize();
    return !(x < 1 || x > size || y < 1 || y > size);
  }
}
