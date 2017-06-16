
/**
 * Auto Generated Java Class.
 */

import java.util.ArrayList;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	public Block[][] grid;
	private int dim, topIndex, bottomIndex;
	private WeightedQuickUnionUF wqf;

	/**
	 * The constructor initializes the class by creating NxN grid with all
	 * closed cells initializes the quick union find data structure needed.
	 * 
	 * @param N
	 *            the dimension of the grid
	 * @exception IllegalArgumentException
	 *                for N <=0
	 */
	public Percolation(int N) {
		if (N <= 0)
			throw new java.lang.IllegalArgumentException();
		dim = N;
		topIndex = 0;
		bottomIndex = (dim * dim) + 1;

		// creating N*N WeightedQuickUnionUF with 2 extras for top and bottom
		// creating NxN grid with all closed cells
		wqf = new WeightedQuickUnionUF((dim * dim) + 2);
		grid = new Block[dim][dim];

		int idx = 0;
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				grid[i][j] = new Block(i + 1, j + 1, (idx++) + 1);

	}

	/**
	 * this method get all the neighbors of a cell that are open and connects
	 * the cell to them
	 * 
	 * @param block
	 *            the block to be considered
	 */
	private void connectOpenNeighbors(Block block) {
		int x = block.i - 1;
		int y = block.j - 1;
		// join to the top or bottom if it is at the first or last row.
		if (x == 0)
			wqf.union(block.index, bottomIndex);
		if (x == dim - 1)
			wqf.union(block.index, topIndex);
		// get neighbors and account for the corners and edge blocks
		for (int xx = x - 1; xx <= x + 1; xx++) {
			for (int yy = y - 1; yy <= y + 1; yy++) {
				if ((x > -1 && x < dim) && 
					(y > -1 && y < dim) && 
					(x != xx || y != yy) && 
					(xx >= 0 && xx < dim)&& 
					(yy >= 0 && yy < dim)) 
				{
					Block neighbor = grid[xx][yy];
					// connect only neighbors that are open
					if (neighbor.status) {
						if (!wqf.connected(block.index, neighbor.index)) {
							wqf.union(block.index, neighbor.index);
						}
					}
				}
			}
		}
	}

	/**
	 * Opens a cell block
	 * 
	 * @param i
	 *            the row of the block
	 * @param j
	 *            the column of the block
	 */
	public void open(int i, int j) {
		validateArgs(i, j);
		Block block = grid[i - 1][j - 1];
		block.status = true;
		connectOpenNeighbors(block);
	}

	/**
	 * check if a cell is open
	 * 
	 * @param i
	 *            the row of the block
	 * @param j
	 *            the column of the block
	 */
	public boolean isOpen(int i, int j) {
		validateArgs(i, j);
		return grid[i - 1][j - 1].status;
	}

	/**
	 * check if a cell is full or connect to the top
	 * 
	 * @param i
	 *            the row of the block
	 * @param j
	 *            the column of the block
	 */
	public boolean isFull(int i, int j) {
		validateArgs(i, j);
		return wqf.connected(grid[i - 1][j - 1].index, topIndex);
	}

	/**
	 * check if the grid percolates
	 */
	public boolean percolates() {
		return wqf.connected(topIndex, bottomIndex);
	}

	private void validateArgs(int i, int j) {
		i = i - 1;
		j = j - 1;
		if (!isValid(i, j))
			throw new java.lang.IndexOutOfBoundsException();
	}

	private boolean isValid(int i, int j) {
		return i >= 0 && i < dim && j >= 0 && j < dim;
	}

	/** method to help print the grid */
	public void printGrid() {
		for (int i = 0; i < dim; i++) {
			System.out.println(" ");
			for (int j = 0; j < dim; j++) {
				Block cell = grid[i][j];
				System.out.print((cell.status) ? 'o' : 'x');
				System.out.print((' '));
			}
		}
	}

	public static void main(String[] args) {

	}

}
