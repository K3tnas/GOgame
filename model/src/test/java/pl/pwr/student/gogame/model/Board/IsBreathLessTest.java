package pl.pwr.student.gogame.model.Board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;

public class IsBreathLessTest {
  @Test
  public void simpleBreathlessTest() {
    Board b = new Board(2);

    int[][] stones = {
      {1, 1, Team.BLACK.ordinal()},
      {1, 2, Team.BLACK.ordinal()},
      {2, 2, Team.BLACK.ordinal()},
      {2, 1, Team.WHITE.ordinal()}
    };

    for (int[] s : stones) {
      b.getField(s[0], s[1]).setTeam(Team.values()[s[2]]);
    }

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertTrue(b.getField(2, 1).isBreathless());
  }

  @Test
  public void advancedBreathlessTest() {
    Board b = new Board(9);

    int[][] black = {{3, 3}, {3, 4}, {4, 3}, {4, 4}};

    int[][] white = {
      {2, 3}, {2, 4},
      {3, 2}, {3, 5},
      {4, 2}, {4, 5},
      {5, 3}, {5, 4}
    };

    for (int[] p : black) {
      b.getField(p[0], p[1]).setTeam(Team.BLACK);
    }

    for (int[] p : white) {
      b.getField(p[0], p[1]).setTeam(Team.WHITE);
    }

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertTrue(b.getField(3, 3).isBreathless());
    assertTrue(b.getField(4, 3).isBreathless());
    assertTrue(b.getField(3, 4).isBreathless());
    assertTrue(b.getField(4, 4).isBreathless());
  }

  @Test
  public void singleStoneOneLibertyTest() {
    Board b = new Board(3);

    b.getField(2, 2).setTeam(Team.BLACK);
    b.getField(2, 1).setTeam(Team.WHITE);
    b.getField(1, 2).setTeam(Team.WHITE);
    b.getField(3, 2).setTeam(Team.WHITE);

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertFalse(b.getField(2, 2).isBreathless());
  }

  @Test
  public void singleStoneCapturedTest() {
    Board b = new Board(3);

    b.getField(2, 2).setTeam(Team.BLACK);
    b.getField(2, 1).setTeam(Team.WHITE);
    b.getField(1, 2).setTeam(Team.WHITE);
    b.getField(2, 3).setTeam(Team.WHITE);
    b.getField(3, 2).setTeam(Team.WHITE);

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertTrue(b.getField(2, 2).isBreathless());
  }

  @Test
  public void groupWithEyeTest() {
    Board b = new Board(5);

    int[][] black = {{2, 2}, {2, 3}, {2, 4}, {3, 2}, {3, 4}, {4, 2}, {4, 3}, {4, 4}};

    for (int[] p : black) {
      b.getField(p[0], p[1]).setTeam(Team.BLACK);
    }

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertFalse(b.getField(2, 2).isBreathless());
  }

  @Test
  public void falseEyeTest() {
    Board b = new Board(5);

    int[][] black = {{2, 2}, {2, 3}, {2, 4}, {3, 2}, {3, 4}, {4, 2}, {4, 3}, {4, 4}};

    for (int[] p : black) {
      b.getField(p[0], p[1]).setTeam(Team.BLACK);
    }

    b.getField(3, 3).setTeam(Team.WHITE);

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertTrue(b.getField(3, 3).isBreathless());
  }
}
