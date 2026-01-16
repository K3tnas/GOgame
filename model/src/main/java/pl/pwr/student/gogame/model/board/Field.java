package pl.pwr.student.gogame.model.board;

import com.sun.jdi.request.InvalidRequestStateException;
import java.util.HashSet;
import java.util.Set;

/** Field */
public class Field {

  private Team team;
  private transient Field[] neighbourFields;

  public Field() {
    team = Team.EMPTY;
  }

  public void setNeighbours(Field[] neighbourFields) {
    this.neighbourFields = neighbourFields;
  }

  public Team getTeam() {
    return this.team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public boolean isBreathless() {
    if (team == Team.EMPTY) throw new InvalidRequestStateException("this field's empty mate");
    return isBreathless(new HashSet<>());
  }

  public Team whoseTerritory() {
    if (team != Team.EMPTY) throw new InvalidRequestStateException("this field aint't empty mate");
    Team t = whoseTerritory(new HashSet<>());
    return t == null ? Team.EMPTY : t;
  }

  // HACK: ALE JESTEM GOATEM
  private Team whoseTerritory(Set<Field> visited) {
    Team territory = null;
    visited.add(this);

    for (Field neighbor : neighbourFields) {
      if (neighbor.team != Team.EMPTY) {

        if (territory == null) territory = neighbor.team;
        else if (territory != neighbor.team) return Team.EMPTY;

      } else if (!visited.contains(neighbor)) {

        Team nt = neighbor.whoseTerritory(visited);

        if (nt == null) continue;

        if (nt == Team.EMPTY) return Team.EMPTY;

        if (territory == null) territory = nt;
        else if (territory != nt) return Team.EMPTY;
      }
    }

    return territory;
  }

  private boolean isBreathless(Set<Field> visited) {
    visited.add(this);

    for (Field neighbor : neighbourFields) {
      if (neighbor.team == Team.EMPTY) {
        return false;
      }
    }

    for (Field neighbor : neighbourFields) {
      if (neighbor.team == this.team && !visited.contains(neighbor)) {
        if (!neighbor.isBreathless(visited)) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public String toString() {
    switch (team) {
      case Team.EMPTY:
        return "·";
      case Team.WHITE:
        return "●";
      case Team.BLACK:
        return "○";
    }

    return null;
  }
}
