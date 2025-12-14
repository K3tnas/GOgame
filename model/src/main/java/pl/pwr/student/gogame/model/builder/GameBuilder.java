package pl.pwr.student.gogame.model.builder;

import java.util.Random;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.Player;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.rules.Rule;
import pl.pwr.student.gogame.model.rules.RuleSet;
import pl.pwr.student.gogame.model.exceptions.PlayersNotSettledException;

public class GameBuilder {

  protected Player player1;
  protected Player player2;
  protected RuleSet ruleSet;
  protected int boardWidth;
  protected Random rand;

  public GameBuilder setPlayer1(Player player1) {
    this.player1 = player1;
    return this;
  }

  public GameBuilder setPlayer2(Player player2) {
    this.player2 = player2;
    return this;
  }

  public GameBuilder setRules(RuleSet ruleSet) {
    this.ruleSet = ruleSet;
    return this;
  }

  public GameBuilder addRule(Rule rule) {
    if (this.ruleSet == null) {
      this.ruleSet = new RuleSet();
    }
    this.ruleSet.addRule(rule);
    return this;
  }

  public GameBuilder setSize(int boardWidth) {
    this.boardWidth = boardWidth;
    return this;
  }

  public GameBuilder setRand(Random rand) {
    this.rand = rand;
    return this;
  }

  public Game buildGame() throws PlayersNotSettledException {
    if ((rand.nextInt() % 2) == 1) {
      return new Game(new Board(boardWidth), player1, player2, ruleSet, rand);
    }
    return new Game(new Board(boardWidth), player2, player1, ruleSet, rand);
  }
}
