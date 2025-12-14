package pl.pwr.student.gogame.model;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import pl.pwr.student.gogame.model.builder.GameBuilder;
import pl.pwr.student.gogame.model.commands.CMDMove;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.exceptions.PlayersNotSettledException;
import pl.pwr.student.gogame.model.rules.RuleSet;
import pl.pwr.student.gogame.model.rules.ConcreteRules.YouShallNotSuicide;

public class GameTest {
    @Test
    public void gameCodeLengthTest() {
        Random rand = new Random();
        Game game = new Game(null, null, null, null, rand);
        System.out.println("Kod gry: " + game.getGameCode());
        assertEquals(Game.GAME_CODE_LEN, game.getGameCode().length());
    }

    @Test
    public void gameStateMachineTest() {
        GameBuilder gb = new GameBuilder();

        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        gb.setPlayer1(p1).setPlayer2(p2);

        YouShallNotSuicide ysnsRule = new YouShallNotSuicide();
        gb.addRule(ysnsRule);

        Random rand = new Random();
        gb.setRand(rand);

        gb.setSize(13);

        Game g;

        try {
            g = gb.buildGame();
        } catch (PlayersNotSettledException e) {
            assertEquals("Gracze nie są ustawieni, nie pownien test nigdy tutaj wejść", "a jednak wszedł");
            return;
        }

        g.startGame();

        CMDMove cmd1 = new CMDMove(0, 0, true);
        g.execCommand(cmd1);
        CMDPass cmd2 = new CMDPass(false);
        g.execCommand(cmd2);

        assertEquals((Integer) 2, g.getMoveCount());
    }
}
