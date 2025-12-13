package pl.pwr.student.gogame.model;

import org.apache.commons.text.RandomStringGenerator;

public class Game {
    private Board board;
    private GameStateMachine gameState;

    private String gameCode;

    private Server host;

    private Player[] players = new Player[2];

    // TODO: wspólny random dla całego serwera
    private Random random = new Random();

    private static final int GAME_CODE_LEN = 10;

    public void setBoardSize(int width) {
        this.board = new Board(width);
    }

    public String getGameCode() {
        return this.gameCode;
    }

    public void startGame() {

    }

    public void randomizeColors() {
        if (random.nextBoolean()) {
            // 50% szans
            Player tmp = players[0];
            players[0] = players[1];
            players[1] = tmp;
            tmp = null;
        }
    }

    private String generateGameCode() {
        String alphabet = "";
    }

    public Game() {
        this.gameCode = generateGameCode();
    }
}