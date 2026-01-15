package pl.pwr.student.gogame.model.rules.concreteRules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Field;
import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.rules.Rule;

public class YouMaySuicideIfYouKillOponent implements Rule {

  @Override
  public Boolean meetsRule(Board board, int x, int y, Team team) {
    Team previousTeam = board.getField(x, y).getTeam();
    board.getField(x, y).setTeam(team);

    int size = board.getSize();
    for (int i = 1; i <= size; i++) {
      for (int j = 1; j <= size; j++) {
        Field field = board.getField(i, j);
        if (field.getTeam() != Team.EMPTY && field.getTeam() != team) {
          if (field.isBreathless()) {
            board.getField(x, y).setTeam(previousTeam);
            return true;
          }
        }
      }
    }

    board.getField(x, y).setTeam(previousTeam);
    return false;
  }
}
