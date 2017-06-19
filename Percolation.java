import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
        private static final int full = 0;
        private static final int open = 1;
        private int N;
        private int[][] grid;
        private WeightedQuickUnionUF wquf;
        
        public Percolation(int n){
            N = n;
            grid = new int[N][N];
            
            wquf = new WeightedQuickUnionUF((N*N) + 2);
        }

public void open(int i, int j){
    int row = i-1;
    int col = j-1;
        
        if(row<1 || row>N || col<1 || col>N)
        throw new IndexOutOfBoundsException("Parameter Wrong");
    
    grid[row][col] = open;
        
        if(row == 0)
               wquf.union(0, xyTo1D(row, col));
           

if(row == (N-1)){
    wquf.union((N*N) + 1, xyTo1D(row, col));
               if((row+1) < N){
                   if(grid[row+1][col] == open)
                          wquf.union(xyTo1D(row, col), xyTo1D(row+1, col));
                      }
if((row-1) >= 0){
    if(grid[row-1][col] == open)
           wquf.union(xyTo1D(row, col), xyTo1D(row-1, col));
    }
    if((col+1)<N){
      if(grid[row][col+1]==open)
        wquf.union(xyTo1D(row, col), xyTo1D(row, col+1));
    }

    if((col-1) >= 0 ){
      if(grid[row][col-1] == open)
        wquf.union(xyTo1D(row, col), xyTo1D(row, (col+1)));
    } 
}
}

public boolean isOpen(int i, int j){
  int row = i-1;
  int col = j-1;
  if(row<0 || row>N || col<0 || col>N)
    throw new IndexOutOfBoundsException("Parameter Wrong");

  return grid[row][col] == open;
}

public boolean isFull(int i, int j){
  int row = i-1;
  int col = j-1;
  if(row<0 || row>N || col<0 || col>N)
    throw new IndexOutOfBoundsException("Parameter Wrong");
return grid[row][col] == full;
}

public boolean Percolates(){
  return wquf.connected(0, (N*N)+1);
}

private int xyTo1D(int i, int j){
  if(i<0 || i>N || j<0 || j>N)
    throw new IndexOutOfBoundsException("Wrong Parameter");
  return ((i*N) + j) + 1;
}
  private int count(){
    return wquf.count();
  }

  private int getN(){
    return N;
  }

  public static void main(String[] args) {
    System.out.println("Monte Carlo Simulation");
    final int testSize = 50;
    final int gridSize = 20;

    Percolation perco = new Percolation(gridSize);

    System.out.println("Opening sites at random");
    int row, col, 
    counter = 0;
    int totalSitesOpen = 0;

    for(int i = 0; i<testSize; i++){
        counter = 0;
        perco = new Percolation(gridSize);
        
        while(!perco.Percolates()){
            row = StdRandom.uniform(perco.getN()) + 1;
            col = StdRandom.uniform(perco.getN()) + 1;
            
            if(perco.isFull(row, col)){
                perco.open(row, col);
                
                counter++;
            }
            System.out.println("Countr reads:" +counter);
        }
        totalSitesOpen += counter;
    }
    
    System.out.println("After " + testSize + "attempts to open an average of" + (totalSitesOpen/testSize)/(gridSize*gridSize) * 100);


  }
}