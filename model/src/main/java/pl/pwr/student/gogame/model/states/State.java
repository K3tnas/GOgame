package pl.pwr.student.gogame.model.states;

public enum State {
    BLACK_TURN(0),
    WHITE_TURN(1),
    END_OF_GAME(2);

    public Integer idx;

    State(Integer idx) {
        this.idx = idx;
    }
}
