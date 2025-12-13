package pl.pwr.student.gogame.model.rules.ConcreteRules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Stone;
import pl.pwr.student.gogame.model.commands.Move;
import pl.pwr.student.gogame.model.rules.ComplexRule;

public class YouShallNotSufficate extends ComplexRule {

  @Override
  protected boolean meetsOwnRule(Board board, Move move) {

  }

  @Override
  public boolean meetsRule(Board board, Move move) {
    // TODO: Przyszła implementacja
    return meetsOwnRule(board, move);
  }

}
