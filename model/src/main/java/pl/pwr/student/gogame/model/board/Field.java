package pl.pwr.student.gogame.model.board;

import java.util.HashSet;
import java.util.Set;

/** Field */
public class Field {

  private Team team;
  private Field[] neighbourFields;

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
    return isBreathless(new HashSet<>());
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
