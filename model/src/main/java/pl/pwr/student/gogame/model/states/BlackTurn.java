package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDPut;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.rules.RuleSet;

public class BlackTurn extends GameState {

  public BlackTurn(RuleSet rules, int whitePlayerId, int blackPlayerId, boolean[] passMemory) {
    super(rules, whitePlayerId, blackPlayerId, passMemory);
  }

  @Override
  public State putStone(CMDPut command, Board board) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'putStone'");
  }

  @Override
  public State pass(CMDPass command) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'pass'");
  }
}
