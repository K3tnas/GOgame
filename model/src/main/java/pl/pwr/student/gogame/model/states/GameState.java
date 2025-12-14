package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDMove;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.rules.RuleSet;

public abstract class GameState {
  protected RuleSet rules;
  protected Board board;
  protected ContextManipulation contextManipulation;

  // TODO: dopisanie metod wywoływanych na wejściu do stanu
  // Na przykład przy wejściu do stanu EndOfGame ma być wywołane liczenie punktów

  public abstract void makeMove(CMDMove move);
  public abstract void pass(CMDPass pass);

  public GameState(RuleSet rules, Board board, ContextManipulation contextManipulation) {
    this.rules = rules;
    this.board = board;
    this.contextManipulation = contextManipulation;
  }
}
