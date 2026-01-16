package pl.pwr.student.gogame.model.utilities;

import java.io.Serializable;

import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.rules.Ruleset;

public record GameInfo(
    Board board,
    Ruleset ruleset,
    Player whitePlayer,
    Player blackPlayer,
    PassHistory passHistory) implements Serializable {}
