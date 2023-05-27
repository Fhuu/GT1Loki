package htw.loki;

import java.util.ArrayList;
import java.util.Arrays;

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
		final ArrayList<Integer> othersPositions = new ArrayList<Integer>(Arrays.asList(this.gameboard.getAllStonePositionExcluding(this.playerNumber)));
		if(this.hasGameStopped()) {
			if(gameboard.hasPlayerWon(this.playerNumber)) return 2;
			return -2;
		}
		
		if(depth == 1) {
			for(Stone stone : gameboard.getStones(this.playerNumber)) {
				final Integer[] possibleMoves = stone.getValidMoves(this.playerNumber);
				
				// Check if it is possible to isolate enemy
				for(Integer move : possibleMoves) {
					if(othersPositions.contains(move)) return 1;
				}
			}
		}
		
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
		Stone[] stones = this.gameboard.getStones(this.playerNumber);
		
		int stoneCount = stones.length;
		for(Stone stone : stones) if(stone.getPosition() == -1) stoneCount--;
		if(stoneCount <= 2) return true;
		
		if(this.gameboard.hasPlayerWon(0) || this.gameboard.hasPlayerWon(1) || this.gameboard.hasPlayerWon(2)) return true;
		
		return false;
	}

	
	public int evaluateGame() {
		for(int playerIndex = 0; playerIndex <= 2; playerIndex++) {
			if(this.gameboard.hasPlayerWon(playerIndex) && playerIndex == this.playerNumber) return 2;
			if(this.gameboard.hasPlayerWon(playerIndex) && playerIndex != this.playerNumber) return -2;
		}
		
		
		
		return 0;
	}
}
