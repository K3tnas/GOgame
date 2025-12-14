package pl.pwr.student.gogame.model.rules.ConcreteRules;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Stone;
import pl.pwr.student.gogame.model.rules.ComplexRule;

public class YouShallNotSuicide extends ComplexRule {

  /*
   * Zasada: kamień nie może być postawiony, jeśli zostałby sam od razu zbity
   *
   * TODO: dodanie zasady, albo rozwinięcie poniższej w taki sposób, aby
   * rekurencyjnie
   * przechodzić po łańcuchu i sprawdzać, czy któryś z kamieni w nim ma co
   * najmniej 1
   * oddech - jeśli nie, cały łańcuch jest zbierany
   */
  @Override
  protected Boolean meetsOwnRule(Board board, int x, int y, boolean isBlack) {
    // List positionsToDelete = new ArrayList<Pos>();

    // for (int y = 0; board.isInside(0, y); ++y) {
    // for (int x = 0; board.isInside(x, y); ++x) {
    // if (board.getStone(x, y) == null) {
    // continue;
    // }

    Stone currentStone = new Stone(isBlack, board.getNeighbourFieldsCount(x, y));
    currentStone.resetNeighbourCounters();

    for (Integer[] neigh : Board.NEIGHBOURS) {
      if (!board.isInside(x + neigh[0], y + neigh[1])) {
        continue;
      }

      Stone neighbourStone = board.getStone(x + neigh[0], y + neigh[1]);

      if (neighbourStone == null) {
        continue;
      }

      if (currentStone.isBlack() == neighbourStone.isBlack()) {
        currentStone.incAlly();
      } else {
        currentStone.incEnemy();
      }
    }

    return !currentStone.isBreathless();

    // if (currentStone.isBreathless()) {
    // // kamień ma zostać usunięty
    // positionsToDelete.add(new Pos(x, y));
    // }
    // }
    // }
  }

  @Override
  public Boolean meetsRule(Board board, int x, int y, boolean isBlack) {
    // TODO: Przyszła implementacja rekurencyjnego sprawdzenia podzasad (wyjątków)

    return meetsOwnRule(board, x, y, isBlack);
  }

}
