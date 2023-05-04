package htw.loki;

/**
 * DOKU:
 * Considered ArrayList for the linked neighbouring position because some position has only 1 or 2 neighbours
 * Decided to use two dimentional array because array has better performance compared to arraylist
 * becaue arraylist.get(index) is a function call and it takes more performance comparing to referencing the index of an array
 * 
 * TODO 
 * - Generate board neighbouring positions either in constructor or in a separated function
 * - preferably constructor
 * @author dirob
 *
 */
public class GameBoard {

	private int[][] neighbours = new int[36][3];
	
	// TODO
	public GameBoard() {
		
	}

	// returns all the neighbouring positions of a position
	public int[] getNeighbours(int index) {
		return this.neighbours[index];
	}
	
	//TODO 
	public void generateNeighbours() {
		
	}
}
