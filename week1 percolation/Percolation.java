import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private final int n, top, bottom;
    private int openCount;  // Store the numbers of open sites
    private boolean[][] openSites;
    // Store the states of every site if it's open or blocked. Size == n*n
    private final WeightedQuickUnionUF ufInstance;

    // Constructor
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be a positive number.");
        }

        // If there's no exception pop up, construct the instance and initial all cells as false
        this.n = n;
        openSites = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                openSites[i][j] = false;
            }
        }

        // The 0 is the top node. The (n*n+1) is the bottom node.
        top = 0;
        bottom = n * n + 1;
        openCount = 0;
        ufInstance = new WeightedQuickUnionUF(n * n + 2);
    }

    private void checkBoundary(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("The row parameter must be between 1 and n.");
        }
        if (col < 1 || col > n) {
            throw new IllegalArgumentException("The column parameter must be between 1 and n.");
        }
    }

    // translate (row, col) to 1 dimension for union-find set to access the specific data
    private int rowColTo1dUF(int row, int col) {
        checkBoundary(row, col);
        return (row - 1) * n + col;
    }

    // opens the site (row, col) if it is not open already
    // the range of row and col are both [1, n]
    public void open(int row, int col) {
        checkBoundary(row, col);

        // Set the site open and add 1 to the total numbers of open sites
        if (!isOpen(row, col)) {
            openSites[row - 1][col - 1] = true;
            openCount++;
        }
        else return;

        // The next part is to update the union-find set
        if (n == 1) {
            ufInstance.union(top, rowColTo1dUF(1, 1));
            ufInstance.union(bottom, rowColTo1dUF(1, 1));
            return;
        }

        if (row > 1 && row < n) {
            // Connect the next up node
            if (isOpen(row - 1, col))
                ufInstance.union(rowColTo1dUF(row, col), rowColTo1dUF(row - 1, col));
            // Connect the next down node
            if (isOpen(row + 1, col))
                ufInstance.union(rowColTo1dUF(row, col), rowColTo1dUF(row + 1, col));
        }
        if (row == 1) {
            // Connect with the top node
            ufInstance.union(rowColTo1dUF(row, col), top);
            // Connect with the next down node
            if (isOpen(row + 1, col))
                ufInstance.union(rowColTo1dUF(row, col), rowColTo1dUF(row + 1, col));
        }
        if (row == n) {
            // Connect with the bottom node
            ufInstance.union(rowColTo1dUF(row, col), bottom);
            // Connect with the next up node
            if (isOpen(row - 1, col))
                ufInstance.union(rowColTo1dUF(row, col), rowColTo1dUF(row - 1, col));
        }

        // If this open site is not on the first column, then see if the left site is open or not
        if (col > 1 && col < n) {
            if (isOpen(row, col - 1))
                ufInstance.union(rowColTo1dUF(row, col), rowColTo1dUF(row, col - 1));
            if (isOpen(row, col + 1))
                ufInstance.union(rowColTo1dUF(row, col), rowColTo1dUF(row, col + 1));
        }
        if (col == 1) {
            if (isOpen(row, col + 1))
                ufInstance.union(rowColTo1dUF(row, col), rowColTo1dUF(row, col + 1));
        }
        if (col == n) {
            if (isOpen(row, col - 1))
                ufInstance.union(rowColTo1dUF(row, col), rowColTo1dUF(row, col - 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBoundary(row, col);

        return (openSites[row - 1][col - 1]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBoundary(row, col);
        return ufInstance.find(rowColTo1dUF(row, col)) == ufInstance.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1) {
            return openSites[0][0];
        }
        return ufInstance.find(top) == ufInstance.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        // int n = Integer.parseInt(args[0]);
        // Percolation per = new Percolation(n);

        // test model with 3x3 cells
        int n = 5;
        Percolation per = new Percolation(n);
        for (int i = 0; i < n; i++) {
            per.open(i + 1, 2);
            StdOut.println(per.numberOfOpenSites());
        }

        if (per.percolates()) StdOut.println("Percolation Success");
        else StdOut.println("Percolation Fail");

        int count = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (per.isOpen(i, j) != per.isFull(i, j)) {
                    count++;
                    StdOut.println("isOpen != isFull count: " + count);
                    StdOut.println("row: " + i + "; col: " + j);
                    StdOut.println("isOpen(i, j): " + per.isOpen(i, j));
                    StdOut.println("isFull(i, j): " + per.isFull(i, j));
                }
            }
        }

        // StdOut.println("per (0): " + per.find(0));
        // for (int i = 1; i <= n; i++) {
        //     StdOut.println("per (" + i + "): " + per.find(i));
        // }
        // StdOut.println("per (" + (n * n + 1) + "): " + per.find(n * n + 1));
        // for (int i = 1; i <= n; i++) {
        //     StdOut.println("per (" + ((n - 1) * n + i) + "): " + per.find((n - 1) * n + i));
        // }
    }
}
