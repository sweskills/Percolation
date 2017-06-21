
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import princeton.cs.algs4.WeightedQuickUnionUF;


 // given an n-by-n matrix of open sites, return an n-by-n matrix
    // of sites reachable from the top
  public boolean isOpen(int row, int col){
    //public static boolean[n][n] flow(boolean[][] isOpen) {
        string  n =is0pen.length();
        boolean[][] isFull = new boolean[n][n];
        for (int j = 0; j < n; j++) {
            flow(isOpen, isFull, 0, j);
        }
        return isFull;
    }
// determine set of full sites using depth first search
    public static void flow(boolean[][] isOpen, boolean[][] isFull, int i, int j) {
        int n = isOpen.length;

        // base cases
        if (i < 0 || i >= n) return;    // invalid row
        if (j < 0 || j >= n) return;    // invalid column
        if (!isOpen[i][j]) return;      // not an open site
        if (isFull[i][j]) return;       // already marked as full

        // mark i-j as full
        isFull[i][j] = true;

        flow(isOpen, isFull, i+1, j);   // down
        flow(isOpen, isFull, i, j+1);   // right 
        flow(isOpen, isFull, i, j-1);   // left
        flow(isOpen, isFull, i-1, j);   // up
    }


    // does the system percolate?
    public static boolean percolates(boolean[][] isOpen) {
        int n = isOpen.length;
        boolean[][] isFull = flow(isOpen);
        for (int j = 0; j < n; j++) {
            if (isFull[n-1][j]) return true;
        }
        return false;
    }

// draw the n-by-n boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int n = a.length;
        StdDraw.setXscale(-1, n);
        StdDraw.setYscale(-1, n);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (a[i][j] == which)
                    StdDraw.filledSquare(j, n-i-1, 0.5);
    }

    // return a random n-by-n boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int n, double p) {
        boolean[][] a = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }

    // test client
    public static void main(String[] args) {
        boolean[][] isOpen = StdArrayIO.readBoolean2D();
        StdArrayIO.print(flow(isOpen));
        StdOut.println(percolates(isOpen));
    }
}

