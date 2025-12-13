package pl.pwr.student.gogame.model;

import pl.pwr.student.gogame.model.rules.RuleSet;

import java.util.Random;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.states.GameState;
import pl.pwr.student.gogame.model.commands.CMDMove;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.commands.Command;

public class Game {
  private Board board;
  private GameState gameState;

  private String gameCode;

  private Random rand;

  // private Server host;

  private Player blackPlayer;
  private Player whitePlayer;

  private RuleSet rules;

  public static final int GAME_CODE_LEN = 10;

  public Game(Board board, Player blackPlayer, Player whitePlayer, RuleSet rules, Random rand) {
    this.board = board;
    this.whitePlayer = whitePlayer;
    this.blackPlayer = blackPlayer;
    this.rules = rules;

    this.rand = rand;

    this.gameCode = generateGameCode();
  }

  public String getGameCode() {
    return this.gameCode;
  }

  public void startGame() {

  }

  public void execCommand(Command command) {
    switch (command.commandType) {
      case MOVE:
        gameState.makeMove(this.board, (CMDMove) command);
        break;

      case PASS:
        gameState.pass(this.board, (CMDPass) command);
        break;

      default:
        break;
    }
  }

  private String generateGameCode() {
    String consonants = "BCDFGHJKLMNPRST";
    String vowels = "AEIOU";

    String result = "";

    for (int i = 0; i < GAME_CODE_LEN/2; ++i) {
      result += "" + consonants.charAt(rand.nextInt(consonants.length())) + vowels.charAt(rand.nextInt(vowels.length()));
    }

    return result;
  }

  public Game() {
    this.gameCode = generateGameCode();
  }
}
