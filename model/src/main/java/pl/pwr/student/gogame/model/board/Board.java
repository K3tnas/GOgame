package pl.pwr.student.gogame.model.board;

public class Board {
    private FieldState[][] fields;

    public void removeAllStones() {
        for (int i = 0; i < this.fields.length; ++i) {
            for (int j = 0; j < this.fields[0].length; ++j) {
                this.fields[i][j] = null;
            }
        }
    }

    public Board(int width) {
        this.fields = new FieldState[width][width];
        this.removeAllStones();
    }
}
