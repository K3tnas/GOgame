package pl.pwr.student.gogame.model.Board;

import org.junit.Test;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;

public class BoardTest {

  @Test
  public void printTest() {
    Board board = new Board(19);

    board.getField(1, 1).setTeam(Team.BLACK);
    board.getField(1, 2).setTeam(Team.WHITE);

    System.out.println(board.toString());
  }
}
