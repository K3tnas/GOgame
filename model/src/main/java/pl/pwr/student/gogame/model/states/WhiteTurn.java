package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.Command;

public class WhiteTurn implements GameState {

    @Override
    public GameState makeMove(Board board, Command move) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeMove'");
    }

    @Override
    public GameState pass(Board board, Command pass) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pass'");
    }

}