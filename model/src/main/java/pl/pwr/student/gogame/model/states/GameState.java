package pl.pwr.student.gogame.model;

public interface GameState {
    public static GameState enter();
    public static GameState inside();
    public static GameState exit();
}