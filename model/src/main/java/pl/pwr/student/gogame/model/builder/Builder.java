package pl.pwr.student.gogame.model.builder;

import pl.pwr.student.gogame.model.Player;

public abstract class Builder {

  private Player player1;
  private Player player2;

  public void setPlayer1(Player player1) {
    this.player1 = player1;
  }

  public void setPlayer2(Player player2) {
    this.player2 = player2;
  }

  public void setRules(RuleSet ruleSet) {

  }

  public void addRule(Rule rule) {
    this.ruleSet.add(rule);
  }

  public abstract Game buildGame();
}
