
public class PercolationStats {
	private int gridDimension; // number of columns/rows in N x N grid
	private int numberOfExperiment; // number of times to run stats
	private double[] results; // stores results of percolation experiments
	private boolean print_grid = false;

	/**
	 * Runs T percolation experiments on an N x N grid, and collects the results
	 * (percolation threshold of each experiment)
	 * 
	 * @param N
	 *            the number of nodes in a single row/column of an NxN grid
	 * @param T
	 *            the number of percolation experiments to run
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0)
			throw new IllegalArgumentException("N&T cannot be less than 1 ");
		// loop through numberOfExperiment times
		gridDimension = N;
		numberOfExperiment = T;
		double total_blocks = gridDimension * gridDimension;
		results = new double[numberOfExperiment];
		for (int x = 0; x < numberOfExperiment; x++) {
			Percolation sample = new Percolation(gridDimension);
			int open_count = 0;
			// test for percolation after every
			// return count number of opens that gave percolation
			while (!sample.percolates()) {
				// initialize and generate random opens in grid
				int i = StdRandom.uniform(1, gridDimension + 1);
				int j = StdRandom.uniform(1, gridDimension + 1);
				if (!sample.isOpen(i, j)) {
					sample.open(i, j);
					open_count++;
				}
			}
			results[x] = open_count / total_blocks;
			if (print_grid) {
				sample.printGrid();
				System.out.println(" ");
				System.out.println("perculates at:" + open_count);
			}
		}
	}

	/**
	 * sample mean of percolation threshold
	 * 
	 * @return mean of the threshold of all samples
	 */
	public double mean() {
		return StdStats.mean(results);
	}

	/**
	 * sample standard deviation of percolation threshold
	 * 
	 * @return standard deviation of the threshold of all samples
	 */
	// 
	public double stddev() {
		return StdStats.stddev(results);
	}

	/**
	 * Returns lower bound of the 95% confidence interval.
	 */
	public double confidenceLo() {
		return mean() - ((1.96 * stddev()) / Math.sqrt(numberOfExperiment));
	}

	/**
	 * Returns upper bound of the 95% confidence interval.
	 */
	public double confidenceHi() {
		return mean() + ((1.96 * stddev()) / Math.sqrt(numberOfExperiment));
	}

	/**
	 * test client
	 */
	public static void main(String[] args) {
		// PercolationStats pStat = new PercolationStats(
		// Integer.parseInt(args[0]),
		// Integer.parseInt(args[1])
		// );
		PercolationStats pStat = new PercolationStats(100, 100);
		String confidence = pStat.confidenceLo() + ", " + pStat.confidenceHi();
		StdOut.println("mean                    = " + pStat.mean());
		StdOut.println("stddev                  = " + pStat.stddev());
		StdOut.println("95% confidence interval = " + confidence);
	}
}
