package pl.pwr.student.gogame.model;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import pl.pwr.student.gogame.model.builder.GameBuilder;
import pl.pwr.student.gogame.model.builder.StandardGameBuilder;
import pl.pwr.student.gogame.model.commands.CMDMove;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.commands.Command;
import pl.pwr.student.gogame.model.exceptions.PlayersNotSettledException;
import pl.pwr.student.gogame.model.states.State;

public class GameTest {
  @Test
  public void gameCodeLengthTest() {
    Random rand = new Random();
    Game game = new Game(null, null, null, null, rand);
    System.out.println("Kod gry: " + game.getGameCode());
    assertEquals(Game.GAME_CODE_LEN - (Game.GAME_CODE_LEN % 2), game.getGameCode().length());
  }

  @Test
  public void gameStateMachineChangingStates() {
    Game g = createGameForTests();
    g.startGame();

    assertEquals(g.getState().idx, State.BLACK_TURN.idx);
    assertEquals((Integer) 0, g.getMoveCount());

    Command cmd1 = new CMDPass(true);
    g.execCommand(cmd1);
    assertEquals(g.getState().idx, State.WHITE_TURN.idx);
    assertEquals((Integer) 1, g.getMoveCount());

    // wysłanie ruchu drugi raz zostanie zignorowane
    g.execCommand(cmd1);
    assertEquals(g.getState().idx, State.WHITE_TURN.idx);
    assertEquals((Integer) 1, g.getMoveCount());

    Command cmd2 = new CMDMove(0, 0, false);
    g.execCommand(cmd2);
    assertEquals(g.getState().idx, State.BLACK_TURN.idx);
    assertEquals((Integer) 2, g.getMoveCount());

    Command cmd3 = new CMDMove(0, 1, true);
    g.execCommand(cmd3);
    assertEquals(g.getState().idx, State.WHITE_TURN.idx);
    assertEquals((Integer) 3, g.getMoveCount());
  }

  private Game createGameForTests() {
    GameBuilder gb = new StandardGameBuilder();

    Player p1 = new Player("Alice");
    Player p2 = new Player("Bob");
    gb.setPlayer1(p1).setPlayer2(p2);

    Game g;

    try {
      g = gb.buildGame();
      return g;
    } catch (PlayersNotSettledException e) {
      return null;
    }
  }
}
