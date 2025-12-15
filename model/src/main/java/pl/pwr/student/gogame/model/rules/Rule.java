package pl.pwr.student.gogame.model.rules;

import pl.pwr.student.gogame.model.board.Board;

public interface Rule {

  public Boolean meetsRule(Board board, int x, int y, boolean isBlack);

}
