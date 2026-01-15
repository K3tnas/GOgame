package pl.pwr.student.gogame.model.rules;

import java.util.ArrayList;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;

public class Ruleset {

  private final ArrayList<Rule> ruleset;

  public Ruleset() {
    ruleset = new ArrayList<>();
  }

  public boolean meetsRules(Board board, int x, int y, Team team) {
    for (Rule r : ruleset) {
      if (!r.meetsRule(board, x, y, team)) {
        return false;
      }
    }

    return true;
  }

  public Ruleset addRule(Rule rule) {
    ruleset.add(rule);
    return this;
  }
}
