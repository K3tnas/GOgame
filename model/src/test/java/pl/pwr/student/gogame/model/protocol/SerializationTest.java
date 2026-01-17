package pl.pwr.student.gogame.model.protocol;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.builder.StandardGameBuilder;
import pl.pwr.student.gogame.model.utilities.Player;

public class SerializationTest {
  @Test
  public void serialization() {
    Game g = setup();
    g.putStone(1, 1, "0");
    g.putStone(1, 2, "1");
    g.putStone(2, 1, "0");
    String serialized = "";
    try {
      serialized = g.getGameInfo().board().toCSV();
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }
    Board b = Board.fromCSV(serialized);
    assertEquals(b.toCSV(), serialized);
  }

  private static Game setup() {
    StandardGameBuilder gb = new StandardGameBuilder();
    gb.setSize(2);
    gb.addPlayer(new Player("0"));
    gb.addPlayer(new Player("1"));
    return gb.buildGame();
  }
}
