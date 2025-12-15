package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.PassHistory;
import pl.pwr.student.gogame.model.Player;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.commands.CMDPut;
import pl.pwr.student.gogame.model.rules.RuleSet;

public class EndOfGame extends GameState {

  public EndOfGame(RuleSet rules, Player whitePlayer, Player blackPlayer, PassHistory passHistory) {
    super(rules, whitePlayer, blackPlayer, passHistory);
  }

  @Override
  public State putStone(CMDPut command, Board board) {
    // TODO:
    return State.END_OF_GAME;
  }

  @Override
  public State pass(CMDPass command) {
    // TODO:
    return State.END_OF_GAME;
  }

}
