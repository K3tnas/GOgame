package pl.pwr.student.gogame.model.rules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallBeInsideTheBoard;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallNotSuicide;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallPlaceStoneOnEmptySpaceOnly;

public class RuleTest {

  @Test
  public void YouShallBeInside() {
    Board b = new Board(5);
    Rule r = new YouShallBeInsideTheBoard();

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertFalse(r.meetsRule(b, -1, 6, Team.BLACK));
  }

  @Test
  public void YouShallPlaceOnEmptyOnly() {
    Board b = new Board(5);
    Rule r = new YouShallPlaceStoneOnEmptySpaceOnly();

    b.getField(1, 1).setTeam(Team.BLACK);

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertFalse(r.meetsRule(b, 1, 1, Team.WHITE));
    assertFalse(r.meetsRule(b, 1, 1, Team.BLACK));
    assertTrue(r.meetsRule(b, 1, 2, Team.WHITE));
  }

  @Test
  public void YouShallNotSuicide() {
    Board b = new Board(5);
    Rule r = new YouShallNotSuicide();

    b.getField(2, 1).setTeam(Team.BLACK);
    b.getField(1, 2).setTeam(Team.BLACK);
    b.getField(3, 2).setTeam(Team.BLACK);
    b.getField(2, 3).setTeam(Team.BLACK);

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertFalse(r.meetsRule(b, 2, 2, Team.WHITE));
    assertTrue(r.meetsRule(b, 2, 2, Team.BLACK));

    b.getField(3, 3).setTeam(Team.WHITE);
    b.getField(3, 1).setTeam(Team.WHITE);
    b.getField(4, 2).setTeam(Team.WHITE);

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName() + " exception");
    System.out.println(b);

    assertTrue(r.meetsRule(b, 2, 2, Team.WHITE));
    assertTrue(r.meetsRule(b, 2, 2, Team.BLACK));
  }

  @Test
  public void All() {
    Board b = new Board(5);
    var ruleset = setUpRules();

    int[][] white = {{1, 1}, {1, 2}, {1, 3}, {2, 3}, {3, 3}, {3, 2}};
    int[][] black = {{1, 4}, {2, 4}, {3, 4}, {4, 4}, {4, 3}, {4, 2}, {4, 1}, {2, 1}, {2, 2}};

    for (int[] w : white) {
      b.getField(w[0], w[1]).setTeam(Team.WHITE);
    }

    for (int[] i : black) {
      b.getField(i[0], i[1]).setTeam(Team.BLACK);
    }

    System.out.println(new Object() {}.getClass().getEnclosingMethod().getName());
    System.out.println(b);

    assertTrue(ruleset.meetsRules(b, 3, 1, Team.WHITE));
    assertTrue(ruleset.meetsRules(b, 3, 1, Team.BLACK));
  }

  private static Ruleset setUpRules() {
    var ruleSet = new Ruleset();
    ruleSet.addRule(new YouShallBeInsideTheBoard());
    ruleSet.addRule(new YouShallPlaceStoneOnEmptySpaceOnly());
    ruleSet.addRule(new YouShallNotSuicide());

    return ruleSet;
  }
}
