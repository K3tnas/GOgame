// package pl.pwr.student.gogame.model.states;
//
// import pl.pwr.student.gogame.model.Player;
// import pl.pwr.student.gogame.model.board.Board;
// import pl.pwr.student.gogame.model.commands.Command;
// import pl.pwr.student.gogame.model.commands.CMDMove;
// import pl.pwr.student.gogame.model.rules.RuleSet;
//
// public abstract class GameMachine {
// Player blackPlayer, whitePlayer;
// GameState gameState;
// Board board;
// RuleSet ruleSet;
// Boolean[] passMemory = { false, false };
//
// public void handleRequest(Command cmd) {
// if (gameState.getCurrentPlayerId(blackPlayer, whitePlayer) != cmd.playerId) {
// return;
// }
//
// switch (cmd.commandType) {
// case MOVE:
// putPawnOnTheBoard(((CMDMove) cmd).x, ((CMDMove) cmd).y);
// break;
// case PASS:
// pass();
// break;
// default:
// break;
// }
// }
//
// protected void putPawnOnTheBoard(int x, int y) {
// if (!board.isInside(x, y)) {
// return;
// }
//
// boolean isBlack = false;
//
// if (gameState.geCurrentState() instanceof BlackTurn) {
// isBlack = true;
// }
//
// if (!ruleSet.meetsRules(board, x, y, isBlack)) {
// return;
// }
//
// board.setStone(x, y, isBlack);
// updateBoard(isBlack);
//
// passMemory[0] = passMemory[1];
// passMemory[1] = false;
// }
//
// protected void updateBoard(boolean isBlack) {
// for (int i = 0; i < board.getWidth(); i++) {
// for (int j = 0; j < board.getHeight(); j++) {
// board.updateStone(i, j);
// // TODO: w przyszłości update aby zbijał
// // łańcuchy oraz tylko przeciwnika
// if (board.getStone(i, j).isBreathless()) {
// if (isBlack) {
// blackPlayer.addCaptive();
// } else {
// whitePlayer.addCaptive();
// }
// }
// }
// }
// }
//
// private void pass() {
// passMemory[0] = passMemory[1];
// passMemory[1] = true;
//
// if (passMemory[0]) {
// // TODO:
// endGame();
// }
// }
// }
