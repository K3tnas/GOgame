package pl.pwr.student.gogame.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Random;

import org.junit.Test;

import pl.pwr.student.gogame.model.builder.GameBuilder;
import pl.pwr.student.gogame.model.builder.StandardGameBuilder;
import pl.pwr.student.gogame.model.commands.CMDPut;
import pl.pwr.student.gogame.model.commands.CMDPass;
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
    var bPlayer = g.getBlackPlayerId();
    var wPlayer = g.getWhitePlayerId();

    // Zaczyna czarny
    assertEquals(State.BLACK_TURN, g.getState());

    // Test wykrwywania błędu
    g.execCommand(new CMDPut(15, 15, bPlayer));
    assertEquals(State.BLACK_TURN, g.getState());

    // Poprawny ruch - zmiana rundy
    g.execCommand(new CMDPut(1, 1, bPlayer));
    assertEquals(State.WHITE_TURN, g.getState());
    assertNotNull(g.getBoard().getStone(1, 1));

    // Czarny gracz próbuje coś zrobić
    // w nie swojej turze - brak zmiany
    g.execCommand(new CMDPut(1, 1, bPlayer));
    assertEquals(State.WHITE_TURN, g.getState());

    // biały gracz próbuje postawić piona na niepuste pole
    // brak zmiany
    g.execCommand(new CMDPut(1, 1, wPlayer));
    assertEquals(State.WHITE_TURN, g.getState());

    // biały gracz passuje - zmiana
    g.execCommand(new CMDPass(wPlayer));
    assertEquals(State.BLACK_TURN, g.getState());

    // koniec gry
    g.execCommand(new CMDPass(bPlayer));
    assertEquals(true, g.getPassHistory().isGameOver());
    assertEquals(State.END_OF_GAME, g.getState());
  }

  @Test
  public void killTest() {
    Game g = createGameForTests();
    g.startGame();
    var bPlayer = g.getBlackPlayerId();
    var wPlayer = g.getWhitePlayerId();

    g.execCommand(new CMDPut(0, 0, bPlayer));
    g.execCommand(new CMDPut(1, 0, wPlayer));
    g.execCommand(new CMDPass(bPlayer));
    g.execCommand(new CMDPut(0, 1, wPlayer));

    assertEquals(true, g.getBoard().isEmpty(0, 0));
  }

  private Game createGameForTests() {
    GameBuilder gb = new StandardGameBuilder();

    Player p1 = new Player("Alice", 1234);
    Player p2 = new Player("Bob", 4321);
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
