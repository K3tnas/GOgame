package pl.pwr.student.gogame.model.rules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallBeInsideTheBoard;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallNotSuicide;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallPlaceStoneOnEmptySpaceOnly;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallRespectKo;

public class KOTest {

  @Test
  public void simpleKOTest() {
    Board b = new Board(5);
    var ruleset = setUpRules();

    int[][] white = {{2, 1}, {1, 2}, {2, 3}};
    int[][] black = {{3, 1}, {4, 2}, {3, 3}};
    int[] lastMove = {3, 2};

    for (int[] w : white) {
      b.getField(w[0], w[1]).setTeam(Team.WHITE);
    }

    for (int[] i : black) {
      b.getField(i[0], i[1]).setTeam(Team.BLACK);
    }

    b.saveCurrState();
    b.getField(lastMove[0], lastMove[1]).setTeam(Team.WHITE);
    b.saveCurrState();

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertTrue(ruleset.meetsRules(b, 2, 2, Team.BLACK));
    b.getField(2, 2).setTeam(Team.BLACK);
    b.getField(3, 2).setTeam(Team.EMPTY);

    System.out.println(b);

    assertFalse(ruleset.meetsRules(b, 3, 2, Team.WHITE));
  }

  private static Ruleset setUpRules() {
    var ruleSet = new Ruleset();
    ruleSet.addRule(new YouShallBeInsideTheBoard());
    ruleSet.addRule(new YouShallPlaceStoneOnEmptySpaceOnly());
    ruleSet.addRule(new YouShallNotSuicide());
    ruleSet.addRule(new YouShallRespectKo());

    return ruleSet;
  }
}
