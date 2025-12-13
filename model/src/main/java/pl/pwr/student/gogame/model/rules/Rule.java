package pl.pwr.student.gogame.model.rules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDMove;

public interface Rule {

  public Boolean meetsRule(Board board, CMDMove move);

}
