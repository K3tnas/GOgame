package pl.pwr.student.gogame.model.builder;

import java.util.Random;
import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.rules.Ruleset;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallBeInsideTheBoard;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallNotSuicide;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallPlaceStoneOnEmptySpaceOnly;
import pl.pwr.student.gogame.model.rules.concreteRules.YouShallRespectKo;
import pl.pwr.student.gogame.model.utilities.GameInfo;
import pl.pwr.student.gogame.model.utilities.PassHistory;

public class StandardGameBuilder extends GameBuilder {

  private static final int STANDARD_BOARD_WIDTH = 13;
  private static final Ruleset STANDARD_RULE_SET;

  static {
    STANDARD_RULE_SET = new Ruleset();
    STANDARD_RULE_SET
        .addRule(new YouShallBeInsideTheBoard())
        .addRule(new YouShallPlaceStoneOnEmptySpaceOnly())
        .addRule(new YouShallNotSuicide())
        .addRule(new YouShallRespectKo());
  }

  @Override
  public Game buildGame() {
    if (p1 == null || p2 == null) {
      throw new RuntimeException("Players not settled");
    }

    if (boardWidth == null) {
      setSize(STANDARD_BOARD_WIDTH);
    }

    if (ruleset == null) {
      setRuleset(STANDARD_RULE_SET);
    }

    if (rand == null) {
      rand = new Random();
    }

    if (rand.nextInt() % 2 == 1) {
      return new Game(new GameInfo(new Board(boardWidth), ruleset, p1, p2, new PassHistory()));
    }

    return new Game(new GameInfo(new Board(boardWidth), ruleset, p2, p1, new PassHistory()));
  }
}
