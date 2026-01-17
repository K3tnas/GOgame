package pl.pwr.student.gogame.client.board;

public class PutStone implements GUICommand {
    private int row;
    private int col;

    public PutStone(int row, int col) {
        this.col = col;
        this.row = row;        
    }

    public String toString() {
        return "PUT," + this.col + "," + this.row + ";";
    }
}
