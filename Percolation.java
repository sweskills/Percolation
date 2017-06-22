import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   public boolean[][] grid;
   private int nos  = N;
   public WeightedQuickUnionUF connections;
   private StdRandom randomGenerator;
   
   public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        nos = N;
        boolean[][] grid = new boolean[N][N];
        connect = new WeightedQuickUnionUF(N * N);
        al = new ArrayList<Integer>();
        randomGenerator = new Random();
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        
   }
   
   private int toNum(int row, int col){
       int num = col + ((row-1) * N) -1;
       return num;
   }    
   public void open(int row, int col) {    // open site (row, col) if it is not open already
       grid[i - 1][j - 1] = true;         // (i - 1, j - 1) is the site I just opened
           if (i - 2 >= 0 && isOpen(i - 1, j)) { // left
               connect.union((j - 1) * nos + i - 1, (j - 1) * nos + i - 2);
           }
           if (i < nos && isOpen(i + 1, j)) // right
           {
               connect.union((j - 1) * nos + i - 1, (j - 1) * nos + i);
           }
           if (j - 2 >= 0 && isOpen(i, j - 1)) // up
           {
               connect.union((j - 2) * nos + i - 1, (j - 1) * nos + i - 1); 
           }
           if (j < nos && isOpen(i, j + 1)) // down
           {
               connect.union((j - 1) * nos + i - 1, j * nos + i - 1);
           }
    }
   public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        return grid[i - 1][j - 1];
    }
   public boolean isFull(int row, int col)  // is site (row, col) full?
   {
        return grid[i - 1][j - 1] == false;
   }     

   public int numberOfOpenSites(){       // number of open sites
       return al.length();
   }    
   public boolean percolates(){              // does the system percolate?
       for (int i = (nos * (nos - 1)); i < (nos * nos); i++) {
            for (int i2 = 0; i2 < nos; i2++) {
                if (connect.connected(i, i2)) return true;
            }
        }
        return false;
   }    

   public static void main(String[] args){   // test client (optional)
       int N = 10;
        Percolation p = new Percolation(N);
        for (int j = 0; j <= N - 1; j++) {
            for (int i = 0; i <= N - 1; i++) {
                 System.out.print(p.grid[i][j]);
                 System.out.print("\t");
            }
            System.out.print("\n");
        }
    
   }    
}