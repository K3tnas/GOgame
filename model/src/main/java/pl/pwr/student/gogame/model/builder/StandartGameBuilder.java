package pl.pwr.student.gogame.model.builder;

import java.util.Random;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.rules.RuleSet;
import pl.pwr.student.gogame.model.exceptions.PlayersNotSettledException;

public class StandartGameBuilder extends GameBuilder {

  private static final int STANDARD_BOARD_WIDTH = 13;
  private static final RuleSet STANDART_RULE_SET = null;

  @Override
  public Game buildGame() throws PlayersNotSettledException {
    if ((player1 == null) || (player2 == null)) {
      throw new PlayersNotSettledException();
    }

    if ((Integer) boardWidth == null) {
      setSize(STANDARD_BOARD_WIDTH);
    }

    if (ruleSet == null) {
      setRules(STANDART_RULE_SET);
    }

    if (rand == null) {
      rand = new Random();
    }

    if ((rand.nextInt() % 2) == 1) {
      return new Game(new Board(boardWidth), player1, player2, ruleSet, rand);
    }

    return new Game(new Board(boardWidth), player2, player1, ruleSet, rand);
  }
}
