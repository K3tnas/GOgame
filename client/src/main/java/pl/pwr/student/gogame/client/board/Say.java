package pl.pwr.student.gogame.client.board;

public class Say implements GUICommand {
    private String message;

    public Say(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SAY," + message + ";";
    }
}
