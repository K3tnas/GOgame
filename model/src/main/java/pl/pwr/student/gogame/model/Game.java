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
    return gameState;
  }

  public GameInfo getGameInfo() {
    return gameInfo;
  }

  public void pass(String playerId) {
    gameState = gameState.pass(playerId);
  }

  public void putStone(int x, int y, String playerId) {
    gameState = gameState.putStone(x, y, playerId);
  }

  public void surrender(String playerId) {
    gameState = gameState.surrender(playerId);
  }

  public void proposeDeadField(int x, int y, String playerId) {
    gameState = gameState.proposeDeadField(x, y, playerId);
  }

  public void acceptProposition(String playerId) {
    gameState = gameState.acceptProposition(playerId);
  }

  public void declineProposition(String playerId) {
    gameState = gameState.declineProposition(playerId);
  }
}
