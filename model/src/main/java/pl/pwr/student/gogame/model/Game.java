package pl.pwr.student.gogame.model;

import pl.pwr.student.gogame.model.rules.RuleSet;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.states.GameState;
import pl.pwr.student.gogame.model.commands.Command;

import org.apache.commons.text.RandomStringGenerator;

public class Game {
  private Board board;
  private GameState gameState;

  private String gameCode;

  private Server host;

  private static final int GAME_CODE_LEN = 10;

  public Game(Board board, Player blackPlayer, Player whitePlayer, RuleSet rules) {
    super();
  }

  public String getGameCode() {
    return this.gameCode;
  }

  public void startGame() {

  }

  public void execCommand(Command command) {
    switch (command.commandType) {
      case MOVE:
        break;

      default:
        break;
    }
  }

  private String generateGameCode() {
    String alphabet = "";
  }

  public Game() {
    this.gameCode = generateGameCode();
  }

  private void execMove() 
}
