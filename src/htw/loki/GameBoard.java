package htw.loki;

public class GameBoard {

	private int[][] neighbours = new int[36][3];
	
	public GameBoard() {
		
	}

	public int[] getNeighbours(int index) {
		return this.neighbours[index];
	}
	
	//TODO
	public void generateNeighbours() {
		
	}
}
