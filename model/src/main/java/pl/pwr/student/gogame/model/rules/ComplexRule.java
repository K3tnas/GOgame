package pl.pwr.student.gogame.model.rules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.Move;

public abstract class ComplexRule implements Rule {
  private RuleSet subRuleSet;

  public ComplexRule() {
    this.subRuleSet = new RuleSet();
  }

  protected abstract boolean meetsOwnRule(Board board, Move move);

  public abstract boolean meetsRule(Board board, Move move);
}
