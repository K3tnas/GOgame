package pl.pwr.student.gogame.model.state;

public interface GameStateMachine {
    static GameState makeMove(Board board, Move move);
    static GameState pass();
}