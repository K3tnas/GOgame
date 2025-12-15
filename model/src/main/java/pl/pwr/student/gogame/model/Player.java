package pl.pwr.student.gogame.model;

public class Player {
  private String username;
  private int id;
  private int captives;

  public Player(String username, int id) {
    this.username = username;
    this.id = id;
    captives = 0;
  }

  public Player() {
    captives = 0;
  }

  public void addCaptive() {
    captives++;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public int getCaptives() {
    return captives;
  }
}
