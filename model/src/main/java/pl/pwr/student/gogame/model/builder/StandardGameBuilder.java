package pl.pwr.student.gogame.model.builder;

import java.util.Random;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.rules.RuleSet;
import pl.pwr.student.gogame.model.rules.ConcreteRules.YouShallPlaceStoneOnlyOnEmptyPlace;
import pl.pwr.student.gogame.model.exceptions.PlayersNotSettledException;

public class StandardGameBuilder extends GameBuilder {

  private static final int STANDARD_BOARD_WIDTH = 13;
  private static final RuleSet STANDARD_RULE_SET;

  static {
    STANDARD_RULE_SET = new RuleSet();
    STANDARD_RULE_SET.addRule(new YouShallPlaceStoneOnlyOnEmptyPlace());
  }

  @Override
  public Game buildGame() throws PlayersNotSettledException {

    if (player1 == null || player2 == null) {
      throw new PlayersNotSettledException();
    }

    if (boardWidth == null) {
      setSize(STANDARD_BOARD_WIDTH);
    }

    if (ruleSet == null) {
      setRules(STANDARD_RULE_SET);
    }

    if (rand == null) {
      rand = new Random();
    }

    if (rand.nextInt() % 2 == 1) {
      return new Game(new Board(boardWidth), player1, player2, ruleSet, rand);
    }

    return new Game(new Board(boardWidth), player2, player1, ruleSet, rand);
  }
}
