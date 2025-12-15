package pl.pwr.student.gogame.model.rules;

import pl.pwr.student.gogame.model.board.Board;

public abstract class ComplexRule implements Rule {
  protected RuleSet subRuleSet;

  public ComplexRule() {
    this.subRuleSet = new RuleSet();
  }

  protected abstract Boolean meetsOwnRule(Board board, int x, int y, boolean isBlack);

  public abstract Boolean meetsRule(Board board, int x, int y, boolean isBlack);
}
