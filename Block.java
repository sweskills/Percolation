
/**
 * The data structure is the unit representation of a grid block and it defaults the open status to close
 */
public class Block {
	
	public int index, j, i;
	public boolean status;
	/**
	 * The constructor
	 * @param i row id of the block in the grid
	 * @param j column id of the block in the grid
	 * @param index the index number of the cell starting from 1
	 */
	public Block(int i, int j, int index) {
		this.index = index;
		this.i = i;
		this.j = j;
		this.status = false;
	}
	/**
	 * The string representation of the block
	 */
	public String toString(){
		return "i:"+i+", y:"+j+" status:"+status;
	}

}
