package pl.pwr.student.gogame.client.board.GUICommands;

public class PutStone implements GUICommand {
    private int row;
    private int col;

    public PutStone(int row, int col) {
        this.col = col;
        this.row = row;        
    }

    @Override
    public String toString() {
        return "PUT," + this.col + "," + this.row + ";";
    }
}
