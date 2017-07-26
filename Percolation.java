import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {          //0 mans full, 1 mans open
        private static final int full = 0;  //all sites full at beginning
        private static final int open = 1;  //0 sites open at beginning
        private int N;
        private int[][] grid;
        private WeightedQuickUnionUF wquf;  //object of imported class
        
        public Percolation(int n){
            N = n;
            grid = new int[N][N];   //creation of N*N array of grid
            
            wquf = new WeightedQuickUnionUF((N*N) + 2);   //total no of sites = sites in grid + open site
        }

public void open(int i, int j){      //to open up sites
    int row = i-1;
    int col = j-1;
        
        if(row<1 || row>N || col<1 || col>N)  //not within array
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
  return wquf.connected(0, (N*N)+1);     //checks whether all the sites are connected
}

private int xyTo1D(int i, int j){ //to convert 2dimensional array to 1dim.
  if(i<0 || i>N || j<0 || j>N)
    throw new IndexOutOfBoundsException("Wrong Parameter");
  return ((i*N) + j) + 1;  //e.g grid [2][2] translates to "18" for a 5 by 5 grid 
}
  private int count(){
    return wquf.count();
  }

  private int getN(){
    return N;
  }

  public static void main(String[] args) {
    System.out.println("Monte Carlo Simulation");
    final int testSize = 1000;
    final int gridSize = 20;

    Percolation perco = new Percolation(gridSize);  //new Percolation objct

    System.out.println("Opening sites at random");
    int row, col, counter;
    counter = 0;
    int totalSitesOpen = 0;

    for(int i = 0; i<testSize; i++){    //run the number of tests supplied by user
        counter = 0;  //start counting
        perco = new Percolation(gridSize);
        
        while(!perco.Percolates()){
            row = StdRandom.uniform(perco.getN()) + 1;  //chosing sites randomly to open
            col = StdRandom.uniform(perco.getN()) + 1;
            
            if(perco.isFull(row, col)){    //if the site is ful
                perco.open(row, col);      //then open it up
                
                counter++;                 //add to open sites
            }
            System.out.println("Countr reads:" +counter);
        }
        totalSitesOpen += counter;        
    }
    
    System.out.println("After " + testSize + "attempts to open an average of" + (totalSitesOpen/testSize)/(gridSize*gridSize) * 100);
    System.out.println("nUMBER of sites opened :" +counter/testSize);

  }
}