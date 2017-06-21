import edu.princeton.cs.algs4.StdRandom;
import  edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  // UF used for percolates test
  private WeightedQuickUnionUF PercolateTest;   

  // UF used for connection site test
  // open site in the top row
  private WeightedQuickUnionUF TestFull;

  // siteStatus stores every site's open or block
  // true means open,false means block
  private boolean[] siteStatus;

  // percolation experiment's grid width and height
  private int SIZE;

  // 1D of site connected to all sites in first row
  private int H20_SURFACE;

  // 1D of site connected to all sites in last row
  private int OIL_BELOW;

  // create N-by-N grid, with all sites blocked
  public Percolation(int N) {
    SIZE = N;

    // the last two site for H20_SURFACE and OIL_BELOW
    PercolateTest = new WeightedQuickUnionUF(SIZE * SIZE + 2);

    // the last site is for H20_SURFACE
    TestFull = new WeightedQuickUnionUF(SIZE * SIZE + 1);

    H20_SURFACE = SIZE * SIZE;
    OIL_BELOW = SIZE * SIZE + 1;

    // initialize all site status blocked
    siteStatus = new boolean[SIZE*SIZE + 2];
    for (int i = 0; i < SIZE*SIZE; i++) {
      siteStatus[i] = false;
    }

    // initialize the two sites open
    siteStatus[H20_SURFACE] = true;
    siteStatus[OIL_BELOW] = true;

    // for the UF for percolate test and the UF for test full
    // connect each site in first row to H20_SURFACE
    for (int i = 0; i < SIZE; i++) {
      PercolateTest.union(H20_SURFACE, i);
      TestFull.union(H20_SURFACE, i);
    }

    // only for the UF used for percolate test
    // connect each site in last row to OIL_BELOW
    for (int i = (SIZE-1)*SIZE; i < SIZE*SIZE; i++) {
      PercolateTest.union(OIL_BELOW, i);
    }

  }

  // open site (row i, column j) if it is not already
  // i,j between 1 and N
  public void open(int i, int j) {
    if (!validIndex(i, j)) {
      throw new IndexOutOfBoundsException("row or/and column index out of bounds");
    }

    if (!isOpen(i, j)) {
      openSite(i, j);
      connectAdjacentSite(i, j);
    }
  }

  private void openSite(int i, int j) {
    int idx = xyTo1D(i, j);
    siteStatus[idx] = true;
  }

  private void connectAdjacentSite(int i, int j) {
    connectTwoSite(i, j, i-1, j);
    connectTwoSite(i, j, i+1, j);
    connectTwoSite(i, j, i  , j-1);
    connectTwoSite(i, j, i  , j+1);
  }

  private void connectTwoSite(int x1, int y1, int x2, int y2) {
    if (validIndex(x2, y2)) {
      if (isOpen(x2, y2)) {
        int p = xyTo1D(x1, y1);
        int q = xyTo1D(x2, y2);
        PercolateTest.union(p, q);
        TestFull.union(p, q);
      }
    }
  }

  /** is site (row i, column j) open? */
  public boolean isOpen(int i, int j) {
    if (!validIndex(i, j)) {
      throw new IndexOutOfBoundsException("row or/and column index out of bounds");
    }
    int n = xyTo1D(i, j);
    return siteStatus[n];
  }

  /** is site (row i, column j) full? */
  public boolean isFull(int i, int j) {
    if (!validIndex(i, j)) {
      throw new IndexOutOfBoundsException("row or/and column index out of bounds");
    }
    int p = xyTo1D(i, j);
    return isOpen(i, j) && TestFull.connected(p, H20_SURFACE);
  }

  /** does the system percolate? */
  public boolean percolates() {
    if (SIZE == 1) return isOpen(1, 1); // used for 1x1 grid
    return PercolateTest.connected(OIL_BELOW, H20_SURFACE);
  }

  // convert 2 dimensional(row, column) pair to 1 dimensional index
  private int xyTo1D(int r, int c) {
    return ((r - 1) * SIZE + c) - 1;
  }

  private boolean validIndex(int r, int c) {
    if (r < 1 || r > SIZE || c < 1 || c > SIZE) {
      return false;
    }
    return true;
  }
