package pl.pwr.student.gogame.model;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class GameTest {
    @Test
    public void gameCodeLengthTest() {
        Random rand = new Random();
        Game game = new Game(null, null, null, null, rand);
        System.out.println("Kod gry: " + game.getGameCode());
        assertEquals(Game.GAME_CODE_LEN, game.getGameCode().length());
    }
}
