package pl.pwr.student.gogame.model.rules.ConcreteRules;

import java.util.ArrayList;
import java.util.List;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Stone;
import pl.pwr.student.gogame.model.commands.CMDMove;
import pl.pwr.student.gogame.model.rules.ComplexRule;
import pl.pwr.student.gogame.model.board.Pos;

public class YouShallNotSuffocate extends ComplexRule {


  /*
  * Zasada: kamień umiera kiedy nie ma oddechów (proste sprawdzenie czy jakiekolwiek pole wokół jest wolne)
  */
  @Override
  protected Boolean meetsOwnRule(Board board, CMDMove move) {
    // List positionsToDelete = new ArrayList<Pos>();

    // for (int y = 0; board.isInside(0, y); ++y) {
    //   for (int x = 0; board.isInside(x, y); ++x) {
    //     if (board.getStone(x, y) == null) {
    //       continue;
    //     }

        Stone currentStone = new Stone(move.isFromBlackPlayer);
        currentStone.resetNeighbourCounters();

        for (Integer[] neigh : Board.NEIGHBOURS) {
          if (!board.isInside(move.x + neigh[0], move.y + neigh[1])) {
            continue;
          }

          Stone neighbourStone = board.getStone(move.x + neigh[0], move.y + neigh[1]);

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

    //     if (currentStone.isBreathless()) {
    //       // kamień ma zostać usunięty
    //       positionsToDelete.add(new Pos(x, y));
    //     }
    //   }
    // }
  }

  @Override
  public Boolean meetsRule(Board board, CMDMove move) {
    // TODO: Przyszła implementacja rekurencyjnego sprawdzenia podzasad (wyjątków)

    return meetsOwnRule(board, move);
  }

}
