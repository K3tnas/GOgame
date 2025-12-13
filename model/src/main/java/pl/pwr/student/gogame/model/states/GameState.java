package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.Command;

public interface GameState {
  GameState makeMove(Board board, Command move);
  GameState pass(Board board, Command pass);
}
