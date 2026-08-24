package game;

public class Line {
    private final GameBean.GameState[] lineDatas;
    private final int lineIndex;

    public Line(GameBean.GameState[] lineDatas, int lineIndex) {
        this.lineDatas = lineDatas;
        this.lineIndex = lineIndex;
    }

    public GameBean.GameState[] getDatas() {
        return lineDatas;
    }

    public int getIndex() {
        return lineIndex;
    }
}