package htw.loki;

public class MoveCalculator {
	
	private AIAlgorithm algorithm;
	private GameBoard gameboard;
	private final int LARGEST_NUMBER = 10000;
	private final int SMALLEST_NUMBER = -10000;
	private int calculationDepth;
	
	public MoveCalculator(AIAlgorithm algorithm) {
		this.algorithm = algorithm;
		this.gameboard = GameBoard.getInstance();
		this.calculationDepth = 2;
	}
	
	public int calculate() {
		if(this.algorithm.equals(AIAlgorithm.MINIMAX)) return minimax(true, 2, 1);
		return -1;
	}
	
	
	public void setCalculationDepth(int newDepth) {
		this.calculationDepth = newDepth;
	}
	
	// TODO: fix for when only 2 players are playing
	public int minimax(boolean isMaximizing, int depth, int minCount) {
		if(this.calculationDepth == depth) return 1;
		if(isMaximizing) {
			//TODO: calculate maximizing turn
			int result = Math.max(this.SMALLEST_NUMBER, minimax(true, depth, 1));
			return 1;
		}
		
		// Calculate the minimizing turn
		for(int position : gameboard.getPossibleNextPositions(minCount))
		
		// Call next recursion
		if(minCount == Loki.playerCount) {
			return Math.min(this.LARGEST_NUMBER, minimax(true, depth, 0));
		}
		return Math.min(this.LARGEST_NUMBER, minimax(false, depth, 2));
	}
}
