package pl.pwr.student.gogame.model.utilities;

public class Player {
  private int captives;
  private final String id;
  private boolean mayPass;

  public Player(String id) {
    this.id = id;
    captives = 0;
    mayPass = true;
  }

  public boolean mayPass() {
    return mayPass;
  }

  public void setMayPass(boolean mayPass) {
    this.mayPass = mayPass;
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
