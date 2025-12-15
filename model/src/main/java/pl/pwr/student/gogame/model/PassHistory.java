package pl.pwr.student.gogame.model;

public class PassHistory {
  private boolean[] lastPasses;

  public PassHistory() {
    lastPasses = new boolean[] { false, false };
  }

  public void addPass() {
    lastPasses[0] = lastPasses[1];
    lastPasses[1] = true;
  }

  public boolean isGameOver() {
    return lastPasses[0] && lastPasses[1];
  }

  public void addAction() {
    lastPasses[0] = lastPasses[1];
    lastPasses[1] = false;
  }

  @Override
  public String toString() {
    return String.format("PassHistory: [%s, %s]",
        lastPasses[0] ? "PASS" : "MOVE",
        lastPasses[1] ? "PASS" : "MOVE");
  }
}
