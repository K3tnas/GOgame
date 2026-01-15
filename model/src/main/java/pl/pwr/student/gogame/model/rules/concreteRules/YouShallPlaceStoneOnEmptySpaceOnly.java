package pl.pwr.student.gogame.model.rules.concreteRules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.rules.Rule;

public class YouShallPlaceStoneOnEmptySpaceOnly implements Rule {

  @Override
  public Boolean meetsRule(Board board, int x, int y, Team team) {
    return board.getField(x, y).getTeam() == Team.EMPTY;
  }
}
