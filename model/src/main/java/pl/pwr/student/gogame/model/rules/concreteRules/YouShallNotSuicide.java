package pl.pwr.student.gogame.model.rules.concreteRules;

import java.util.ArrayList;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.rules.ComplexRule;

public class YouShallNotSuicide extends ComplexRule {

  public YouShallNotSuicide() {
    subRuleSet = new ArrayList<>();
    subRuleSet.add(new YouMaySuicideIfYouKillOponent());
  }

  @Override
  protected Boolean meetsOwnRule(Board board, int x, int y, Team team) {
    Team previousTeam = board.getField(x, y).getTeam();
    board.getField(x, y).setTeam(team);

    if (board.getField(x, y).isBreathless()) {
      board.getField(x, y).setTeam(previousTeam);
      return false;
    }

    board.getField(x, y).setTeam(previousTeam);
    return true;
  }
}
