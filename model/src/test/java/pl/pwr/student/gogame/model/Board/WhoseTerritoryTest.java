package pl.pwr.student.gogame.model.Board;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;

public class WhoseTerritoryTest {

  @Test
  public void simpleTest() {
    int size = 5;
    Board b = new Board(size);

    for (int i = 1; i <= size; i++) {
      for (int j = 1; j <= size; j++) {
        assertSame(Team.EMPTY, b.getField(i, j).whoseTerritory());
      }
    }

    int[][] black = {{2, 1}, {2, 2}, {1, 2}};
    int[][] white = {{1, 4}, {2, 4}, {3, 4}, {4, 4}, {4, 3}, {4, 2}, {4, 1}};

    for (int[] p : black) {
      b.getField(p[0], p[1]).setTeam(Team.BLACK);
    }

    for (int[] p : white) {
      b.getField(p[0], p[1]).setTeam(Team.WHITE);
    }

    System.out.println(b);

    int[][] should_be_empty = {{1, 3}, {2, 3}, {3, 3}, {3, 2}, {3, 1}};
    int[][] should_be_black = {{1, 1}};
    int[][] should_be_white = {
      {1, 5}, {2, 5}, {3, 5}, {4, 5}, {5, 5}, {5, 4}, {5, 3}, {5, 2}, {5, 1}
    };

    for (int[] p : should_be_empty) {
      assertSame(Team.EMPTY, b.getField(p[0], p[1]).whoseTerritory());
    }

    for (int[] p : should_be_black) {
      assertSame(Team.BLACK, b.getField(p[0], p[1]).whoseTerritory());
    }

    for (int[] p : should_be_white) {
      assertSame(Team.WHITE, b.getField(p[0], p[1]).whoseTerritory());
    }
  }
}
