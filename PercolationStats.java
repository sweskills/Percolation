import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    
    // holds each experiment's percolation threshold result
   private double[] thresholdResults;
   
   private int T;
   // perform T independent computational experiments on an n-by-n grid
   
   public PercolationStats(int n, int T) 
   {
       ///The constructor should throw a java.lang.IllegalArgumentException if either n <= 0 or T >= 0.
       if (n < 1 || T < 1)
       {
           throw new IllegalArgumentException("both arguments n and T must be greater than 1");
       }
       
       this.T = T;
       thresholdResults = new double[T];
       for (int t = 0; t < T; t++)
       {
            Percolation percolation = new Percolation(n);
            int openSites = 0;
            while (!percolation.percolates())
            {
                int a = StdRandom.uniform(1, n+1);
                int b = StdRandom.uniform(1, n+1);
           
                if (!percolation.isOpen(a, b))
                {
                    percolation.open(a, b);
                    openSites += 1;
                }
            }
            double threshold = (double)openSites/(double)(n * n);
           thresholdResults[t] = threshold;
       }
   }
   
   // sample mean of percolation threshold
   public double mean() 
   {
       return StdStats.mean(thresholdResults);
   }
   
   // sample standard deviation of percolation threshold
   public double stddev()  
   {
       return StdStats.stddev(thresholdResults);
   }
   
   // returns lower bound of the 95% confidence interval
   public double confidenceLo()  
   {
       return mean() - (1.96 * stddev() / Math.sqrt(T));
   }
   
   // returns upper bound of the 95% confidence interval
   public double confidenceHi()             
       {
       return mean() + (1.96 * stddev() / Math.sqrt(T));
   }
   
   public static void main(String[] args)
   {
       int n = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);
       PercolationStats stats = new PercolationStats(n, T);
       StdOut.println("mean = "+ stats.mean());
       StdOut.println("standard deviation = "+ stats.stddev());
       StdOut.println("95% confidence interval = "+ stats.confidenceLo() + " , " + stats.confidenceHi());
   }     
     
}