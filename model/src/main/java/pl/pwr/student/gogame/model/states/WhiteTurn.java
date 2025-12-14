package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDMove;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.rules.RuleSet;

public class WhiteTurn extends GameState {

    public WhiteTurn(RuleSet rules, Board board, ContextManipulation contextManipulation) {
        super(rules, board, contextManipulation);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void makeMove(CMDMove move) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeMove'");
    }

    @Override
    public void pass(CMDPass pass) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pass'");
    }

}