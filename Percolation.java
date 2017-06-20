import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    // gridLength is the length of the square grid. 
    // There are gridLength^2 non-virtual sites in the grid
    private int gridLength;
    
     // two sites can percolate to one another if they are both open and connected:
    // isOpen monitors open/closed state of each site.
    private boolean[] isOpen;
    
    // percolation represents connectivity between sites. connected, open sites percolate to one another.
    private WeightedQuickUnionUF percolation;
    
    // quick union structure for tracking fullness without backwash.
    // similar to percolation above, but without bottom virtual site
    private WeightedQuickUnionUF fullness;
    
    // index of virtual site that is connected to entire top row, initializes to 0.
    private int virtualTopIndex; 
    
    // index of virtual site that is connected to entire bottom row, initializes to (n^2)+1
    private int virtualBottomIndex;
    
    // converts between two dimensional coordinate system and site array index. 
    // throws exceptions on invalid bounds. valid indices are 1 : n^2
    // a is the row; b is the column
    private int siteIndex(int a, int b)
    {
        checkBounds(a, b);
        int x = b;
        int y = a;
        return (y - 1) * gridLength + (x);
    }
    
    /*
     * By convention, the indices a and b are integers between 1 and n, where (1, 1) is the upper-left site: 
     * Throw a java.lang.IndexOutOfBoundsException if either a or b is outside this range. 
     */
    private void checkBounds(int a, int b)
    {
    if (a > gridLength || a < 1 )
    {
         throw new IndexOutOfBoundsException("row index a out of bounds");
    }
    if (b > gridLength || b < 1)
    {
         throw new IndexOutOfBoundsException("column index b out of bounds");
    }
    }
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n)
    {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        gridLength = n;
        int arraySize = n * n + 2;
        isOpen = new boolean[arraySize]; 
        
        virtualTopIndex = 0;
        virtualBottomIndex = (n * n) + 1;
        
        isOpen[virtualTopIndex] = true; /// open virtual top site
        isOpen[virtualBottomIndex] = true; /// open virtual bottom site
        
        percolation = new WeightedQuickUnionUF(arraySize);
        fullness = new WeightedQuickUnionUF(arraySize);
        for (int b = 1; b <= n; b++)
        {
            /// connect all top row sites to virtual top site
            int a = 1;
            int topSiteIndex = siteIndex(a, b);
            percolation.union(virtualTopIndex, topSiteIndex);
            fullness.union(virtualTopIndex, topSiteIndex);
            
            /// connect all bottom row sites to virtual bottom site
            a = n;
            int bottomSiteIndex = siteIndex(a, b);
            percolation.union(virtualBottomIndex, bottomSiteIndex);
            
        }
    };     
    
    // open site (row a, column b) if it is not already
    public void open(int a, int b)
    {
        int siteIndex = siteIndex(a,b);
        if (!isOpen[siteIndex])
        {
            /// to open a site, change boolean value, and union with any adjacent open sites
            isOpen[siteIndex] = true;

            // before connecting to a neighbor, first check that site is not on an edge, and is open
            if (b > 1 && isOpen(a, b - 1))
            {
                int indexToLeft = siteIndex(a, b - 1);
                percolation.union(siteIndex, indexToLeft);
                fullness.union(siteIndex, indexToLeft);
            }
            
            if (b < gridLength && isOpen(a, b + 1)) 
            {
                int indexToRight = siteIndex(a, b + 1);
                percolation.union(siteIndex, indexToRight);
                fullness.union(siteIndex,indexToRight);
            }
            
            if (a > 1 && isOpen(a - 1, b)) // site is not top edge
            {
                int indexToTop = siteIndex(a - 1, b);
                percolation.union(siteIndex, indexToTop);
                fullness.union(siteIndex,indexToTop);
            }
            
            if (a < gridLength && isOpen(a + 1, b)) /// site is not on bottom edge
            {
                int indexToBottom = siteIndex(a + 1, b);
                percolation.union(siteIndex, indexToBottom);
                fullness.union(siteIndex,indexToBottom);
            }
        }  
    };   
    
    // is site (row a, column b) open?
    //// openness represented by boolean value in isOpen array
    public boolean isOpen(int a, int b)
    {
        int siteIndex = siteIndex(a, b);
        return isOpen[siteIndex];
    }
    
    // is site (row a, column b) full?
    /// fullness represented by union with virtual top node
    public boolean isFull(int a, int b)
    {
        int siteIndex = siteIndex(a, b);
        //return (percolation.connected(virtualTopIndex,siteIndex) && isOpen[siteIndex]);
        return (fullness.connected(virtualTopIndex,siteIndex) && isOpen[siteIndex]);
    }  
    
    // does the system percolate?
    public boolean percolates() {
        if (gridLength > 1) {
            return percolation.connected(virtualTopIndex,virtualBottomIndex);
        }
        else {
            return isOpen[siteIndex(1,1)];
        }

    }
   
    public static void  main(String[] args)
    {
        Percolation percolation = new Percolation(1);
        StdOut.println(percolation.percolates());
        percolation.open(1,1);
        StdOut.println(percolation.percolates());
        Percolation percolation2 = new Percolation(2);
        StdOut.println(percolation2.percolates());
        percolation2.open(1,1);
        StdOut.println(percolation2.percolates());
        percolation2.open(2,1);
        StdOut.println(percolation2.percolates());
    }
}