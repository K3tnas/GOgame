package pl.pwr.student.gogame.model.rules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDMove;

public abstract class ComplexRule implements Rule {
  private RuleSet subRuleSet;

  public ComplexRule() {
    this.subRuleSet = new RuleSet();
  }

  protected abstract Boolean meetsOwnRule(Board board, CMDMove move);

  public abstract Boolean meetsRule(Board board, CMDMove move);
}
