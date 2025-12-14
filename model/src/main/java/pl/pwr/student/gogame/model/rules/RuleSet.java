package pl.pwr.student.gogame.model.rules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDMove;

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

  public boolean meetsRules(Board board, CMDMove move) {
    for (Rule rule : ruleSet) {
      if (!rule.meetsRule(board, move)) {
        return false;
      }
    }

    return true;
  }
}
