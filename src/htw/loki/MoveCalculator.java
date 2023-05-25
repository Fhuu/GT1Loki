package htw.loki;

public class MoveCalculator {
	
	private AIAlgorithm algorithm;
	private GameBoard gameboard;
	private final int LARGEST_NUMBER = Integer.MAX_VALUE;
	private final int SMALLEST_NUMBER = Integer.MIN_VALUE;
	private int playerNumber;
	
	public MoveCalculator(AIAlgorithm algorithm, int playerNumber) {
		this.algorithm = algorithm;
		this.gameboard = GameBoard.getInstance();
		this.playerNumber = playerNumber;
	}
	
	
	public int calculate(int depth) {
		if(this.algorithm.equals(AIAlgorithm.MINIMAX)) return minimax(this.playerNumber, depth);
		return -1;
	}
	
	
	// TODO: fix for when only 2 players are playing
	public int minimax(int playerNumber, int depth) {
		final int nextPlayer = playerNumber >= 2 ? 0 : playerNumber + 1;
		if(depth == 0) return 1;
		
		
		
		int result;
		if(playerNumber == this.playerNumber) {
			//TODO: calculate maximizing turn
			result = Math.max(Integer.MIN_VALUE, minimax(nextPlayer, depth - 1));
			
			// Here should undo move
			
			return result;
		}
		
		result = Math.min(Integer.MAX_VALUE, minimax(nextPlayer, depth));
		
		// Here should undo move
		
		return result;
		
	}
	
	public boolean hasGameStopped() {
		GameBoard gameboard = GameBoard.getInstance();
		Stone[] stones = gameboard.getStones(this.playerNumber);
		if(stones[0].getPosition() == 0 && stones[1].getPosition() == 0 && stones[2].getPosition() == 0 && stones[3].getPosition() == 0) {
			
		}
		
		return false;
	}
}
