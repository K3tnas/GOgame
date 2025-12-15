package pl.pwr.student.gogame.model.board;

public class Stone {
  private Integer allyCount = 0;
  private Integer enemyCount = 0;
  private Integer neighbourFields = 0;
  private Boolean black;

  public void resetNeighbourCounters() {
    this.allyCount = 0;
    this.enemyCount = 0;
  }

  public void incAlly() {
    if (this.allyCount + this.enemyCount == this.neighbourFields) {
      return;
    }

    ++this.allyCount;
  }

  public void incEnemy() {
    if (this.allyCount + this.enemyCount == this.neighbourFields) {
      return;
    }

    ++this.enemyCount;
  }

  public Integer breathCount() {
    return neighbourFields - allyCount - enemyCount;
  }

  public Boolean isBlack() {
    return this.black;
  }

  public Boolean isBreathless() {
    // TODO: sprawdzanie czy sojusznik ma "pożyczyć oddech" - czyli rekurencyjne
    // chodzenie po łańcuchu do momentu kiedy:
    // A: znajdziemy przynajmniej 1 kamień sojuszniczy z oddechem (nie musimy
    // przechodzić całego łańcuchu) - wtedy kamień żyje
    // B: przejdziemy wszystkie kamienie w łańcuchu i żaden z nich nie będzie miał
    // oddechu - wtedy kamień umiera

    return breathCount() == 0/* && allyCount == 0 */;
  }

  public Stone(Boolean isBlack, Integer neighbourFields) {
    this.black = isBlack;
    this.neighbourFields = neighbourFields;
  }
}
