package pl.pwr.student.gogame.model.rules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;

public interface Rule {
  public Boolean meetsRule(Board board, int x, int y, Team team);
}
