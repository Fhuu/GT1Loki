package htw.loki;

public class MoveCalculator {
	
	private AIAlgorithm algorithm;
	private GameBoard gameboard;
	
	public MoveCalculator(AIAlgorithm algorithm) {
		this.algorithm = algorithm;
		this.gameboard = GameBoard.getInstance();
	}
	
	public int calculate() {
		if(this.algorithm.equals(AIAlgorithm.MINIMAX)) return minimax(true, 2, 1);
		return -1;
	}
	
	public int minimax(boolean isMaximizing, int depth, int minCount) {
		if(isMaximizing) {
			//TODO: calculate maximizing turn
			int result = minimax(false, depth, 1);
			return 1;
		}
		
		// Calculate the minimizing turn
		for(int position : gameboard.getPossibleNextPositions(minCount)x)
		
		// Call next recursion
		if(minCount == 2) {
			minimax(true, depth, 0);
		}
		minimax(false, depth, 2);
		// TODO: calculate minimizing turn		
		return 1;
	}
}
