package hw2;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private int top;
    private int bottom;
    private int N;
    private int size;

    private WeightedQuickUnionUF fakeUf;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("input N required > 0");
        }
        this.N = N;
        size = 0;
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        fakeUf = new WeightedQuickUnionUF(N * N + 1);
        top = N * N;
        bottom = N * N + 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
    }

    private int xyToUfIndex(int x, int y) {
        return x * N + y;
    }


    // create N-by-N grid, with all sites initially blocked
    public void open(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException("row and col are between 0 ~ N - 1");
        }
        if (isOpen(row, col)) {
            return;
        }
        size++;
        grid[row][col] = true;
        //look up
        if (row > 0) {
            if (isOpen(row - 1, col)) {
                uf.union(xyToUfIndex(row, col), xyToUfIndex(row - 1, col));
                fakeUf.union(xyToUfIndex(row, col), xyToUfIndex(row - 1, col));
            }
        }
        //look down
        if (row < N - 1) {
            if (isOpen(row + 1, col)) {
                uf.union(xyToUfIndex(row, col), xyToUfIndex(row + 1, col));
                fakeUf.union(xyToUfIndex(row, col), xyToUfIndex(row + 1, col));
            }
        }
        //look left
        if (col > 0) {
            if (isOpen(row, col - 1)) {
                uf.union(xyToUfIndex(row, col), xyToUfIndex(row, col - 1));
                fakeUf.union(xyToUfIndex(row, col), xyToUfIndex(row, col - 1));
            }
        }
        //look right
        if (col < N - 1) {
            if (isOpen(row, col + 1)) {
                uf.union(xyToUfIndex(row, col), xyToUfIndex(row, col + 1));
                fakeUf.union(xyToUfIndex(row, col), xyToUfIndex(row, col + 1));
            }
        }
        if (row == 0) {
            uf.union(top, xyToUfIndex(row, col));
            fakeUf.union(top, xyToUfIndex(row, col));
        }
        if (row == N - 1) {
            uf.union(bottom, xyToUfIndex(row, col));
        }
//        if (isOpen(N - 1, col) && uf.connected(top, xyToUfIndex(N - 1, col))) {
//            uf.union(bottom, xyToUfIndex(N - 1, col));
//        }


/*        if (row == N - 1) {
            if (uf.connected(top, xyToUfIndex(row, col))) {
                uf.union(bottom, xyToUfIndex(row, col));
            }
            uf.union(bottom, xyToUfIndex(row, col));
        }*/
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return fakeUf.connected(xyToUfIndex(row, col), top);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return size;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(bottom, top);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }

}
