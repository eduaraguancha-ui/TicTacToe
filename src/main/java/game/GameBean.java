package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBean {
    private static final int GRID_SIZE = 3;

    public enum GameState {
        NULL, O, X
    }

    public enum GamePlayer {
        USER(GameState.X),
        COMPUTER(GameState.O),
        NOBODY(GameState.NULL);

        private final GameState state;

        GamePlayer(GameState state) {
            this.state = state;
        }

        public GameState getState() {
            return state;
        }
    }

    private boolean userFirst = true;
    private final GameState[][] gameStatus;
    private static final Random rand = new Random();

    public GameBean() {
        this.gameStatus = new GameState[GRID_SIZE][GRID_SIZE];
        startGame();
    }

    public void setStartByUser(boolean userFirst) {
        this.userFirst = userFirst;
    }

    public void startGame() {
        for (int line = 0; line < GRID_SIZE; line++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                gameStatus[line][col] = GameState.NULL;
            }
        }

        // si inicia el computador, juega centro (si está libre)
        if (!userFirst) {
            play(GamePlayer.COMPUTER, 1, 1);
        }
    }

    public void playPlayerTurn(int line, int col) {
        play(GamePlayer.USER, line, col);
    }

    public void playComputerTurn() {
        if (!hasEmptyCell()) return;

        int line = getRandomLineIndexWithEmptyCell();
        int col = getRandomEmptyCell(line);
        play(GamePlayer.COMPUTER, line, col);
    }

    private void play(GamePlayer player, int line, int col) {
        if (line < 0 || line >= GRID_SIZE || col < 0 || col >= GRID_SIZE) return;
        if (gameStatus[line][col] != GameState.NULL) return;

        gameStatus[line][col] = player.getState();
    }

    public GamePlayer getWinner() {for (int i = 0; i < GRID_SIZE; i++) {
        GameState first = gameStatus[i][0];

        if (first == GameState.NULL) continue;

        boolean win = true;

        for (int j = 1; j < GRID_SIZE; j++) {
            if (gameStatus[i][j] != first) {
                win = false;
                break;
            }
        }

        if (win) return getPlayer(first);
    }

    // Revisar columnas
    for (int j = 0; j < GRID_SIZE; j++) {
        GameState first = gameStatus[0][j];

        if (first == GameState.NULL) continue;

        boolean win = true;

        for (int i = 1; i < GRID_SIZE; i++) {
            if (gameStatus[i][j] != first) {
                win = false;
                break;
            }
        }

        if (win) return getPlayer(first);
    }

    // Revisar diagonal principal
    GameState firstDiag = gameStatus[0][0];
    if (firstDiag != GameState.NULL) {
        boolean win = true;
        for (int i = 1; i < GRID_SIZE; i++) {
            if (gameStatus[i][i] != firstDiag) {
                win = false;
                break;
            }
        }
        if (win) return getPlayer(firstDiag);
    }

    // Revisar diagonal secundaria
    GameState firstAntiDiag = gameStatus[0][GRID_SIZE - 1];
    if (firstAntiDiag != GameState.NULL) {
        boolean win = true;
        for (int i = 1; i < GRID_SIZE; i++) {
            if (gameStatus[i][GRID_SIZE - 1 - i] != firstAntiDiag) {
                win = false;
                break;
            }
        }
        if (win) return getPlayer(firstAntiDiag);
    }

    return GamePlayer.NOBODY;
    }

    private GamePlayer getPlayer(GameState state) {
        for (GamePlayer p : GamePlayer.values()) {
            if (p.getState() == state) return p;
        }
        return GamePlayer.NOBODY;
    }

    public boolean hasEmptyCell() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (gameStatus[i][j] == GameState.NULL) return true;
            }
        }
        return false;
    }

    private int getRandomLineIndexWithEmptyCell() {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (gameStatus[i][j] == GameState.NULL) {
                    indexes.add(i);
                    break;
                }
            }
        }
        return indexes.get(rand.nextInt(indexes.size()));
    }

    private int getRandomEmptyCell(int line) {
        List<Integer> indexes = new ArrayList<>();
        for (int j = 0; j < GRID_SIZE; j++) {
            if (gameStatus[line][j] == GameState.NULL) {
                indexes.add(j);
            }
        }
        return indexes.get(rand.nextInt(indexes.size()));
    }

    // Para el JSP
    public List<Line> getGridLines() {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            lines.add(new Line(gameStatus[i], i));
        }
        return lines;
    }

    public List<Cell> getGridStatus(Line line) {
        List<Cell> cells = new ArrayList<>();
        GameState[] datas = line.getDatas();
        for (int j = 0; j < datas.length; j++) {
            cells.add(new Cell(datas[j], line.getIndex(), j));
        }
        return cells;
    }
}