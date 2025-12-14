package pl.pwr.student.gogame.model;

import pl.pwr.student.gogame.model.rules.RuleSet;

import java.util.Random;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.states.BlackTurn;
import pl.pwr.student.gogame.model.states.EndOfGame;
import pl.pwr.student.gogame.model.states.GameState;
import pl.pwr.student.gogame.model.states.State;
import pl.pwr.student.gogame.model.states.WhiteTurn;
import pl.pwr.student.gogame.model.commands.CMDPut;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.commands.Command;

public class Game {
  private Board board;

  private final String gameCode;

  private final Random rand;

  private Integer moveCount;

  // private Server host;

  private Player blackPlayer;
  private Player whitePlayer;

  private final RuleSet rules;

  private GameState[] gameStates;
  private State gameState;

  public static final int GAME_CODE_LEN = 10;

  private boolean[] passMemory = { false, false };

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
    initializeGameStateMachine();
  }

  private void initializeGameStateMachine() {
    this.gameStates = new GameState[3];
    this.gameStates[State.BLACK_TURN.idx] = new BlackTurn(this.rules, whitePlayer.getId(),
        blackPlayer.getId(), passMemory);
    this.gameStates[State.WHITE_TURN.idx] = new WhiteTurn(this.rules, whitePlayer.getId(),
        blackPlayer.getId(), passMemory);
    this.gameStates[State.END_OF_GAME.idx] = new EndOfGame(this.rules, whitePlayer.getId(),
        blackPlayer.getId(), passMemory);
    // grę rozpoczyna gracz czarny
    this.gameState = State.BLACK_TURN;
    this.moveCount = 0;
  }

  private void setState(State state) {
    if (!this.gameState.equals(state)) {
      this.gameState = state;
      this.moveCount++;
    }
  }

  public void execCommand(Command command) {
    switch (command.commandType) {
      case PUT:
        setState(gameStates[gameState.idx].putStone((CMDPut) command, board));
        break;

      case PASS:
        setState(gameStates[gameState.idx].pass((CMDPass) command));
        break;

      default:
        break;
    }
  }

  public Integer getMoveCount() {
    return this.moveCount;
  }

  public State getState() {
    return this.gameState;
  }

  private String generateGameCode() {
    String consonants = "BCDFGHJKLMNPRST";
    String vowels = "AEIOU";

    String result = "";

    for (int i = 0; i < GAME_CODE_LEN / 2; ++i) {
      result += "" + consonants.charAt(this.rand.nextInt(consonants.length()))
          + vowels.charAt(this.rand.nextInt(vowels.length()));
    }

    return result;
  }
}
