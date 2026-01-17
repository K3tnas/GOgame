package pl.pwr.student.gogame.model.utilities;

import java.util.ArrayList;
import pl.pwr.student.gogame.model.board.Team;

public class ProposedDeadFields {

  private final ArrayList<int[]> whiteStonesToKill, blackStonesToKill;

  public ProposedDeadFields() {
    whiteStonesToKill = new ArrayList<>();
    blackStonesToKill = new ArrayList<>();
  }

  public void addStoneToKill(int x, int y, Team team) {
    int[] xy = {x, y};
    switch (team) {
      case BLACK:
        blackStonesToKill.add(xy);
        break;
      case WHITE:
        whiteStonesToKill.add(xy);
        break;
      default:
        break;
    }
  }

  public void clear() {
    whiteStonesToKill.clear();
    blackStonesToKill.clear();
  }

  public ArrayList<int[]> getBlackStonesToKill() {
    return blackStonesToKill;
  }

  public ArrayList<int[]> getWhiteStonesToKill() {
    return whiteStonesToKill;
  }
}
