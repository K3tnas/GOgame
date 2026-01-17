package pl.pwr.student.gogame.model.states;

import java.util.ArrayList;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;
import pl.pwr.student.gogame.model.utilities.GameInfo;
import pl.pwr.student.gogame.model.utilities.Player;
import pl.pwr.student.gogame.model.utilities.ProposedDeadFields;

public class Proposition extends GameState {
  private boolean whiteAccepted, blackAccepted;

  public Proposition(GameInfo gameInfo) {
    super(gameInfo);
    whiteAccepted = false;
    blackAccepted = false;
    gameInfo.pdf().clear();
  }

  @Override
  public GameState proposeDeadField(int x, int y, String id) {
    final Board b = gameInfo.board();
    if (b.getField(x, y).getTeam() != getTeamByPlayerId(id)) {
      gameInfo.pdf().addStoneToKill(x, y, getTeamByPlayerId(id));
    }
    return this;
  }

  @Override
  public GameState acceptProposition(String id) {
    if (getTeamByPlayerId(id) == Team.WHITE) whiteAccepted = true;
    else blackAccepted = true;

    if (whiteAccepted && blackAccepted) return chooseWinner();
    return this;
  }

  @Override
  public GameState declineProposition(String id) {
    (getTeamByPlayerId(id) == Team.WHITE ? gameInfo.whitePlayer() : gameInfo.blackPlayer())
        .setMayPass(false);
    return getTeamByPlayerId(id) == Team.WHITE ? new WhiteTurn(gameInfo) : new BlackTurn(gameInfo);
  }

  @Override
  public GameState putStone(int x, int y, String id) {
    return this;
  }

  @Override
  public GameState pass(String id) {
    return this;
  }

  private GameState chooseWinner() {
    final Board b = gameInfo.board();
    final ProposedDeadFields pdf = gameInfo.pdf();
    final ArrayList<int[]> wList = pdf.getWhiteStonesToKill();
    final ArrayList<int[]> bList = pdf.getBlackStonesToKill();
    final Player wp = gameInfo.whitePlayer();
    final Player bp = gameInfo.blackPlayer();
    final int size = b.getSize();
    Team t;

    for (int[] f : wList) {
      b.getField(f[0], f[1]).setTeam(Team.EMPTY);
      wp.addCaptive();
    }

    for (int[] f : bList) {
      b.getField(f[0], f[1]).setTeam(Team.EMPTY);
      bp.addCaptive();
    }

    for (int i = 1; i <= size; i++) {
      for (int j = 1; j <= size; j++) {
        if (b.getField(i, j).getTeam() == Team.EMPTY) {
          t = b.getField(i, j).whoseTerritory();
          if (t != Team.EMPTY) getPlayerByTeam(t).addCaptive();
        }
      }
    }

    if (gameInfo.blackPlayer().getCaptives() == gameInfo.whitePlayer().getCaptives())
      return new Tie(gameInfo);
    return gameInfo.blackPlayer().getCaptives() < gameInfo.whitePlayer().getCaptives()
        ? new WhitePlayerWon(gameInfo)
        : new BlackPlayerWon(gameInfo);
  }
}
