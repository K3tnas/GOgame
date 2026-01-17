package pl.pwr.student.gogame.model.board;

import java.io.IOException;
import java.text.ParseException;

/** Board */
public class Board {
  private final Field[][] fields;
  private final Team[][][] boardHistory;
  private final int size;

  public Board(int size) {
    if (size < 2) {
      throw new IllegalArgumentException("Board is a square at least 2 x 2");
    }
    this.size = size;
    this.boardHistory = new Team[2][this.size][this.size];
    this.fields = new Field[this.size][this.size];
    setUpBoard();
  }

  public void saveCurrState() {
    boardHistory[0] = boardHistory[1];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        boardHistory[1][i][j] = getField(i + 1, j + 1).getTeam();
      }
    }
  }

  public Team[][] getYourPrevState() {
    return boardHistory[0];
  }

  public Field getField(int x, int y) {
    if (x < 1 || y < 1 || x > size || y > size) {
      throw new IllegalArgumentException("Out of bounds");
    }
    return fields[y - 1][x - 1];
  }

  public int getSize() {
    return this.size;
  }

  @Override
  public String toString() {
    final StringBuilder b = new StringBuilder();
    final int maxNumLength = Integer.toString(size).length();
    for (int digit = 0; digit < maxNumLength; digit++) {
      for (int i = 0; i < maxNumLength; i++) {
        b.append(" ");
      }
      b.append(" ");
      for (int column = 1; column <= size; column++) {
        if (column < (int) Math.pow(10, maxNumLength - digit - 1)) {
          b.append(" ");
        } else {
          b.append(Math.abs((column / (int) Math.pow(10, maxNumLength - digit - 1)) % 10));
        }
        b.append(" ");
      }
      b.append("\b\n");
    }
    for (int row = 1; row <= size; row++) {
      final String rowNum = Integer.toString(row);
      for (int i = 0; i < maxNumLength - rowNum.length(); i++) {
        b.append(" ");
      }
      b.append(rowNum);
      b.append(" ");
      for (int col = 0; col < size; col++) {
        b.append(fields[row - 1][col].toString());
        b.append(" ");
      }
      b.append("\b\n");
    }
    return b.toString();
  }

  private void setUpBoard() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        fields[i][j] = new Field();
        boardHistory[0][i][j] = Team.EMPTY;
        boardHistory[1][i][j] = Team.EMPTY;
      }
    }

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {

        int neighbourCount = 0;
        for (int di = -1; di <= 1; di++) {
          for (int dj = -1; dj <= 1; dj++) {
            if (Math.abs(di) + Math.abs(dj) != 1) continue;

            final int ni = i + di;
            final int nj = j + dj;
            if (ni >= 0 && ni < size && nj >= 0 && nj < size) {
              neighbourCount++;
            }
          }
        }

        final Field[] neighbours = new Field[neighbourCount];
        int index = 0;

        for (int di = -1; di <= 1; di++) {
          for (int dj = -1; dj <= 1; dj++) {
            if (Math.abs(di) + Math.abs(dj) != 1) continue;

            final int ni = i + di;
            final int nj = j + dj;
            if (ni >= 0 && ni < size && nj >= 0 && nj < size) {
              neighbours[index++] = fields[ni][nj];
            }
          }
        }

        fields[i][j].setNeighbours(neighbours);
      }
    }
  }

  // do użytku tylko na serwerze
  public String toCSV() {
    StringBuilder sb = new StringBuilder("");
    sb.append("SIZE," + size + ";");

    // zapis planszy
    for (int y = 1; y <= size; y++) {
      for (int x = 1; x <= size; x++) {
        sb.append(getField(x, y).getTeam().name());
        if (x < size) {
          sb.append(",");
        }
      }
      sb.append(";");
    }

    return sb.toString();
  }

  // do użytku tylko na kliencie
  public static Board fromCSV(String csv) throws NumberFormatException {
    String[] csvFields = csv.split(";");
    String sizeField = csvFields[0];
    int boardSize = Integer.parseInt(sizeField.split(",")[1]);
    Board b = new Board(boardSize);
    b.setUpBoard();
    for (int rowIdx = 0; rowIdx < boardSize; ++rowIdx) {
        String row = csvFields[rowIdx + 1]; // +1 bo pierwsze csvField jest postaci SIZE,x;
        String[] fields = row.split(",");
        for (int colIdx = 0; colIdx < boardSize; ++colIdx) {
          Team team = Team.EMPTY;
          for (Team t : Team.values()) {
            if (t.name().equals(fields[colIdx])) {
              team = t;
            }
          }
          b.fields[rowIdx][colIdx].setTeam(team);
        }
    }
    return b;
  }
}
