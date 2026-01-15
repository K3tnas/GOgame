package pl.pwr.student.gogame.model.builder;

import java.util.Random;
import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.rules.Ruleset;
import pl.pwr.student.gogame.model.utilities.Player;

public abstract class GameBuilder {

  protected Player p1, p2;
  protected Integer boardWidth;
  protected Ruleset ruleset;
  protected Random rand;

  public GameBuilder addPlayer(Player player) {
    if (p1 == null) {
      p1 = player;
    } else if (p2 == null) {
      p2 = player;
    }

    return this;
  }

  public GameBuilder setSize(int boardWidth) {
    this.boardWidth = boardWidth;
    return this;
  }

  public GameBuilder setRuleset(Ruleset rs) {
    this.ruleset = rs;
    return this;
  }

  public GameBuilder setRand(Random rand) {
    this.rand = rand;
    return this;
  }

  public abstract Game buildGame();
}
