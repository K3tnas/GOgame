package pl.pwr.student.gogame.model.board;

public class Board {
    private Stone[][] fields;
    
    public Boolean isInside(Pos pos) {
        return isInside(pos.x, pos.y);
    }

    public Boolean isInside(int x, int y) {
        return 0 <= x && x < this.getWidth() &&
               0 <= y && y < this.getHeight();
    }

    public static final Integer[][] NEIGHBOURS = {
        { 1, 0 },
        { 0, 1 },
        { -1, 0 },
        { 0, -1 }
    };

    public void removeAllStones() {
        for (int y = 0; y < this.getHeight(); ++y) {
            for (int x = 0; x < this.getWidth(); ++x) {
                this.removeStone(x, y);
            }
        }
    }

    public void removeStone(int x, int y) {
        this.fields[y][x] = null;
    }

    public Stone getStone(int x, int y) {
        return this.fields[y][x];
    }

    public void setStone(int x, int y, Boolean isBlack) {
        this.fields[y][x] = new Stone(isBlack, getNeighbourFieldsCount(x, y));
    }

    public Integer getWidth() {
        return fields[0].length;
    }
    
    public Integer getHeight() {
        return fields.length;
    }

    private Integer getNeighbourFieldsCount(int x, int y) {
        int count = 0;
        for (Integer[] neigh : NEIGHBOURS) {
            if (isInside(x + neigh[0], y + neigh[1])) {
                ++count;
            }
        }
        return count;
    }

    public Board(int width) {
        this.fields = new Stone[width][width];
        this.removeAllStones();
    }
}
