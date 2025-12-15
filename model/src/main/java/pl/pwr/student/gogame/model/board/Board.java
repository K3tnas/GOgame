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

  public Boolean isEmpty(int x, int y) {
    return fields[x][y] == null;
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

  public Integer getNeighbourFieldsCount(int x, int y) {
    int count = 0;
    for (Integer[] neigh : NEIGHBOURS) {
      if (isInside(x + neigh[0], y + neigh[1])) {
        ++count;
      }
    }
    return count;
  }

  public boolean isBreathless(int x, int y) {
    if (getStone(x, y) == null) {
      return false;
    }

    return getStone(x, y).isBreathless();
  }

  public void updateStone(int x, int y) {
    Stone stone = getStone(x, y);
    if (stone == null) {
      return;
    }

    for (Integer[] neigh : NEIGHBOURS) {
      int nx = x + neigh[0];
      int ny = y + neigh[1];

      if (!isInside(nx, ny)) {
        continue;
      }

      Stone neighbour = getStone(nx, ny);
      if (neighbour == null) {
        continue;
      }

      if (neighbour.isBlack() == stone.isBlack()) {
        stone.incAlly();
      } else {
        stone.incEnemy();
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    b.append("   ");
    for (int tensDigitColumn = 0; tensDigitColumn < getWidth(); ++ tensDigitColumn) {
      int tens = tensDigitColumn / 10;
      if (tens == 0) {
        b.append("  ");
      } else {
        b.append(tens);
        b.append(" ");
      }
    }
    b.append("\n   ");
    for (int tensDigitColumn = 0; tensDigitColumn < getWidth(); ++ tensDigitColumn) {
      b.append(tensDigitColumn % 10);
      b.append(" ");
    }
    b.append("\n");
    for (int y = 0; y < this.getHeight(); ++y) {
      b.append(String.format("%1$3s", y));
      for (int x = 0; x < this.getWidth(); ++x) {
        Stone stone = getStone(x, y);

        if (stone == null) {
          b.append(". ");
          continue;
        }

        if (stone.isBlack()) {
          b.append("○ ");
        } else {
          b.append("● ");
        }
      }
      b.append("\n");
    }
    return b.toString();
  }

  public Board(int width) {
    this.fields = new Stone[width][width];
    this.removeAllStones();
  }
}
