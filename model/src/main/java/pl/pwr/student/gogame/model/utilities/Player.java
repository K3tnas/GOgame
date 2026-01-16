package pl.pwr.student.gogame.model.utilities;

public class Player {
  private int captives;
  private final String id;

  public Player(String id) {
    this.id = id;
    captives = 0;
  }

  public int getCaptives() {
    return captives;
  }

  public String getId() {
    return id;
  }

  public void addCaptive() {
    this.captives++;
  }
}
