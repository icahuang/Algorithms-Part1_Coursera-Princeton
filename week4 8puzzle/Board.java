import edu.princeton.cs.algs4.In;

import java.util.Iterator;

public class Board {
    int[][] tiles;
    int n; // the dimension of tiles is "n*n"

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new NullPointerException();
        }
        int row = tiles.length;
        int column = tiles[0].length;
        if (row != column) {
            throw new IllegalArgumentException();
        }
        n = row;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder outputString = new StringBuilder(dimension() + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                outputString.append(String.format("%3d", tiles[i][j]));
            }
            outputString.append("\n");
        }
        return outputString.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        int positionCount = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != positionCount) {
                    hamming++;
                }
                positionCount++;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                manhattan += singleTileManhattan(i, j);
            }
        }
        return manhattan;
    }

    private int singleTileManhattan(int row, int col) {
        // if (row < 0 || row > n || col < 0 || col > n) {
        //     throw new IndexOutOfBoundsException();
        // }

        int num = tiles[row][col];
        if (num == 0) {
            return 0;
        }

        int targetRow = (num - 1) / n;
        int targetCol = (num - 1) - targetRow * n;

        return Math.abs(row - targetRow) + Math.abs(col - targetCol);
    }

    // is this board the goal board?
    public boolean isGoal() {
        int count = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // if it's the goal board, the last element must be 0. It needs to be dealed with separately.
                if (i == n - 1 && j == n - 1) {
                    if (tiles[i][j] != 0) {
                        return false;
                    }
                }
                else if (tiles[i][j] != count) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }

    @Override
    // does this board equal y?
    public boolean equals(Object y) {
        Board that = (Board) y;
        int thatSize = that.dimension();
        if (n != thatSize) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new BoardIterator();
            }
        };
    }

    class BoardIterator implements Iterator<Board> {
        Board[] neighborsBoard;
        int currentCount = 0;
        int neighborsCount;

        BoardIterator() {
            int zeroRow = -1;
            int zeroCol = -1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (tiles[i][j] == 0) {
                        zeroRow = i;
                        zeroCol = j;
                        break;
                    }
                }
            }
            neighborsCount = 4;
            if (zeroRow == 0 || zeroRow == n - 1) {
                neighborsCount--;
            }
            if (zeroCol == 0 || zeroCol == n - 1) {
                neighborsCount--;
            }

            int count = 0;
            neighborsBoard = new Board[neighborsCount];
            if (zeroRow > 0) {
               int[][] upExchange = dulplicateTiles();
               exchange(upExchange, zeroRow, zeroCol, zeroRow - 1, zeroCol);
               neighborsBoard[count] = new Board(upExchange);
               count++;
            }
            if (zeroRow < n - 1) {
                int[][] downExchange = dulplicateTiles();
                exchange(downExchange, zeroRow, zeroCol, zeroRow + 1, zeroCol);
                neighborsBoard[count] = new Board(downExchange);
                count++;
            }
            if (zeroCol > 0) {
                int[][] leftExchange = dulplicateTiles();
                exchange(leftExchange, zeroRow, zeroCol, zeroRow, zeroCol - 1);
                neighborsBoard[count] = new Board(leftExchange);
                count++;
            }
            if (zeroCol < n - 1) {
                int[][] rightExchange = dulplicateTiles();
                exchange(rightExchange, zeroRow, zeroCol, zeroRow, zeroCol + 1);
                neighborsBoard[count] = new Board(rightExchange);
                count++;
            }
        }

        private int[][] dulplicateTiles() {
            int[][] newTiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    newTiles[i][j] = tiles[i][j];
                }
            }
            return newTiles;
        }

        private void exchange (int[][] a, int row1, int col1, int row2, int col2) {
            int exch = a[row1][col1];
            a[row1][col1] = a[row2][col2];
            a[row2][col2] = exch;
        }

        public boolean hasNext() {
            if (currentCount < neighborsCount) {
                return true;
            }
            return false;
        }

        public Board next() {
            return neighborsBoard[currentCount++];
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newBoardArr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoardArr[i][j] = tiles[i][j];
            }
        }
        int randomRow1 = (int) (Math.random() * n);
        int randomRow2 = (int) (Math.random() * n);
        int randomCol1 = (int) (Math.random() * n);
        int randomCol2 = (int) (Math.random() * n);
        int exch = newBoardArr[randomRow1][randomCol1];
        newBoardArr[randomRow1][randomCol1] = newBoardArr[randomRow2][randomCol2];
        newBoardArr[randomRow2][randomCol2] = exch;
        return new Board(newBoardArr);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        // int n = scanner.nextInt();
        // int[][] tiles = new int[n][n];
        // while(scanner.hasNext()) {
        //     for (int i = 0; i < n; i++) {
        //         for (int j = 0; j < n; j++) {
        //             tiles[i][j] = scanner.nextInt();
        //         }
        //     }
        // }

        In in = new In(args[0]);
        int n = in.readInt();

        int[][] tiles = new int[n][n];
        while(!in.isEmpty()) {
            for (int i = 0; i < n; i++) {
               for (int j = 0; j < n; j++) {
                   tiles[i][j] = in.readInt();
               }
            }
        }
        Board board = new Board(tiles);
        Board board1 = new Board(tiles);
        System.out.println(board);
        System.out.println("hamming(): " + board.hamming());
        System.out.println("manhattan(): " + board.manhattan());
        System.out.println("equals(): " + board.equals(board1));
        System.out.println("isGoal(): " + board.isGoal());
        System.out.println();

        System.out.println("twin():");
        Board newBoard = board.twin();
        System.out.println(newBoard);

        System.out.println("neighbors():");
        Iterable<Board> iter = board.neighbors();
        for (Board b : iter) {
            System.out.println(b);
        }
    }
}