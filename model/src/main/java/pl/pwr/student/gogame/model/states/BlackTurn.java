package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDMove;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.rules.RuleSet;

public class BlackTurn extends GameState {

    public BlackTurn(RuleSet rules, ContextManipulation contextManipulation) {
        super(rules, contextManipulation);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void makeMove(Board board, CMDMove move) {
        if (!move.isFromBlackPlayer) {
            return;
        }

        if (rules.meetsRules(board, move)) {
            contextManipulation.setState(State.WHITE_TURN);
        }
    }

    @Override
    public void pass(CMDPass pass) {
        if (!pass.isFromBlackPlayer) {
            return;
        }

        contextManipulation.setState(State.WHITE_TURN);
    }
}