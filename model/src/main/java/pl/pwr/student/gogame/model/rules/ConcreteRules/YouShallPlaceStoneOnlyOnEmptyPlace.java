package pl.pwr.student.gogame.model.rules.ConcreteRules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDMove;
import pl.pwr.student.gogame.model.rules.ComplexRule;

public class YouShallPlaceStoneOnlyOnEmptyPlace extends ComplexRule {

    @Override
    protected Boolean meetsOwnRule(Board board, CMDMove move) {
        return board.getStone(move.x, move.y) == null;
    }

    @Override
    public Boolean meetsRule(Board board, CMDMove move) {
        return meetsOwnRule(board, move);
    }
    
}
