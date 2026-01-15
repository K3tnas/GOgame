package pl.pwr.student.gogame.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.builder.StandardGameBuilder;
import pl.pwr.student.gogame.model.states.BlackTurn;
import pl.pwr.student.gogame.model.states.WhiteTurn;
import pl.pwr.student.gogame.model.utilities.Player;

public class GameTest {
  @Test
  public void simpleGameTest() {
    Game g = setup();
    final int[][] moves = {{1, 1}, {1, 2}, {5, 5}, {2, 1}};
    final String wit = g.getGameInfo().whitePlayer().getId();
    final String bit = g.getGameInfo().blackPlayer().getId();
    final Board b = g.getGameInfo().board();
    int c = 0;

    System.out.println(b);
    System.out.println(g.getGameState());

    for (int[] m : moves) {
      String id = (c % 2 == 0 ? wit : bit);
      if (c % 2 == 0) {
        assertTrue(g.getGameState() instanceof WhiteTurn);
      } else {
        assertTrue(g.getGameState() instanceof BlackTurn);
      }
      g.putStone(m[0], m[1], id);
      System.out.println(b);
      System.out.println(g.getGameState());
      c++;
    }
  }

  @Test
  public void gameTest1() {
    Game g = setup();
    final int[][] moves = {{1, 1}, {2, 1}, {1, 2}, {1, 3}, {2, 2}, {2, 3}, {5, 5}, {3, 2}};
    final String wit = g.getGameInfo().whitePlayer().getId();
    final String bit = g.getGameInfo().blackPlayer().getId();
    final Board b = g.getGameInfo().board();
    int c = 0;

    System.out.println(b);
    System.out.println(g.getGameState());

    for (int[] m : moves) {
      String id = (c % 2 == 0 ? wit : bit);
      if (c % 2 == 0) {
        assertTrue(g.getGameState() instanceof WhiteTurn);
      } else {
        assertTrue(g.getGameState() instanceof BlackTurn);
      }
      g.putStone(m[0], m[1], id);
      System.out.println(b);
      System.out.println(g.getGameState());
      c++;
    }
  }

  @Test
  public void gameTest2() {
    Game g = setup();
    final int[][] moves = {{2, 1}, {3, 1}, {1, 2}, {4, 2}, {3, 2}, {3, 3}, {2, 3}, {2, 2}};
    final String wit = g.getGameInfo().whitePlayer().getId();
    final String bit = g.getGameInfo().blackPlayer().getId();
    final Board b = g.getGameInfo().board();
    int c = 0;

    System.out.println(b);
    System.out.println(g.getGameState());

    for (int[] m : moves) {
      String id = (c % 2 == 0 ? wit : bit);
      if (c % 2 == 0) {
        assertTrue(g.getGameState() instanceof WhiteTurn);
      } else {
        assertTrue(g.getGameState() instanceof BlackTurn);
      }
      g.putStone(m[0], m[1], id);
      System.out.println(b);
      System.out.println(g.getGameState());
      c++;
    }

    assertFalse(g.getGameInfo().ruleset().meetsRules(b, 3, 2, Team.WHITE));
    g.putStone(3, 2, wit);

    // illegal move so the state doesnt change
    assertTrue(g.getGameState() instanceof WhiteTurn);
  }

  private Game setup() {
    StandardGameBuilder gb = new StandardGameBuilder();
    gb.addPlayer(new Player("asdf"));
    gb.addPlayer(new Player("zxcv"));
    Game g = gb.buildGame();
    return g;
  }
}
