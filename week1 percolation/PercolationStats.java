import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trialsCount;         // The times of trial experiments
    private final double[] fractions;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trails) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be a positive number.");
        }
        if (trails <= 0) {
            throw new IllegalArgumentException("trials should be a positive number.");
        }
        this.trialsCount = trails;
        fractions = new double[trails];

        int row;
        int col;
        for (int i = 0; i < trialsCount; i++) {
            Percolation perInstance = new Percolation(n);
            while (!perInstance.percolates()) {
                row = StdRandom.uniform(1, n + 1);  // Randomly return a integer from [1, n - 1)
                col = StdRandom.uniform(1, n + 1);
                perInstance.open(row, col);
            }
            fractions[i] = (double) perInstance.numberOfOpenSites() / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (trialsCount == 1) return Double.NaN;
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(trialsCount);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(trialsCount);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationInstance = new PercolationStats(n, trials);

        // Print results
        StdOut.println("mean                    =" + percolationInstance.mean());
        StdOut.println("stddev                  =" + percolationInstance.stddev());
        StdOut.println(
                "95% confidence interval = [" + percolationInstance.confidenceLo() + ", "
                        + percolationInstance.confidenceHi()
                        + "]");
    }
}
