package pl.pwr.student.gogame.model.rules;

import pl.pwr.student.gogame.model.board.Board;

import java.util.ArrayList;
import java.util.List;

public class RuleSet {

  private List<Rule> ruleSet;

  public RuleSet() {
    this.ruleSet = new ArrayList<Rule>();
  }

  public void addRule(Rule rule) {
    ruleSet.add(rule);
  }

  public boolean meetsRules(Board board, int x, int y, boolean isBlack) {
    for (Rule rule : ruleSet) {
      if (!rule.meetsRule(board, x, y, isBlack)) {
        return false;
      }
    }

    return true;
  }
}
