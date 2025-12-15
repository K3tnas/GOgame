package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.rules.RuleSet;
import pl.pwr.student.gogame.model.PassHistory;
import pl.pwr.student.gogame.model.Player;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.commands.CMDPut;

public abstract class GameState {

  protected RuleSet rules;
  protected Player whitePlayer, blackPlayer;
  protected PassHistory passHistory;

  public GameState(RuleSet rules, Player whitePlayer, Player blackPlayer, PassHistory passHistory) {
    this.rules = rules;
    this.whitePlayer = whitePlayer;
    this.blackPlayer = blackPlayer;
    this.passHistory = passHistory;
  }

  public abstract State putStone(CMDPut command, Board board);

  public abstract State pass(CMDPass command);
}
