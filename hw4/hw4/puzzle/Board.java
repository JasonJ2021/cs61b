package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    /*Constructs a board from an N-by-N array of tiles where
         tiles[i][j] = tile at row i, column j*/
    private int[][] tile;
    private final int BLANK = 0;
    private int N;

    public Board(int[][] tiles) {
        this.N = tiles.length;
        this.tile = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tile[i][j] = tiles[i][j];
            }
        }
    }


    /*Returns value of tile at row i, column j (or 0 if blank)*/
    public int tileAt(int i, int j) {
        return this.tile[i][j];
    }


    /*Returns the board size N*/
    public int size() {
        return N;
    }


    /*Returns the neighbors of the current board*/

    /***
     * @Source http://joshh.ug/neighbors.html
     * @return
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    private int xyToIndex(int row, int col) {
        return 1 + row * N + col;
    }

    /*Hamming estimate described below*/
    public int hamming() {
        int ham = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == BLANK) {
                    continue;
                } else if (tileAt(i, j) != xyToIndex(i, j)) {
                    ham++;
                }
            }
        }
        return ham;
    }

    private int actualRow(int index) {
        return (index - 1) / N;
    }

    private int actualCol(int index) {
        return (index - 1) % N;
    }

    /*Manhattan estimate described below*/
    public int manhattan() {
        int manhat = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == BLANK) {
                    continue;
                } else {
                    manhat += Math.abs(i - actualRow(tileAt(i, j)));
                    manhat += Math.abs(j - actualCol(tileAt(i, j)));
                }
            }
        }
        return manhat;
    }


    /*Estimated distance to goal. This method should
    simply return the results of manhattan() when submitted to
    Gradescope.*/
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /*Returns true if this board's tile values are the same
              position as y's*/
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (this.N != that.N) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tileAt(i, j) != that.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = tile.hashCode();
        return result;
    }
}
