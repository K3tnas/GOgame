package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.Move;

public interface GameState {
  public GameState makeMove(Board board, Move move);

  public GameState pass();
}
