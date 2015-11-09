package test.worksApplication;

/**
 * Created by river on 15/10/27.
 */
public class Game2 {

    private static boolean isBlocked = true;
    private static int maxGamePoints = -1;

    public static boolean isBlocked() {
        return isBlocked;
    }

    public static int getMaxGamePoints() {
        return maxGamePoints;
    }

    public static void playGame(int[][] map, boolean[][] visited, int row, int colunm, int gamePoints) {
        int cellPoints = map[row][colunm];
        if (!visited[row][colunm] && cellPoints != -1) {
            gamePoints += cellPoints;
            visited[row][colunm] = true;

            if (colunm == map[row].length - 1) {
                isBlocked = false;
                maxGamePoints = maxGamePoints < gamePoints ? gamePoints : maxGamePoints;
            }

            if (canMoveDown(map, visited, row, colunm)) {
                if (row < map.length - 1) {
                    playGame(map, visited, row + 1, colunm, gamePoints);
                } else {
                    playGame(map, visited, 0, colunm, 0);
                }
            }

            if (canMoveUp(map, visited, row, colunm)) {
                if (row > 0) {
                    playGame(map, visited, row - 1, colunm, gamePoints);
                } else {
                    playGame(map, visited, map.length - 1, colunm, 0);
                }
            }

            if (canMoveRight(map, row, colunm)) {
                playGame(map, visited, row, colunm + 1, gamePoints);
            }

            visited[row][colunm] = false;

        } else {
            return;
        }
    }

    private static boolean canMoveUp(int[][] map, boolean[][] visited, int i, int j) {
        int bottom = map.length - 1;
        if (i > 0)
            return map[i - 1][j] != -1 && visited[i - 1][j] != true;
        else
            return map[bottom][j] != -1 && visited[bottom][j] != true;
    }

    private static boolean canMoveDown(int[][] map, boolean[][] visited, int i, int j) {
        if (i < map.length - 1)
            return map[i + 1][j] != -1 && visited[i + 1][j] != true;
        else
            return map[0][j] != -1 && visited[0][j] != true;
    }

    private static boolean canMoveRight(int[][] map, int i, int j) {
        return (j < map[i].length - 1 && map[i][j + 1] != -1);
    }
}


