package pl.pwr.student.gogame.client.board.GUICommands;

import pl.pwr.student.gogame.model.board.Board;

public class RedrawBoard implements GUICommand {
    private Board b;

    public RedrawBoard(Board b) {
        this.b = b;
    }

    public Board getBoard() {
        return this.b;
    }

    @Override
    public String toString() {
        return this.b.toString();
    }
}
