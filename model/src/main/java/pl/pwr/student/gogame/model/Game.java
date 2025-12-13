package pl.pwr.student.gogame.model;

import org.apache.commons.text.RandomStringGenerator;

public class Game {
    private Board board;
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

    public void randomizeColors() {
        if (true) {

        }
    }

    private String generateGameCode() {
        return RandomStringGenerator.generate(GAME_CODE_LEN);
    }

    public Game() {
        this.gameCode = generateGameCode();
    }
}