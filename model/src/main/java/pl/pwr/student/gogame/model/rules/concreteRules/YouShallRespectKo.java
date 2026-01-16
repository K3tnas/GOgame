package pl.pwr.student.gogame.model.rules.concreteRules;

import java.util.ArrayList;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Field;
import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.rules.Rule;

public class YouShallRespectKo implements Rule {
  static final int[][] neighbours = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

  @Override
  public Boolean meetsRule(Board board, int x, int y, Team team) {
    final int size = board.getSize();
    final Team[][] lastState = board.getYourPrevState();
    final ArrayList<Field> toKill = new ArrayList<>();
    Board tempBoard = new Board(size);

    for (int i = 1; i <= size; i++) {
      for (int j = 1; j <= size; j++) {
        tempBoard.getField(i, j).setTeam(board.getField(i, j).getTeam());
      }
    }

    tempBoard.getField(x, y).setTeam(team);

    for (int i = 1; i <= size; i++) {
      for (int j = 1; j <= size; j++) {
        var f = tempBoard.getField(i, j);
        if (f.getTeam() == Team.EMPTY) continue;
        if (f.isBreathless() && f.getTeam() != team) toKill.add(f);
      }
    }

    if (toKill.size() == 0) return true;

    for (Field f : toKill) {
      f.setTeam(Team.EMPTY);
    }

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (tempBoard.getField(i + 1, j + 1).getTeam() != lastState[i][j]) return true;
      }
    }

    return false;
  }
}
