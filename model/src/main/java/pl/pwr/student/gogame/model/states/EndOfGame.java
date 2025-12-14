package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDMove;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.rules.RuleSet;

public class EndOfGame extends GameState {

    public EndOfGame(RuleSet rules, ContextManipulation contextManipulation) {
        super(rules, contextManipulation);
        //TODO Auto-generated constructor stub
    }

    // Dojście do EndOfGame oznacza koniec działania maszyny stanów gry
    // Metody makeMove i pass celowo nic nie robią

    @Override
    public void makeMove(Board board, CMDMove move) {
    }

    @Override
    public void pass(CMDPass pass) {
    }

}