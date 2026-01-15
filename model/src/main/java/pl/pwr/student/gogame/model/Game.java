package pl.pwr.student.gogame.model;

import pl.pwr.student.gogame.model.states.GameState;
import pl.pwr.student.gogame.model.states.WhiteTurn;
import pl.pwr.student.gogame.model.utilities.GameInfo;

public class Game {
  private GameState gameState;
  private final GameInfo gameInfo;

  public Game(GameInfo gameInfo) {
    this.gameInfo = gameInfo;
    this.gameState = new WhiteTurn(gameInfo);
  }

  public GameState getGameState() {
    return this.gameState;
  }

  public GameInfo getGameInfo() {
    return this.gameInfo;
  }

  public void pass(String playerId) {
    this.gameState = gameState.pass(playerId);
  }

  public void putStone(int x, int y, String playerId) {
    this.gameState = gameState.putStone(x, y, playerId);
  }

  public void surrender(String playerId) {
    this.gameState = gameState.surrender(playerId);
  }
}
