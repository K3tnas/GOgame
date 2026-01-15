package pl.pwr.student.gogame.model.rules;

import java.util.List;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;

public abstract class ComplexRule implements Rule {
  protected List<Rule> subRuleSet;

  protected abstract Boolean meetsOwnRule(Board board, int x, int y, Team team);

  public Boolean meetsRule(Board board, int x, int y, Team team) {
    if (!meetsOwnRule(board, x, y, team)) {
      for (Rule rule : subRuleSet) {
        if (rule.meetsRule(board, x, y, team)) {
          return true;
        }
      }
      return false;
    }

    return true;
  }
}
