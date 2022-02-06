package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] x;
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException("Requirement: N > 0 && T >0");
        Percolation perco;
        x = new double[T];
        this.T = T;
        for (int i = 0; i < T; i++) {
            perco = pf.make(N);
            while (!perco.percolates()) {
                int rowRandom = StdRandom.uniform(N);
                int colRandom = StdRandom.uniform(N);
                perco.open(rowRandom, colRandom);
            }
            x[i] = ((double) perco.numberOfOpenSites()) / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
