package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.rules.RuleSet;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.commands.CMDPut;

public abstract class GameState {

  protected RuleSet rules;
  protected int whitePlayerId;
  protected int blackPlayerId;
  protected boolean[] passMemory;

  public GameState(RuleSet rules, int whitePlayerId, int blackPlayerId, boolean[] passMemory) {
    this.rules = rules;
    this.whitePlayerId = whitePlayerId;
    this.blackPlayerId = blackPlayerId;
    this.passMemory = passMemory;
  }

  public abstract State putStone(CMDPut command, Board board);

  public abstract State pass(CMDPass command);
}
