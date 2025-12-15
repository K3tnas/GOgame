package pl.pwr.student.gogame.model.states;

import pl.pwr.student.gogame.model.PassHistory;
import pl.pwr.student.gogame.model.Player;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.commands.CMDPut;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.rules.RuleSet;

public class WhiteTurn extends GameState {

  public WhiteTurn(RuleSet rules, Player whitePlayer, Player blackPlayer, PassHistory passHistory) {
    super(rules, whitePlayer, blackPlayer, passHistory);
  }

  @Override
  public State putStone(CMDPut command, Board board) {
    if (command.playerId == blackPlayer.getId()) {
      System.out.println("To nie jest twoja tura >:(");
      return State.WHITE_TURN;
    }

    // TODO: musimy najpierw sprawdzic czy indeksy są w bounds
    // na przyszłość ruleset może być uporządkowany i może być to pierwszy rule
    if (!board.isInside(command.x, command.y)) {
      System.out.println("Niepoprawny ruch");
      return State.WHITE_TURN;
    }

    if (!rules.meetsRules(board, command.x, command.y, false)) {
      System.out.println("Niepoprawny ruch");
      return State.WHITE_TURN;
    }

    board.setStone(command.x, command.y, false);

    // WARNING: to działa tylko obecnie, zamienić w późniejszych iteracjach
    boolean[][] stonesToKill = new boolean[board.getWidth()][board.getHeight()];

    for (int i = 0; i < board.getWidth(); i++) {
      for (int j = 0; j < board.getHeight(); j++) {
        board.updateStone(i, j);
      
        // jeśli nie ma kamienia na polu, to nie ma co zabić
        if (board.getStone(i, j) == null) {
          stonesToKill[i][j] = false;
        }

        if (!board.isEmpty(i, j) && board.getStone(i, j).isBreathless()) {
          stonesToKill[i][j] = true;
        } else {
          stonesToKill[i][j] = false;
        }
      }
    }

    for (int i = 0; i < board.getWidth(); i++) {
      for (int j = 0; j < board.getHeight(); j++) {
        if (stonesToKill[i][j]) {
          board.removeStone(i, j);
          whitePlayer.addCaptive();
        }
      }
    }

    for (int i = 0; i < board.getWidth(); i++) {
      for (int j = 0; j < board.getHeight(); j++) {
        board.updateStone(i, j);
      }
    }

    passHistory.addAction();
    System.out.println("Biały gracz postawił pionka na polu " + command.x + " " + command.y);
    return State.BLACK_TURN;
  }

  @Override
  public State pass(CMDPass command) {
    if (command.playerId == blackPlayer.getId()) {
      System.out.println("To nie jest twoja tura >:(");
      return State.WHITE_TURN;
    }

    passHistory.addPass();
    if (passHistory.isGameOver()) {
      System.out.println("Koniec gry");
      return State.END_OF_GAME;
    }
    System.out.println("Biały gracz spasował");
    return State.BLACK_TURN;
  }
}
