package pl.pwr.student.gogame.model.builder;

import java.util.Random;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.Player;
import pl.pwr.student.gogame.model.rules.Rule;
import pl.pwr.student.gogame.model.rules.RuleSet;
import pl.pwr.student.gogame.model.exceptions.PlayersNotSettledException;

public abstract class Builder {

  protected Player player1;
  protected Player player2;
  protected RuleSet ruleSet;
  protected int boardWidth;
  protected Random rand;

  public Builder setPlayer1(Player player1) {
    this.player1 = player1;
    return this;
  }

  public Builder setPlayer2(Player player2) {
    this.player2 = player2;
    return this;
  }

  public Builder setRules(RuleSet ruleSet) {
    this.ruleSet = ruleSet;
    return this;
  }

  public Builder addRule(Rule rule) {
    this.ruleSet.addRule(rule);
    return this;
  }

  public Builder setSize(int boardWidth) {
    this.boardWidth = boardWidth;
    return this;
  }

  public Builder setRand(Random rand) {
    this.rand = rand;
    return this;
  }

  public abstract Game buildGame() throws PlayersNotSettledException;
}
