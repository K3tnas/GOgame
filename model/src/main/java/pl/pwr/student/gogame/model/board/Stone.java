package pl.pwr.student.gogame.model.board;

public abstract class Stone {
    private Integer allyCount = 0;
    private Integer enemyCount = 0;
    
    public Integer breathCount() {
        return 4 - allyCount - enemyCount;
    }
}